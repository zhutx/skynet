package com.moredian.fishnet.org.enums;

public enum OrgType {
	
	/** 平台  **/
	PLATFORM("平台",99),
	
	/** 酒店  **/
	HOTEL("酒店",4),
	
	/** 网吧 **/
	NETBAR("网吧",5),
	
	/** 企业 **/
	ENTERPRISE("企业",7),
	
	/** 公安机关 **/
	POLICE("公安机关",6),
	
	/** 社区 **/
	COMMUNITY("社区",0),
	
	;
	
	private String desc;
	
	private int value;

	OrgType(String desc ,int value){
		this.value = value;
		this.desc =desc;
	}
	
	public String getDesc() {
		return desc;
	}

	public static String getDesc(int value) {
		for (OrgType type : OrgType.values()) {
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
