package com.newspace.aps.payChannel.wocheng.strategy;

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


/**
 * @description 沃橙支付策略类，实现此策略的具体逻辑
 * @author huqili
 * @date 2017年2月16日
 * @remark 此类是通过反射机制拿到的（相当于new出的对象）， 所以不能使用注解得到其对象。 此类下面的其它对象也不能使用注解拿到
 */
public class WoChengStrategy extends BaseStrategy implements IPayStrategy {

	@Override
	public PayResponVo pay(Order order, Merchandise merch, List<ResultBody> bodyList, String clientPackageName)
	{
		
		logger.info("Enter Unicom Wocheng...");
		
		int returnCode = ReturnCode.SUCCESS.getCode();  //定义默认状态码
		String status = Order.STATUS_WAIT;  //定义默认支付状态
		
		bodyList = PayHandler.fillBodyList(bodyList, PayConfig.PAY_CTS, PayHandler.getParamCTS(Order.PAYTYPE_WOCHENG));
		
		return PayHandler.getRespVo(returnCode, status);
	}

}
