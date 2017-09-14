package com.moredian.skynet.web.controller.utils;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.xier.sesame.pigeon.enums.SMSType;
import com.xier.sesame.pigeon.mm.service.MMService;
import com.xier.sesame.pigeon.mm.smsParam.CommonParams;

public class SmsUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(SmsUtil.class);
	
	// 手机号:验证码
	private static Map<String, String> checkCodeCache = new HashMap<String, String>();
	
	// 手机号:验证码获取时间
	private static Map<String, Long> smsTimeCache = new HashMap<String, Long>();
	
	
	/**
	 * 发送短信
	 * @param smsType 短信类型【参考SMS_TYPE】
	 * @param mobile 接收手机号
	 * @param params 用以替换短信内容模板参数
	 * @param mmService
	 */
	public static void sendSms(SMSType smsType, String mobile, Map<String, String> params, MMService mmService){
		
		@SuppressWarnings("rawtypes")
		com.xier.sesame.common.rpc.ServiceResponse sr = mmService.sendSms(smsType, mobile, params);
		if(sr.isSuccess()) {
			logger.info("-----获取验证码："+mobile+"的验证码为：" + params.get(CommonParams.CODE.getValue()));
			cacheCheckCode(mobile, params.get(CommonParams.CODE.getValue())); // 缓存手机的验证码
	    	cacheSmsTime(mobile, System.currentTimeMillis()); // 缓存手机验证码的生成时间毫秒数
		}
        
	}
	/*public static void sendSms(SMSType smsType, String mobile, Map<String, String> params, MMService mmService){
		
		//com.xier.sesame.common.rpc.ServiceResponse sr = mmService.sendSms(smsType, mobile, params);
		if(true) {
			logger.info(mobile+"的验证码为：" + params.get(CommonParams.CODE.getValue()));
			cacheCheckCode(mobile, params.get(CommonParams.CODE.getValue())); // 缓存手机的验证码
	    	cacheSmsTime(mobile, System.currentTimeMillis()); // 缓存手机验证码的生成时间毫秒数
		}
        
	}*/
	
	public static void cacheCheckCode(String mobileNo, String checkCode){
		checkCodeCache.put(mobileNo, checkCode);
	}
	
	public static String getCheckCode(String mobile){
		if(checkCodeCache.containsKey(mobile)){
			return checkCodeCache.get(mobile);
		}
		return null;
	}
	
	public static String getExistSendCode(String mobile){
		if(!checkCodeCache.containsKey(mobile)){
			return null; // 不存在
		} else {
			long timeMillis = smsTimeCache.get(mobile);
			if(System.currentTimeMillis() - timeMillis > 59 * 1000){ // 超过59秒了
				return null; // 存在，但超过60秒了
			} else {
				return checkCodeCache.get(mobile);
			}
		}
	}
	
	public static void removeCheckCode(String mobileNo){
		if(checkCodeCache.containsKey(mobileNo)){
			checkCodeCache.remove(mobileNo);
		}
	}
	
	public static void cacheSmsTime(String mobileNo, long smsTime){
		smsTimeCache.put(mobileNo, smsTime);
	}
	
	public static Long getSmsTime(String mobileNo) {
		if(smsTimeCache.containsKey(mobileNo)){
			return smsTimeCache.get(mobileNo);
		}
		return null;
	}
	
	public static void removeSmsTime(String mobileNo){
		if(smsTimeCache.containsKey(mobileNo)){
			smsTimeCache.remove(mobileNo);
		}
	}

}
