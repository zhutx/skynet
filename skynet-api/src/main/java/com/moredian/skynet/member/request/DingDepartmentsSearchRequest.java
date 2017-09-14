package com.moredian.skynet.member.request;

import java.io.Serializable;

public class DingDepartmentsSearchRequest implements Serializable {

	private static final long serialVersionUID = -6562567642926898549L;
	
	private Long orgId;//机构id

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
}
