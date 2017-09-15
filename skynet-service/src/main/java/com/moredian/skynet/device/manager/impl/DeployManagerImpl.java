package com.moredian.skynet.device.manager.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.moredian.bee.common.exception.BizAssert;
import com.moredian.bee.common.utils.BeanUtils;
import com.moredian.bee.common.utils.JsonUtils;
import com.moredian.bee.common.utils.Pagination;
import com.moredian.bee.mybatis.convertor.PaginationConvertor;
import com.moredian.bee.mybatis.domain.PaginationDomain;
import com.moredian.bee.rmq.EventBus;
import com.moredian.bee.rmq.annotation.Subscribe;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.common.model.msg.SyncCloudeyeDeployMsg;
import com.moredian.skynet.common.model.msg.SyncLocalDeployMsg;
import com.moredian.skynet.device.config.DeployProperties;
import com.moredian.skynet.device.convertor.DeployConvertor;
import com.moredian.skynet.device.convertor.DeployDetailConvertor;
import com.moredian.skynet.device.domain.Deploy;
import com.moredian.skynet.device.domain.DeployDetail;
import com.moredian.skynet.device.domain.DeployQueryCondition;
import com.moredian.skynet.device.enums.CloudeyeDeployAction;
import com.moredian.skynet.device.enums.DeployLabel;
import com.moredian.skynet.device.enums.DeployStatus;
import com.moredian.skynet.device.enums.DeviceAction;
import com.moredian.skynet.device.enums.DeviceType;
import com.moredian.skynet.device.manager.CloudeyeHuberConfigProxy;
import com.moredian.skynet.device.manager.DeployDetailManager;
import com.moredian.skynet.device.manager.DeployManager;
import com.moredian.skynet.device.manager.DeviceGroupManager;
import com.moredian.skynet.device.mapper.DeployMapper;
import com.moredian.skynet.device.model.DeployGroupInfo;
import com.moredian.skynet.device.model.DeployInfo;
import com.moredian.skynet.device.request.DeployAddRequest;
import com.moredian.skynet.device.request.DeployQueryRequest;
import com.moredian.skynet.device.request.DeployUpdateRequest;
import com.moredian.skynet.member.service.MemberService;
import com.moredian.skynet.org.enums.BizType;
import com.moredian.skynet.org.service.OrgService;
import com.moredian.idgenerator.service.IdgeneratorService;

@Service
public class DeployManagerImpl implements DeployManager {
	
	private static Logger logger = LoggerFactory.getLogger(DeployManagerImpl.class);
	
	@Autowired
	private DeployMapper deployMapper;
	@Autowired
	private DeployDetailManager deployDetailManager;
	@Autowired
	private CloudeyeHuberConfigProxy cloudeyeHuberConfigProxy;
	@Reference
	private OrgService orgService;
	@Autowired
	private DeployProperties deployProperties;
	@SI
	private MemberService memberService;
	@Autowired
	private DeviceGroupManager deviceGroupManager;
	@SI
	private IdgeneratorService idgeneratorService;
	@Autowired
	private CloudeyeHuberConfigProxy ceHuberConfigProxy;

	@Override
	@Transactional
	public boolean addDeploy(DeployAddRequest request) {
		
		BizAssert.notNull(request.getOrgId(), "orgId must not be null");
		BizAssert.notNull(request.getDeviceId(), "deviceId must not be null");
		BizAssert.notNull(request.getThreshold(), "threshold must not be null");
		BizAssert.notNull(request.getDeployBeginTime(), "deployBeginDate must not be null");
		BizAssert.notNull(request.getDeployEndTime(), "deployEndDate must not be null");
		BizAssert.notEmpty(request.getGroups());
		
		Deploy deploy = DeployConvertor.deployAddRequestToDeploy(request);
		
		List<DeployDetail> deployDetailList = DeployDetailConvertor.deployAddRequestToDeployDetailList(request);
		
		Long deployId = idgeneratorService.getNextIdByTypeName("com.xier.skynet.device.Deploy").getData();
		deploy.setDeployId(deployId);
		deploy.setStatus(DeployStatus.WAIT.getValue());
		
		deployMapper.insert(deploy);
		
		for(DeployDetail deployDetail : deployDetailList) {
			Long deployDetailId = idgeneratorService.getNextIdByTypeName(DeployDetail.class.getName()).getData();
			deployDetail.setDeployDetailId(deployDetailId);
			deployDetail.setDeployId(deployId);
			deployDetail.setOrgId(deploy.getOrgId());
			deployDetailManager.addDeployDetail(deployDetail);
		}
		
		// 布控同步云眼
		SyncCloudeyeDeployMsg msg = new SyncCloudeyeDeployMsg();
		msg.setOrgId(deploy.getOrgId());
		msg.setDeployId(deploy.getDeployId());
		msg.setCloudeyeDeployAction(CloudeyeDeployAction.ADD.getValue());
		EventBus.publish(msg);
		logger.info("发出MQ消息[布控同步云眼]:"+JsonUtils.toJson(msg));
		
		return true;
	}

