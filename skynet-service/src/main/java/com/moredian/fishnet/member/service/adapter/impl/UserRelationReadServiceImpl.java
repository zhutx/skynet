package com.moredian.fishnet.member.service.adapter.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.member.domain.UserRelation;
import com.moredian.fishnet.member.manager.UserRelationManager;
import com.moredian.fishnet.member.service.adapter.UserRelationReadService;
import com.moredian.fishnet.member.service.adapter.request.UserRelationSearchRequest;
import com.moredian.fishnet.member.service.adapter.response.UserRelationResponse;

@SI
public class UserRelationReadServiceImpl implements UserRelationReadService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserRelationReadServiceImpl.class);
	
	@Autowired
	private UserRelationManager userRelationManager;

	public static List<UserRelationResponse> userForUserRelationsListToUserRelationResponse(List<UserRelation> data) {
        if (data == null) return null;
        List<UserRelationResponse> relationResponses = new ArrayList<>(data.size());
        for (UserRelation userRelation : data) {
            UserRelationResponse response = new UserRelationResponse();
            response.setUserId(userRelation.getUserId());
            response.setOrgId(userRelation.getOrgId());
            response.setRelation(userRelation.getRelation());
            response.setRelationStatus(userRelation.getRelationStatus());
            response.setUserRelationId(userRelation.getUserRelationId());
            response.setSubOrgId(userRelation.getSubOrgId());
            relationResponses.add(response);
        }

        return relationResponses;
    }
	
	@Override
	public ServiceResponse<List<UserRelationResponse>> selectSubOrgIdByRelation(UserRelationSearchRequest request) {
		
		logger.debug("###################selectSubOrgIdByRelation###################");
		
		List<UserRelation> userRelations = userRelationManager.selectSubOrgIdOwner(request.getOrgId(), request.getSubOrgId(), request.getRelation());
		
		return new ServiceResponse<List<UserRelationResponse>>(true, null, userForUserRelationsListToUserRelationResponse(userRelations));
	}

}
