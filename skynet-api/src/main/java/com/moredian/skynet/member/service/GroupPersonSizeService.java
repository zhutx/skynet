package com.moredian.skynet.member.service;

import java.util.List;

import com.moredian.bee.common.rpc.ServiceResponse;

public interface GroupPersonSizeService {
	
	/**
	 * 为某群组重置成员数
	 * @param orgId
	 * @param groupId
	 * @return
	 */
	ServiceResponse<Boolean> resetPersonSize(Long orgId, Long groupId, Integer personType);
	
	/**
	 * 批量重置某些群组的成员数
	 * @param orgId
	 * @param groupIdList
	 * @return
	 */
	ServiceResponse<Boolean> batchResetPersonSize(Long orgId, List<Long> groupIdList, Integer personType);
	
	/**
	 * 批量重置机构所有成员群组的成员数
	 * @param orgId
	 * @return
	 */
	ServiceResponse<Boolean> resetPersonSizeForAllMemberGroup(Long orgId);

}
