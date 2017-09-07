package com.moredian.fishnet.device.request;

import java.util.Date;

import java.io.Serializable;

public class AlarmUpdateRequest  implements Serializable {

	private static final long serialVersionUID = 5388143676797714256L;
	private Long id;
	private String serialNumber;
	private String dealOpinion;
	private Integer deal;    
	private Date updatedAt;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
 
	public Integer getDeal() {
		return deal;
	}
	public void setDeal(Integer deal) {
		this.deal = deal;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
 
	public String getDealOpinion() {
		return dealOpinion;
	}
	public void setDealOpinion(String dealOpinion) {
		this.dealOpinion = dealOpinion;
	}
 
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
