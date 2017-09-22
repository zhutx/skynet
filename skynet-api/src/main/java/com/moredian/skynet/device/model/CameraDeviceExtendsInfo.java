package com.moredian.skynet.device.model;

import java.io.Serializable;

public class CameraDeviceExtendsInfo implements Serializable {

	private static final long serialVersionUID = -2471446463901281116L;
	
	private Integer provider_type;
	private String camera_nvr;
	private String camera_ip;
	private Integer camera_port;
	private String camera_username;
	private String camera_password;
	private String camera_stream;
	private String camera_resolution;
	
	public Integer getProvider_type() {
		return provider_type;
	}
	public void setProvider_type(Integer provider_type) {
		this.provider_type = provider_type;
	}
	public String getCamera_nvr() {
		return camera_nvr;
	}
	public void setCamera_nvr(String camera_nvr) {
		this.camera_nvr = camera_nvr;
	}
	public String getCamera_ip() {
		return camera_ip;
	}
	public void setCamera_ip(String camera_ip) {
		this.camera_ip = camera_ip;
	}
	public Integer getCamera_port() {
		return camera_port;
	}
	public void setCamera_port(Integer camera_port) {
		this.camera_port = camera_port;
	}
	public String getCamera_username() {
		return camera_username;
	}
	public void setCamera_username(String camera_username) {
		this.camera_username = camera_username;
	}
	public String getCamera_password() {
		return camera_password;
	}
	public void setCamera_password(String camera_password) {
		this.camera_password = camera_password;
	}
	public String getCamera_stream() {
		return camera_stream;
	}
	public void setCamera_stream(String camera_stream) {
		this.camera_stream = camera_stream;
	}
	public String getCamera_resolution() {
		return camera_resolution;
	}
	public void setCamera_resolution(String camera_resolution) {
		this.camera_resolution = camera_resolution;
	}

}
