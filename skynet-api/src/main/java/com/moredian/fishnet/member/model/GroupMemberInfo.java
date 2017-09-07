package com.moredian.fishnet.member.model;

import java.io.Serializable;

public class GroupMemberInfo implements Serializable {
	
	private static final long serialVersionUID = 1745732053435407941L;
	
	//成员id 
	private Long memberId;
	//用于显示的人脸图片 
	private String showFaceUrl;
	//成员名 
	private String memberName;
	//昵称
	private String nickName;
	//英文首字母
	private String engLetter;
	
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getShowFaceUrl() {
		return showFaceUrl;
	}
	public void setShowFaceUrl(String showFaceUrl) {
		this.showFaceUrl = showFaceUrl;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getEngLetter() {
		return engLetter;
	}
	public void setEngLetter(String engLetter) {
		this.engLetter = engLetter;
	}
	
	

}
