package com.moredian.skynet.org.enums;

public enum BizType {
	
	RECOGNIZE("1比N识别",100),
	
	CONTRAST("1比1对比",200),
	
	CERTIFICATION("身份自证", 300),
	
	OPENDOOR("门禁",301),
	
	VISITOR("访客", 302),
	
	SECURITY("动态安防",303),
	
	INTERCOM("可视对讲",304),
	
	HOTEL("未来酒店",305),
	
	;
	
	private String desc;
	
	private int value;

	BizType(String desc ,int value){
		this.value = value;
		this.desc =desc;
	}
	
	public String getDesc() {
		return desc;
	}

	public static String getDesc(int value) {
		for (BizType type : BizType.values()) {
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
