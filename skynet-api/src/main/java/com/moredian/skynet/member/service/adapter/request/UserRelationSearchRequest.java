package com.moredian.skynet.member.service.adapter.request;

import java.io.Serializable;

/**
 * Created by wu yb on 2017/5/9.
 */
public class UserRelationSearchRequest implements Serializable {

	private static final long serialVersionUID = 2044788159200363732L;
	
	private Long orgId;
    private Long subOrgId;
    private int relation;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getSubOrgId() {
        return subOrgId;
    }

    public void setSubOrgId(Long subOrgId) {
        this.subOrgId = subOrgId;
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }
}
