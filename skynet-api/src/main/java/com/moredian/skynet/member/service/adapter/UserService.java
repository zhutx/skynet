package com.moredian.skynet.member.service.adapter;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.skynet.member.service.adapter.request.ModifyUserRelationRequest;

public interface UserService {
	
	ServiceResponse<Boolean> addProscenium(ModifyUserRelationRequest request);
	
	ServiceResponse<Boolean> deleteProscenium(ModifyUserRelationRequest request);

}
