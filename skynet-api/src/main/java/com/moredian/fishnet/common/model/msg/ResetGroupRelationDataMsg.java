package com.moredian.fishnet.common.model.msg;

import java.io.Serializable;

public class ResetGroupRelationDataMsg implements Serializable {

	private static final long serialVersionUID = 3733660291554978151L;
	
	private Long orgId;
	private Long groupId;
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	
	

}
