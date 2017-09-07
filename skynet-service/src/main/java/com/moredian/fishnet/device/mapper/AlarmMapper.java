package com.moredian.fishnet.device.mapper;

import com.moredian.fishnet.device.domain.Alarm;
import com.moredian.fishnet.device.domain.AlarmQueryCondition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AlarmMapper {
	
	int update(Alarm device);

	Alarm getById(@Param("id") Long id);
	
	//Alarm getBySerialNumber(@Param("serialNumber")String serialNumber);
	
	int getTotalCountByCondition(AlarmQueryCondition condition);
	
	List<Object> findPaginationByCondition(AlarmQueryCondition condition);
}
