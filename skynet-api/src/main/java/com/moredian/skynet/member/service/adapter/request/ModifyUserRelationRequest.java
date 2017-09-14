package com.moredian.skynet.member.service.adapter.request;

import java.io.Serializable;

/**
 * Created by wuyb on 2017/2/8.
 */
public class ModifyUserRelationRequest implements Serializable {

	private static final long serialVersionUID = -2234372899090704719L;
	//主机构id
    private Long orgId;
    //用户id
    private String dingUserId;

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
}
