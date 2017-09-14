package com.moredian.skynet.device.enums;

public enum DeviceProviderType {
	
	HIKVISION("海康",9),
	
	DAHUA("大华",10),
	
	;
	
	private String desc;
	
	private int value;

	DeviceProviderType(String desc ,int value){
		this.value = value;
		this.desc =desc;
	}
	
	public String getDesc() {
		return desc;
	}

	public static String getDesc(int value) {
		for (DeviceProviderType type : DeviceProviderType.values()) {
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
