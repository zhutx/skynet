package com.moredian.skynet.device.enums;

public enum DeviceStatus {
	
	UNACTIVE("未激活",0),
	
	USABLE("可激活",1),
	
	;
	
	private String desc;
	
	private int value;

	DeviceStatus(String desc ,int value){
		this.value = value;
		this.desc =desc;
	}
	
	public String getDesc() {
		return desc;
	}

	public static String getDesc(int value) {
		for (DeviceStatus type : DeviceStatus.values()) {
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
