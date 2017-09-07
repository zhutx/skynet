package com.moredian.fishnet.org.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moredian.bee.common.exception.BizAssert;
import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.common.utils.ExceptionUtils;
import com.moredian.bee.common.utils.JsonUtils;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.convertor.PaginationConvertor;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.bee.rmq.EventBus;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.common.model.msg.ConfigGroupRelationDataMsg;
import com.moredian.fishnet.common.model.msg.DeleteGroupRelationDataMsg;
import com.moredian.fishnet.common.model.msg.ResetGroupRelationDataMsg;
import com.moredian.fishnet.member.manager.MemberManager;
import com.moredian.fishnet.org.domain.Group;
import com.moredian.fishnet.org.domain.GroupQueryCondition;
import com.moredian.fishnet.org.enums.GroupType;
import com.moredian.fishnet.org.enums.OrgErrorCode;
import com.moredian.fishnet.org.enums.YesNoFlag;
import com.moredian.fishnet.org.manager.GroupManager;
import com.moredian.fishnet.org.mapper.GroupMapper;
import com.moredian.fishnet.org.model.GroupInfo;
import com.moredian.fishnet.org.request.GroupAddRequest;
import com.moredian.fishnet.org.request.GroupQueryRequest;
import com.moredian.idgenerator.service.IdgeneratorService;

@Service
public class GroupManagerImpl implements GroupManager {
	
	private static final Logger logger = LoggerFactory.getLogger(GroupManagerImpl.class);
	
	@Autowired
	private GroupMapper groupMapper;
	@SI
	private IdgeneratorService idgeneratorService;
	@Value("${rmq.switch}")
	private int rmqSwitch;
	@Autowired
	private MemberManager memberManager;
	
	private Long genGroupId() {
		return idgeneratorService.getNextIdByTypeName("com.moredian.fishnet.org.Group").getData();
	}

	@Override
	public Long addSimpleGroup(Long orgId, String groupName, boolean allMemberUse) {
		
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notBlank(groupName, "groupName must not be blank");
		
		Group group = groupMapper.loadByGroupName(orgId, groupName);
		if(group != null) ExceptionUtils.throwException(OrgErrorCode.GROUP_EXIST, OrgErrorCode.GROUP_EXIST.getMessage());
		
		group = new Group();
		group.setGroupId(genGroupId());
		group.setOrgId(orgId);
		group.setGroupCode(String.valueOf(group.getGroupId()));
		group.setGroupName(groupName);
		group.setGroupType(GroupType.CUSTOM.getValue());
		group.setSystemDefault(YesNoFlag.NO.getValue());
		group.setAllMemberFlag(allMemberUse?YesNoFlag.YES.getValue():YesNoFlag.NO.getValue());
		group.setMemberSize(0);
		groupMapper.insert(group);
		
		return group.getGroupId();
	}
	
	@Override
	@Transactional
	public Long addGroup(GroupAddRequest request) {
		BizAssert.notNull(request.getOrgId(), "orgId must not be null");
		BizAssert.notBlank(request.getGroupName(), "groupName must not be blank");
		
		Group group = groupMapper.loadByGroupName(request.getOrgId(), request.getGroupName());
		if(group != null) ExceptionUtils.throwException(OrgErrorCode.GROUP_EXIST, OrgErrorCode.GROUP_EXIST.getMessage());
		
		group = new Group();
		group.setGroupId(this.genGroupId());
		group.setOrgId(request.getOrgId());
		group.setGroupCode(String.valueOf(group.getGroupId()));
		group.setGroupName(request.getGroupName());
		group.setGroupType(GroupType.CUSTOM.getValue());
		group.setSystemDefault(YesNoFlag.NO.getValue());
		group.setAllMemberFlag(request.isAllMember()?YesNoFlag.YES.getValue():YesNoFlag.NO.getValue());
		group.setMemberSize(0);
		groupMapper.insert(group);
		
		if(YesNoFlag.YES.getValue() == rmqSwitch) {
			// 发出群组创建消息
			ConfigGroupRelationDataMsg msg = BeanUtils.copyProperties(ConfigGroupRelationDataMsg.class, group);
			msg.setDepts(request.getDepts());
			msg.setMembers(request.getMembers());
			EventBus.publish(msg);
			logger.info("发出MQ消息[新群组]: "+JsonUtils.toJson(msg));
		}
		
		return group.getGroupId();
	}

