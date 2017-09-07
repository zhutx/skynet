package com.moredian.fishnet.org.service.adapter.dto;

import java.io.Serializable;
import java.util.Date;

public class SubOrgDto implements Serializable {

	private static final long serialVersionUID = -5186208893603080649L;

	//子机构id
	private Long subOrgId;

	//子机构码【zipcode 6】【机构类型 2 】【主机构编号6】【子3】【子3】【子3】【子3】【子2】【子2】【子2】【子2】【子2】
	private String subOrgCode;

	//主机构ID 
	private Long orgId;
	
	//父级ID 
	private Long parentId;
	
	//名称 
	private String subOrgName;
	
	//等级 
	private Integer level;
	
	//拨号编码 
	private String dialCode;
	
	//位置标记
	private Integer locationFlag;
	
	//备注 
	private String remark;
	
	//子机构类型:1幢,2单元,3室,99其他
	private Integer subOrgType;
	
	//1-物管中心,2-保安室,3-保洁室
	private Integer roomType;
	
	//创建时间 
	private Date gmtCreate;
	
	//更新时间 
	private Date gmtModify;

	public String getSubOrgCode() {
		return subOrgCode;
	}

	public void setSubOrgCode(String subOrgCode) {
		this.subOrgCode = subOrgCode;
	}

	public Long getSubOrgId() {
		return subOrgId;
	}

	public void setSubOrgId(Long subOrgId) {
		this.subOrgId = subOrgId;
	}
	
	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public String getSubOrgName() {
		return subOrgName;
	}

	public void setSubOrgName(String subOrgName) {
		this.subOrgName = subOrgName;
	}
	
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	public Integer getLocationFlag() {
		return locationFlag;
	}

	public void setLocationFlag(Integer locationFlag) {
		this.locationFlag = locationFlag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getDialCode() {
		return dialCode;
	}

	public void setDialCode(String dialCode) {
		this.dialCode = dialCode;
	}

	public Integer getSubOrgType() {
		return subOrgType;
	}

	public void setSubOrgType(Integer subOrgType) {
		this.subOrgType = subOrgType;
	}

	public Integer getRoomType() {
		return roomType;
	}

	public void setRoomType(Integer roomType) {
		this.roomType = roomType;
	}
	
}
