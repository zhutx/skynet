package com.moredian.fishnet.org.request;

import java.io.Serializable;

import com.moredian.fishnet.org.enums.PersonType;


public class GroupPersonRemoveRequest implements Serializable {

	private static final long serialVersionUID = 215060511912725804L;
	
	/** 机构id */
	private Long orgId; //required
	/** 组id */
	private Long groupId; //required
	/** 人员id */
	private Long personId; //required
	/** 人员类型 {@link PersonType} */
	private Integer personType; //required
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public Integer getPersonType() {
		return personType;
	}
	public void setPersonType(Integer personType) {
		this.personType = personType;
	}

}
