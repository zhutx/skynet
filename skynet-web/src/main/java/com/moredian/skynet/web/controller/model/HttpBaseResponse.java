package com.moredian.skynet.web.controller.model;

import java.util.UUID;

import com.moredian.bee.common.rpc.ServiceResponse;
import com.moredian.bee.common.web.BaseResponse;

public class HttpBaseResponse<T> extends BaseResponse<T>{

	private static final long serialVersionUID = 2890448299017522464L;
	
	private String requestId;

	public HttpBaseResponse() {
		super();
		requestId = UUID.randomUUID().toString();
	}

	public HttpBaseResponse(T data) {
		super(data);
		requestId = UUID.randomUUID().toString();
	}

	public HttpBaseResponse(ServiceResponse<T> serviceResponse) {
		super(serviceResponse);
		requestId = UUID.randomUUID().toString();
	}

	public HttpBaseResponse(String resultCode, String message) {
		super(resultCode,message);
		requestId = UUID.randomUUID().toString();
	}

	public HttpBaseResponse(String resultCode, String message, T data) {
		super(resultCode,message,data);
		requestId = UUID.randomUUID().toString();
	}
 
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

}