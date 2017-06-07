package com.newspace.aps.dao.device;

import com.newspace.aps.model.Device;

public interface DeviceDao {

	Device queryDeviceInfo(int deviceId);
}