	@Override
	public boolean editGroup(Long orgId, Long groupId, String groupName) {
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notNull(groupId, "groupId must not be null");
		BizAssert.notBlank(groupName, "groupName must not be blank");
		
		Group existGroup = groupMapper.load(orgId, groupId);
		
		if(existGroup.getGroupName().equals(groupName)) {
			return true;
		}
		
		if(existGroup.getSystemDefault() == YesNoFlag.YES.getValue()) {
			ExceptionUtils.throwException(OrgErrorCode.GROUP_NAME_UPDATE_REFUSE, OrgErrorCode.GROUP_NAME_UPDATE_REFUSE.getMessage());
		}
		
		Group group = groupMapper.loadByGroupName(orgId, groupName);
		if(group != null) ExceptionUtils.throwException(OrgErrorCode.GROUP_EXIST, OrgErrorCode.GROUP_EXIST.getMessage());
		
		group = new Group();
		group.setOrgId(orgId);
		group.setGroupId(groupId);
		group.setGroupName(groupName);
		groupMapper.update(group);
		
		return true;
	}
	
	@Override
	public int countGroup(Long orgId) {
		return groupMapper.count(orgId);
	}
	
	@Override
	public Group getGroupById(Long orgId, Long groupId) {
		return groupMapper.load(orgId, groupId);
	}

	@Override
	@Transactional
	public boolean deleteGroup(Long orgId, Long groupId) {
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notNull(groupId, "groupId must not be null");
		
		Group group = groupMapper.load(orgId, groupId);
		if(YesNoFlag.YES.getValue() == group.getSystemDefault()) ExceptionUtils.throwException(OrgErrorCode.GROUP_REFUSE_DELETE, OrgErrorCode.GROUP_REFUSE_DELETE.getMessage());
		
		groupMapper.delete(orgId, groupId);
		
		if(YesNoFlag.YES.getValue() == rmqSwitch) {
			// 发出删除群组消息
			DeleteGroupRelationDataMsg msg = new DeleteGroupRelationDataMsg();
			msg.setOrgId(orgId);
			msg.setGroupId(groupId);
			EventBus.publish(msg);
			logger.info("发出MQ消息[删除群组]: "+JsonUtils.toJson(msg));
		}
		
		return true;
	}

	@Override
	public List<Group> findGroup(Long orgId) {
		return groupMapper.findAll(orgId);
	}
	
	private GroupQueryCondition buildCondition(GroupQueryRequest request) {
		return BeanUtils.copyProperties(GroupQueryCondition.class, request);
	}

	@Override
	public List<Group> findGroup(GroupQueryRequest request) {
		BizAssert.notNull(request.getOrgId());
		return groupMapper.findByCondition(this.buildCondition(request));
	}

	@Override
	@Transactional
	public boolean updateAllMemberFlag(Long orgId, Long groupId, Integer allMemberFlag) {
		
		Group group = groupMapper.load(orgId, groupId);
		if(group.getSystemDefault() != YesNoFlag.YES.getValue()) {
			groupMapper.updateAllMemberFlag(orgId, groupId, allMemberFlag);
			
			if(YesNoFlag.YES.getValue() == allMemberFlag) { // 切换为全员使用
				if(YesNoFlag.YES.getValue() == rmqSwitch) {
					ResetGroupRelationDataMsg msg = new ResetGroupRelationDataMsg();
					msg.setOrgId(orgId);
					msg.setGroupId(groupId);
					EventBus.publish(msg);
					logger.info("发出MQ消息[切换为全员组]: " + JsonUtils.toJson(msg));
				}
			}
		}
		
		return true;
	}
	
	@Override
	@Transactional
	public boolean justUpdateAllMemberFlag(Long orgId, Long groupId, Integer allMemberFlag) {
		
		Group group = groupMapper.load(orgId, groupId);
		if(group.getSystemDefault() == YesNoFlag.YES.getValue()) ExceptionUtils.throwException(OrgErrorCode.SYSGROUP_REFUSE_OPERA, OrgErrorCode.SYSGROUP_REFUSE_OPERA.getMessage());
		
		groupMapper.justUpdateAllMemberFlag(orgId, groupId, allMemberFlag);
		
		return true;
	}
	
	@Override
	public List<Long> findAllMemberUseGroupIds(Long orgId) {
		BizAssert.notNull(orgId);
		return groupMapper.findAllMemberUseGroupIds(orgId, YesNoFlag.YES.getValue());
	}

