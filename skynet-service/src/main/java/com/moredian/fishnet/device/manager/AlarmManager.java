package com.moredian.fishnet.device.manager;

import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.fishnet.device.domain.Alarm;
import com.moredian.fishnet.device.model.AlarmInfo;
import com.moredian.fishnet.device.request.AlarmQueryRequest;
import com.moredian.fishnet.device.request.AlarmUpdateRequest;

public interface AlarmManager{
	 
	//Alarm getAlarmInfoBySerialNumber(String serialNumber);

	PaginationDomain<Alarm> getPaginationAlarm(AlarmQueryRequest request, Pagination<AlarmInfo> pagination);

	boolean updateAlarm(AlarmUpdateRequest request) ;
}
