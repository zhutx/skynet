package com.moredian.fishnet.device.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moredian.bee.common.exception.BizException;
import com.moredian.bee.common.utils.ExceptionUtils;
import com.moredian.bee.common.utils.JsonUtils;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.cloudeye.core.api.conf.huber.OHCNodeType;
import com.moredian.cloudeye.core.api.conf.huber.OHCRecognizeChainItem;
import com.moredian.cloudeye.core.api.conf.huber.OHCRecognizeHistoryItem;
import com.moredian.cloudeye.core.api.conf.huber.OHCRecognizeThresholdItem;
import com.moredian.cloudeye.core.api.conf.huber.OHuberConfig;
import com.moredian.cloudeye.core.api.conf.huber.OHuberConfigNode;
import com.moredian.cloudeye.core.api.conf.huber.OHuberConfigProvider;
import com.moredian.fishnet.device.domain.Deploy;
import com.moredian.fishnet.device.domain.DeployDetail;
import com.moredian.fishnet.device.domain.Device;
import com.moredian.fishnet.device.domain.DeviceMatch;
import com.moredian.fishnet.device.enums.DeployLabel;
import com.moredian.fishnet.device.enums.DeviceErrorCode;
import com.moredian.fishnet.device.enums.DeviceType;
import com.moredian.fishnet.device.manager.CloudeyeHuberConfigProxy;
import com.moredian.fishnet.device.manager.DeployManager;
import com.moredian.fishnet.device.manager.DeviceManager;
import com.moredian.fishnet.device.manager.DeviceMatchManager;
import com.moredian.fishnet.device.mapper.DeployDetailMapper;
import com.moredian.fishnet.org.service.OrgService;

@Service
public class CloudeyeHuberConfigProxyImpl implements CloudeyeHuberConfigProxy {
	
	private static Logger logger = LoggerFactory.getLogger(CloudeyeHuberConfigProxyImpl.class);
	
	private static final int SIMILAR_NUM = 5;
	
	@SI
	private OHuberConfigProvider oHuberConfigProvider;
	@SI
	private OrgService orgService;
	@Autowired
	private DeployManager deployManager;
	@Autowired
	private DeviceManager deviceManager;
	@Autowired
	private DeviceMatchManager deviceMatchManager;
	@Autowired
	private DeployDetailMapper deployDetailMapper;
	
	private void printConfLog(long orgId) {
		OHuberConfig oHuberConfig = oHuberConfigProvider.loadOhuberConfig(orgId);
		String json = JsonUtils.toJson(oHuberConfig);
		logger.info("==========变更后的Huber配置：\n"+json);
	}

	@Override
	public void initHuberConfig(long orgId, boolean isUseCeRd) {
		
		OHuberConfig oHuberConfig = oHuberConfigProvider.loadOhuberConfig(orgId);
		if(oHuberConfig.getLastUpdateTime() != 0) return;
		
		oHuberConfig =oHuberConfigProvider.initOHuberConfig(orgId, isUseCeRd);
		
		logger.info(JsonUtils.toJson(oHuberConfig));
	}
	
	@Override
	public void mountHuberNode(long orgId, long deployId) {
		Deploy deploy = deployManager.getDeployById(deployId, orgId);
		this.addDeviceToServer(deploy);
	}
	
	private Device getDevice(String deviceSn){
		return deviceManager.getDeviceBySn(deviceSn);
	}
	
	public OHuberConfigNode getNode(long orgId, String uniqueNumber){
		OHuberConfig oHuberConfig = oHuberConfigProvider.loadOhuberConfig(orgId);
		if(oHuberConfig == null) return null;
		
		OHuberConfigNode node = oHuberConfig.findNodeByCode(uniqueNumber);
		if(node == null) return null;
		
		return node;
	}
	
