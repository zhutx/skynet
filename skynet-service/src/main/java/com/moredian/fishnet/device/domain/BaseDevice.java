package com.moredian.fishnet.device.domain;

import java.io.Serializable;
import java.util.Date;

public class BaseDevice implements Serializable {
	private static final long serialVersionUID = -2124696882825388123L;
    private Long id;
	 private Date createdAt;
	 private Date updatedAt;
    private Long ownerId;
	 private Date activeTime;
	 private String batchNo;
    private String communicator;
	 private String communicatorMaster;
	 private String connectivity;
    private int countryCode;
	 private String cpuUsage;
	 private String deviceModel;
    private String deviceType;
	 private String displayVersion;
	 private String domainName;
    private String extendedInf;
	 private String gateway;
	 private String hardwareVersion;
    private String ipAddress;
	 private int ipType;
	 private int isSimulate;
    private String location;
	 private String macAddress;
	 private String managedStatus;
    private String memoryUsage;
	 private String netmask;
	 private String os;
	 private String osVersion;
	 private String previousCommunicator;
	private String provider;
	 private int reconnectReson;	 
	 private int regionCode;
	 private String securityKey;
	 private String serialNumber;
	 private String softwareVersion;
	private Long upTime;
	private String cpuId;
	
	
	public String getCpuId() {
		return cpuId;
	}
	public void setCpuId(String cpuId) {
		this.cpuId = cpuId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
	public Date getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getCommunicator() {
		return communicator;
	}
	public void setCommunicator(String communicator) {
		this.communicator = communicator;
	}
	public String getCommunicatorMaster() {
		return communicatorMaster;
	}
	public void setCommunicatorMaster(String communicatorMaster) {
		this.communicatorMaster = communicatorMaster;
	}
	public String getConnectivity() {
		return connectivity;
	}
	public void setConnectivity(String connectivity) {
		this.connectivity = connectivity;
	}
	public int getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(int countryCode) {
		this.countryCode = countryCode;
	}
	public String getCpuUsage() {
		return cpuUsage;
	}
	public void setCpuUsage(String cpuUsage) {
		this.cpuUsage = cpuUsage;
	}
	public String getDeviceModel() {
		return deviceModel;
	}
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getDisplayVersion() {
		return displayVersion;
	}
	public void setDisplayVersion(String displayVersion) {
		this.displayVersion = displayVersion;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getExtendedInf() {
		return extendedInf;
	}
	public void setExtendedInf(String extendedInf) {
		this.extendedInf = extendedInf;
	}
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	public String getHardwareVersion() {
		return hardwareVersion;
	}
	public void setHardwareVersion(String hardwareVersion) {
		this.hardwareVersion = hardwareVersion;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public int getIpType() {
		return ipType;
	}
	public void setIpType(int ipType) {
		this.ipType = ipType;
	}
	public int getIsSimulate() {
		return isSimulate;
	}
	public void setIsSimulate(int isSimulate) {
		this.isSimulate = isSimulate;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public String getManagedStatus() {
		return managedStatus;
	}
	public void setManagedStatus(String managedStatus) {
		this.managedStatus = managedStatus;
	}
	public String getMemoryUsage() {
		return memoryUsage;
	}
	public void setMemoryUsage(String memoryUsage) {
		this.memoryUsage = memoryUsage;
	}
	public String getNetmask() {
		return netmask;
	}
	public void setNetmask(String netmask) {
		this.netmask = netmask;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	public String getPreviousCommunicator() {
		return previousCommunicator;
	}
	public void setPreviousCommunicator(String previousCommunicator) {
		this.previousCommunicator = previousCommunicator;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public int getReconnectReson() {
		return reconnectReson;
	}
	public void setReconnectReson(int reconnectReson) {
		this.reconnectReson = reconnectReson;
	}
	public int getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(int regionCode) {
		this.regionCode = regionCode;
	}
	public String getSecurityKey() {
		return securityKey;
	}
	public void setSecurityKey(String securityKey) {
		this.securityKey = securityKey;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getSoftwareVersion() {
		return softwareVersion;
	}
	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}
	public Long getUpTime() {
		return upTime;
	}
	public void setUpTime(Long upTime) {
		this.upTime = upTime;
	}
}
