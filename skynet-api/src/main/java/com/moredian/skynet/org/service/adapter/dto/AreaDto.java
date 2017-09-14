package com.moredian.skynet.org.service.adapter.dto;

import java.io.Serializable;
import java.util.Date;

public class AreaDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7489563798983248407L;

	//主键ID 
	private Long areaId;
	
	//区域行政编码 
	private Integer areaCode;
	
	//地区名 
	private String areaName;
	
	//父级区域行政编码，0表示省级 
	private Integer parentCode;
	
	// 
	private Date gmtCreate;
	
	// 
	private Date gmtModify;
	
	//邮编 
	private String zipCode;
	
	//电话区号 
	private String areaNumber;
	
	// 
	private String operator;
	

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	
	public Integer getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(Integer areaCode) {
		this.areaCode = areaCode;
	}
	
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	public Integer getParentCode() {
		return parentCode;
	}

	public void setParentCode(Integer parentCode) {
		this.parentCode = parentCode;
	}
	
	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	
	public Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}
	
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	public String getAreaNumber() {
		return areaNumber;
	}

	public void setAreaNumber(String areaNumber) {
		this.areaNumber = areaNumber;
	}
	
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
}
