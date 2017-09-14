package com.moredian.skynet.device.service.adapter;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.skynet.device.service.adapter.dto.CameraInfoDto;

public interface CameraInfoService {
	
	ServiceResponse<CameraInfoDto> getCameraInfoByOrgiEId(Long orgiEId, Long orgId);

}
