package com.moredian.skynet.device.manager.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.moredian.skynet.device.domain.Device;
import com.moredian.skynet.device.manager.DeviceManager;
import com.moredian.skynet.device.manager.DeviceMonitorManager;
import com.moredian.skynet.device.model.DeviceImageVersion;
import com.moredian.skynet.device.model.DeviceStateInfo;
import com.moredian.skynet.device.request.RebootRequest;
import com.moredian.skynet.device.request.StatusRequest;
import com.moredian.skynet.device.request.TransferRequest;
import com.moredian.skynet.device.request.UpgradeRequest;
import com.moredian.skynet.device.utils.HttpInvoker;
import com.moredian.skynet.device.utils.HttpInvokerResponse;


/**
 * Already move to spider service, replaced by
 * com.moredian.skynet.web.device.DeviceManagementService
 */
@Service
@Deprecated
public class DeviceMonitorManagerImpl implements DeviceMonitorManager {
	
	private static final Logger log = LoggerFactory.getLogger(DeviceMonitorManagerImpl.class);

	@Value("${scheduler.address}")
	private String schedulerAddress;

	@Value("${img.address}")
	private String imageAddress;

	@Autowired
	private DeviceManager deviceManager;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Value("${spider-webapp.address:http://localhost:8080/spider-webapp}")
	private String spiderWebappAddress;

	final public static String DEVICE_MGMT_PATH = "/services/device/management";

	private String urlOfDeviceMgmtService( ) {
		return this.spiderWebappAddress + DEVICE_MGMT_PATH;
	}

	@Override
	public DeviceStateInfo getStatus(String serialNumber) {
		byte[] buffer = (byte[]) redisTemplate.opsForHash().get(DeviceStateInfo.DEVICE_STATE, serialNumber);
		DeviceStateInfo deviceStateInfo = new DeviceStateInfo(serialNumber, DeviceStateInfo.STATE_MANAGE, 0, (byte) 0,
				false, 0L);
		deviceStateInfo.parse(buffer);
		return deviceStateInfo;
	}

	@Override
	public List<DeviceStateInfo> getStatusList(StatusRequest request) {
		List<DeviceStateInfo> map = new ArrayList<DeviceStateInfo>();
		List<Object> hashKeys = new ArrayList<Object>();
		
		List<String> list = new ArrayList<String>();
		if(request==null || request.getSnArray()==null || request.getSnArray().isEmpty()){
			return  null;
		}else{
			Collections.addAll(list, request.getSnArray().split(","));			
		} 
		
		for (String model : list) {
			hashKeys.add(model);
		}
		for (Object model : redisTemplate.opsForHash().multiGet(DeviceStateInfo.DEVICE_STATE, hashKeys)) {
			byte[] buffer = (byte[]) model;

			DeviceStateInfo deviceStateInfo = new DeviceStateInfo();
			deviceStateInfo.parse(buffer);
			if(deviceStateInfo.getSerialNumber()==null){
				continue;
			}
			map.add(deviceStateInfo);
		}
		return map;
	}

	@Override
	public DeviceImageVersion getDeviceVersion(String serialNumber){
		Device device = deviceManager.getDeviceBySn(serialNumber);		
		byte[] buffer = (byte[]) redisTemplate.opsForHash().get(DeviceImageVersion.class.getSimpleName(), device.getDeviceType().toString());
		DeviceImageVersion deviceImageVersion = new DeviceImageVersion("","","","");
		deviceImageVersion.parse(buffer);		
		deviceImageVersion.setImageName(imageAddress +"/"+ deviceImageVersion.getImageName());
		return deviceImageVersion;
	}

	private void performRequest( String url ) {
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		headerMap.put("Accept", "text/plain, application/json, application/*+json, */*");
		Map<String, String> parameterMap = new HashMap<String, String>();
		HttpInvokerResponse response = HttpInvoker.invokerPost(url, headerMap, parameterMap, "");
		if (response == null) {
			log.error("No response from remote: {}", url);
			return;
		}

		if (response.getResponseCode() == 200) {
			log.info("Response {} from remote: {}", 200, url);
		} else {
			log.error("Response {}, and body {} from remote {}", response.getResponseCode(), response.getBody(), url);
		}
	}

	@Override
	public DeviceStateInfo upgradeDevice(UpgradeRequest request) {
		String url = urlOfDeviceMgmtService();
		url = url + "/upgrade?sn=" + request.getSerialNumber() +
				"&delaySeconds=" + request.getDelaySeconds();

		log.info("UpgradeDevice with uri:" + url + ";sn:" + request.getSerialNumber());
		performRequest(url);
		return null;
	}

	@Override
	public DeviceStateInfo rebootDevice(RebootRequest request) {
		String url = urlOfDeviceMgmtService();
		url = url + "/reboot?sn=" + request.getSerialNumber() +
				"&delaySeconds=" + request.getDelaySeconds();

		log.info("RebootDevice with uri:" + url + ";sn:" + request.getSerialNumber());
		performRequest(url);
		return null;
	}

