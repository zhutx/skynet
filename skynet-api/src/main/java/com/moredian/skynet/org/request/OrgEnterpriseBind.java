package com.moredian.skynet.org.request;

import java.io.Serializable;

public class OrgEnterpriseBind implements Serializable {

	private static final long serialVersionUID = -6679097378329192992L;
	
	private Long orgId;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
}
