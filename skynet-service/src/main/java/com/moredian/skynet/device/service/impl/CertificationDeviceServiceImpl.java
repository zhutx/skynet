package com.moredian.skynet.device.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.convertor.PaginationConvertor;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.device.domain.CertificationDevice;
import com.moredian.skynet.device.manager.CertificationDeviceManager;
import com.moredian.skynet.device.request.CertificationDeviceSearchRequest;
import com.moredian.skynet.device.response.CertificationDeviceInfo;
import com.moredian.skynet.device.service.CertificationDeviceService;
import com.moredian.skynet.org.service.AreaService;

@SI
public class CertificationDeviceServiceImpl implements CertificationDeviceService {
	
	@Autowired
	private CertificationDeviceManager certificationDeviceManager;
	@SI
	private AreaService areaService;
	
	private List<CertificationDeviceInfo> deviceListToDeviceInfoList(List<CertificationDevice> deviceList) {
		List<CertificationDeviceInfo> list = new ArrayList<CertificationDeviceInfo>();
		if(CollectionUtils.isEmpty(deviceList)) return list;
		
		for(CertificationDevice device : deviceList) {
			CertificationDeviceInfo item = BeanUtils.copyProperties(CertificationDeviceInfo.class, device);
			item.setDistrictName(areaService.getAreaByCode(device.getDistrictCode()).getAreaName());
			list.add(item);
		}
		return list;
	}

	private Pagination<CertificationDeviceInfo> paginationDeviceToPaginationDeviceInfo(PaginationDomain<CertificationDevice> fromPagination) {
		Pagination<CertificationDeviceInfo> toPagination = PaginationConvertor.paginationDomainToPagination(fromPagination, new Pagination<CertificationDeviceInfo>());
		if (toPagination == null)
			return null;
		toPagination.setData(deviceListToDeviceInfoList(fromPagination.getData()));
		return toPagination;
	}
	
	@Override
	public Pagination<CertificationDeviceInfo> findDevice(Pagination<CertificationDeviceInfo> pagination,
			CertificationDeviceSearchRequest request) {
		
		PaginationDomain<CertificationDevice> paginationDevice = certificationDeviceManager.findDevice(pagination, request);
		
		return paginationDeviceToPaginationDeviceInfo(paginationDevice);
	}

}
