package com.newspace.aps.mapper.userAccount;

import org.apache.ibatis.annotations.Param;

import com.newspace.aps.model.UserAccount;

public interface UserAccountMapper {

	UserAccount queryAccount(@Param(value = "userId") int userId, @Param(value = "systemDomain") int systemDomain);
	
	int updateAccount(UserAccount userAccount);
	
	void insertAccount(UserAccount userAccount);
}
