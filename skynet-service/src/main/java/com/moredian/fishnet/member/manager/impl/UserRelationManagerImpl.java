package com.moredian.fishnet.member.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moredian.fishnet.member.domain.UserRelation;
import com.moredian.fishnet.member.manager.UserRelationManager;
import com.moredian.fishnet.member.mapper.UserRelationMapper;

@Service
public class UserRelationManagerImpl implements UserRelationManager {
	
	@Autowired
	private UserRelationMapper userRelationMapper;

	@Override
	public List<UserRelation> selectSubOrgIdOwner(Long orgId, Long subOrgId, int relation) {
		return userRelationMapper.findVisibleRelation(orgId, subOrgId, relation);
	}

}
