package com.moredian.skynet.common.model.msg;

import java.io.Serializable;

public class DeleteGroupRelationDataMsg implements Serializable {
	
	private static final long serialVersionUID = -5224293106729667859L;
	
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
