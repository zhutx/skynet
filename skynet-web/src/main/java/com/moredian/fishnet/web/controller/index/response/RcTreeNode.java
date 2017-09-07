package com.moredian.fishnet.web.controller.index.response;

import java.util.ArrayList;
import java.util.List;

/**
 * 前端RC-TREE组件专用模型
 * @author Administrator
 *
 */
public class RcTreeNode {
	
	private String key;
	private String pId;
	private String label;
	private String value;
	private List<RcTreeNode> children = new ArrayList<>();
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getPId() {
		return pId;
	}
	public void setPId(String pId) {
		this.pId = pId;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<RcTreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<RcTreeNode> children) {
		this.children = children;
	}

}
