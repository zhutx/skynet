package com.moredian.fishnet.member.manager.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moredian.bee.common.exception.BizAssert;
import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.common.utils.JsonUtils;
import com.moredian.bee.rmq.annotation.Subscribe;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.cloudeye.core.api.lib.PersonFullInfo;
import com.moredian.fishnet.common.model.msg.ClearMemberRelationDataMsg;
import com.moredian.fishnet.common.model.msg.ConfigMemberGroupDataMsg;
import com.moredian.fishnet.common.model.msg.DeleteGroupRelationDataMsg;
import com.moredian.fishnet.common.model.msg.ResetMemberGroupDataMsg;
import com.moredian.fishnet.member.domain.GroupPerson;
import com.moredian.fishnet.member.domain.Member;
import com.moredian.fishnet.member.enums.MemberStatus;
import com.moredian.fishnet.member.manager.CloudeyePersonProxy;
import com.moredian.fishnet.member.manager.GroupPersonManager;
import com.moredian.fishnet.member.manager.GroupPersonSizeManager;
import com.moredian.fishnet.member.manager.GroupRangeManager;
import com.moredian.fishnet.member.manager.MemberManager;
import com.moredian.fishnet.member.mapper.GroupPersonMapper;
import com.moredian.fishnet.org.enums.GroupRangeType;
import com.moredian.fishnet.org.enums.PersonType;
import com.moredian.fishnet.org.manager.GroupManager;
import com.moredian.fishnet.org.request.GroupMemberBatchAddRequest;
import com.moredian.fishnet.org.request.GroupMemberBatchRemoveRequest;
import com.moredian.fishnet.org.request.GroupPersonAddRequest;
import com.moredian.fishnet.org.request.GroupPersonRemoveRequest;
import com.moredian.idgenerator.service.IdgeneratorService;

@Service
public class GroupPersonManagerImpl implements GroupPersonManager {
	
	private static final Logger logger = LoggerFactory.getLogger(GroupPersonManagerImpl.class);
	
	@SI
	private IdgeneratorService idgeneratorService;
	@Autowired
	private GroupPersonMapper groupPersonMapper;
	@Autowired
	private GroupManager groupManager;
	@Autowired
	private CloudeyePersonProxy cloudeyePersonProxy;
	@Autowired
	private MemberManager memberManager;
	@Autowired
	private GroupRangeManager groupRangeManager;
	@Autowired
	private GroupPersonSizeManager groupPersonSizeManager;
	
	private Long genGroupPersonId() {
		return idgeneratorService.getNextIdByTypeName(GroupPerson.class.getName()).getData();
	}

	private GroupPerson buildGroupPerson(GroupPersonAddRequest request) {
		GroupPerson groupPerson = new GroupPerson();
		groupPerson.setGroupPersonId(genGroupPersonId());
		groupPerson.setOrgId(request.getOrgId());
		groupPerson.setGroupId(request.getGroupId());
		groupPerson.setPersonId(request.getPersonId());
		groupPerson.setPersonType(request.getPersonType());
		return groupPerson;
	}
	
	@Override
	@Transactional
	public boolean addGroupMembers(GroupMemberBatchAddRequest request, Integer personType) {
		BizAssert.notNull(request.getOrgId(), "orgId must not be null");
		BizAssert.notNull(request.getGroupId(), "groupId must not be null");
		BizAssert.notNull(personType, "personType must not be null");
		
		if(request.getMembers() == null || request.getMembers().isEmpty()) return true;
		
		List<Long> memberIds = new ArrayList<>();
		for(Long memberId : request.getMembers()) {
			GroupPersonAddRequest req = new GroupPersonAddRequest();
			req.setOrgId(request.getOrgId());
			req.setGroupId(request.getGroupId());
			req.setPersonType(personType);
			req.setPersonId(memberId);
			groupPersonMapper.insert(this.buildGroupPerson(req));
			
			memberIds.add(memberId);
		}
		
		// 重置群组人员数
		groupPersonSizeManager.resetPersonSize(request.getOrgId(), request.getGroupId(), personType);
		
		List<Long> filterIds = memberManager.findNoVerifyUrlIds(request.getOrgId()); // 获取没有识别照片的成员id集
		memberIds.removeAll(filterIds); 
		
		cloudeyePersonProxy.addMembersToGroup(request.getOrgId(), request.getGroupId(), memberIds);
		
		return true;
	}
	
