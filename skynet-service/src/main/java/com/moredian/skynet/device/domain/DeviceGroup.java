package com.moredian.skynet.device.domain;

import java.util.Date;

import java.io.Serializable;

public class DeviceGroup implements Serializable {

	private static final long serialVersionUID = -3685698756016470461L;
	
	private Long deviceGroupId;
	private Long orgId;
	private Long deviceId;
	private Long groupId;
	private Date gmtCreate;
	private Date gmtModify;
	
	public Long getDeviceGroupId() {
		return deviceGroupId;
	}
	public void setDeviceGroupId(Long deviceGroupId) {
		this.deviceGroupId = deviceGroupId;
	}
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
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
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
