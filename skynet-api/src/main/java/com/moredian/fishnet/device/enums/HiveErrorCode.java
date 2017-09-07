package com.moredian.fishnet.device.enums;

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
public enum HiveErrorCode implements ErrorCode {

	/** 户室ID不可为空 */
	EMPTY_ROOM_EXCEPTION(new V1ErrorCode(ErrorType.SYSTEM, ErrorLevel.ERROR, "014", "0001"), "户室ID不可为空"),
	/** 小区ID不可为空 */
	EMPTY_ORG_EXCEPTION(new V1ErrorCode(ErrorType.SYSTEM, ErrorLevel.ERROR, "014", "0002"), "机构ID不可为空"),
	/** 未找到相关户室! */
	NO_ROOM_EXCEPTION(new V1ErrorCode(ErrorType.SYSTEM, ErrorLevel.ERROR, "014", "0003"), "未找到相关户室"),
	/** 重复添加户室 */
	REPEAT_ROOM_EXCEPTION(new V1ErrorCode(ErrorType.SYSTEM, ErrorLevel.ERROR, "014", "0004"), "重复添加户室"),
	/** 设备效验失败 */
	CHECK_EQUIPMENT_EXCEPTION(new V1ErrorCode(ErrorType.SYSTEM, ErrorLevel.ERROR, "014", "0005"), "设备效验失败"),
	/**账号不存在*/
	USER_NOEXIST(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.ERROR, "014", "0006"), "账号不存在"),
	/**密码错误*/
	USER_PASSWD_WRONG(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.ERROR, "014", "0007"), "密码错误"),
	/** 账号或密码错误 */
	USER_OR_PASSWD_WRONG(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.ERROR, "014", "0018"), "账号或密码错误"),
	/**存在子权限，禁止删除*/
	PERM_DELETE_HADCHILD(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.ERROR, "014", "0008"), "存在子权限，禁止删除"),
	/**权限已被引用，禁止删除*/
	PERM_DELETE_INUSE(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.ERROR, "014", "0009"), "权限已被引用，禁止删除"),
	/**角色已被引用，禁止删除 */
	ROLE_DELETE_INUSE(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0010"), "角色已被引用，禁止删除"),
	/**该户室不存在或ID无效*/
	INVALID_ORG_ID(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.ERROR, "014", "0011"), "该户室不存在或ID无效"),
	/**该户室不存在或ID无效*/
	INVALID_ROOM_ID(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.ERROR, "014", "0012"), "该户室不存在或ID无效"),
	/**设备类型不正确*/
	EQUIPMENT_TYPE_WRONG(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.ERROR, "014", "0013"), "设备类型不正确"),
	/** 设备类型不匹配，无法激活 */
	EQUIPMENT_TYPE_NOMATCH(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.ERROR, "014", "0053"), "设备类型不匹配，无法激活"),
	/**上级机构不存在*/
	PARENT_ORG_NOTEXIST(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.ERROR, "014", "0014"), "上级机构不存在"),
	/**该用户不是户主*/
	IS_NOT_OWNER(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.ERROR, "014", "0015"), "该用户不是户主"),
	/**验证码效验失败*/
	CHECK_VERIFY_FAIL(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.ERROR, "014", "0016"), "验证码效验失败"),
	/** 手机号码验证失败 */
	VALIDATE_MOBILE_FAIL(new V1ErrorCode(ErrorType.SYSTEM, ErrorLevel.ERROR, "014", "0017"), "手机号码验证失败"),
	/** 设备已激活过 */
	ACTIVE_CONFLICT_ERROR(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0018"), "设备已激活过"),
	/** 激活码无效 */
	INVALID_ACTIVE_EXCEPTION(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0019"), "激活码无效"),
	/** 激活码过期 */
	INVALID_ACTIVE_EXPIRE(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0219"), "超时过期"),
	/** 激活码过期 */
	/** 参数不符合要求 */
	PARAMS_WRONG(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0020"), "参数不符合要求"),
	/** 未找到设备 */
	NO_EXIST_EQUIPMENT(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0021"), "未找到设备"),
	/** 图片类型不符合要求 */
	WRONG_IMG_TYP(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0022"), "图片类型不符合要求"),

	/**机构成员不存在*/
	USER_NOT_EXIST(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0023"),"成员不存在"),
	/**机构成员审核记录不存在*/
	USER_AUDIT_NOT_EXIST(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0024"),"审核记录不存在"),
	USER_AUDIT_NOT_ALLOW(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0025"),"您没有审核此记录的权限"),
	USER_AUDIT_STATUS_NOT_RIGHT(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0026"),"审核状态不正确"),
	USER_AUDITOR_NOT_EXIST(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0027"),"审核申请人不存在"),

	MOBILE_NOT_RIGHT(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0028"),"用户手机号码不正确"),
	ID_CARD_NOT_RIGHT(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0029"),"用户身份证不正确"),
	USER_MESSAGE_NOT_EXIST(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0030"),"用户信息不完整"),
	EMPTY_ID_CARD_MOBILE(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0031"),"用户身份证和手机号必须有一个"),
	EMPTY_ORG_USER_NAME(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0032"),"成员名称不能为空"),

	NOTEXIST_ROOM_EXCEPTION(new V1ErrorCode(ErrorType.SYSTEM, ErrorLevel.ERROR, "014", "0033"), "未找到户室数据"),

	USER_RELATION_NOT_EXIST(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0034"),"成员关系不存在"),
	USER_ACTIVITY_NOT_EXIST(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0035"),"成员活动范围不存在"),
	/** 没有导入设备 */
	NO_IMPORT_EQUIPMENT(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0121"), "未导入设备"),
	/**用户已经存在*/
	MOBILE_USER_EXIST(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"111","0001"),"用户已经存在"),
	/**已存在户主*/
	OWENER_USER_EXIST(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0036"),"已存在户主"),
	/**在该户室下被禁用*/
	CLOUDE_USER_DISABLE(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0037"),"您在该户室下被禁用"),

	USER_DELETE_FAIL(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0038"),"成员删除失败"),
	/** 此激活码只能在原设备上使用 */
	ACTIVE_REFUSED(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0039"), "此激活码只能在原设备上使用"),

	EMPTY_PARAM_EXCEPTION(new V1ErrorCode(ErrorType.SYSTEM, ErrorLevel.ERROR, "014", "0040"), "参数不可为空"),

	WRONG_PARAM_EXCEPTION(new V1ErrorCode(ErrorType.SYSTEM, ErrorLevel.ERROR, "014", "0041"), "参数有误"),
	
	/** 手机号变更失败 */
	MOBILE_CHANGE_EXCEPTION(new V1ErrorCode(ErrorType.SYSTEM, ErrorLevel.ERROR, "014", "0042"), "手机号变更失败"),
	
	/** 账号注册失败 */
	ACCOUNT_REGISTER_EXCEPTION(new V1ErrorCode(ErrorType.SYSTEM, ErrorLevel.ERROR, "014", "0043"), "账号注册失败"),
	AVATAR_WRONG_IMG_TYP(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0044"), "头像图片类型不符合要求"),
	FACE_WRONG_IMG_TYP(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0045"), "识别头像图片类型不符合要求"),
	ROOT_SUB_ORG_NOTEXIST(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0046"), "未找到子机构根节点数据"),
	OPER_EXIST(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0053"), "帐号已存在，请勿重复添加"),
	ROLE_EXIST(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0054"), "角色已存在，请勿重复添加"),
	
	UNKONW_VIDEO_STREAM(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0047"), "未知的视频流格式"),
	WRONG_VIDEO_STREAM(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.WARN, "014", "0048"), "视频流格式有误"),
	EMPTY_SEX_STREAM(new V1ErrorCode(ErrorType.SERVICE, ErrorLevel.ERROR, "014", "0049"), "性别不正确"),
	USER_Modify_FAIL(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0050"),"成员修改失败"),
	
	ROOM_RELATION_NOT_EXIST(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0051"),"户室关系不存在"),
	CLOUDE_USER_DISABLE_FAIL(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0052"),"户室禁用失败"),
	ADD_USER_FAIL(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0053"),"添加用户失败"),
	DELETE_USER_FAIL(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0054"),"删除用户失败"),
	ACCOUNT_ACTIVITY_FAIL(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0055"),"用户激活失败"),
	USER_AUDIT_ADD_FAIL(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0056"),"审核添加失败"),
	USER_AUDIT_REFUSE_FAIL(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0057"),"审核拒绝失败"),
	USER_AUDIT_AGREE_FAIL(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0058"),"审核同意失败"),
	USER_AUDIT_DELETE_FAIL(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0059"),"审核删除失败"),
	CAMERA_LOCATION_EXIST(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0060"),"此位置已存在摄像头"),
	BOX_LOCATION_EXIST(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0061"),"此位置已存在悉尔盒子"),
	MJTYJ_LOCATION_EXIST(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0062"),"此位置已存在门禁一体机"),
	ISSU_DEVICE_ACCESS_KEY_FAIL(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0063"),"设备密钥颁发失败"),
	
	EQUIPMENT_UPDATE_NOT_SUPPORT(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.INFO,"014","0064"),"设备不支持升级"),
	EQUIPMENT_UPDATE_NO_PACKAGE(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.INFO,"014","0065"),"没有安装包"),
	IDENTITY_CARD_USER_EXIST(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0066"),"该身份证的用户已经存在"),

	ACCOUNT_ALREADY_ACTIVITY(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0067"),"账户已激活"),
	ROOM_RELATION_EXIST(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0068"),"户室关系已存在"),
	ACCOUNT_NOT_EXIST(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0069"),"申请人不存在"),
	
	DOOR_PASSWD_WRONG(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.WARN,"014","0070"),"旧门禁密码有误"),

	END_DATE_BEFORE_TODAY_WRONG(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.WARN,"014","0071"),"结束时间早于今天"),
	END_DATE_AFTER_100YEAR_WRONG(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.WARN,"014","0072"),"结束时间晚于100年"),

	BEGIN_DATE_BEFORE_TODAY_WRONG(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.WARN,"014","0073"),"开始时间早于今天"),

	SYS_STOP_USED(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.WARN,"014","0074"),"旧向日葵系统暂停使用"),

	ACTIVITY_USER_ERROR(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.WARN,"014","0075"),"激活用户账户失败"),

	ACTIVITY_USER_ACCOUNT_UNKNOWN_ERROR(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.WARN,"014","0076"),"激活用户注册账户未知错误"),
	USER_SUB_ORG_ID_AUDITOR_NOT_ADD(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0077"),"您不能申请该户室"),
	USER_OWNER_EXIST_AUDITOR_NOT_AGREE(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0078"),"该户室已有户主"),
	ROOM_RELATION_GET_ERROR(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0079"),"获取户室关系失败"),
	/**    注册校验*/	
	REAL_NAME_VALIDATE_GET_ERROR(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0080"),"真实姓名错误"),
	ORG_ID_VALIDATE_GET_ERROR(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0081"),"机构id错误"),
	USER_ID_VALIDATE_GET_ERROR(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0082"),"用户id错误"),
	ACCOUNT_ID_VALIDATE_GET_ERROR(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0083"),"账户id错误"),
    AVATAR_IMG_URL_VALIDATE_GET_ERROR(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0084"),"头像错误"),
    MOBILE_VALIDATE_GET_ERROR(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0085"),"手机号错误"),
    
    EMAIL_VALIDATE_GET_ERROR(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0086"),"邮箱错误"),
    DEPARTMENT_VALIDATE_GET_ERROR(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0087"),"部门错误"),
    JOBNUM_VALIDATE_GET_ERROR(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0088"),"工号错误"),
    FACE_IMG_URL_VALIDATE_GET_ERROR(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0089"),"照片错误"),
    WORK_TIME_VALIDATE_GET_ERROR(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0090"),"工作时间错误"),
    REMARK_VALIDATE_GET_ERROR(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0091"),"备注错误"),
    SINGNATURE_VALIDATE_GET_ERROR(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0092"),"签名错误"),
    BIRTHDAY_VALIDATE_GET_ERROR(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0093"),"生日错误"),
    DUTY_VALIDATE_GET_ERROR(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0094"),"职务错误"),
    ORG_USER_NAME_VALIDATE_GET_ERROR(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0095"),"用户名称错误"),
    PHONE_VALIDATE_GET_ERROR(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0096"),"电话号错误"),
    USER_STAUS_VALIDATE_GET_ERROR(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0097"),"用户状态错误"),

    
    SN_MAC_UNIQUE_ERROR(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0098"),"sn,mac地址,私钥关系已存在"),
    SN_MAC_ADD_ERROR(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0099"),"新增出错"),
	ORG_SUBORG_NOT_EXIST(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0100"),"该机构没有此位置"),
	SN_MAC_UNIQUE_EXCEPTION(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0101"),"sn,mac地址,私钥关系【多条】或【无】"),
	USER_CLOSE_FAIL(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0102"),"成员禁用失败"),

	USER_ENABLE_FAIL(new V1ErrorCode(ErrorType.SERVICE,ErrorLevel.ERROR,"014","0103"),"成员启用失败"),
	/** 未找到借还点! */
	NO_BORROW_RETURN_ROOM_EXCEPTION(new V1ErrorCode(ErrorType.SYSTEM, ErrorLevel.ERROR, "014", "0104"), "未找到借还点"),
	DELETE_DEVICE_CONFIG_EXCEPTION(new V1ErrorCode(ErrorType.SYSTEM, ErrorLevel.ERROR, "014", "0105"), "删除布控失败"),
	SYS_ERROR(new V1ErrorCode(ErrorType.SYSTEM, ErrorLevel.ERROR, "014", "0106"), "系统错误"),

	;

	/** 错误码，不能为空 */
	private V1ErrorCode errorCode;

	/** 错误信息，一般情况下不能为空 */
	private String errorMessage;

	HiveErrorCode(V1ErrorCode errorCode, String errorMessage) {
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
