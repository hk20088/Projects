package com.newspace.aps.common.thrift;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * @description 启动thrift服务的监听器
 * @author huqili
 * @since JDK1.8
 * @date 2016年6月3日
 *
 */
public class ThriftServerStartListener implements ServletContextListener{
	
	private static Logger logger = Logger.getLogger(ThriftServerStartListener.class);
	

	/**
	 * WEB程序启动时加载监听器，调用此方法循环启动thrift服务
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void contextInitialized(ServletContextEvent event) {
		
		try 
		{
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
			
			List<ThriftServerProxy> list = ((List<ThriftServerProxy>) context.getBean("thriftServerList"));
			
			if(list != null && list.size() > 0)
			{
				for(ThriftServerProxy userProxy:list)
				{
					userProxy.start();
				}
			}

			logger.info("Thrift Server监听接口启动...");
		} 
		catch (Exception e) 
		{
			logger.error("Thrift Server监听接口启动错误...", e);
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}

	

}

