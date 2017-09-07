package com.moredian.fishnet.web.controller.common.request;

public enum ImageFileType {
	
	PERSON_HEAD("头像",1),
	
	PERSON_FACE("人脸",2),
	
	SUBJECT_BG("主题背景",3), 
	
	SUBJECT_LOGO("主题LOGO", 4)
	
	;
	
	private String desc;
	
	private int value;

	ImageFileType(String desc ,int value){
		this.value = value;
		this.desc =desc;
	}
	
	public String getDesc() {
		return desc;
	}

	public static String getDesc(int value) {
		for (ImageFileType type : ImageFileType.values()) {
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
