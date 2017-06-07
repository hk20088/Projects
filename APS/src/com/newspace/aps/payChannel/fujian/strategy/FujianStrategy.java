package com.newspace.aps.payChannel.fujian.strategy;

import java.util.List;

import com.newspace.aps.ReturnCode;
import com.newspace.aps.model.Merchandise;
import com.newspace.aps.model.order.Order;
import com.newspace.aps.model.order.PayRespVo.PayRespData.ResultBody;
import com.newspace.aps.pay.PayConfig;
import com.newspace.aps.pay.PayHandler;
import com.newspace.aps.pay.param.PayResponVo;
import com.newspace.aps.pay.strategy.BaseStrategy;
import com.newspace.aps.pay.strategy.IPayStrategy;
import com.newspace.aps.payChannel.fujian.FujianHandler;
import com.newspace.common.utils.SpringBeanUtils;
import com.newspace.common.utils.TwoTuple;

/**
 * @description 福建广电支付策略类，实现此策略的具体逻辑
 * @author huqili
 * @date 2017年2月16日
 * @remark 此类是通过反射机制拿到的（相当于new出的对象）， 所以不能使用注解得到其对象。 此类下面的其它对象也不能使用注解拿到
 */
public class FujianStrategy extends BaseStrategy implements IPayStrategy {

	FujianHandler fujianHandler = (FujianHandler) SpringBeanUtils.getBeanByClass(FujianHandler.class);
	
	@Override
	public PayResponVo pay(Order order, Merchandise merch, List<ResultBody> bodyList, String clientPackageName) 
	{
		logger.info("Enter FuJian Pay...");
		
		int returnCode = ReturnCode.SUCCESS.getCode();  //定义默认状态码
		String status = Order.STATUS_WAIT;  //定义默认支付状态
		
		//获取支付请求URL
		TwoTuple<Integer, String> result = fujianHandler.getHttpsUrl(order);
		String payUrl = result.second;
		
		//封装响应参数
		bodyList = PayHandler.fillBodyList(bodyList, PayConfig.PAY_SPQ, PayHandler.getParam(payUrl, Order.PAYTYPE_FUJIAN));
		
		//赋值给returnCode
		returnCode = result.first;
		
		return PayHandler.getRespVo(returnCode, status);
	}

}
