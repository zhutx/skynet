package com.moredian.fishnet.member.manager;

import java.util.List;

import com.moredian.fishnet.member.domain.UserRelation;

public interface UserRelationManager {
	
	List<UserRelation> selectSubOrgIdOwner(Long orgId, Long subOrgId, int relation);

}
