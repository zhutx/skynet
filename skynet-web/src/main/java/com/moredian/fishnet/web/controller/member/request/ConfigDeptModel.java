package com.moredian.fishnet.web.controller.member.request;

import java.util.List;

import lombok.Data;

@Data
public class ConfigDeptModel {
	
	//机构id
	private Long orgId;
	//成员id 
	private Long memberId;
	//部门
	private List<Long> relationDepts;


}
