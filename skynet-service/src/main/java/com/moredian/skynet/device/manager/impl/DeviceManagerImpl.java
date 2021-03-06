package com.moredian.skynet.device.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.moredian.bee.common.exception.BizAssert;
import com.moredian.bee.common.utils.ExceptionUtils;
import com.moredian.bee.common.utils.JsonUtils;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.convertor.PaginationConvertor;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.bee.rmq.EventBus;
import com.moredian.bee.rmq.annotation.Subscribe;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.idgenerator.service.IdgeneratorService;
import com.moredian.skynet.common.model.msg.BindDefaultSubjectMsg;
import com.moredian.skynet.common.model.msg.DeleteGroupRelationDataMsg;
import com.moredian.skynet.common.model.msg.RefreshDeviceConfigMsg;
import com.moredian.skynet.common.model.msg.SyncLocalDeployMsg;
import com.moredian.skynet.device.domain.Device;
import com.moredian.skynet.device.domain.DeviceConfig;
import com.moredian.skynet.device.domain.DeviceQueryCondition;
import com.moredian.skynet.device.enums.DeviceAction;
import com.moredian.skynet.device.enums.DeviceErrorCode;
import com.moredian.skynet.device.enums.DeviceType;
import com.moredian.skynet.device.manager.CloudeyeDeviceSyncProxy;
import com.moredian.skynet.device.manager.DeviceConfigManager;
import com.moredian.skynet.device.manager.DeviceGroupManager;
import com.moredian.skynet.device.manager.DeviceManager;
import com.moredian.skynet.device.manager.DeviceMatchManager;
import com.moredian.skynet.device.mapper.DeviceConfigMapper;
import com.moredian.skynet.device.mapper.DeviceGroupMapper;
import com.moredian.skynet.device.mapper.DeviceMapper;
import com.moredian.skynet.device.model.DeviceInfo;
import com.moredian.skynet.device.request.DeviceActiveRequest;
import com.moredian.skynet.device.request.DeviceAddRequest;
import com.moredian.skynet.device.request.DeviceQueryRequest;
import com.moredian.skynet.device.request.DeviceUpdateRequest;
import com.moredian.skynet.device.response.DeviceActiveResponse;
import com.moredian.skynet.device.utils.HttpInvoker;
import com.moredian.skynet.device.utils.HttpInvokerResponse;
import com.moredian.skynet.org.enums.BizType;
import com.moredian.skynet.org.enums.DeviceStatus;
import com.moredian.skynet.org.model.OrgInfo;
import com.moredian.skynet.org.service.OrgService;
import com.moredian.skynet.org.service.PositionService;
import com.xier.guard.accessKey.dto.AccessKeyDto;
import com.xier.guard.accessKey.service.UserAccessKeyService;

@Service
public class DeviceManagerImpl implements DeviceManager {
	
	private static final Logger logger = LoggerFactory.getLogger(DeviceManagerImpl.class);
	
	@Autowired
	private DeviceGroupMapper deviceGroupMapper;
	@Autowired
	private DeviceMapper deviceMapper;
	@Autowired
	private DeviceConfigMapper deviceConfigMapper;
	@SI
	private IdgeneratorService idgeneratorService;

	@Autowired
	private DeviceMatchManager deviceMatchManager;
	@Autowired
	private DeviceGroupManager deviceGroupManager;
	@Autowired
	private CloudeyeDeviceSyncProxy cloudeyeDeviceSyncProxy;
	@SI
	private PositionService positionService;
	@SI
	private OrgService orgService;
	@Reference
    protected UserAccessKeyService userAccessKeyService;
	@Autowired
	private DeviceConfigManager deviceConfigManager;
	
	@Override
	public Device getDeviceById(Long orgId, Long deviceId) {
		BizAssert.notNull(orgId);
		BizAssert.notNull(deviceId);
		return deviceMapper.load(orgId, deviceId);
	}

	@Override
	public Device getDeviceBySn(String deviceSn) {
		BizAssert.notBlank(deviceSn, "deviceSn must not be null");
		return deviceMapper.loadBySnOnly(deviceSn);
	}

