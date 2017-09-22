package com.moredian.skynet.device.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moredian.bee.common.exception.BizAssert;
import com.moredian.bee.common.utils.ExceptionUtils;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.idgenerator.service.IdgeneratorService;
import com.moredian.skynet.device.domain.DeviceMatch;
import com.moredian.skynet.device.enums.DeviceErrorCode;
import com.moredian.skynet.device.manager.DeviceMatchManager;
import com.moredian.skynet.device.mapper.DeviceMatchMapper;

@Service
public class DeviceMatchManagerImpl implements DeviceMatchManager {
	
	@Autowired
	private DeviceMatchMapper deviceMatchMapper;
	@SI
	private IdgeneratorService idgeneratorService;

	private Long genPrimaryKey(String name) {
	    return idgeneratorService.getNextIdByTypeName("com.xier.skynet.device.DeviceMatch").getData();	
	}
	
	public DeviceMatch requestToDomain(Long orgId, Long cameraId, Long boxId) {
		DeviceMatch deviceMatch = new DeviceMatch();
		Long deviceMatchId = this.genPrimaryKey(DeviceMatch.class.getName());
		deviceMatch.setDeviceMatchId(deviceMatchId);
		deviceMatch.setOrgId(orgId);
		deviceMatch.setBoxId(cameraId);
		deviceMatch.setCameraId(boxId);
		return deviceMatch;
	}

	@Override
	@Transactional
	public boolean matchDevice(Long orgId, Long cameraId, Long boxId) {
		
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notNull(cameraId, "cameraId must not be null");
		BizAssert.notNull(boxId, "boxId must not be null");
		
		DeviceMatch existOne = deviceMatchMapper.loadByCameraId(cameraId, orgId);
		if(existOne != null) {
			ExceptionUtils.throwException(DeviceErrorCode.DEVICE_MATCH_REPEAT, DeviceErrorCode.DEVICE_MATCH_REPEAT.getMessage());
		}
		
		DeviceMatch deviceMatch = this.requestToDomain(orgId, cameraId, boxId);
		deviceMatchMapper.insert(deviceMatch);

		return true;
	}

	@Override
	public boolean unMatchDevice(Long orgId, Long cameraId) {
		
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notNull(cameraId, "cameraId must not be null");
		
		deviceMatchMapper.deleteByCameraId(orgId, cameraId);
		
		return true;
	}

	@Override
	public DeviceMatch getByCameraId(Long cameraId, Long orgId) {
		return deviceMatchMapper.loadByCameraId(cameraId, orgId);
	}


	@Override
	public DeviceMatch getByBoxId(Long boxId, Long orgId) {
		return deviceMatchMapper.loadByBoxId(boxId,orgId);
	}

	@Override
	public boolean bindCameraWithDevice(Long orgId,Long cameraId, Long deviceId) {
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notNull(deviceId, "boxId must not be null");
		BizAssert.notNull(cameraId, "cameraId must not be null");
		DeviceMatch deviceMatch = new DeviceMatch();
		deviceMatch.setOrgId(orgId);
		deviceMatch.setCameraId(cameraId);
		deviceMatch.setBoxId(deviceId);
		Long id = idgeneratorService.getNextIdByTypeName("com.moredian.skynet.device.DeviceMatch").getData();
		deviceMatch.setDeviceMatchId(id);
		int result = deviceMatchMapper.insert(deviceMatch);

		return result > 0 ? true : false;
	}

	@Override
	public List<Long> findCameraIdByBoxId(Long orgId, Long boxId) {
		return deviceMatchMapper.findCameraIdByBoxId(orgId, boxId);
	}
}