	public boolean addGroupRelations(Long orgId, Long memberId, List<Long> groupIds, Integer personType) {
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notNull(memberId, "memberId must not be null");
		BizAssert.notNull(personType, "personType must not be null");
		
		if(groupIds == null || groupIds.isEmpty()) return true;
		
		for(Long groupId : groupIds) {
			GroupPersonAddRequest req = new GroupPersonAddRequest();
			req.setOrgId(orgId);
			req.setGroupId(groupId);
			req.setPersonType(personType);
			req.setPersonId(memberId);
			groupPersonMapper.insert(this.buildGroupPerson(req));
			
		}
		
		return true;
	}
	
	private GroupPerson buildGroupPerson(GroupPersonRemoveRequest request) {
		GroupPerson groupPerson = new GroupPerson();
		groupPerson.setGroupPersonId(genGroupPersonId());
		groupPerson.setOrgId(request.getOrgId());
		groupPerson.setGroupId(request.getGroupId());
		groupPerson.setPersonId(request.getPersonId());
		groupPerson.setPersonType(request.getPersonType());
		return groupPerson;
	}

	@Override
	@Transactional
	public boolean removeGroupMembers(GroupMemberBatchRemoveRequest request, Integer personType) {
		BizAssert.notNull(request.getOrgId(), "orgId must not be null");
		BizAssert.notNull(request.getGroupId(), "groupId must not be null");
		BizAssert.notNull(personType, "personType must not be null");
		
		if(CollectionUtils.isEmpty(request.getMembers())) return true;
		
		for(Long memberId : request.getMembers()) {
			GroupPersonRemoveRequest req = new GroupPersonRemoveRequest();
			req.setOrgId(request.getOrgId());
			req.setGroupId(request.getGroupId());
			req.setPersonType(personType);
			req.setPersonId(memberId);
			groupPersonMapper.delete(this.buildGroupPerson(req));
		}
		
		// 重置群组人员数
		groupPersonSizeManager.resetPersonSize(request.getOrgId(), request.getGroupId(), personType);
		
		List<Long> memberIds = new ArrayList<>();
		memberIds.addAll(request.getMembers());
		
		List<Long> filterIds = memberManager.findNoVerifyUrlIds(request.getOrgId()); // 获取没有识别照片的成员id集
		memberIds.removeAll(filterIds); 
		
		cloudeyePersonProxy.removeMembersToGroup(request.getOrgId(), request.getGroupId(), memberIds);
		
		return true;
	}
	
	public boolean removeGroupRelations(Long orgId, Long memberId, List<Long> groups, Integer personType) {
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notNull(memberId, "groupId must not be null");
		BizAssert.notNull(personType, "personType must not be null");
		
		if(CollectionUtils.isEmpty(groups)) return true;
		
		for(Long groupId : groups) {
			GroupPersonRemoveRequest req = new GroupPersonRemoveRequest();
			req.setOrgId(orgId);
			req.setGroupId(groupId);
			req.setPersonType(personType);
			req.setPersonId(memberId);
			groupPersonMapper.delete(this.buildGroupPerson(req));
			
		}
		
		return true;
	}
	
	@Override
	public List<Long> findPersonIdByGroup(Long orgId, Long groupId, Integer personType) {
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notNull(groupId, "groupId must not be null");
		return groupPersonMapper.findPersonId(orgId, groupId, personType);
	}

	@Override
	public boolean removeByGroupId(Long orgId, Long groupId) {
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notNull(groupId, "groupId must not be null");
		groupPersonMapper.deleteByGroupId(orgId, groupId);
		return true;
	}
	
