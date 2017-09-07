package com.moredian.fishnet.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.moredian.bee.tube.annotation.SI;
import com.moredian.fishnet.member.manager.RecognizeManager;
import com.moredian.fishnet.member.service.RecognizeService;

@SI
public class RecognizeServiceImpl implements RecognizeService {
	
	@Autowired
	private RecognizeManager recognizeManager;

	@Override
	public void sendMatchLogJson(String matchLogJson) {
		recognizeManager.sendMatchLogJson(matchLogJson);
	}

}
