package com.moredian.skynet.org.request;

import java.util.List;

import java.io.Serializable;

public class GroupDeptBatchRemoveRequest implements Serializable {

	private static final long serialVersionUID = 215060511912725804L;
	
	/** 机构id */
	private Long orgId; //required
	/** 组id */
	private Long groupId; //required
	/** 部门id */
	private List<Long> depts; //required
	
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
	public List<Long> getDepts() {
		return depts;
	}
	public void setDepts(List<Long> depts) {
		this.depts = depts;
	}
	

}
