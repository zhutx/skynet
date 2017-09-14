package com.moredian.skynet.org.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.moredian.skynet.org.domain.Group;
import com.moredian.skynet.org.domain.GroupQueryCondition;

@Mapper
public interface GroupMapper {
	
	int insert(Group group);
	
	int update(Group group);
	
	Group loadByGroupName(@Param("orgId")Long orgId, @Param("groupName")String groupName);
	
	Group loadByGroupCode(@Param("orgId")Long orgId, @Param("groupCode")String groupCode);
	
	int incMemberSize(@Param("orgId")Long orgId, @Param("groupId")Long groupId, @Param("memberSize")Integer memberSize);
	
	int decMemberSize(@Param("orgId")Long orgId, @Param("groupId")Long groupId, @Param("memberSize")Integer memberSize);
	
	int updateMemberSize(@Param("orgId")Long orgId, @Param("groupId")Long groupId, @Param("memberSize")Integer memberSize);
	
	int count(Long orgId);
	
	Group load(@Param("orgId")Long orgId, @Param("groupId")Long groupId);
	
	int delete(@Param("orgId")Long orgId, @Param("groupId")Long groupId);
	
	List<Long> findAllMemberUseGroupIds(@Param("orgId")Long orgId, @Param("allMemberFlag")Integer allMemberFlag);
	
	List<Group> findAll(Long orgId);
	
	List<Group> findByCondition(GroupQueryCondition condition);
	
	int updateAllMemberFlag(@Param("orgId")Long orgId, @Param("groupId")Long groupId, @Param("allMemberFlag")Integer allMemberFlag);
	
	int justUpdateAllMemberFlag(@Param("orgId")Long orgId, @Param("groupId")Long groupId, @Param("allMemberFlag")Integer allMemberFlag);
	
	Group loadByGroupType(@Param("orgId")Long orgId, @Param("groupType")Integer groupType);
	
	List<Group> findByIds(@Param("orgId")Long orgId, @Param("groupIdList")List<Long> groupIdList);
	
	List<String> findNameByIds(@Param("orgId")Long orgId, @Param("groupIdList")List<Long> groupIdList);
	
	int getTotalCountByCondition(GroupQueryCondition condition);
	
	List<Object> findPaginationByCondition(GroupQueryCondition condition);
	
	List<Long> findGroupIdByTypes(@Param("orgId")Long orgId, @Param("groupTypeList")List<Integer> groupTypeList);

}
