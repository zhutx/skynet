package com.moredian.fishnet.device.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.moredian.bee.cache.CacheFactory;
import com.moredian.bee.cache.ICache;
import com.moredian.bee.common.exception.BizAssert;
import com.moredian.bee.common.exception.ErrorContext;
import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.ExceptionUtils;
import com.moredian.bee.common.utils.RandomUtil;
import com.moredian.bee.rmq.EventBus;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.common.model.msg.RefreshDeviceConfigMsg;
import com.moredian.fishnet.common.model.msg.SyncLocalDeployMsg;
import com.moredian.fishnet.device.constant.HiveConst;
import com.moredian.fishnet.device.constant.HiveMessage;
import com.moredian.fishnet.device.constant.ModuleType;
import com.moredian.fishnet.device.constant.StatusCode;
import com.moredian.fishnet.device.domain.ActiveEquipmentInfo;
import com.moredian.fishnet.device.domain.BaseDevice;
import com.moredian.fishnet.device.domain.Device;
import com.moredian.fishnet.device.domain.InventoryDevice;
import com.moredian.fishnet.device.enums.ActivationStatus;
import com.moredian.fishnet.device.enums.DeviceAction;
import com.moredian.fishnet.device.enums.HiveErrorCode;
import com.moredian.fishnet.device.manager.ActivityManager;
import com.moredian.fishnet.device.manager.BaseDeviceManager;
import com.moredian.fishnet.device.manager.DeviceManager;
import com.moredian.fishnet.device.manager.InventoryDeviceManager;
import com.moredian.fishnet.device.request.ActivateDeviceRequest;
import com.moredian.fishnet.device.request.ActivateInfoRequest;
import com.moredian.fishnet.device.request.ActivationCodeRequest;
import com.moredian.fishnet.device.request.BindDeviceRequest;
import com.moredian.fishnet.device.request.DeviceAddRequest;
import com.moredian.fishnet.device.request.DeviceUpdateRequest;
import com.moredian.fishnet.device.request.GenerateQrCodeRequest;
import com.moredian.fishnet.device.request.GetActivityStatusRequest;
import com.moredian.fishnet.device.request.PerfectInfoRequest;
import com.moredian.fishnet.device.request.ScanQrCodeRequest;
import com.moredian.fishnet.device.request.UpdateQrCodeStatusRequest;
import com.moredian.fishnet.device.response.ActivateDeviceResponse;
import com.moredian.fishnet.device.response.BindDeviceResponse;
import com.moredian.fishnet.device.response.GenerateQrCodeResponse;
import com.moredian.fishnet.device.response.GetActivityStatusResponse;
import com.moredian.fishnet.device.response.PerfectInfoResponse;
import com.moredian.fishnet.device.response.ScanQrCodeResponse;
import com.moredian.fishnet.device.response.UpdateQrCodeStatusResponse;
import com.moredian.fishnet.device.utils.Coder;
import com.moredian.fishnet.device.utils.HttpInvoker;
import com.moredian.fishnet.device.utils.HttpInvokerResponse;
import com.moredian.fishnet.org.model.AreaInfo;
import com.moredian.fishnet.org.model.OrgInfo;
import com.moredian.fishnet.org.service.AreaService;
import com.moredian.fishnet.org.service.OrgService;
import com.moredian.idgenerator.service.IdgeneratorService;
import com.xier.guard.accessKey.dto.AccessKeyDto;
import com.xier.guard.accessKey.service.UserAccessKeyService;

@Service
public class ActivityManagerImpl implements ActivityManager {
	
	private static final Logger log = LoggerFactory.getLogger(ActivityManagerImpl.class);

    @SI
    protected UserAccessKeyService userAccessKeyService;

    @SI
    protected AreaService areaService;

    @Autowired
    private InventoryDeviceManager inventoryDeviceManager;

    private ICache memcachedCacheManager = CacheFactory.getCache(HiveConst.MEMCACHED_ACTIVE_CODE_FRO_EQUIPMENT_ACTIVE);

    @Autowired
    private DeviceManager deviceManager;

    @Autowired
    private BaseDeviceManager baseDeviceManager;


    @SI
    private OrgService orgService;
    @SI
    private IdgeneratorService idgeneratorService;

    private static final int TIME_OUT = 120;

    private static final Integer ACTIVIATED=1;

    private static final Integer DEACTIVIATED=0;

    @Value("${spider.web.address}")
    private String spiderWebAddress;

