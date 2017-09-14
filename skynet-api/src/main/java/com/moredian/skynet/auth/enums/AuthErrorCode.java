package com.moredian.skynet.auth.enums;

import com.moredian.bee.common.exception.ErrorCode;
import com.moredian.bee.common.exception.v1.ErrorLevel;
import com.moredian.bee.common.exception.v1.ErrorType;
import com.moredian.bee.common.exception.v1.V1ErrorCode;

/**
 * 定义机构错误码类型
 *
 */
public enum AuthErrorCode implements ErrorCode {
	
	/** 帐号或密码错误 */
	ACCOUNT_WRONG(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "001", "0001"), "帐号或密码错误"),
	
	/** 帐号不存在 */
	ACCOUNT_NOT_EXIST(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "001", "0001"), "帐号不存在"),
	
	PASSWD_UPDATE_FAIL(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "001", "0001"), "密码修改失败"),
	
	/** 帐号异常 */
	ACCOUNT_STATE_PROPREM(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "001", "0001"), "帐号异常"),
	
	/** 无此操作员 */
	NO_THIS_OPER(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "001", "0001"), "操作员不存在"),
	
	/** 操作员状态异常 */
	OPER_STATE_PROPREM(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "001", "0001"), "操作员状态异常"),

	/** 权限已存在 */
	PERM_EXIST(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "001", "0001"), "权限已存在"),
	
	/** 拥有子权限 */
	HAD_CHILD_PERM(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "001", "0001"), "拥有子权限，拒绝删除"),
	
	/** 权限已被引用 */
	PERM_IN_USE(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "001", "0001"), "权限已被引用，拒绝删除"),
	
	/** 角色已存在 */
	ROLE_EXIST(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "001", "0001"), "角色已存在"),
	
	/** 角色已被引用 */
	ROLE_IN_USE(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "001", "0001"), "角色已被引用，拒绝删除"),
	
	/** 操作员已存在 */
	OPER_EXIST(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "001", "0001"), "账号已存在"),
	
	/** 手机号格式有误 */
	MOBILE_WRONG(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "001", "0001"), "手机号格式有误"),
	
	MOBILE_EXIST(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "001", "0001"), "手机号已注册"),
	
	;

	/** 错误码，不能为空 */
	private V1ErrorCode errorCode;

	/** 错误信息，一般情况下不能为空 */
	private String errorMessage;

	AuthErrorCode(V1ErrorCode errorCode, String errorMessage) {
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
