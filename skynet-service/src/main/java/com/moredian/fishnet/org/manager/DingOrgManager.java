package com.moredian.fishnet.org.manager;

import com.moredian.fishnet.org.request.OrgEnterpriseBind;
import com.moredian.fishnet.org.request.OrgEnterpriseNotBindRequest;

public interface DingOrgManager {
	
	Long addOrgEnterpriseNotBind(OrgEnterpriseNotBindRequest request);
	
	boolean bingOrgEnterpriseNotBind(OrgEnterpriseBind request);
	
}
