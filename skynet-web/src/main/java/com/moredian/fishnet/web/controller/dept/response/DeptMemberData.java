package com.moredian.fishnet.web.controller.dept.response;

public class DeptMemberData {
	
	//成员id 
	private Long memberId;
	//成员名 
	private String memberName;
	//显示头像 
	private String showFaceUrl;
	//识别照片
	private String verifyFaceUrl;
	//手机
	private String mobile;
	//昵称
	private String nickName;
	//职位
	private String post;
	//工号
	private String jobNumber;
	//负责人标识
	private Integer chargeFlag;
	//管理员标识
	private Integer adminFlag;
	
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
	public String getVerifyFaceUrl() {
		return verifyFaceUrl;
	}
	public void setVerifyFaceUrl(String verifyFaceUrl) {
		this.verifyFaceUrl = verifyFaceUrl;
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
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getJobNumber() {
		return jobNumber;
	}
	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

}
