package com.wu.crawler.core.util;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Random;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpClientUtil {
	private static Logger logger = Logger.getLogger(HttpClientUtil.class);
	private static CloseableHttpClient httpClient = null;
	private static RequestConfig requestConfig = null;
	
	static {
		httpClient = HttpClients.createDefault();
		requestConfig = RequestConfig.custom().setSocketTimeout(Constants.TIMEOUT)
				.setConnectTimeout(Constants.TIMEOUT).setCookieSpec(CookieSpecs.STANDARD)
				.setConnectionRequestTimeout(Constants.TIMEOUT).build();
	}
	/**
	 * 发送请求，获得响应
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public  static CloseableHttpResponse getResponse(String url) {
		HttpGet request = new HttpGet(url);
		request.setConfig(requestConfig);
		request.setHeader("User-Agent",
				Constants.userAgentArray[new Random().nextInt(Constants.userAgentArray.length)]);
		CloseableHttpResponse response = null;
		try {
				response = httpClient.execute(request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("网络连接失败");
			logger.error(e);
		}
		return response;
	}
	/**
	 * 得到页面内容
	 * @param url
	 * @param encoding
	 * @return
	 */
	public  static String getContent(String url,String encoding) {
		String content = null;
		try {
			CloseableHttpResponse response = getResponse(url);
	     	content = EntityUtils.toString(response.getEntity(), encoding);
		}catch(Exception e) {
			logger.error("解析页面失败!");
			logger.error(e);
		}
		return content;
	}
	
}
