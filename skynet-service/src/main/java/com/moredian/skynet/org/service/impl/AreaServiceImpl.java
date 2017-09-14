package com.moredian.skynet.org.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.org.domain.Area;
import com.moredian.skynet.org.manager.AreaManager;
import com.moredian.skynet.org.model.AreaInfo;
import com.moredian.skynet.org.service.AreaService;

@SI
public class AreaServiceImpl implements AreaService {
	
	@Autowired
	private AreaManager areaManager;
	
	private List<AreaInfo> areaListToAreaInfoList(List<Area> areaList) {
		return BeanUtils.copyListProperties(AreaInfo.class, areaList);
	}

	@Override
	public List<AreaInfo> findChildren(Integer parentCode) {
		List<Area> areaList = areaManager.findChildren(parentCode);
		return this.areaListToAreaInfoList(areaList);
	}
	
	@Override
	public List<Integer> findChildrenCode(Integer parentCode) {
		return areaManager.findChildrenCode(parentCode);
	}

	private AreaInfo areaToAreaInfo(Area area) {
		return BeanUtils.copyProperties(AreaInfo.class, area);
	}

	@Override
	public AreaInfo getAreaByCode(Integer areaCode) {
		Area area = areaManager.getAreaByCode(areaCode);
		return this.areaToAreaInfo(area);
	}

	@Override
	public List<AreaInfo> findByPrefix(String prefixCode, Integer areaLevel) {
		List<Area> areaList = areaManager.findByPrefix(prefixCode, areaLevel);
		return this.areaListToAreaInfoList(areaList);
	}

	@Override
	public ServiceResponse<Boolean> updateExtendsInfo(Long areaId, String extendsInfo) {
		boolean result = areaManager.updateExtendsInfo(areaId, extendsInfo);
		return new ServiceResponse<Boolean>(true, null, result);
	}

}
