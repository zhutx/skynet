package com.moredian.skynet.web.controller.system.req;

import com.moredian.bee.common.utils.StringUtil;

public class EditPermModel {
	
	private Long permId;
	private String permName;
	private String permUrl;
	private String permDesc;
	
	public Long getPermId() {
		return permId;
	}
	public void setPermId(Long permId) {
		this.permId = permId;
	}
	public String getPermName() {
		return permName;
	}
	public void setPermName(String permName) {
		if(StringUtil.isBlank(permName)) this.permName = null;
		this.permName = permName;
	}
	public String getPermUrl() {
		return permUrl;
	}
	public void setPermUrl(String permUrl) {
		this.permUrl = permUrl;
	}
	public String getPermDesc() {
		return permDesc;
	}
	public void setPermDesc(String permDesc) {
		if(StringUtil.isBlank(permDesc)) this.permDesc = null;
		this.permDesc = permDesc;
	}

}