    @Override
    @Transactional
    public ServiceResponse<GenerateQrCodeResponse> generateQrCode(GenerateQrCodeRequest request) {
        log.debug("receive generate QrCode,sn:{},checkCode:{}.", request.getSn(), request.getCheckCode());
        boolean isValid = true;
        // 判断参数是否为空
        isValid = isValid && (request != null) && (request.getCheckCode() != null) && (request.getSn() != null);
        BizAssert.isTrue(isValid, HiveErrorCode.PARAMS_WRONG, HiveErrorCode.PARAMS_WRONG.getMessage());

        //device has mulit macaddress. need to set the real work macAddress to activyInfo
        String realMacAddress="";

        // 判断是否合法设备
        InventoryDevice device = new InventoryDevice();
        device.setSerialNumber(request.getSn());
        InventoryDevice inventoryDevice = this.inventoryDeviceManager.getByCondition(device);
        BizAssert.isTrue(inventoryDevice != null, HiveErrorCode.NO_EXIST_EQUIPMENT, HiveErrorCode.NO_EXIST_EQUIPMENT.getMessage());

        // 激活码验证
        //对设备条码和mac地址做md5加密后做base64加密的值当做privatekey的salt值
        String salt = Base64.encodeBase64String(Coder.md5(inventoryDevice.getSerialNumber() + inventoryDevice.getMacAddress()));
        String key = Coder.decrypt(inventoryDevice.getSecretKey(), salt);

        //For activated devices before, use old method to validate
        if(!request.getCheckCode().equals(key)){
            try {
                String oldKey=Coder.HmacSHA1Encrypt(inventoryDevice.getMacAddress(),inventoryDevice.getMacAddress());
                if(!request.getCheckCode().equals(oldKey)){
                    if(StringUtils.isNotEmpty(inventoryDevice.getMacAddress2())) {
                        String oldKey2 = Coder.HmacSHA1Encrypt(inventoryDevice.getMacAddress2(), inventoryDevice.getMacAddress2());
                        BizAssert.isTrue(request.getCheckCode().equals(oldKey2), HiveErrorCode.CHECK_EQUIPMENT_EXCEPTION, HiveErrorCode.CHECK_EQUIPMENT_EXCEPTION.getMessage());
                        realMacAddress=inventoryDevice.getMacAddress2();
                    }else{
                        ExceptionUtils.throwException(HiveErrorCode.CHECK_EQUIPMENT_EXCEPTION,HiveErrorCode.CHECK_EQUIPMENT_EXCEPTION.getMessage());
                    }

                }else {
                    realMacAddress=inventoryDevice.getMacAddress();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

//        if(!request.getCheckCode().equals(key)){
////            String oldkey = crypto.dectypt(inventoryDevice.getSecretKey());
//            BizAssert.isTrue(request.getCheckCode().equals(oldkey), HiveErrorCode.CHECK_EQUIPMENT_EXCEPTION, HiveErrorCode.CHECK_EQUIPMENT_EXCEPTION.getMessage());
//
//        }
//        BizAssert.isTrue(request.getCheckCode().equals(key), HiveErrorCode.CHECK_EQUIPMENT_EXCEPTION, HiveErrorCode.CHECK_EQUIPMENT_EXCEPTION.getMessage());


        // 生成二维码
        String qRCode = RandomUtil.getUUID26() + RandomUtil.getRandom32String(10);

        ActiveEquipmentInfo activeEquipment = new ActiveEquipmentInfo();
        activeEquipment.setMacAddress(realMacAddress);
        activeEquipment.setPrivateKey(inventoryDevice.getSecretKey());
        activeEquipment.setSn(inventoryDevice.getSerialNumber());

        Device deviceInfo = deviceManager.getDeviceBySn(inventoryDevice.getSerialNumber());

        GenerateQrCodeResponse response = new GenerateQrCodeResponse();
        response.setQrCode(qRCode);
        if (deviceInfo == null) {
            // 扫码操作
            memcachedCacheManager.add(qRCode + HiveConst.MEMCACHED_EQUIPMENT_QRCODE_KEY, TIME_OUT, activeEquipment);
            memcachedCacheManager.add(qRCode + HiveConst.MEMCACHED_EQUIPMENT_QRCODE_STATUS_KEY, TIME_OUT, StatusCode.NOT_SCAN);
            response.setGenerate(true);
        } else {
            // 免扫码操作
            memcachedCacheManager.add(qRCode + HiveConst.MEMCACHED_EQUIPMENT_QRCODE_KEY, TIME_OUT, activeEquipment);
            memcachedCacheManager.add(qRCode + HiveConst.MEMCACHED_EQUIPMENT_QRCODE_STATUS_KEY, TIME_OUT, StatusCode.ACTIVE_WAIT);
            response.setGenerate(false);

            UpdateQrCodeStatusRequest updateQrCodeStatusRequest = new UpdateQrCodeStatusRequest();
            //updateQrCodeStatusRequest.setEquipmentType(deviceInfo.getDeviceType());
            updateQrCodeStatusRequest.setOrgId(deviceInfo.getOrgId());
            updateQrCodeStatusRequest.setQrCode(qRCode);

            updateQrCodeScan(updateQrCodeStatusRequest);
        }
        log.debug("succeed to generate qrcode {},device sn is {}.", response.getQrCode(), request.getSn());
        return new ServiceResponse<GenerateQrCodeResponse>(response);
    }

    @Override
    @Transactional
    public ServiceResponse<ScanQrCodeResponse> scanQrCode(ScanQrCodeRequest request) {
        //log.debug("receive scan QrCode,qrCode:{}.",request.getQrCode());
        boolean isValid = true;
        // 判断参数是否为空
        isValid = isValid && (request != null) && (request.getQrCode() != null);
        BizAssert.isTrue(isValid, HiveErrorCode.PARAMS_WRONG, HiveErrorCode.PARAMS_WRONG.getMessage());

        String qRCode = request.getQrCode();
        Integer status = (Integer) memcachedCacheManager.get(qRCode + HiveConst.MEMCACHED_EQUIPMENT_QRCODE_STATUS_KEY);
        ScanQrCodeResponse response = new ScanQrCodeResponse();
        if (status == null) {
            response.setMessage(HiveMessage.EXPIRED);
            response.setStatusCode(StatusCode.EXPIRED);
        } else if (status == StatusCode.SCAN) {
            memcachedCacheManager.delete(qRCode + HiveConst.MEMCACHED_EQUIPMENT_QRCODE_STATUS_KEY);
            response.setMessage(HiveMessage.OVER_SCAN);
            response.setStatusCode(StatusCode.SCAN);
        } else {
            response.setMessage(HiveMessage.NOT_SCAN);
            response.setStatusCode(StatusCode.NOT_SCAN);
        }
        return new ServiceResponse<ScanQrCodeResponse>(response);
    }

    @Override
    @Transactional
    public ServiceResponse<UpdateQrCodeStatusResponse> updateQrCodeScan(UpdateQrCodeStatusRequest request) {
        log.debug("receive to update qrcode: {},orgId : {}.", request.getQrCode(), request.getOrgId());
        boolean isValid = true;
        // 判断参数是否为空
        isValid = isValid && (request != null) && (request.getQrCode() != null) && (request.getOrgId() != null);
        BizAssert.isTrue(isValid, HiveErrorCode.PARAMS_WRONG, HiveErrorCode.PARAMS_WRONG.getMessage());

        String qRCode = request.getQrCode();
        ActiveEquipmentInfo info = (ActiveEquipmentInfo) memcachedCacheManager.get(qRCode + HiveConst.MEMCACHED_EQUIPMENT_QRCODE_KEY);

        UpdateQrCodeStatusResponse response = new UpdateQrCodeStatusResponse();
        if (info == null) {
            response.setMessage(HiveMessage.EXPIRED);
            response.setStatusCode(StatusCode.EXPIRED);
            return new ServiceResponse<UpdateQrCodeStatusResponse>(response);
        }

        OrgInfo orgInfo = orgService.getOrgInfo(request.getOrgId());

        Integer cityId=orgInfo.getCityId();
        if(cityId!=null){
            AreaInfo areaInfo=areaService.getAreaByCode(cityId);
            if(areaInfo!=null){
                info.setCityName(areaInfo.getAreaName());
            }
        }else{
            info.setCityName("杭州市");
        }


        BizAssert.isTrue(orgInfo != null, HiveErrorCode.ORG_ID_VALIDATE_GET_ERROR, HiveErrorCode.ORG_SUBORG_NOT_EXIST.getMessage());
        info.setModelType(ModuleType.ATTENCE.getValue());
        //info.setEquipmentType(request.getEquipmentType());
        info.setOrgId(request.getOrgId());
        // info.setSubOrgId(qrCodeScanRequest.getSubOrgId());
        // info.setAutoDeploy(qrCodeScanRequest.isAutoDeploy());
        memcachedCacheManager.add(qRCode + HiveConst.MEMCACHED_EQUIPMENT_QRCODE_KEY, TIME_OUT, info);
        memcachedCacheManager.add(qRCode + HiveConst.MEMCACHED_EQUIPMENT_QRCODE_STATUS_KEY, TIME_OUT, StatusCode.SCAN);
        memcachedCacheManager.add(qRCode + HiveConst.MEMCACHED_EQUIPMENT_ACTIVE_STATUS_KEY, TIME_OUT, StatusCode.ACTIVE_WAIT);

        response.setMessage(HiveMessage.SUCCESS);
        response.setStatusCode(StatusCode.SCAN_SUCCESS);

        log.debug("update qrcode {},device orgId is {},reponse {}.", request.getQrCode(), request.getOrgId(), response.getStatusCode());
        return new ServiceResponse<UpdateQrCodeStatusResponse>(response);
    }


    private String getCityName(Long orgId){
        OrgInfo orgInfo = orgService.getOrgInfo(orgId);

        Integer cityId=orgInfo.getCityId();
        if(cityId!=null){
            AreaInfo areaInfo=areaService.getAreaByCode(cityId);
            if(areaInfo!=null){
                return areaInfo.getAreaName();
            }
        }
        return "杭州市";
    }

    @Override
    @Transactional
    public ServiceResponse<GetActivityStatusResponse> getActivityStatus(GetActivityStatusRequest request) {

        //log.debug("get activity status,qrCode:{}.",request.getQrCode());
        boolean isValid = true;
        // 判断参数是否为空
        isValid = isValid && (request != null) && (request.getQrCode() != null);
        BizAssert.isTrue(isValid, HiveErrorCode.PARAMS_WRONG, HiveErrorCode.PARAMS_WRONG.getMessage());

        String qRCode = request.getQrCode();
        ActiveEquipmentInfo info = (ActiveEquipmentInfo) memcachedCacheManager.get(qRCode + HiveConst.MEMCACHED_EQUIPMENT_QRCODE_KEY);

        Integer status = (Integer) memcachedCacheManager.get(qRCode + HiveConst.MEMCACHED_EQUIPMENT_ACTIVE_STATUS_KEY);
        GetActivityStatusResponse response = new GetActivityStatusResponse();
        if (status == null) {
            response.setMessage(HiveMessage.EXPIRED);
            response.setStatusCode(StatusCode.ACTIVE_EXPIRED);

        } else if (status == StatusCode.ACTIVE_SUCCESS) {
            memcachedCacheManager.delete(qRCode + HiveConst.MEMCACHED_EQUIPMENT_QRCODE_KEY);
            memcachedCacheManager.delete(qRCode + HiveConst.MEMCACHED_EQUIPMENT_ACTIVE_STATUS_KEY);

            if(info.getSn()!=null){
                Device device=deviceManager.getDeviceBySn(info.getSn());
                response.setDeviceId(device.getDeviceId());
                response.setSerialNumber(device.getDeviceSn());
            }

            response.setMessage(HiveMessage.OVER_ACTIVE);
            response.setStatusCode(StatusCode.ACTIVE_SUCCESS);
            response.setSerialNumber(info.getSn());
        } else if (status == StatusCode.ACTIVE_FAIL) {
            response.setMessage(HiveMessage.FAIL);
            response.setStatusCode(StatusCode.ACTIVE_FAIL);
        } else {
            response.setMessage(HiveMessage.NOT_ACTIVE);
            response.setStatusCode(StatusCode.ACTIVE_WAIT);
        }
        return new ServiceResponse<GetActivityStatusResponse>(response);
    }

    @Override
    @Transactional
    public ServiceResponse<ActivateDeviceResponse> activateDevice(ActivateDeviceRequest request) {
        log.debug(" activate device,qrCode:{},deviceType:{},version:{},cpuId:{},Ip:{}.", request.getQrCode(), request.getEquipmentType(), request.getVersion(), request.getCpuId(), request.getIpAddress());
        boolean isValid = true;
        // 判断参数是否为空
        isValid = isValid && (request != null) && (request.getQrCode() != null) && (request.getVersion() != null) && (request.getEquipmentType() != null);
        BizAssert.isTrue(isValid, HiveErrorCode.PARAMS_WRONG, HiveErrorCode.PARAMS_WRONG.getMessage());
        ServiceResponse<ActivateDeviceResponse> response = new ServiceResponse<ActivateDeviceResponse>(new ErrorContext());

        ActiveEquipmentInfo info = memcachedCacheManager.get(request.getQrCode() + HiveConst.MEMCACHED_EQUIPMENT_QRCODE_KEY);
        //判断缓存是否正常
        BizAssert.isTrue(info != null, HiveErrorCode.INVALID_ACTIVE_EXPIRE, HiveErrorCode.INVALID_ACTIVE_EXPIRE.getMessage());

        info.setEquipmentType(request.getEquipmentType());
        // 获取秘钥
        ActivateDeviceResponse acitivityResponse = new ActivateDeviceResponse();
        com.xier.sesame.common.rpc.ServiceResponse<AccessKeyDto> sr = userAccessKeyService.issuDeviceAccessKeyAutoRevoke(info.getSn());

        //判断激活码是否有效
        BizAssert.isTrue((sr.isSuccess() && sr.isExistData()), HiveErrorCode.ISSU_DEVICE_ACCESS_KEY_FAIL, HiveErrorCode.ISSU_DEVICE_ACCESS_KEY_FAIL.getMessage());

        acitivityResponse.setAccessKeySecret(sr.getData().getAccessKeySecret());

        response.setData(acitivityResponse);

        // Device表添加设备信息
        DeviceAddRequest deviceAddRequest = new DeviceAddRequest();
        deviceAddRequest.setDeviceSn(info.getSn());
        deviceAddRequest.setDeviceType(info.getEquipmentType());
        deviceAddRequest.setOrgId(info.getOrgId());
        deviceManager.addDevice(deviceAddRequest);



        //更新设备
        Boolean isActive = deviceManager.activeDevice(info.getOrgId(), info.getSn());
        boolean isBaseDevice = false;

        //更新inventory表

        InventoryDevice inventoryDevice = new InventoryDevice();
        inventoryDevice.setSerialNumber(info.getSn());
        inventoryDevice.setOrgId(info.getOrgId());
        inventoryDevice.setActivityStatus(ACTIVIATED);
        //don't need to update macAddress
//        inventoryDevice.setMacAddress(info.getMacAddress());

        InventoryDevice condition = new InventoryDevice();
        condition.setSerialNumber(info.getSn());
        boolean updateResult=inventoryDeviceManager.updateByCondition(inventoryDevice,condition);




        if(updateResult && isActive) {
            // BaseDevice表添加设备信息
            String requestUrl = this.spiderWebAddress + "/services/inventory/devices/create";

            String invokerUrl = getPostUrl(requestUrl, info);

            Map<String, String> headerMap = new HashMap<String, String>();
            headerMap.put("Content-Type", "application/json");
            headerMap.put("Accept", "text/plain, application/json, application/*+json, */*");
            Map<String, String> urlVaMap = new HashMap<String, String>();
            HttpInvokerResponse httpResponse = HttpInvoker.invokerPost(invokerUrl, headerMap, urlVaMap, "");

            if (httpResponse == null) {
                log.debug("Failed to activate device,mac is {},device sn is {}.", info.getMacAddress(), info.getSn());
            }
            if (httpResponse.getResponseCode() == 200) {
                isBaseDevice = true;
            }

        }

//        BaseDevice baseDevice = new BaseDevice();
//        Long id = idgeneratorService.getNextIdByTypeName("com.moredian.fishnet.device.domain.BaseDevice").getData();
//        baseDevice.setId(id);
//        baseDevice.setActiveTime(new Date());
//        baseDevice.setSerialNumber(info.getSn());
//        baseDevice.setMacAddress(info.getMacAddress());
//        baseDevice.setDeviceType(info.getEquipmentType().toString());
//        baseDevice.setDeviceModel(info.getModelType().toString());
//        baseDevice.setOwnerId(info.getOrgId());
//        baseDevice.setIpAddress(request.getIpAddress());
//        baseDevice.setSoftwareVersion(request.getVersion().toString());
//        baseDevice.setCpuId(request.getCpuId());
//        boolean isBaseDevice = baseDeviceManager.insert(baseDevice);



        isActive = isBaseDevice && isActive;
        if (!isActive) {
            log.warn("failed to active device,sn{},isAddDevice:{},isBaseDevice:{},isActive:{}", info.getSn(), true, isBaseDevice, isActive);
        }

        BizAssert.isTrue(isActive, HiveErrorCode.SYS_ERROR, HiveErrorCode.SYS_ERROR.getMessage());

        response.setSuccess(isActive);

        acitivityResponse.setOrgId(info.getOrgId());
        acitivityResponse.setSubOrgId(info.getSubOrgId());
        acitivityResponse.setEquipmentType(info.getEquipmentType());
        acitivityResponse.setSerialNumber(info.getSn());
        acitivityResponse.setCityName(info.getCityName());
        acitivityResponse.setGmtCreate(new Date());

        // 更新激活状态
        if (response.isSuccess() && response.isExistData()) {
            memcachedCacheManager.add(request.getQrCode() + HiveConst.MEMCACHED_EQUIPMENT_ACTIVE_STATUS_KEY, TIME_OUT, StatusCode.ACTIVE_SUCCESS);
        } else {
            memcachedCacheManager.add(request.getQrCode() + HiveConst.MEMCACHED_EQUIPMENT_ACTIVE_STATUS_KEY, TIME_OUT, StatusCode.ACTIVE_FAIL);
        }

        Device device=deviceManager.getDeviceBySn(info.getSn());

        SyncLocalDeployMsg deployEntrySyncMsg=new SyncLocalDeployMsg();
        deployEntrySyncMsg.setOrgId(info.getOrgId());
        deployEntrySyncMsg.setDeviceAction(DeviceAction.ACTIVE.getValue());
        deployEntrySyncMsg.setDeviceId(device.getDeviceId());
        deployEntrySyncMsg.setDeviceType(device.getDeviceType());
        deployEntrySyncMsg.setDeviceSn(info.getSn());
        EventBus.publish(deployEntrySyncMsg); // 发出设备操作消息, 消息接受方负责调整布控实例并同步云眼Huber配置
        log.debug("Send DeployEntrySyncMsg message,deviceid:{}",device.getDeviceId());

        RefreshDeviceConfigMsg msg=new RefreshDeviceConfigMsg();
        msg.setOrgId(info.getOrgId());
        msg.setDeviceSn(info.getSn());
        EventBus.publish(msg);
        log.debug("Send NoticeDeviceConfigEventMsg message,orgId:{}",info.getOrgId());
        return response;
    }

    @Override
    public ServiceResponse<Boolean> deActivateDevice(String serialNumber) {
        InventoryDevice inventoryDevice = new InventoryDevice();
        inventoryDevice.setSerialNumber(serialNumber);
        inventoryDevice.setActivityStatus(DEACTIVIATED);

        InventoryDevice condition = new InventoryDevice();
        condition.setSerialNumber(serialNumber);
        boolean updateResult=inventoryDeviceManager.updateByCondition(inventoryDevice,condition);
        return new ServiceResponse(updateResult);
    }

    @Override
    public ServiceResponse<Boolean> deleteDeviceFromBaseDevice(String serialNumber) {
        String invokerUrl = this.spiderWebAddress + "/services/inventory/devices/sn/" + serialNumber;

        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type", "application/json");
        headerMap.put("Accept", "text/plain, application/json, application/*+json, */*");
        Map<String, String> urlVaMap = new HashMap<String, String>();
        HttpInvokerResponse httpResponse = HttpInvoker.invokerDelete(invokerUrl, headerMap, urlVaMap);

        if (httpResponse == null) {
            log.debug("Failed to delete device,serialNumber is {}.", serialNumber);
        }
        ServiceResponse<Boolean> response=new ServiceResponse<Boolean>(new ErrorContext());
        if (httpResponse.getResponseCode() == 200) {
            response.setSuccess(true);
            response.setData(true);
        }else{
            response.setData(false);
            response.setSuccess(false);
        }
        return response;
    }

    @Override
    @Transactional
    public ServiceResponse<ActivateDeviceResponse> activateDeviceWithActivationCode(ActivationCodeRequest request) {
        log.debug(" activate device with activation code:{},deviceType:{}", request.getActivationCode(), request.getEquipmentType());
        boolean isValid = true;
        // 判断参数是否为空
        isValid = isValid && (request != null) && (request.getActivationCode() != null) && (request.getVersion() != null) && (request.getEquipmentType() != null);
        BizAssert.isTrue(isValid, HiveErrorCode.PARAMS_WRONG, HiveErrorCode.PARAMS_WRONG.getMessage());
        ServiceResponse<ActivateDeviceResponse> response = new ServiceResponse<ActivateDeviceResponse>(new ErrorContext());

        //判断激活码是否有效
        InventoryDevice device = new InventoryDevice();
        device.setSerialNumber(request.getDeviceSn());
        device.setActivationCode(request.getActivationCode());
        InventoryDevice inventoryDevice=inventoryDeviceManager.getByCondition(device);

        BizAssert.isTrue(inventoryDevice != null, HiveErrorCode.INVALID_ACTIVE_EXCEPTION, HiveErrorCode.INVALID_ACTIVE_EXCEPTION.getMessage());

        Long orgId=inventoryDevice.getOrgId();
        Assert.assertNotNull(orgId);

        // 获取秘钥
        ActivateDeviceResponse acitivityResponse = new ActivateDeviceResponse();
        com.xier.sesame.common.rpc.ServiceResponse<AccessKeyDto> sr = userAccessKeyService.issuDeviceAccessKeyAutoRevoke(request.getDeviceSn());

        //判断激活码是否有效
        BizAssert.isTrue((sr.isSuccess() && sr.isExistData()), HiveErrorCode.ISSU_DEVICE_ACCESS_KEY_FAIL, HiveErrorCode.ISSU_DEVICE_ACCESS_KEY_FAIL.getMessage());

        acitivityResponse.setAccessKeySecret(sr.getData().getAccessKeySecret());

        response.setData(acitivityResponse);

        // Device表添加设备信息
        DeviceAddRequest deviceAddRequest = new DeviceAddRequest();
        deviceAddRequest.setDeviceSn(request.getDeviceSn());
        deviceAddRequest.setDeviceType(request.getEquipmentType());
        deviceAddRequest.setOrgId(orgId);
        deviceManager.addDevice(deviceAddRequest);



        //更新设备
        Boolean isActive = deviceManager.activeDevice(orgId, request.getDeviceSn());
        boolean isBaseDevice = false;

        //更新inventory表

        inventoryDevice.setActivityStatus(ACTIVIATED);
        boolean updateResult=inventoryDeviceManager.updateByCondition(inventoryDevice,device);


        if(updateResult && isActive) {
            // BaseDevice表添加设备信息
            String requestUrl = this.spiderWebAddress + "/services/inventory/devices/create";

            String invokerUrl = getPostUrlFromInventoryDevice(requestUrl, inventoryDevice,request.getEquipmentType().toString());

            Map<String, String> headerMap = new HashMap<String, String>();
            headerMap.put("Content-Type", "application/json");
            headerMap.put("Accept", "text/plain, application/json, application/*+json, */*");
            Map<String, String> urlVaMap = new HashMap<String, String>();
            HttpInvokerResponse httpResponse = HttpInvoker.invokerPost(invokerUrl, headerMap, urlVaMap, "");

            if (httpResponse == null) {
                log.debug("Failed to activate device,mac is {},device sn is {}.", inventoryDevice.getMacAddress(), inventoryDevice.getSerialNumber());
            }
            if (httpResponse.getResponseCode() == 200) {
                isBaseDevice = true;
            }

        }




        isActive = isBaseDevice && isActive;
        if (!isActive) {
            log.warn("failed to active device,sn{},isAddDevice:{},isBaseDevice:{},isActive:{}", inventoryDevice.getSerialNumber(), true, isBaseDevice, isActive);
        }

        BizAssert.isTrue(isActive, HiveErrorCode.SYS_ERROR, HiveErrorCode.SYS_ERROR.getMessage());

        response.setSuccess(isActive);

        acitivityResponse.setOrgId(orgId);
//        acitivityResponse.setSubOrgId(info.getSubOrgId());
        acitivityResponse.setEquipmentType(request.getEquipmentType());
        acitivityResponse.setSerialNumber(inventoryDevice.getSerialNumber());
        acitivityResponse.setCityName(getCityName(inventoryDevice.getOrgId()));
        acitivityResponse.setGmtCreate(new Date());

        // 更新激活状态

        Device activatedDevice=deviceManager.getDeviceBySn(inventoryDevice.getSerialNumber());

        SyncLocalDeployMsg deployEntrySyncMsg=new SyncLocalDeployMsg();
        deployEntrySyncMsg.setOrgId(orgId);
        deployEntrySyncMsg.setDeviceAction(DeviceAction.ACTIVE.getValue());
        deployEntrySyncMsg.setDeviceId(activatedDevice.getDeviceId());
        deployEntrySyncMsg.setDeviceType(activatedDevice.getDeviceType());
        deployEntrySyncMsg.setDeviceSn(inventoryDevice.getSerialNumber());
        EventBus.publish(deployEntrySyncMsg); // 发出设备操作消息, 消息接受方负责调整布控实例并同步云眼Huber配置
        log.debug("Send DeployEntrySyncMsg message,deviceid:{}",activatedDevice.getDeviceId());

        RefreshDeviceConfigMsg msg=new RefreshDeviceConfigMsg();
        msg.setOrgId(orgId);
        msg.setDeviceSn(inventoryDevice.getSerialNumber());
        EventBus.publish(msg);
        log.debug("Send NoticeDeviceConfigEventMsg message,orgId:{}",orgId);
        return response;
    }

    @Override
    @Transactional
    public ServiceResponse<ActivateDeviceResponse> getActivateInfo(ActivateInfoRequest request) {
        /**
         * step1:check from inventory table,whether the white device existed,return null if not existed
         * step2:If the status is actived, and device (orig_equipment) is existed and base device existed, return key
         * step3:If the status is new,and now device and base device, generate key and return it
         *
         */
        log.debug(" getActivateInfo,sn:{},macaddress:{},checkCode:{}.", request.getSn(), request.getCheckCode());
        boolean isValid = true;
        // 判断参数是否为空
        isValid = isValid && (request != null) && (request.getSn() != null)  && (request.getCheckCode() != null);
        BizAssert.isTrue(isValid, HiveErrorCode.PARAMS_WRONG, HiveErrorCode.PARAMS_WRONG.getMessage());

        ServiceResponse<ActivateDeviceResponse> response = new ServiceResponse<ActivateDeviceResponse>(new ErrorContext());

        InventoryDevice device = new InventoryDevice();
        device.setSerialNumber(request.getSn());
        InventoryDevice inventoryDevice=inventoryDeviceManager.getByCondition(device);

        Device realDevice=deviceManager.getDeviceBySn(request.getSn());

        //step 1
        if(inventoryDevice==null ) {
            response.setSuccess(true);
            // 获取秘钥
//            ActivateDeviceResponse acitivityResponse = new ActivateDeviceResponse();
//            acitivityResponse.setAccessKeySecret(null);

            response.setData(null);
            return response;
        }
        //step 2
        if(inventoryDevice.getOrgId()!=null && inventoryDevice.getActivityStatus()== ActivationStatus.ACTIVATED.getValue() && realDevice!=null && realDevice.getOrgId()!=null  ) {

            // 获取秘钥
            ActivateDeviceResponse acitivityResponse = new ActivateDeviceResponse();
            com.xier.sesame.common.rpc.ServiceResponse<AccessKeyDto> sr = userAccessKeyService.issuDeviceAccessKeyAutoRevoke(inventoryDevice.getSerialNumber());

            //判断激活码是否有效
            BizAssert.isTrue((sr.isSuccess() && sr.isExistData()), HiveErrorCode.ISSU_DEVICE_ACCESS_KEY_FAIL, HiveErrorCode.ISSU_DEVICE_ACCESS_KEY_FAIL.getMessage());
            acitivityResponse.setAccessKeySecret(sr.getData().getAccessKeySecret());
            response.setData(acitivityResponse);
            response.setSuccess(true);

            acitivityResponse.setOrgId(inventoryDevice.getOrgId());
//        acitivityResponse.setSubOrgId();
            acitivityResponse.setEquipmentType(realDevice.getDeviceType());
            acitivityResponse.setSerialNumber(inventoryDevice.getSerialNumber());
            acitivityResponse.setCityName(getCityName(inventoryDevice.getOrgId()));
            acitivityResponse.setGmtCreate(new Date());

            SyncLocalDeployMsg deployEntrySyncMsg=new SyncLocalDeployMsg();
            deployEntrySyncMsg.setOrgId(realDevice.getOrgId());
            deployEntrySyncMsg.setDeviceAction(DeviceAction.DELETE.getValue());
            deployEntrySyncMsg.setDeviceId(realDevice.getDeviceId());
            deployEntrySyncMsg.setDeviceType(realDevice.getDeviceType());
            deployEntrySyncMsg.setDeviceSn(realDevice.getDeviceSn());
            EventBus.publish(deployEntrySyncMsg); // 发出设备操作消息, 消息接受方负责调整布控实例并同步云眼Huber配置

            RefreshDeviceConfigMsg msg=new RefreshDeviceConfigMsg();
            msg.setOrgId(realDevice.getOrgId());
            msg.setDeviceSn(realDevice.getDeviceSn());
            EventBus.publish(msg);
            return response;


        }
        //step 3
        if( inventoryDevice.getOrgId()!=null && inventoryDevice.getDeviceType()!=null && inventoryDevice.getActivityStatus()== ActivationStatus.INACTIVATED.getValue() && realDevice==null  ) {
            // 获取秘钥
            ActivateDeviceResponse acitivityResponse = new ActivateDeviceResponse();
            com.xier.sesame.common.rpc.ServiceResponse<AccessKeyDto> sr = userAccessKeyService.issuDeviceAccessKeyAutoRevoke(inventoryDevice.getSerialNumber());

            //判断激活码是否有效
            BizAssert.isTrue((sr.isSuccess() && sr.isExistData()), HiveErrorCode.ISSU_DEVICE_ACCESS_KEY_FAIL, HiveErrorCode.ISSU_DEVICE_ACCESS_KEY_FAIL.getMessage());

            acitivityResponse.setAccessKeySecret(sr.getData().getAccessKeySecret());

            response.setData(acitivityResponse);

            // Device表添加设备信息
            DeviceAddRequest deviceAddRequest = new DeviceAddRequest();
            deviceAddRequest.setDeviceSn(inventoryDevice.getSerialNumber());
            deviceAddRequest.setDeviceType(inventoryDevice.getDeviceType());
            deviceAddRequest.setOrgId(inventoryDevice.getOrgId());
            deviceManager.addDevice(deviceAddRequest);



            //更新设备
            Boolean isActive = deviceManager.activeDevice(inventoryDevice.getOrgId(), inventoryDevice.getSerialNumber());
            boolean isBaseDevice = false;

            //更新inventory表

            inventoryDevice.setActivityStatus(ACTIVIATED);
            boolean updateResult=inventoryDeviceManager.updateByCondition(inventoryDevice,device);


            if(updateResult && isActive) {
                // BaseDevice表添加设备信息
                String requestUrl = this.spiderWebAddress + "/services/inventory/devices/create";

                String invokerUrl = getPostUrlFromInventoryDevice(requestUrl, inventoryDevice,inventoryDevice.getDeviceType().toString());

                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-Type", "application/json");
                headerMap.put("Accept", "text/plain, application/json, application/*+json, */*");
                Map<String, String> urlVaMap = new HashMap<String, String>();
                HttpInvokerResponse httpResponse = HttpInvoker.invokerPost(invokerUrl, headerMap, urlVaMap, "");

                if (httpResponse == null) {
                    log.debug("Failed to activate device,mac is {},device sn is {}.", inventoryDevice.getMacAddress(), inventoryDevice.getSerialNumber());
                }
                if (httpResponse.getResponseCode() == 200) {
                    isBaseDevice = true;
                }

            }




            isActive = isBaseDevice && isActive;
            if (!isActive) {
                log.warn("failed to active device,sn{},isAddDevice:{},isBaseDevice:{},isActive:{}", inventoryDevice.getSerialNumber(), true, isBaseDevice, isActive);
            }

            BizAssert.isTrue(isActive, HiveErrorCode.SYS_ERROR, HiveErrorCode.SYS_ERROR.getMessage());

            response.setSuccess(isActive);

            acitivityResponse.setOrgId(inventoryDevice.getOrgId());
//        acitivityResponse.setSubOrgId(info.getSubOrgId());
            acitivityResponse.setEquipmentType(inventoryDevice.getDeviceType());
            acitivityResponse.setSerialNumber(inventoryDevice.getSerialNumber());
            acitivityResponse.setCityName(getCityName(inventoryDevice.getOrgId()));
            acitivityResponse.setGmtCreate(new Date());

            // 更新激活状态

            Device activatedDevice=deviceManager.getDeviceBySn(inventoryDevice.getSerialNumber());

            SyncLocalDeployMsg deployEntrySyncMsg=new SyncLocalDeployMsg();
            deployEntrySyncMsg.setOrgId(inventoryDevice.getOrgId());
            deployEntrySyncMsg.setDeviceAction(DeviceAction.ACTIVE.getValue());
            deployEntrySyncMsg.setDeviceId(activatedDevice.getDeviceId());
            deployEntrySyncMsg.setDeviceType(activatedDevice.getDeviceType());
            deployEntrySyncMsg.setDeviceSn(inventoryDevice.getSerialNumber());
            EventBus.publish(deployEntrySyncMsg); // 发出设备操作消息, 消息接受方负责调整布控实例并同步云眼Huber配置
            log.debug("Send DeployEntrySyncMsg message,deviceid:{}",activatedDevice.getDeviceId());

            RefreshDeviceConfigMsg msg=new RefreshDeviceConfigMsg();
            msg.setOrgId(inventoryDevice.getOrgId());
            msg.setDeviceSn(inventoryDevice.getSerialNumber());
            EventBus.publish(msg);
            log.debug("Send NoticeDeviceConfigEventMsg message,orgId:{}",inventoryDevice.getOrgId());
            return response;
        }

        response.setSuccess(true);
        // 获取秘钥
//            ActivateDeviceResponse acitivityResponse = new ActivateDeviceResponse();
//            acitivityResponse.setAccessKeySecret(null);

        response.setData(null);
        return response;

    }



////    @Override
//    @Transactional
//    public ServiceResponse<ActivateDeviceResponse> getActivateInfo1(ActivateInfoRequest request) {
//        log.debug(" getActivateInfo,sn:{},macaddress:{},checkCode:{}.", request.getSn(), request.getCheckCode());
//        boolean isValid = true;
//        // 判断参数是否为空
//        isValid = isValid && (request != null) && (request.getSn() != null)  && (request.getCheckCode() != null);
//        BizAssert.isTrue(isValid, HiveErrorCode.PARAMS_WRONG, HiveErrorCode.PARAMS_WRONG.getMessage());
//
//        ServiceResponse<ActivateDeviceResponse> response = new ServiceResponse<ActivateDeviceResponse>(new ErrorContext());
//
//        InventoryDevice device = new InventoryDevice();
//        device.setSerialNumber(request.getSn());
//        InventoryDevice inventoryDevice=inventoryDeviceManager.getByCondition(device);
//
//        Device realDevice=deviceManager.getDeviceBySn(request.getSn());
//
//        if(inventoryDevice==null || inventoryDevice.getOrgId()==null || inventoryDevice.getActivityStatus()!=1 || realDevice==null || realDevice.getOrgId()==null ) {
//            response.setSuccess(true);
//            // 获取秘钥
////            ActivateDeviceResponse acitivityResponse = new ActivateDeviceResponse();
////            acitivityResponse.setAccessKeySecret(null);
//
//            response.setData(null);
//            return response;
//        }
//        if(inventoryDevice.getActivityStatus()!=1 || realDevice==null || realDevice.getOrgId()==null ) {
//            response.setSuccess(true);
//            // 获取秘钥
////            ActivateDeviceResponse acitivityResponse = new ActivateDeviceResponse();
////            acitivityResponse.setAccessKeySecret(null);
//
//            response.setData(null);
//            return response;
//        }
//        // 获取秘钥
//        ActivateDeviceResponse acitivityResponse = new ActivateDeviceResponse();
//        com.xier.sesame.common.rpc.ServiceResponse<AccessKeyDto> sr = userAccessKeyService.issuDeviceAccessKeyAutoRevoke(inventoryDevice.getSerialNumber());
//
//        //判断激活码是否有效
//        BizAssert.isTrue((sr.isSuccess() && sr.isExistData()), HiveErrorCode.ISSU_DEVICE_ACCESS_KEY_FAIL, HiveErrorCode.ISSU_DEVICE_ACCESS_KEY_FAIL.getMessage());
//
//        acitivityResponse.setAccessKeySecret(sr.getData().getAccessKeySecret());
//
//        response.setData(acitivityResponse);
//
//
//
//
//        response.setSuccess(true);
//
//        acitivityResponse.setOrgId(inventoryDevice.getOrgId());
////        acitivityResponse.setSubOrgId();
//        acitivityResponse.setEquipmentType(realDevice.getDeviceType());
//        acitivityResponse.setSerialNumber(inventoryDevice.getSerialNumber());
//        acitivityResponse.setCityName(getCityName(inventoryDevice.getOrgId()));
//        acitivityResponse.setGmtCreate(new Date());
//
//
//
//        SyncLocalDeployMsg deployEntrySyncMsg=new SyncLocalDeployMsg();
//        deployEntrySyncMsg.setOrgId(realDevice.getOrgId());
//        deployEntrySyncMsg.setDeviceAction(DeviceAction.DELETE.getValue());
//        deployEntrySyncMsg.setDeviceId(realDevice.getDeviceId());
//        deployEntrySyncMsg.setDeviceType(realDevice.getDeviceType());
//        deployEntrySyncMsg.setDeviceSn(realDevice.getDeviceSn());
//        EventBus.publish(deployEntrySyncMsg); // 发出设备操作消息, 消息接受方负责调整布控实例并同步云眼Huber配置
//
//        RefreshDeviceConfigMsg msg=new RefreshDeviceConfigMsg();
//        msg.setOrgId(realDevice.getOrgId());
//        msg.setDeviceSn(realDevice.getDeviceSn());
//        EventBus.publish(msg);
//        return response;
//    }

    private String getPostUrl(String requestUrl, ActiveEquipmentInfo info) {
        StringBuilder sb = new StringBuilder(requestUrl);
        sb.append("?");
        sb.append("sn=").append(info.getSn()).append("&").append("mac=").append(info.getMacAddress()).append("&").append("orgId=").append(info.getOrgId()).append("&");
        sb.append("deviceType=").append(info.getEquipmentType().toString()).append("&").append("softwareVersion=").append("1");

        return sb.toString();

    }


    private String getPostUrlFromInventoryDevice(String requestUrl, InventoryDevice inventoryDevice,String equipmentType) {
        StringBuilder sb = new StringBuilder(requestUrl);
        sb.append("?");
        sb.append("sn=").append(inventoryDevice.getSerialNumber()).append("&").append("mac=").append(inventoryDevice.getMacAddress()).append("&").append("orgId=").append(inventoryDevice.getOrgId()).append("&");
        sb.append("deviceType=").append(equipmentType).append("&").append("softwareVersion=").append("1");

        return sb.toString();

    }

    @Transactional
    @Override
    public ServiceResponse<List<BindDeviceResponse>> bindDevices(List<BindDeviceRequest> request) {
        List<BindDeviceResponse> list = new ArrayList<BindDeviceResponse>();
        for (BindDeviceRequest info : request) {
            // InventoryDevice表增加设备
            InventoryDevice inventoryDevice = new InventoryDevice();
            inventoryDevice.setMacAddress(info.getMacAddress());
            inventoryDevice.setPatchFlag(1);
            inventoryDevice.setSecretKey(info.getPrivateKey());
            inventoryDevice.setSerialNumber(info.getSn());
            boolean inventoryDeviceFlag = inventoryDeviceManager.insert(inventoryDevice);
            // Device表添加设备信息
            BindDeviceResponse res = new BindDeviceResponse();
            res.setSerialNumber(info.getSn());
            DeviceAddRequest deviceAddRequest = new DeviceAddRequest();
            deviceAddRequest.setDeviceSn(info.getSn());
            deviceAddRequest.setDeviceType(info.getEquipmentType());
            deviceAddRequest.setOrgId(info.getOrgId());
            deviceManager.addDevice(deviceAddRequest);

            // BaseDevice表添加设备信息
            BaseDevice baseDevice = new BaseDevice();
            Long id = idgeneratorService.getNextIdByTypeName("com.moredian.fishnet.device.domain.BaseDevice").getData();
            baseDevice.setId(id);
            baseDevice.setActiveTime(new Date());
            baseDevice.setSerialNumber(info.getSn());
            baseDevice.setMacAddress(info.getMacAddress());
            baseDevice.setDeviceType(info.getEquipmentType().toString());
            baseDevice.setDeviceModel(info.getModelType().toString());
            baseDevice.setOwnerId(info.getOrgId());
            boolean isBaseDevice = baseDeviceManager.insert(baseDevice);

            //更新设备
//			Boolean isActive =false;
//			ServiceResponse<Boolean> activeDevice =    deviceService.activeDevice(info.getOrgId(), info.getSn());
//			isActive =  (activeDevice.getData()==null?false:activeDevice.getData());
            res.setResult(inventoryDeviceFlag && isBaseDevice && true);
        }
        return new ServiceResponse<List<BindDeviceResponse>>(list);
    }

    @Override
    public ServiceResponse<PerfectInfoResponse> perfectDevice(PerfectInfoRequest request) {
        log.debug("perfect device,sn:{},orgId:{},deviceName:{},position:{}.", request.getSerialNumber(), request.getOrgId(), request.getDeviceName(), request.getPosition());
        boolean isValid = true;
        // 判断参数是否为空
        isValid = isValid && (request != null) && (request.getSerialNumber() != null) && (request.getOrgId() != null);
        BizAssert.isTrue(isValid, HiveErrorCode.PARAMS_WRONG, HiveErrorCode.PARAMS_WRONG.getMessage());
        Device device = this.deviceManager.getDeviceBySn(request.getSerialNumber());

        BizAssert.isTrue(device != null, HiveErrorCode.NO_EXIST_EQUIPMENT, HiveErrorCode.NO_EXIST_EQUIPMENT.getMessage());

        OrgInfo orgInfo = orgService.getOrgInfo(request.getOrgId());
        BizAssert.isTrue(orgInfo != null, HiveErrorCode.ORG_ID_VALIDATE_GET_ERROR, HiveErrorCode.ORG_ID_VALIDATE_GET_ERROR.getMessage());

        // 更新设备信息
        DeviceUpdateRequest deviceUpdateRequest = new DeviceUpdateRequest();
        deviceUpdateRequest.setDeviceId(device.getDeviceId());
        deviceUpdateRequest.setDeviceName(request.getDeviceName());
        deviceUpdateRequest.setOrgId(orgInfo.getOrgId());
        deviceUpdateRequest.setPosition(request.getPosition());
        this.deviceManager.updateDevice(deviceUpdateRequest);
        PerfectInfoResponse perfectInfoResponse = new PerfectInfoResponse();
        return new ServiceResponse<PerfectInfoResponse>(perfectInfoResponse);
    }
}
