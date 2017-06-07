package com.newspace.aps.pay.strategy;

import java.util.List;

import com.newspace.aps.model.Merchandise;
import com.newspace.aps.model.order.Order;
import com.newspace.aps.model.order.PayRespVo.PayRespData.ResultBody;
import com.newspace.aps.pay.param.PayResponVo;

/**
 * @description 抽象策略类，定义具体策略类需要调用的接口
 * @author huqili
 * @date 2017年2月16日
 *
 */
public interface IPayStrategy {
	

	public PayResponVo pay(Order order,Merchandise merch,List<ResultBody> bodyList, String clientPackageName);
}
