package com.moredian.fishnet.web.controller.system.req;

public class ListRoleModel {
	
	private Long orgId;
	private String roleName;

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

}
