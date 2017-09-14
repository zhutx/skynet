package com.moredian.skynet.auth.domain;

import java.util.Date;

import java.io.Serializable;

public class OperRole implements Serializable {

	private static final long serialVersionUID = -5349360434959966545L;
	
	private Long operRoleId;
	private Long operId;
	private Long roleId;
	private Date gmtCreate;
	private Date gmtModify;
	
	public Long getOperRoleId() {
		return operRoleId;
	}
	public void setOperRoleId(Long operRoleId) {
		this.operRoleId = operRoleId;
	}
	public Long getOperId() {
		return operId;
	}
	public void setOperId(Long operId) {
		this.operId = operId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
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
