package com.moredian.fishnet.device.service.adapter;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.fishnet.device.service.adapter.dto.CameraInfoDto;

public interface CameraInfoService {
	
	ServiceResponse<CameraInfoDto> getCameraInfoByOrgiEId(Long orgiEId, Long orgId);

}
