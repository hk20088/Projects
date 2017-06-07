package com.newspace.aps.dao.userProduct.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newspace.aps.dao.userProduct.UserProductDao;
import com.newspace.aps.mapper.userProduct.UserProductMapper;
import com.newspace.aps.model.UserProduct;

@Service
@Transactional
public class UserProductDaoImpl implements UserProductDao{

	@Resource
	private UserProductMapper userProductMapper;	
	
	/**
	 * 根据用户ID查询用户资产信息
	 */
	@Override
	public List<UserProduct> queryAsset(UserProduct userProduct) 
	{
		return userProductMapper.queryAsset(userProduct);
	}

	/**
	 * 根据用户ID修改用户资产信息
	 * 返回的是修改影响的条数
	 */
	@Override
	public void updateAsset(UserProduct userProduct)
	{
		userProductMapper.updateAsset(userProduct);
	}

	/**
	 * 添加用户资产信息
	 */
	@Override
	public void insertAsset(UserProduct userProduct) 
	{
		userProductMapper.insertAsset(userProduct);
	}

}
