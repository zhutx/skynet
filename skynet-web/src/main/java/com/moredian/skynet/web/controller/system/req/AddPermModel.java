package com.moredian.skynet.web.controller.system.req;

public class AddPermModel {
	
	private Integer moduleType;
	private Integer permType;
	private String permName;
	private Integer permAction;
	private String permUrl;
	private String permDesc;
	private Long parentId;
	
	public Integer getModuleType() {
		return moduleType;
	}
	public void setModuleType(Integer moduleType) {
		this.moduleType = moduleType;
	}
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
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
