package com.moredian.skynet.device.manager;

import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.skynet.device.domain.Alarm;
import com.moredian.skynet.device.model.AlarmInfo;
import com.moredian.skynet.device.request.AlarmQueryRequest;
import com.moredian.skynet.device.request.AlarmUpdateRequest;

public interface AlarmManager{
	 
	//Alarm getAlarmInfoBySerialNumber(String serialNumber);

	PaginationDomain<Alarm> getPaginationAlarm(AlarmQueryRequest request, Pagination<AlarmInfo> pagination);

	boolean updateAlarm(AlarmUpdateRequest request) ;
}
