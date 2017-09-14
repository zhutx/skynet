package com.moredian.skynet.member.domain;

import java.io.Serializable;

public class DeptRelationQueryCondition implements Serializable {
	
	private static final long serialVersionUID = 5933210213130365233L;
	
	private Long orgId;
	private Long deptId;
	private Integer status;
	private Integer startRow;
	private Integer pageSize;
	
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
	public Integer getStartRow() {
		return startRow;
	}
	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	

}
