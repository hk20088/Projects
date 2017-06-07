package com.newspace.aps.paynotify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;
import org.apache.log4j.Logger;

import com.newspace.aps.paynotify.param.NotifyContent;
import com.newspace.aps.paynotify.param.NotifyEntity;
import com.newspace.common.utils.JsonUtils;

/**
 * {@link ReceiveFutureCallback.java} 
 * @description:  接收响应的Future  
 * @author Huqili
 * @date 2016年9月22日  修改
 */
public class ReceiveFutureCallback implements FutureCallback<HttpResponse>
{
	private NotifyEntity entity;
	
	String orderNo = null;

	private static final ScheduledExecutorService pool = Executors.newScheduledThreadPool(20);

	private static final Logger logger = Logger.getLogger(ReceiveFutureCallback.class);

	public ReceiveFutureCallback(NotifyEntity entity)
	{
		this.entity = entity;
		
		//拿到订单号，方便日志查询
		NotifyContent content = new NotifyContent();
		String json = entity.getNotifyContent().split("transdata=")[1].split("&sign=")[0];
		content = JsonUtils.fromJson(json, NotifyContent.class);
		orderNo = content.getPayOrderNo();
	}

	@Override
	public void completed(HttpResponse response)
	{
		logger.info(String.format("\r\n【正常接收到NotfiyUrl ：%s 响应， Completed】", entity.getNotifyUrl()));
		System.out.println(String.format("\r\n【正常接收到NotfiyUrl ：%s 响应， Completed】", entity.getNotifyUrl()));
		
		BufferedReader reader = null;
		try
		{
			HttpEntity httpEntity = response.getEntity();
			reader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
			String str = reader.readLine();
			
			logger.info(String.format("\r\n【接收到NotfiyUrl ：%s，订单号为：%s，响应信息：%s】", entity.getNotifyUrl(), orderNo ,str));
			System.out.println(String.format("\r\n【接收到NotfiyUrl ：%s，订单号为：%s，响应信息：%s】", entity.getNotifyUrl(), orderNo ,str));
			
			// 接收到success响应 或 已经发送8次，则不再发送
			if (!"success".equalsIgnoreCase(str) && entity.getSendTime() < 8)
			{
				entity.setSendTime(entity.getSendTime() + 1);
				int delay = entity.getDelayTime();
				pool.schedule(new Runnable()
				{
					@Override
					public void run()
					{
						HttpAsyncConnExecutor.submitNotifyTask(entity);
					}
				}, delay, TimeUnit.SECONDS);
			}
		}
		catch (IOException e)
		{
			logger.error(String.format("【接收到NotifyUrl: %s ，订单：%s 的响应异常：%s】",entity.getNotifyUrl(),orderNo, e));
			entity.setSendTime(entity.getSendTime() + 1);
		}
		finally
		{
			if (reader != null)
				try
				{
					reader.close();
				}
				catch (IOException e)
				{
					logger.error("关闭输入流时出现异常：" + e);
				}
		}
	}

	@Override
	public void failed(Exception e)
	{
		logger.info(String.format("【接收到NotfiyUrl ：%s ，订单号：%s， 响应失败， failed，异常信息为：%s】", entity.getNotifyUrl(),orderNo,e));
		logger.error(String.format("【接收到NotfiyUrl ：%s ，订单号：%s， 响应失败， failed，异常信息为：%s】", entity.getNotifyUrl(),orderNo,e));
		
		System.out.println(String.format("【接收到NotfiyUrl ：%s，订单号：%s，  响应失败， failed，异常信息为：%s】", entity.getNotifyUrl(),orderNo,e));
		// 已经发送8次，则不再发送
		if (entity.getSendTime() >= 8)
			return;
		entity.setSendTime(entity.getSendTime() + 1);  
//		int delay = entity.getDelayTime();  //如果请求失败，则三秒后继续请求。
		pool.schedule(new Runnable()
		{
			@Override
			public void run()
			{
				HttpAsyncConnExecutor.submitNotifyTask(entity);
			}
		}, 3, TimeUnit.SECONDS);
	}

	@Override
	public void cancelled()
	{
		logger.info(String.format("\r\n【接收到NotfiyUrl ：%s 响应取消， cancelled】", entity.getNotifyUrl()));
		System.out.println("cancleled");
	}
}
