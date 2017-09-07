package com.moredian.fishnet.member.service.adapter.request;

import java.io.Serializable;

public class DingDepartmentSearchRequest implements Serializable {

	private static final long serialVersionUID = 3731708918854643898L;
	private Long parentDingDepartmentId;//部门id
    private Long dingDepartmentId;
    private Long orgId;//机构id
    private Long userId;

    public Long getDingDepartmentId() {
        return dingDepartmentId;
    }

    public void setDingDepartmentId(Long dingDepartmentId) {
        this.dingDepartmentId = dingDepartmentId;
    }

    public Long getParentDingDepartmentId() {
        return parentDingDepartmentId;
    }

    public void setParentDingDepartmentId(Long parentDingDepartmentId) {
        this.parentDingDepartmentId = parentDingDepartmentId;
    }

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
