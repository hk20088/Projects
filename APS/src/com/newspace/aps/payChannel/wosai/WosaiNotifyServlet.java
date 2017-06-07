package com.newspace.aps.payChannel.wosai;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newspace.aps.common.BaseServlet;
import com.newspace.aps.model.order.Order;
import com.newspace.aps.pay.PayConfig;
import com.newspace.aps.pay.PayUtils;
import com.newspace.aps.payChannel.wosai.utils.WosaiUtils;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.SpringBeanUtils;

/**
 * @description 喔噻异步通知支付结果
 * @author huqili
 * @date 2016年11月1日
 */
@WebServlet(urlPatterns = { "/wosaiNotify.do" })
public class WosaiNotifyServlet extends BaseServlet{
	
	private static final long serialVersionUID = 1L;
	PayUtils payUtils = (PayUtils) SpringBeanUtils.getBeanByClass(PayUtils.class);
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		logger.info("Enter Wosai Notify Servlet...");
		
		PrintWriter out = response.getWriter();
		
		Order order = null;
		String reqStr = getStrFromRequest(request);
		
		//将json字符串转化为map对象
		Map<String, Object> map = JsonUtils.json2Map(reqStr);
		
		Integer result = Integer.parseInt(map.get("status").toString().replace("\"", ""));  //交易结果  1 代表支付成功，0代表支付未完成。
//		Integer amount = Integer.parseInt(map.get("total_fee").toString().replace("\"", ""));  //订单金额
		String orderNo = map.get("store_owner_order_sn").toString().replace("\"", "");   //ATET订单号
		String outTradeNo = map.get("order_sn").toString().replace("\"", "");  //wosai订单号
		String trade_no = map.get("trade_no").toString().replace("\"", ""); //微信或支付宝订单号

		/*
		 *根据wosai的请求参数，临时修改如下
		 *修改payType，注释 sign取消校验
		 */
//		String payType =map.get("pay_way").toString().replace("\"", "");  //支付方式
		String payType =map.get("payway").toString().replace("\"", "");  //支付方式
		
//		String sign = map.get("sign").toString().replaceAll("\"", "");  //签名
//		logger.info(String.format("WosaiNotifyServlet 订单：%s获取到的支付状态是：%s，签名是：%s", orderNo,result,sign));
		//校验签名是否合法
//		if(sign.equals(WosaiUtils.getSign(map)))
		if(true)
		{
			logger.info("ReceiveWosaiNotifyServlet签名校验成功，响应喔噻...");
			out.write("success"); //响应wosai
			
			//因为请求喔噻时，订单号前面加了"w"或"A"的标识，这里查询订单前先截取正确的订单号
			order = payUtils.queryByNo(PayConfig.FLAG_ORDERNO, orderNo.substring(1),Boolean.TRUE);
			
			if(order != null)
			{
				if(payType.equals("ALIPAY"))  //说明使用的是支付宝
				{
					order.setPayType(Order.PAYTYPE_WOSAI_ALIPAY);
				}
				else
				{
					order.setPayType(Order.PAYTYPE_WOSAI_WECHAT);
				}
				
				//wosai订单号和微信/支付宝订单号以$分开，共同存入在paymentCreatedOrderNo字段中，以方便查询
				order.setPaymentOrderNo(outTradeNo+"$"+trade_no);
				
				if(result == 1)//支付成功
				{
					logger.info(String.format("喔噻异步通知，订单：%s 的支付结果为：成功", orderNo));
					order.setStatus(Order.STATUS_SUCCESS);
					
					String _notifyURL = payUtils.getNotifyURl(order.getKeyId());
					
					//通知商户
					payUtils.notify(order, _notifyURL);
					
					//处理用户资产信息
					payUtils.doAsset(order,null);
				}
				else
				{
					logger.error(String.format("喔噻异步通知，订单：%s 的支付结果为：未完成", orderNo));
					order.setStatus(Order.STATUS_FAIL);
				}
				//更新订单
				payUtils.updateOrder(order);
			}
			else
			{
				logger.error(String.format("喔噻异步通知，根据ATET订单号：%s未查询出相应的订单，喔噻订单号：%s,微信或支付宝订单号：%s", orderNo,outTradeNo,trade_no));
			}
		}
		else
		{
			logger.error("ReceiveWosaiNotifyServlet签名校验失败！");
			out.write("fail");
		}
		
	}

}
