package com.moredian.skynet.web.controller.deploy.request;

public class CloudeyeDeployModel {
	
	private Long orgId;
	private Long deployId;
	private Integer cloudeyeDeployAction;
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getDeployId() {
		return deployId;
	}
	public void setDeployId(Long deployId) {
		this.deployId = deployId;
	}
	public Integer getCloudeyeDeployAction() {
		return cloudeyeDeployAction;
	}
	public void setCloudeyeDeployAction(Integer cloudeyeDeployAction) {
		this.cloudeyeDeployAction = cloudeyeDeployAction;
	}

}
