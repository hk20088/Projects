package com.newspace.aps.payChannel.coin;

import java.util.List;

import com.newspace.aps.ReturnCode;
import com.newspace.aps.model.Merchandise;
import com.newspace.aps.model.goods.Goods;
import com.newspace.aps.model.order.Order;
import com.newspace.aps.model.order.PayRespVo.PayRespData.ResultBody;
import com.newspace.aps.pay.PayConfig;
import com.newspace.aps.pay.PayHandler;
import com.newspace.aps.pay.PayUtils;
import com.newspace.aps.pay.param.PayResponVo;
import com.newspace.aps.pay.strategy.BaseStrategy;
import com.newspace.aps.pay.strategy.IPayStrategy;
import com.newspace.common.utils.SpringBeanUtils;

/**
 * @description A豆支付策略类，实现此策略的具体逻辑
 * @author huqili
 * @date 2017年2月16日
 * @remark 此类是通过反射机制拿到的（相当于new出的对象）， 所以不能使用注解得到其对象。 此类下面的其它对象也不能使用注解拿到
 */
public class ACoinStrategy extends BaseStrategy implements IPayStrategy {

	PayUtils payUtils = (PayUtils) SpringBeanUtils.getBeanByClass(PayUtils.class);
	
	ACoinHandler AcoinHandler = (ACoinHandler) SpringBeanUtils.getBeanByClass(ACoinHandler.class);
	
	@Override
	public PayResponVo pay(Order order, Merchandise merch, List<ResultBody> bodyList, String clientPackageName)
	{

		logger.info("Enter ACoin...");
		
		int returnCode = ReturnCode.SUCCESS.getCode();  //定义默认状态码
		String status = Order.STATUS_WAIT;  //定义默认支付状态
		
		//判断是否可以直接支付。如果bodylist里存有其它支付方式，说明A豆优先级较低，需要将此支付方式返回给客户端
		if(bodyList == null || bodyList.isEmpty())
		{
			int result = AcoinHandler.pay(order);
			
			/**
			 * 问题：当使用A豆支付失败时，没有改变returnCode的值，如果一个渠道只配了A豆一种支付方式， 这里会出现A豆支付失败，但返回状态为0的情况 。
			 * 但是这里如果改变了returnCode的值，则如果配了其它支付方式，比如微信扫码（并且优先级比A豆低），那A豆支付失败时，最终响应给终端的returnCode非0，会影响终端判断使用微信
			 * 解决方案：可以在每个具体策略类里面声明returnCode的值，这样有多种支付方式时，会以最后一个为准
			 * 备注：目前考虑基本不会出现一个渠道只配A豆一种支付方式的情况，所以暂不处理   - 2017年2月17日   胡起立  
			 * 目前已在每个具体策略类里声明returnCode的值，上述问题已不存在 — 2017年3月3日  胡起立
			 */
			if(result == ReturnCode.SUCCESS.getCode())
			{
				status = Order.STATUS_SUCCESS;
				order.setPayType(Order.PAYTYPE_COIN);
				
				//异步通知商户
				payUtils.notify(order, order.getNotifyURL());
				
				//处理用户资产信息，比如给用户加VIP
				payUtils.doAsset(order,merch);
				
			}
			else
			{
				returnCode = result;
			}
		}
		else
		{
			//如果购买的商品是A豆，则不返回此支付方式
			if(!Goods.TYPE_ACOIN.equals(order.getMerchType()))
			{
				//封装响应参数
				bodyList = PayHandler.fillBodyList(bodyList, PayConfig.PAY_ATET, PayHandler.getParamATET(Order.PAYTYPE_COIN));
			}
		}

		return PayHandler.getRespVo(returnCode, status);
	}

}
