package com.moredian.fishnet.member.model;

import java.io.Serializable;
import java.util.Date;

import com.moredian.bee.common.utils.ExtendModel;

public class MemberInfo implements Serializable {
	
	private static final long serialVersionUID = -2462859115679402912L;
	
	//机构id
	private Long orgId;
	//成员id 
	private Long memberId;
	//第三方类型
	private Integer tpType;
	//第三方id
	private String tpId;
	//用于显示的人脸图片 
	private String showFaceUrl;
	//用于识别的人脸图片 
	private String verifyFaceUrl;
	//钉钉头像
	private String dingAvatar;
	//成员类型 
	private Integer memberType;
	//负责人标识
	private Integer chargeFlag;
	//管理员标识
	private Integer adminFlag;
	//成员名 
	private String memberName;
	//昵称
	private String nickName;
	//生日
	private Date birthday;
	//默认显示识别头像
	private Integer showVerifyFlag;
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
	private Date enterTime;
	//职务
	private String post;
	//英文字母
	private String engLetter;
	//隐藏标识
	private Integer hideFlag;
	//刷脸授权起始时间 
	private Long authStartTime;
	//刷脸授权截止时间 
	private Long authEndTime;
	//个性签名
	private String signature;
	//备注
	private String memo;
	//扩展信息
	private ExtendModel extend;
	//状态 
	private Integer status;
	//创建时间
	private Date gmtCreate;
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Integer getTpType() {
		return tpType;
	}
	public void setTpType(Integer tpType) {
		this.tpType = tpType;
	}
	public String getTpId() {
		return tpId;
	}
	public void setTpId(String tpId) {
		this.tpId = tpId;
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
	public String getDingAvatar() {
		return dingAvatar;
	}
	public void setDingAvatar(String dingAvatar) {
		this.dingAvatar = dingAvatar;
	}
	public Integer getMemberType() {
		return memberType;
	}
	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
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
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Integer getShowVerifyFlag() {
		return showVerifyFlag;
	}
	public void setShowVerifyFlag(Integer showVerifyFlag) {
		this.showVerifyFlag = showVerifyFlag;
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
	public Date getEnterTime() {
		return enterTime;
	}
	public void setEnterTime(Date enterTime) {
		this.enterTime = enterTime;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getEngLetter() {
		return engLetter;
	}
	public void setEngLetter(String engLetter) {
		this.engLetter = engLetter;
	}
	public Integer getHideFlag() {
		return hideFlag;
	}
	public void setHideFlag(Integer hideFlag) {
		this.hideFlag = hideFlag;
	}
	public Long getAuthStartTime() {
		return authStartTime;
	}
	public void setAuthStartTime(Long authStartTime) {
		this.authStartTime = authStartTime;
	}
	public Long getAuthEndTime() {
		return authEndTime;
	}
	public void setAuthEndTime(Long authEndTime) {
		this.authEndTime = authEndTime;
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
	public ExtendModel getExtend() {
		return extend;
	}
	public void setExtend(ExtendModel extend) {
		this.extend = extend;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	
}
