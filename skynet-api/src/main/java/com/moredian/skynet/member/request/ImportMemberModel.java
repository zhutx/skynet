package com.moredian.skynet.member.request;

import java.io.Serializable;

public class ImportMemberModel implements Serializable {
	
	private static final long serialVersionUID = -5897185691687390335L;
	
	private String memberName;
	private String mobile;
	private String nickName;
	private Integer sex;
	private String birthday;
	private String email;
	private Long deptId;
	private String post;
	
	public ImportMemberModel(String memberName, String mobile, String nickName, Integer sex, String birthday, String email, Long deptId, String post) {
		this.memberName = memberName;
		this.mobile = mobile;
		this.nickName = nickName;
		this.sex = sex;
		this.birthday = birthday;
		this.email = email;
		this.deptId = deptId;
		this.post = post;
	}
	
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
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
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}

}
