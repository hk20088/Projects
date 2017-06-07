package com.newspace.aps.common;

import java.util.Properties;

import org.springframework.stereotype.Component;

import com.newspace.common.utils.PropertiesUtils;

/**
 * {@link ConstantData.java}
 * 
 * @description 常量类，存放从configuration.properties配置文件中读取的有可能被修改的常量。
 * @author Huqili
 * @date 2016年5月31日
 *
 */

@Component
public class ConstantData {

	public static final String ORDER_CODE_PAY;	//订单号前缀代码
		
	//---------------------------贵州广电相关地址-------------------------------------
	public static final String GUIZHOU_BOSS_URL;  //贵州广电网关地址
	
	public static final String GUIZHOU_NOTIFY_URL;  //贵州广电异步通知地址

	public static final String GUIZHOU_QRCODE_PAY_URL; //贵州广电扫码支付网关地址
	
	public static final String GUIZHOU_REDIRECT_URL;  //跳转地址
	
	public static final String FAMILY_PACKAGENAME;  //家庭游戏大厅包名
	
	//--------------------------喔噻相关地址------------------------------------------
	public static final String WOSAI_NOTIFY_URL;  //wosai异步通知地址
	
	public static final String WOSAI_ALIPAY_URL;  //wosai-请求支付宝扫码支付接口地址
	
	public static final String WOSAI_WECHAT_URL;  //wosai-请求微信扫码支付接口地址
	
	//--------------------------平遥wsdl地址-----------------------------------------
	public static final String PINGYAO_WSDL;
	

	//--------------------------福建广电相关地址-------------------------------
	public static final String FUJIAN_QUOTA_EVERYDAY;  //每日限额
	
	public static final String FUJIAN_REDIRECT_URL;  //支付完成后跳转地址
	
	public static final String FUJIAN_BOSS_URL;  //BOSS支付地址
	
	//--------------------------联通沃橙旧平台支付地址-----------------------------
	public static final String ATET_WOCHENG_PAY_URL;
	
	static{
		
		Properties prop = PropertiesUtils.getProps("configuration");
		
		//订单号前缀代码
		ORDER_CODE_PAY = prop.getProperty("order_code_pay");
		
		//贵州广电相关地址
		GUIZHOU_BOSS_URL = prop.getProperty("guizhou_boss_url");
		GUIZHOU_NOTIFY_URL = prop.getProperty("guizhou_notify_url");
		GUIZHOU_QRCODE_PAY_URL = prop.getProperty("guizhou_QRCode_pay_url");
		GUIZHOU_REDIRECT_URL = prop.getProperty("guizhou_redirect_url");
		
		//喔噻相关地址
		WOSAI_NOTIFY_URL = prop.getProperty("wosai_notify_url");
		WOSAI_ALIPAY_URL = prop.getProperty("wosai_alipay_url");
		WOSAI_WECHAT_URL = prop.getProperty("wosai_wechat_url");
		
		//平遥WSDL地址
		PINGYAO_WSDL = prop.getProperty("pingyao_wsdl");
		
		//家庭游戏大厅包名
		FAMILY_PACKAGENAME = prop.getProperty("family_packageName");
		
		//福建广电支付完成后跳转地址
		FUJIAN_REDIRECT_URL = prop.getProperty("fujian_redirect_url");
		
		//福建广电支付请求地址
		FUJIAN_BOSS_URL = prop.getProperty("fujian_boss_url");
		FUJIAN_QUOTA_EVERYDAY = prop.getProperty("fujian_quota_everyday");
		
		//联通沃橙旧平台支付地址
		ATET_WOCHENG_PAY_URL = prop.getProperty("atet_wocheng_pay_url");
		
	}
}
