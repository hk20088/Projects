package com.newspace.aps.payChannel.unicomiptv;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.newspace.aps.ReturnCode;
import com.newspace.aps.common.BaseServlet;
import com.newspace.aps.model.order.Order;
import com.newspace.aps.pay.PayConfig;
import com.newspace.aps.pay.PayUtils;
import com.newspace.aps.payChannel.unicomiptv.param.IptvRespVo;
import com.newspace.aps.payChannel.unicomiptv.param.IptvReturnCode;
import com.newspace.aps.payChannel.unicomiptv.utils.IptvUtils;
import com.newspace.common.utils.SpringBeanUtils;

/**
 * @description 联通IPTV异步通知接口
 * @author huqili
 * @since JDK1.6
 * @date 2017年5月23日
 *
 */
@WebServlet("/IptvNotify.do")
public class IptvNotifyServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(IptvNotifyServlet.class);
	PayUtils payUtils = (PayUtils) SpringBeanUtils.getBeanByClass(PayUtils.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.info("进入联通IPTV异步通知接口...");
		
		int returnCode = IptvReturnCode.UNICOM_IPTV_SUCCESS.getCode();
		IptvRespVo respVo = new IptvRespVo();
		Order orderVo = new Order();
		
		String reqStr = request.getQueryString();
		logger.info(String.format("接收到联通IPTV异步通知的内容是：%s", reqStr));
		
		if(reqStr != null)
		{
			try 
			{
				//将请求参数封装到map中
				Map<String, String> params = resolveToMap(reqStr);
				
				//校验sign是否有效
				if(IptvUtils.verify(params))
				{
					//根据订单号获取订单对象,并将请求参数填充到对象中,然后更新记录。
					orderVo = payUtils.queryByNo(PayConfig.FLAG_ORDERNO, params.get("TradeNo"),Boolean.TRUE);
					
					if(orderVo != null)
					{
						//将参数封装到Order对象中
						orderVo = fillOrderVo(orderVo, params);

						/**
						 * 支付结果状态定义： 0：支付成功。
						 * 1：订单处理中（最终结果以后台通知为准，如有必要由应用调用queryPayment做漏单查询）。
						 * 2：支付失败，余额不足。 3：支付失败，用户主动取消支付。 9：支付失败，其它错误。
						 * 
						 * 目前用的这个支付结果定义
						 * 支付结果： Completed:支付成功 Failed:失败 Canceled:取消支付 * Expired:处理中
						 */
						if(params.get("TradeStatus").equals("completed"))
						{
							orderVo.setStatus(Order.STATUS_SUCCESS);
							
							String _notifyURL = payUtils.getNotifyURl(orderVo.getKeyId());
							
							//通知商户
							payUtils.notify(orderVo, _notifyURL);
							
							//处理用户资产信息
							payUtils.doAsset(orderVo,null);
						}
						else
						{
							orderVo.setStatus(Order.STATUS_FAIL);
						}
						
						//更新订单
						payUtils.updateOrder(orderVo);
					}
					else
					{
						logger.error("联通IPTV异步通知接口，订单号无效...");
						returnCode = IptvReturnCode.UNICOM_IPTV_ORDERNO_INVALID.getCode();
					}
				}
				else
				{
					logger.error("联通IPTV异步通知，校验签名失败...");
					returnCode = IptvReturnCode.UNICOM_IPTV_SIGN_INVALID.getCode();
				}
				
			} 
			catch (Exception e) 
			{
				logger.error("联通IPTV异步通知接口出现异常...",e);
				returnCode = IptvReturnCode.UNICOM_IPTV_FAIL.getCode();
			}
		}
		else
		{
			logger.error("联通IPTV异步通知请求参数无效...");
			returnCode = IptvReturnCode.UNICOM_IPTV_PARAM_INVALID.getCode();
		}
		
		respVo.setErrorCode(String.valueOf(returnCode));
		respVo.setErrorDesc(ReturnCode.getDesc(returnCode));
		
		outputResult(response, respVo);
		
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
			//请求参数中会有Src=&link=等空值情况出现，为了避免ArrayIndexOutOfBoundsException异常，封装Map时做个判断
			if(!str.endsWith("="))
			{
				String[] valueStrs = str.split("=");
				map.put(valueStrs[0], valueStrs[1]);
			}
			else
			{
				map.put(str.substring(0,str.indexOf("=")), "");
			}
		}
		
		return map;
	}

	/**
	 * 封装订单对象
	 * @param orderVo
	 * @param orderId
	 * @param channelId
	 * @return
	 */
	private Order fillOrderVo(Order orderVo,Map<String, String> map)
	{
		orderVo.setAmount( (int)(Double.parseDouble(map.get("Amount"))) * 100);  //金额
		orderVo.setExtraPayTypeInfo((map.get("ConsumeStreamId")));
		orderVo.setPayPointName(map.get("Subject"));
		orderVo.setPayType(Order.PAYTYPE_IPTV);
		orderVo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		return orderVo;
	}
}
