package com.moredian.skynet.device.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.convertor.PaginationConvertor;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.skynet.device.domain.Deploy;
import com.moredian.skynet.device.domain.DeployDetail;
import com.moredian.skynet.device.domain.Device;
import com.moredian.skynet.device.manager.DeployDetailManager;
import com.moredian.skynet.device.manager.DeployManager;
import com.moredian.skynet.device.manager.DeviceManager;
import com.moredian.skynet.device.model.DeployGroupInfo;
import com.moredian.skynet.device.model.DeployInfo;
import com.moredian.skynet.device.request.DeployAddRequest;
import com.moredian.skynet.device.request.DeployQueryRequest;
import com.moredian.skynet.device.request.DeployUpdateRequest;
import com.moredian.skynet.device.service.DeployService;
import com.moredian.skynet.org.response.PositionInfo;
import com.moredian.skynet.org.service.PositionService;

@Service
public class DeployServiceImpl implements DeployService {
	
	@Autowired
	private DeployManager deployManager;
	@Autowired
	private DeviceManager deviceManager;
	@Reference
	private PositionService positionService;
	@Autowired
	private DeployDetailManager deployDetailManager;

	@Override
	public ServiceResponse<Boolean> addDeploy(DeployAddRequest request) {
		boolean result = deployManager.addDeploy(request);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> updateDeploy(DeployUpdateRequest request) {
		boolean result = deployManager.updateDeploy(request);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> stopDeploy(Long deployId, Long orgId) {
		boolean result = deployManager.stopDeploy(deployId, orgId);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> deleteDeploy(Long deployId, Long orgId) {
		boolean result = deployManager.deleteDeploy(deployId, orgId);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public DeployInfo getDeployInfoByDeployId(Long deployId, Long orgId) {
		
		Deploy deploy = deployManager.getDeployById(deployId, orgId);
		DeployInfo deployInfo = new DeployInfo();
		deployInfo.setDeployId(deploy.getDeployId());
		deployInfo.setDeviceId(deploy.getDeviceId());
		deployInfo.setThreshold(deploy.getThreshold());
		deployInfo.setDeployBeginTime(deploy.getDeployBeginTime());
		deployInfo.setDeployoEndTime(deploy.getDeployEndTime());
		deployInfo.setNoticeRoles(deploy.getNoticeRoles());
		deployInfo.setStatus(deploy.getStatus());
		
		List<DeployDetail> deployDetailList = deployDetailManager.findDeployDetailByDeployId(deployId, orgId);
		List<DeployGroupInfo> deployLibrarys = new ArrayList<>();
		for(DeployDetail deployDetail : deployDetailList) {
			DeployGroupInfo deployLibrary = new DeployGroupInfo();
			deployLibrary.setGroupCode(deployDetail.getGroupCode());
			deployLibrary.setDeployLabel(deployDetail.getDeployLabel());
			deployLibrarys.add(deployLibrary);
		}
		deployInfo.setDeployLibrarys(deployLibrarys);
		
		return deployInfo;
	}

	@Override
	public Pagination<DeployInfo> findPaginationDeploy(Pagination<DeployInfo> pagination,
			DeployQueryRequest request) {
		PaginationDomain<Deploy> paginationDeploy = deployManager.findPaginationDeploy(request, pagination);
		return paginationDeployToPaginationDeployInfo(paginationDeploy);
	}
	
	public Pagination<DeployInfo> paginationDeployToPaginationDeployInfo(PaginationDomain<Deploy> fromPagination) {
		Pagination<DeployInfo> toPagination = PaginationConvertor.paginationDomainToPagination(fromPagination, new Pagination<DeployInfo>());
		if (toPagination == null)
			return null;
		toPagination.setData(this.deployListToDeployInfoList(fromPagination.getData()));
		return toPagination;
	}
	
	public List<DeployInfo> deployListToDeployInfoList(List<Deploy> deployList) {
		if (deployList == null) return null;
		
		List<DeployInfo> response = new ArrayList<>();
		for(Deploy deploy : deployList) {
			response.add(this.deployToDeployInfo(deploy));
		}
		
		return response;
	}
	
	public DeployInfo deployToDeployInfo(Deploy deploy) {
		if (deploy == null) return null;
		DeployInfo response = new DeployInfo();
		response.setDeployBeginTime(deploy.getDeployBeginTime());
		response.setDeployoEndTime(deploy.getDeployEndTime());
		response.setDeployId(deploy.getDeployId());
		
		Device device = deviceManager.getDeviceById(deploy.getDeviceId(), deploy.getOrgId());
		PositionInfo positionInfo = positionService.getPositionById(device.getPositionId(), device.getOrgId());
		response.setPositionName(positionInfo.getFullName());
		
		List<DeployDetail> deployDetailList = deployDetailManager.findDeployDetailByDeployId(deploy.getDeployId(), deploy.getOrgId());
		List<DeployGroupInfo> deployLibrarys = new ArrayList<>();
		for(DeployDetail deployDetail : deployDetailList) {
			DeployGroupInfo deployLibrary = new DeployGroupInfo();
			deployLibrary.setGroupCode(deployDetail.getGroupCode());
			deployLibrary.setDeployLabel(deployDetail.getDeployLabel());
			deployLibrarys.add(deployLibrary);
		}
		response.setDeployLibrarys(deployLibrarys);
		
		response.setDeviceId(deploy.getDeviceId());
		response.setGmtCreate(deploy.getGmtCreate());
		response.setGmtModify(deploy.getGmtModify());
		response.setNoticeRoles(deploy.getNoticeRoles());
		response.setOrgId(deploy.getOrgId());
		response.setStatus(deploy.getStatus());
		response.setThreshold(deploy.getThreshold());
		return response;
	}
	
	@Override
	public ServiceResponse<Boolean> clearOHuberConfig(Long orgId) {
		boolean result = deployManager.clearOHuberConfig(orgId);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> deployCloudeye(Long orgId, Long deployId, Integer cloudeyeDeployAction) {
		boolean result = deployManager.deployCloudeye(orgId, deployId, cloudeyeDeployAction);
		return new ServiceResponse<Boolean>(true, null, result);
	}

}
