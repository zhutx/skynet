package com.moredian.skynet.device.manager;

import java.util.List;

import com.moredian.skynet.device.domain.DeployDetail;

public interface DeployDetailManager {
	
	List<DeployDetail> findDeployDetailByDeployId(Long deployId, Long orgId);
	
	int addDeployDetail(DeployDetail deployDetail);
	
	int deleteOne(String libraryCode, Long deployId, Long orgId);
	
}
