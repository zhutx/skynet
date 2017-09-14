package com.moredian.skynet.device.manager;

import com.moredian.skynet.device.domain.InventoryDevice;

import java.util.List;

public interface InventoryDeviceManager {
	
	boolean insert(InventoryDevice device);
	
	InventoryDevice getByCondition(InventoryDevice device);
	
	boolean updateByCondition(InventoryDevice device, InventoryDevice condition);
	
	boolean deleteByCondition(InventoryDevice device); 
	
	public List<InventoryDevice> findByCondition(InventoryDevice device);
	
	int getCountByCondition(InventoryDevice device);


}
