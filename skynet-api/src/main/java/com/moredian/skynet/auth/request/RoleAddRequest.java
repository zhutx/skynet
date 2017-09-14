package com.moredian.skynet.auth.request;

import java.util.List;

import java.io.Serializable;

public class RoleAddRequest implements Serializable{

	private static final long serialVersionUID = 4343819021311464184L;
	
	private Long orgId;
	private String roleName;
	private String roleDesc;
	private List<Long> permIds;
	
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
	public List<Long> getPermIds() {
		return permIds;
	}
	public void setPermIds(List<Long> permIds) {
		this.permIds = permIds;
	}

}
