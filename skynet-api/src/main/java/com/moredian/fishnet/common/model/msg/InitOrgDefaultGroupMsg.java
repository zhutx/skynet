package com.moredian.fishnet.common.model.msg;

import java.io.Serializable;

public class InitOrgDefaultGroupMsg implements Serializable {
	
	private static final long serialVersionUID = 1617973152233407614L;
	
	private Long orgId;

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

}
