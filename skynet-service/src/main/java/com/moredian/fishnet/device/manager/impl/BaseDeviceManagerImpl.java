package com.moredian.fishnet.device.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moredian.fishnet.device.convertor.ObjectUtil;
import com.moredian.fishnet.device.domain.BaseDevice;
import com.moredian.fishnet.device.manager.BaseDeviceManager;
import com.moredian.fishnet.device.mapper.BaseDeviceMapper;

@Component
public class BaseDeviceManagerImpl  implements BaseDeviceManager{
	@Autowired
	private BaseDeviceMapper baseDeviceMapper;
	
	@Override
	public boolean insert(BaseDevice device) {
		return baseDeviceMapper.insert(ObjectUtil.objectToMap(device))>0;
	}

	@Override
	public BaseDevice getByCondition(BaseDevice device) {
		Map<String,Object> model = baseDeviceMapper.getByCondition(ObjectUtil.objectToMap(device));
		return (BaseDevice)ObjectUtil.mapToObject(model, BaseDevice.class);
	}

	@Override
	public boolean updateByCondition(String serialNumber, BaseDevice device) {
		return baseDeviceMapper.updateByCondition(ObjectUtil.objectToMap(serialNumber),ObjectUtil.objectToMap(device))>0;
	}

	@Override
	public boolean deleteByCondition(BaseDevice device) {
		return baseDeviceMapper.deleteByCondition(ObjectUtil.objectToMap(device))>0;
	}

	@Override
	public List<BaseDevice> findByCondition(BaseDevice device) {
		List<Map<String,Object>> list = baseDeviceMapper.findByCondition(ObjectUtil.objectToMap(device));
		List<BaseDevice> devices = new ArrayList<BaseDevice>();
		for(Map<String,Object> model :list){
			BaseDevice dev = (BaseDevice)ObjectUtil.mapToObject(model, BaseDevice.class);
			devices.add(dev);
		}
		return devices;
	}

	@Override
	public int getCountByCondition(BaseDevice device) {
		// TODO Auto-generated method stub
		return 0;
	}
 
}
