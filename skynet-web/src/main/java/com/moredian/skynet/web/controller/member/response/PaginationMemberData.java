package com.moredian.skynet.web.controller.member.response;

import java.util.List;

import com.moredian.skynet.model.PageData;

public class PaginationMemberData extends PageData {
	
	private List<MemberData> members;

	public List<MemberData> getMembers() {
		return members;
	}

	public void setMembers(List<MemberData> members) {
		this.members = members;
	}

	
}
