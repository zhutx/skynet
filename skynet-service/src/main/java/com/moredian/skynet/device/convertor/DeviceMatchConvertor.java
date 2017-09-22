package com.moredian.skynet.device.convertor;

import com.moredian.skynet.device.domain.DeviceMatch;

public class DeviceMatchConvertor {
	
	public static DeviceMatch deviceMatchRequestToDeviceMatch(Long orgId, Long cameraId, Long boxId) {
		DeviceMatch deviceMatch = new DeviceMatch();
		deviceMatch.setOrgId(orgId);
		deviceMatch.setBoxId(cameraId);
		deviceMatch.setCameraId(boxId);
		return deviceMatch;
	}
	
}
