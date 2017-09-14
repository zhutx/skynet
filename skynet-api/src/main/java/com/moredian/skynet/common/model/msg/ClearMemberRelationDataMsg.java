package com.moredian.skynet.common.model.msg;

import java.io.Serializable;

public class ClearMemberRelationDataMsg implements Serializable {

	private static final long serialVersionUID = -2430230486185373780L;
	
	private Long orgId;
	private Long memberId;
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

}
