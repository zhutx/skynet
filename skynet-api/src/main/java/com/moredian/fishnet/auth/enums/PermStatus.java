package com.moredian.fishnet.auth.enums;

public enum PermStatus {
	
	DISABLE("停用",0),
	
	USABLE("可用",1)
	
	;
	
	private String desc;
	
	private int value;

	PermStatus(String desc ,int value){
		this.value = value;
		this.desc =desc;
	}
	
	public String getDesc() {
		return desc;
	}

	public static String getDesc(int value) {
		for (PermStatus type : PermStatus.values()) {
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