	private boolean removeCameraFromBox(long orgId, String boxSn, String cameraSn) {
		
		logger.info("=========开始: 将摄像机["+cameraSn+"]从盒子["+boxSn+"]上移除");
		
		OHuberConfigNode boxNode = this.getNode(orgId, boxSn);
		if(boxNode == null) {
			logger.info("=========盒子节点配置不存在，操作被忽略");
			return true; // 盒子不存在，则忽略
		}
		
		boxNode.removeChild(cameraSn);
		oHuberConfigProvider.configNode(orgId, null, boxNode);
		
		this.printConfLog(orgId);
		
		logger.info("=========结束: 将摄像头["+cameraSn+"]从盒子["+boxSn+"]上移除");
		
		return true;
	}
	
	private void removeNodeConfig(long orgId, String deviceSn) {
		
		if(StringUtils.isBlank(deviceSn)) return;
		
		Device device = this.getDevice(deviceSn);
		
		logger.info("=========开始: 移除Huber节点配置：\n"+device.getDeviceName()+"[code:"+deviceSn+"]");
		
		if(this.getNode(orgId, deviceSn) == null) {
			logger.info("=========Huber节点配置不存在，操作被忽略");
			return;
		}
		
		boolean result = oHuberConfigProvider.removeNode(orgId, deviceSn);
		if(result) {
			logger.info("=========Huber节点配置移除成功");
		} else {
			logger.info("=========Huber节点配置移除失败");
		}
		
		this.printConfLog(orgId);
		
		logger.info("=========结束: 移除Huber节点配置：\n"+device.getDeviceName()+"[code:"+deviceSn+"]");
		
		
	}
	
	private void removeMatchDevicesFromServer(Deploy deploy) {
		
		logger.info("=========开始: 将匹配的“盒子-摄像机”节点链路从火蚁服务器上移除");
		
		Long orgId = deploy.getOrgId();
		
		// 获取摄像头设备
		Device camera = deviceManager.getDeviceById(deploy.getDeviceId(), orgId);
		
		if(camera == null) {
			logger.info("=========布控位置摄像头已删除，操作被忽略");
			return;
		}
		// 获取摄像头的匹配关系
		DeviceMatch deviceMatch = deviceMatchManager.getByCameraId(camera.getDeviceId(), orgId);
		if(deviceMatch == null) {
			logger.info("=========未找到匹配的盒子，操作被忽略");
			return;
		}
		// 获取盒子设备
		Device box = deviceManager.getDeviceById(deviceMatch.getBoxId(), orgId);
		if(box == null) {
			logger.info("=========盒子已被删除，操作被忽略");
			return;
		}
		
		// 先移除摄像头
		this.removeCameraFromBox(orgId, box.getDeviceSn(), camera.getDeviceSn());
		
		// 检查盒子，如果没有子节点了，移除盒子
		OHuberConfig oHuberConfig = oHuberConfigProvider.loadOhuberConfig(orgId);
		OHuberConfigNode boxNode = oHuberConfig.findNodeByCode(box.getDeviceSn());
		if(boxNode == null) {
			logger.info("=========盒子节点配置不存在，操作被忽略");
			return;
		}
		if(!boxNode.existChild()) { // 不存在子节点，则删除盒子
			this.removeNodeConfig(orgId, box.getDeviceSn());
		}
		
		this.printConfLog(orgId);
		
		logger.info("=========结束: 将匹配的“盒子-摄像机”节点链路从火蚁服务器上移除");
		
	}
	
	private void removeDeviceFromServer(Deploy deploy) {
		
		logger.info("=========开始: 将主板节点从火蚁服务器上移除");
		
		Long orgId = deploy.getOrgId();
		
		// 获取盒子设备
		Device box = deviceManager.getDeviceById(deploy.getDeviceId(), orgId);
		if(box == null) {
			logger.info("=========主板已被删除，操作被忽略");
			return;
		}
		
		this.removeNodeConfig(orgId, box.getDeviceSn());
		
		this.printConfLog(orgId);
		
		logger.info("=========结束: 将主板节点从火蚁服务器上移除");
		
	}

