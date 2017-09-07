package com.moredian.fishnet.org.enums;

public enum GroupRangeType {
	
	RANGE_DEPT("部门范围关系",1),
	
	RANGE_MEMBER("成员范围关系",2),
	
	;
	
	private String desc;
	
	private int value;

	GroupRangeType(String desc ,int value){
		this.value = value;
		this.desc =desc;
	}
	
	public String getDesc() {
		return desc;
	}

	public static String getDesc(int value) {
		for (GroupRangeType type : GroupRangeType.values()) {
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
