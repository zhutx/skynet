package com.moredian.skynet.device.convertor;

import com.moredian.skynet.device.domain.DeviceMatch;
import com.moredian.skynet.device.request.DeviceMatchRequest;

public class DeviceMatchConvertor {
	
	public static DeviceMatch deviceMatchRequestToDeviceMatch(DeviceMatchRequest request) {
		if (request == null) return null;
		DeviceMatch deviceMatch = new DeviceMatch();
		deviceMatch.setOrgId(request.getOrgId());
		deviceMatch.setBoxId(request.getBoxId());
		deviceMatch.setCameraId(request.getCameraId());
		return deviceMatch;
	}
	
}
