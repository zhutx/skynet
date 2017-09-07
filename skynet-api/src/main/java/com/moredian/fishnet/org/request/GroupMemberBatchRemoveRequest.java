package com.moredian.fishnet.org.request;

import java.util.List;

import java.io.Serializable;

public class GroupMemberBatchRemoveRequest implements Serializable {

	private static final long serialVersionUID = 215060511912725804L;
	
	/** 机构id */
	private Long orgId; //required
	/** 组id */
	private Long groupId; //required
	/** 成员id */
	private List<Long> members; //required
	
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
	public List<Long> getMembers() {
		return members;
	}
	public void setMembers(List<Long> members) {
		this.members = members;
	}
	

}
