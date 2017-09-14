package com.moredian.skynet.member.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moredian.skynet.member.domain.UserRelation;
import com.moredian.skynet.member.manager.UserRelationManager;
import com.moredian.skynet.member.mapper.UserRelationMapper;

@Service
public class UserRelationManagerImpl implements UserRelationManager {
	
	@Autowired
	private UserRelationMapper userRelationMapper;

	@Override
	public List<UserRelation> selectSubOrgIdOwner(Long orgId, Long subOrgId, int relation) {
		return userRelationMapper.findVisibleRelation(orgId, subOrgId, relation);
	}

}
