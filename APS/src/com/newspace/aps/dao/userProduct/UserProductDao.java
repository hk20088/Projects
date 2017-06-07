package com.newspace.aps.dao.userProduct;

import java.util.List;

import com.newspace.aps.model.UserProduct;

public interface UserProductDao {
	
	List<UserProduct> queryAsset(UserProduct userProduct);
	
	void insertAsset(UserProduct userProduct);
	
	void updateAsset(UserProduct userProduct);

}
