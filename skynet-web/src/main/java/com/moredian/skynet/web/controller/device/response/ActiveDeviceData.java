package com.moredian.skynet.web.controller.device.response;

public class ActiveDeviceData {
	
	private String secretKey;
	private Long orgId;
	
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

}
