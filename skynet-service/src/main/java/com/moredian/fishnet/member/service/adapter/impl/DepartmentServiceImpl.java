package com.moredian.fishnet.member.service.adapter.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.moredian.bee.common.exception.BizAssert;
import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.JsonUtils;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.member.domain.DeptRelation;
import com.moredian.fishnet.member.domain.Member;
import com.moredian.fishnet.member.manager.DeptMemberManager;
import com.moredian.fishnet.member.manager.MemberManager;
import com.moredian.fishnet.member.manager.impl.DingDeptExtend;
import com.moredian.fishnet.member.model.DingUserExtend;
import com.moredian.fishnet.member.request.DeptRelationQueryRequest;
import com.moredian.fishnet.member.service.adapter.DepartmentService;
import com.moredian.fishnet.member.service.adapter.request.DingDepartmentSearchRequest;
import com.moredian.fishnet.member.service.adapter.request.JudgeDepartmentLeaderRequest;
import com.moredian.fishnet.member.service.adapter.response.DepartmentMemberResponse;
import com.moredian.fishnet.member.service.adapter.response.DingDepartmentResponse;
import com.moredian.fishnet.org.domain.Dept;
import com.moredian.fishnet.org.enums.YesNoFlag;
import com.moredian.fishnet.org.manager.DeptManager;

@SI
public class DepartmentServiceImpl implements DepartmentService {
	
