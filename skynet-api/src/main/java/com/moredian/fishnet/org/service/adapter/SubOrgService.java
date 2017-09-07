package com.moredian.fishnet.org.service.adapter;

import java.util.List;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.fishnet.org.service.adapter.dto.SubOrgDto;

public interface SubOrgService {
	
	ServiceResponse<SubOrgDto> getSubOrgById(Long id, Long orgId);
	
	ServiceResponse<List<SubOrgDto>> getSubOrg(SubOrgDto subOrgDto);
	
	ServiceResponse<Long> addEquipmentAddressSubOrg(SubOrgDto subOrgDto);
	
	ServiceResponse<Integer> updateSubOrgSelective(SubOrgDto subOrgDto);
	
	ServiceResponse<SubOrgDto> getOneSubOrg(SubOrgDto subOrgDto);
	
	ServiceResponse<SubOrgDto> getVisitorSubOrg(Long orgId);
	
	ServiceResponse<List<SubOrgDto>> getSubOrgListByParentId(Long parentId, Long orgId);
	
	ServiceResponse<List<SubOrgDto>> getSubOrgBySubOrgName(String subOrgName, Long orgId, Integer subOrgType);

}
