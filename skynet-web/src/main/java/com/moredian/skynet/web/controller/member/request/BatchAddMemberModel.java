package com.moredian.skynet.web.controller.member.request;

import java.util.List;

public class BatchAddMemberModel {
	
	private List<SimpleMemberModel> memberList;

	public List<SimpleMemberModel> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<SimpleMemberModel> memberList) {
		this.memberList = memberList;
	}
	
	
}
