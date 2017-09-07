package com.moredian.fishnet.web.controller.member.request;

import java.util.List;

import lombok.Data;

@Data
public class ConfigGroupsModel {
	
	//机构id
	private Long orgId;
	//成员id 
	private Long memberId;
	//群组
	private List<Long> groupIds;

}
