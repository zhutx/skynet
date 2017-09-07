package com.moredian.fishnet.member.service.adapter.request;

import java.io.Serializable;
import java.util.Set;

public class JudgeDepartmentLeaderRequest implements Serializable {
	
	private static final long serialVersionUID = 7378071727105348240L;
	private Set<Long> department;
    private Long visitorUserId;
    private Long orgId;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Set<Long> getDepartment() {
        return department;
    }

    public void setDepartment(Set<Long> department) {
        this.department = department;
    }

    public Long getVisitorUserId() {
        return visitorUserId;
    }

    public void setVisitorUserId(Long visitorUserId) {
        this.visitorUserId = visitorUserId;
    }
}