	@Subscribe
	public void receiveNewMemberWithoutFaceMsg(ConfigMemberGroupDataMsg msg) {
		logger.info("接收到MQ消息[同步通讯录成员] " + JsonUtils.toJson(msg));
		logger.info("维护成员群组关系 ");
		
		if(MemberStatus.USABLE.getValue() != msg.getStatus()) return;
		
		Set<Long> groupIdSet = new HashSet<>();
		
		// 查询全员使用的群组
		List<Long> groupIdList1 = groupManager.findAllMemberUseGroupIds(msg.getOrgId()); 
		
		List<Long> deptIdList = new ArrayList<>();
		for(Long deptId : msg.getDepartments()) {
			deptIdList.add(deptId);
		}
		// 查询范围包含部门的群组
		List<Long> groupIdList2 = groupRangeManager.findGroupIdByRanges(msg.getOrgId(), GroupRangeType.RANGE_DEPT.getValue(), deptIdList);
		
		groupIdSet.addAll(groupIdList1);
		groupIdSet.addAll(groupIdList2);
		
		for(Long groupId : groupIdSet) {
			
			GroupPerson groupPerson = new GroupPerson();
			groupPerson.setGroupPersonId(this.genGroupPersonId());
			groupPerson.setOrgId(msg.getOrgId());
			groupPerson.setGroupId(groupId);
			groupPerson.setPersonId(msg.getMemberId());
			groupPerson.setPersonType(PersonType.MEMBER.getValue());
			groupPersonMapper.insert(groupPerson);
			
		}
		
		// 重置群组人员数
		List<Long> groupIdList = new ArrayList<>();
		groupIdList.addAll(groupIdSet);
		groupPersonSizeManager.batchResetPersonSize(msg.getOrgId(), groupIdList, PersonType.MEMBER.getValue());
		
	}
	
	/**
	 * 接收某员工通讯录重新同步的消息
	 * @param msg
	 */
	@Subscribe
	public void receiveReSyncMemberMsg(ResetMemberGroupDataMsg msg) {
		logger.info("接收到MQ消息[调整通讯录成员] " + JsonUtils.toJson(msg));
		
		// 1) 清理掉成员的群组关系数据
		List<Long> groupIdList = groupPersonMapper.findGroupIdByMember(msg.getOrgId(), msg.getMemberId(), PersonType.MEMBER.getValue());
		groupPersonMapper.deleteByPerson(msg.getOrgId(), msg.getMemberId(), PersonType.MEMBER.getValue());
		// 2) 清理成员的群组范围数据
		groupRangeManager.deleteByRangeId(msg.getOrgId(), msg.getMemberId(), GroupRangeType.RANGE_MEMBER.getValue());
		// 3) 重置群组人员数
		groupPersonSizeManager.batchResetPersonSize(msg.getOrgId(), groupIdList, PersonType.MEMBER.getValue());
		
		// 重新以新员工方式同步进来
		ConfigMemberGroupDataMsg nmsg = BeanUtils.copyProperties(ConfigMemberGroupDataMsg.class, msg);
		this.receiveNewMemberWithoutFaceMsg(nmsg);
		
		// 根据状态同步云眼人员
		Member member = memberManager.getMember(msg.getOrgId(), msg.getMemberId());
		if(MemberStatus.USABLE.getValue() == member.getStatus()) {
			// 查询云眼是否存在
			if(StringUtils.isNotBlank(member.getVerifyFaceUrl())) {
				boolean cloudeyePersonExist = cloudeyePersonProxy.isPersonExist(msg.getOrgId(), msg.getMemberId());
				
				if(cloudeyePersonExist) {
					cloudeyePersonProxy.activePerson(msg.getOrgId(), msg.getMemberId());
				} else {
					cloudeyePersonProxy.addPerson(member);
				}
			}
		}
	}
	
