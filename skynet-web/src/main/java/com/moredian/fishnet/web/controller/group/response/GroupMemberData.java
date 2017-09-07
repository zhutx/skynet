package com.moredian.fishnet.web.controller.group.response;

public class GroupMemberData {
	
	//成员id 
	private Long memberId;
	//用于显示的人脸图片 
	private String showFaceUrl;
	//负责人标识
	private Integer chargeFlag;
	//管理员标识
	private Integer adminFlag;
	//成员名 
	private String memberName;
	//昵称
	private String nickName;
	//性别 
	private Integer sex;
	//年龄
	private Integer age;
	//手机号 
	private String mobile;
	//职务
	private String post;
	
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
	public Integer getChargeFlag() {
		return chargeFlag;
	}
	public void setChargeFlag(Integer chargeFlag) {
		this.chargeFlag = chargeFlag;
	}
	public Integer getAdminFlag() {
		return adminFlag;
	}
	public void setAdminFlag(Integer adminFlag) {
		this.adminFlag = adminFlag;
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
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	
}
