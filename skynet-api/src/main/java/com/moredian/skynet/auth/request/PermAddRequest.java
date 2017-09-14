package com.moredian.skynet.auth.request;

import java.io.Serializable;

public class PermAddRequest implements Serializable {

	private static final long serialVersionUID = -2543218381572959872L;
	
	private Integer permType;
	private String permName;
	private Integer permAction;
	private String permUrl;
	private String permDesc;
	private String iconName;
	private Long parentId;
	private Integer moduleType;
	
	public Integer getPermType() {
		return permType;
	}
	public void setPermType(Integer permType) {
		this.permType = permType;
	}
	public String getPermName() {
		return permName;
	}
	public void setPermName(String permName) {
		this.permName = permName;
	}
	public Integer getPermAction() {
		return permAction;
	}
	public void setPermAction(Integer permAction) {
		this.permAction = permAction;
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
	public String getIconName() {
		return iconName;
	}
	public void setIconName(String iconName) {
		this.iconName = iconName;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Integer getModuleType() {
		return moduleType;
	}
	public void setModuleType(Integer moduleType) {
		this.moduleType = moduleType;
	}

}
