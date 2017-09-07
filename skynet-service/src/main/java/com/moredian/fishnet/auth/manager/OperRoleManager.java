package com.moredian.fishnet.auth.manager;

import java.util.List;

public interface OperRoleManager {
	
	List<Long> findRoleIdByOperId(Long operId);

}
