package com.moredian.fishnet.member.request;

import java.io.Serializable;

public class ModifyDingUserIsAdminRequest implements Serializable {

	private static final long serialVersionUID = 893017839033002523L;
	
	private Long orgId;
    private String dingUserId;
    private Boolean dingIsAdmin;

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

    public Boolean getDingIsAdmin() {
        return dingIsAdmin;
    }

    public void setDingIsAdmin(Boolean dingIsAdmin) {
        this.dingIsAdmin = dingIsAdmin;
    }
}
