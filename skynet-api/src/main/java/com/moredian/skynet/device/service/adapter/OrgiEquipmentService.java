package com.moredian.skynet.device.service.adapter;

import java.util.List;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.skynet.device.service.adapter.dto.OrgiEquipmentDto;
import com.moredian.skynet.device.service.adapter.dto.SubOrgDeviceRelationDto;
import com.moredian.skynet.device.service.adapter.request.HasActiveEquipmentRequest;

public interface OrgiEquipmentService {
	
	List<SubOrgDeviceRelationDto> getSubOrgDeviceRelationByDeviceId(long orgId, long deviceId);
	
	ServiceResponse<Pagination<OrgiEquipmentDto>> getPaginationOrgiEquipment(Pagination<OrgiEquipmentDto> paginationDto,OrgiEquipmentDto orgiEquipmentDto);
	
	ServiceResponse<Boolean> hasActiveEquipment(HasActiveEquipmentRequest request);
	
	ServiceResponse<Integer> getOrgiEquipmentCount(OrgiEquipmentDto orgiEquipmentDto);
	
}
