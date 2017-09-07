package com.moredian.fishnet.device.manager;

import java.util.List;

import com.moredian.fishnet.device.domain.DeployDetail;

public interface DeployDetailManager {
	
	List<DeployDetail> findDeployDetailByDeployId(Long deployId, Long orgId);
	
	int addDeployDetail(DeployDetail deployDetail);
	
	int deleteOne(String libraryCode, Long deployId, Long orgId);
	
}
