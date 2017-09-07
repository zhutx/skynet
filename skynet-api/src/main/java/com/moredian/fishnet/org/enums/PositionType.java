package com.moredian.fishnet.org.enums;

public enum PositionType {
	
	ROOT("根", 0),
	
	POINT("设备点位",5),
	
	STRUCT_COMMUNITY("社区结构",2),
	
	STRUCT_PARK("园区结构",3),
	
	CAMERA("摄像头点位",4),
	
	;
	
	private String desc;
	
	private int value;

	PositionType(String desc ,int value){
		this.value = value;
		this.desc =desc;
	}
	
	public String getDesc() {
		return desc;
	}

	public static String getDesc(int value) {
		for (PositionType type : PositionType.values()) {
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
