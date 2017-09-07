package com.moredian.fishnet.org.model;

import java.io.Serializable;
import java.util.Date;

public class DeptInfo implements Serializable {

	private static final long serialVersionUID = 1319177841308277837L;
	
	private Long deptId;
	private Long orgId;
	private Integer tpType;
	private Long tpId;
	private String deptName;
	private Long parentId;
	private String tpExtend;
	private Date gmtCreate;
	private Date gmtModify;
	
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
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
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModify() {
		return gmtModify;
	}
	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}
	
}
