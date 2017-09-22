package com.moredian.skynet.web.controller.device.request;

public class UnbindBoxModel {
	
	private Long orgId;
	private Long cameraId;
	
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

}
