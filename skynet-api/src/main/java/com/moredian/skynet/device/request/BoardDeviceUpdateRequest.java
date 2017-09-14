package com.moredian.skynet.device.request;

import java.io.Serializable;

public class BoardDeviceUpdateRequest implements Serializable {
	
	private static final long serialVersionUID = -6193748358046734880L;
	
	// 设备id
	private Long deviceId;
	// 机构id
	private Long orgId;
	// 设备名
	private String deviceName;
	// ip
	private String ip;
	// 端口
	private Integer port;
	
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
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
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	

}
