package com.moredian.skynet.device.response;

import java.io.Serializable;

public class BoardDeviceAddResponse implements Serializable {

	private static final long serialVersionUID = -4139388831733403451L;
	
	// 设备id
	private Long deviceId;
	// 机构id
	private Long orgId;
	// 位置id
	private Long positionId;
	// 设备类型
	private Integer deviceType;
	// 设备名
	private String deviceName;
	// 状态
	private Integer status;
	
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
	public Long getPositionId() {
		return positionId;
	}
	public void setPositionId(Long positionId) {
		this.positionId = positionId;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	

}
