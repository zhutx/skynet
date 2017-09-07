package com.moredian.fishnet.member.enums;

public enum MemberStatus {
	
	USABLE("可用",0),
	DISABLE("已删除",1),
	CLOSE("已离职",2),
	
	;
	
	private String desc;
	
	private int value;

	MemberStatus(String desc ,int value){
		this.value = value;
		this.desc =desc;
	}
	
	public String getDesc() {
		return desc;
	}

	public static String getDesc(int value) {
		for (MemberStatus type : MemberStatus.values()) {
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
