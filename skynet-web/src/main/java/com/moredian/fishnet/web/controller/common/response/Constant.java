package com.moredian.fishnet.web.controller.common.response;

import java.util.HashMap;
import java.util.Map;

public class Constant {
	
	private String constantName;
	private String constantDesc;
	private Map<String, String> constantData = new HashMap<>();
	
	public Constant(String constantName, String constantDesc) {
		this.constantName = constantName;
		this.constantDesc = constantDesc;
	}
	
	public String getConstantName() {
		return constantName;
	}
	public void setConstantName(String constantName) {
		this.constantName = constantName;
	}
	public String getConstantDesc() {
		return constantDesc;
	}
	public void setConstantDesc(String constantDesc) {
		this.constantDesc = constantDesc;
	}
	public Map<String, String> getConstantData() {
		return constantData;
	}
	public void setConstantData(Map<String, String> constantData) {
		this.constantData = constantData;
	}

}
