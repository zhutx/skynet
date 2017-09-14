package com.moredian.skynet.web.controller.model;

public class PageData {
	
	private Integer pageNo = 1;
	private Integer totalCount = 0;
	
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

}