	@Override
	@Transactional
	public boolean updateDeploy(DeployUpdateRequest request) {
		
		BizAssert.notNull(request.getDeployId(), "deployId must not be null");
		BizAssert.notNull(request.getThreshold(), "threshold must not be null");
		BizAssert.notNull(request.getDeployBeginTime(), "deployBeginDate must not be null");
		BizAssert.notNull(request.getDeployEndTime(), "deployEndDate must not be null");
		
		Deploy deploy = DeployConvertor.deployUpdateRequestToDeploy(request);
		deployMapper.update(deploy);
		
		List<DeployDetail> existDeployDetailList = deployDetailManager.findDeployDetailByDeployId(deploy.getDeployId(), deploy.getOrgId());
		Set<String> existLibraryCodeSet = new HashSet<>();
		if(CollectionUtils.isNotEmpty(existDeployDetailList)) {
			
			for(DeployDetail deployDetail : existDeployDetailList) {
				existLibraryCodeSet.add(deployDetail.getGroupCode());
			}
			
		}
		
		List<DeployDetail> deployDetailList = DeployDetailConvertor.deployUpdateRequestToDeployDetailList(request);
		Map<String, Integer> deployLabelMap = new HashMap<>();
		Set<String> finalLibraryCodeSet = new HashSet<>();
		for(DeployDetail deployDetail: deployDetailList) {
			finalLibraryCodeSet.add(deployDetail.getGroupCode());
			deployLabelMap.put(deployDetail.getGroupCode(), deployDetail.getDeployLabel());
		}
		
		Set<String> existLibraryCodeSet_clone = new HashSet<>();
		existLibraryCodeSet_clone.addAll(existLibraryCodeSet);
		
		Set<String> finalLibraryCodeSet_clone = new HashSet<>();
		finalLibraryCodeSet_clone.addAll(finalLibraryCodeSet);
		
		existLibraryCodeSet.removeAll(finalLibraryCodeSet); // 定位要删的
		for(String libraryCode : existLibraryCodeSet) {
			deployDetailManager.deleteOne(libraryCode, deploy.getDeployId(), deploy.getOrgId());
		}
		
		finalLibraryCodeSet_clone.removeAll(existLibraryCodeSet_clone); // 定位要加的
		for(String libraryCode : finalLibraryCodeSet_clone) {
			
			DeployDetail deployDetail = new DeployDetail();
			
			Long id = idgeneratorService.getNextIdByTypeName("com.xier.skynet.device.DeployDetail").getData();
			deployDetail.setDeployDetailId(id);
			deployDetail.setDeployId(deploy.getDeployId());
			deployDetail.setOrgId(deploy.getOrgId());
			deployDetail.setGroupCode(libraryCode);
			deployDetail.setDeployLabel(deployLabelMap.get(libraryCode));
			
			deployDetailManager.addDeployDetail(deployDetail);
		}
		
		return true;
	}

	@Override
	public boolean stopDeploy(Long deployId, Long orgId) {
		BizAssert.notNull(deployId, "deployId must not be null");
		BizAssert.notNull(orgId, "orgId must not be null");
		deployMapper.updateStatus(deployId, orgId, DeployStatus.OVER.getValue());
		return true;
	}

	@Override
	public Deploy getDeployByDevice(Long orgId, Long deviceId) {
		return deployMapper.loadByDevice(orgId, deviceId);
	}

	@Override
	@Transactional
	public boolean deleteDeploy(Long deployId, Long orgId) {
		BizAssert.notNull(deployId, "deployId must not be null");
		BizAssert.notNull(orgId, "orgId must not be null");
		deployMapper.updateStatus(deployId, orgId, DeployStatus.DELETE.getValue());
		return true;
	}

