package com.moredian.skynet.member.manager;

import java.util.List;

public interface GroupPersonSizeManager {
	
	boolean resetPersonSize(Long orgId, Long groupId, Integer personType);
	
	boolean batchResetPersonSize(Long orgId, List<Long> groupIdList, Integer personType);
	
	boolean resetPersonSizeForAllMemberGroup(Long orgId);

}
