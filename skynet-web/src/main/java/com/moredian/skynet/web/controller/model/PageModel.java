package com.moredian.skynet.web.controller.model;

public class PageModel {
	
	private Integer pageSize = 20;
	private Integer pageNo = 1;
	
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

}
