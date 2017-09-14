package com.moredian.skynet.member.manager.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moredian.bee.common.exception.BizAssert;
import com.moredian.bee.common.utils.ExceptionUtils;
import com.moredian.bee.common.utils.JsonUtils;
import com.moredian.bee.rmq.annotation.Subscribe;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.common.model.msg.ConfigGroupRelationDataMsg;
import com.moredian.skynet.common.model.msg.DeleteGroupRelationDataMsg;
import com.moredian.skynet.common.model.msg.ResetGroupRelationDataMsg;
import com.moredian.skynet.member.domain.GroupRange;
import com.moredian.skynet.member.manager.DeptMemberManager;
import com.moredian.skynet.member.manager.GroupPersonManager;
import com.moredian.skynet.member.manager.GroupPersonSizeManager;
import com.moredian.skynet.member.manager.GroupRangeManager;
import com.moredian.skynet.member.manager.MemberManager;
import com.moredian.skynet.member.mapper.GroupRangeMapper;
import com.moredian.skynet.org.domain.Group;
import com.moredian.skynet.org.enums.GroupRangeType;
import com.moredian.skynet.org.enums.OrgErrorCode;
import com.moredian.skynet.org.enums.PersonType;
import com.moredian.skynet.org.enums.YesNoFlag;
import com.moredian.skynet.org.manager.DeptManager;
import com.moredian.skynet.org.manager.GroupManager;
import com.moredian.skynet.org.request.GroupMemberBatchAddRequest;
import com.moredian.skynet.org.request.GroupMemberBatchRemoveRequest;
import com.moredian.idgenerator.service.IdgeneratorService;

@Service
public class GroupRangeManagerImpl implements GroupRangeManager {
	
	
	private static final Logger logger = LoggerFactory.getLogger(GroupRangeManagerImpl.class);
	
	@Autowired
	private GroupManager groupManager;
	@Autowired
	private GroupRangeMapper groupRangeMapper;
	@Autowired
	private DeptManager deptManager;
	@Autowired
	private DeptMemberManager deptMemberManager;
	@Autowired
	private GroupPersonManager groupPersonManager;
	@SI
	private IdgeneratorService idgeneratorService;
	@Autowired
	private MemberManager memberManager;
	@Autowired
	private GroupPersonSizeManager groupPersonSizeManager;
	
	private Long genGroupRangeId() {
		return idgeneratorService.getNextIdByTypeName("com.moredian.skynet.member.GroupRange").getData();
	}
	
	private void doResetGroupDeptRange(Long orgId, Long groupId, List<Long> existRangeIds, List<Long> finalRangeIds, Integer groupRangeType) {
		
		List<Long> existRangeIds_bak = new ArrayList<>();
		existRangeIds_bak.addAll(existRangeIds);
		
		existRangeIds.removeAll(finalRangeIds); // 定位删除的范围
		for(Long rangeId : existRangeIds) {
			groupRangeMapper.delete(orgId, groupId, groupRangeType, rangeId);
		}
		
		finalRangeIds.removeAll(existRangeIds_bak); // 定位新增的范围
		for(Long rangeId : finalRangeIds) {
			GroupRange groupRange = new GroupRange();
			groupRange.setGroupRangeId(this.genGroupRangeId());
			groupRange.setGroupId(groupId);
			groupRange.setOrgId(orgId);
			groupRange.setRangeType(groupRangeType);
			groupRange.setRangeId(rangeId);
			groupRangeMapper.insert(groupRange);
		}
	}
	
	private GroupMemberBatchRemoveRequest buildGroupMemberBatchRemoveRequest(Long orgId, Long groupId, List<Long> memberIds) {
		GroupMemberBatchRemoveRequest request = new GroupMemberBatchRemoveRequest();
		request.setOrgId(orgId);
		request.setGroupId(groupId);
		request.setMembers(memberIds);
		return request;
	}
	
	private GroupMemberBatchAddRequest buildGroupMemberBatchAddRequest(Long orgId, Long groupId, List<Long> memberIds) {
		GroupMemberBatchAddRequest request = new GroupMemberBatchAddRequest();
		request.setOrgId(orgId);
		request.setGroupId(groupId);
		request.setMembers(memberIds);
		return request;
	}
	
