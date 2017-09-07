package com.moredian.fishnet.device.enums;

public enum CloudeyeDeployAction {
	
	ADD("新增主板布控",1),
	
	UPDATE("更新主板布控",2),
	
	DELETE("删除主板布控",3),
	
	CAMERA_BIND("绑定摄像机",4),
	
	CAMERA_UNBIND("解绑摄像机",5),
	
	;
	
	private String desc;
	
	private int value;

	CloudeyeDeployAction(String desc ,int value){
		this.value = value;
		this.desc =desc;
	}
	
	public String getDesc() {
		return desc;
	}

	public static String getDesc(int value) {
		for (CloudeyeDeployAction type : CloudeyeDeployAction.values()) {
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
