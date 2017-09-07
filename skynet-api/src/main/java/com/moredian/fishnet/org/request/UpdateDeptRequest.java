package com.moredian.fishnet.org.request;

import java.io.Serializable;

public class UpdateDeptRequest implements Serializable {

	private static final long serialVersionUID = -4513441127549835886L;
	
	private Long orgId;
	private Long deptId;
	private String deptName;
	private Long parentId;
	private String tpExtend;
	
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
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getTpExtend() {
		return tpExtend;
	}
	public void setTpExtend(String tpExtend) {
		this.tpExtend = tpExtend;
	}

}
