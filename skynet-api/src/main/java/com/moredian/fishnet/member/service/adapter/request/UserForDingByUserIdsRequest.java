package com.moredian.fishnet.member.service.adapter.request;

import java.io.Serializable;
import java.util.Set;

public class UserForDingByUserIdsRequest implements Serializable {
	
	private static final long serialVersionUID = -7884271093638589158L;
	private Set<Long> userIds;
    private Long orgId;

    public Set<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<Long> userIds) {
        this.userIds = userIds;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
}
