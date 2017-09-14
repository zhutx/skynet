package com.moredian.skynet.org.service.adapter;

import java.util.List;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.skynet.org.service.adapter.dto.AreaDto;

public interface AreaService {
	
	ServiceResponse<List<AreaDto>> getAreaListByParentCode(Integer parentCode);
	
	ServiceResponse<AreaDto> getAreaByCode(Integer areaCode);

}