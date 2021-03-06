package com.moredian.skynet.web.controller.system.resp;

import java.util.List;

public class RoleData {
	
	private Long roleId;
	private Long orgId;
	private String roleName;
	private String roleDesc;
	private List<Long> permIds;
	
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
	public List<Long> getPermIds() {
		return permIds;
	}
	public void setPermIds(List<Long> permIds) {
		this.permIds = permIds;
	}

}
