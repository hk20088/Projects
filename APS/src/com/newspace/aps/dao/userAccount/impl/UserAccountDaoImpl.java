package com.newspace.aps.dao.userAccount.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newspace.aps.dao.userAccount.UserAccountDao;
import com.newspace.aps.mapper.userAccount.UserAccountMapper;
import com.newspace.aps.model.UserAccount;

@Service
@Transactional
public class UserAccountDaoImpl implements UserAccountDao{

	@Resource
	private UserAccountMapper userAccountMapper;
	
	/**
	 * 根据用户ID查询用户账户信息
	 */
	@Override
	public UserAccount queryAccount(int userId, int systemDomain) 
	{
		return userAccountMapper.queryAccount(userId,systemDomain);
	}

	/**
	 * 修改用户账户信息
	 */
	@Override
	public int updateAccount(UserAccount userAccount) 
	{
		return userAccountMapper.updateAccount(userAccount);
	}

	/**
	 * 新增用户信息
	 */
	@Override
	public void insertAccount(UserAccount userAccount) {
		userAccountMapper.insertAccount(userAccount);
	}
	
	
	

}
