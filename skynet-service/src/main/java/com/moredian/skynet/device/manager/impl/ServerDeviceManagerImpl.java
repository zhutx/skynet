package com.moredian.skynet.device.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moredian.bee.common.exception.BizAssert;
import com.moredian.bee.common.utils.ExceptionUtils;
import com.moredian.bee.common.utils.JsonUtils;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.convertor.PaginationConvertor;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.idgenerator.service.IdgeneratorService;
import com.moredian.skynet.device.domain.Device;
import com.moredian.skynet.device.domain.DeviceQueryCondition;
import com.moredian.skynet.device.enums.DeviceErrorCode;
import com.moredian.skynet.device.enums.DeviceStatus;
import com.moredian.skynet.device.enums.DeviceType;
import com.moredian.skynet.device.manager.CloudeyeDeviceSyncProxy;
import com.moredian.skynet.device.manager.ServerDeviceManager;
import com.moredian.skynet.device.mapper.DeviceMapper;
import com.moredian.skynet.device.model.ServerDeviceExtendsInfo;
import com.moredian.skynet.device.model.ServerDeviceInfo;
import com.moredian.skynet.device.request.ServerDeviceAddRequest;
import com.moredian.skynet.device.request.ServerDeviceQueryRequest;
import com.moredian.skynet.device.request.ServerDeviceUpdateRequest;
import com.moredian.skynet.org.service.PositionService;

@Service
public class ServerDeviceManagerImpl implements ServerDeviceManager {
	
	@Autowired
	private DeviceMapper deviceMapper;
	@Autowired
	private CloudeyeDeviceSyncProxy cloudeyeDeviceSyncFacade;
	@SI
	private PositionService positionService;
	@SI
	private IdgeneratorService idgeneratorService;
	
	
	private Device serverDeviceAddRequestToDevice(ServerDeviceAddRequest request) {
		if (request == null) return null;
		Device device = new Device();
		Long id = idgeneratorService.getNextIdByTypeName("com.moredian.skynet.device.Device").getData();
		device.setDeviceId(id);
		device.setOrgId(request.getOrgId());
		if(request.getDeviceName() == null) {
			device.setDeviceName(DeviceType.SERVER_FIREANT.getName());
		} else {
			device.setDeviceName(request.getDeviceName());
		}
		device.setDeviceType(DeviceType.SERVER_FIREANT.getValue());
		
		ServerDeviceExtendsInfo extendsInfo = new ServerDeviceExtendsInfo();
		extendsInfo.setC_host(request.getHost());
		extendsInfo.setC_port(request.getPort());
		extendsInfo.setC_outer_host(request.getOuterHost());
		extendsInfo.setC_outer_port(request.getOuterPort());
		extendsInfo.setC_tp_host(request.getTpHost());
		extendsInfo.setC_tp_port(request.getTpPort());
		extendsInfo.setC_rd_maxorgnum(request.getRdMaxorgnum());
		extendsInfo.setC_rd_maxpersonnum(request.getRdMaxpersonnum());
		
		device.setExtendsInfo(JsonUtils.toJson(extendsInfo));
		device.setStatus(DeviceStatus.UNACTIVE.getValue());
		return device;
	}
	
	private void deviceAutoActive(Device device){
		String deviceSn = RandomStringUtils.randomNumeric(20);
		device.setDeviceSn(deviceSn);
		device.setStatus(DeviceStatus.USABLE.getValue());
		device.setActiveTime(new Date());
		deviceMapper.update(device);
	}

	@Override
	@Transactional
	public Device addDevice(ServerDeviceAddRequest request) {
		BizAssert.notNull(request.getOrgId(), "orgId must not be null");
		BizAssert.notBlank(request.getDeviceName(), "deviceName must not be blank");
		BizAssert.notNull(request.getHost(), "host must not be null");
		BizAssert.notNull(request.getPort(), "port must not be null");
		
		Device device = this.serverDeviceAddRequestToDevice(request);
		deviceMapper.insert(device);
		
		this.deviceAutoActive(device); //服务器设备直接自动激活
		
		// 同步设备至云眼
		cloudeyeDeviceSyncFacade.addCloudeyeDevice(device);
		
		return device;
	}
	
	private Device serverDeviceUpdateRequestToDevice(ServerDeviceUpdateRequest request) {
		Device device = deviceMapper.load(request.getOrgId(), request.getDeviceId());
		if(device == null) ExceptionUtils.throwException(DeviceErrorCode.DEVICE_NOT_EXIST, String.format(DeviceErrorCode.DEVICE_NOT_EXIST.getMessage(), request.getDeviceId()));
		
		device.setDeviceName(request.getDeviceName());
		
		ServerDeviceExtendsInfo extendsInfo = new ServerDeviceExtendsInfo();
		extendsInfo.setC_host(request.getHost());
		extendsInfo.setC_port(request.getPort());
		extendsInfo.setC_outer_host(request.getOuterHost());
		extendsInfo.setC_outer_port(request.getOuterPort());
		extendsInfo.setC_tp_host(request.getTpHost());
		extendsInfo.setC_tp_port(request.getTpPort());
		extendsInfo.setC_rd_maxorgnum(request.getRdMaxorgnum());
		extendsInfo.setC_rd_maxpersonnum(request.getRdMaxpersonnum());
		
		device.setExtendsInfo(JsonUtils.toJson(extendsInfo));
		return device;
	}

