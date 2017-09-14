package com.moredian.skynet.device.manager.impl;

import com.moredian.skynet.device.convertor.ObjectUtil;
import com.moredian.skynet.device.domain.InventoryDevice;
import com.moredian.skynet.device.manager.InventoryDeviceManager;
import com.moredian.skynet.device.mapper.InventoryDeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class InventoryDeviceManagerImpl  implements InventoryDeviceManager{
	@Autowired
	private InventoryDeviceMapper inventoryDeviceMapper;
	
	@Override
	public boolean insert(InventoryDevice device) {
		return inventoryDeviceMapper.insert(ObjectUtil.objectToMap(device))>0;
	}

	@Override
	public InventoryDevice getByCondition(InventoryDevice device) {
		Map<String,Object> model = inventoryDeviceMapper.getByCondition(ObjectUtil.objectToMap(device));
		return (InventoryDevice)ObjectUtil.mapToObject(model, InventoryDevice.class);
	}

	@Override
	public boolean updateByCondition(InventoryDevice device, InventoryDevice condition) {
		return inventoryDeviceMapper.updateByCondition(ObjectUtil.objectToMap(device),ObjectUtil.objectToMap(condition))>0;
	}

	@Override
	public boolean deleteByCondition(InventoryDevice device) {
		return inventoryDeviceMapper.deleteByCondition(ObjectUtil.objectToMap(device))>0;
	}

	@Override
	public List<InventoryDevice> findByCondition(InventoryDevice device) {
		List<Map<String,Object>> list = inventoryDeviceMapper.findByCondition(ObjectUtil.objectToMap(device));
		List<InventoryDevice> devices = new ArrayList<InventoryDevice>();
		for(Map<String,Object> model :list){
			InventoryDevice dev = (InventoryDevice)ObjectUtil.mapToObject(model, InventoryDevice.class);
			devices.add(dev);
		}
		return devices;
	}

	@Override
	public int getCountByCondition(InventoryDevice device) {
		// TODO Auto-generated method stub
		return 0;
	}



}
