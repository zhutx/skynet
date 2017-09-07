package com.moredian.fishnet.device.service.adapter;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.fishnet.device.service.adapter.dto.EquipmentDto;

public interface EquipmentService {
	
	ServiceResponse<EquipmentDto> getEquipmentByUniqueNumber(Long orgId, String uniqueNumber);

}
