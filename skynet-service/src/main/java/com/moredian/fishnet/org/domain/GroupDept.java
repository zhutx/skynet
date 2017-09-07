package com.moredian.fishnet.org.domain;

import java.io.Serializable;
import java.util.Date;

public class GroupDept implements Serializable {

	private static final long serialVersionUID = -3342506918168518252L;
	
	private Long groupDeptId;
	private Long orgId;
	private Long groupId;
	private Long deptId;
	private Date gmtCreate;
	private Date gmtModify;
	
	public Long getGroupDeptId() {
		return groupDeptId;
	}
	public void setGroupDeptId(Long groupDeptId) {
		this.groupDeptId = groupDeptId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
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
