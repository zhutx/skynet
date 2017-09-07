package com.moredian.fishnet.auth.request;

import java.io.Serializable;

public class SimpleRoleQueryRequest implements Serializable {

	private static final long serialVersionUID = -1621739501676948719L;
	
	private Long orgId;
	private Long operId;
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getOperId() {
		return operId;
	}
	public void setOperId(Long operId) {
		this.operId = operId;
	}

}
