package com.moredian.fishnet.device.request;

import java.io.Serializable;

/**
 * Created by xxu on 2017/6/21.
 */
public class CameraDeviceBindingRequest implements Serializable {

    private static final long serialVersionUID = 8318720461530978962L;

    // 机构id
    private Long orgId;
    // 摄像头ID
    private Long cameraId;
    // 魔点盒子ID
    private Long deviceId;
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
