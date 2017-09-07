package com.moredian.fishnet.device.domain;

import java.util.Date;

import java.io.Serializable;

public class AlarmQueryCondition implements Serializable {
	
	private static final long serialVersionUID = 5933210213130365233L;
	
	private String serialNumber;
	private Integer mainType;
	private Integer subType;
    private Integer severity;
	private Integer deal;    
    private Date startDate;
    private Date endDate;
    private Integer startRow;
    private Integer pageSize;
    
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public Integer getMainType() {
		return mainType;
	}
	public void setMainType(Integer mainType) {
		this.mainType = mainType;
	}
	public Integer getSubType() {
		return subType;
	}
	public void setSubType(Integer subType) {
		this.subType = subType;
	}
	public Integer getSeverity() {
		return severity;
	}
	public void setSeverity(Integer severity) {
		this.severity = severity;
	}
	public Integer getDeal() {
		return deal;
	}
	public void setDeal(Integer deal) {
		this.deal = deal;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getStartRow() {
		return startRow;
	}
	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
