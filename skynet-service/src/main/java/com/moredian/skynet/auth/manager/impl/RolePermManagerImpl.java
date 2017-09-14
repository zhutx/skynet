package com.moredian.skynet.auth.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moredian.skynet.auth.manager.RolePermManager;
import com.moredian.skynet.auth.mapper.RolePermMapper;

@Service
public class RolePermManagerImpl implements RolePermManager {
	
	@Autowired
	private RolePermMapper rolePermMapper;

	@Override
	public List<Long> findPermIdByRoleId(Long roleId) {
		return rolePermMapper.findPermIdByRoleId(roleId);
	}

}
