package com.newspace.aps.payChannel.coin;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.newspace.aps.ReturnCode;
import com.newspace.aps.model.UserAccount;
import com.newspace.aps.model.goods.Goods;
import com.newspace.aps.model.order.Order;
import com.newspace.aps.pay.PayUtils;

@Service
public class ACoinHandler {
	
	private static final Logger logger = Logger.getLogger(ACoinHandler.class);
	
	@Resource
	private PayUtils payUtils;
	
	/**
	 * 用A豆支付 
	 * @return
	 */
	public int pay(Order order)
	{
		int returnCode = ReturnCode.SUCCESS.getCode();
		
		try 
		{
			//判断如果购买的商品是A豆，则跳过此支付方式
			if(!Goods.TYPE_ACOIN.equals(order.getMerchType()))
			{
				//查询此用户在当前系统账户信息
				UserAccount userAccount = payUtils.queryAccount(order.getEndUser(),order.getSystemDomain());

				//判断用户的A豆是否足够
				if(userAccount != null && userAccount.getCoin() >= order.getAmount())
				{
					userAccount.setCoin(userAccount.getCoin() - order.getAmount());
					//修改账户信息
					payUtils.updateAccount(userAccount);
					logger.info(String.format("Order[%s],Payment by Acoin is successful...", order.getOrderNo()));
				}
				else
				{
					logger.error(String.format("Order[%s],The Acoin is not enought...", order.getOrderNo()));
					returnCode = ReturnCode.ACOIN_NOT_ENOUGH.getCode();
				}
			}
			else
			{
				logger.error(String.format("订单[%s]要购买的商品是A豆,禁止使用A豆购买A豆", order.getOrderNo()));
				returnCode = ReturnCode.ERROR.getCode();
			}
		}
		catch (Exception e) 
		{
			logger.error(String.format("使用A豆支付订单[%s]时出现异常:%s", order.getOrderNo(),e));
			returnCode = ReturnCode.ERROR.getCode();
		}
		
	
		return returnCode;
	}
	

}