	@Override
	public Deploy getDeployById(Long deployId, Long orgId) {
		BizAssert.notNull(deployId, "deployId must not be null");
		BizAssert.notNull(orgId, "orgId must not be null");
		return deployMapper.load(deployId, orgId);
	}
	
	public DeployQueryCondition deployQueryRequestToDeployQueryCondition(DeployQueryRequest request) {
		DeployQueryCondition condition = new DeployQueryCondition();
		
		condition.setOrgId(request.getOrgId());
		condition.setDeviceId(request.getDeviceId());
		condition.setStatus(request.getStatus());
		
		return condition;
	}
	
	public Deploy deployInfoToDeploy(DeployInfo deployInfo) {
		if (deployInfo == null) return null;
		Deploy deploy = new Deploy();
		deploy.setDeployBeginTime(deployInfo.getDeployBeginTime());
		deploy.setDeployEndTime(deployInfo.getDeployoEndTime());
		deploy.setDeployId(deployInfo.getDeployId());
		deploy.setDeviceId(deployInfo.getDeviceId());
		deploy.setGmtCreate(deployInfo.getGmtCreate());
		deploy.setGmtModify(deployInfo.getGmtModify());
		deploy.setNoticeRoles(deployInfo.getNoticeRoles());
		deploy.setOrgId(deployInfo.getOrgId());
		deploy.setStatus(deployInfo.getStatus());
		deploy.setThreshold(deployInfo.getThreshold());
		return deploy;
	}
	
	public List<Deploy> deployeInfoListToDeployList(List<DeployInfo> deployInfoList) {
		if (deployInfoList == null) return null;
		
		List<Deploy> deployList = new ArrayList<>();
		for(DeployInfo deployInfo : deployInfoList) {
			deployList.add(deployInfoToDeploy(deployInfo));
		}
		
		return deployList;
	}
	
	public PaginationDomain<Deploy> paginationDeployInfoToPaginationDeploy(Pagination<DeployInfo> fromPagination) {
		PaginationDomain<Deploy> toPagination = PaginationConvertor.paginationToPaginationDomain(fromPagination, new PaginationDomain<Deploy>());
		if (toPagination == null)
			return null;
		toPagination.setData(deployeInfoListToDeployList(fromPagination.getData()));
		return toPagination;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginationDomain<Deploy> findPaginationDeploy(DeployQueryRequest request, Pagination<DeployInfo> deployInfoPagination) {
		BizAssert.notNull(request.getOrgId(), "orgId must not be null");
		
		DeployQueryCondition queryCondition =deployQueryRequestToDeployQueryCondition(request);
		PaginationDomain<Deploy> deployPagination = paginationDeployInfoToPaginationDeploy(deployInfoPagination);
		
		return this.getPagination(deployPagination, queryCondition);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected  PaginationDomain getPagination(PaginationDomain pagination, DeployQueryCondition queryCondition) {
        int totalCount = (Integer) deployMapper.getTotalCountByCondition(queryCondition);
        pagination.setTotalCount(totalCount);
        if (totalCount > 0) {
        	queryCondition.setStartRow(pagination.getStartRow());
        	queryCondition.setPageSize(pagination.getPageSize());
        	pagination.setData(deployMapper.findPaginationByCondition(queryCondition));
        }
        return pagination;
    }
	
	@SuppressWarnings("rawtypes")
	public int getStartRow(PaginationDomain pagination) {
        if (pagination.getPageNo() > 1) {
            return (pagination.getPageSize() * (pagination.getPageNo() - 1));
        } else {
            return 0;
        }
	}
	
	/**
	 * 设备是否支持布控
	 * @param device
	 * @return
	 */
	private boolean isNeedDeploy(int deviceType){
		
    	if(DeviceType.isNeedDeployDevice(deviceType)) {
    		return true;
    	}
    	
    	return false;
	}
	
	private Date getSomeYearsLaterDate(int years) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, years);
		return cal.getTime();
	}
	
