package com.newspace.aps.payChannel.wosai.strategy;

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
import com.newspace.aps.payChannel.wosai.WosaiHandler;

/**
 * @description 喔噻微信支付策略类，实现此策略的具体逻辑
 * @author huqili
 * @date 2017年2月16日
 * @remark 此类是通过反射机制拿到的（相当于new出的对象）， 所以不能使用注解得到其对象。 此类下面的其它对象也不能使用注解拿到
 */
public class WosaiWechatStrategy extends BaseStrategy implements IPayStrategy {

	@Override
	public PayResponVo pay(Order order, Merchandise merch, List<ResultBody> bodyList, String clientPackageName) 
	{
		logger.info("Enter wosai Wechat...");
		
		int returnCode = ReturnCode.SUCCESS.getCode();  //定义默认状态码
		String status = Order.STATUS_WAIT;  //定义默认支付状态
		
		//生成支付二维码（需要订单号，商品等信息）
		String QRCode = WosaiHandler.getQRCode(order, Order.PAYTYPE_WOSAI_WECHAT);
		
		//封装响应参数
		bodyList = PayHandler.fillBodyList(bodyList, PayConfig.PAY_SPQ, PayHandler.getParam(QRCode, Order.PAYTYPE_WOSAI_WECHAT));
		
		return PayHandler.getRespVo(returnCode, status);
	}

}
