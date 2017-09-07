package com.moredian.fishnet.device.enums;


public enum DeployStatus {
	
	OVER("已结束",1),
	
	WAIT("未开始",2),
	
	INUSE("进行中", 3),
	
	DELETE("已删除", 4),
	
	ANOMALY("设备异常", 5);
	
	private String desc;
	
	private int value;

	DeployStatus(String desc ,int value){
		this.value = value;
		this.desc =desc;
	}
	
	public String getDesc() {
		return desc;
	}

	public static String getDesc(int value) {
		for (DeployStatus type : DeployStatus.values()) {
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