	/**
	 * 创建布控实例
	 */
	private void installDeploy(SyncLocalDeployMsg deviceLog) {
		
		DeployAddRequest request = new DeployAddRequest();
		int cameraFlag = DeviceType.CAMERA.getValue() == deviceLog.getDeviceType() ? 1 : 0;
		request.setCameraFlag(cameraFlag);
		request.setDeployBeginTime(new Date());
		request.setDeployEndTime(this.getSomeYearsLaterDate(100));
		request.setDeviceId(deviceLog.getDeviceId());
		List<DeployGroupInfo> groups = new ArrayList<>();
		
		List<Long> groupIdList = deviceGroupManager.findGroupIdByDeviceId(deviceLog.getOrgId(), deviceLog.getDeviceId());
		for(Long groupId : groupIdList) {
			DeployGroupInfo group = new DeployGroupInfo();
			group.setDeployLabel(DeployLabel.WHITE.getValue());
			group.setGroupCode(groupId.toString());
			groups.add(group);
		}
		
		request.setGroups(groups);
		//request.setNoticeRoles(noticeRoles);
		request.setOrgId(deviceLog.getOrgId());
		request.setThreshold(deployProperties.getThreshold());
		this.addDeploy(request);
		
		logger.info("------新的布控实例："+JsonUtils.toJson(request));
	}
	
	/**
	 * 调整布控实例
	 * @param deviceLog
	 */
	private void updateDeployGroup(SyncLocalDeployMsg deviceLog) {
		
		Deploy deploy = this.getDeployByDevice(deviceLog.getOrgId(), deviceLog.getDeviceId());
		
		DeployUpdateRequest request = BeanUtils.copyProperties(DeployUpdateRequest.class, deploy);
		
		List<DeployGroupInfo> groups = new ArrayList<>();
		List<Long> groupIdList = deviceGroupManager.findGroupIdByDeviceId(deviceLog.getOrgId(), deviceLog.getDeviceId());
		for(Long groupId : groupIdList) {
			DeployGroupInfo group = new DeployGroupInfo();
			group.setDeployLabel(DeployLabel.WHITE.getValue());
			group.setGroupCode(groupId.toString());
			groups.add(group);
		}
		request.setGroups(groups);
		
		this.updateDeploy(request);
		logger.info("------调整后的布控实例"+JsonUtils.toJson(request));
	}
	
	/**
	 * 卸载布控
	 * @param deviceLog
	 */
	private void uninstallDeploy(SyncLocalDeployMsg deviceLog) {
		deployMapper.updateStatusByDeviceId(deviceLog.getOrgId(), deviceLog.getDeviceId(), DeployStatus.DELETE.getValue());
		logger.info("------已删除设备布控实例");
	}
	
	
	@Subscribe
	public void subscribeDeviceMsg(SyncLocalDeployMsg deviceMsg) {
		
		logger.info("收到MQ消息[同步本地布控实例]："+JsonUtils.toJson(deviceMsg));
				
		if(!this.isNeedDeploy(deviceMsg.getDeviceType())) {
			logger.info("------设备布控同步被忽略：设备类型["+deviceMsg.getDeviceType()+"]为非识别设备");
			return;
		}
		if(!orgService.isBizEnable(deviceMsg.getOrgId(), BizType.RECOGNIZE.getValue())) {
			logger.info("------设备布控同步被忽略：机构["+deviceMsg.getOrgId()+"]尚未开启人脸识别能力");
			return;
		}
		
		if(DeviceAction.ACTIVE.getValue() == deviceMsg.getDeviceAction()) {
			logger.info("------设备激活操作，开始创建设备布控实例");
			this.installDeploy(deviceMsg);
		} 
		
		if(DeviceAction.UPDATE_GROUP.getValue() == deviceMsg.getDeviceAction()) {
			logger.info("------设备群组变更操作，开始调整设备布控实例");
			this.updateDeployGroup(deviceMsg);
		}
		
		if(DeviceAction.DELETE.getValue() == deviceMsg.getDeviceAction()) {
			logger.info("------设备删除操作，开始卸载设备布控实例");
			this.uninstallDeploy(deviceMsg);
		}
				
		
	}

