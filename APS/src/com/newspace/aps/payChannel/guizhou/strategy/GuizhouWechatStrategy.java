package com.newspace.aps.payChannel.guizhou.strategy;

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
import com.newspace.aps.payChannel.guizhou.GuiZhouHandler;
import com.newspace.aps.payChannel.guizhou.param.TransitParam;

/**
 * @description 贵州微信支付策略类，实现此策略的具体逻辑
 * @author huqili
 * @date 2017年2月16日
 * @remark 此类是通过反射机制拿到的（相当于new出的对象）， 所以不能使用注解得到其对象。 此类下面的其它对象也不能使用注解拿到
 */
public class GuizhouWechatStrategy extends BaseStrategy implements IPayStrategy {
	
	@Override
	public PayResponVo pay(Order order, Merchandise merch, List<ResultBody> bodyList, String clientPackageName) 
	{
		logger.info("Enter Guizhou Wechat...");
		
		int returnCode = ReturnCode.SUCCESS.getCode();  //定义默认状态码
		String status = Order.STATUS_WAIT;  //定义默认支付状态
		
		TransitParam param = GuiZhouHandler.getQRCodeUrl(order.getExtraId(), (order.getAmount() / 100.0), merch, Order.PAYTYPE_GUIZHOU_WECHAT,clientPackageName);
		String payUrl = param.getPayurl();
		
		//以下三行代码是在测试环境用的
//		TransitParam param = new TransitParam();
//		param.setCode(ReturnCode.SUCCESS.getCode());
//		param.setOrderid("guizhouwechat");
//		param.setCitycode("guizhoucitycode..");
//		String payUrl = "http://cn.bing.com/";
		
		if(param.getCode() == ReturnCode.GUIZHOU_ALREADY_BUY.getCode())  //用户已订购该产品，直接返回提示用户
		{
			returnCode = ReturnCode.GUIZHOU_ALREADY_BUY.getCode();
		}
		else if(param.getCode() == ReturnCode.SUCCESS.getCode()) //获取到参数，封装响应参数并记录订单
		{
			//封装响应参数
			bodyList = PayHandler.fillBodyList(bodyList, PayConfig.PAY_SPQ, PayHandler.getParam(payUrl, Order.PAYTYPE_GUIZHOU_WECHAT));
			
			//封装贵阳广电BOSS系统订单号（这里有可能已经被赋了值，比如在处理支付宝通道时已经生成了订单号，这里用$隔开，追加到后面）
			order.setPaymentOrderNo(order.getPaymentOrderNo() == null ? param.getOrderid() : order.getPaymentOrderNo()+"$"+param.getOrderid());  
			order.setExtraPayTypeInfo(param.getCitycode());  //城市代码，BOSS通知ATET平台支付结果，然后再跟BOSS确认订单时会用到
		}
		
		return PayHandler.getRespVo(returnCode, status);
	}

}
