package com.moredian.fishnet.member.service.adapter.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.member.manager.MemberManager;
import com.moredian.fishnet.member.service.adapter.UserService;
import com.moredian.fishnet.member.service.adapter.request.ModifyUserRelationRequest;

@SI
public class UserServiceImpl implements UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private MemberManager memberManager;

	@Override
	public ServiceResponse<Boolean> addProscenium(ModifyUserRelationRequest request) {
		logger.debug("###################addProscenium###################");
		boolean result = memberManager.addProscenium(request);
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> deleteProscenium(ModifyUserRelationRequest request) {
		logger.debug("###################deleteProscenium###################");
		boolean result = memberManager.deleteProscenium(request);
		return new ServiceResponse<Boolean>(true, null, result);
	}

}
