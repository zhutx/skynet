package com.moredian.fishnet.web.controller.system.resp;

import java.util.Date;

public class RoleData {
	
	private Long roleId;
	private Long orgId;
	private String roleName;
	private String roleDesc;
	private Date gmtCreate;
	private String permIds;
	
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public String getPermIds() {
		return permIds;
	}
	public void setPermIds(String permIds) {
		this.permIds = permIds;
	}

}
