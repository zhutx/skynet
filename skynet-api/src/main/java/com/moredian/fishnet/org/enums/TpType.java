package com.moredian.fishnet.org.enums;

public enum TpType {
	
	SELF("自有平台",0),
	
	DING("钉钉",1),
	
	;
	
	private String desc;
	
	private int value;

	TpType(String desc ,int value){
		this.value = value;
		this.desc =desc;
	}
	
	public String getDesc() {
		return desc;
	}

	public static String getDesc(int value) {
		for (TpType type : TpType.values()) {
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
