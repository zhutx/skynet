package com.moredian.skynet.auth.manager;

import java.util.List;

public interface RolePermManager {
	
	List<Long> findPermIdByRoleId(Long roleId);

}
