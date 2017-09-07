package com.moredian.fishnet.device.manager;

import com.moredian.fishnet.device.domain.BaseDevice;

import java.util.List;

public interface BaseDeviceManager {
	
	boolean insert(BaseDevice device);
	
	BaseDevice getByCondition(BaseDevice device);
	
	boolean updateByCondition(String serialNumber, BaseDevice device);
	
	boolean deleteByCondition(BaseDevice device); 
	
	public List<BaseDevice> findByCondition(BaseDevice device);
	
	int getCountByCondition(BaseDevice device);
}
