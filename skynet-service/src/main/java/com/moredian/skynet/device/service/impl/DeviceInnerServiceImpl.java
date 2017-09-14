package com.moredian.skynet.device.service.impl;

import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.convertor.PaginationConvertor;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.device.domain.Alarm;
import com.moredian.skynet.device.manager.AlarmManager;
import com.moredian.skynet.device.manager.DeviceMonitorManager;
import com.moredian.skynet.device.model.AlarmInfo;
import com.moredian.skynet.device.model.DeviceImageVersion;
import com.moredian.skynet.device.model.DeviceStateInfo;
import com.moredian.skynet.device.request.*;
import com.moredian.skynet.device.service.DeviceInnerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SI
public class DeviceInnerServiceImpl implements DeviceInnerService {
	
	@Autowired
	private DeviceMonitorManager deviceMonitorManager;

	@Autowired
	private AlarmManager alarmManager;
	
	@Override
	public DeviceStateInfo getStatus(String serialNumber) {
		return deviceMonitorManager.getStatus(serialNumber);
	}

	@Override
	public Map<String, DeviceStateInfo> getStatusList(StatusRequest request) {
		return null;//return deviceMonitorManager.getStatusList(request);
	}

	@Override
	public DeviceStateInfo upgradeDevice(UpgradeRequest request) {
		return deviceMonitorManager.upgradeDevice(request);
	}
	
	@Override
	public DeviceImageVersion getDeviceVersion(String serialNumber) {
		return deviceMonitorManager.getDeviceVersion(serialNumber);
	}

	@Override
	public DeviceStateInfo rebootDevice(RebootRequest request) {
		return deviceMonitorManager.rebootDevice(request);
	}

	@Override
	public DeviceStateInfo transferDevice(TransferRequest request) {
		return deviceMonitorManager.transferDevice(request);
	}

//	@Override
//	public AlarmInfo getAlarmInfoBySerialNumber(String serialNumber) {
//		Alarm model = alarmManager.getAlarmInfoBySerialNumber(serialNumber);
//		return convertInfo(model);
//	}

	@Override
	public boolean updateAalarm(AlarmUpdateRequest request) {
		return alarmManager.updateAlarm(request);
	}

	@Override
	public Pagination<AlarmInfo> getPaginationAlarm(Pagination<AlarmInfo> pagination, AlarmQueryRequest request) {
		PaginationDomain<Alarm> pageModel= alarmManager.getPaginationAlarm(request, pagination);		
		return convertPaginationInfo(pageModel);
	}
 
	private AlarmInfo convertInfo(Alarm model){
		AlarmInfo info = new AlarmInfo();
		info.setBody(model.getBody());
		info.setCreatedAt(model.getCreatedAt());
	    info.setDeal(model.getDeal());
	    info.setDealOpinion(model.getDealOpinion());
	    info.setMainType(model.getMainType());
	    info.setSerialNumber(model.getSerialNumber());
	    info.setSeverity(model.getSeverity());
	    info.setSubType(model.getSubType());
	    info.setUpdatedAt(model.getUpdatedAt());
		return info;
	}
	
	private  Pagination<AlarmInfo> convertPaginationInfo(PaginationDomain<Alarm> fromPagination) {
		Pagination<AlarmInfo> toPagination = PaginationConvertor.paginationDomainToPagination(fromPagination, new Pagination<AlarmInfo>());
		if (toPagination == null)
			return null;
		List<Alarm> list = fromPagination.getData();
		if (list == null) return null;
		
		List<AlarmInfo> response = new ArrayList<>();
		for(Alarm model : list) {
			response.add(convertInfo(model));
		}		 
		toPagination.setData(response);
		return toPagination;
	}

}