	@Override
	public boolean updateXmlConfig(Long orgId, Long deviceId, String xml) {
		BizAssert.notNull(deviceId, "deviceId must not be null");
		BizAssert.notBlank(xml, "xml must not be blank");
		
		Device device = deviceMapper.load(orgId, deviceId);
		
		DeviceConfig deviceConfig = new DeviceConfig();
		Long id = idgeneratorService.getNextIdByTypeName("com.moredian.skynet.device.DeviceConfig").getData();
		deviceConfig.setDeviceConfigId(id);
		deviceConfig.setDeviceSn(device.getDeviceSn());
		deviceConfig.setXmlConfig(xml);
		deviceConfigMapper.insertOrUpdate(deviceConfig);
		
		return true;
	}

	@Override
	public String getXmlConfig(String deviceSn) {
		DeviceConfig deviceConfig = deviceConfigMapper.load(deviceSn);
		if(deviceConfig == null) return "";
		return deviceConfig.getXmlConfig();
	}
	
	@Subscribe
	public void receiveGroupDeleteMsg(DeleteGroupRelationDataMsg msg) {
		logger.info("收到删除组的消息: "+JsonUtils.toJson(msg));
		deviceGroupMapper.deleteByGroup(msg.getOrgId(), msg.getGroupId());
	}
	
	private Device deviceAddRequestToDeviceForOld(DeviceAddRequest request) {
		
		Device existDevice = deviceMapper.loadBySnOnly(request.getDeviceSn());
		if(existDevice != null) ExceptionUtils.throwException(DeviceErrorCode.DEVICE_EXIST, String.format(DeviceErrorCode.DEVICE_EXIST.getMessage(), request.getDeviceSn()));
		
		Device device = new Device();
		Long id = idgeneratorService.getNextIdByTypeName("com.moredian.skynet.device.Device").getData();
		device.setDeviceId(id);
		device.setOrgId(request.getOrgId());
		device.setPosition(request.getPosition());
		device.setDeviceType(request.getDeviceType());
		if(StringUtils.isBlank(request.getDeviceName())) {
			device.setDeviceName(DeviceType.getName(request.getDeviceType()));
		} else {
			device.setDeviceName(request.getDeviceName());
		}
		device.setDeviceSn(request.getDeviceSn());
		device.setStatus(DeviceStatus.UNACTIVE.getValue());
		return device;
	}
	
	@Override
	@Transactional
	public Long addDeviceForOld(DeviceAddRequest request) {
		
		BizAssert.notNull(request.getOrgId(), "orgId must not be null");
		BizAssert.notNull(request.getDeviceType(), "deviceType must not be null");
		
		Device device = this.deviceAddRequestToDeviceForOld(request);
		deviceMapper.insert(device);
		
		if(DeviceType.isSubjectNeedDevice(device.getDeviceType())) {
			
			orgService.enableBiz(device.getOrgId(), BizType.OPENDOOR.getValue());
			
			BindDefaultSubjectMsg msg = new BindDefaultSubjectMsg();
			msg.setOrgId(device.getOrgId());
			msg.setDeviceId(device.getDeviceId());
			EventBus.publish(msg);
		}
		
		return device.getDeviceId();
	}
	
	@Override
	@Transactional
	public String genActiveCode(Long orgId, Long deviceId) {
		// 生成一个激活码
        String random = "" + (long) ((Math.random() * 9 + 1) * 100000000000l);
        String activeCode = random.substring(0, 4) + "-" + random.substring(4, 8) + "-" + random.substring(8, 12);

        if (deviceMapper.loadByActiveCode(activeCode) == null) {
            deviceMapper.updateActiveCode(orgId, deviceId, activeCode);
            return activeCode;
        } else {
            return genActiveCode(orgId, deviceId); //激活码已存在则重新生成
        }
	}
	
