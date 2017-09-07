package com.moredian.fishnet.device.request;

import java.util.Date;
import java.util.List;

import java.io.Serializable;
import com.moredian.fishnet.device.model.DeployGroupInfo;

public class DeployUpdateRequest implements Serializable {
	
	private static final long serialVersionUID = -5261747916332000417L;
	
	private Long deployId;
	private Long orgId;
	private Integer threshold;
	private Date deployBeginTime;
	private Date deployEndTime;
	private String noticeRoles;
	private List<DeployGroupInfo> groups;

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

	public List<DeployGroupInfo> getGroups() {
		return groups;
	}

	public void setGroups(List<DeployGroupInfo> groups) {
		this.groups = groups;
	}


}
