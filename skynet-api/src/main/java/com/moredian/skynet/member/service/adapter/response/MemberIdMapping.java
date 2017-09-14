package com.moredian.skynet.member.service.adapter.response;

import java.io.Serializable;

public class MemberIdMapping implements Serializable {
	
	private static final long serialVersionUID = 8804191542707857433L;
	
	private Long memberId;
	private String dingUserId;
	
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getDingUserId() {
		return dingUserId;
	}
	public void setDingUserId(String dingUserId) {
		this.dingUserId = dingUserId;
	}
	

}
