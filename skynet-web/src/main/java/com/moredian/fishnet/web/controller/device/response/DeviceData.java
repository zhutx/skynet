package com.moredian.fishnet.web.controller.device.response;

import java.util.List;

public class DeviceData {
	
	private Long deviceId;
	private Integer deviceType;
	private String deviceName;
	private String deviceSn;
	private String activeCode;
	private Integer status;
	private String position;
	private List<String> groupNames;
	
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public Integer getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
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
	public String getActiveCode() {
		return activeCode;
	}
	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public List<String> getGroupNames() {
		return groupNames;
	}
	public void setGroupNames(List<String> groupNames) {
		this.groupNames = groupNames;
	}
	
}