	@Override
	@Transactional
	public boolean updateDevice(ServerDeviceUpdateRequest request) {
		BizAssert.notNull(request.getOrgId(), "orgId must not be null");
		BizAssert.notNull(request.getDeviceId(), "deviceId must not be null");
		
		Device device = this.serverDeviceUpdateRequestToDevice(request);
		
		deviceMapper.update(device);
		
		// 同步修改云眼设备
		cloudeyeDeviceSyncFacade.updateCloudeyeDevice(device);
		
		return true;
	}

	@Override
	@Transactional
	public boolean deleteDeviceById(Long orgId, Long deviceId) {
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notNull(deviceId, "deviceId must not be null");
		
		Device device = deviceMapper.load(orgId, deviceId);
		if(device == null) ExceptionUtils.throwException(DeviceErrorCode.DEVICE_NOT_EXIST, String.format(DeviceErrorCode.DEVICE_NOT_EXIST.getMessage(), deviceId));
		
		deviceMapper.deleteById(orgId, deviceId);
	    
		// 同步删除云眼设备
		cloudeyeDeviceSyncFacade.deleteCloudeyeDevice(device);
		
		return true;
	}

	@Override
	@Transactional
	public boolean deleteDeviceBySn(Long orgId, String deviceSn) {
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notBlank(deviceSn, "deviceSn must not be blank");
		
		Device device = deviceMapper.loadBySn(orgId, deviceSn);
		if(device == null) ExceptionUtils.throwException(DeviceErrorCode.DEVICE_NOT_EXIST, String.format(DeviceErrorCode.DEVICE_NOT_EXIST.getMessage(), deviceSn));
		
		deviceMapper.deleteBySn(orgId, deviceSn);
		
		// 同步删除云眼设备
		cloudeyeDeviceSyncFacade.deleteCloudeyeDevice(device);
		
		return true;
	}

	@Override
	public Device getDeviceById(Long orgId, Long deviceId) {
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notNull(deviceId, "deviceId must not be null");
		return deviceMapper.load(orgId, deviceId);
	}

	@Override
	public Device getDeviceBySn(Long orgId, String deviceSn) {
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notBlank(deviceSn, "deviceSn must not be null");
		return deviceMapper.loadBySn(orgId, deviceSn);
	}

	@Override
	public Device getDeviceBySn(String deviceSn) {
		BizAssert.notBlank(deviceSn, "deviceSn must not be null");
		return deviceMapper.loadBySnOnly(deviceSn);
	}
	
	private static DeviceQueryCondition serverDeviceQueryRequestToDeviceQueryCondition(ServerDeviceQueryRequest request) {
		DeviceQueryCondition condition = new DeviceQueryCondition();
		
		condition.setOrgId(request.getOrgId());
		condition.setDeviceName(request.getDeviceName());
		condition.setDeviceSn(request.getDeviceSn());
		
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
	
	private Device serverDeviceInfoToDevice(ServerDeviceInfo serverDeviceInfo) {
		if (serverDeviceInfo == null) return null;
		Device device = new Device();
		device.setDeviceId(serverDeviceInfo.getDeviceId());
		device.setOrgId(serverDeviceInfo.getOrgId());
		device.setDeviceName(serverDeviceInfo.getDeviceName());
		device.setDeviceSn(serverDeviceInfo.getDeviceSn());
		device.setActiveTime(serverDeviceInfo.getActiveTime());
		device.setDeviceSn(serverDeviceInfo.getDeviceSn());
		device.setStatus(serverDeviceInfo.getStatus());
		device.setGmtCreate(serverDeviceInfo.getGmtCreate());
		return device;
	}
	
	private List<Device> serverDeviceInfoListToDeviceList(List<ServerDeviceInfo> serverDeviceInfoList) {
		if (serverDeviceInfoList == null) return null;
		
		List<Device> deviceList = new ArrayList<>();
		for(ServerDeviceInfo serverDeviceInfo : serverDeviceInfoList) {
			deviceList.add(serverDeviceInfoToDevice(serverDeviceInfo));
		}
		
		return deviceList;
	}
	
	private PaginationDomain<Device> paginationServerDeviceInfoToPaginationDevice(Pagination<ServerDeviceInfo> fromPagination) {
		PaginationDomain<Device> toPagination = PaginationConvertor.paginationToPaginationDomain(fromPagination, new PaginationDomain<Device>());
		if (toPagination == null)
			return null;
		toPagination.setData(serverDeviceInfoListToDeviceList(fromPagination.getData()));
		return toPagination;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginationDomain<Device> findPaginationDevice(ServerDeviceQueryRequest request,
			Pagination<ServerDeviceInfo> pagination) {
		BizAssert.notNull(request.getOrgId(), "orgId must not be null");
		
		DeviceQueryCondition condition = serverDeviceQueryRequestToDeviceQueryCondition(request);
		PaginationDomain<Device> devicePagination = paginationServerDeviceInfoToPaginationDevice(pagination);
		
		return this.getPagination(devicePagination, condition);
	}
	
	@SuppressWarnings("rawtypes")
	public int getStartRow(PaginationDomain pagination) {
        if (pagination.getPageNo() > 1) {
            return (pagination.getPageSize() * (pagination.getPageNo() - 1));
        } else {
            return 0;
        }
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected  PaginationDomain getPagination(PaginationDomain pagination, DeviceQueryCondition condition) {
        int totalCount = (Integer) deviceMapper.getTotalCountByCondition(condition);
        pagination.setTotalCount(totalCount);
        if (totalCount > 0) {
        	condition.setStartRow(this.getStartRow(pagination));
        	condition.setPageSize(pagination.getPageSize());
        	pagination.setData(deviceMapper.findPaginationByCondition(condition));
        }
        return pagination;
    }

}
