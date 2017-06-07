package com.newspace.aps.dao.operLog.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newspace.aps.dao.operLog.OperLogDao;
import com.newspace.aps.mapper.operLog.OperLogMapper;
import com.newspace.aps.model.OperLog;

@Service
@Transactional
public class OperLogDaoImpl implements OperLogDao{

	@Resource
	private OperLogMapper operLogMapper;
	
	@Override
	public void insert(OperLog operLog) 
	{
		operLogMapper.insert(operLog);
	}

}
