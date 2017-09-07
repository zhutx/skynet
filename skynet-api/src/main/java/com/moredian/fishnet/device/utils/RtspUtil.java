package com.moredian.fishnet.device.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.moredian.bee.common.utils.ExceptionUtils;
import com.moredian.fishnet.device.enums.DeviceErrorCode;
import com.moredian.fishnet.device.enums.DeviceProviderType;

/**
 * RTSP视频流解析工具
 * @author tianxiang
 *
 */
public class RtspUtil {
	
	// 海康RTSP的正则模式
	private static final String HIKVISION_RTSP_PATTERN = "^rtsp://([\\w]{3,15}):([\\w]{3,15})@(\\d{3}\\.\\d{3}\\.\\d{1,3}\\.\\d{1,3})(/.+)";
	// 大华RTSP的正则模式
	private static final String DAHUA_RTSP_PATTERN = "^rtsp://([\\w]{3,15}):([\\w]{3,15})@(\\d{3}\\.\\d{3}\\.\\d{1,3}\\.\\d{1,3})(/.+)";
	
	
	public static final String KEY_USERNAME = "username";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_IP = "ip";

	/**
	 * 从视频流中解析出用户名、密码、ip
	 * @param providerType
	 * @param videoStream
	 * @return
	 */
	public static Map<String, String> parseVideoStream(Integer providerType, String videoStream){
		Map<String, String> map = new HashMap<>();
		
		if(DeviceProviderType.HIKVISION.getValue() == providerType) {
			
			// 视频流格式： rtsp://admin:xe123456@192.168.1.52/h264/ch1/main/av_stream
			// 视频流格式校验
			Pattern p = Pattern.compile(HIKVISION_RTSP_PATTERN);
			Matcher m = p.matcher(videoStream);
			if(!m.matches()) {
				ExceptionUtils.throwException(DeviceErrorCode.WRONG_VIDEO_STREAM, DeviceErrorCode.WRONG_VIDEO_STREAM.getMessage());
			}
			
			m.reset();
			while(m.find()){
				map.put(KEY_USERNAME, m.group(1));
				map.put(KEY_PASSWORD, m.group(2));
				map.put(KEY_IP, m.group(3));
			} 
			
			return map;
			
		}
		
		if(DeviceProviderType.DAHUA.getValue() == providerType) {
			
			// 视频流格式： rtsp://admin:xe123456@192.168.1.52/cam/realmonitor?channel=1&subtype=0
			// 视频流格式校验
			Pattern p = Pattern.compile(DAHUA_RTSP_PATTERN);
			Matcher m = p.matcher(videoStream);
			if(!m.matches()) {
				ExceptionUtils.throwException(DeviceErrorCode.WRONG_VIDEO_STREAM, DeviceErrorCode.WRONG_VIDEO_STREAM.getMessage());
			}
			
			m.reset();
			while(m.find()){
				map.put(KEY_USERNAME, m.group(1));
				map.put(KEY_PASSWORD, m.group(2));
				map.put(KEY_IP, m.group(3));
			}
			
			return map;
			
		} 
		
		ExceptionUtils.throwException(DeviceErrorCode.NO_VIDEO_STREAM_PATTERN, String.format(DeviceErrorCode.NO_VIDEO_STREAM_PATTERN.getMessage(), DeviceProviderType.getDesc(providerType)));
		
		return null;
		
	}
	
	public static void main(String[] args) {
		
		// 视频流
		String videoString = "rtsp://admin:xe123456@192.168.1.52/h264/ch1/main/av_stream";
		
		// 正则模式
		Pattern p = Pattern.compile("^rtsp://([\\w]{5,15}):([\\w]{5,15})@(\\d{3}\\.\\d{3}\\.\\d{1,3}\\.\\d{1,3})(/.+)");
		
		// 模式匹配
		Matcher m = p.matcher(videoString);
		
		if(m.matches()) { // 匹配成功
			m.reset();
			while(m.find()){
				System.out.println(m.group(1));
				System.out.println(m.group(2));
				System.out.println(m.group(3));
				System.out.println(m.group(4));
			}
		}
	}
    
}
