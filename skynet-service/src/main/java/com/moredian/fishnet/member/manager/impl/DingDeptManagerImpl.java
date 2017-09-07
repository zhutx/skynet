package com.moredian.fishnet.member.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.moredian.bee.common.consts.Sex;
import com.moredian.bee.common.exception.BizAssert;
import com.moredian.bee.common.utils.ArrayUtil;
import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.common.utils.JsonUtils;
import com.moredian.bee.common.utils.StringUtil;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.member.domain.Member;
import com.moredian.fishnet.member.enums.MemberStatus;
import com.moredian.fishnet.member.manager.DeptMemberManager;
import com.moredian.fishnet.member.manager.DingDeptManager;
import com.moredian.fishnet.member.manager.MemberManager;
import com.moredian.fishnet.member.model.DingUserExtend;
import com.moredian.fishnet.member.request.BindDeptRelationRequest;
import com.moredian.fishnet.member.request.DingDeleteDepartmentRequest;
import com.moredian.fishnet.member.request.DingDepartmentRequest;
import com.moredian.fishnet.member.request.DingDepartmentsSearchRequest;
import com.moredian.fishnet.member.request.MemberAddRequest;
import com.moredian.fishnet.org.domain.Dept;
import com.moredian.fishnet.org.enums.TpType;
import com.moredian.fishnet.org.enums.YesNoFlag;
import com.moredian.fishnet.org.manager.DeptManager;
import com.moredian.fishnet.org.request.EnterDeptRequest;
import com.moredian.fishnet.org.request.UpdateDeptRequest;
import com.moredian.idgenerator.service.IdgeneratorService;

@Service
public class DingDeptManagerImpl implements DingDeptManager {
	
	@Autowired
	private DeptManager deptManager;
	@SI
	private IdgeneratorService idgeneratorService;
	@Autowired
	private MemberManager memberManager;
	@Autowired
	private DeptMemberManager deptMemberManager;
	
	private Long buildParentId(Long orgId, Long parentTpId) {
		
		if(parentTpId == null) return 0L;
		
		Dept parentDept = deptManager.getDeptByTpId(orgId, parentTpId);
		
		if(parentDept == null) {
			
			EnterDeptRequest request = new EnterDeptRequest();
			request.setOrgId(orgId);
			request.setParentId(0L);
			request.setTpId(parentTpId);
			request.setTpType(TpType.DING.getValue());
			Long deptId = deptManager.enterDept(request);
			
			return deptId;
			
		} else {
			
			return parentDept.getDeptId();
			
		}
		
	}

