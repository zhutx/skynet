package com.moredian.skynet.device.service.adapter;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.skynet.device.service.adapter.dto.DEquipmentDto;

public interface DEquipmentService {
	
	public ServiceResponse<DEquipmentDto> getDequipmentByUniqueNumber(String uniqueNumber);

}
