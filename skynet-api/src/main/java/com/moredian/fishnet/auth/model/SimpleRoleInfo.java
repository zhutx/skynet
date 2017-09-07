package com.moredian.fishnet.auth.model;

import java.io.Serializable;

public class SimpleRoleInfo implements Serializable {
	
	private static final long serialVersionUID = -8933465931451428611L;
	
	private Long roleId;
	private String roleName;
	private boolean checked = true;
	
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
