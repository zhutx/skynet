package com.moredian.fishnet.device.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.moredian.fishnet.device.domain.CertificationDevice;
import com.moredian.fishnet.device.domain.CertificationDeviceQueryCondition;

@Mapper
public interface CertificationDeviceMapper {
	
	int getTotalCountByCondition(CertificationDeviceQueryCondition condition);
	
	List<CertificationDevice> findPaginationByCondition(CertificationDeviceQueryCondition condition);

}
