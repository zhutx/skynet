package com.moredian.fishnet.device.service.adapter.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.device.domain.Device;
import com.moredian.fishnet.device.enums.DeviceStatus;
import com.moredian.fishnet.device.manager.DeviceGroupManager;
import com.moredian.fishnet.device.manager.DeviceManager;
import com.moredian.fishnet.device.model.DeviceInfo;
import com.moredian.fishnet.device.request.DeviceQueryRequest;
import com.moredian.fishnet.device.service.adapter.OrgiEquipmentService;
import com.moredian.fishnet.device.service.adapter.dto.OrgiEquipmentDto;
import com.moredian.fishnet.device.service.adapter.dto.SubOrgDeviceRelationDto;
import com.moredian.fishnet.device.service.adapter.request.HasActiveEquipmentRequest;
import com.moredian.fishnet.org.model.GroupInfo;
import com.moredian.fishnet.org.service.GroupService;

@SI
@Component("adapterOrgiEquipmentService")
public class OrgiEquipmentServiceImpl implements OrgiEquipmentService {
	
	private static final Logger logger = LoggerFactory.getLogger(OrgiEquipmentServiceImpl.class);
	
	@Autowired
	private DeviceManager deviceManager;
	@Autowired
	private DeviceGroupManager deviceGroupManager;
	@SI
	private GroupService groupService;
	
	private List<SubOrgDeviceRelationDto> groupInfoListToSubOrgDeviceRelationDtoList(List<GroupInfo> groupInfoList, Long deviceId) {
		List<SubOrgDeviceRelationDto> dtoList = new ArrayList<>();
		if(CollectionUtils.isEmpty(groupInfoList)) return dtoList;
		
		for(GroupInfo groupInfo : groupInfoList) {
			SubOrgDeviceRelationDto dto = new SubOrgDeviceRelationDto();
			dto.setOrgId(groupInfo.getOrgId());
			dto.setDeviceId(deviceId);
			dto.setSubOrgCode(groupInfo.getGroupCode());
			dto.setSubOrgId(groupInfo.getGroupId());
			dtoList.add(dto);
		}
		
		return dtoList;
	}

	@Override
	public List<SubOrgDeviceRelationDto> getSubOrgDeviceRelationByDeviceId(long orgId, long deviceId) {
		
		logger.debug("###################getSubOrgDeviceRelationByDeviceId###################");
		
		List<Long> groupIdList = deviceGroupManager.findGroupIdByDeviceId(orgId, deviceId);
		List<GroupInfo> groupInfoList = groupService.findGroupByIds(orgId, groupIdList);
		return groupInfoListToSubOrgDeviceRelationDtoList(groupInfoList, deviceId);
	}
	
	private List<OrgiEquipmentDto> deviceListToOrgiEquipmentDto(List<Device> deviceList) {
		List<OrgiEquipmentDto> dtoList = new ArrayList<>();
		if(CollectionUtils.isEmpty(deviceList)) return dtoList;
		for(Device device : deviceList) {
			OrgiEquipmentDto dto = new OrgiEquipmentDto();
			dto.setOrgiEquipmentId(device.getDeviceId());
			dto.setOrgId(device.getOrgId());
			dto.setSubOrgId(device.getPositionId());
			dto.setEquipmentName(device.getDeviceName());
			dto.setEquipmentType(device.getDeviceType());
			dto.setEquipmentSn(device.getDeviceSn());
			dto.setUniqueNumber(device.getDeviceSn());
			dto.setStatus(device.getStatus());
			dto.setActiveCode(device.getActiveCode());
			dto.setGmtActiveCode(device.getActiveTime());
			dto.setGmtCreate(device.getGmtCreate());
			dto.setGmtModify(device.getGmtModify());
			dtoList.add(dto);
		}
		return dtoList;
	}
	
	@Override
	public ServiceResponse<Integer> getOrgiEquipmentCount(OrgiEquipmentDto orgiEquipmentDto) {
		logger.debug("###################getOrgiEquipmentCount###################");
		DeviceQueryRequest request = new DeviceQueryRequest();
		request.setOrgId(orgiEquipmentDto.getOrgId());
		request.setPositionId(orgiEquipmentDto.getSubOrgId());
		request.setProviderType(orgiEquipmentDto.getCompanyType());
		request.setDeviceName(orgiEquipmentDto.getEquipmentName());
		request.setDeviceType(orgiEquipmentDto.getEquipmentType());
		request.setDeviceSn(orgiEquipmentDto.getUniqueNumber());
		request.setStatus(orgiEquipmentDto.getStatus());
		
		int count = deviceManager.getCount(request);
		return new ServiceResponse<Integer>(true, null, count);
	}

	private Pagination<OrgiEquipmentDto> paginationDeviceToPaginationOrgiEquipmentDto(PaginationDomain<Device> paginationDevice) {
		Pagination<OrgiEquipmentDto> paginationDto = new Pagination<>();
		paginationDto.setPageNo(paginationDevice.getPageNo());
		paginationDto.setPageSize(paginationDevice.getPageSize());
		paginationDto.setTotalCount(paginationDevice.getTotalCount());
		
		paginationDto.setData(deviceListToOrgiEquipmentDto(paginationDevice.getData()));
		
		return paginationDto;
	}

	@Override
	public ServiceResponse<Pagination<OrgiEquipmentDto>> getPaginationOrgiEquipment(
			Pagination<OrgiEquipmentDto> paginationDto, OrgiEquipmentDto orgiEquipmentDto) {
		logger.debug("###################getPaginationOrgiEquipment###################");
		
		DeviceQueryRequest request = new DeviceQueryRequest();
		request.setOrgId(orgiEquipmentDto.getOrgId());
		List<Integer> deviceTypeList = null;
		if(orgiEquipmentDto.getEquipmentType() != null) {
			deviceTypeList = new ArrayList<>();
			deviceTypeList.add(orgiEquipmentDto.getEquipmentType());
			request.setDeviceTypeList(deviceTypeList);
		}
		request.setDeviceSn(orgiEquipmentDto.getMacAddress());
		
		Pagination<DeviceInfo> pagination = new Pagination<>();
		pagination.setPageNo(paginationDto.getPageNo());
		pagination.setPageSize(paginationDto.getPageSize());
		
		PaginationDomain<Device> paginationDevice = deviceManager.findPaginationDevice(request, pagination);
		
		return new ServiceResponse<>(true ,null, paginationDeviceToPaginationOrgiEquipmentDto(paginationDevice));
	}

	@Override
	public ServiceResponse<Boolean> hasActiveEquipment(HasActiveEquipmentRequest request) {
		logger.debug("###################hasActiveEquipment###################");
		List<Device> deviceList = deviceManager.findDeviceByType(request.getOrgId(), request.getEquipmentType());
		if(CollectionUtils.isEmpty(deviceList)) {
			return new ServiceResponse<Boolean>(true, null, false);
		} else {
			boolean result = false;
			for(Device device : deviceList) {
				if(device.getStatus() != DeviceStatus.USABLE.getValue()) continue;
				result = true;
				break;
			}
			return new ServiceResponse<Boolean>(true, null, result);
		}
	}

}
