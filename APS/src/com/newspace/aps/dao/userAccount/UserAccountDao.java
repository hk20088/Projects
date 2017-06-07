package com.newspace.aps.dao.userAccount;

import com.newspace.aps.model.UserAccount;

public interface UserAccountDao {

	UserAccount queryAccount(int userId,int systemDomain);
	
	int updateAccount(UserAccount userAccount);
	
	void insertAccount(UserAccount userAccount);
}
