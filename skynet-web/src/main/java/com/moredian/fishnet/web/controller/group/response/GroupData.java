package com.moredian.fishnet.web.controller.group.response;

public class GroupData {
	
	private Long groupId;
	private Integer groupType;
	private String groupCode;
	private String groupName;
	private Integer systemDefault;
	private Integer allMemberFlag;
	private Integer memberSize;
	
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public Integer getGroupType() {
		return groupType;
	}
	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
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
	public Integer getMemberSize() {
		return memberSize;
	}
	public void setMemberSize(Integer memberSize) {
		this.memberSize = memberSize;
	}

}