	@Override
	public void disMountHuberNode(long orgId, long deployId) {
		Deploy deploy = deployManager.getDeployById(deployId, orgId);
		
		Device device = deviceManager.getDeviceById(orgId, deploy.getDeviceId());
		
		if(device.getDeviceType() == DeviceType.BOARD_BOX.getValue()) {
			this.removeMatchDevicesFromServer(deploy);
		} else {
			this.removeDeviceFromServer(deploy);
		}
	}
	
	/**
	 * 将盒子挂到火蚁服务器上，如果已存在盒子，则返回盒子配置
	 * @return
	 */
	private OHuberConfigNode mountBoxToServer(String boxSn, Deploy deploy) {
		
		List<DeployDetail> deployDetailList = deployDetailMapper.findByDeployId(deploy.getDeployId(), deploy.getOrgId());
		
		logger.info("=========将盒子["+boxSn+"]挂到火蚁服务器上");
		
		OHuberConfig oHuberConfig = oHuberConfigProvider.loadOhuberConfig(deploy.getOrgId());
		OHuberConfigNode boxNode = oHuberConfig.findNodeByCode(boxSn);
		if(boxNode != null) {
			logger.info("=========盒子接节点配置已存在，操作被忽略");
			return boxNode; // 盒子已存在，则返回盒子配置
		}
		
		// 盒子不存在，则配置盒子节点
		boxNode = new OHuberConfigNode();
		boxNode.setCode(boxSn);
		boxNode.setNodeType(OHCNodeType.TURNIP_VERIFY.name());
		boxNode.setSimilarNum(SIMILAR_NUM);
		boxNode.setChainItems(this.buildChainItemList(deployDetailList)); // 从布控信息里获取识别链（底库）
		boxNode.setThresholdItem(this.buildThresholdItem(deploy)); // 从布控信息里获取阈值
		//boxNode.setBusinessType(BusinessType.EAGLE_MONITOR.getValue());
		//boxNode.setChildren(children);
		//boxNode.setExtend(extend);
		//boxNode.setSubscriberItems(subscriberItems);
		
		/*// 获取火蚁服务器
		OHuberConfigNode serverNode = this.getOHuberServer(orgId);
		
		// 将盒子挂到火蚁服务器上
		logger.info("=========配置悉尔盒子节点至火蚁服务器");
		logger.info("=========parentCode参数："+serverNode.getCode());
		logger.info("=========node参数：\n"+JsonUtils.toJson(boxNode));
		oHuberConfigProvider.configNode(orgId, serverNode.getCode(), boxNode);*/
		
		// 将盒子挂到火蚁服务器上
		logger.info("配置盒子节点至火蚁服务器");
		logger.info("node参数：\n"+JsonUtils.toJson(boxNode));
		oHuberConfigProvider.configNode(deploy.getOrgId(), null, boxNode);
		
		return boxNode;
	}
	
	/**
	 * 获取黑白名单
	 * @param deployLabel
	 * @return
	 */
	public static int getTypeForChainItem(int deployLabel){
		if(deployLabel == DeployLabel.BLACK.getValue()) {
			return OHCRecognizeChainItem.TYPE_BLACK;
		} else {
			return OHCRecognizeChainItem.TYPE_WHITE;
		}
	}
	
	/**
	 * 构建识别链ItemList
	 * @param deployDetailList
	 * @return
	 */
	private List<OHCRecognizeChainItem> buildChainItemList(List<DeployDetail> deployDetailList) {
		List<OHCRecognizeChainItem> chainItems = new ArrayList<>();
		int index = 0;
		for(DeployDetail deployDetail : deployDetailList) {
			OHCRecognizeChainItem item = new OHCRecognizeChainItem();
			item.setIndex(index);
			item.setTree(false);
			item.setType(getTypeForChainItem(deployDetail.getDeployLabel()));
			item.setValue(String.valueOf(deployDetail.getGroupCode()));
			chainItems.add(item);
			index++;
		}
		return chainItems;
	}
	
