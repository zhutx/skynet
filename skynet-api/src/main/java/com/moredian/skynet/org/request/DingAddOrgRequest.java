package com.moredian.skynet.org.request;

import java.io.Serializable;

public class DingAddOrgRequest implements Serializable {

	private static final long serialVersionUID = 7827730806088424557L;
	
	//名称
    private String orgName;
    //省ID
    private Integer provinceId;
    //市ID
    private Integer cityId;
    //区ID
    private Integer districtId;
    //详细地址
    private String detailedAddress;
    //地理坐标LOG
    private Double lon;
    //地理坐标LAT
    private Double lat;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}
