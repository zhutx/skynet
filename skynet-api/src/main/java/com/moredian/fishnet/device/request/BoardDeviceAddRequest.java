package com.moredian.fishnet.device.request;

import java.io.Serializable;

public class BoardDeviceAddRequest implements Serializable {

	private static final long serialVersionUID = 6943127224543140740L;
	
	private Long orgId;
	private Long positionId;
	private Integer deviceType;
	private String deviceName;
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getPositionId() {
		return positionId;
	}
	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public Integer getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

}
