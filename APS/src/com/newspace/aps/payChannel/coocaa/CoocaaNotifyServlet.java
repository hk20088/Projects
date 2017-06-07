package com.newspace.aps.payChannel.coocaa;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newspace.aps.common.BaseServlet;
import com.newspace.aps.model.order.Order;
import com.newspace.aps.pay.PayConfig;
import com.newspace.aps.pay.PayUtils;
import com.newspace.common.utils.SpringBeanUtils;

/** 
 * @Description: 创维酷开回调接口
 * @author liqiao
 * @date 2017年2月10日 下午3:18:26   
 */
@WebServlet(urlPatterns = { "/coocaaNotify.do" })
public class CoocaaNotifyServlet extends BaseServlet{
	
	private static final long serialVersionUID = 1L;
	PayUtils payUtils = (PayUtils) SpringBeanUtils.getBeanByClass(PayUtils.class);
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		logger.info("进入酷开支付回调接口...");
		
		PrintWriter out = response.getWriter();
		
		Order order = null;
		//从HttpServletRequest中读取数据字符串
		String reqStr = getStrFromRequest(request);
		
		//将json字符串转化为map对象
//		Map<String, Object> map = JsonUtils.json2Map(reqStr);
		Map<String,Object> map = new Gson().fromJson(reqStr, new TypeToken<HashMap<String,Object>>(){}.getType());
		
		//校验请求是否合法
		boolean flag = CoocaaUtils.isLegal(map);
		if(flag)
		{
			logger.info("CoocaaNotifyServlet 酷开支付回调接口请求参数校验成功！");
			
			order = payUtils.queryByNo(PayConfig.FLAG_ORDERNO,map.get("order_id").toString(),Boolean.TRUE);
			if(order == null)
			{
				logger.error("CoocaaNotifyServlet 根据订单号未查询出对应订单信息！");
				out.print("success");
			}
			else
			{
				if("0000".equals(map.get("resp_code")))
				{
					order.setStatus(Order.STATUS_SUCCESS);
				}
				else
				{
					order.setStatus(Order.STATUS_FAIL);;
				}
				//配置支付手机号
				order.setPhone(map.get("tel").toString());
				//配置支付方式
				order.setPayType(Order.PAYTYPE_COOCAA);
				
				logger.info("ReceiveCoocaaNotifyServlet 即将更新订单");
				//更新订单
				payUtils.updateOrder(order);
				
				String _notifyURL = payUtils.getNotifyURl(order.getKeyId());
				//通知商户
				payUtils.notify(order, _notifyURL);
				//处理用户资产信息
				payUtils.doAsset(order,null);
				
				//返回success
				out.print("success");
				logger.info("CoocaaNotifyServlet 已响应酷开success");
			}
		
		}
	}

}
