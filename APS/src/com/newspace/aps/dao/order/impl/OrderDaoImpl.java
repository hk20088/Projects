package com.newspace.aps.dao.order.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newspace.aps.dao.order.OrderDao;
import com.newspace.aps.mapper.order.OrderMapper;
import com.newspace.aps.model.order.Order;
import com.newspace.common.utils.StringUtils;

@Service
@Transactional
public class OrderDaoImpl implements OrderDao{

	@Resource
	private OrderMapper orderMapper;
	
	/**
	 * 订单新增
	 */
	@Override
	public void insert(Order order) 
	{
		orderMapper.insert(order);
	}

	/**
	 * 根据订单号和订单状态查询订单
	 */
	@Override
	public Order queryByNo(String flag,String orderNo,String query,String state) 
	{
		return orderMapper.queryByNo(flag,orderNo,query,state);
	}

	
	/**
	 * 更新订单
	 */
	@Override
	public void update(Order order) 
	{
		orderMapper.update(order);
	}

	/**
	 * 查询某个用户当天消费总额
	 */
	@Override
	public int queryTotalMoney(int userId) {
		
		String totalMoney = orderMapper.queryTotalMoney(userId);
		
		return  StringUtils.isNullOrEmpty(totalMoney) ? 0 : Integer.valueOf(totalMoney);
	}

}
