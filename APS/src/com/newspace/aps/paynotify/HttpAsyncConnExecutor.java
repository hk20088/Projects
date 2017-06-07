package com.newspace.aps.paynotify;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.http.impl.nio.client.DefaultHttpAsyncClient;
import org.apache.http.impl.nio.conn.PoolingClientAsyncConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.nio.conn.ClientAsyncConnectionManager;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.log4j.Logger;

import com.newspace.aps.paynotify.param.NotifyContent;
import com.newspace.aps.paynotify.param.NotifyEntity;

/**
 * {@link HttpAsyncConnExecutor.java} 
 * @description:  异步通知执行器
 * @author:  huqili
 * @date 2015年6月26日
 */
public class HttpAsyncConnExecutor
{
	private static volatile BlockingQueue<NotifyEntity> taskQueue = new LinkedBlockingQueue<NotifyEntity>();

	private static final Logger logger = Logger.getLogger(HttpAsyncConnExecutor.class);

	private static final ScheduledExecutorService pool = Executors.newScheduledThreadPool(3);

	/**
	 *  提交通知任务
	 */
	public static void submitNotifyTask(final NotifyEntity entity)
	{
		logger.info(String.format("异步通知队列大小为：%s", taskQueue.size()));
		if (!taskQueue.offer(entity)) // 添加失败，则三秒后尝试再添加一次
		{
			logger.info(String.format("【提交通知任务失败，3秒后再重新提交一次，NotifyUrl：%s,】", entity.getNotifyUrl()));
			pool.schedule(new Runnable()
			{
				@Override
				public void run()
				{
					if (taskQueue.offer(entity))
						logger.info(String.format("\r\n【提交通知任务成功，第%s次通知，NotifyUrl：%s,通知内容：%s 】",entity.getSendTime(), entity.getNotifyUrl(),entity.getNotifyContent()));
					else
						logger.info(String.format("【再次提交任务失败，将忽略此任务，NotifyUrl：%s,】", entity.getNotifyUrl()));
				}
			}, 3, TimeUnit.SECONDS);
		}
		else
		{
			logger.info(String.format("【提交通知任务成功，NotifyUrl：%s, 第%s次通知】", entity.getNotifyUrl(), entity.getSendTime()));
			System.out.println("此时队列大小为："+taskQueue.size());
		}
	}

	/**
	 * 启动执行器开始执行通知任务
	 */
	public static void execute()
	{
		HttpAsyncClient httpclient = null;
		try
		{
			ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor();
			ClientAsyncConnectionManager manager = new PoolingClientAsyncConnectionManager(ioReactor);
			httpclient = new DefaultHttpAsyncClient(manager);
			//HttpClient 4.3 后此设置超时时间的方法已过时 －－>最新设置方法见NotifyEntity类NotifyEntity方法
//			httpclient.getParams().setParameter("http.connection.timeout", 5000);
			httpclient.start();
		}
		catch (IOReactorException e)
		{
			logger.error("初始化HttpAsyncClient对象失败，异步通知模块启动失败！", e);
			return;
		}
		logger.info("异步通知模块启动成功！");
		System.out.println("异步通知模块启动成功！");
		while (true)
		{
			NotifyEntity entity = null;
			try
			{
				entity = taskQueue.take();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
				continue;
			}
			if (entity != null)
			{
				logger.info(String.format("\r\n【正在执行通知任务，NotifyUrl：%s, 本次为第%s次通知】", entity.getNotifyUrl(), entity.getSendTime()));
				try
				{
					httpclient.execute(entity.getHttpPost(), new ReceiveFutureCallback(entity));
				}
				catch (Exception e)
				{
					logger.error(String.format("\r\n【通知发生错误，NotifyUrl：%s】", entity.getNotifyUrl()), e);
				}
			}
		}
	}

	public static void main(String[] args) {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				HttpAsyncConnExecutor.execute();
				
			}
		}).start();
		
		//通知CP
		NotifyContent content = new NotifyContent();
		content.setExOrderNo("1011083992214966");
		content.setPayOrderNo("10012017060578281113");
		content.setAppId("20141106170335964137");
		content.setAmount(500);
		content.setPayType("WOSAI_WECHAT");
		content.setTransTime("2017-06-05 14:30:37");
		content.setCounts(1);
		content.setPayPoint("2832");
		content.setResult(0);
		content.setCpPrivateInfo("101|1011083992214966|32669275|700|5|5");
		
//		NotifyEntity entity = new NotifyEntity("http://10.1.1.88:8088/ftinterface/orderback.do", content.generateContent());
//		NotifyEntity entity = new NotifyEntity("http://3rdapi.ximigame.net/payauth/tcltv", content.generateContent());
		NotifyEntity entity = new NotifyEntity("http://ddzsjpay.ximigame.net/payauth/tcltv", content.generateContent());
//		NotifyEntity entity = new NotifyEntity("http://10.1.1.62:8080", content.generateContent());
		
		HttpAsyncConnExecutor.submitNotifyTask(entity);
		
		/*pool.schedule(new Runnable() {
			
			@Override
			public void run() {
				
				NotifyContent content = new NotifyContent();
				content.setExOrderNo("20151212202057392117");
				content.setPayOrderNo("10011512122019020583");
				content.setAppId("20150929520927572766");
				content.setAmount(500);
				content.setPayType(3);
				content.setTransTime("2015-12-12 20:20:06");
				content.setCounts(1);
				content.setPayPoint("vip_12");
				content.setResult(0);
				content.setCpPrivateInfo("9-1000001492");
				
				NotifyEntity entity = new NotifyEntity("http://ftint.at-et.com:25001/ftinterace/or.do", content.generateContent());
				
				HttpAsyncConnExecutor.submitNotifyTask(entity);
				
			}
		} ,0, TimeUnit.SECONDS);*/
		
	}
	
}