	private static final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);
	
	@Autowired
	private DeptMemberManager deptMemberManager;
	@Autowired
	private DeptManager deptManager;
	@Autowired
	private MemberManager memberManager;

	@Override
	public ServiceResponse<List<DingDepartmentResponse>> getDingDepartmentBySearchBean(
			DingDepartmentSearchRequest request) {
		
		logger.debug("###################getDingDepartmentBySearchBean###################");
		
		BizAssert.notNull(request.getOrgId());
		BizAssert.notNull(request.getUserId());
		
		List<DingDepartmentResponse> list = new ArrayList<>();
		
		if (request.getParentDingDepartmentId() != null) { // 查子部门
			Dept parentDept = deptManager.getDeptByTpId(request.getOrgId(), request.getParentDingDepartmentId());
			
			List<Dept> children = deptManager.findDepts(request.getOrgId(), parentDept.getDeptId());
			for(Dept dept : children) {
				this.addToResponse(dept, list);
			}
			
		} else {
			
			Member member = memberManager.getMember(request.getOrgId(), request.getUserId());
			DingUserExtend tpExtend = JsonUtils.fromJson(DingUserExtend.class, member.getTpExtend());
			String myManageDepts = tpExtend.getDing_isLeaderInDepts();
			if(StringUtils.isNotBlank(myManageDepts)) {
				Map<Long,Boolean> manageDeptMap = JSON.parseObject(myManageDepts, new TypeReference<Map<Long, Boolean>>() {});
				List<Long> manageTpDeptSet = new ArrayList<>();
                for (Map.Entry<Long,Boolean> entry: manageDeptMap.entrySet()) {
                    if(entry.getValue()) manageTpDeptSet.add(entry.getKey());
                }
                
                if(CollectionUtils.isNotEmpty(manageTpDeptSet)) {
                	Dept rootDept = deptManager.getRootDept(request.getOrgId());
                	List<Dept> childrenDeptList = deptManager.findAllChildren(request.getOrgId(), rootDept.getDeptId());
                	for(Dept dept : childrenDeptList) {
                		if(!manageTpDeptSet.contains(dept.getTpId())) continue;
                		this.addToResponse(dept, list);
                	}
                }
			}
		}
		
		return new ServiceResponse<List<DingDepartmentResponse>>(true, null, list);
	}
	
	private void addToResponse(Dept dept, List<DingDepartmentResponse> list){
		DingDeptExtend dingDeptExtend = JsonUtils.fromJson(DingDeptExtend.class, dept.getTpExtend());
		if(dingDeptExtend == null) return;
		if(dingDeptExtend.getDingDeptHiding() == YesNoFlag.YES.getValue()) return;
		
		DingDepartmentResponse dingDepartment = new DingDepartmentResponse();
        dingDepartment.setOrgId(dept.getOrgId());
        dingDepartment.setDingDepartmentId(dingDeptExtend.getDingDepartmentId());
        dingDepartment.setDepartmentId(dept.getDeptId());
        dingDepartment.setDingDepartmentName(dingDeptExtend.getDingDepartmentName());
        list.add(dingDepartment);
	}
	
	public static void main(String[] args) {
		String json ="{\"dingDepartmentId\":31609250,\"dingDepartmentName\":\"行政\",\"dingDepartmentParentId\":1,\"dingDepartmentOrder\":31609250,\"dingCreateDeptGroup\":0,\"dingAutoAddUser\":0,\"dingDeptHiding\":0,\"dingDeptPermits\":\"\",\"dingUserPermits\":\"\",\"dingOuterDept\":0,\"dingOuterPermitsDepts\":\"\",\"dingOuterPermitUsers\":\"\",\"dingOrgDeptOwner\":\"\",\"dingDeptManagerUseridList\":\"020129675327546898\"}";
		DingDeptExtend dingDeptExtend = JsonUtils.fromJson(DingDeptExtend.class, json);
		System.out.println(JsonUtils.toJson(dingDeptExtend));
	}

	@Override
	public ServiceResponse<Pagination<DepartmentMemberResponse>> getDingDepartmentMemberByPagination(
			Pagination<DepartmentMemberResponse> pagination, DingDepartmentSearchRequest request) {
		
		logger.debug("###################getDingDepartmentMemberByPagination###################");
		
		Dept dept = deptManager.getDeptByTpId(request.getOrgId(), request.getDingDepartmentId());
		
		Pagination<Long> paginationParam = new Pagination<>();
		paginationParam.setPageNo(pagination.getPageNo());
		paginationParam.setPageSize(pagination.getPageSize());
		
		DeptRelationQueryRequest req = new DeptRelationQueryRequest();
		req.setOrgId(request.getOrgId());
		req.setDeptId(dept.getDeptId());
		
		PaginationDomain<DeptRelation> paginationDetpRelation = deptMemberManager.findPaginationDeptRelation(paginationParam, req);
		
		List<Long> memberIdList = new ArrayList<>();
		for(DeptRelation deptRelation : paginationDetpRelation.getData()) {
			memberIdList.add(deptRelation.getMemberId());
		}
		
		List<Member> memberList = memberManager.findMemberByIds(dept.getOrgId(), memberIdList);
		
		List<DepartmentMemberResponse> list = new ArrayList<>();
		
		for(Member member : memberList) {
			DingUserExtend extend = JsonUtils.fromJson(DingUserExtend.class, member.getTpExtend());
			
			DepartmentMemberResponse response = new DepartmentMemberResponse();
			response.setDingAvatar(extend.getDing_avatar());
			response.setDingName(extend.getDing_name());
			response.setFaceImgUrl(member.getVerifyFaceUrl());
			response.setUserId(member.getMemberId());
			list.add(response);
		}
		
		Pagination<DepartmentMemberResponse> paginationResponse = new Pagination<>();
		paginationResponse.setPageNo(paginationDetpRelation.getPageNo());
		paginationResponse.setPageSize(paginationDetpRelation.getPageSize());
		paginationResponse.setData(list);
		paginationResponse.setTotalCount(paginationDetpRelation.getTotalCount());
		
		return new ServiceResponse<Pagination<DepartmentMemberResponse>>(paginationResponse);
	}

	@Override
	public ServiceResponse<Boolean> judgeIsUserLeader(JudgeDepartmentLeaderRequest request) {
		logger.debug("###################judgeIsUserLeader###################");
		boolean result = deptMemberManager.judgeIsUserLeader(request);
		return new ServiceResponse<Boolean>(true, null, result);
	}

}
