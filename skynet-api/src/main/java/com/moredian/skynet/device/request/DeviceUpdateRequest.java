package com.moredian.skynet.device.request;

import java.io.Serializable;

public class DeviceUpdateRequest implements Serializable {
	
	private static final long serialVersionUID = -6193748358046734880L;
	
	/** 设备id */
	private Long deviceId; //required
	/** 机构id */
	private Long orgId; //required
	/** 设备名称 */
	private String deviceName; //optional
	/** 位置 */
	private String position; //optional
	
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
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	

}
