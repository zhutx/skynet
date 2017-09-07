package com.moredian.fishnet.device.request;

import java.io.Serializable;

public class ServerDeviceQueryRequest implements Serializable {
	
	private static final long serialVersionUID = 8318720461530978962L;
	
	// 机构id
	private Long orgId;
	// 设备名
	private String deviceName;
	// 设备SN
	private String deviceSn;
	// 状态 
	private Integer status;
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceSn() {
		return deviceSn;
	}
	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
