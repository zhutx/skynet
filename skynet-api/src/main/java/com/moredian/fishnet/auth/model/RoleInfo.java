package com.moredian.fishnet.auth.model;

import java.util.Date;
import java.util.List;

import java.io.Serializable;

public class RoleInfo implements Serializable {

	private static final long serialVersionUID = -5156981189089654560L;
	
	private Long roleId;
	private Integer orgType;
	private Long orgId;
	private String orgName;
	private String roleName;
	private String roleDesc;
	private Date gmtCreate;
	private List<Long> permIds;
	
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
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public List<Long> getPermIds() {
		return permIds;
	}
	public void setPermIds(List<Long> permIds) {
		this.permIds = permIds;
	}

}
