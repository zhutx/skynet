package com.moredian.skynet.device.service.impl;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.device.domain.InventoryDevice;
import com.moredian.skynet.device.manager.InventoryDeviceManager;
import com.moredian.skynet.device.request.WhiteDeviceAddRequest;
import com.moredian.skynet.device.request.WhiteDeviceStatusRequest;
import com.moredian.skynet.device.service.InventoryDeviceService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by xxu on 2017/6/5.
 */
@SI
public class InventoryDeviceServiceImpl implements InventoryDeviceService {

    @Autowired
    private InventoryDeviceManager inventoryDeviceManager;

    @Override
    public ServiceResponse<Boolean> addWhiteDevice(WhiteDeviceAddRequest request) {
        InventoryDevice iDevice=convertInventoryDeviceFromWhiteDevice(request);
        boolean result = inventoryDeviceManager.insert(iDevice);
        return new ServiceResponse<Boolean>(true, null, result);
    }

    private InventoryDevice convertInventoryDeviceFromWhiteDevice(WhiteDeviceAddRequest request){
        InventoryDevice iDevice=new InventoryDevice();
        iDevice.setMacAddress(request.getMacAddress());
        iDevice.setSerialNumber(request.getSerialNumber());
        iDevice.setSecretKey(request.getSecretKey());
        iDevice.setActivationCode(request.getActivationCode());
        iDevice.setOrgId(request.getOrgId());
        return iDevice;

    }


    @Override
    public ServiceResponse<Boolean> isWhiteDeviceActivated(WhiteDeviceStatusRequest request) {
        InventoryDevice iDevice=new InventoryDevice();
        iDevice.setMacAddress(request.getMac());
        iDevice.setSerialNumber(request.getDeviceSn());

        boolean checkResult=false;
        InventoryDevice result = inventoryDeviceManager.getByCondition(iDevice);
        if(result!=null && result.getOrgId()!=null && 1==result.getActivityStatus()){
            checkResult= true;
        }
        return  new ServiceResponse<Boolean>(true, null, checkResult);
    }
}