	private Device deviceAddRequestToDevice(DeviceAddRequest request) {
		
		Device existDevice = deviceMapper.loadBySnOnly(request.getDeviceSn());
		if(existDevice != null) ExceptionUtils.throwException(DeviceErrorCode.DEVICE_EXIST, String.format(DeviceErrorCode.DEVICE_EXIST.getMessage(), request.getDeviceSn()));
		
		Device device = new Device();
		Long id = idgeneratorService.getNextIdByTypeName("com.moredian.skynet.device.Device").getData();
		device.setDeviceId(id);
		device.setOrgId(request.getOrgId());
		device.setPosition(request.getPosition());
		device.setDeviceType(request.getDeviceType());
		if(StringUtils.isBlank(request.getDeviceName())) {
			device.setDeviceName(DeviceType.getName(request.getDeviceType()));
		} else {
			device.setDeviceName(request.getDeviceName());
		}
		device.setDeviceSn(request.getDeviceSn());
		device.setActiveTime(new Date());
		device.setStatus(DeviceStatus.USABLE.getValue());
		return device;
	}
	
	private void enableBiz(Device device) {
		if(DeviceType.BOARD_ATTENDANCE.getValue() == device.getDeviceType()
				|| DeviceType.BOARD_BOX.getValue() == device.getDeviceType()
				|| DeviceType.BOARD_ATTENDANCE_DUALEYE.getValue() == device.getDeviceType()) {
			orgService.enableBiz(device.getOrgId(), BizType.RECOGNIZE.getValue());
			orgService.enableBiz(device.getOrgId(), BizType.OPENDOOR.getValue());
		}
		if(DeviceType.BOARD_VISITOR.getValue() == device.getDeviceType()) {
			orgService.enableBiz(device.getOrgId(), BizType.RECOGNIZE.getValue());
			orgService.enableBiz(device.getOrgId(), BizType.VISITOR.getValue());
		}
		if(DeviceType.BOARD_GATE.getValue() == device.getDeviceType()) {
			orgService.enableBiz(device.getOrgId(), BizType.RECOGNIZE.getValue());
		}
	}

	@Override
	@Transactional
	public Long addDevice(DeviceAddRequest request) {
		
		BizAssert.notNull(request.getOrgId(), "orgId must not be null");
		BizAssert.notNull(request.getDeviceType(), "deviceType must not be null");
		BizAssert.notBlank(request.getDeviceSn(), "deviceSn must not be blank");
		
		Device device = this.deviceAddRequestToDevice(request);
		deviceMapper.insert(device);
		
		// 设备绑定默认群组
		deviceGroupManager.addDefaultDeviceGroup(device.getOrgId(), device.getDeviceId());
		
		this.enableBiz(device);
		
		// 设备同步云眼
		cloudeyeDeviceSyncProxy.addCloudeyeDevice(device);
		
		if(DeviceType.isSubjectNeedDevice(device.getDeviceType())) {
			BindDefaultSubjectMsg msg = new BindDefaultSubjectMsg();
			msg.setOrgId(device.getOrgId());
			msg.setDeviceId(device.getDeviceId());
			EventBus.publish(msg);
		}
		
		return device.getDeviceId();
	}
	
	private Device requestToDomain(DeviceUpdateRequest request) {
		Device device = new Device();
		device.setDeviceId(request.getDeviceId());
		device.setOrgId(request.getOrgId());
		device.setDeviceName(request.getDeviceName());
		device.setPosition(request.getPosition());
		return device;
	}

	@Override
	public boolean updateDevice(DeviceUpdateRequest request) {
		BizAssert.notNull(request.getDeviceId(), "deviceId must not be null");
		BizAssert.notNull(request.getOrgId(), "orgId must not be null");
		
		deviceMapper.update(this.requestToDomain(request));
		
		return true;
	}

	@Override
	@Transactional
	public boolean deleteDeviceById(Long orgId, Long deviceId) {
		
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notNull(deviceId, "deviceId must not be null");
		
		Device device = deviceMapper.load(orgId, deviceId);
		if(DeviceType.BOARD_BOX.getValue() == device.getDeviceType()) {
			List<Long> matchCameraIds = deviceMatchManager.findCameraIdByBoxId(orgId, deviceId);
			if(CollectionUtils.isNotEmpty(matchCameraIds)) {
				ExceptionUtils.throwException(DeviceErrorCode.DEVICE_IN_BINDING, DeviceErrorCode.DEVICE_IN_BINDING.getMessage());
			}
			
		}
		
		deviceMapper.deleteById(orgId, deviceId);
		deviceGroupManager.deleteByDevice(orgId, deviceId);
		deviceConfigManager.delete(device.getDeviceSn());
		// 5) TODO 设备删除消息广播到业务系统
		
		return true;
	}
	
