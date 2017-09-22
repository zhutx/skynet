package com.moredian.skynet.device.service;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.skynet.device.model.CameraInfo;
import com.moredian.skynet.device.request.BoxUpdateRequest;
import com.moredian.skynet.device.request.CameraAddRequest;
import com.moredian.skynet.device.request.CameraQueryRequest;
import com.moredian.skynet.device.request.CameraUpdateRequest;

/**
 * 摄像机服务
 * @author zhutx
 *
 */
public interface CameraService {

		/**
         * 添加摄像机
         * @param request
         * @return
         * <li>CE_DEVICE_ADD_FAIL</li>
         */
	ServiceResponse<Long> addDevice(CameraAddRequest request);
	
	/**
	 * 修改摄像机
	 * @param request
	 * @return
	 * <li>DEVICE_NOT_EXIST</li>
	 */
	ServiceResponse<Boolean> updateDevice(CameraUpdateRequest request);
	
	/**
	 * 删除摄像机
	 * @param orgId
	 * @param deviceId
	 * @return
	 * <li>DEVICE_NOT_EXIST</li>
	 * <li>CE_DEVICE_DELETE_FAIL</li>
	 */
	ServiceResponse<Boolean> deleteDevice(Long orgId, Long deviceId);
	
	/**
	 * 获取摄像机信息
	 * @param orgId
	 * @param deviceId
	 * @return
	 */
	CameraInfo getDeviceById(Long orgId, Long deviceId);
	
	/**
	 * 分页查询设备
	 * @param request
	 * @param pagination
	 * @return
	 */
	Pagination<CameraInfo> findPaginationDevice(CameraQueryRequest request, Pagination<CameraInfo> pagination);


	/**
	 * 将摄像机和魔点盒子绑定
	 * @param orgId
	 * @param cameraId
	 * @param deviceId
	 * @return
	 */
	ServiceResponse<Boolean> bindCameraWithDevice(Long orgId,Long cameraId,Long deviceId);


	/**
	 * 将摄像机和盒子解绑
	 * @param orgId
	 * @param cameraId
	 * @param deviceId
	 * @return
	 */
	 ServiceResponse<Boolean> unBindCameraWithDevice(Long orgId, Long cameraId, Long deviceId);


	/**
	 * 更新摄像头和盒子
	 * @param boxUpdateRequest
	 * @return
	 */
	 ServiceResponse<Boolean> updateBoxCamera(BoxUpdateRequest boxUpdateRequest);


	CameraInfo getCameraDeviceByBoxId(Long boxId, Long orgId);


}
