package com.moredian.skynet.web.controller.system.resp;

import java.util.List;

public class RoleDetailData {
	
	private Long roleId;
	private Integer orgType;
	private Long orgId;
	private String orgName;
	private String roleName;
	private String roleDesc;
	private List<Long> permIdList;
	
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Integer getOrgType() {
		return orgType;
	}
	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
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
	public List<Long> getPermIdList() {
		return permIdList;
	}
	public void setPermIdList(List<Long> permIdList) {
		this.permIdList = permIdList;
	}

}
