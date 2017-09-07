package com.moredian.fishnet.web.controller.group.response;

import java.util.ArrayList;
import java.util.List;

public class RelationDeptAndMemberData {
	
	private List<Long> deptIds = new ArrayList<>();
	private List<Long> memberIds = new ArrayList<>();
	
	public List<Long> getDeptIds() {
		return deptIds;
	}
	public void setDeptIds(List<Long> deptIds) {
		this.deptIds = deptIds;
	}
	public List<Long> getMemberIds() {
		return memberIds;
	}
	public void setMemberIds(List<Long> memberIds) {
		this.memberIds = memberIds;
	}

}
