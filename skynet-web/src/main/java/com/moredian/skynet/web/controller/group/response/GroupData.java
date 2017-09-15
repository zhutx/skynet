package com.moredian.skynet.web.controller.group.response;

public class GroupData {
	
	private Long groupId;
	private Integer groupType;
	private String groupName;
	private Integer systemDefault;
	private Integer allMemberFlag;
	private Integer blackFlag;
	private Integer personSize;
	
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
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Integer getAllMemberFlag() {
		return allMemberFlag;
	}
	public void setAllMemberFlag(Integer allMemberFlag) {
		this.allMemberFlag = allMemberFlag;
	}
	public Integer getBlackFlag() {
		return blackFlag;
	}
	public void setBlackFlag(Integer blackFlag) {
		this.blackFlag = blackFlag;
	}
	public Integer getSystemDefault() {
		return systemDefault;
	}
	public void setSystemDefault(Integer systemDefault) {
		this.systemDefault = systemDefault;
	}
	public Integer getPersonSize() {
		return personSize;
	}
	public void setPersonSize(Integer personSize) {
		this.personSize = personSize;
	}

}
