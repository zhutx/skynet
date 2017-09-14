package com.moredian.skynet.org.request;

import java.io.Serializable;

public class GroupDeptAddRequest implements Serializable {

	private static final long serialVersionUID = 215060511912725804L;
	
	/** 机构id */
	private Long orgId; //required
	/** 群组id */
	private Long groupId; //required
	/** 部门id */
	private Long deptId; //required
	
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

}
