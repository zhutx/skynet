package com.moredian.skynet.org.manager;

import com.moredian.skynet.org.request.OrgEnterpriseBind;
import com.moredian.skynet.org.request.OrgEnterpriseNotBindRequest;

public interface DingOrgManager {
	
	Long addOrgEnterpriseNotBind(OrgEnterpriseNotBindRequest request);
	
	boolean bingOrgEnterpriseNotBind(OrgEnterpriseBind request);
	
}
