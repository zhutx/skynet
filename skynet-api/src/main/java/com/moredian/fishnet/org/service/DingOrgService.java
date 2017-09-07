package com.moredian.fishnet.org.service;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.fishnet.org.request.OrgEnterpriseBind;
import com.moredian.fishnet.org.request.OrgEnterpriseNotBindRequest;

public interface DingOrgService {
	
	ServiceResponse<Long> addOrgEnterpriseNotBind(OrgEnterpriseNotBindRequest request);
	
	ServiceResponse<Boolean> bingOrgEnterpriseNotBind(OrgEnterpriseBind request);

}
