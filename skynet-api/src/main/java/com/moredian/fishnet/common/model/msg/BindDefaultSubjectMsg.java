package com.moredian.fishnet.common.model.msg;

import java.io.Serializable;

public class BindDefaultSubjectMsg implements Serializable {
	
	private static final long serialVersionUID = -699019969743718891L;
	
	private Long orgId;
	private Long deviceId;
	
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

}
