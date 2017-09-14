package com.moredian.skynet.device.service.adapter.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.device.domain.Device;
import com.moredian.skynet.device.manager.DeviceManager;
import com.moredian.skynet.device.service.adapter.DEquipmentService;
import com.moredian.skynet.device.service.adapter.dto.DEquipmentDto;

@SI
public class DEquipmentServiceImpl implements DEquipmentService {
	
	private static final Logger logger = LoggerFactory.getLogger(DEquipmentServiceImpl.class);
	
	@Autowired
	private DeviceManager deviceManager;

	@Override
	public ServiceResponse<DEquipmentDto> getDequipmentByUniqueNumber(String uniqueNumber) {
		
		logger.debug("################DEquipmentService.getDequipmentByUniqueNumber################");
		
		Device device = deviceManager.getDeviceBySn(uniqueNumber);
		if(device == null) {
			return new ServiceResponse<DEquipmentDto>(true, null, null);
		}
		
		DEquipmentDto dto = new DEquipmentDto();
		dto.setDEquipmentId(device.getDeviceId());
		dto.setdEquipmentId(device.getDeviceId());
		dto.setEquipmentName(device.getDeviceName());
		//dEquipment.setCpuId();
		//dEquipment.setIpAddress();
		//dEquipment.setEquipmentToken();
		dto.setEquipmentType(device.getDeviceType());
		dto.setGmtCreate(device.getGmtCreate());
		dto.setGmtModify(device.getGmtModify());
		//dto.setMacAddress(device.getMacAddress());
		dto.setOrgId(device.getOrgId());
		dto.setSubOrgId(device.getPositionId());
		dto.setUniqueNumber(device.getDeviceSn());
		return new ServiceResponse<DEquipmentDto>(true, null, dto);
	}

}
