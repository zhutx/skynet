package com.moredian.fishnet.member.service.adapter.request;

import java.io.Serializable;

/**
 * Created by wuyb on 2017/1/17.
 */
public class UserSearchForVisitorRequest implements Serializable {

	private static final long serialVersionUID = 5971842792864707366L;
	
	private Long orgId;
    private Long userId;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
