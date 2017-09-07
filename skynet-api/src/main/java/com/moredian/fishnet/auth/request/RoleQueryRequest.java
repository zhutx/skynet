package com.moredian.fishnet.auth.request;

import java.io.Serializable;

public class RoleQueryRequest implements Serializable {

	private static final long serialVersionUID = 5450779513475458197L;
	
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
