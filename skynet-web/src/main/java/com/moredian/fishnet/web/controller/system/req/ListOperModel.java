package com.moredian.fishnet.web.controller.system.req;

import org.apache.commons.lang.StringUtils;

public class ListOperModel {
	
	private Long orgId;
	private Integer moduleType;
	private String accountName;
	private String operName;
	private String keywords;

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

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		if(StringUtils.isBlank(accountName)){
			this.accountName = null;
		} else {
			this.accountName = accountName;
		}
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		if(StringUtils.isBlank(operName)){
			this.operName = null;
		} else {
			this.operName = operName;
		}
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		if(StringUtils.isBlank(keywords)){
			this.keywords = null;
		} else {
			this.keywords = keywords;
		}
	}

}
