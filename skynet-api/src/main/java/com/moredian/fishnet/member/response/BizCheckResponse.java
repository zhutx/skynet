package com.moredian.fishnet.member.response;

import java.io.Serializable;

public class BizCheckResponse implements Serializable {

	private static final long serialVersionUID = 7995844982292276699L;
	
	private Long orgId;
    private String dingUserId;
    private boolean hold;
    
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getDingUserId() {
		return dingUserId;
	}
	public void setDingUserId(String dingUserId) {
		this.dingUserId = dingUserId;
	}
	public boolean isHold() {
		return hold;
	}
	public void setHold(boolean hold) {
		this.hold = hold;
	}
    
}
