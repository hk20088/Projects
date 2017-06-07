package com.newspace.aps.mapper.chanPayType;

import java.util.List;

import com.newspace.aps.model.ChanPayType;
import com.newspace.aps.model.Device;

public interface ChanPayTypeMapper {
	
	List<ChanPayType> queryChanPayInfo(Device device);

}
