package com.moredian.skynet.member.enums;

public enum Sex {
	
	MALE("男",0),
	FEMALE("女",1),
	UNKNOW("未知", -1),
	
	;
	
	private String desc;
	
	private int value;

	Sex(String desc ,int value){
		this.value = value;
		this.desc =desc;
	}
	
	public String getDesc() {
		return desc;
	}

	public static String getDesc(int value) {
		for (Sex type : Sex.values()) {
			if (type.getValue() == value) {
				return type.desc;
			}
		}
		return null;
	}
	
	public static Integer getValue(String desc) {
		for (Sex type : Sex.values()) {
			if (type.getDesc().equals(desc)) {
				return type.getValue();
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
