package com.moredian.fishnet.device.service.impl;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.convertor.PaginationConvertor;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.device.domain.InventoryDevice;
import com.moredian.fishnet.device.manager.WhiteDeviceManager;
import com.moredian.fishnet.device.model.WhiteDeviceInfo;
import com.moredian.fishnet.device.request.WhiteDeviceAddRequest;
import com.moredian.fishnet.device.request.WhiteDeviceQueryRequest;
import com.moredian.fishnet.device.service.WhiteDeviceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@SI	
public class WhiteDeviceServiceImpl implements WhiteDeviceService {
	
	@Autowired
	private WhiteDeviceManager whiteDeviceManager;
	
	private WhiteDeviceInfo whiteDeviceToWhiteDeviceInfo(InventoryDevice whiteDevice) {
		return BeanUtils.copyProperties(WhiteDeviceInfo.class, whiteDevice);
	}
	
	private List<WhiteDeviceInfo> whiteDeviceListToWhiteDeviceInfoList(List<InventoryDevice> whiteDeviceList) {
		if (whiteDeviceList == null) return null;
		
		List<WhiteDeviceInfo> response = new ArrayList<>();
		for(InventoryDevice device : whiteDeviceList) {
			response.add(whiteDeviceToWhiteDeviceInfo(device));
		}
		
		return response;
	}
	
	private Pagination<WhiteDeviceInfo> paginationWhiteDeviceToPaginationWhiteDeviceInfo(PaginationDomain<InventoryDevice> fromPagination) {
		Pagination<WhiteDeviceInfo> toPagination = PaginationConvertor.paginationDomainToPagination(fromPagination, new Pagination<WhiteDeviceInfo>());
		if (toPagination == null)
			return null;
		toPagination.setData(whiteDeviceListToWhiteDeviceInfoList(fromPagination.getData()));
		return toPagination;
	}

	@Override
	public Pagination<WhiteDeviceInfo> findPaginationWhiteDevice(Pagination<WhiteDeviceInfo> pagination,
			WhiteDeviceQueryRequest request) {
		
		PaginationDomain<InventoryDevice> paginationWhiteDevice = whiteDeviceManager.findPaginationWhiteDevice(request, pagination);
		return paginationWhiteDeviceToPaginationWhiteDeviceInfo(paginationWhiteDevice);
	}

	@Override
	public ServiceResponse<Boolean> addWhiteDevice(WhiteDeviceAddRequest request) {
		
		boolean result = whiteDeviceManager.addWhiteDevice(request);
		
		return new ServiceResponse<Boolean>(true, null, result);
	}

	@Override
	public ServiceResponse<Boolean> deleteWhiteDevice(String deviceSn) {
		boolean result = whiteDeviceManager.deleteWhiteDevice(deviceSn);
		return new ServiceResponse<Boolean>(true, null, result);
	}

}
