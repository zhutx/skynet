package com.moredian.fishnet.web.controller.member.request;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SimpleUpdateMemberModel {
	
	//机构id
	private Long orgId;
	//成员id 
	private Long memberId;
	//用于显示的人脸图片 
	private String showFaceUrl;
	//用于识别的人脸图片 
	private String verifyFaceUrl;
	//成员名 
	private String memberName;
	//昵称
	private String nickName;
	//生日
	private Date birthday;
	//性别 
	private Integer sex;
	//年龄
	private Integer age;
	//手机号 
	private String mobile;
	//邮箱
	private String email;
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
	//个性签名
	private String signature;
	//备注
	private String memo;
	//部门
	private List<Long> relationDepts;

	

}
