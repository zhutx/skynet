package com.moredian.fishnet.device.convertor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.moredian.fishnet.device.domain.DeployDetail;
import com.moredian.fishnet.device.model.DeployGroupInfo;
import com.moredian.fishnet.device.request.DeployAddRequest;
import com.moredian.fishnet.device.request.DeployUpdateRequest;

public class DeployDetailConvertor {
	
	public static List<DeployDetail> deployAddRequestToDeployDetailList(DeployAddRequest request) {
		if (request == null) return null;
		
		List<DeployDetail> deployDetailList = new ArrayList<>();
		if(CollectionUtils.isEmpty(request.getGroups())) return deployDetailList;
		
		for(DeployGroupInfo libraryInfo : request.getGroups()) {
			DeployDetail deployDetail = new DeployDetail();
			deployDetail.setGroupCode(libraryInfo.getGroupCode());
			deployDetail.setDeployLabel(libraryInfo.getDeployLabel());
			deployDetailList.add(deployDetail);
		}
		
		return deployDetailList;
	}
	
	public static List<DeployDetail> deployUpdateRequestToDeployDetailList(DeployUpdateRequest request) {
		if (request == null) return null;
		
		List<DeployDetail> deployDetailList = new ArrayList<>();
		if(CollectionUtils.isEmpty(request.getGroups())) return deployDetailList;
		
		for(DeployGroupInfo libraryInfo : request.getGroups()) {
			DeployDetail deployDetail = new DeployDetail();
			deployDetail.setGroupCode(libraryInfo.getGroupCode());
			deployDetail.setDeployLabel(libraryInfo.getDeployLabel());
			deployDetailList.add(deployDetail);
		}
		
		return deployDetailList;
	}

}
