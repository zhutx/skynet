package com.moredian.skynet.org.service.adapter.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.org.domain.Area;
import com.moredian.skynet.org.manager.AreaManager;
import com.moredian.skynet.org.service.adapter.AreaService;
import com.moredian.skynet.org.service.adapter.dto.AreaDto;

@Component("adapterAreaService")
@SI
public class AreaServiceImpl implements AreaService {
	
	private static final Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);
	
	@Autowired
	private AreaManager areaManager;
	
	private List<AreaDto> areaListToAreaListDto(List<Area> areaList) {
		List<AreaDto> areaDtoList = new ArrayList<>();
		if(CollectionUtils.isEmpty(areaList)) return areaDtoList;
		
		return BeanUtils.copyListProperties(AreaDto.class, areaList);
	}

	@Override
	public ServiceResponse<List<AreaDto>> getAreaListByParentCode(Integer parentCode) {
		logger.debug("###################getAreaListByParentCode###################");
		List<Area> areaList = areaManager.findChildren(parentCode);
		return new ServiceResponse<List<AreaDto>>(true, null, this.areaListToAreaListDto(areaList));
	}

	@Override
	public ServiceResponse<AreaDto> getAreaByCode(Integer areaCode) {
		logger.debug("###################getAreaByCode###################");
		Area area = areaManager.getAreaByCode(areaCode);
		AreaDto areaDto = BeanUtils.copyProperties(AreaDto.class, area);
		return new ServiceResponse<AreaDto>(true, null, areaDto);
	}

}
