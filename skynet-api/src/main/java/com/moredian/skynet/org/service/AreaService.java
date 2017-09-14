package com.moredian.skynet.org.service;

import java.util.List;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.skynet.org.model.AreaInfo;

public interface AreaService {
	
	List<AreaInfo> findChildren(Integer parentCode);
	
	List<Integer> findChildrenCode(Integer parentCode);
	
	AreaInfo getAreaByCode(Integer areaCode);
	
	List<AreaInfo> findByPrefix(String prefixCode, Integer areaLevel);
	
	ServiceResponse<Boolean> updateExtendsInfo(Long areaId, String extendsInfo);

}
