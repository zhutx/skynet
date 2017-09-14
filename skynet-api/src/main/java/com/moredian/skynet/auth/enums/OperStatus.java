package com.moredian.skynet.auth.enums;

public enum OperStatus {
	
	DISABLE("停用",0),
	
	USABLE("可用",1),
	
	UNCHECK("未验证",2)
	
	;
	
	private String desc;
	
	private int value;

	OperStatus(String desc ,int value){
		this.value = value;
		this.desc =desc;
	}
	
	public String getDesc() {
		return desc;
	}

	public static String getDesc(int value) {
		for (OperStatus type : OperStatus.values()) {
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
