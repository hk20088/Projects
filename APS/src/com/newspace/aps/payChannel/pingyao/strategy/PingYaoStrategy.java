package com.newspace.aps.payChannel.pingyao.strategy;

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
import com.newspace.aps.payChannel.pingyao.PingYaoHandler;
import com.newspace.aps.payChannel.pingyao.param.CustInfo;
import com.newspace.common.utils.SpringBeanUtils;

/**
 * @description 平遥广电支付策略类，实现此策略的具体逻辑
 * @author huqili
 * @date 2017年2月16日
 * @remark 此类是通过反射机制拿到的（相当于new出的对象）， 所以不能使用注解得到其对象。 此类下面的其它对象也不能使用注解拿到
 */
public class PingYaoStrategy extends BaseStrategy implements IPayStrategy {
	
	PingYaoHandler pingYaoHandler = (PingYaoHandler) SpringBeanUtils.getBeanByClass(PingYaoHandler.class);

	@Override
	public PayResponVo pay(Order order, Merchandise merch, List<ResultBody> bodyList, String clientPackageName) 
	{
		logger.info("Enter PingYao Pay...");
		
		Integer returnCode = ReturnCode.SUCCESS.getCode();  //定义默认状态码
		String status = Order.STATUS_WAIT;  //定义默认支付状态
		
		//获取用户的银行卡尾号
		CustInfo custInfo = pingYaoHandler.getAccNo(order.getExtraId(), order.getEndUser());
		
		//以下是在测试环境中用到的代码
//		CustInfo custInfo = new CustInfo();
//		custInfo.setAccNo("9556");
//		custInfo.setStrOrderId("111111111");
//		custInfo.setStrTxnTime("00000000");
		
		returnCode = custInfo.getCode();
		
		//封装响应参数
		bodyList = PayHandler.fillBodyList(bodyList, PayConfig.PAY_MI, PayHandler.getParamMI(Order.PAYTYPE_PINGYAO, custInfo));
		
		return PayHandler.getRespVo(returnCode, status);
	}

}
