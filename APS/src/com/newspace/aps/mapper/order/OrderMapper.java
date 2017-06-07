package com.newspace.aps.mapper.order;

import org.apache.ibatis.annotations.Param;

import com.newspace.aps.model.order.Order;

public interface OrderMapper {
	
	void insert(Order order); 
	
	//使用注解的方法传递参数
	Order queryByNo(@Param(value="flag") String flag, @Param(value="orderNo") String orderNo, @Param(value="query") String query,@Param(value="state") String state);
	
	void update(Order order);
	
	String queryTotalMoney(int userId);

}
