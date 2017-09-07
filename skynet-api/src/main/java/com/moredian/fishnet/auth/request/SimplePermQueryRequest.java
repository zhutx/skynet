package com.moredian.fishnet.auth.request;

import java.io.Serializable;

public class SimplePermQueryRequest implements Serializable {

	private static final long serialVersionUID = -5171277789726299951L;
	
	private Long roleId;
	private Long parentPermId;
	private Integer moduleType;
	
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getParentPermId() {
		return parentPermId;
	}
	public void setParentPermId(Long parentPermId) {
		this.parentPermId = parentPermId;
	}
	public Integer getModuleType() {
		return moduleType;
	}
	public void setModuleType(Integer moduleType) {
		this.moduleType = moduleType;
	}

}
