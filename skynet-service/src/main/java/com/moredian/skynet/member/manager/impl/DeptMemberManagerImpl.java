package com.moredian.skynet.member.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moredian.bee.common.exception.BizAssert;
import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.convertor.PaginationConvertor;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.member.domain.DeptRelation;
import com.moredian.skynet.member.domain.DeptRelationQueryCondition;
import com.moredian.skynet.member.enums.DeptPersonStatus;
import com.moredian.skynet.member.manager.DeptMemberManager;
import com.moredian.skynet.member.mapper.DeptRelationMapper;
import com.moredian.skynet.member.request.BindDeptRelationRequest;
import com.moredian.skynet.member.request.DeptRelationQueryRequest;
import com.moredian.skynet.member.service.adapter.request.JudgeDepartmentLeaderRequest;
import com.moredian.skynet.org.enums.YesNoFlag;
import com.moredian.skynet.org.manager.DeptManager;
import com.moredian.idgenerator.service.IdgeneratorService;

@Service
public class DeptMemberManagerImpl implements DeptMemberManager {
	
	@Autowired
	private DeptRelationMapper deptMemberMapper;
	@Autowired
	private DeptManager deptManager;
	@SI
	private IdgeneratorService idgeneratorService;
	
	private Long genPrimaryKey(String name) {
		return idgeneratorService.getNextIdByTypeName(name).getData();
	}
	
	@Override
	public List<Long> findMemberIdByDeptId(Long orgId, Long deptId) {
		return deptMemberMapper.findMemberIdsByDeptId(orgId, deptId, DeptPersonStatus.USABLE.getValue());
	}
	
	private DeptRelationQueryCondition buildCondition(DeptRelationQueryRequest request) {
		DeptRelationQueryCondition condition = new DeptRelationQueryCondition();
		condition.setOrgId(request.getOrgId());
		condition.setDeptId(request.getDeptId());
		condition.setStatus(YesNoFlag.YES.getValue());
		return condition;
	}
	
	private PaginationDomain<DeptRelation> paginationToPaginationDomain(Pagination<?> fromPagination) {
		PaginationDomain<DeptRelation> toPagination = PaginationConvertor.paginationToPaginationDomain(fromPagination, new PaginationDomain<DeptRelation>());
		if (toPagination == null)
			return null;
		
		return toPagination;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginationDomain<DeptRelation> findPaginationDeptRelation(Pagination<?> pagination, DeptRelationQueryRequest request) {
		DeptRelationQueryCondition condition = this.buildCondition(request);
		PaginationDomain<DeptRelation> domainPagination = paginationToPaginationDomain(pagination);
		return this.getPagination(domainPagination, condition);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected  PaginationDomain getPagination(PaginationDomain pagination, DeptRelationQueryCondition condition) {
        int totalCount = (Integer) deptMemberMapper.getTotalCountByCondition(condition);
        pagination.setTotalCount(totalCount);
        if (totalCount > 0) {
        	condition.setStartRow(pagination.getStartRow());
        	condition.setPageSize(pagination.getPageSize());
        	pagination.setData(deptMemberMapper.findPaginationByCondition(condition));
        }
        return pagination;
    }

	@Override
	public List<Long> findMemberIdByDepts(Long orgId, List<Long> deptIds) {
		return deptMemberMapper.findMemberIdByDepts(orgId, deptIds, DeptPersonStatus.USABLE.getValue());
	}

	@Override
	public List<String> findDeptName(Long orgId, Long memberId) {
		List<Long> deptIdList = deptMemberMapper.findDeptIdByMemberId(orgId, memberId, DeptPersonStatus.USABLE.getValue());
		return deptManager.findDeptNamesByIds(orgId, deptIdList);
	}
	
	@Override
	public List<String> findDeptNameWithoutRoot(Long orgId, Long memberId) {
		List<Long> deptIdList = deptMemberMapper.findDeptIdByMemberId(orgId, memberId, DeptPersonStatus.USABLE.getValue());
		return deptManager.findDeptNamesWithoutRootByIds(orgId, deptIdList);
	}

	@Override
	public List<Long> findDeptId(Long orgId, Long memberId) {
		return deptMemberMapper.findDeptIdByMemberId(orgId, memberId, DeptPersonStatus.USABLE.getValue());
	}

	@Override
	public void addMemberToDept(DeptRelation deptRelation) {
		deptMemberMapper.insertOrUpdate(deptRelation);
	}

	@Override
	public List<Long> findLeaderIds(Long orgId, Long deptId, Integer leaderFlag, Integer status) {
		return deptMemberMapper.findLeaderIds(orgId, deptId, leaderFlag, status);
	}

	@Override
	public boolean addDeptRelation(BindDeptRelationRequest request) {
		BizAssert.notNull(request.getOrgId());
		BizAssert.notNull(request.getDeptId());
		BizAssert.notNull(request.getTpDeptId());
		BizAssert.notNull(request.getMemberId());
		BizAssert.notNull(request.getLeaderFlag());
		BizAssert.notNull(request.getStatus());
		
		DeptRelation deptRelation = BeanUtils.copyProperties(DeptRelation.class, request);
		deptRelation.setDeptRelationId(this.genPrimaryKey(DeptRelation.class.getName()));
		
		deptMemberMapper.insertOrUpdate(deptRelation);
		
		return true;
	}

	@Override
	public boolean clearLeaderRelation(Long orgId, Long deptId, Long memberId) {
		
		deptMemberMapper.updateLeaderFlag(orgId, deptId, memberId, YesNoFlag.NO.getValue());
		
		return true;
	}

	@Override
	public boolean disableRelation(Long orgId, Long memberId) {
		
		deptMemberMapper.updateStatusByMember(orgId, memberId, YesNoFlag.NO.getValue());
		
		return true;
	}

	@Override
	public int findRelationCount(Long orgId, Long memberId, Long deptId) {
		return deptMemberMapper.findRelationCount(orgId, memberId, deptId);
	}

	@Override
	public boolean disableRelation(Long orgId, Long memberId, Long deptId) {
		
		deptMemberMapper.updateOneStatus(orgId, memberId, deptId, YesNoFlag.NO.getValue());
		
		return true;
	}

	@Override
	public boolean judgeIsUserLeader(JudgeDepartmentLeaderRequest request) {
		
		List<Long> deptIdList = deptManager.findAllChildrenId(request.getOrgId(), 0L);
		List<Long> tpDeptIdList = deptManager.findTpIdByIds(request.getOrgId(), deptIdList); // 机构所有第三方部门id
		
		List<Long> user_deptIdList = deptMemberMapper.findDeptIdByMemberId(request.getOrgId(), request.getVisitorUserId(), YesNoFlag.YES.getValue());
		List<Long> user_tpDeptIdList = deptManager.findTpIdByIds(request.getOrgId(), user_deptIdList); // 用户的所有第三方部门id
		
		for(Long tpDeptId : tpDeptIdList) {
			
			if(!request.getDepartment().contains(tpDeptId)) continue;
			
			if(!user_tpDeptIdList.contains(tpDeptId)) continue;
			
			return true;
		}
		
		return false;
	}

	@Override
	public boolean fillDeptIdByTpDeptId(Long orgId, Long tpDeptId, Long deptId) {
		BizAssert.notNull(orgId);
		BizAssert.notNull(tpDeptId);
		BizAssert.notNull(deptId);
		
		deptMemberMapper.fillDeptIdByTpDeptId(orgId, tpDeptId, deptId);
		
		return true;
	}


}
