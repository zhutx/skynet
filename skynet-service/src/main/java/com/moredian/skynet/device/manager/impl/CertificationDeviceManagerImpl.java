package com.moredian.skynet.device.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.convertor.PaginationConvertor;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.skynet.device.domain.CertificationDevice;
import com.moredian.skynet.device.domain.CertificationDeviceQueryCondition;
import com.moredian.skynet.device.enums.DeviceType;
import com.moredian.skynet.device.manager.CertificationDeviceManager;
import com.moredian.skynet.device.mapper.CertificationDeviceMapper;
import com.moredian.skynet.device.request.CertificationDeviceSearchRequest;
import com.moredian.skynet.device.response.CertificationDeviceInfo;

@Service
public class CertificationDeviceManagerImpl implements CertificationDeviceManager {
	
	@Autowired
	private CertificationDeviceMapper deviceMapper;
	
	public CertificationDeviceQueryCondition deviceQueryRequestToDeviceQueryCondition(CertificationDeviceSearchRequest request) {
		CertificationDeviceQueryCondition condition = BeanUtils.copyProperties(CertificationDeviceQueryCondition.class, request);
		condition.setDeviceType(DeviceType.BOARD_EVIDENCE.getValue());
		return condition;
	}
	
	public PaginationDomain<CertificationDevice> paginationDeviceInfoToPaginationDevice(Pagination<CertificationDeviceInfo> fromPagination) {
		return PaginationConvertor.paginationToPaginationDomain(fromPagination, new PaginationDomain<CertificationDevice>());
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginationDomain<CertificationDevice> findDevice(Pagination<CertificationDeviceInfo> pagination,
			CertificationDeviceSearchRequest request) {
		
		CertificationDeviceQueryCondition queryCondition = deviceQueryRequestToDeviceQueryCondition(request);
		PaginationDomain<CertificationDevice> paginationDevice = paginationDeviceInfoToPaginationDevice(pagination);
		
		return this.getPagination(paginationDevice, queryCondition);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected PaginationDomain getPagination(PaginationDomain pagination, CertificationDeviceQueryCondition queryCondition) {
        int totalCount = (Integer) deviceMapper.getTotalCountByCondition(queryCondition);
        pagination.setTotalCount(totalCount);
        if (totalCount > 0) {
        	queryCondition.setStartRow(pagination.getStartRow());
        	queryCondition.setPageSize(pagination.getPageSize());
        	pagination.setData(deviceMapper.findPaginationByCondition(queryCondition));
        }
        return pagination;
    }

}
