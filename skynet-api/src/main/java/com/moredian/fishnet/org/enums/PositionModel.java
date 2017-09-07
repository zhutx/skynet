package com.moredian.fishnet.org.enums;

public enum PositionModel {
	
	TREE("树型",0),
	
	COMMON("平型",1),
	
	;
	
	private String desc;
	
	private int value;

	PositionModel(String desc ,int value){
		this.value = value;
		this.desc =desc;
	}
	
	public String getDesc() {
		return desc;
	}

	public static String getDesc(int value) {
		for (PositionModel type : PositionModel.values()) {
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
