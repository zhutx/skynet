package com.moredian.fishnet.org.enums;

public enum OrgBizStatus {
	
	DISABLE("停用",0),
	
	ENABLE("启用",1),
	
	;
	
	private String desc;
	
	private int value;

	OrgBizStatus(String desc ,int value){
		this.value = value;
		this.desc =desc;
	}
	
	public String getDesc() {
		return desc;
	}

	public static String getDesc(int value) {
		for (OrgBizStatus type : OrgBizStatus.values()) {
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
