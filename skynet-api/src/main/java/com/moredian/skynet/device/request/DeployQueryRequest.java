package com.moredian.skynet.device.request;

import java.io.Serializable;

public class DeployQueryRequest implements Serializable {

	private static final long serialVersionUID = 8687969398232592775L;
	
	private Long orgId;
	private Long deviceId;
	private Integer status;

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
