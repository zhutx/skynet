package com.moredian.fishnet.member.service.adapter;

import java.util.List;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.fishnet.member.service.adapter.request.SelectUserRelationRequest;
import com.moredian.fishnet.member.service.adapter.request.UserForDingByUserIdsRequest;
import com.moredian.fishnet.member.service.adapter.request.UserSearchForSynRequest;
import com.moredian.fishnet.member.service.adapter.request.UserSearchForVisitorRequest;
import com.moredian.fishnet.member.service.adapter.response.ProsceniumUserResponse;
import com.moredian.fishnet.member.service.adapter.response.UserForDingResponse;
import com.moredian.fishnet.member.service.adapter.response.UserForSynDingResponse;

public interface UserSearchService {
	
	ServiceResponse<Boolean> judgeProscenium(SelectUserRelationRequest request);
	
	ServiceResponse<UserForDingResponse> getVisibleDdUser(UserSearchForVisitorRequest searchBean);
	
	ServiceResponse<List<UserForDingResponse>> getVisibleUserByIds(UserForDingByUserIdsRequest request);
	
	ServiceResponse<UserForSynDingResponse> getSynUserForDing(UserSearchForSynRequest request);
	
	ServiceResponse<List<ProsceniumUserResponse>> selectProscenium(SelectUserRelationRequest request);
	
}
