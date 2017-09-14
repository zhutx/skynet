package com.moredian.skynet.member.service.adapter.request;

import java.io.Serializable;

/**
 * Created by wuyb on 2017/2/8.
 */
public class SelectUserRelationRequest implements Serializable {

	private static final long serialVersionUID = 8092947863082613075L;
	
	//主机构id
    private Long orgId;
    //用户id
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
