package com.moredian.fishnet.auth.domain;

import java.util.Date;

import java.io.Serializable;

public class RolePerm implements Serializable {

	private static final long serialVersionUID = 7800791519034617471L;
	
	private Long rolePermId;
	private Long roleId;
	private Long permId;
	private Date gmtCreate;
	private Date gmtModify;
	
	public Long getRolePermId() {
		return rolePermId;
	}
	public void setRolePermId(Long rolePermId) {
		this.rolePermId = rolePermId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getPermId() {
		return permId;
	}
	public void setPermId(Long permId) {
		this.permId = permId;
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
