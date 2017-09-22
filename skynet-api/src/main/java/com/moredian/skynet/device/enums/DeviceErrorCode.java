package com.moredian.skynet.device.enums;

import com.moredian.bee.common.exception.ErrorCode;
import com.moredian.bee.common.exception.v1.ErrorLevel;
import com.moredian.bee.common.exception.v1.ErrorType;
import com.moredian.bee.common.exception.v1.V1ErrorCode;

/**
 * 定义机构错误码类型
 *
 * @author erxiao
 *
 */
public enum DeviceErrorCode implements ErrorCode {
	
	DEVICE_EXIST(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0048"), "设备已存在"),
	
	DEVICE_NOT_EXIST(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0048"), "设备不存在"),
	
	WRONG_VIDEO_STREAM(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0048"), "视频流格式有误"),
	
	NO_VIDEO_STREAM_PATTERN(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0048"), "%s的视频流解析规则未配置"),
	
	DEVICE_EDIT_FAIL(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0018"), "设备编辑失败,请重试"),
	DEVICE_DELETE_FAIL(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0018"), "设备删除失败,请重试"),
	
	CE_DEVICE_DELETE_FAIL(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0018"), "删除云眼设备失败"),
	
	CE_DEVICE_UPDATE_FAIL(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0018"), "修改云眼设备失败"),
	
	RECOGNIZE_BUSI_NOT_OPEN(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0018"), "1比N识别业务未开启"),
	
	CE_DEVICE_ADD_FAIL(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0018"), "添加云眼设备失败"),
	
	INVALID_ACTIVE_EXCEPTION(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0019"), "激活码无效"),
	
	/** 设备类型不匹配，无法激活 */
	EQUIPMENT_TYPE_NOMATCH(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.ERROR, "014", "0053"), "设备类型不匹配，无法激活"),
	
	/** 设备已激活过 */
	ACTIVE_CONFLICT_ERROR(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0018"), "设备已在[%s]激活过"),
	
	/** 此激活码只能在原设备上使用 */
	ACTIVE_REFUSED(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0039"), "此激活码只能在原设备上使用"),
	
	DEPLOY_GROUP_EMPTY(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0048"), "布控目标组不能为空"),
	
	DEVICE_MATCH_REPEAT(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0018"), "此摄像头已匹配过盒子"),
	
	DEVICE_CE_DEPLOY_ERROR(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0018"), "设备云眼布控异常"),

	/**摄像机和魔点盒子有绑定关系，不能删除摄像机或魔点盒子*/
	DEVICE_CAMERA_BINDING_EXIST(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0052"), "设备和其他设备有绑定关系"),
	
	DEVICE_IN_BINDING(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0052"), "设备和其他设备有绑定关系"),

	DEVICE_CREATE_FAILED(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0054"), "创建摄像头失败"),

	DEVICE_BOX_CAMERA_BIND_FAILED(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0055"), "摄像头和盒子绑定"),

	DEVICE_BOX_UPDATED_FAILED(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0056"), "摄像头更新失败"),

	DEVICE_CAMERA_DELETE_FAILED(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0057"), "删除摄像头失败"),

	;

	/** 错误码，不能为空 */
	private V1ErrorCode errorCode;

	/** 错误信息，一般情况下不能为空 */
	private String errorMessage;

	DeviceErrorCode(V1ErrorCode errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	@Override
	public String getCode() {
		return this.errorCode.getCode();
	}

	/**
	 * @return the errorMessage
	 */
	public String getMessage() {
		return errorMessage;
	}

}