	private void doResetGroupMember(Long orgId, Long groupId, List<Long> existMemberIds, List<Long> finalMemberIds) {
		
		List<Long> existMemberIds_bak = new ArrayList<>();
		existMemberIds_bak.addAll(existMemberIds);
		
		existMemberIds.removeAll(finalMemberIds); // 定位删除的组成员
		groupPersonManager.removeGroupMembers(this.buildGroupMemberBatchRemoveRequest(orgId, groupId, existMemberIds), PersonType.MEMBER.getValue());
		
		finalMemberIds.removeAll(existMemberIds_bak); // 定位新增的组成员
		groupPersonManager.addGroupMembers(this.buildGroupMemberBatchAddRequest(orgId, groupId, finalMemberIds), PersonType.MEMBER.getValue());
		
	}

	@Override
	@Transactional
	public boolean resetGroupRange(Long orgId, Long groupId, List<Long> finalDeptIds, List<Long> finalMemberIds) {
		Group group = groupManager.getGroupById(orgId, groupId);
		if(group.getSystemDefault() == YesNoFlag.YES.getValue()) ExceptionUtils.throwException(OrgErrorCode.SYSGROUP_REFUSE_OPERA, OrgErrorCode.SYSGROUP_REFUSE_OPERA.getMessage());
		
		List<Long> clone_finalDeptIds = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(finalDeptIds)) clone_finalDeptIds.addAll(finalDeptIds);
		// 1) 重置群组与部门的范围关系
		List<Long> existDeptIds = groupRangeMapper.findRangeIdByGroupId(orgId, groupId, GroupRangeType.RANGE_DEPT.getValue());
		this.doResetGroupDeptRange(orgId, groupId, existDeptIds, clone_finalDeptIds, GroupRangeType.RANGE_DEPT.getValue());
		
		// 2) 重置群组与成员的范围关系
		List<Long> existMemberIds = groupRangeMapper.findRangeIdByGroupId(orgId, groupId, GroupRangeType.RANGE_MEMBER.getValue());
		
