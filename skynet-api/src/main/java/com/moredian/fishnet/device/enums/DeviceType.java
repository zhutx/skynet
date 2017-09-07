package com.moredian.fishnet.device.enums;

import java.util.ArrayList;
import java.util.List;

public enum DeviceType {
	
	SERVER_FIREANT("SERVER_FIRANT",21, "识别服务器"),
	
	CAMERA("CAMERA",10, "摄像机"),
	
	BOARD_BOX("BOX",41, "门禁盒子"),
	
	BOARD_DYNAMIC_BOX("DYNAMIC_BOX",42, "动态盒子"),
	
	BOARD_VISITOR("VISITOR",81, "访客机"),
	
	BOARD_ATTENDANCE("ATTENDANCE",61, "门禁机"),

	BOARD_ATTENDANCE_DUALEYE("DUAL_EYE_ATTENDANCE",62, "D1门禁机"),
	
	BOARD_GATE("EVIDENCE",40, "闸机"),
	
	BOARD_EVIDENCE("EVIDENCE",71, "认证机"),
	
	;
	
	// 安卓主板设备
	private static List<Integer> BOARD_LIST = new ArrayList<>();
	// 安卓主板识别设备
	private static List<Integer> VERIFY_BOARD_LIST = new ArrayList<>();
	// 需要绑定群组的设备
	private static List<Integer> NEED_GROUP_LIST = new ArrayList<>();
	// 需要同步云眼的设备
	private static List<Integer> CLOUDEYE_NEED_LIST = new ArrayList<>();
	// 需要布控的设备
	private static List<Integer> NEED_DEPLOY_LIST = new ArrayList<>();
	// 需要标准位置的设备
	private static List<Integer> NEED_POSITION_LIST = new ArrayList<>();
	// 门禁设备
	private static List<Integer> DOOR_DEVICE_LIST = new ArrayList<>();
	// 需要绑定主题
	private static List<Integer> SUBJECT_NEED_LIST = new ArrayList<>();
    
	
	static {
		
		BOARD_LIST.add(BOARD_BOX.getValue());
		BOARD_LIST.add(BOARD_VISITOR.getValue());
		BOARD_LIST.add(BOARD_ATTENDANCE.getValue());
		BOARD_LIST.add(BOARD_ATTENDANCE_DUALEYE.getValue());
		BOARD_LIST.add(BOARD_EVIDENCE.getValue());
		
		VERIFY_BOARD_LIST.add(BOARD_BOX.getValue());
		VERIFY_BOARD_LIST.add(BOARD_VISITOR.getValue());
		VERIFY_BOARD_LIST.add(BOARD_ATTENDANCE.getValue());
		VERIFY_BOARD_LIST.add(BOARD_ATTENDANCE_DUALEYE.getValue());
		VERIFY_BOARD_LIST.add(BOARD_EVIDENCE.getValue());
		VERIFY_BOARD_LIST.add(BOARD_GATE.getValue());
		
		NEED_GROUP_LIST.add(BOARD_ATTENDANCE.getValue());
		NEED_GROUP_LIST.add(BOARD_ATTENDANCE_DUALEYE.getValue());
		NEED_GROUP_LIST.add(BOARD_VISITOR.getValue());
		NEED_GROUP_LIST.add(BOARD_BOX.getValue());
		NEED_GROUP_LIST.add(BOARD_GATE.getValue());
		
		NEED_DEPLOY_LIST.add(CAMERA.getValue());
		NEED_DEPLOY_LIST.add(BOARD_BOX.getValue());
		NEED_DEPLOY_LIST.add(BOARD_VISITOR.getValue());
		NEED_DEPLOY_LIST.add(BOARD_ATTENDANCE.getValue());
		NEED_DEPLOY_LIST.add(BOARD_ATTENDANCE_DUALEYE.getValue());
		NEED_DEPLOY_LIST.add(BOARD_GATE.getValue());
		
		CLOUDEYE_NEED_LIST.add(SERVER_FIREANT.getValue());
		CLOUDEYE_NEED_LIST.add(CAMERA.getValue());
		CLOUDEYE_NEED_LIST.add(BOARD_BOX.getValue());
		CLOUDEYE_NEED_LIST.add(BOARD_VISITOR.getValue());
		CLOUDEYE_NEED_LIST.add(BOARD_ATTENDANCE.getValue());
		CLOUDEYE_NEED_LIST.add(BOARD_ATTENDANCE_DUALEYE.getValue());
		CLOUDEYE_NEED_LIST.add(BOARD_EVIDENCE.getValue());
		CLOUDEYE_NEED_LIST.add(BOARD_GATE.getValue());
		
		DOOR_DEVICE_LIST.add(BOARD_ATTENDANCE.getValue());
		DOOR_DEVICE_LIST.add(BOARD_ATTENDANCE_DUALEYE.getValue());
		DOOR_DEVICE_LIST.add(BOARD_BOX.getValue());
		//DOOR_DEVICE_LIST.add(BOARD_GATE.getValue());
		
		SUBJECT_NEED_LIST.add(BOARD_ATTENDANCE.getValue());
		SUBJECT_NEED_LIST.add(BOARD_ATTENDANCE_DUALEYE.getValue());
		SUBJECT_NEED_LIST.add(BOARD_BOX.getValue());
		
	}
	
	private String desc;
	
	private int value;
	
	private String name;

	DeviceType(String desc ,int value, String name){
		this.value = value;
		this.desc =desc;
		this.name = name;
	}
	
	public static String getName(int value) {
		for (DeviceType type : DeviceType.values()) {
			if (type.getValue() == value) {
				return type.name;
			}
		}
		return null;
	}
	
	/**
	 * 是否为安卓主板识别设备
	 * @param value
	 * @return
	 */
	public static boolean isVerifyBoardDevice(int value) {
		if(VERIFY_BOARD_LIST.contains(value)){
			return true;
		}
		return false;
	}
	
	/**
	 * 是否需要绑定群组
	 * @param value
	 * @return
	 */
	public static boolean isNeedGroupDevice(int value) {
		if(NEED_GROUP_LIST.contains(value)){
			return true;
		}
		return false;
	}
	
	public static boolean isCloudeyeNeedDevice(int value) {
		if(CLOUDEYE_NEED_LIST.contains(value)){
			return true;
		}
		return false;
	}
	
	public static boolean isSubjectNeedDevice(int value) {
		if(SUBJECT_NEED_LIST.contains(value)){
			return true;
		}
		return false;
	}
	
	public static boolean isNeedDeployDevice(int value) {
		if(NEED_DEPLOY_LIST.contains(value)){
			return true;
		}
		return false;
	}
	
	public static boolean isDoorDevice(int value) {
		if(DOOR_DEVICE_LIST.contains(value)){
			return true;
		}
		return false;
	}
	
	public static boolean isServer(int value) {
		if(DeviceType.SERVER_FIREANT.getValue() == value) return true;
		return false;
	}
	
	public static boolean isCamera(int value) {
		if(DeviceType.CAMERA.getValue() == value) return true;
		return false;
	}
	
	public static boolean isBoard(int value) {
		if(BOARD_LIST.contains(value)) return true;
		return false;
	}
	
	public static boolean isNeedPosition(int value) {
		if(NEED_POSITION_LIST.contains(value)) return true;
		return false;
	}
	
	public static List<Integer> getDoorDevices() {
		return DOOR_DEVICE_LIST;
	}

	public String getDesc() {
		return desc;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
