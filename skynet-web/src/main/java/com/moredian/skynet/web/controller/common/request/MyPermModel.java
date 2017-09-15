package com.moredian.skynet.web.controller.common.request;

public class MyPermModel {
	
	private Integer moduleType;
	private Long operId;

	public Long getOperId() {
		return operId;
	}

	public void setOperId(Long operId) {
		this.operId = operId;
	}

	public Integer getModuleType() {
		return moduleType;
	}

	public void setModuleType(Integer moduleType) {
		this.moduleType = moduleType;
	}

}
