package com.moredian.fishnet.device.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moredian.bee.common.exception.BizAssert;
import com.moredian.bee.mybatis.convertor.PaginationConvertor;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.fishnet.device.domain.Alarm;
import com.moredian.fishnet.device.domain.AlarmQueryCondition;
import com.moredian.fishnet.device.manager.AlarmManager;
import com.moredian.fishnet.device.mapper.AlarmMapper;
import com.moredian.fishnet.device.model.AlarmInfo;
import com.moredian.fishnet.device.request.AlarmQueryRequest;
import com.moredian.fishnet.device.request.AlarmUpdateRequest;

@Service
public class AlarmManagerImpl implements  AlarmManager{

	@Autowired
	private AlarmMapper alarmMapper;
	
//	public Alarm getAlarmInfoBySerialNumber(String serialNumber){
//		BizAssert.notNull(serialNumber, "uniqueNumber must not be null");
//		return alarmMapper.getBySerialNumber(serialNumber);
//	}
 
	public  boolean updateAlarm(AlarmUpdateRequest request) {
		BizAssert.notNull(request.getSerialNumber(), "serialNumber must not be null");
		
		Alarm model = alarmMapper.getById(request.getId());
		BizAssert.notNull(model, "Alarm is not exist");
		 model.setUpdatedAt(request.getUpdatedAt());
		 model.setDeal(request.getDeal());
		 model.setDealOpinion(request.getDealOpinion());
		 model.setSerialNumber(request.getSerialNumber());
		int result = alarmMapper.update(model);
		
		if(result > 0) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginationDomain<Alarm> getPaginationAlarm(AlarmQueryRequest request, Pagination<AlarmInfo> pagination) {	
		AlarmQueryCondition condition = convertQueryParameter(request);
		PaginationDomain<Alarm> devicePagination = convertPaginationModel(pagination);
		
		return this.getPagination(devicePagination, condition);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private  PaginationDomain getPagination(PaginationDomain pagination, AlarmQueryCondition condition) {
        int totalCount = (Integer) alarmMapper.getTotalCountByCondition(condition);
        pagination.setTotalCount(totalCount);
        if (totalCount > 0) {
        	condition.setStartRow(pagination.getStartRow());
        	condition.setPageSize(pagination.getPageSize());
        	pagination.setData(alarmMapper.findPaginationByCondition(condition));
        }
        return pagination;
    }
	
	@SuppressWarnings("rawtypes")
	public int getStartRow(PaginationDomain pagination) {
        if (pagination.getPageNo() > 1) {
            return (pagination.getPageSize() * (pagination.getPageNo() - 1));
        } else {
            return 0;
        }
	}
	
	private AlarmQueryCondition convertQueryParameter(AlarmQueryRequest request) {
		AlarmQueryCondition condition = new AlarmQueryCondition();
		condition.setDeal(request.getDeal());
		condition.setEndDate(request.getEndDate());
		condition.setMainType(request.getMainType());
		condition.setSerialNumber(request.getSerialNumber());
		condition.setSeverity(request.getSeverity());
		condition.setStartDate(request.getStartDate());
		condition.setSubType(request.getSubType());
		return condition;
	}
	
	private Alarm convertModel(AlarmInfo info){
		Alarm model = new Alarm();
		model.setBody(info.getBody());
		model.setCreatedAt(info.getCreatedAt());
		model.setDeal(info.getDeal());
		model.setDealOpinion(info.getDealOpinion());
		model.setMainType(info.getMainType());
		model.setSerialNumber(info.getSerialNumber());
		model.setSeverity(info.getSeverity());
		model.setSubType(info.getSubType());
		model.setUpdatedAt(info.getUpdatedAt());
		return model;
	}
	
	private PaginationDomain<Alarm> convertPaginationModel(Pagination<AlarmInfo> fromPagination) {
		PaginationDomain<Alarm> toPagination = PaginationConvertor.paginationToPaginationDomain(fromPagination, new PaginationDomain<Alarm>());
		if (toPagination == null)
			return null;
		List<AlarmInfo> list = fromPagination.getData();
		if (list == null) return null;
		
		List<Alarm> response = new ArrayList<>();
		for(AlarmInfo info : list) {
			response.add(convertModel(info));
		}		 
		toPagination.setData(response);
		return toPagination;
	}
}
