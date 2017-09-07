package com.moredian.fishnet.org.request;

import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;

public class GroupAddRequest implements Serializable {

	private static final long serialVersionUID = -3403460179096467117L;
	
	/** 机构id */
	private Long orgId; //required
	/** 群组名 */
	private String groupName; //required
	/** 是否指定全员 */
	private boolean allMember; //required
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
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public boolean isAllMember() {
		return allMember;
	}
	public void setAllMember(boolean allMember) {
		this.allMember = allMember;
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
