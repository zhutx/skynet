package com.moredian.fishnet.org.service.adapter.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.org.domain.Org;
import com.moredian.fishnet.org.manager.OrgManager;
import com.moredian.fishnet.org.request.OrgUpdateRequest;
import com.moredian.fishnet.org.service.adapter.OrgService;
import com.moredian.fishnet.org.service.adapter.dto.OrgDto;

@Component("adapterOrgService")
@SI
public class OrgServiceImpl implements OrgService {
	
	private static final Logger logger = LoggerFactory.getLogger(OrgServiceImpl.class);
	
	@Autowired
	private OrgManager orgManager;
	
	private OrgDto orgToOrgDto(Org org) {
		if(org == null) return null;
		OrgDto orgDto = BeanUtils.copyProperties(OrgDto.class, org);
		// set diff props
		orgDto.setDetailedAddress(org.getAddress());
		orgDto.setRemark(org.getMemo());
		return orgDto;
	}
	
	@Override
	public ServiceResponse<OrgDto> getOrgById(Long orgId) {
		logger.debug("###################getOrgById###################");
		Org org = orgManager.getOrgById(orgId);
		return new ServiceResponse<OrgDto>(true, null, orgToOrgDto(org));
	}
	
	private OrgUpdateRequest buildRequest(OrgDto orgDto) {
		OrgUpdateRequest request = BeanUtils.copyProperties(OrgUpdateRequest.class, orgDto);
		// set diff props
		request.setAddress(orgDto.getDetailedAddress());
		request.setMemo(orgDto.getRemark());
		return request;
	}

	@Override
	public ServiceResponse<Boolean> updateOrgSelective(OrgDto orgDto) {
		logger.debug("###################updateOrgSelective###################");
		boolean result = orgManager.updateOrg(buildRequest(orgDto));
		return new ServiceResponse<Boolean>(true, null, result);
	}

}
