package com.newspace.aps;


import javax.servlet.ServletException;

import com.newspace.aps.common.BaseServlet;
import com.newspace.aps.paynotify.HttpAsyncConnExecutor;

/**
 * StartupServlet.java 
 * @description:  初始化Servlet  
 * @author huqili
 * @date： 2015年3月4日
 */
@SuppressWarnings("serial")
public class StartupServlet extends BaseServlet
{
	@Override
	public void init() throws ServletException
	{
		//匿名内部类 启动异步通知模块
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				HttpAsyncConnExecutor.execute();
				
			}
		}).start();
		
		
	}
}
