package com.moredian.fishnet.member.request;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

public class GroupMemberQueryRequest implements Serializable {

	private static final long serialVersionUID = 2632990012066410722L;
	
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
		if(StringUtils.isNotBlank(keywords)) {
			this.keywords = keywords.toUpperCase();
		} else {
			this.keywords = null;
		}
	}

}
