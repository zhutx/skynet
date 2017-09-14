package com.moredian.skynet.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.moredian.skynet.member.domain.GroupPerson;

@Mapper
public interface GroupPersonMapper {
	
	int insert(GroupPerson groupPerson);
	
	int delete(GroupPerson groupPerson);
	
	List<Long> findPersonId(@Param("orgId")Long orgId, @Param("groupId")Long groupId, @Param("personType")Integer personType);
	
	List<GroupPerson> findPerson(@Param("orgId")Long orgId, @Param("groupId")Long groupId, @Param("personType")Integer personType);
	
	int deleteByGroupId(@Param("orgId")Long orgId, @Param("groupId")Long groupId);
	
	List<Long> findGroupIdByMember(@Param("orgId")Long orgId, @Param("personId")Long personId, @Param("personType")Integer personType);
	
	int getPersonSize(@Param("orgId")Long orgId, @Param("groupId")Long groupId, @Param("personType")Integer personType);
	
	int deleteByPerson(@Param("orgId")Long orgId, @Param("personId")Long personId, @Param("personType")Integer personType);
	
}
