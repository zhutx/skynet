package com.moredian.fishnet.org.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.org.manager.DingOrgManager;
import com.moredian.fishnet.org.request.OrgEnterpriseBind;
import com.moredian.fishnet.org.request.OrgEnterpriseNotBindRequest;
import com.moredian.fishnet.org.service.DingOrgService;

@SI
public class DingOrgServiceImpl implements DingOrgService {
	
	private static final Logger logger = LoggerFactory.getLogger(DingOrgServiceImpl.class);
	
	@Autowired
	private DingOrgManager dingOrgManager;

	@Override
	public ServiceResponse<Long> addOrgEnterpriseNotBind(OrgEnterpriseNotBindRequest request) {
		logger.info("##########addOrgEnterpriseNotBind#############");
		Long orgId = dingOrgManager.addOrgEnterpriseNotBind(request);
		return new ServiceResponse<Long>(true, null, orgId);
	}

	@Override
	public ServiceResponse<Boolean> bingOrgEnterpriseNotBind(OrgEnterpriseBind request) {
		logger.info("##########bingOrgEnterpriseNotBind#############");
		boolean result = dingOrgManager.bingOrgEnterpriseNotBind(request);
		return new ServiceResponse<Boolean>(true, null, result);
	}

}
