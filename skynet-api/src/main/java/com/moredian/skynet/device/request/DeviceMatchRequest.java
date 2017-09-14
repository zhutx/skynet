package com.moredian.skynet.device.request;

import java.io.Serializable;

public class DeviceMatchRequest implements Serializable {

	private static final long serialVersionUID = 1892922264283156767L;
	
	private Long orgId;
	private Long boxId;
	private Long cameraId;
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getBoxId() {
		return boxId;
	}
	public void setBoxId(Long boxId) {
		this.boxId = boxId;
	}
	public Long getCameraId() {
		return cameraId;
	}
	public void setCameraId(Long cameraId) {
		this.cameraId = cameraId;
	}

}
