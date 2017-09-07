package com.moredian.fishnet.org.enums;

public enum OrgStatus {
	
	INIT("初始化",-1),
	
	UNBIND("未绑定",0),
	
	USABLE("可用",1),
	
	DELETE("删除",2),
	
	CLOSE("关闭",3),
	
	;
	
	private String desc;
	
	private int value;

	OrgStatus(String desc ,int value){
		this.value = value;
		this.desc =desc;
	}
	
	public String getDesc() {
		return desc;
	}

	public static String getDesc(int value) {
		for (OrgStatus type : OrgStatus.values()) {
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
