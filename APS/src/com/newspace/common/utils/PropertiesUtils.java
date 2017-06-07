package com.newspace.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * PropertiesUtils.java 
 * @description 用于操作properties文件的工具类
 * @author huqili  
 */
public class PropertiesUtils
{

	private static final Logger logger = Logger.getLogger(PropertiesUtils.class);


	/**
	 * 根据.properties文件，获得Properties对象
	 * @param fileName .properties文件名，无需输入后缀名
	 * @return Properties对象
	 */
	public static Properties getProps(String fileName)
	{
		
		if (fileName.endsWith(".properties"))
		{
			fileName = fileName.substring(0, fileName.indexOf(".properties"));
		}
		
		Properties props = new Properties();
		try {
			String filePath = System.getProperty("user.dir") + File.separator
				+ "APS" + File.separator
				+ "config" + File.separator + fileName + ".properties";
			logger.info("配置文件的路径是："+ filePath);
			FileInputStream fis = new FileInputStream(filePath);
			props.load(fis);
			fis.close();
		} catch (FileNotFoundException e) {
			logger.error("加载Properties文件出错_找不到配置文件！", e);
			return null;
		}catch (IOException e) {
			logger.error("加载Properties文件出错！", e);
			return null;
		}
		
		return props;
	}

	/**
	 * 根据.properties文件，获得Properties对象
	 * @param fileName  .properties文件名，无需输入后缀名
	 * @param flag 如果为true，不需要每次重新加载;如果为false，每次重新加载。
	 * @return
	 */
	public static Properties getProps(String fileName,boolean flag){
		Properties props = null;
		if(flag){
			props = getProps(fileName);
		}else{
			props = loadProperties(fileName);
		}
		return props;
	}
	
	/**
	 * 获得配置文件中具体某属性的值
	 * @param fileName properties文件名，无需输入后缀名
	 * @param propertyName 属性名
	 * return String 属性值
	 */
	public static String getPropertyValue(String fileName, String propertyName)
	{
		Properties props = getProps(fileName);
		return props.getProperty(propertyName);
	}

	/**
	 * 获得属性值分隔产生的数组
	 * @param fileName properties文件名，无需输入后缀名
	 * @param propertyName 属性名
	 * @param separator  分隔符
	 * @return String[]  属性值分隔产生的String数组
	 */
	public static String[] getSeparateValue(String fileName, String propertyName, String separator)
	{
		String value = getPropertyValue(fileName, propertyName);
		return value.split(separator);
	}


	/**
	 * 获取指定资源文件中指定propertyName的值，并格式化字符串
	 * @param fileName 资源文件名
	 * @param propertyName 资源文件中的key
	 * @param 格式化字符串中的参数数组
	 * @return
	 */
	public static String getPropertyValueWithParams(String fileName, String propertyName, String[] params)
	{
		String msg = null;
		try
		{
			msg = getPropertyValue(fileName, propertyName);
			MessageFormat format = new MessageFormat(msg);
			msg = format.format(params);
		}
		catch (RuntimeException e)
		{
			e.printStackTrace(System.err);
		}
		return msg;
	}

	/**
	 * 私有方法：加载Properties
	 */
	private static Properties loadProperties(String fileName)
	{
		Properties props = null;
		try
		{
			props = new Properties();
			InputStream is = PropertiesUtils.class.getResourceAsStream("/" + fileName + ".properties");
			props.load(is);
			is.close();
			return props;
		}
		catch (IOException e)
		{
			logger.error("加载Properties文件出错！", e);
			return null;
		}
	}
	

	public static void main(String[] args)
	{
		System.out.println(getPropertyValue("configuration", "WebPath"));
		System.out.println(getPropertyValueWithParams("configuration", "WebPath", new String[] { "xx" }));
	}
}