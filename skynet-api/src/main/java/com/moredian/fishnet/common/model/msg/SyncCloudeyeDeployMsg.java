package com.moredian.fishnet.common.model.msg;

import java.io.Serializable;

/**
 * 云眼布控同步消息模型
 * @author zhutx
 *
 */
public class SyncCloudeyeDeployMsg implements Serializable {

	private static final long serialVersionUID = 8589175332089952953L;
	
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
