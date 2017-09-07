package com.moredian.fishnet.member.manager.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.moredian.bee.common.utils.JsonUtils;
import com.moredian.bee.rmq.EventBus;
import com.moredian.cloudeye.core.api.rs.MatchLog;
import com.moredian.fishnet.member.manager.RecognizeManager;
import com.moredian.fishnet.org.enums.YesNoFlag;

@Service
public class RecognizeManagerImpl implements RecognizeManager {
	
	@Value("${rmq.switch}")
	private int rmqSwitch;

	@Override
	public void sendMatchLogJson(String matchLogJson) {
		if(YesNoFlag.YES.getValue() == rmqSwitch) {
			EventBus.publish(JsonUtils.fromJson(MatchLog.class, matchLogJson));
		}
	}

}
