package com.moredian.skynet.web.controller.system.req;

import org.apache.commons.lang.StringUtils;

public class ListPermModel {
	
	private Integer moduleType;
	private String permName;
	private Long parentId;
	
	public Integer getModuleType() {
		return moduleType;
	}
	public void setModuleType(Integer moduleType) {
		this.moduleType = moduleType;
	}
	public String getPermName() {
		return permName;
	}
	public void setPermName(String permName) {
		if(StringUtils.isBlank(permName)){
			this.permName = null;
		} else {
			this.permName = permName;
		}
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
