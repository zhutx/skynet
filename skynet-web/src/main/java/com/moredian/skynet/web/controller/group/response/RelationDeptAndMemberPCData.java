package com.moredian.skynet.web.controller.group.response;

import java.util.ArrayList;
import java.util.List;

public class RelationDeptAndMemberPCData {
	
	private List<SimpleDeptData> depts = new ArrayList<>();
	private List<SimpleMemberData> members = new ArrayList<>();
	
	public List<SimpleDeptData> getDepts() {
		return depts;
	}
	public void setDepts(List<SimpleDeptData> depts) {
		this.depts = depts;
	}
	public List<SimpleMemberData> getMembers() {
		return members;
	}
	public void setMembers(List<SimpleMemberData> members) {
		this.members = members;
	}
	
}
