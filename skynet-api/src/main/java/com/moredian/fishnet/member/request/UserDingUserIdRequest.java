package com.moredian.fishnet.member.request;

import java.io.Serializable;

public class UserDingUserIdRequest implements Serializable {

	private static final long serialVersionUID = -1848129806226719650L;
	
	private Long orgId;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
}
