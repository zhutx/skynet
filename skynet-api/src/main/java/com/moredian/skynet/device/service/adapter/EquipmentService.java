package com.moredian.skynet.device.service.adapter;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.skynet.device.service.adapter.dto.EquipmentDto;

public interface EquipmentService {
	
	ServiceResponse<EquipmentDto> getEquipmentByUniqueNumber(Long orgId, String uniqueNumber);

}
