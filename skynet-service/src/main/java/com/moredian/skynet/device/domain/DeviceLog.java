package com.moredian.skynet.device.domain;

import java.util.Date;

import java.io.Serializable;

public class DeviceLog implements Serializable {

	private static final long serialVersionUID = -7916908204232994077L;
	
	private Long deviceLogId;
	private Long orgId;
	private Integer deviceAction;
	private Long deviceId;
	private Integer deviceType;
	private String deviceSn;
	private Date gmtCreate;
	private Date gmtModify;
	
	public Long getDeviceLogId() {
		return deviceLogId;
	}
	public void setDeviceLogId(Long deviceLogId) {
		this.deviceLogId = deviceLogId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Integer getDeviceAction() {
		return deviceAction;
	}
	public void setDeviceAction(Integer deviceAction) {
		this.deviceAction = deviceAction;
	}
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
	public String getDeviceSn() {
		return deviceSn;
	}
	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModify() {
		return gmtModify;
	}
	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

}
