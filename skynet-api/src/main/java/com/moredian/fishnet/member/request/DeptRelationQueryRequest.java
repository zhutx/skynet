package com.moredian.fishnet.member.request;

import java.io.Serializable;

public class DeptRelationQueryRequest implements Serializable {

	private static final long serialVersionUID = 1541536721276892667L;
	
	private Long orgId;
	private Long deptId;
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

}
