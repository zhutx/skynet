package com.moredian.fishnet.web.controller.common.response;

public class MyOrgData {
	
	private Long orgId;
	private String orgName;
	private Long operId;
	
	public MyOrgData(Long orgId, String orgName, Long operId) {
		this.orgId = orgId;
		this.orgName = orgName;
		this.operId = operId;
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
	public Long getOperId() {
		return operId;
	}
	public void setOperId(Long operId) {
		this.operId = operId;
	}

}
