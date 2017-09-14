package com.moredian.skynet.web.controller.member.request;

import java.util.List;

public class ConfigDeptModel {
	
	//机构id
	private Long orgId;
	//成员id 
	private Long memberId;
	//部门
	private List<Long> depts;
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public List<Long> getDepts() {
		return depts;
	}
	public void setDepts(List<Long> depts) {
		this.depts = depts;
	}


}
