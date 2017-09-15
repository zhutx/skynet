package com.moredian.skynet.auth.request;

import java.io.Serializable;

public class OperQueryRequest implements Serializable {

	private static final long serialVersionUID = 3010903131886624666L;
	
	private Long orgId;
	private Integer moduleType;
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
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

}
