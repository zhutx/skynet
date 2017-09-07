package com.moredian.fishnet.member.request;

import java.io.Serializable;

public class BindDeptRelationRequest implements Serializable {

	private static final long serialVersionUID = -18583626642367913L;
	
	private Long orgId;
	private Long deptId;
	private Long tpDeptId;
	private Long memberId;
	private Integer leaderFlag;
	private Integer status;
	
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
	public Long getTpDeptId() {
		return tpDeptId;
	}
	public void setTpDeptId(Long tpDeptId) {
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

}
