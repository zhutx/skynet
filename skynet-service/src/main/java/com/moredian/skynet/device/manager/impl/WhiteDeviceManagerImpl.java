package com.moredian.skynet.device.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moredian.bee.common.exception.BizAssert;
import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.convertor.PaginationConvertor;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.idgenerator.service.IdgeneratorService;
import com.moredian.skynet.device.domain.DeviceWhite;
import com.moredian.skynet.device.domain.InventoryDevice;
import com.moredian.skynet.device.domain.WhiteDeviceQueryCondition;
import com.moredian.skynet.device.enums.DeviceWhiteStatus;
import com.moredian.skynet.device.manager.WhiteDeviceManager;
import com.moredian.skynet.device.mapper.DeviceWhiteMapper;
import com.moredian.skynet.device.mapper.WhiteDeviceMapper;
import com.moredian.skynet.device.model.WhiteDeviceInfo;
import com.moredian.skynet.device.request.DeviceWhiteAddRequest;
import com.moredian.skynet.device.request.WhiteDeviceQueryRequest;

@Service
public class WhiteDeviceManagerImpl implements WhiteDeviceManager {
	
	@Autowired
	private WhiteDeviceMapper whiteDeviceMapper;
	@Autowired
	private DeviceWhiteMapper deviceWhiteMapper;

	@SI
	private IdgeneratorService idgeneratorService;
	
	private Long genPrimaryKey(String name) {
		return idgeneratorService.getNextIdByTypeName(name).getData();
	}
	
	public static WhiteDeviceQueryCondition whiteDeviceQueryRequestToWhiteDeviceQueryCondition(WhiteDeviceQueryRequest request) {
		return BeanUtils.copyProperties(WhiteDeviceQueryCondition.class, request);
	}
	
	private List<InventoryDevice> whiteDeviceInfoListToWhiteDeviceList(List<WhiteDeviceInfo> whiteDeviceInfoList) {
		if (whiteDeviceInfoList == null) return null;
		return BeanUtils.copyListProperties(InventoryDevice.class, whiteDeviceInfoList);
	}
	
	private PaginationDomain<InventoryDevice> paginationWhiteDeviceInfoToPaginationWhiteDevice(Pagination<WhiteDeviceInfo> fromPagination) {
		PaginationDomain<InventoryDevice> toPagination = PaginationConvertor.paginationToPaginationDomain(fromPagination, new PaginationDomain<InventoryDevice>());
		if (toPagination == null)
			return null;
		toPagination.setData(whiteDeviceInfoListToWhiteDeviceList(fromPagination.getData()));
		return toPagination;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected  PaginationDomain getPagination(PaginationDomain pagination, WhiteDeviceQueryCondition condition) {
        int totalCount = whiteDeviceMapper.getTotalCountByCondition(condition);
        pagination.setTotalCount(totalCount);
        if (totalCount > 0) {
        	condition.setStartRow(pagination.getStartRow());
        	condition.setPageSize(pagination.getPageSize());
        	pagination.setData(whiteDeviceMapper.findPaginationByCondition(condition));
        }
        return pagination;
    }

	@SuppressWarnings("unchecked")
	@Override
	public PaginationDomain<InventoryDevice> findPaginationWhiteDevice(WhiteDeviceQueryRequest request,
			Pagination<WhiteDeviceInfo> pagination) {
		
		WhiteDeviceQueryCondition condition = whiteDeviceQueryRequestToWhiteDeviceQueryCondition(request);
		PaginationDomain<InventoryDevice> devicePagination = paginationWhiteDeviceInfoToPaginationWhiteDevice(pagination);
		
		return this.getPagination(devicePagination, condition);
	}
	
	private DeviceWhite requestToDomain(DeviceWhiteAddRequest request) {
		DeviceWhite deviceWhite = BeanUtils.copyProperties(DeviceWhite.class, request);
		deviceWhite.setDeviceWhiteId(this.genPrimaryKey(DeviceWhite.class.getName()));
		deviceWhite.setStatus(DeviceWhiteStatus.USABLE.getValue());
		return deviceWhite;
	}
	
	@Override
	public boolean addWhiteDevice(DeviceWhiteAddRequest request) {
		
		BizAssert.notNull(request.getDeviceType());
		BizAssert.notBlank(request.getDeviceSn());
		BizAssert.notBlank(request.getDeviceMac());
		BizAssert.notBlank(request.getSecretKey());
		BizAssert.notNull(request.getBindOrgId());
		
		deviceWhiteMapper.insert(this.requestToDomain(request));
		
		return true;
	}

	@Override
	public boolean deleteWhiteDevice(String deviceSn) {
		whiteDeviceMapper.delete(deviceSn);
		return true;
	}

}
