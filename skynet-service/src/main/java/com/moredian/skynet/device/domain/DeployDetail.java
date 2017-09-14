package com.moredian.skynet.device.domain;

import java.io.Serializable;
import java.util.Date;

public class DeployDetail implements Serializable {
	
	private static final long serialVersionUID = -8275131044997722897L;
	
	private Long deployDetailId;
	private Long deployId;
	private Long orgId;
	private String groupCode;
	private Integer deployLabel;
	private Date gmtCreate;
	private Date gmtModify;
	
	public Long getDeployDetailId() {
		return deployDetailId;
	}
	public void setDeployDetailId(Long deployDetailId) {
		this.deployDetailId = deployDetailId;
	}
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
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public Integer getDeployLabel() {
		return deployLabel;
	}
	public void setDeployLabel(Integer deployLabel) {
		this.deployLabel = deployLabel;
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
