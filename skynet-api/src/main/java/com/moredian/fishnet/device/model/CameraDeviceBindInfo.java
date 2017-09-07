package com.moredian.fishnet.device.model;

import java.io.Serializable;

/**
 * Created by xxu on 2017/6/20.
 */
public class CameraDeviceBindInfo implements Serializable {

    private Long deviceMatchId;

    private Long orgId;

    private Long cameraId;

    private Long deviceId;

	public Long getDeviceMatchId() {
		return deviceMatchId;
	}

	public void setDeviceMatchId(Long deviceMatchId) {
		this.deviceMatchId = deviceMatchId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getCameraId() {
		return cameraId;
	}

	public void setCameraId(Long cameraId) {
		this.cameraId = cameraId;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
}
