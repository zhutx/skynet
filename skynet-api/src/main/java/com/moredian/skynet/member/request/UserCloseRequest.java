package com.moredian.skynet.member.request;

import java.io.Serializable;

public class UserCloseRequest implements Serializable {
	
	private static final long serialVersionUID = 8013519814424988434L;

	private String dingUserId;

    private Long orgId; //机构id

    public String getDingUserId() {
        return dingUserId;
    }

    public void setDingUserId(String dingUserId) {
        this.dingUserId = dingUserId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
}