	/**
	 * 构建阈值Item
	 * @param deploy
	 * @return
	 */
	private OHCRecognizeThresholdItem buildThresholdItem(Deploy deploy) {
		OHCRecognizeThresholdItem thresholdItem = new OHCRecognizeThresholdItem();
		thresholdItem.setVideoVerifyConfidence(deploy.getThreshold().intValue());
		thresholdItem.setImageVerifyConfidence(deploy.getThreshold().intValue());
		thresholdItem.setImageImageMatchConfidence(68);
		return thresholdItem;
	}
	
	/**
	 * 构建摄像头配置节点
	 * @param cameraSn
	 * @param deploy
	 * @return
	 */
	private OHuberConfigNode buildCameraConfigNode(String cameraSn, Deploy deploy) {
		
		List<DeployDetail> deployDetailList = deployDetailMapper.findByDeployId(deploy.getDeployId(), deploy.getOrgId());
		
		OHuberConfigNode node = new OHuberConfigNode();
		node.setCode(cameraSn);
		node.setChainItems(this.buildChainItemList(deployDetailList)); // 从摄像头的布控信息里获取识别链（底库）
		node.setThresholdItem(this.buildThresholdItem(deploy)); // 从摄像头的布控信息里获取阈值
		node.setNodeType(OHCNodeType.IPCAM.name());
		node.setSimilarNum(SIMILAR_NUM);
		//node.setBusinessType(BusinessType.EAGLE_MONITOR.getValue());
		OHCRecognizeHistoryItem historyItem = new OHCRecognizeHistoryItem();
		historyItem.setEnable(true);
		node.setHistoryItem(historyItem);
		//node.setChildren(children);
		//node.setExtend(extend);
		//node.setSubscriberItems(subscriberItems);
		return node;
	}
	
	private OHuberConfigNode buildBoardConfigNode(String boardSn, Deploy deploy) {
		
		List<DeployDetail> deployDetailList = deployDetailMapper.findByDeployId(deploy.getDeployId(), deploy.getOrgId());
		
		OHuberConfigNode node = new OHuberConfigNode();
		node.setCode(boardSn);
		node.setNodeType(OHCNodeType.TURNIP_VERIFY.name());
		node.setSimilarNum(SIMILAR_NUM);
		node.setChainItems(this.buildChainItemList(deployDetailList)); // 从布控信息里获取识别链（底库）
		node.setThresholdItem(this.buildThresholdItem(deploy)); // 从布控信息里获取阈值
		
		/* 
		 * 如果需要则开启识别历史
		OHCRecognizeHistoryItem historyItem = new OHCRecognizeHistoryItem();
		historyItem.setEnable(true);
		node.setHistoryItem(historyItem);
		*/
		
		//node.setBusinessType(BusinessType.EAGLE_MONITOR.getValue());
		//node.setChildren(children);
		//node.setExtend(extend);
		//node.setSubscriberItems(subscriberItems);
		
		return node;
	}
	
	private void addDeviceToServer(Deploy deploy) {
		
		Long orgId = deploy.getOrgId();
		
		logger.info("=========开始: 将节点挂到火蚁服务器上");
		
		logger.info("=========布控id"+ deploy.getDeployId());
		
		// 获取设备
		Device device = deviceManager.getDeviceById(orgId, deploy.getDeviceId());
		
		OHuberConfig oHuberConfig = oHuberConfigProvider.loadOhuberConfig(orgId);
		OHuberConfigNode boardNode = oHuberConfig.findNodeByCode(device.getDeviceSn());
		if(boardNode != null) {
			logger.info("=========盒子接节点配置已存在，操作被忽略");
			return; // 盒子已存在，则返回盒子配置
		}
		
		// 创建主板节点配置
		boardNode = this.buildBoardConfigNode(device.getDeviceSn(), deploy);
		
		// 将主板挂到服务器上
		try {
			oHuberConfigProvider.configNode(orgId, null, boardNode);
		} catch (BizException e) {
			ExceptionUtils.throwException(DeviceErrorCode.DEVICE_CE_DEPLOY_ERROR, DeviceErrorCode.DEVICE_CE_DEPLOY_ERROR.getMessage());
		}
				
		this.printConfLog(orgId);
		
		logger.info("=========结束: 将节点挂到火蚁服务器上");
		
	}

