package com.newspace.aps.payChannel.pingyao.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.newspace.common.utils.RandomUtils;

/**
 * @description 平遥广电参数配置类
 * @author Huqili
 * @date 2016年4月22日
 *
 */
public class PingyaoPayConfig {

	//版本号
	public static final String VERSION = "2.0";
	
	//操作员代码
	public static final String AGENT_CODE = "2";
	
	//商家ID
	public static final String BUSINESSID = "800001";
	
	//产品ID
	public static final String PRDID = "1001";
	
	//固定值为2
	public static final String TXN_TYPE = "2";
	
	//WSDL地址(移到配置文件中)
//	public static final String WSDL = "http://192.168.11.25:8080/WSA/services/UcWsInterface?wsdl";
	
	/**
	 * 生成stubId，生成规则：yyyyMMddnnnnnnnn
	 * 			其中：
	 * 				yyyy: 4位的年份（如2016）
	 * 				MM  ：2位的月份（01－12）
	 * 				dd  ：2位的日期（01－31）
	 * 				nnnnnnnn: 8位十进制整数，不足8位的用0补齐（如00000012）
	 * @return
	 */
	public static String getStubId()
	{
		return RandomUtils.getRandom(4, 4);
	}
	
	/**
	 * 订单ID，格式：当前时间的毫秒值
	 * @return
	 */
	public static String getStrOrderId()
	{
		return String.valueOf(System.currentTimeMillis());
	}
	
	/**
	 * 订单发送时间，格式：yyyyMMddHHmmss
	 * @return
	 */
	public static String getStrTxnTime()
	{
		StringBuffer sb = new StringBuffer(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(getStrOrderId());
		System.out.println(getStrTxnTime());
		System.err.println(getStubId());
	}
	
}
