package com.moredian.fishnet.device.manager.impl;

import com.moredian.bee.common.exception.BizAssert;
import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.convertor.PaginationConvertor;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.device.domain.InventoryDevice;
import com.moredian.fishnet.device.domain.WhiteDevice;
import com.moredian.fishnet.device.domain.WhiteDeviceQueryCondition;
import com.moredian.fishnet.device.enums.YesNoFlag;
import com.moredian.fishnet.device.manager.InventoryDeviceManager;
import com.moredian.fishnet.device.manager.WhiteDeviceManager;
import com.moredian.fishnet.device.mapper.WhiteDeviceMapper;
import com.moredian.fishnet.device.model.WhiteDeviceInfo;
import com.moredian.fishnet.device.request.WhiteDeviceAddRequest;
import com.moredian.fishnet.device.request.WhiteDeviceQueryRequest;
import com.moredian.idgenerator.service.IdgeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WhiteDeviceManagerImpl implements WhiteDeviceManager {
	
	@Autowired
	private WhiteDeviceMapper whiteDeviceMapper;

	@Autowired
	private InventoryDeviceManager inventoryDeviceMapper;

	@SI
	private IdgeneratorService idgeneratorService;
	
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
	
	private Long genWhiteDeviceId() {
		return idgeneratorService.getNextIdByTypeName("com.xier.fishnet.device.WhiteDevice").getData();
	}

	@Override
	public boolean addWhiteDevice(WhiteDeviceAddRequest request) {
		
		BizAssert.notBlank(request.getSerialNumber());
		BizAssert.notBlank(request.getMacAddress());
		BizAssert.notBlank(request.getSecretKey());
		
		InventoryDevice whiteDevice = BeanUtils.copyProperties(InventoryDevice.class, request);
		whiteDevice.setActivityStatus(YesNoFlag.NO.getValue());
		
		whiteDeviceMapper.insert(whiteDevice);
		
		return true;
	}

	@Override
	public boolean deleteWhiteDevice(String deviceSn) {
		whiteDeviceMapper.delete(deviceSn);
		return true;
	}

}
