package com.newspace.aps.payChannel.guizhou;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newspace.aps.ReturnCode;
import com.newspace.aps.common.BaseServlet;
import com.newspace.aps.model.order.Order;
import com.newspace.aps.pay.PayConfig;
import com.newspace.aps.pay.PayUtils;
import com.newspace.aps.payChannel.guizhou.param.GuizhouNotifyContent;
import com.newspace.aps.payChannel.guizhou.utils.GuizhouPayUtils;
import com.newspace.common.utils.SpringBeanUtils;

/**
 * @description 扫码支付成功后， BOSS会异步通知平台支付结果
 * @author huqili
 * @date 2016年9月23日
 */
@WebServlet("/guizhouNotify.do")
//@Service 备注：servlet并不能直接调用@service的注解，因为springmvc并没有将其注入，如果直接使用@service 会报 cannot create resource instance 异常
public class GuizhouNotifyServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	PayUtils payUtils = (PayUtils) SpringBeanUtils.getBeanByClass(PayUtils.class);
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.info("进入贵州广电通知接口...");

		GuizhouNotifyContent content = new GuizhouNotifyContent();
		Order order = null;

		/**
		 * 接收到的reqStr格式为：
		 * version=1&clientcode=SZATT001&clientpwd=6cb2d0b736f3841658074f9b854e8c54&citycode=5201&servicecode=
		 * &requestid=SZATT00120160504974b9659
		 * &requestContent=%7B%22bankaccno%22%3A%22%22%2C%22orderNo%22%3A%22100000271322%22%2C%22paycode%22%3A%22040400%22%2C%22payreqid%22%3A%22201605040001896383%22%2C%22payway%22%3A%2233%22%2C%22status%22%3A%222%22%7D
		 */
		String reqStr = getStrFromRequest(request);

		//处理reqStr，拿到requestContent的值
		reqStr = reqStr.split("requestContent=")[1];
		reqStr = URLDecoder.decode(reqStr,"utf-8");
		
		logger.info(String.format("贵州广电通知接口_处理后的通知参数为：%s",reqStr));
		int returnCode = padRequestVo(reqStr, content);

		if (returnCode == ReturnCode.SUCCESS.getCode()) 
		{
			//根据BOSS订单号查询订单(因为PaymentOrderNo可能拼装了两个BOSS订单，这里需要做一个处理)
			order = payUtils.queryByNo(PayConfig.FLAG_PAYMENTORDERNO,content.getOrderNo(),Boolean.TRUE);

			if (order != null) {
				logger.info(String.format("贵州广电通知接口_根据订单号 %S 查询到订单...", content .getOrderNo()));
				
				if (content.getStatus().equals("2")) // 当BOSS的状态为 2 时，代表支付成功
				{
					logger.info("贵州广电通知接口_此订单支付成功...");
					order.setStatus(Order.STATUS_SUCCESS);
					
					/*//广电属内部网，暂不需要通知CP
					String _notifyURL = payUtils.getNotifyURl(order.getKeyId());
					
					//通知商户
					payUtils.notify(order, _notifyURL);*/
					
					//处理用户资产信息
					payUtils.doAsset(order,null);
					
				} else {
					logger.info("贵州广电通知接口_此订单支付失败...");
					order.setStatus(Order.STATUS_FAIL);
				}

				
				if(content.getPayway().equals("33"))  //支付宝
				{
					order.setPayType(Order.PAYTYPE_GUIZHOU_ALIPAY); 
				}
				else  //微信
				{
					order.setPayType(Order.PAYTYPE_GUIZHOU_WECHAT); 
				}
				
				// 更新订单
				order.setPaymentOrderNo(content.getOrderNo());  //更新BOSS的订单号，因为生成订单时它可能封装了微信和支付宝两个订单号
				payUtils.updateOrder(order);	

				// 调用订单确认接口，通知支付网关
				logger.info("贵州广电异步通知接口_开始通知支付网关...");
				GuizhouPayUtils.ConfirmOrder(order.getExtraPayTypeInfo(), content.getOrderNo(), content.getPaycode(), content.getPayway(), content.getStatus());
				
			} else {
				logger.error("贵州广电通知接口，未查询到订单信息...请求参数是：" + reqStr);
			}
		} else {
			logger.error("贵州广电通知接口，解析请求参数失败...请求参数是：" + reqStr);
		}
	
	}

}
