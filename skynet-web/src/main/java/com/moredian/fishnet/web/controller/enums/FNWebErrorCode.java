package com.moredian.fishnet.web.controller.enums;

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
public enum FNWebErrorCode implements ErrorCode {

	NOT_IMAGE(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0001"), "非法图片"),
	
	IMAGE_RATIO_REFUSE(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0002"), "图片宽高比不符合要求"),
	
	IMAGE_SO_SMALL(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0003"), "图片规格太小"),
	
	IMAGE_SO_LARGE(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0004"), "图片规格太大")
	
	;

	/** 错误码，不能为空 */
	private V1ErrorCode errorCode;

	/** 错误信息，一般情况下不能为空 */
	private String errorMessage;

	FNWebErrorCode(V1ErrorCode errorCode, String errorMessage) {
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
