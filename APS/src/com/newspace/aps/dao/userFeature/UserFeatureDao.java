package com.newspace.aps.dao.userFeature;

import com.newspace.aps.model.UserFeature;

public interface UserFeatureDao {
	/**
	 * 
	* @Title: queryMerchInfoSwitch 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param user
	* @param system
	* @return UserFeature
	* @author tangpan
	* @date 2016年12月5日 上午9:19:25
	 */
	UserFeature queryMerchInfoSwitch(Integer user,Integer system);

}
