package com.moredian.skynet.device.manager;

import java.util.List;

import com.moredian.skynet.device.domain.DeviceMatch;

public interface DeviceMatchManager {
	
	boolean matchDevice(Long orgId, Long cameraId, Long boxId);
	
	boolean unMatchDevice(Long orgId, Long cameraId);
	
	DeviceMatch getByCameraId(Long cameraId, Long orgId);
	
	DeviceMatch getByBoxId(Long boxId, Long orgId);

	boolean bindCameraWithDevice(Long orgId,Long cameraId,Long deviceId);

	List<Long> findCameraIdByBoxId(Long orgId, Long boxId);
}
