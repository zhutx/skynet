package com.moredian.skynet.auth.request;

import java.io.Serializable;

public class PermUpdateRequest implements Serializable {

	private static final long serialVersionUID = -2543218381572959872L;
	
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
		this.permDesc = permDesc;
	}
	
}