	/**
	 * 接收删除员工消息
	 * @param msg
	 */
	@Subscribe
	public void receiveDeleteMemberMsg(ClearMemberRelationDataMsg msg) {
		logger.info("接收到MQ消息[删除成员] " + JsonUtils.toJson(msg));
		
		// 1) 清理掉成员的群组关系数据
		List<Long> groupIdList = groupPersonMapper.findGroupIdByMember(msg.getOrgId(), msg.getMemberId(), PersonType.MEMBER.getValue());
		groupPersonMapper.deleteByPerson(msg.getOrgId(), msg.getMemberId(), PersonType.MEMBER.getValue());
		// 2) 清理成员的群组范围数据
		groupRangeManager.deleteByRangeId(msg.getOrgId(), msg.getMemberId(), GroupRangeType.RANGE_MEMBER.getValue());
		// 3) 重置群组人员数
		groupPersonSizeManager.batchResetPersonSize(msg.getOrgId(), groupIdList, PersonType.MEMBER.getValue());
		// 4) 云眼删除
		
		PersonFullInfo person = cloudeyePersonProxy.getPerson(msg.getOrgId(), msg.getMemberId());
		if(person == null) return;
		
		if(person.getBaseInfo().getStatus() == 0) {
			cloudeyePersonProxy.disablePerson(msg.getOrgId(), msg.getMemberId());
		}
		
	}
	
	@Subscribe
	public void receiveDeleteGroupMsg(DeleteGroupRelationDataMsg msg) {
		logger.info("接收到MQ消息[删除组] " + JsonUtils.toJson(msg));
		logger.info("删除群组成员关系 ");
		groupPersonMapper.deleteByGroupId(msg.getOrgId(), msg.getGroupId());
	}

	@Override
	public List<Long> findGroupIdByMember(Long orgId, Long personId, Integer personType) {
		return groupPersonMapper.findGroupIdByMember(orgId, personId, personType);
	}
	
	private List<Long> getAddGroupIds(Long orgId, Long memberId, List<Long> finalGroupIds) {
		
		List<Long> clone_finalGroupIds = new ArrayList<>();
		clone_finalGroupIds.addAll(finalGroupIds);
		
		List<Long> existGroupIds = groupPersonMapper.findGroupIdByMember(orgId, memberId, PersonType.MEMBER.getValue());
		clone_finalGroupIds.removeAll(existGroupIds);
		return clone_finalGroupIds;
	}
	
	private List<Long> getRemoveGroupIds(Long orgId, Long memberId, List<Long> finalGroupIds) {
		
		List<Long> clone_existGroupIds = new ArrayList<>();
		
		List<Long> existGroupIds = groupPersonMapper.findGroupIdByMember(orgId, memberId, PersonType.MEMBER.getValue());
		clone_existGroupIds.addAll(existGroupIds);
		
		clone_existGroupIds.removeAll(finalGroupIds);
		return clone_existGroupIds;
	}

	@Override
	@Transactional
	public boolean resetGroupRelation(Long orgId, Long memberId, List<Long> groups, Integer personType) {
		
		BizAssert.notNull(orgId);
		BizAssert.notNull(memberId);
		BizAssert.notEmpty(groups);
		
		List<Long> addGroupIds = this.getAddGroupIds(orgId, memberId, groups); // 定位增加的群组
		this.addGroupRelations(orgId, memberId, addGroupIds, personType); // 增加人员的群组关系
		
		List<Long> removeGroupIds = this.getRemoveGroupIds(orgId, memberId, groups); // 定位移除的群组
		this.removeGroupRelations(orgId, memberId, removeGroupIds, personType); // 移除人员的群组关系
		
		// 重置群组人员数
		List<Long> groupIdList = new ArrayList<>();
		groupIdList.addAll(addGroupIds);
		groupIdList.addAll(removeGroupIds);
		groupPersonSizeManager.batchResetPersonSize(orgId, groupIdList, personType);
		
		// 修改云眼人员群组关系
		Member existMember = memberManager.getMember(orgId, memberId);
		if(StringUtils.isNotBlank(existMember.getVerifyFaceUrl())) {
			List<Long> groupIds = groupPersonMapper.findGroupIdByMember(orgId, memberId, personType);
			cloudeyePersonProxy.changeMemberGroupRelation(existMember, groupIds);
		}
		
		return false;
	}

	@Override
	public int getMemberSize(Long orgId, Long groupId, Integer personType) {
		
		BizAssert.notNull(orgId);
		BizAssert.notNull(groupId);
		BizAssert.notNull(personType);
		
		return groupPersonMapper.getPersonSize(orgId, groupId, personType);
	}


}