	/**
	 * 停止布控(已过布控时间)
	 */
	private void autoStopDeploy() {
		// 查询可停止的布控
		List<Deploy> stopDeployList = deployMapper.findForStop(DeployStatus.INUSE.getValue());
		if(CollectionUtils.isNotEmpty(stopDeployList)) {
			for(Deploy deploy : stopDeployList) {
				try {
					cloudeyeHuberConfigProxy.disMountHuberNode(deploy.getOrgId(), deploy.getDeployId());
					// 停止布控
					deploy.setStatus(DeployStatus.OVER.getValue());
					deployMapper.updateStatus(deploy.getDeployId(), deploy.getOrgId(), deploy.getStatus());
				} catch (Exception e) {
					logger.info("---------停止布控失败----------");
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 启动布控(已达布控时间)
	 */
	private void autoStartDeploy() {
		// 查询可启用的布控
		List<Deploy> startDeployList = deployMapper.findForStart(DeployStatus.WAIT.getValue());
		if(CollectionUtils.isNotEmpty(startDeployList)) {
			for(Deploy deploy : startDeployList) {
				try {
					cloudeyeHuberConfigProxy.mountHuberNode(deploy.getOrgId(), deploy.getDeployId());
					// 启动布控
					deploy.setStatus(DeployStatus.INUSE.getValue());
					deployMapper.updateStatus(deploy.getDeployId(), deploy.getOrgId(), deploy.getStatus());
				} catch (Exception e) {
					logger.info("---------开启布控失败----------");
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 接收云眼布控同步消息
	 * @param msg
	 */
	@Subscribe
	public void receiveCEDeploySyncMsg(SyncCloudeyeDeployMsg msg) {
		logger.info("收到MQ消息[云眼布控同步]:"+JsonUtils.toJson(msg));
		this.doSyncCloudeyeDeploy(msg.getOrgId(), msg.getDeployId(), msg.getCloudeyeDeployAction());
	}
	
	private void doSyncCloudeyeDeploy(Long orgId, Long deployId, Integer cloudeyeDeployAction) {
		Deploy deploy = deployMapper.load(deployId, orgId);
		if(cloudeyeDeployAction == CloudeyeDeployAction.ADD.getValue()) {
			cloudeyeHuberConfigProxy.mountHuberNode(deploy.getOrgId(), deploy.getDeployId());
			deploy.setStatus(DeployStatus.INUSE.getValue());
			deployMapper.updateStatus(deploy.getDeployId(), deploy.getOrgId(), deploy.getStatus());
			return;
		}
		
		if(cloudeyeDeployAction == CloudeyeDeployAction.DELETE.getValue()) {
			cloudeyeHuberConfigProxy.disMountHuberNode(deploy.getOrgId(), deploy.getDeployId());
			deploy.setStatus(DeployStatus.OVER.getValue());
			deployMapper.updateStatus(deploy.getDeployId(), deploy.getOrgId(), deploy.getStatus());
			return;
		}
		
		if(cloudeyeDeployAction == CloudeyeDeployAction.CAMERA_BIND.getValue()) {
			cloudeyeHuberConfigProxy.bindCameraToBox(deploy.getOrgId(), deploy.getDeployId());
			return;
		}
		
		if(cloudeyeDeployAction == CloudeyeDeployAction.CAMERA_UNBIND.getValue()) {
			cloudeyeHuberConfigProxy.unbindCameraToBox(deploy.getOrgId(), deploy.getDeployId());
			return;
		}
		
		if(cloudeyeDeployAction == CloudeyeDeployAction.UPDATE.getValue()) {
			cloudeyeHuberConfigProxy.disMountHuberNode(deploy.getOrgId(), deploy.getDeployId());
			cloudeyeHuberConfigProxy.mountHuberNode(deploy.getOrgId(), deploy.getDeployId());
			return;
		}
		
	}

	@Override
	public void autoToggleDeployState() {
		this.autoStopDeploy();
		this.autoStartDeploy();
	}

	@Override
	public boolean deployCloudeye(Long orgId, Long deployId, Integer cloudeyeDeployAction) {
		BizAssert.notNull(orgId);
		BizAssert.notNull(deployId);
		BizAssert.notNull(cloudeyeDeployAction);
		
		SyncCloudeyeDeployMsg msg = new SyncCloudeyeDeployMsg();
		msg.setOrgId(orgId);
		msg.setDeployId(deployId);
		msg.setCloudeyeDeployAction(cloudeyeDeployAction);
		EventBus.publish(msg);
		logger.info("发出MQ消息[布控同步云眼]:"+JsonUtils.toJson(msg));
		
		return true;
	}
	
	@Override
	public boolean clearOHuberConfig(Long orgId) {
		BizAssert.notNull(orgId);
		ceHuberConfigProxy.clearOHuberConfig(orgId);
		return true;
	}


}
