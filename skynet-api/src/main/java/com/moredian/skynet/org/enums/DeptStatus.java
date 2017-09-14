package com.moredian.skynet.org.enums;

public enum DeptStatus {
	
	DELETE("已删除",0),
	
	ENABLE("可用",1),
	
	;
	
	private String desc;
	
	private int value;

	DeptStatus(String desc ,int value){
		this.value = value;
		this.desc =desc;
	}
	
	public String getDesc() {
		return desc;
	}

	public static String getDesc(int value) {
		for (DeptStatus type : DeptStatus.values()) {
			if (type.getValue() == value) {
				return type.desc;
			}
		}
		return null;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
