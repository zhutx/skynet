package com.moredian.fishnet.auth.model;

import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;

public class PermNode implements Serializable {

	private static final long serialVersionUID = 7216385835916209437L;
	
	private Long permId;
	private Integer permType;
	private String permName;
	private String permUrl;
	private String iconName;
	private Long parentId;
	private Integer status;
	private List<PermNode> children = new ArrayList<>();
	
	public Long getPermId() {
		return permId;
	}
	public void setPermId(Long permId) {
		this.permId = permId;
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
