package com.newspace.aps.pay.strategy;

import java.util.List;

import com.newspace.aps.model.Merchandise;
import com.newspace.aps.model.order.Order;
import com.newspace.aps.model.order.PayRespVo.PayRespData.ResultBody;
import com.newspace.aps.pay.param.PayResponVo;

/**
 * @description 策略模式环境类，用来确定调用者调用哪个具体策略类
 * @author huqili
 * @date 2017年2月16日
 *
 */
public class Context {

	private IPayStrategy strategy;  //定义接口类

	//有参构造，接收传入的对象，来确定需要调用哪个支付渠道的支付方法
	public Context(IPayStrategy strategy)
	{
		this.strategy = strategy;
	}
	
	//具体策略类的实现方法 
	public PayResponVo pay(Order order,Merchandise merch,List<ResultBody> bodyList, String clientPackageName)
	{
		return this.strategy.pay(order, merch, bodyList, clientPackageName);
	}
	
	public IPayStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(IPayStrategy strategy) {
		this.strategy = strategy;
	}
	
	
}