	@Override
	public Group getQYGroup(Long orgId) {
		BizAssert.notNull(orgId);
		return groupMapper.loadByGroupType(orgId, GroupType.ALLMEMBER.getValue());
	}

	@Override
	public Group getVisitorGroup(Long orgId) {
		BizAssert.notNull(orgId);
		return groupMapper.loadByGroupType(orgId, GroupType.VISITOR.getValue());
	}

	@Override
	public boolean updateMemberSize(Long orgId, Long groupId, boolean inc, int incOrDecSize) {
		BizAssert.notNull(orgId);
		BizAssert.notNull(groupId);
		if(inc) {
			groupMapper.incMemberSize(orgId, groupId, incOrDecSize);
		} else {
			groupMapper.decMemberSize(orgId, groupId, incOrDecSize);
		}
		return true;
	}
	
	@Override
	public boolean resetMemberSize(Long orgId, Long groupId, int memberSize) {
		BizAssert.notNull(orgId);
		BizAssert.notNull(groupId);
		groupMapper.updateMemberSize(orgId, groupId, memberSize);
		return true;
	}

	@Override
	public Group getGroupByName(Long orgId, String groupName) {
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notBlank(groupName, "groupName must not be blank");
		return groupMapper.loadByGroupName(orgId, groupName);
	}

	@Override
	public Group getGroupByCode(Long orgId, String groupCode) {
		BizAssert.notNull(orgId, "orgId must not be null");
		BizAssert.notBlank(groupCode, "groupCode must not be blank");
		return groupMapper.loadByGroupCode(orgId, groupCode);
	}

	@Override
	public List<Group> findGroupByIds(Long orgId, List<Long> groupIdList) {
		BizAssert.notNull(orgId);
		if(CollectionUtils.isEmpty(groupIdList)) return new ArrayList<>();
		
		return groupMapper.findByIds(orgId, groupIdList);
	}
	
	@Override
	public List<String> findGroupNameByIds(Long orgId, List<Long> groupIdList) {
		BizAssert.notNull(orgId);
		if(CollectionUtils.isEmpty(groupIdList)) return new ArrayList<>();
		
		return groupMapper.findNameByIds(orgId, groupIdList);
	}

	public static GroupQueryCondition groupQueryRequestToGroupQueryCondition(GroupQueryRequest request) {
		return BeanUtils.copyProperties(GroupQueryCondition.class, request);
	}
	
	private List<Group> groupInfoListToGroupList(List<GroupInfo> groupInfoList) {
		if (groupInfoList == null) return null;
		return BeanUtils.copyListProperties(Group.class, groupInfoList);
	}
	
	private PaginationDomain<Group> paginationGroupInfoToPaginationGroup(Pagination<GroupInfo> fromPagination) {
		PaginationDomain<Group> toPagination = PaginationConvertor.paginationToPaginationDomain(fromPagination, new PaginationDomain<Group>());
		if (toPagination == null)
			return null;
		toPagination.setData(groupInfoListToGroupList(fromPagination.getData()));
		return toPagination;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginationDomain<Group> findPaginationGroup(GroupQueryRequest request, Pagination<GroupInfo> pagination) {
		BizAssert.notNull(request.getOrgId(), "orgId must not be null");
		
		GroupQueryCondition condition = groupQueryRequestToGroupQueryCondition(request);
		PaginationDomain<Group> groupPagination = paginationGroupInfoToPaginationGroup(pagination);
		
		return this.getPagination(groupPagination, condition);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected  PaginationDomain getPagination(PaginationDomain pagination, GroupQueryCondition condition) {
        int totalCount = (Integer) groupMapper.getTotalCountByCondition(condition);
        pagination.setTotalCount(totalCount);
        if (totalCount > 0) {
        	condition.setStartRow(pagination.getStartRow());
        	condition.setPageSize(pagination.getPageSize());
        	pagination.setData(groupMapper.findPaginationByCondition(condition));
        }
        return pagination;
    }

	@Override
	public List<Long> findGroupIdByTypes(Long orgId, List<Integer> groupTypeList) {
		if(CollectionUtils.isEmpty(groupTypeList)) return new ArrayList<>();
		return groupMapper.findGroupIdByTypes(orgId, groupTypeList);
	}

}
