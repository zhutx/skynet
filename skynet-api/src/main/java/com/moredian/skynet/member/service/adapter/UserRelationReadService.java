package com.moredian.skynet.member.service.adapter;

import java.util.List;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.skynet.member.service.adapter.request.UserRelationSearchRequest;
import com.moredian.skynet.member.service.adapter.response.UserRelationResponse;

public interface UserRelationReadService {
	
	ServiceResponse<List<UserRelationResponse>> selectSubOrgIdByRelation(UserRelationSearchRequest request);

}
