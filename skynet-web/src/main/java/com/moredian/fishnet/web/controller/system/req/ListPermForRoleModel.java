package com.moredian.fishnet.web.controller.system.req;

public class ListPermForRoleModel {
	
	private Integer moduleType;
	private Long roleId;
	private Long parentPermId;
	
	public Integer getModuleType() {
		return moduleType;
	}
	public void setModuleType(Integer moduleType) {
		this.moduleType = moduleType;
	}
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

}
