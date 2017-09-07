package com.moredian.fishnet.web.controller.common.response;

public class LoginOperInfo {
	
	private Long accountId;
	private String accountName;
	private Long operId;
	private String operName;
	private String mobile;
	private String email;
	private Integer orgType;
	private Long orgId;
	private String orgName;
	private Integer orgLevel;


	private Integer tpType;

	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public Long getOperId() {
		return operId;
	}
	public void setOperId(Long operId) {
		this.operId = operId;
	}
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
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
	public Integer getOrgLevel() {
		return orgLevel;
	}
	public void setOrgLevel(Integer orgLevel) {
		this.orgLevel = orgLevel;
	}

	public Integer getTpType() {
		return tpType;
	}

	public void setTpType(Integer tpType) {
		this.tpType = tpType;
	}
	

}
