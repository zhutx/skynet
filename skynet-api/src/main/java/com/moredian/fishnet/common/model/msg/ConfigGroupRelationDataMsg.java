package com.moredian.fishnet.common.model.msg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConfigGroupRelationDataMsg implements Serializable {

	private static final long serialVersionUID = 3733660291554978151L;
	
	private Long orgId;
	private Long groupId;
	private String groupCode;
	private String groupName;
	private Integer systemDefault;
	private Integer allMemberFlag;
	/** 群组关联部门 */
	private List<Long> depts = new ArrayList<>(); //optional
	/** 群组通行成员id  */
	private List<Long> members = new ArrayList<>(); //optional
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
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Integer getSystemDefault() {
		return systemDefault;
	}
	public void setSystemDefault(Integer systemDefault) {
		this.systemDefault = systemDefault;
	}
	public Integer getAllMemberFlag() {
		return allMemberFlag;
	}
	public void setAllMemberFlag(Integer allMemberFlag) {
		this.allMemberFlag = allMemberFlag;
	}
	public List<Long> getDepts() {
		return depts;
	}
	public void setDepts(List<Long> depts) {
		this.depts = depts;
	}
	public List<Long> getMembers() {
		return members;
	}
	public void setMembers(List<Long> members) {
		this.members = members;
	}

}
