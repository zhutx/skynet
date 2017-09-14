package com.moredian.skynet.org.service;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.skynet.org.request.OrgEnterpriseBind;
import com.moredian.skynet.org.request.OrgEnterpriseNotBindRequest;

public interface DingOrgService {
	
	ServiceResponse<Long> addOrgEnterpriseNotBind(OrgEnterpriseNotBindRequest request);
	
	ServiceResponse<Boolean> bingOrgEnterpriseNotBind(OrgEnterpriseBind request);

}
