package com.newspace.aps.mapper.userProduct;

import java.util.List;

import com.newspace.aps.model.UserProduct;

public interface UserProductMapper {

	List<UserProduct> queryAsset(UserProduct userProduct);

	void insertAsset(UserProduct userProduct);
	
	void updateAsset(UserProduct userProduct);
}
