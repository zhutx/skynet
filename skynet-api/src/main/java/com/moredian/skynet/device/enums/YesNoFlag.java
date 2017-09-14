package com.moredian.skynet.device.enums;

public enum YesNoFlag {
	
	NO("否",0),
	YES("是",1)
	
	;
	
	private String desc;
	
	private int value;

	YesNoFlag(String desc ,int value){
		this.value = value;
		this.desc =desc;
	}
	
	public String getDesc() {
		return desc;
	}

	public static String getDesc(int value) {
		for (YesNoFlag type : YesNoFlag.values()) {
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