	public static DeviceQueryCondition deviceQueryRequestToDeviceQueryCondition(DeviceQueryRequest request) {
		DeviceQueryCondition condition = new DeviceQueryCondition();
		
		condition.setOrgId(request.getOrgId());
		condition.setPositionId(request.getPositionId());
		condition.setProviderType(request.getProviderType());
		condition.setDeviceType(request.getDeviceType());
		condition.setDeviceName(request.getDeviceName());
		condition.setDeviceSn(request.getDeviceSn());
		condition.setKeywords(request.getKeywords());
		condition.setDeviceTypeList(request.getDeviceTypeList());
		
		List<Integer> statusList = new ArrayList<>();
		if(request.getStatus() != null) {
			statusList.add(request.getStatus());
		} else {
			for(DeviceStatus deviceStatus : DeviceStatus.values()) {
				statusList.add(deviceStatus.getValue());
			}
		}
		
		condition.setStatusList(statusList);
		
		return condition;
	}
	
	private PaginationDomain<Device> paginationDeviceInfoToPaginationDevice(Pagination<DeviceInfo> fromPagination) {
		PaginationDomain<Device> toPagination = PaginationConvertor.paginationToPaginationDomain(fromPagination, new PaginationDomain<Device>());
		if (toPagination == null)
			return null;
		toPagination.setData(deviceInfoListToDeviceList(fromPagination.getData()));
		return toPagination;
	}
	
	private List<Device> deviceInfoListToDeviceList(List<DeviceInfo> deviceInfoList) {
		if (deviceInfoList == null) return null;
		
		List<Device> deviceList = new ArrayList<>();
		for(DeviceInfo deviceInfo : deviceInfoList) {
			deviceList.add(deviceInfoToDevice(deviceInfo));
		}
		
		return deviceList;
	}
	
