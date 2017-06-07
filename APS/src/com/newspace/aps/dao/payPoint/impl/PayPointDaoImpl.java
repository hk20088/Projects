package com.newspace.aps.dao.payPoint.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newspace.aps.dao.payPoint.PayPointDao;
import com.newspace.aps.mapper.payPoint.PayPointMapper;
import com.newspace.aps.model.PayPoint;

@Service
@Transactional
public class PayPointDaoImpl implements PayPointDao{

	@Resource
	private PayPointMapper payPointMapper;
	
	/**
	 * 根据支付点查询支付点信息
	 */
	@Override
	public PayPoint queryPayPoint(int payPoint) 
	{
		return payPointMapper.queryPayPoint(payPoint);
	}

}
