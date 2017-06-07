package com.newspace.aps.dao.chanPayType;

import java.util.List;

import com.newspace.aps.model.ChanPayType;
import com.newspace.aps.model.Device;

public interface ChanPayTypeDao {
	
	List<ChanPayType> queryChanPayInfo(Device device);

}
