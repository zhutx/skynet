package com.moredian.skynet.org.enums;

public enum ModuleType {
	
	FISHPOND("蜂巢PC",99),
	
	GATE_H5("门禁H5",4),
	
	GATE_PC("门禁PC", 5),
	
	PMO_PC("酒店PC", 7),
	
	POLICE_PC("公安实人认证PC", 8),
	
	VISITOR_H5("访客H5", 21),
	
	VISITOR_PC("访客PC", 22)
	
	;
	
	private String desc;
	
	private int value;

	ModuleType(String desc ,int value){
		this.value = value;
		this.desc =desc;
	}
	
	public String getDesc() {
		return desc;
	}

	public static String getDesc(int value) {
		for (ModuleType type : ModuleType.values()) {
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
