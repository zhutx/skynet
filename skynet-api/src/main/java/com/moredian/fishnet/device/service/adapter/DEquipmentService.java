package com.moredian.fishnet.device.service.adapter;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.fishnet.device.service.adapter.dto.DEquipmentDto;

public interface DEquipmentService {
	
	public ServiceResponse<DEquipmentDto> getDequipmentByUniqueNumber(String uniqueNumber);

}
