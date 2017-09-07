package com.moredian.fishnet.device.convertor;

import com.moredian.fishnet.device.domain.Deploy;
import com.moredian.fishnet.device.request.DeployAddRequest;
import com.moredian.fishnet.device.request.DeployUpdateRequest;

public class DeployConvertor {
	
	public static Deploy deployAddRequestToDeploy(DeployAddRequest request) {
		if (request == null) return null;
		Deploy deploy = new Deploy();
		deploy.setOrgId(request.getOrgId());
		deploy.setDeviceId(request.getDeviceId());
		deploy.setCameraFlag(request.getCameraFlag());
		deploy.setThreshold(request.getThreshold());
		deploy.setDeployBeginTime(request.getDeployBeginTime());
		deploy.setDeployEndTime(request.getDeployEndTime());
		deploy.setNoticeRoles(request.getNoticeRoles());
		return deploy;
	}
	
	public static Deploy deployUpdateRequestToDeploy(DeployUpdateRequest request) {
		if (request == null) return null;
		Deploy deploy = new Deploy();
		deploy.setDeployId(request.getDeployId());
		deploy.setOrgId(request.getOrgId());
		deploy.setThreshold(request.getThreshold());
		deploy.setDeployBeginTime(request.getDeployBeginTime());
		deploy.setDeployEndTime(request.getDeployEndTime());
		deploy.setNoticeRoles(request.getNoticeRoles());
		return deploy;
	}

}
