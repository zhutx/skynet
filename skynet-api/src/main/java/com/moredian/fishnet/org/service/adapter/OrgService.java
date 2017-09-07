package com.moredian.fishnet.org.service.adapter;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.fishnet.org.service.adapter.dto.OrgDto;

public interface OrgService {
	
	ServiceResponse<Boolean> updateOrgSelective(OrgDto orgDto);
	
	ServiceResponse<OrgDto> getOrgById(Long orgId);

}
