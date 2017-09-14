package com.moredian.skynet.org.enums;

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
public enum OrgErrorCode implements ErrorCode {
	
	ORG_EXIST(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0046"), "机构已存在"),
	
	ORG_NOT_EXIST(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0046"), "机构不存在"),
	
	ORG_NOT_STOP(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0046"), "机构尚未停用"),
	
	SOME_BIZ_NOT_STOP(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0046"), "业务尚未完全停用"),
	
	ROOT_POSITION_NOT_EXIST(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0046"), "未找到根位置"),
	
	PARENT_POSITION_NOT_EXIST(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0046"), "父位置不存在"),
	
	POSITION_NOT_EXIST(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0046"), "位置不存在"),
	
	TREE_POSITION_REFUSE_DELETE(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0046"), "拒绝删除树型位置节点"),
	
	DEPT_EXIST(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0046"), "部门已存在"),
	
	PARENT_DEPT_NOTEXIST(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0046"), "父部门不存在"),
	
	GROUP_EXIST(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0046"), "组已存在"),
	
	GROUP_NAME_UPDATE_REFUSE(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0046"), "拒绝修改系统默认群组"),
	
	GROUP_REFUSE_DELETE(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0046"), "拒绝删除系统创建的组"),
	
	SYSGROUP_REFUSE_OPERA(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0046"), "系统默认群组不支持本操作"),
	
	AREA_WRONG(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0046"), "行区数据有误"),
	
	;

	/** 错误码，不能为空 */
	private V1ErrorCode errorCode;

	/** 错误信息，一般情况下不能为空 */
	private String errorMessage;

	OrgErrorCode(V1ErrorCode errorCode, String errorMessage) {
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
