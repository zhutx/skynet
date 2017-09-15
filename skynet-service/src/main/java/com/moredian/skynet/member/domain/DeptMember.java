package com.moredian.skynet.member.domain;

import java.util.Date;

public class DeptMember {
	
	private Long deptMemberId;
	private Long orgId;
	private Long deptId;
	private String tpDeptId;
	private Long memberId;
	private Integer leaderFlag;
	private Integer status;
	private Date gmtCreate;
	private Date gmtModify;
	
	public Long getDeptMemberId() {
		return deptMemberId;
	}
	public void setDeptMemberId(Long deptMemberId) {
		this.deptMemberId = deptMemberId;
	}
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
	public String getTpDeptId() {
		return tpDeptId;
	}
	public void setTpDeptId(String tpDeptId) {
		this.tpDeptId = tpDeptId;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Integer getLeaderFlag() {
		return leaderFlag;
	}
	public void setLeaderFlag(Integer leaderFlag) {
		this.leaderFlag = leaderFlag;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
