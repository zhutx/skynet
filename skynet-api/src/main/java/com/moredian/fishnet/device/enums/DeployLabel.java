package com.moredian.fishnet.device.enums;


public enum DeployLabel {
	
	WHITE("白名单",1),
	
	BLACK("黑名单",2);
	
	private String desc;
	
	private int value;

	DeployLabel(String desc ,int value){
		this.value = value;
		this.desc =desc;
	}
	
	public String getDesc() {
		return desc;
	}

	public static String getDesc(int value) {
		for (DeployLabel type : DeployLabel.values()) {
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
