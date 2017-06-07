package com.newspace.aps.payChannel.fujian;

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
import com.newspace.aps.payChannel.fujian.utils.FujianPayUtils;
import com.newspace.common.utils.SpringBeanUtils;
import com.newspace.common.utils.StringUtils;

/**
 * @description 福建广电异步通知Servlet
 * @author huqili
 * @date 2016年11月28日
 *
 */
@WebServlet("/fujianNotify.do")
public class FujianNotifyServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	PayUtils payUtils = (PayUtils) SpringBeanUtils.getBeanByClass(PayUtils.class);
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		logger.info("进入福建广电通知接口...");
		
		
		Order order = null;
		PrintWriter out = response.getWriter();
		/**
		 * 接收到的reqStr格式为：
		 * merchantID=00001&ordNum=2014000003&ordDate=20140730&payMent=100.00&payState=Y&platformNo=201400000332&tranBank=CUP
		 * &tranBankNo=201400000353&orgPara=&tranTime=123453
		 */
		String reqStr = getStrFromRequest(request);
		logger.info(String.format("福建广电通知接口接收到的通知参数为：%s",reqStr));

		if (!StringUtils.isExistNullOrEmpty(reqStr))
		{
			//将接收到的参数放到map中
			Map<String, String> map = resolveToMap(reqStr);
			
			//校验请求参数
			if(FujianPayUtils.verifySign(map))
			{
				logger.info(String.format("福建广电通知接口_订单[ %S] 校验通过，准备查询订单...", map.get("ordNum")));
				
				
				//根据ATET订单号查询订单
				order = payUtils.queryByNo(PayConfig.FLAG_ORDERNO,map.get("ordNum"),Boolean.TRUE);

				if (order != null) 
				{
					logger.info(String.format("福建广电通知接口_根据订单号 %S 查询到订单，准备响应 [Y]给广电...", map.get("ordNum")));
					
					//响应信息给广电
					out.write("Y");
					
					if (map.get("payState").equals("Y")) // Y-“成功”； N-“失败”； B-“不确定”
					{
						logger.info("福建广电通知接口_此订单支付成功...");
						order.setStatus(Order.STATUS_SUCCESS);
						
						//处理用户资产信息
						payUtils.doAsset(order,null);
					}
					else 
					{
						logger.info("福建广电通知接口_此订单支付失败...");
						order.setStatus(Order.STATUS_FAIL);
					}

					// 更新订单
					order.setPayType(Order.PAYTYPE_FUJIAN); 
					order.setPaymentOrderNo(map.get("platformNo")+"$"+map.get("tranBankNo"));  //更新BOSS的订单号
					payUtils.updateOrder(order);	
					
				} 
				else 
				{
					logger.error("福建广电通知接口，未查询到订单信息...接收到的请求参数是：" + reqStr);
					out.write("orderNo is not exist");
				}
			}
			else
			{
				logger.error("福建广电通知接口，验签失败...请求参数是：" + reqStr);
				out.write("Signature verification failed");
			}
		} 
		else
		{
			logger.error("福建广电通知接口，解析请求参数失败...请求参数是：" + reqStr);
		}
	}

}
