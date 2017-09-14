package com.moredian.skynet.web.controller.system.resp;

import java.util.List;

import com.moredian.skynet.auth.model.PermNode;

public class PermNodeData {
	
	private Long permId;
	private String permName;
	private String permUrl;
	private String iconName;
	private Long parentId;
	private Integer status;
	private List<PermNode> children;
	
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public List<PermNode> getChildren() {
		return children;
	}
	public void setChildren(List<PermNode> children) {
		this.children = children;
	}

}
