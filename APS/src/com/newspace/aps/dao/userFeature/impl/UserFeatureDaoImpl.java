package com.newspace.aps.dao.userFeature.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newspace.aps.dao.userFeature.UserFeatureDao;
import com.newspace.aps.mapper.userFeature.UserFeatureMapper;
import com.newspace.aps.model.UserFeature;

@Service
@Transactional
public class UserFeatureDaoImpl implements UserFeatureDao{

	@Resource
	private UserFeatureMapper userFeatureMapper;

	@Override
	public UserFeature queryMerchInfoSwitch(Integer user, Integer system)
	{
		/*
		 * 设置查询参数
		 */
		UserFeature userFeature = new UserFeature(user, system);		
		return  userFeatureMapper.queryMerchInfoSwitch(userFeature);
	}

	
	
	

}
