package com.moredian.skynet.device.service.adapter.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.device.domain.Device;
import com.moredian.skynet.device.manager.DeviceManager;
import com.moredian.skynet.device.service.adapter.EquipmentService;
import com.moredian.skynet.device.service.adapter.dto.EquipmentDto;

@SI
@Component("adapterEquipmentService")
public class EquipmentServiceImpl implements EquipmentService {
	
	private static final Logger logger = LoggerFactory.getLogger(EquipmentServiceImpl.class);
	
	@Autowired
	private DeviceManager deviceManager;
	
	private EquipmentDto deviceToEquipmentDto(Device device) {
		if(device == null) return null;
		EquipmentDto dto = new EquipmentDto();
		dto.setEquipmentId(device.getDeviceId());
		dto.setOrgId(device.getOrgId());
		dto.setSubOrgId(device.getPositionId());
		dto.setSubOrgCode(device.getPosition());
		dto.setEquipmentName(device.getDeviceName());
		dto.setEquipmentType(device.getDeviceType());
		dto.setUniqueNumber(device.getDeviceSn());
		dto.setGmtCreate(device.getGmtCreate());
		dto.setGmtModify(device.getGmtModify());
		return dto;
	}

	@Override
	public ServiceResponse<EquipmentDto> getEquipmentByUniqueNumber(Long orgId, String uniqueNumber) {
		logger.debug("###################getEquipmentByUniqueNumber###################");
		Device device = deviceManager.getDeviceBySn(uniqueNumber);
		return new ServiceResponse<EquipmentDto>(true, null, deviceToEquipmentDto(device));
	}

}