	private Device deviceInfoToDevice(DeviceInfo deviceInfo) {
		if (deviceInfo == null) return null;
		Device device = new Device();
		device.setDeviceId(deviceInfo.getDeviceId());
		device.setOrgId(deviceInfo.getOrgId());
		device.setPosition(deviceInfo.getPosition());
		device.setDeviceType(deviceInfo.getDeviceType());
		device.setDeviceName(deviceInfo.getDeviceName());
		device.setDeviceSn(deviceInfo.getDeviceSn());
		device.setActiveTime(deviceInfo.getActiveTime());
		device.setStatus(deviceInfo.getStatus());
		device.setGmtCreate(deviceInfo.getGmtCreate());
		return device;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginationDomain<Device> findPaginationDevice(DeviceQueryRequest request, Pagination<DeviceInfo> pagination) {
		
		BizAssert.notNull(request.getOrgId(), "orgId must not be null");
		
		DeviceQueryCondition condition = deviceQueryRequestToDeviceQueryCondition(request);
		PaginationDomain<Device> devicePagination = paginationDeviceInfoToPaginationDevice(pagination);
		
		return this.getPagination(devicePagination, condition);
		
	}
	
	@Override
	public int getCount(DeviceQueryRequest request) {
		BizAssert.notNull(request.getOrgId(), "orgId must not be null");
		DeviceQueryCondition condition = deviceQueryRequestToDeviceQueryCondition(request);
		return deviceMapper.getTotalCountByCondition(condition);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected  PaginationDomain getPagination(PaginationDomain pagination, DeviceQueryCondition condition) {
        int totalCount = (Integer) deviceMapper.getTotalCountByCondition(condition);
        pagination.setTotalCount(totalCount);
        if (totalCount > 0) {
        	condition.setStartRow(pagination.getStartRow());
        	condition.setPageSize(pagination.getPageSize());
        	pagination.setData(deviceMapper.findPaginationByCondition(condition));
        }
        return pagination;
    }
	
	private String genDeviceSn(DeviceActiveRequest request){
        return request.getCpu()+"-"+request.getMac();
    }
	
	private void doFirstActive(DeviceActiveRequest request, Device device, String deviceSn) {
		Device existDevice = deviceMapper.loadBySnOnly(deviceSn);
		if(existDevice != null) {
			OrgInfo org = orgService.getOrgInfo(existDevice.getOrgId());
			ExceptionUtils.throwException(DeviceErrorCode.ACTIVE_CONFLICT_ERROR, String.format(DeviceErrorCode.ACTIVE_CONFLICT_ERROR.getMessage(), org.getOrgName()));
		}
		
		if(DeviceType.BOARD_ATTENDANCE.getValue() == device.getDeviceType()
				|| DeviceType.BOARD_ATTENDANCE_DUALEYE.getValue() == device.getDeviceType()) {
			orgService.enableBiz(device.getOrgId(), BizType.OPENDOOR.getValue());
		}
		
		// 更新设备信息
		deviceMapper.updateByActive(device.getOrgId(), device.getDeviceId(), deviceSn, request.getVersion(), DeviceStatus.USABLE.getValue());
		
		// 设备绑定默认群组
		deviceGroupManager.addDefaultDeviceGroup(device.getOrgId(), device.getDeviceId());
		
		// 设备同步云眼
		cloudeyeDeviceSyncProxy.addCloudeyeDevice(device);
		
		SyncLocalDeployMsg msg = new SyncLocalDeployMsg();
		msg.setOrgId(device.getOrgId());
		msg.setDeviceAction(DeviceAction.ACTIVE.getValue());
		msg.setDeviceId(device.getDeviceId());
		msg.setDeviceType(device.getDeviceType());
		msg.setDeviceSn(device.getDeviceSn());
		EventBus.publish(msg); // 发出设备操作消息, 消息接受方负责调整布控实例并同步云眼Huber配置
		logger.info("发出MQ消息[设备被激活]:" + JsonUtils.toJson(msg));
		
		// 发设备消息
		if(DeviceType.BOARD_ATTENDANCE.getValue() == device.getDeviceType()
				|| DeviceType.BOARD_ATTENDANCE_DUALEYE.getValue() == device.getDeviceType()) {
			RefreshDeviceConfigMsg eventMsg = new RefreshDeviceConfigMsg();
			eventMsg.setOrgId(device.getOrgId());
			eventMsg.setDeviceSn(deviceSn);
	        EventBus.publish(msg);
	        logger.info("发出MQ消息[生成设备配置]:" + JsonUtils.toJson(eventMsg));
		}
	}

	@Override
	@Transactional
	public boolean activeDevice(Long orgId, String deviceSn) {

		Device device = deviceMapper.loadBySn(orgId, deviceSn);

		deviceMapper.updateStatusBySn(device.getOrgId(), device.getDeviceSn(), DeviceStatus.USABLE.getValue());

/*		// 记录设备添加增量日志(后续由定时任务读取设备增量日志进行布控控制)
		DeviceLog deviceLog = new DeviceLog();
		deviceLog.setDeviceLogId(this.getDeviceLogId());
		deviceLog.setOrgId(device.getOrgId());
		deviceLog.setDeviceAction(DeviceAction.ACTIVE.getValue());
		deviceLog.setDeviceId(device.getDeviceId());
		deviceLog.setDeviceType(device.getDeviceType());
		deviceLog.setDeviceSn(device.getDeviceSn());
		deviceLogManager.addDeviceLog(deviceLog);*/

		return true;
	}

	@Override
	@Transactional
	public DeviceActiveResponse activeDevice(DeviceActiveRequest request) {
		
		BizAssert.notNull(request.getActiveCode());
		BizAssert.notBlank(request.getIp());
		BizAssert.notBlank(request.getCpu());
		BizAssert.notBlank(request.getMac());
		BizAssert.notNull(request.getVersion());
		BizAssert.notNull(request.getDeviceType());
		
		Device device = deviceMapper.loadByActiveCode(request.getActiveCode());
		if(device == null) ExceptionUtils.throwException(DeviceErrorCode.INVALID_ACTIVE_EXCEPTION, DeviceErrorCode.INVALID_ACTIVE_EXCEPTION.getMessage());
		
		String deviceSn = this.genDeviceSn(request);
		if(StringUtils.isNotBlank(device.getDeviceSn()) && !deviceSn.equals(device.getDeviceSn())) { // 激活码只能在原来设备上激活
			ExceptionUtils.throwException(DeviceErrorCode.ACTIVE_REFUSED, DeviceErrorCode.ACTIVE_REFUSED.getMessage());
		}
		
		if(DeviceStatus.UNACTIVE.getValue() == device.getStatus()) {
			this.doFirstActive(request, device, deviceSn);
		} else {
			// 二次激活
		}
		
		return this.buildResponse(request, device, deviceSn);
		
	}

	public static void main(String [] args){

		String u2="http://192.168.4.73:8087/services/inventory/devices/create?sn=yrhftest12388&mac=000011112222&orgId=123456789&deviceType=610&softwareVersion=18.0.0&simulated=true";


		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		headerMap.put("Accept", "text/plain, application/json, application/*+json, */*");
		Map<String, String> urlVaMap = new HashMap<String, String>();
//        urlVaMap.put("sn","yrhftest12355");
//        urlVaMap.put("mac","000511112222");
//        urlVaMap.put("orgId","12345678965");
//        urlVaMap.put("deviceType","61");
//        urlVaMap.put("softwareVersion","1.0.2");
//        urlVaMap.put("simulated","true");
		HttpInvokerResponse reponse = HttpInvoker.invokerPost(u2, headerMap, urlVaMap, "");
		if (reponse == null) {
			System.out.println("558547");
		}
		if (reponse.getResponseCode() == 200) {
			if (reponse != null && reponse.getBody() != null) {
				// 需要做些判断
			}
		}
	}
	
	private DeviceActiveResponse buildResponse(DeviceActiveRequest request, Device device, String deviceSn) {
		DeviceActiveResponse response = new DeviceActiveResponse();
		response.setDeviceId(device.getDeviceId());
		response.setOrgId(device.getOrgId());
		response.setDeviceName(device.getDeviceName());
		response.setDeviceType(device.getDeviceType());
		response.setIp(request.getIp());
		response.setMac(request.getMac());
		response.setPosition(device.getPosition());
		response.setDeviceSn(deviceSn);
		response.setCpu(request.getCpu());
		response.setAccessKeySecret(this.getUserAccessKey(device.getDeviceType(), deviceSn));
		response.setGmtCreate(device.getGmtCreate());
		response.setGmtModify(device.getGmtModify());
		return response;
	}
	
	public String getUserAccessKey(Integer deviceType, String deviceSn) {
        if (deviceType == null) return null; 
        com.xier.sesame.common.rpc.ServiceResponse<AccessKeyDto> sr = userAccessKeyService.issuDeviceAccessKeyAutoRevoke(deviceSn);
        if (sr.isSuccess() && sr.isExistData()) {
           return sr.getData().getAccessKeySecret();
        } else {
           // ExceptionUtils.throwException(sr.getErrorContext(), sr.getErrorContext().getMessage());
			//ExceptionUtils.throwException(sr.getErrorContext().getCode(),sr.getErrorContext().getMessage());
			logger.error(sr.getErrorContext().getMessage());
        }
        return null;
    }

	@Override
	public List<String> findDeviceNameByIds(List<Long> deviceIdList) {
		return deviceMapper.findDeviceNameByIds(deviceIdList);
	}

	@Override
	public List<Device> findDeviceByType(Long orgId, Integer deviceType) {
		BizAssert.notNull(orgId);
		BizAssert.notNull(deviceType);
		return deviceMapper.findDeviceByType(orgId, deviceType);
	}

	@Override
	public List<Device> findDeviceByTypes(Long orgId, List<Integer> deviceTypes) {
		BizAssert.notNull(orgId);
		BizAssert.notEmpty(deviceTypes);
		return deviceMapper.findDeviceByTypes(orgId, deviceTypes);
	}

	@Override
	public List<Long> findDeviceIdByType(Long orgId, Integer deviceType) {
		BizAssert.notNull(orgId);
		BizAssert.notNull(deviceType);
		return deviceMapper.findDeviceIdByType(orgId, deviceType);
	}

}
