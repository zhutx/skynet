package com.moredian.fishnet.auth.enums;

public enum AccountType {
	
	MOBILE("手机",1),
	
	USERNAME("用户名",2), 
	
	EMAIL("邮箱", 3)
	
	;
	
	private String desc;
	
	private int value;

	AccountType(String desc ,int value){
		this.value = value;
		this.desc =desc;
	}
	
	public String getDesc() {
		return desc;
	}

	public static String getDesc(int value) {
		for (AccountType type : AccountType.values()) {
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
