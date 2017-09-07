package com.moredian.fishnet.web.controller.member.response;

import java.util.List;

import com.moredian.fishnet.web.controller.model.PageData;

public class PaginationSimpleMemberData extends PageData {
	
	private List<SimpleMemberData> members;

	public List<SimpleMemberData> getMembers() {
		return members;
	}

	public void setMembers(List<SimpleMemberData> members) {
		this.members = members;
	}

	
}
