package com.moredian.skynet.auth.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moredian.skynet.auth.manager.OperRoleManager;
import com.moredian.skynet.auth.mapper.OperRoleMapper;

@Service
public class OperRoleManagerImpl implements OperRoleManager {
	
	@Autowired
	private OperRoleMapper operRoleMapper;

	@Override
	public List<Long> findRoleIdByOperId(Long operId) {
		return operRoleMapper.findRoleIdByOperId(operId);
	}

}
