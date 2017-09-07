package com.moredian.fishnet.auth.request;

import java.util.List;

import java.io.Serializable;

public class OperUpdateRequest implements Serializable{

	private static final long serialVersionUID = 4343819021311464184L;
	
	private Integer moduleType;
	private Long operId;
	private Long orgId;
	private String operName;
	private String email;
	private String mobile;
	private String operDesc;
	private List<Long> roleIds;
	
	public Integer getModuleType() {
		return moduleType;
	}
	public void setModuleType(Integer moduleType) {
		this.moduleType = moduleType;
	}
	public Long getOperId() {
		return operId;
	}
	public void setOperId(Long operId) {
		this.operId = operId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getOperDesc() {
		return operDesc;
	}
	public void setOperDesc(String operDesc) {
		this.operDesc = operDesc;
	}
	public List<Long> getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}
	

}
