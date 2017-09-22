package com.moredian.skynet.device.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moredian.skynet.device.manager.DeviceConfigManager;
import com.moredian.skynet.device.mapper.DeviceConfigMapper;

@Service
public class DeviceConfigManagerImpl implements DeviceConfigManager {
	
	@Autowired
	private DeviceConfigMapper deviceConfigMapper;

	@Override
	public boolean delete(String deviceSn) {
		deviceConfigMapper.deleteBySn(deviceSn);
		return true;
	}

}
