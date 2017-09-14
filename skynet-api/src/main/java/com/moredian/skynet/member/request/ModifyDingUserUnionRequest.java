package com.moredian.skynet.member.request;

import java.io.Serializable;

public class ModifyDingUserUnionRequest implements Serializable {
	
	private static final long serialVersionUID = 2801153059306190817L;
	
	private Long orgId;
    private String dingUserId;
    private String unionId;

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

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }
}
