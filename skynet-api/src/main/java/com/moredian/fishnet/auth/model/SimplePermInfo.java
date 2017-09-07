package com.moredian.fishnet.auth.model;

import java.io.Serializable;

public class SimplePermInfo implements Serializable {

	private static final long serialVersionUID = 5311019447059853869L;
	
	private Long permId;
	private String permName;
	private boolean hasChild;
	private boolean checked;
	
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
	public boolean isHasChild() {
		return hasChild;
	}
	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}
