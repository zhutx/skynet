package com.moredian.skynet.auth.model;

import java.util.Date;
import java.util.List;

import java.io.Serializable;

public class OperInfo implements Serializable {

	private static final long serialVersionUID = -5174045954476404572L;
	
	private Long operId;
	private Long orgId;
	private Integer moduleType;
	private String accountName;
	private String mobile;
	private String email;
	private String operName;
	private String operDesc;
	private Integer defaultFlag;
	private Integer status;
	private Date gmtCreate;
	private Date gmtModify;
	
	private List<SimpleRoleInfo> roles;
	
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getDefaultFlag() {
		return defaultFlag;
	}
	public void setDefaultFlag(Integer defaultFlag) {
		this.defaultFlag = defaultFlag;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModify() {
		return gmtModify;
	}
	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}
	public List<SimpleRoleInfo> getRoles() {
		return roles;
	}
	public void setRoles(List<SimpleRoleInfo> roles) {
		this.roles = roles;
	}

}
