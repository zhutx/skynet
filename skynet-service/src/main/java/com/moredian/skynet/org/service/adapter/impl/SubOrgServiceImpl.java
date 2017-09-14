package com.moredian.skynet.org.service.adapter.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.tube.annotation.SI;
import com.moredian.skynet.org.domain.Group;
import com.moredian.skynet.org.domain.Position;
import com.moredian.skynet.org.enums.PositionType;
import com.moredian.skynet.org.enums.YesNoFlag;
import com.moredian.skynet.org.manager.GroupManager;
import com.moredian.skynet.org.manager.PositionManager;
import com.moredian.skynet.org.request.FlatPositionAddRequest;
import com.moredian.skynet.org.service.adapter.SubOrgService;
import com.moredian.skynet.org.service.adapter.dto.SubOrgDto;

@Component("adapterSubOrgService")
@SI
public class SubOrgServiceImpl implements SubOrgService {
	
	private static final Logger logger = LoggerFactory.getLogger(SubOrgServiceImpl.class);
	
	@Autowired
	private GroupManager groupManager;
	@Autowired
	private PositionManager positionManager;

	private SubOrgDto groupToSubOrgDto(Group group) {
		SubOrgDto subOrgDto = new SubOrgDto();
		subOrgDto.setSubOrgId(group.getGroupId());
		subOrgDto.setSubOrgCode(group.getGroupCode());
		return subOrgDto;
	}
	
	@Override
	public ServiceResponse<SubOrgDto> getSubOrgById(Long id, Long orgId) {
		logger.debug("###################getSubOrgById###################");
		Group group = groupManager.getGroupById(orgId, id);
		return new ServiceResponse<SubOrgDto>(true, null, groupToSubOrgDto(group));
	}
	
	private SubOrgDto positionToSubOrgDto(Position position) {
		if(position == null) return null;
		SubOrgDto dto = new SubOrgDto();
		dto.setOrgId(position.getOrgId());
		dto.setSubOrgId(position.getPositionId());
		dto.setSubOrgCode(position.getPositionCode());
		dto.setParentId(position.getParentId());
		dto.setSubOrgName(position.getPositionName());
		dto.setLevel(position.getLevel());
		dto.setLocationFlag(YesNoFlag.YES.getValue());
		dto.setRemark(position.getFullName());
		dto.setSubOrgType(position.getPositionType());
		dto.setGmtCreate(position.getGmtCreate());
		dto.setGmtModify(position.getGmtModify());
		return dto;
	}

	private List<SubOrgDto> positionListToSubOrgDtoList(List<Position> positionList) {
		List<SubOrgDto> subOrgDtoList = new ArrayList<>();
		if(CollectionUtils.isEmpty(positionList)) return subOrgDtoList;
		for(Position position : positionList) {
			subOrgDtoList.add(this.positionToSubOrgDto(position));
		}
		return subOrgDtoList;
	}

	@Override
	public ServiceResponse<List<SubOrgDto>> getSubOrg(SubOrgDto subOrgDto) {
		logger.debug("###################getSubOrg###################");
		List<Position> positionList = positionManager.findPlatPositions(subOrgDto.getOrgId(), PositionType.POINT.getValue());
		return new ServiceResponse<List<SubOrgDto>>(true, null, positionListToSubOrgDtoList(positionList));
	}

	@Override
	public ServiceResponse<Long> addEquipmentAddressSubOrg(SubOrgDto subOrgDto) {
		logger.debug("###################addEquipmentAddressSubOrg###################");
		FlatPositionAddRequest request = new FlatPositionAddRequest();
		request.setOrgId(subOrgDto.getOrgId());
		request.setPositionName(subOrgDto.getSubOrgName());
		request.setPositionType(PositionType.POINT.getValue());
		Position position = positionManager.addFlatPosition(request);
		return new ServiceResponse<Long>(true, null, position.getPositionId());
	}

	@Override
	public ServiceResponse<Integer> updateSubOrgSelective(SubOrgDto subOrgDto) {
		logger.debug("###################updateSubOrgSelective###################");
		positionManager.updatePosition(subOrgDto.getOrgId(), subOrgDto.getSubOrgId(), subOrgDto.getSubOrgName());
		return new ServiceResponse<Integer>(true ,null, 1);
	}

	@Override
	public ServiceResponse<SubOrgDto> getOneSubOrg(SubOrgDto subOrgDto) {
		logger.debug("###################getOneSubOrg###################");
		Position position = positionManager.getRootPosition(subOrgDto.getOrgId());
		return new ServiceResponse<SubOrgDto>(true, null, positionToSubOrgDto(position));
	}

	@Override
	public ServiceResponse<SubOrgDto> getVisitorSubOrg(Long orgId) {
		logger.debug("###################getVisitorSubOrg###################");
		Group group = groupManager.getVisitorGroup(orgId);
		return new ServiceResponse<SubOrgDto>(true, null, groupToSubOrgDto(group));
	}

	@Override
	public ServiceResponse<List<SubOrgDto>> getSubOrgListByParentId(Long parentId, Long orgId) {
		logger.debug("###################getSubOrgListByParentId###################");
		List<Position> positionList = positionManager.findDirectChildrens(orgId, parentId);
		return new ServiceResponse<List<SubOrgDto>>(true, null, positionListToSubOrgDtoList(positionList));
	}

	@Override
	public ServiceResponse<List<SubOrgDto>> getSubOrgBySubOrgName(String subOrgName, Long orgId, Integer subOrgType) {
		logger.debug("###################getSubOrgBySubOrgName###################");
		List<Position> positionList = positionManager.findByNameAndType(orgId, subOrgType, subOrgName);
		return new ServiceResponse<List<SubOrgDto>>(true, null, positionListToSubOrgDtoList(positionList));
	}

}
