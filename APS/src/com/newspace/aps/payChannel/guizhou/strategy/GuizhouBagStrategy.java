package com.newspace.aps.payChannel.guizhou.strategy;

import java.util.List;

import com.newspace.aps.ReturnCode;
import com.newspace.aps.model.Merchandise;
import com.newspace.aps.model.order.Order;
import com.newspace.aps.model.order.PayRespVo.PayRespData.ResultBody;
import com.newspace.aps.pay.PayHandler;
import com.newspace.aps.pay.PayUtils;
import com.newspace.aps.pay.param.PayResponVo;
import com.newspace.aps.pay.strategy.BaseStrategy;
import com.newspace.aps.pay.strategy.IPayStrategy;
import com.newspace.aps.payChannel.guizhou.GuiZhouHandler;
import com.newspace.common.utils.SpringBeanUtils;

/**
 * @description 贵州钱袋支付策略类，实现此策略的具体逻辑
 * @author huqili
 * @date 2017年2月16日
 * @remark 此类是通过反射机制拿到的（相当于new出的对象）， 所以不能使用注解得到其对象。 此类下面的其它对象也不能使用注解拿到
 */
public class GuizhouBagStrategy extends BaseStrategy implements IPayStrategy{
	
	PayUtils payUtils = (PayUtils) SpringBeanUtils.getBeanByClass(PayUtils.class);
	
	@Override
	public PayResponVo pay(Order order, Merchandise merch,List<ResultBody> bodyList, String clientPackageName) 
	{ 

		logger.info("Enter Guizhou Pay_bag...");
		
		int returnCode = ReturnCode.SUCCESS.getCode();  //定义默认状态码
		String status = Order.STATUS_WAIT;  //定义默认支付状态
		
		int result = GuiZhouHandler.pay_bag(order.getExtraId(), (order.getAmount() / 100.0), merch,clientPackageName);
		
		logger.info(String.format("钱袋支付结果为[%s]，描述信息[%s]", result,ReturnCode.getDesc(result)));
		//如果支付成功则跳出循环
		if(result == ReturnCode.SUCCESS.getCode())
		{
			status = Order.STATUS_SUCCESS;
			order.setPayType(Order.PAYTYPE_GUIZHOU_BAG);
			
			//处理用户资产信息，比如给用户加VIP
			payUtils.doAsset(order,merch);
			
		}
		else if(result == ReturnCode.GUIZHOU_ALREADY_BUY.getCode()) //用户已包月，不能重复购买
		{
			returnCode = ReturnCode.GUIZHOU_ALREADY_BUY.getCode();
		}
		
		return PayHandler.getRespVo(returnCode, status);
	}

}
