package com.moredian.skynet.device.enums;


public enum DeviceWhiteStatus {
	
	USABLE("有效",1),
	
	DISABLE("失效", 0);
	
	private String desc;
	
	private int value;

	DeviceWhiteStatus(String desc ,int value){
		this.value = value;
		this.desc =desc;
	}
	
	public String getDesc() {
		return desc;
	}

	public static String getDesc(int value) {
		for (DeviceWhiteStatus type : DeviceWhiteStatus.values()) {
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
