package com.newspace.aps.paynotify.param;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.protocol.HTTP;

/**
 * {@link NotifyEntity.java} 
 * @description:  通知CP实体类，包括通知地址、通知内容、发送次数等信息。
 * @author Huqili
 * @date 2015年6月26日
 */
public class NotifyEntity
{
	/**
	 * 通知地址
	 */
	private String notifyUrl;

	/**
	 * 通知内容
	 */
	private String notifyContent;

	/**
	 * HttpPost对象
	 */
	private HttpPost post;

	/**
	 * 发送次数，初始值为1
	 */
	private int sendTime = 1;

	/**
	 * 用于存放不同发送次数的间隔时间
	 * key: 本次的发送次数；
	 * value：发送间隔时间 单位为 秒
	 */
	private static final Map<Integer, Integer> delayMap = new HashMap<Integer, Integer>();

	static
	{
		// 第一次立即发送
		delayMap.put(2, 2 * 60);// 2min
		delayMap.put(3, 5 * 60);// 5min
		delayMap.put(4, 10 * 60);// 10min
		delayMap.put(5, 60 * 60); // 1hour
		delayMap.put(6, 2 * 60 * 60); // 2hour
		delayMap.put(7, 6 * 60 * 60); // 6hour
		delayMap.put(8, 15 * 60 * 60); // 15hour
	}

	public NotifyEntity(String url, String content)
	{
		this.notifyUrl = url;
		this.notifyContent = content;
		HttpPost post = new HttpPost(notifyUrl);
		//设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000*30).setConnectTimeout(1000*30).build();
		
		post.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;");
		post.setConfig(requestConfig);
		post.setEntity(new ByteArrayEntity(notifyContent.getBytes()));
		
		this.post = post;
	}

	public int getDelayTime()
	{
		return delayMap.get(sendTime);
	}

	public HttpPost getHttpPost()
	{
		return post;
	}

	public String getNotifyUrl()
	{
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl)
	{
		this.notifyUrl = notifyUrl;
	}

	public String getNotifyContent()
	{
		return notifyContent;
	}

	public void setNotifyContent(String notifyContent)
	{
		this.notifyContent = notifyContent;
	}

	public int getSendTime()
	{
		return sendTime;
	}

	public void setSendTime(int sendTime)
	{
		this.sendTime = sendTime;
	}
}