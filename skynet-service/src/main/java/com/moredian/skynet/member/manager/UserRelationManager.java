package com.moredian.skynet.member.manager;

import java.util.List;

import com.moredian.skynet.member.domain.UserRelation;

public interface UserRelationManager {
	
	List<UserRelation> selectSubOrgIdOwner(Long orgId, Long subOrgId, int relation);

}
