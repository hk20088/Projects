package com.newspace.aps.common.thrift;

import java.lang.reflect.Constructor;

import org.apache.log4j.Logger;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * {@link ThriftServerProxy.java}
 * @description ThriftServer代理类
 * @author huqili
 * @since JDk1.8
 * @date 2016年6月2日
 *
 */
public class ThriftServerProxy {
	
	private static final Logger logger = Logger.getLogger(ThriftServerProxy.class);
	
	private int port;// 端口
	
	private String serviceInterface;// 实现类接口
	
	private Object serviceImplObject;//实现类

	public Object getServiceImplObject() {
		return serviceImplObject;
	}

	public void setServiceImplObject(Object serviceImplObject) {
		this.serviceImplObject = serviceImplObject;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getServiceInterface() {
		return serviceInterface;
	}

	public void setServiceInterface(String serviceInterface) {
		this.serviceInterface = serviceInterface;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void start() {
		new Thread() {
			public void run() {

				try {
					TServerSocket serverTransport = new TServerSocket(getPort());					
					Class Processor = Class.forName(getServiceInterface()+"$Processor");
					Class Iface = Class.forName(getServiceInterface()+"$Iface");
					
					
					Constructor con = Processor.getConstructor(Iface);
					TProcessor processor = (TProcessor)con.newInstance(serviceImplObject);

					TProtocolFactory protFactory = new TCompactProtocol.Factory();
					TThreadPoolServer.Args args = new TThreadPoolServer.Args(serverTransport);
					args.protocolFactory(protFactory);
					
					args.processor(processor);
					TServer server = new TThreadPoolServer(args);
					logger.info("Starting thrift server on port "+getPort()+" ...");
					server.serve();
				} 
				catch (TTransportException e) 
				{
					logger.error("ThriftServerProxy代理类启动服务时出现异常...",e);
				}
				catch (Exception e) 
				{
					logger.error("ThriftServerProxy代理类，动态加载类时出现异常...",e);
				}
			}
		}.start();
	}

	
		

}
