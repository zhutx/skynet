package com.moredian.skynet.member.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MemberUpdateRequest implements Serializable {

	private static final long serialVersionUID = 7256247072443623139L;
	
	//成员id 
	private Long memberId;
	//机构id
	private Long orgId;
	//用于显示的人脸图片 
	private String showFaceUrl;
	//用于识别的人脸图片 
	private String verifyFaceUrl;
	//成员名 
	private String memberName;
	//昵称
	private String nickName;
	//生日
	private String birthday;
	//性别 
	private Integer sex;
	//年龄
	private Integer age;
	//手机号 
	private String mobile;
	//邮箱
	private String email;
	//企业邮箱
	private String orgEmail;
	//证件类型
	private Integer certType;
	//证件号 
	private String certNo;
	//工号
	private String jobNum;
	//入职时间
	private String enterTime;
	//职务
	private String post;
	//个性签名
	private String signature;
	//备注
	private String memo;
	//关联部门
	private List<Long> relationDepts = new ArrayList<>();
	
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOrgEmail() {
		return orgEmail;
	}
	public void setOrgEmail(String orgEmail) {
		this.orgEmail = orgEmail;
	}
	public Integer getCertType() {
		return certType;
	}
	public void setCertType(Integer certType) {
		this.certType = certType;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getJobNum() {
		return jobNum;
	}
	public void setJobNum(String jobNum) {
		this.jobNum = jobNum;
	}
	public String getEnterTime() {
		return enterTime;
	}
	public void setEnterTime(String enterTime) {
		this.enterTime = enterTime;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public List<Long> getRelationDepts() {
		return relationDepts;
	}
	public void setRelationDepts(List<Long> relationDepts) {
		this.relationDepts = relationDepts;
	}

}
