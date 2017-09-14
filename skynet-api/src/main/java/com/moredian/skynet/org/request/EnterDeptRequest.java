package com.moredian.skynet.org.request;

import java.io.Serializable;

public class EnterDeptRequest implements Serializable {

	private static final long serialVersionUID = 4428003231085941477L;
	
	private Long orgId;
	private Integer tpType;
	private Long tpId;
	private String deptName;
	private Long parentId;
	private String tpExtend = "{}";
	
	public Integer getTpType() {
		return tpType;
	}
	public void setTpType(Integer tpType) {
		this.tpType = tpType;
	}
	public Long getTpId() {
		return tpId;
	}
	public void setTpId(Long tpId) {
		this.tpId = tpId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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
