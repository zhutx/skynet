package com.moredian.skynet.org.enums;

public enum GroupType {
	
	ALLMEMBER("全员组",0),
	
	VISITOR("访客组",3),
	
	CUSTOM("自定义组",6),
	
	;
	
	private String desc;
	
	private int value;

	GroupType(String desc ,int value){
		this.value = value;
		this.desc =desc;
	}
	
	public String getDesc() {
		return desc;
	}

	public static String getDesc(int value) {
		for (GroupType type : GroupType.values()) {
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
