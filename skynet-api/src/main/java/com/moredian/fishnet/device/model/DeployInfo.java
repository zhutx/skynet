package com.moredian.fishnet.device.model;

import java.util.Date;
import java.util.List;

import java.io.Serializable;

public class DeployInfo implements Serializable {

	private static final long serialVersionUID = 4632029921800166025L;
	
	private Long deployId;
	private Long orgId;
	private Long deviceId;
	private String positionName;
	private Integer threshold;
	private Date deployBeginTime;
	private Date deployoEndTime;
	private String noticeRoles;
	private Integer status;
	private Date gmtCreate;
	private Date gmtModify;
	
	private List<DeployGroupInfo> deployLibrarys;

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

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
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

	public Date getDeployoEndTime() {
		return deployoEndTime;
	}

	public void setDeployoEndTime(Date deployoEndTime) {
		this.deployoEndTime = deployoEndTime;
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

	public List<DeployGroupInfo> getDeployLibrarys() {
		return deployLibrarys;
	}

	public void setDeployLibrarys(List<DeployGroupInfo> deployLibrarys) {
		this.deployLibrarys = deployLibrarys;
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
