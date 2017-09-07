package com.moredian.fishnet.device.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moredian.fishnet.device.domain.DeployDetail;
import com.moredian.fishnet.device.manager.DeployDetailManager;
import com.moredian.fishnet.device.mapper.DeployDetailMapper;

@Service
public class DeployDetailManagerImpl implements DeployDetailManager {
	
	@Autowired
	private DeployDetailMapper deployDetailMapper;

	@Override
	public List<DeployDetail> findDeployDetailByDeployId(Long deployId, Long orgId) {
		return deployDetailMapper.findByDeployId(deployId, orgId);
	}

	@Override
	public int addDeployDetail(DeployDetail deployDetail) {
		deployDetailMapper.insert(deployDetail);
		return 0;
	}

	@Override
	public int deleteOne(String libraryCode, Long deployId, Long orgId) {
		return deployDetailMapper.deleteOne(libraryCode, deployId, orgId);
	}

}
