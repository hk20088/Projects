package com.newspace.aps.payChannel.wocheng;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newspace.aps.common.BaseServlet;
import com.newspace.aps.common.ConstantData;
import com.newspace.aps.model.order.Order;
import com.newspace.aps.pay.PayConfig;
import com.newspace.aps.pay.PayUtils;
import com.newspace.common.utils.HttpUtils;
import com.newspace.common.utils.SpringBeanUtils;

@WebServlet("/wocNotify.do")
public class WochengNotifyServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	PayUtils payUtils = (PayUtils) SpringBeanUtils.getBeanByClass(PayUtils.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		logger.info("进入沃橙异步通知接口...");
		PrintWriter out = response.getWriter();
		Order order = new Order();

		Map<String, String> map = generateParamMap(request);

		if (WochengUtils.verify(map)) 
		{
			// 根据订单号获取订单对象,并将请求参数填充到对象中,然后更新记录。
			order = payUtils.queryByNo(PayConfig.FLAG_ORDERNO, map.get("cpparam"),Boolean.TRUE);
			if (order != null) 
			{
				// 响应沃橙
				out.write("ok");

				if (map.get("chargestatus").equals("0")) // 支付成功
				{
					order.setStatus(Order.STATUS_SUCCESS);

					// 广电属内部网，暂不需要通知CP
					String _notifyURL = payUtils.getNotifyURl(order.getKeyId());

					// 通知商户
					payUtils.notify(order, _notifyURL);

					// 处理用户资产信息
					payUtils.doAsset(order, null);
					
				} 
				else
				{
					order.setStatus(Order.STATUS_FAIL);
				}
				
				// 更新订单
				order.setExternalOrderNo(map.get("linkid"));
				order.setPayType(Order.PAYTYPE_WOCHENG);
				payUtils.updateOrder(order);
			} 
			else 
			{
				logger.error(String.format("沃橙异步通知_根据订单号[orderNo = %s]未查询出订单，沃橙订单号为：%s...", map.get("cpparam") , map.get("linkid")));
				try 
				{
					HttpUtils.post(ConstantData.ATET_WOCHENG_PAY_URL, map);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}
		else
		{
			logger.error("沃橙异步通知参数校验失败...");
			out.write("Signature verification failed");
		}
	}

}
