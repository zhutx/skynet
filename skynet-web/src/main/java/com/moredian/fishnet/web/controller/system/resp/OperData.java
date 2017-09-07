package com.moredian.fishnet.web.controller.system.resp;

import java.util.Date;
import java.util.List;

public class OperData {
	
	private Long operId;
	private String operName;
	private String operDesc;
	private String accountName;
	private String mobile;
	private Integer status;
	private Date gmtCreate;
	private List<SimpleRoleData> roles;
	
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
	public List<SimpleRoleData> getRoles() {
		return roles;
	}
	public void setRoles(List<SimpleRoleData> roles) {
		this.roles = roles;
	}

}
