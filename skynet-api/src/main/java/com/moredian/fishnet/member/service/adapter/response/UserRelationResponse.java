package com.moredian.fishnet.member.service.adapter.response;

import java.io.Serializable;

/**
 * Created by wu yb on 2017/5/9.
 */
public class UserRelationResponse implements Serializable {

	private static final long serialVersionUID = 6329512765238281333L;
	
	private Long userRelationId; //主键id
    private Long userId; //机构用户id
    private Integer relation; //关系
    private Integer relationStatus;
    private Long orgId; //机构id
    private Long subOrgId; //子机构Id

    public Long getUserRelationId() {
        return userRelationId;
    }

    public void setUserRelationId(Long userRelationId) {
        this.userRelationId = userRelationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getRelation() {
        return relation;
    }

    public void setRelation(Integer relation) {
        this.relation = relation;
    }

    public Integer getRelationStatus() {
        return relationStatus;
    }

    public void setRelationStatus(Integer relationStatus) {
        this.relationStatus = relationStatus;
    }

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
}
