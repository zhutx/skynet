package com.moredian.fishnet.common.model.msg;

import java.io.Serializable;

public class ConfigMemberGroupDataMsg implements Serializable {
	
	private static final long serialVersionUID = -3837001257326132834L;
	
	private Long orgId;
	private Long memberId;
	private String memberName;
	private Long[] departments;
	private Integer status;
	
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
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public Long[] getDepartments() {
		return departments;
	}
	public void setDepartments(Long[] departments) {
		this.departments = departments;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

}
