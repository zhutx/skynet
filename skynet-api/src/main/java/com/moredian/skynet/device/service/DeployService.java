package com.moredian.skynet.device.service;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.skynet.device.model.DeployInfo;
import com.moredian.skynet.device.request.DeployAddRequest;
import com.moredian.skynet.device.request.DeployQueryRequest;
import com.moredian.skynet.device.request.DeployUpdateRequest;

public interface DeployService {
	
	ServiceResponse<Boolean> addDeploy(DeployAddRequest request);
	
	ServiceResponse<Boolean> updateDeploy(DeployUpdateRequest request);
	
	ServiceResponse<Boolean> stopDeploy(Long deployId, Long orgId);
	
	ServiceResponse<Boolean> deleteDeploy(Long deployId, Long orgId);
	
	DeployInfo getDeployInfoByDeployId(Long deployId, Long orgId);
	
	Pagination<DeployInfo> findPaginationDeploy(Pagination<DeployInfo> pagination, DeployQueryRequest request);
	
	ServiceResponse<Boolean> clearOHuberConfig(Long orggId);
	
	ServiceResponse<Boolean> deployCloudeye(Long orgId, Long deployId, Integer cloudeyeDeployAction);
	
}
