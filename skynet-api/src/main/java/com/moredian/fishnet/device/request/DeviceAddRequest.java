package com.moredian.fishnet.device.request;

import java.io.Serializable;
import com.moredian.fishnet.device.enums.DeviceType;

public class DeviceAddRequest implements Serializable {

	private static final long serialVersionUID = -6866297619442270357L;
	
	/** 机构id */
	private Long orgId; //required
	/** 设备类型  {@link DeviceType}*/
	private Integer deviceType; //required
	/** 设备sn */
	private String deviceSn; //required
	/** 设备名称 */
	private String deviceName; //optional
	/** 位置id */
	private Long positionId; //optional
	/** 位置信息 */
	private String position; //optional
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public Long getPositionId() {
		return positionId;
	}
	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}
	public Integer getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceSn() {
		return deviceSn;
	}
	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	
}
