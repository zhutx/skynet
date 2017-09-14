package com.moredian.skynet.device.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("deploy")
public class DeployProperties {
	
	private String blackGroupCode;
	
	private int threshold;

	public String getBlackGroupCode() {
		return blackGroupCode;
	}

	public void setBlackGroupCode(String blackGroupCode) {
		this.blackGroupCode = blackGroupCode;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
	
}
