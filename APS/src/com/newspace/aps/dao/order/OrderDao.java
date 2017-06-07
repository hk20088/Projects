package com.newspace.aps.dao.order;

import com.newspace.aps.model.order.Order;

public interface OrderDao {

	void insert(Order order); 
	
	Order queryByNo(String flag,String orderNo,String query,String state);
	
	void update(Order order);
	
	int queryTotalMoney(int userId);
}
