package com.newspace.aps.dao.device.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newspace.aps.dao.device.DeviceDao;
import com.newspace.aps.mapper.device.DeviceMapper;
import com.newspace.aps.model.Device;

@Service
@Transactional
public class DeviceDaoImpl implements DeviceDao{

	@Resource
	private DeviceMapper deviceMapper;
	
	
	/**
	 * 根据设备Id查询此设备所属渠道，所属平台台
	 */
	@Override
	public Device queryDeviceInfo(int deviceId) 
	{
		Device device = deviceMapper.queryDeviceInfo(deviceId);
		if(device == null)
		{
			//TODO 默认将此设备归到ATET渠道
		}
		return device;
	}

}
