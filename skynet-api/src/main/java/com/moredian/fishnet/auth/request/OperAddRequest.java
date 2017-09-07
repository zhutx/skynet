package com.moredian.fishnet.auth.request;

import java.util.List;

import java.io.Serializable;

public class OperAddRequest implements Serializable{

	private static final long serialVersionUID = 4343819021311464184L;
	
	private Long orgId;
	private Integer moduleType;
	private String accountName;
	private String password;
	private String operName;
	private String operDesc;
	private String email;
	private String mobile;
	private List<Long> roleIds;
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Integer getModuleType() {
		return moduleType;
	}
	public void setModuleType(Integer moduleType) {
		this.moduleType = moduleType;
	}
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOperDesc() {
		return operDesc;
	}
	public void setOperDesc(String operDesc) {
		this.operDesc = operDesc;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<Long> getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	

}
