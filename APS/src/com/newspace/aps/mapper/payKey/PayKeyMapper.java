package com.newspace.aps.mapper.payKey;

import com.newspace.aps.model.PayKey;

public interface PayKeyMapper {

	/**
	 * 根据KeyId查询密钥信息
	 * @param payKey
	 * @return
	 */
	PayKey queryKeyInfo(String KeyId);
}
