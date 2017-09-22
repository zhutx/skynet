package com.moredian.skynet.web.controller.member.response;

import java.util.List;

import com.moredian.skynet.model.PageData;

public class PaginationSimpleMemberData extends PageData {
	
	private List<SimpleMemberData> members;

	public List<SimpleMemberData> getMembers() {
		return members;
	}

	public void setMembers(List<SimpleMemberData> members) {
		this.members = members;
	}

	
}
