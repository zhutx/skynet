package com.moredian.skynet.device.domain;

import java.io.Serializable;
import java.util.Date;

public class Deploy implements Serializable {

	private static final long serialVersionUID = 4827926981655895399L;
	private Long deployId;
	private Long orgId;
	private Long deviceId;
	private Integer cameraFlag;
	private Integer threshold;
	private Date deployBeginTime;
	private Date deployEndTime;
	private String noticeRoles;
	private Integer status;
	private Date gmtCreate;
	private Date gmtModify;
	
	public Long getDeployId() {
		return deployId;
	}
	public void setDeployId(Long deployId) {
		this.deployId = deployId;
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
	public Integer getCameraFlag() {
		return cameraFlag;
	}
	public void setCameraFlag(Integer cameraFlag) {
		this.cameraFlag = cameraFlag;
	}
	public Integer getThreshold() {
		return threshold;
	}
	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}
	public Date getDeployBeginTime() {
		return deployBeginTime;
	}
	public void setDeployBeginTime(Date deployBeginTime) {
		this.deployBeginTime = deployBeginTime;
	}
	public Date getDeployEndTime() {
		return deployEndTime;
	}
	public void setDeployEndTime(Date deployEndTime) {
		this.deployEndTime = deployEndTime;
	}
	public String getNoticeRoles() {
		return noticeRoles;
	}
	public void setNoticeRoles(String noticeRoles) {
		this.noticeRoles = noticeRoles;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