	private void addMatchDevicesToServer(Deploy deploy) {
		
		Long orgId = deploy.getOrgId();
		
		logger.info("=========开始: 将匹配的“盒子-摄像机”节点链路挂到火蚁服务器上");
		
		logger.info("=========布控id"+ deploy.getDeployId());
		
		// 获取摄像头设备
		Device camera = deviceManager.getDeviceById(deploy.getDeviceId(), orgId);
		// 获取摄像头的匹配关系
		DeviceMatch deviceMatch = deviceMatchManager.getByCameraId(camera.getDeviceId(), orgId);
		// 获取盒子设备
		Device box = deviceManager.getDeviceById(deviceMatch.getBoxId(), orgId);
		
		// 将盒子挂载到火蚁服务器上
		OHuberConfigNode boxNode = this.mountBoxToServer(box.getDeviceSn(), deploy);
		
		// 创建摄像机节点配置
		OHuberConfigNode cameraNode = this.buildCameraConfigNode(camera.getDeviceSn(), deploy);
		
		// 将摄像头挂到盒子上
		logger.info("=========将摄像头挂到盒子上");
		logger.info("=========parentCode参数："+boxNode.getCode());
		logger.info("=========node参数：\n"+JsonUtils.toJson(cameraNode));
		
		oHuberConfigProvider.configNode(orgId, boxNode.getCode(), cameraNode);
		
		this.printConfLog(orgId);
		
		logger.info("=========结束: 将匹配的“盒子-摄像机”节点链路挂到火蚁服务器上");
		
	}

	@Override
	public void clearOHuberConfig(long orgId) {
		oHuberConfigProvider.removeOhuberConfig(orgId);
	}

	@Override
	public void bindCameraToBox(long orgId, long deployId) {
		
		Deploy deploy = deployManager.getDeployById(deployId, orgId);
		

		// 获取盒子设备
		Device box = deviceManager.getDeviceById(orgId, deploy.getDeviceId());
		// 获取摄像头的匹配关系
		DeviceMatch deviceMatch = deviceMatchManager.getByBoxId(box.getDeviceId(), orgId);
		// 获取摄像机设备
		Device camera = deviceManager.getDeviceById(orgId, deviceMatch.getCameraId());
				
		// 创建摄像机节点配置
		OHuberConfigNode cameraNode = this.buildCameraConfigNode(camera.getDeviceSn(), deploy);
		
		// 将摄像头挂到盒子上
		logger.info("=========将摄像头挂到盒子上");
		logger.info("=========parentCode参数："+box.getDeviceSn());
		logger.info("=========node参数：\n"+JsonUtils.toJson(cameraNode));
		
		oHuberConfigProvider.configNode(orgId, box.getDeviceSn(), cameraNode);
		
		this.printConfLog(orgId);
		
	}

	@Override
	public void unbindCameraToBox(long orgId, long deployId) {
		
		Deploy deploy = deployManager.getDeployById(deployId, orgId);
		
		// 获取摄像头设备
		Device camera = deviceManager.getDeviceById(deploy.getDeviceId(), orgId);
		// 获取摄像头的匹配关系
		DeviceMatch deviceMatch = deviceMatchManager.getByCameraId(camera.getDeviceId(), orgId);
		// 获取盒子设备
		Device box = deviceManager.getDeviceById(deviceMatch.getBoxId(), orgId);
		
		// 先移除摄像头
		this.removeCameraFromBox(orgId, box.getDeviceSn(), camera.getDeviceSn());
		
	}

}
