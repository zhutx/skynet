package com.moredian.skynet.org.manager;

import java.util.List;

import com.moredian.skynet.org.domain.Area;

public interface AreaManager {
	
	List<Area> findChildren(Integer parentCode);
	
	List<Integer> findChildrenCode(Integer parentCode);
	
	Area getAreaByCode(Integer areaCode);
	
	List<Area> findByPrefix(String prefixCode, Integer areaLevel);
	
	boolean updateExtendsInfo(Long areaId, String extendsInfo);

}
