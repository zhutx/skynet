package com.moredian.fishnet.device.convertor;

import com.moredian.fishnet.device.domain.DeviceMatch;
import com.moredian.fishnet.device.request.DeviceMatchRequest;

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
