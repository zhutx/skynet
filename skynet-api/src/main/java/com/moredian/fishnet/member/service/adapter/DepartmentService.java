package com.moredian.fishnet.member.service.adapter;

import java.util.List;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.fishnet.member.service.adapter.request.DingDepartmentSearchRequest;
import com.moredian.fishnet.member.service.adapter.request.JudgeDepartmentLeaderRequest;
import com.moredian.fishnet.member.service.adapter.response.DepartmentMemberResponse;
import com.moredian.fishnet.member.service.adapter.response.DingDepartmentResponse;

public interface DepartmentService {
	
	ServiceResponse<List<DingDepartmentResponse>> getDingDepartmentBySearchBean(DingDepartmentSearchRequest request);
	
	ServiceResponse<Pagination<DepartmentMemberResponse>> getDingDepartmentMemberByPagination(
            Pagination<DepartmentMemberResponse> pagination, DingDepartmentSearchRequest request);
	
	ServiceResponse<Boolean> judgeIsUserLeader(JudgeDepartmentLeaderRequest request);

}
