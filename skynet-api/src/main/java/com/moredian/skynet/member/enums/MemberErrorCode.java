package com.moredian.skynet.member.enums;

import com.moredian.bee.common.exception.ErrorCode;
import com.moredian.bee.common.exception.v1.ErrorLevel;
import com.moredian.bee.common.exception.v1.ErrorType;
import com.moredian.bee.common.exception.v1.V1ErrorCode;

/**
 * 定义机构错误码类型
 *
 * @author zhutx
 *
 */
public enum MemberErrorCode implements ErrorCode {
	
	PARAM_WRONG(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0001"), "参数有误"),

	IDENTITY_CARD_WRONG(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0001"), "身份证号码有误"),
	
	MEMBER_EXIST(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0002"), "成员[%s]已存在"),
	
	MEMBER_NOT_EXIST(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0003"), "成员[%s]不存在"),
	
	USER_NOT_EXIST(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0003"), "成员不存在"),
	
	SAVE_IMAGE_ERROR(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0004"), "存储图片异常"),
	
	SAVE_IMAGE_WRONG(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0005"), "存储图片失败"),
	
	NO_FACE(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0006"), "未检测到人脸"),
	
	MORE_THEN_ONE_FACE(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0007"), "识别照片中存在多张人脸"),
	
	VISITOR_NOT_EXIST(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0008"), "访客[%s]不存在"),
	
	NOT_IMAGE(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0009"), "非法图片"),
	
	FACE_CUT_ERROR(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0010"), "裁剪人脸照片异常"),
	
	USER_RELATION_NOT_EXIST(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0011"), "成员关系不存在"),

	DEPT_USER_RELATION_EXIST(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0012"), "部门下还有成员存在"),

	CHILD_DEPT_RELATION_EXIST(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0013"), "部门下还有子部门存在"),

	MOBILE_NUMBER_EXIST(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0014"), "手机号已存在"),
	;

	/** 错误码，不能为空 */
	private V1ErrorCode errorCode;

	/** 错误信息，一般情况下不能为空 */
	private String errorMessage;

	MemberErrorCode(V1ErrorCode errorCode, String errorMessage) {
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