	@Override
	public DeviceStateInfo transferDevice(TransferRequest request) {
		String url = urlOfDeviceMgmtService();
		url = url + "/reboot?sn=" + request.getSerialNumber() +
				"&body=" + request.getBody() +
				"&delaySeconds=" + request.getDelaySeconds();

		log.info("TransferDevice with uri:" + url + ";sn:" + request.getSerialNumber());
		performRequest(url);
		return null;
	}

	private DeviceStateInfo commandScheduler(String serialNumber, String task) {
		DeviceStateInfo stateInfo = getStatus(serialNumber);
		log.debug("sn:{},online:{},progress:{}.",serialNumber,stateInfo.getOnline(),stateInfo.getProgress());
		if (!stateInfo.getOnline()) {
			return stateInfo;
		}
		String invokerUrl = this.schedulerAddress + "/rest/schedule";

		log.info("uri:"+invokerUrl+";sn:"+serialNumber+",task:"+task);
		
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		headerMap.put("Accept", "text/plain, application/json, application/*+json, */*");
		Map<String, String> parametrMap = new HashMap<String, String>();
		HttpInvokerResponse reponse = HttpInvoker.invokerPost(invokerUrl, headerMap, parametrMap, task);
		if (reponse == null) {
			return stateInfo;
		}
		if (reponse.getResponseCode() == 200) {
			if (reponse != null && reponse.getBody() != null) {
				// 需要做些判断
				stateInfo = getStatus(serialNumber);
			}
		}
		return stateInfo;
	}

	private String upgradeCommand(UpgradeRequest request) {
		String json = "\"jsonJobParams\": \"{  \\\"serialNumber\\\" : \\\"" + request.getSerialNumber()
				+ "\\\", \\\"uri\\\" : \\\"" + request.getUri() + "\\\", \\\"deviceVersion\\\" : \\\""
				+ request.getDeviceVersion() + "\\\", \\\"userName\\\" : \\\"" + request.getUserName()
				+ "\\\", \\\"password\\\" : \\\"" + request.getPassword() + "\\\",\\\"sign\\\" : \\\""
				+ request.getSign() + "\\\",   \\\"delaySeconds\\\" : \\\"" + request.getDelaySeconds() + "\\\"}\",";
		return getSchedulerBody(json, "UpgradeDeviceTask");
	}

	private String rebootCommand(RebootRequest request) {
		String json = "\"jsonJobParams\": \"{  \\\"serialNumber\\\" : \\\"" + request.getSerialNumber()
				+ "\\\",  \\\"delaySeconds\\\" :  " + request.getDelaySeconds() + "}\",";
		return getSchedulerBody(json, "RebootDeviceTask");
	}

	private String transferCommand(TransferRequest request) {
		String json = "\"jsonJobParams\": \"{  \\\"serialNumber\\\" : \\\"" + request.getSerialNumber()
				+ "\\\", \\\"exclusive\\\" : \\\"" + request.getExclusive() + "\\\",  \\\"body\\\" : \\\""
				+ request.getBody() + "\\\",  \\\"delaySeconds\\\" : \\\"" + request.getDelaySeconds() + "\\\"}\",";
		return getSchedulerBody(json, "TransferTask");
	}

	private String getSchedulerBody(String json, String taskName) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"description\" : null,");
		sb.append("\"predefined\" : false,");
		sb.append("\"spec\" : {");
		sb.append("\"runMode\" : \"DISTRIBUTED\",");
		sb.append("\"group\" : \"CONFIG\",");
		sb.append("\"name\" : \"" + taskName + "_" + UUID.randomUUID().toString().replace("-", ""));
		sb.append("\",\"splitterCode\" : null,");
		sb.append("\"runnerCode\" : \"" + taskName + "\",");
		sb.append(json);
		sb.append("\"workflowTaskContext\" : null,");
		sb.append("\"expiration\" : null,");
		sb.append("\"expirationUnit\" :\"SECONDS\",");
		sb.append("\"retriesOnFailure\" : 0,");
		sb.append("\"retryInterval\" : 1000,");
		sb.append("\"feedback\" : true");

		sb.append("},");
		sb.append("\"trigger\" : {");
		sb.append("\"startTime\" : null,");
		sb.append("\"endTime\" : null,");
		sb.append("\"delay\" : 0,");
		sb.append("\"delayUnit\" : \"SECONDS\",");
		sb.append("\"interval\" : 0,");
		sb.append("\"intervalUnit\" : \"SECONDS\",");
		sb.append("\"repeatCount\" : -1,");
		sb.append("\"cron\" : null");
		sb.append("}");
		sb.append("}");
		return sb.toString();
	}

}
