package com.moredian.fishnet.auth.model;

import java.io.Serializable;

public class PermZNode implements Serializable {

	private static final long serialVersionUID = 7216385835916209437L;
	
	private String id;	//权限id
	private String pId;	//父权限id
	private String name; //权限名
	private String remark; //权限备注
	private boolean open;	//是否展开
	private boolean checked; //是否选中
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	

}