		List<Long> clone_finalMemberIds = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(finalMemberIds)) clone_finalMemberIds.addAll(finalMemberIds);
		this.doResetGroupDeptRange(orgId, groupId, existMemberIds, clone_finalMemberIds, GroupRangeType.RANGE_MEMBER.getValue());
		
		// 换算出部门中的成员
		List<Long> memberIdList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(finalDeptIds)) {
			
			List<Long> memberIdList1 = deptMemberManager.findMemberIdByDepts(orgId, finalDeptIds); // 查询部门下的人员
			memberIdList.addAll(memberIdList1);
			
			List<Long> deptIds = deptManager.findAllChildrenIdByParentIds(orgId, finalDeptIds); // 查询子部门下的人员
			if(CollectionUtils.isNotEmpty(deptIds)) {
				List<Long> memberIdList2 = deptMemberManager.findMemberIdByDepts(orgId, deptIds);
				memberIdList.addAll(memberIdList2);
			}
			
		}
		
		finalMemberIds.addAll(memberIdList);
		
		// 过滤掉重复的
		Set<Long> finalMemberIdSet = new HashSet<>();
		finalMemberIdSet.addAll(finalMemberIds);
		List<Long> finalMemberIdList = new ArrayList<>();
		finalMemberIdList.addAll(finalMemberIdSet);
		
		// 重置群组成员关系
		List<Long> existMemberIdsInGroup = groupPersonManager.findPersonIdByGroup(orgId, groupId, PersonType.MEMBER.getValue());
		this.doResetGroupMember(orgId, groupId, existMemberIdsInGroup, finalMemberIdList);
		
		return true;
	}
	
	@Subscribe
	public void receiveResetGroupRelationDataMsg(ResetGroupRelationDataMsg msg) {
		logger.info("接收MQ消息[群组切换为全员组]: "+JsonUtils.toJson(msg));
		
		logger.info("设置群组范围信息");
		this.resetGroupRange(msg.getOrgId(), msg.getGroupId(), new ArrayList<Long>(), new ArrayList<Long>());
		
		List<Long> existMemberIds = new ArrayList<>();
		List<Long> finalMemberIds = memberManager.findMemberId(msg.getOrgId());
		this.doResetGroupMember(msg.getOrgId(), msg.getGroupId(), existMemberIds, finalMemberIds);
		
	}
	
	@Subscribe
	public void receiveConfigGroupRelationDataMsg(ConfigGroupRelationDataMsg msg) {
		logger.info("接收MQ消息[新群组]: "+JsonUtils.toJson(msg));
		if(msg.getAllMemberFlag() == YesNoFlag.YES.getValue()) {
			List<Long> existMemberIds = new ArrayList<>();
			List<Long> finalMemberIds = memberManager.findMemberId(msg.getOrgId());
			this.doResetGroupMember(msg.getOrgId(), msg.getGroupId(), existMemberIds, finalMemberIds);
		} else {
			logger.info("设置群组范围信息");
			this.resetGroupRange(msg.getOrgId(), msg.getGroupId(), msg.getDepts(), msg.getMembers());
		}
	}
	
	@Subscribe
	public void receiveDeleteGroupMsg(DeleteGroupRelationDataMsg msg) {
		logger.info("接收到MQ消息[删除组] " + JsonUtils.toJson(msg));
		logger.info("删除群组范围数据 ");
		this.removeByGroupId(msg.getOrgId(), msg.getGroupId());
	}
	
	/*
	private GroupRange buildGroupRange(GroupDeptAddRequest request) {
		GroupRange groupRange = new GroupRange();
		groupRange.setGroupRangeId(genGroupRangeId());
		groupRange.setOrgId(request.getOrgId());
		groupRange.setGroupId(request.getGroupId());
		groupRange.setRangeId(request.getDeptId());
		groupRange.setRangeType(GroupRangeType.RANGE_DEPT.getValue());
		return groupRange;
	}
	*/
	
    /*
    @Override
	@Transactional
	public boolean addGroupDeptsRange(GroupDeptBatchAddRequest request) {
		BizAssert.notNull(request.getOrgId(), "orgId must not be null");
		BizAssert.notNull(request.getGroupId(), "groupId must not be null");
		
		if(CollectionUtils.isEmpty(request.getDepts())) return true;
		
		for(Long deptId : request.getDepts()) {
			GroupDeptAddRequest req = new GroupDeptAddRequest();
			req.setOrgId(request.getOrgId());
			req.setGroupId(request.getGroupId());
			req.setDeptId(deptId);
			groupRangeMapper.insert(this.buildGroupRange(req));
		}
		
		return true;
	}*/
	
    /*	
    @Override
	@Transactional
	public boolean removeGroupDeptsRange(GroupDeptBatchRemoveRequest request) {
		BizAssert.notNull(request.getOrgId(), "orgId must not be null");
		BizAssert.notNull(request.getGroupId(), "groupId must not be null");
		
		if(CollectionUtils.isEmpty(request.getDepts())) return true;
		
		for(Long deptId : request.getDepts()) {
			GroupDeptRemoveRequest req = new GroupDeptRemoveRequest();
			req.setOrgId(request.getOrgId());
			req.setGroupId(request.getGroupId());
			req.setDeptId(deptId);
			groupRangeMapper.delete(req.getOrgId(), req.getGroupId(), GroupRangeType.RANGE_DEPT.getValue(), req.getDeptId());
		}
		
		return true;
	}*/
	
	@Override
	@Transactional
	public boolean removeByGroupId(Long orgId, Long groupId) {
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notNull(groupId, "groupId must not be null");
		groupRangeMapper.deleteByGroupId(orgId, groupId);
		return true;
	}

	@Override
	public List<Long> findRangeId(Long orgId, Long groupId, Integer rangeType) {
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notNull(groupId, "groupId must not be null");
		
		return groupRangeMapper.findRangeIdByGroupId(orgId, groupId, rangeType);
		
	}

	@Override
	public List<Long> findGroupIdByRanges(Long orgId, Integer rangeType, List<Long> rangeIdList) {
		
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notNull(rangeType, "rangeType must not be null");
		BizAssert.notEmpty(rangeIdList);
		
		return groupRangeMapper.findGroupIdByRanges(orgId, rangeType, rangeIdList);
	}

	@Override
	public boolean deleteByRangeId(Long orgId, Long rangeId, Integer rangeType) {
		BizAssert.notNull(orgId);
		BizAssert.notNull(rangeId);
		BizAssert.notNull(rangeType);
		
		groupRangeMapper.deleteByRangeId(orgId, rangeId, rangeType);
		
		return true;
	}

}
