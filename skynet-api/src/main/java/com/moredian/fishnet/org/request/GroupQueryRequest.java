package com.moredian.fishnet.org.request;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class GroupQueryRequest implements Serializable {

	private static final long serialVersionUID = 3048171873387892715L;
	
	private Long orgId;
	private String groupName;
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		if(StringUtils.isBlank(groupName)) {
			this.groupName = null;
		} else {
			this.groupName = groupName;
		}
	}

}
