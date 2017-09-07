package com.moredian.fishnet.org.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moredian.bee.common.exception.BizAssert;
import com.moredian.fishnet.org.domain.Area;
import com.moredian.fishnet.org.manager.AreaManager;
import com.moredian.fishnet.org.mapper.AreaMapper;

@Service
public class AreaManagerImpl implements AreaManager {
	
	@Autowired
	private AreaMapper areaMapper;

	@Override
	public List<Area> findChildren(Integer parentCode) {
		return areaMapper.findByParent(parentCode);
	}

	@Override
	public List<Integer> findChildrenCode(Integer parentCode) {
		return areaMapper.findChildrenCode(parentCode);
	}

	@Override
	public Area getAreaByCode(Integer areaCode) {
		return areaMapper.loadByCode(areaCode);
	}

	@Override
	public List<Area> findByPrefix(String prefixCode, Integer areaLevel) {
		return areaMapper.findByPrefix(prefixCode, areaLevel);
	}

	@Override
	public boolean updateExtendsInfo(Long areaId, String extendsInfo) {
		BizAssert.notNull(areaId);
		BizAssert.notBlank(extendsInfo);
		areaMapper.updateExtendsInfo(areaId, extendsInfo);
		return true;
	}

}
