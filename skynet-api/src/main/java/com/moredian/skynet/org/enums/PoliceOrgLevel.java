package com.moredian.skynet.org.enums;

public enum PoliceOrgLevel {
	
	TOP("公安部",1),
	
	PROV("省厅",2),
	
	CITY("市局", 3),
	
	DISTRICT("分局",4),
	
	TOWN("街道", 5),
	
	;
	
	private String desc;
	
	private int value;

	PoliceOrgLevel(String desc ,int value){
		this.value = value;
		this.desc =desc;
	}
	
	public String getDesc() {
		return desc;
	}

	public static String getDesc(int value) {
		for (PoliceOrgLevel type : PoliceOrgLevel.values()) {
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
