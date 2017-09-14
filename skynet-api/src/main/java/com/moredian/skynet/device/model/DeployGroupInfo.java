package com.moredian.skynet.device.model;

import java.io.Serializable;

public class DeployGroupInfo implements Serializable {

	private static final long serialVersionUID = 5388143976797714256L;
	
	private String groupCode;
	private Integer deployLabel;
	
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public Integer getDeployLabel() {
		return deployLabel;
	}
	public void setDeployLabel(Integer deployLabel) {
		this.deployLabel = deployLabel;
	}

}
