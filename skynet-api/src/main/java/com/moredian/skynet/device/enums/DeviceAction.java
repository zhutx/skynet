package com.moredian.skynet.device.enums;

public enum DeviceAction {
	
	DELETE("删除",0),
	
	ACTIVE("激活",1),
	
	CREATE("新增",2),
	
	UPDATE_GROUP("修改绑定群组",3),
	
	STOP("停止",4),
	
	;
	
	private String desc;
	
	private int value;

	DeviceAction(String desc ,int value){
		this.value = value;
		this.desc =desc;
	}
	
	public String getDesc() {
		return desc;
	}

	public static String getDesc(int value) {
		for (DeviceAction type : DeviceAction.values()) {
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
