package com.moredian.fishnet.device.response;

import java.io.Serializable;

public class ServerDeviceAddResponse implements Serializable {
	
	private static final long serialVersionUID = 7756165391088052393L;
	
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
	// 设备扩展信息
	private String extendsInfo;
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
	public String getExtendsInfo() {
		return extendsInfo;
	}
	public void setExtendsInfo(String extendsInfo) {
		this.extendsInfo = extendsInfo;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
