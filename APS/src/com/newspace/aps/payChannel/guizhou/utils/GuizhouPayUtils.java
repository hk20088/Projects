package com.newspace.aps.payChannel.guizhou.utils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.newspace.aps.payChannel.guizhou.param.CustomerInfo;
import com.newspace.aps.payChannel.guizhou.param.PayResp;

/**
 * @description 贵州广电扣费接口工具类
 * @author Huqili
 *
 */
public class GuizhouPayUtils {

	private static final Logger logger = Logger.getLogger(GuizhouPayUtils.class);
	
	/**
	 * 请求获取城市代码
	 * @return
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	public static String getCityCode(String sm_no) throws JsonProcessingException, IOException
	{
		String citycode = null;
		
		//生成获取城市代码的URL地址
		String url = GuizhouUrlUtils.getCityCodeUrl(sm_no);
		logger.info("贵州广电计费_获取城市代码的请求URL是："+url);
		
		//发送请求
		String respStr = GuizhouUrlUtils.httpGet(url);
		logger.info("贵州广电计费_获取城市代码的响应参数是："+respStr);
		
		if(respStr != null)
		{
			//解析响应的json串
			ObjectMapper mapper = new ObjectMapper();
			
			JsonNode dataJson = mapper.readTree(respStr);
			String status = dataJson.get("status").toString();
			if(status.equals("\"0000\""))
			{
				JsonNode output = mapper.readTree(dataJson.get("output").toString());
				citycode = output.get("citycode").toString().replaceAll("\"", "");
			}
		}
		
		return citycode;
	}

	
	/**
	 * 请求获取客户信息(GuizhouPayServlet接口使用)
	 * @param sm_no
	 * @param citycode
	 * @param respVo
	 * @return
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static CustomerInfo getCusInfo(String sm_no,String citycode) throws JsonProcessingException, IOException
	{
		CustomerInfo info = new CustomerInfo();
		
		//生成获取客户信息的URL地址
		String url = GuizhouUrlUtils.getCustInfoUrl(sm_no, citycode);
		logger.info("贵州广电计费_获取客户信息的请求URL是："+url);
		
		//发起请求
		String respStr = GuizhouUrlUtils.httpGet(url);
		logger.info("贵州广电计费_获取客户信息的响应参数是："+respStr);
		
		if(respStr != null)
		{
			//解析响应的json串
			ObjectMapper mapper = new ObjectMapper();
			
			JsonNode dataJson = mapper.readTree(respStr);
			String status = dataJson.get("status").toString();
			if(status.equals("\"0\""))
			{
				//解析output
				List<Object> list = mapper.readValue(dataJson.get("output").toString(), List.class);
				LinkedHashMap map = (LinkedHashMap) list.get(0);
				
				info.setCustid(map.get("custid").toString());
				info.setCustName(URLEncoder.encode(map.get("custname").toString(),"utf-8"));
				info.setCustAddress(URLEncoder.encode(map.get("cusaddr").toString(),"utf-8") );
				info.setPhone(map.get("mobile").toString());
				info.setExtraId(sm_no);
			}
			
		}
		
		return info;
	}
	
	/**
	 * 查询用户余额
	 * @return
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	public static double queryBalance(String citycode,String custid) throws JsonProcessingException, IOException
	{
		double fee = 0;
		
		//生成查询用户余额的URL地址
		String url = GuizhouUrlUtils.getQueryBalanceUrl(citycode, custid);
		logger.info("贵州广电计费_查询用户余额的请求URL是："+url);
		
		//发起请求
		String respStr = GuizhouUrlUtils.httpGet(url);
		logger.info("贵州广电计费_查询用户余额的响应参数是："+respStr);
		
		if(respStr != null)
		{
			//解析响应的json串
			ObjectMapper mapper = new ObjectMapper();
			
			JsonNode dataJson = mapper.readTree(respStr);
			String status = dataJson.get("status").toString();
			if(status.equals("\"0\""))
			{
				JsonNode output = mapper.readTree(dataJson.get("output").toString());
				fee = Double.valueOf(output.get("feesums").toString().replace("\"", ""));
			}
		}

		return fee;
	}
	
	/**
	 * 钱袋支付(单点计费)
	 * @return
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	public static PayResp pay(String sm_no,double fee,String payPoint,String payPointName) throws JsonProcessingException, IOException
	{
		PayResp payResp = new PayResp();
		
		//生成请求钱袋支付的URL地址
		String url = GuizhouUrlUtils.getPayUrl(sm_no, fee, payPoint, payPointName);
		logger.info("贵州广电计费_钱袋支付(单点)的请求URL是："+url);
		
		//发起请求
		String respStr = GuizhouUrlUtils.httpGet(url);
		logger.info("贵州广电计费_钱袋支付(单点)的响应参数是："+respStr);
		
		if(respStr != null)
		{
			//解析响应的json串
			ObjectMapper mapper = new ObjectMapper();
			
			JsonNode dataJson = mapper.readTree(respStr);
			payResp.setStatus(dataJson.get("status").toString().replaceAll("\"", ""));
			payResp.setMessage(dataJson.get("message").toString().replaceAll("\"", ""));
		}
		
		return payResp;
	}
	
	/**
	 * 钱袋支付(包月/套餐)
	 * @param flag 
	 * 		month: 包月；
	 * 		half_year: 半年套餐
	 * 		one_year:  一年套餐 
	 * 		备注：包月 和套餐的产品代码不同
	 * @param sm_no
	 * @param fee
	 * @return PayResp
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	public static PayResp xpay(String flag,String sm_no,double fee,String packageName) throws JsonProcessingException, IOException
	{
		PayResp payResp = new PayResp();
		
		//生成请求钱袋支付的URL地址
		String url = GuizhouUrlUtils.getXPayUrl(flag, sm_no, fee,packageName);
		logger.info("贵州广电计费_钱袋支付(包月/套餐)的请求URL是："+url);
		
		//发起请求
		String respStr = GuizhouUrlUtils.httpGet(url);
		logger.info("贵州广电计费_钱袋支付(包月/套餐)的响应参数是：："+respStr);
		
		if(respStr != null)
		{
			//解析响应的json串
			ObjectMapper mapper = new ObjectMapper();
			
			JsonNode dataJson = mapper.readTree(respStr);
			
			String state = dataJson.get("state").toString().replaceAll("\"", "");
			String info = dataJson.get("info").toString().replaceAll("\"", "");
			
			if(state.equals("1"))  //代表扣费成功。备注：因为单点计费扣费成功时返回的状态是 0000。 这里为了方便在GuizhouPayServlet里统一处理，手动将成功状态改为 0000
			{
				payResp.setStatus("0000");
			}
			else
			{
				payResp.setStatus(state);
			}
			
			payResp.setMessage(info);
		}
		
		
		return payResp;
	}
	
	
	/**
	 * 请求[订单订购接口]获取扫码支付的订单号
	 * @param flag
	 *   	 flag = once 单次点播
	 *   	 flag = month  包月
	 *   	 flag = half_year 半年套餐
	 *       flag = one_year  一年套餐
	 * @return  payUrl
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	public static String getOrderid(String flag,String cityCode,String custid,String fee,String sm_no,String payPoint,String productName,String packageName) throws JsonProcessingException, IOException
	{
		String orderid = null;
		
		//生成订单订购的URL地址
		String url = GuizhouUrlUtils.getOrderNoUrl(flag,cityCode, custid, sm_no, fee, payPoint, productName,packageName);
		logger.info("贵州广电计费_获取订单号请求URL是："+url);
		//发送请求
		String respStr = GuizhouUrlUtils.httpGet(url);
		logger.info("贵州广电计费_获取订单号响应参数是："+respStr);
		
		if(respStr != null)
		{
			//解析响应json串
			ObjectMapper mapper = new ObjectMapper();
			
			JsonNode dataJson = mapper.readTree(respStr);
			String status = dataJson.get("status").toString();
			if(status.equals("\"0\""))
			{
				JsonNode output = mapper.readTree(dataJson.get("output").toString());
				orderid = output.get("orderid").toString().replace("\"", "");
			}
			else if(status.equals("\"101\""))  //当状态为 101时，代表当前用户已订购过该产品
			{
				orderid = "0";
			}
		}
		
		return orderid;
	}
	
	/**
	 * 调用确认订单接口
	 * @param citycode
	 * @param orderid
	 * @param paycode
	 * @param payway
	 * @param status
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	public static void ConfirmOrder(String citycode,String orderid,String paycode,String payway,String status) throws JsonProcessingException, IOException
	{
		//生成订单确认的请求URL
		String url = GuizhouUrlUtils.getConfirmOrderUrl(citycode, orderid, paycode, payway, status);
		logger.info("贵州广电计费_确认订单接口的请求URL是："+url);
		
		//发送请求
		String respStr = GuizhouUrlUtils.httpGet(url);
		logger.info("贵州广电计费_确认订单接口的响应参数是："+respStr);
		
		if(respStr != null)
		{
			//解析响应json串
			ObjectMapper mapper = new ObjectMapper();
			
			JsonNode dataJson = mapper.readTree(respStr);
			String state = dataJson.get("status").toString();
			
			if(state.equals("\"0\""))
			{
				logger.info("贵州广电计费，确认订单接口应答成功！");
			}
			else
			{
				logger.error("贵州广电计费，确认订单接口应答失败，应答信息是："+respStr);
			}
		}
	}

}
