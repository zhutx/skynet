package com.moredian.fishnet.web.controller.group.response;

import java.util.List;

import com.moredian.fishnet.web.controller.model.PageData;

public class PaginationGroupMemberData extends PageData {
	
	private List<GroupMemberData> members;

	public List<GroupMemberData> getMembers() {
		return members;
	}

	public void setMembers(List<GroupMemberData> members) {
		this.members = members;
	}
	
}
