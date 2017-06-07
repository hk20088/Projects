package com.newspace.aps.dao.payKey;

import com.newspace.aps.model.PayKey;

public interface PayKeyDao {

	PayKey queryKeyInfo(String KeyId);
	
}
