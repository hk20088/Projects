package com.newspace.aps.dao.payKey.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newspace.aps.dao.payKey.PayKeyDao;
import com.newspace.aps.mapper.payKey.PayKeyMapper;
import com.newspace.aps.model.PayKey;

@Service
@Transactional
public class PayKeyDaoImpl implements PayKeyDao {

//	private static final Logger logger = Logger.getLogger(PayKeyDaoImpl.class);
	
	@Resource
	private PayKeyMapper payKeyMapper;

	/**
	 * 根据KeyId查询密钥信息
	 */
	@Override
	public PayKey queryKeyInfo(String keyId) {
		return payKeyMapper.queryKeyInfo(keyId);
	}


}
