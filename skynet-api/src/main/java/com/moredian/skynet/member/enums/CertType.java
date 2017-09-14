package com.moredian.skynet.member.enums;

public enum CertType {
	
	IDENTITY("身份证",1),
	OFFICERS("军官证",2),
	PASSPORT("护照", 3),
	
	;
	
	private String desc;
	
	private int value;

	CertType(String desc ,int value){
		this.value = value;
		this.desc =desc;
	}
	
	public String getDesc() {
		return desc;
	}

	public static String getDesc(int value) {
		for (CertType type : CertType.values()) {
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
