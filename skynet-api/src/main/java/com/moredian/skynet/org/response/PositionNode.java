package com.moredian.skynet.org.response;

import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;

public class PositionNode implements Serializable {

	private static final long serialVersionUID = 5104781927453896414L;
	
	private Long positionId;
	private String positionCode;
	private String positionName;
	private Long parentId;
	private String fullName;
	private List<PositionNode> children = new ArrayList<>();
	
	public Long getPositionId() {
		return positionId;
	}
	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}
	public String getPositionCode() {
		return positionCode;
	}
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public List<PositionNode> getChildren() {
		return children;
	}
	public void setChildren(List<PositionNode> children) {
		this.children = children;
	}
	
	
	

}
