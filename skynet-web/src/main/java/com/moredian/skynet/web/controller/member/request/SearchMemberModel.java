package com.moredian.skynet.web.controller.member.request;

import org.apache.commons.lang.StringUtils;

import com.moredian.skynet.web.controller.model.PageModel;

public class SearchMemberModel extends PageModel {
	
	private Long orgId;
	private String keywords;
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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