	@Override
	@Transactional
	public Long synDingDepartment(DingDepartmentRequest request) {
		
		Long deptId = null;
		
		BizAssert.notNull(request.getOrgId());
		BizAssert.notNull(request.getDingDepartmentId());
		BizAssert.notBlank(request.getDingDepartmentName());
		
		// 1) 同步部门
		Dept dept = deptManager.getDeptByTpId(request.getOrgId(), request.getDingDepartmentId());
		if(dept == null) {
			
			EnterDeptRequest enterDeptRequest = new EnterDeptRequest();
			enterDeptRequest.setOrgId(request.getOrgId());
			enterDeptRequest.setTpType(TpType.DING.getValue());
			enterDeptRequest.setTpId(request.getDingDepartmentId());
			enterDeptRequest.setDeptName(request.getDingDepartmentName());
			enterDeptRequest.setParentId(buildParentId(request.getOrgId(), request.getDingDepartmentParentId()));
			enterDeptRequest.setTpExtend(JsonUtils.toJson(BeanUtils.copyProperties(DingDeptExtend.class, request)));
			
			deptId = deptManager.enterDept(enterDeptRequest);
			
		} else {
			
			UpdateDeptRequest updateDeptRequest = new UpdateDeptRequest();
			updateDeptRequest.setOrgId(dept.getOrgId());
			updateDeptRequest.setDeptId(dept.getDeptId());
			updateDeptRequest.setDeptName(request.getDingDepartmentName());
			updateDeptRequest.setParentId(buildParentId(request.getOrgId(), request.getDingDepartmentParentId()));
			updateDeptRequest.setTpExtend(JsonUtils.toJson(BeanUtils.copyProperties(DingDeptExtend.class, request)));
			
			deptManager.updateDept(updateDeptRequest);
			
			deptId = dept.getDeptId();
			
			// deptMemberManager.fillDeptIdByTpDeptId(request.getOrgId(), request.getDingDepartmentId(), deptId);
			
		}
		
		// 2) 同步部门的主管人员
		Set<String> finalTpLeaderIds = toStringSet(request.getDingDeptManagerUseridList(), "|");
		for(String tpId : finalTpLeaderIds) {
			Member leader = memberManager.getMemberByTpId(request.getOrgId(), tpId);
			if(leader == null) {
				MemberAddRequest memberAddRequest = new MemberAddRequest();
				memberAddRequest.setOrgId(request.getOrgId());
				memberAddRequest.setTpType(TpType.DING.getValue());
				memberAddRequest.setTpId(tpId);
				memberAddRequest.setSex(Sex.UNKNOWN.intValue());
				List<Long> relationDepts = new ArrayList<>();
				relationDepts.add(deptId);
				memberAddRequest.setRelationDepts(relationDepts);
				memberManager.addMember(memberAddRequest);
			} else {
				if(leader.getStatus() != MemberStatus.USABLE.getValue()) {
					memberManager.activeMember(leader.getOrgId(), leader.getMemberId());
				}
				
			}
		}
		
		// 3) 调整部门主管人员关系
		int leaderFlag = YesNoFlag.YES.getValue();
		int status = YesNoFlag.YES.getValue();
		List<Long> existLeaderIds = deptMemberManager.findLeaderIds(request.getOrgId(), deptId, leaderFlag, status);
		List<String> existTpLeaderIds = null;
		if(CollectionUtils.isEmpty(existLeaderIds)) {
			existTpLeaderIds = new ArrayList<>();
		} else {
			existTpLeaderIds = memberManager.findTpId(request.getOrgId(), existLeaderIds);
		}
		
		// 定位增加的关系
		List<String> copy_finalTpLeaderIds = new ArrayList<>();
		copy_finalTpLeaderIds.addAll(finalTpLeaderIds);
		copy_finalTpLeaderIds.removeAll(existTpLeaderIds);
		for(String tpLeaderId : copy_finalTpLeaderIds) {
			// 增加部门主管关系
			BindDeptRelationRequest bindDeptRelationRequest = new BindDeptRelationRequest();
			bindDeptRelationRequest.setOrgId(request.getOrgId());
			bindDeptRelationRequest.setDeptId(deptId);
			
			Member member = memberManager.getMemberByTpId(request.getOrgId(), tpLeaderId);
			if(member != null){
				bindDeptRelationRequest.setMemberId(member.getMemberId());
				bindDeptRelationRequest.setLeaderFlag(YesNoFlag.YES.getValue());
				bindDeptRelationRequest.setStatus(YesNoFlag.YES.getValue());
				bindDeptRelationRequest.setTpDeptId(request.getDingDepartmentId());
				deptMemberManager.addDeptRelation(bindDeptRelationRequest);
				
				DingUserExtend tpExtend = JsonUtils.fromJson(DingUserExtend.class, member.getTpExtend());
				String myManageDepts = tpExtend.getDing_isLeaderInDepts();
				
				Map<Long,Boolean> manageDeptMap = new HashMap<>();
				if(StringUtils.isNotBlank(myManageDepts)) {
					manageDeptMap = JSON.parseObject(myManageDepts, new TypeReference<Map<Long, Boolean>>() {});
					manageDeptMap.put(request.getDingDepartmentId(), true);
				}
				tpExtend.setDing_isLeaderInDepts(JsonUtils.toJson(manageDeptMap));
				memberManager.updateTpExtend(member.getOrgId(), member.getMemberId(), JsonUtils.toJson(tpExtend));
			} 
			
		}
		
		// 定位移除的关系
		existTpLeaderIds.removeAll(finalTpLeaderIds);
		for(String tpLeaderId : existTpLeaderIds) {
			// 移除部门主管关系
			Member member = memberManager.getMemberByTpId(request.getOrgId(), tpLeaderId);
			if(member != null) {
				deptMemberManager.clearLeaderRelation(request.getOrgId(), deptId, member.getMemberId());
				
				DingUserExtend tpExtend = JsonUtils.fromJson(DingUserExtend.class, member.getTpExtend());
				String myManageDepts = tpExtend.getDing_isLeaderInDepts();
				
				Map<Long,Boolean> manageDeptMap = new HashMap<>();
				if(StringUtils.isNotBlank(myManageDepts)) {
					manageDeptMap = JSON.parseObject(myManageDepts, new TypeReference<Map<Long, Boolean>>() {});
					manageDeptMap.put(request.getDingDepartmentId(), false);
				}
				tpExtend.setDing_isLeaderInDepts(JsonUtils.toJson(manageDeptMap));
				memberManager.updateTpExtend(member.getOrgId(), member.getMemberId(), JsonUtils.toJson(tpExtend));
			}
		}
		
		return deptId;
	}
	
	private static Set<String> toStringSet(String str, String split){
	    Set<String> set = new HashSet<>();
	    
	    if(StringUtils.isBlank(str)) return set;
		 
        String arr[] = StringUtil.split(str, split);
        if(ArrayUtil.isNotEmpty(arr)){
            for (String s : arr) {
                set.add(s);
            }
        }

        return set;
    }

	@Override
	public boolean deleteDingDepartment(DingDeleteDepartmentRequest request) {
		BizAssert.notNull(request.getOrgId());
		BizAssert.notNull(request.getDingDepartmentId());
		
		deptManager.removeByTpId(request.getOrgId(), request.getDingDepartmentId());
		
		return true;
	}

	@Override
	public List<Long> getDepartmentByOrgId(DingDepartmentsSearchRequest request) {
		BizAssert.notNull(request.getOrgId());
		
		return deptManager.findTpId(request.getOrgId());
		
	}

}
