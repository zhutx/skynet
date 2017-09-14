package com.moredian.skynet.common.model.msg;

import java.io.Serializable;

public class RefreshDeviceConfigMsg implements Serializable {

	private static final long serialVersionUID = 6601051310490378112L;
	
	private Long orgId;
	private String deviceSn;
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getDeviceSn() {
		return deviceSn;
	}
	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}

}
