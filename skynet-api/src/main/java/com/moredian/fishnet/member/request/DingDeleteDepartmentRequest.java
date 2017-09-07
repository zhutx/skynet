package com.moredian.fishnet.member.request;

import java.io.Serializable;


public class DingDeleteDepartmentRequest implements Serializable {
	
	private static final long serialVersionUID = 5102464514772026247L;
	
	private Long orgId;
    private Long dingDepartmentId;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getDingDepartmentId() {
        return dingDepartmentId;
    }

    public void setDingDepartmentId(Long dingDepartmentId) {
        this.dingDepartmentId = dingDepartmentId;
    }
}
