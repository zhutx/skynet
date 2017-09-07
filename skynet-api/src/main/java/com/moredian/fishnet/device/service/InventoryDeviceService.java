package com.moredian.fishnet.device.service;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.fishnet.device.request.WhiteDeviceAddRequest;
import com.moredian.fishnet.device.request.WhiteDeviceStatusRequest;

/**
 * Created by xxu on 2017/6/5.
 */
public interface InventoryDeviceService {

    ServiceResponse<Boolean> isWhiteDeviceActivated(WhiteDeviceStatusRequest request);

    ServiceResponse<Boolean> addWhiteDevice(WhiteDeviceAddRequest request);


}
