package com.moredian.fishnet.auth.model;

import java.io.Serializable;

public class AccountInfo implements Serializable{
	
	private static final long serialVersionUID = 5359597374352552142L;
	
	private Long accountId;
	private Integer accountType;
	private String accountName;
	private String password;
	private Integer status;
	
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public Integer getAccountType() {
		return accountType;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

}
