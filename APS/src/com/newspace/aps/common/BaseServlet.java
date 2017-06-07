package com.newspace.aps.common;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.newspace.aps.ReturnCode;
import com.newspace.common.utils.StringUtils;

/**
 * BaseServlet.java 
 * @description:  接口Servlet的基类，封装通用方法  
 * @author huqili
 * @date： 2015年3月4日
 */
public class BaseServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;


	/**
	 * 用于缓存每个Class对象关联的PropertyDescriptor[]的Map
	 */
	private static final Map<Class<? extends JsonVo>, PropertyDescriptor[]> cachedPropDescriptorMap = new HashMap<Class<? extends JsonVo>, PropertyDescriptor[]>();

	/**
	 * Object类所声明的方法集合
	 */
	private static final Set<Method> OBJECT_METHODS = new HashSet<Method>();

	protected transient Logger logger = Logger.getLogger(getClass());

	static
	{
		OBJECT_METHODS.addAll(Arrays.asList(Object.class.getDeclaredMethods()));
	}

	/**
	 * 填充vo：将Request请求参数填充到ReqVo对象中
	 * @param HttpServletRequest Http请求对象
	 * @param JsonVo 要进行填充的JsonVo对象
	 * @return returnCode  操作状态码
	 */
	protected int padRequestVo(HttpServletRequest request, JsonVo vo)
	{
		int returnCode = ReturnCode.SUCCESS.getCode();
		String jsonStr = getStrFromRequest(request);
		if (!StringUtils.isNullOrEmpty(jsonStr))
		{
			returnCode = padRequestVo(jsonStr, vo);
		}
		else
		{
			returnCode = ReturnCode.PARAM_ERROR.getCode();
		}
		return returnCode;
	}

	/**
	 * 填充vo：将json格式数据填充到ReqVo对象中
	 * @param json json数据
	 * @param JsonVo 要进行填充的JsonVo对象
	 */
	protected int padRequestVo(String json, JsonVo vo)
	{
		int retCode = ReturnCode.SUCCESS.getCode();
		JsonVo tmpVo = new Gson().fromJson(json, vo.getClass());

		Class<? extends JsonVo> clazz = vo.getClass();
		try
		{
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] props = cachedPropDescriptorMap.get(clazz);
			if (props == null)
			{
				props = beanInfo.getPropertyDescriptors();
				cachedPropDescriptorMap.put(clazz, props);
			}

			for (PropertyDescriptor prop : props)
			{
				if (!OBJECT_METHODS.contains(prop.getReadMethod()))
				{
					Method getMethod = prop.getReadMethod();
					if (getMethod != null)
					{
						Object value = getMethod.invoke(tmpVo, new Object[] {});
						if (value != null)
						{
							Method setMethod = prop.getWriteMethod();
							if (setMethod != null)
							{
								setMethod.invoke(vo, new Object[] { value });
							}
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			logger.error("解析json，填充ReqVo对象时出错！", e);
			retCode = ReturnCode.PARAM_ERROR.getCode();
		}
		return retCode;
	}


	/**
	 * 通过HttpServletResponse输出响应结果
	 * @param response Http响应
	 * @param vo 响应JsonVo类
	 */
	protected void outputResult(HttpServletResponse response, JsonVo vo)
	{
		String json = new Gson().toJson(vo);
		outputResult(response, json);
	}

	/**
	 * 重载方法：通过HttpServletResponse输出响应结果
	 * @param response Http响应
	 * @param json 要输出的json字符串
	 */
	protected void outputResult(HttpServletResponse response, String json)
	{
		try
		{
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.write(json);
			out.flush();
			out.close();
			logger.info(String.format("\r\n【响应结果: %s】", json));
		}
		catch (IOException e)
		{
			logger.error("发送响应结果失败！", e);
		}
	}

	/**
	 * 从HttpServletRequest中读取数据字符串。
	 */
	protected String getStrFromRequest(HttpServletRequest request)
	{
		String str = null;
		BufferedReader reader = null;
		try
		{
			if (request.getContentLength() > 0)
			{
				reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
				StringBuilder sb = new StringBuilder();
				while ((str = reader.readLine()) != null)
				{
					sb.append(str);
				}
				str = sb.toString();
				logger.info(String.format("\r\n【 接收到请求数据字符串：%s】", str));
			}
		}
		catch (IOException e)
		{
			logger.error("从HttpServletRequest中读取数据字符串失败！", e);
			str = null;
		}
		finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				}
				catch (IOException e)
				{
					logger.error("输入流BufferedReader关闭时发生错！", e);
				}
			}
		}
		return str;
	}
	
	/**
	 * 将请求参数封装到Map中，请求参数的格式是：key1=value1&key2=value2...
	 * @param requestStr
	 * @return
	 */
	protected Map<String, String> resolveToMap(String requestStr)
	{
		Map<String, String> map = new HashMap<String, String>();
		
		String[] reqStrs = requestStr.split("&");
		for(String str : reqStrs)
		{
			//请求参数中会有Src=&link=等空值情况出现，为了避免ArrayIndexOutOfBoundsException异常，封装时需要区分处理
			String[] valueStrs = str.split("=");
			if(!str.endsWith("="))
			{
				map.put(valueStrs[0], valueStrs[1]);
			}
			else
			{
				map.put(valueStrs[0], "");
			}
		}
		
		return map;
	}

	/**
	 * 从HttpServletRequest中读取参数并放入Map中
	 * Map的key为属性名，value为属性值
	 * @return Map<String, String>
	 */
	@SuppressWarnings({"rawtypes" })
	protected Map<String, String> generateParamMap(HttpServletRequest request)
	{
		// 将接收到通知消息的参数，使用key-value的形式保存到Map
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();)
		{
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			StringBuilder valueStr = new StringBuilder();
			for (int i = 0; i < values.length; i++)
				valueStr.append((i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",");
			params.put(name, valueStr.toString());
			logger.info(String.format("\r\n【解析出参数：%s，值：%s】", name, valueStr.toString()));
		}
		return params;
	}

}