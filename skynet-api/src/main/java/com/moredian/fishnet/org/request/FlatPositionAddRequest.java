package com.moredian.fishnet.org.request;

import java.io.Serializable;
import com.moredian.fishnet.org.enums.PositionType;

public class FlatPositionAddRequest implements Serializable {

	private static final long serialVersionUID = -5399003389723733059L;
	
	/** 机构id */
	private Long orgId; // required
	/** 位置类型 [参考{@link PositionType}] */
	private Integer positionType; // required
	/** 位置名　*/
	private String positionName; // required
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Integer getPositionType() {
		return positionType;
	}
	public void setPositionType(Integer positionType) {
		this.positionType = positionType;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

}
