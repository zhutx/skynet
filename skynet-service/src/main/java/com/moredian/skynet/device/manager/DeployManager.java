package com.moredian.skynet.device.manager;

import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.skynet.device.domain.Deploy;
import com.moredian.skynet.device.model.DeployInfo;
import com.moredian.skynet.device.request.DeployAddRequest;
import com.moredian.skynet.device.request.DeployQueryRequest;
import com.moredian.skynet.device.request.DeployUpdateRequest;

public interface DeployManager {
	
	Deploy getDeployByDevice(Long orgId, Long deviceId);
	
	boolean addDeploy(DeployAddRequest request);
	
	boolean updateDeploy(DeployUpdateRequest request);
	
	boolean stopDeploy(Long deployId, Long orgId);
	
	boolean deleteDeploy(Long deployId, Long orgId);
	
	Deploy getDeployById(Long deployId, Long orgId);
	
	PaginationDomain<Deploy> findPaginationDeploy(DeployQueryRequest request, Pagination<DeployInfo> pagination);
	
	void autoToggleDeployState();
	
	boolean deployCloudeye(Long orgId, Long deployId, Integer cloudeyeDeployAction);
	
	boolean clearOHuberConfig(Long orgId);
	
}
