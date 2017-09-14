package com.moredian.skynet.auth.request;

import java.io.Serializable;

public class PermQueryRequest implements Serializable {

	private static final long serialVersionUID = -9049770333598252209L;
	
	private String permName;
	private Integer moduleType;
	private Long parentId;
	
	public String getPermName() {
		return permName;
	}
	public void setPermName(String permName) {
		this.permName = permName;
	}
	public Integer getModuleType() {
		return moduleType;
	}
	public void setModuleType(Integer moduleType) {
		this.moduleType = moduleType;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
