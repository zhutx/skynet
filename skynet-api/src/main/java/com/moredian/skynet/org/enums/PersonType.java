package com.moredian.skynet.org.enums;

public enum PersonType {
	
	MEMBER("成员",1),
	
	VISITOR("访客",2),
	
	;
	
	private String desc;
	
	private int value;

	PersonType(String desc ,int value){
		this.value = value;
		this.desc =desc;
	}
	
	public String getDesc() {
		return desc;
	}

	public static String getDesc(int value) {
		for (PersonType type : PersonType.values()) {
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
