package com.moredian.skynet.device.model;

import java.io.Serializable;

public class ServerDeviceExtendsInfo implements Serializable {

	private static final long serialVersionUID = -6467025741000783685L;
	
	private String c_host;
	private int c_port = 0;
	private String c_outer_host;
	private int c_outer_port = 0;
	private String c_tp_host = "127.0.0.1";
	private int c_tp_port = 0;
	private int c_rd_maxorgnum = Integer.MAX_VALUE;
	private int c_rd_maxpersonnum = Integer.MAX_VALUE;
	
	public String getC_host() {
		return c_host;
	}
	public void setC_host(String c_host) {
		this.c_host = c_host;
	}
	public int getC_port() {
		return c_port;
	}
	public void setC_port(int c_port) {
		this.c_port = c_port;
	}
	public String getC_outer_host() {
		return c_outer_host;
	}
	public void setC_outer_host(String c_outer_host) {
		this.c_outer_host = c_outer_host;
	}
	public int getC_outer_port() {
		return c_outer_port;
	}
	public void setC_outer_port(int c_outer_port) {
		this.c_outer_port = c_outer_port;
	}
	public String getC_tp_host() {
		return c_tp_host;
	}
	public void setC_tp_host(String c_tp_host) {
		this.c_tp_host = c_tp_host;
	}
	public int getC_tp_port() {
		return c_tp_port;
	}
	public void setC_tp_port(int c_tp_port) {
		this.c_tp_port = c_tp_port;
	}
	public int getC_rd_maxorgnum() {
		return c_rd_maxorgnum;
	}
	public void setC_rd_maxorgnum(int c_rd_maxorgnum) {
		this.c_rd_maxorgnum = c_rd_maxorgnum;
	}
	public int getC_rd_maxpersonnum() {
		return c_rd_maxpersonnum;
	}
	public void setC_rd_maxpersonnum(int c_rd_maxpersonnum) {
		this.c_rd_maxpersonnum = c_rd_maxpersonnum;
	}
	
}
