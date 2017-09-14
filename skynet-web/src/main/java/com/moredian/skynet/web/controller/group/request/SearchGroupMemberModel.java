package com.moredian.skynet.web.controller.group.request;

import org.apache.commons.lang.StringUtils;

import com.moredian.skynet.web.controller.model.PageModel;

public class SearchGroupMemberModel extends PageModel {
	
	private Long orgId;
	private Long groupId;
	private String keywords;
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		if(StringUtils.isBlank(keywords)) {
			this.keywords = null;
		} else {
			this.keywords = keywords;
		}
	}

}
