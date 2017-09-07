package com.moredian.fishnet.device.manager;

import com.moredian.fishnet.device.domain.DeviceMatch;
import com.moredian.fishnet.device.request.BoxUpdateRequest;
import com.moredian.fishnet.device.request.DeviceMatchRequest;

public interface DeviceMatchManager {
	
	boolean matchDevice(DeviceMatchRequest request);
	
	boolean disMatchDevice(DeviceMatchRequest request);
	
	DeviceMatch getByCameraId(Long cameraId, Long orgId);
	
	DeviceMatch getByBoxId(Long boxId, Long orgId);

	boolean bindCameraWithDevice(Long orgId,Long cameraId,Long deviceId);

	boolean updateBoxCamera(BoxUpdateRequest boxUpdateRequest);
}
