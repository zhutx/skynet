package com.moredian.skynet.web.controller.system.req;

import java.util.ArrayList;
import java.util.List;

public class CreateRoleModel {
	
	private Long orgId;
	private String roleName;
	private String roleDesc;
	private List<Long> permIds = new ArrayList<>();
	
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
