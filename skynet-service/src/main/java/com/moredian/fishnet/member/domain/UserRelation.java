package com.moredian.fishnet.member.domain;

import java.io.Serializable;
import java.util.Date;

public class UserRelation implements Serializable {

	private static final long serialVersionUID = -4353484061143906635L;
	
	private Long userRelationId; //主键id
    private Long userId; //机构用户id
    private Integer relation; //关系
    private Integer relationStatus;
    private Long orgId; //机构id
    private Long subOrgId; //子机构Id
    private Date gmtCreate; // 创建时间
    private Date gmtModify; //修改时间
    
	public Long getUserRelationId() {
		return userRelationId;
	}
	public void setUserRelationId(Long userRelationId) {
		this.userRelationId = userRelationId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getRelation() {
		return relation;
	}
	public void setRelation(Integer relation) {
		this.relation = relation;
	}
	public Integer getRelationStatus() {
		return relationStatus;
	}
	public void setRelationStatus(Integer relationStatus) {
		this.relationStatus = relationStatus;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getSubOrgId() {
		return subOrgId;
	}
	public void setSubOrgId(Long subOrgId) {
		this.subOrgId = subOrgId;
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

}
