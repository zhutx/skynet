/**
 * 
 */
package com.moredian.skynet.device.utils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * @author erxiao 2016年2月24日
 */
public class HttpInvoker {

	private static Logger logger = LoggerFactory.getLogger(HttpInvoker.class);

	private static CloseableHttpClient httpclient;

	private static int MAX_HTTP_CONNECTION = 100;
	private static int CONNECTION_TIME_OUT = 5000;  //设置连接超时时间，单位毫秒。
	private static int CONNECTION_REQUEST_TIMEOUT = 1000;//设置从connect Manager获取Connection 超时时间，单位毫秒。
	private static int CONNECTION_SOCKET_TIMEOUT = 5000;//请求获取数据的超时时间，单位毫秒。
	
	private static 	RequestConfig requestConfig ;
	
	static {
		PoolingHttpClientConnectionManager poolingmgr = new PoolingHttpClientConnectionManager();
		poolingmgr.setDefaultMaxPerRoute(MAX_HTTP_CONNECTION);
		poolingmgr.setMaxTotal(2 * MAX_HTTP_CONNECTION);

		httpclient = HttpClientBuilder.create().setConnectionManager(poolingmgr).build();

		requestConfig = RequestConfig.custom()  
		        .setConnectTimeout(CONNECTION_TIME_OUT).setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)  
		        .setSocketTimeout(CONNECTION_SOCKET_TIMEOUT).build(); 
	}

	public static HttpInvokerResponse invokerPostForm(String invokerUrl, Map<String, String> stringBody, Map<String, byte[]> byteBody,Integer socketTimeout,Integer connectionTimeout) {
		HttpPost httpost = new HttpPost(invokerUrl);
		try {
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			if (stringBody != null) {
				for (String key : stringBody.keySet()) {
					builder.addTextBody(key, stringBody.get(key));
				}
			}
			if (byteBody != null) {
				for (String key : byteBody.keySet()) {
					builder.addBinaryBody(key, byteBody.get(key));
					builder.addPart(key, new ByteArrayBody(byteBody.get(key), key));
				}
			}
			HttpEntity entity = builder.build();
			httpost.setEntity(entity);
		} catch (Exception e) {
			logger.error("创建post请求参数异常.", e);
			return new HttpInvokerResponse(400);
		}
		return invoker(httpost, null, null,socketTimeout,connectionTimeout);
	}
	
	public static HttpInvokerResponse invokerPostForm(String invokerUrl, Map<String, String> stringBody, Map<String, byte[]> byteBody) {
		return invokerPostForm(invokerUrl,stringBody,byteBody,null,null);
	}

	/**
	 * 如果调用异常，{@link HttpInvokerResponse#getResponseCode()} = 0
	 * 
	 * @param invokerUrl
	 * @param requestMethod
	 * @param headerMap
	 * @param body
	 * @return
	 */
	public static HttpInvokerResponse invokerJsonPost(String invokerUrl, Map<String, String> headerMap, Map<String, String> parametrMap, Object body) {
		HttpPost httpost = new HttpPost(invokerUrl);
		try {
			Gson gson = new Gson();
			String bodyJson = gson.toJson(body);
			StringEntity entity = new StringEntity(bodyJson, "UTF-8");
			entity.setContentType("application/json");
			entity.setContentEncoding("UTF-8");
			httpost.setEntity(entity);
		} catch (Exception e) {
			logger.error("创建post请求参数异常.", e);
			return new HttpInvokerResponse(400);
		}
		httpost.addHeader("Content-Type", "application/json;charset=UTF-8");
		return invoker(httpost, headerMap, parametrMap,null,null);
	}
	
	public static HttpInvokerResponse invokerPost(String invokerUrl, Map<String, String> headerMap, Map<String, String> parametrMap, String body) {
		return invokerPost(invokerUrl,headerMap,parametrMap, body,null,null);
	}
	
	public static HttpInvokerResponse invokerPost(String invokerUrl, Map<String, String> headerMap, Map<String, String> parametrMap, String body,Integer socketTimeout,Integer connectionTimeout) {
		HttpPost httpost = new HttpPost(invokerUrl);
		try {
			StringEntity entity = new StringEntity(body);
			httpost.setEntity(entity);
		} catch (Exception e) {
			logger.error("创建post请求参数异常.", e);
			return new HttpInvokerResponse(400);
		}
		return invoker(httpost, headerMap, parametrMap,socketTimeout,connectionTimeout);
	}
	
	public static HttpInvokerResponse invokerPost(String invokerUrl, Map<String, String> headerMap, Map<String, String> parametrMap, File body) {
		return invokerPost(invokerUrl,headerMap,parametrMap, body,null,null);
	}
	
	public static HttpInvokerResponse invokerPost(String invokerUrl, Map<String, String> headerMap, Map<String, String> parametrMap, File body,Integer socketTimeout,Integer connectionTimeout) {
		HttpPost httpost = new HttpPost(invokerUrl);
		try {
			FileEntity entity = new FileEntity(body);
			httpost.setEntity(entity);
		} catch (Exception e) {
			logger.error("创建post请求参数异常.", e);
			return new HttpInvokerResponse(400);
		}
		return invoker(httpost, headerMap, parametrMap,socketTimeout,connectionTimeout);
	}
	
	
	public static HttpInvokerResponse invokerPost(String invokerUrl, Map<String, String> headerMap, Map<String, String> parametrMap, byte[] body) {
		return invokerPost(invokerUrl,headerMap,parametrMap, body,null,null);
	}
	
	public static HttpInvokerResponse invokerPost(String invokerUrl, Map<String, String> headerMap, Map<String, String> parametrMap, byte[] body,Integer socketTimeout,Integer connectionTimeout) {
		HttpPost httpost = new HttpPost(invokerUrl);
		try {
			AbstractHttpEntity entity = new ByteArrayEntity(body);
			httpost.setEntity(entity);
		} catch (Exception e) {
			logger.error("创建post请求参数异常.", e);
			return new HttpInvokerResponse(400);
		}
		return invoker(httpost, headerMap, parametrMap,socketTimeout,connectionTimeout);
	}

	public static HttpInvokerResponse invokerGet(String invokerUrl, Map<String, String> headerMap, Map<String, String> parametrMap) {
		return invokerDelete(invokerUrl,headerMap,parametrMap,null,null);
	}
	
	public static HttpInvokerResponse invokerGet(String invokerUrl, Map<String, String> headerMap, Map<String, String> parametrMap,Integer socketTimeout,Integer connectionTimeout) {
		HttpGet httpGet = new HttpGet(invokerUrl);
		return invoker(httpGet, headerMap, parametrMap,socketTimeout,connectionTimeout);
	}

	public static HttpInvokerResponse invokerDelete(String invokerUrl, Map<String, String> headerMap, Map<String, String> parametrMap) {
		return invokerDelete(invokerUrl,headerMap,parametrMap,null,null);
	}
	
	public static HttpInvokerResponse invokerDelete(String invokerUrl, Map<String, String> headerMap, Map<String, String> parametrMap,Integer socketTimeout,Integer connectionTimeout) {
		HttpDelete httpDelete = new HttpDelete(invokerUrl);
		return invoker(httpDelete, headerMap, parametrMap,socketTimeout,connectionTimeout);
	}
	

	private static HttpInvokerResponse invoker(final HttpRequestBase request, Map<String, String> headerMap, Map<String, String> parametrMap,Integer socketTimeout,Integer connectionTimeout) {
		HttpInvokerResponse result = new HttpInvokerResponse();
		CloseableHttpResponse response = null;
		try {
			if (headerMap != null) {
				for (String key : headerMap.keySet()) {
					request.setHeader(key, headerMap.get(key));
				}
			}
			if (parametrMap != null) {
				for (String key : parametrMap.keySet()) {
					request.setHeader(key, parametrMap.get(key));
				}
			}
			request.setHeader("Cache-Control", "no-cache");
			if((socketTimeout==null) || (connectionTimeout==null)){				 
				request.setConfig(RequestConfig.custom()
						.setSocketTimeout(socketTimeout==null?CONNECTION_SOCKET_TIMEOUT:socketTimeout)
						.setConnectTimeout(connectionTimeout==null?CONNECTION_TIME_OUT:connectionTimeout).build());
			}else{
				request.setConfig(requestConfig);
			}
						
			response = httpclient.execute(request);
			HttpEntity responseEntity = response.getEntity();
			result.setBody(EntityUtils.toString(responseEntity, "UTF-8"));
			result.setResponseCode(response.getStatusLine().getStatusCode());
		} catch (Exception e) {
			logger.error("", e);
			result.setResponseCode(0);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
				}
			}
		}
		return result;
	}
	 
}
