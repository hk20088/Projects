package com.newspace.aps.pay.param;

import java.util.HashMap;
import java.util.Map;

import com.newspace.aps.model.order.Order;

/**
 * @description 支付方式枚举类
 * @author huqili
 * @date 2016年9月17日
 *
 */
public enum PayTypeEnum {

	PAYTYPE_COIN(Order.PAYTYPE_COIN,"A豆","com.newspace.aps.payChannel.coin.ACoinStrategy"),          
	PAYTYPE_POINT(Order.PAYTYPE_POINT,"积分",""),         
	PAYTYPE_DIAMOND(Order.PAYTYPE_DIAMOND,"钻石",""),       
	PAYTYPE_VAC(Order.PAYTYPE_VAC,"联通VAC",""),           
	PAYTYPE_WO(Order.PAYTYPE_WO,"联通沃邮箱","com.newspace.aps.payChannel.womailbox.WoMailboxStrategy"),            
	PAYTYPE_IPTV(Order.PAYTYPE_IPTV,"联通IPTV","com.newspace.aps.payChannel.unicomiptv.strategy.IptvStrategy"),
	PAYTYPE_WOCHENG(Order.PAYTYPE_WOCHENG,"联通沃橙","com.newspace.aps.payChannel.wocheng.strategy.WoChengStrategy"),
	PAYTYPE_ALIPAY(Order.PAYTYPE_ALIPAY,"支付宝(手机版)",""),        
	PAYTYPE_WOSAI_ALIPAY(Order.PAYTYPE_WOSAI_ALIPAY,"支付宝","com.newspace.aps.payChannel.wosai.strategy.WosaiAlipayStrategy"),  
	PAYTYPE_WOSAI_WECHAT(Order.PAYTYPE_WOSAI_WECHAT,"微信","com.newspace.aps.payChannel.wosai.strategy.WosaiWechatStrategy"),  
	PAYTYPE_HISENSE(Order.PAYTYPE_HISENSE,"海信",""),       
	PAYTYPE_COOCAA(Order.PAYTYPE_COOCAA,"创维","com.newspace.aps.payChannel.coocaa.strategy.CoocaaStrategy"),        
	PAYTYPE_GUIZHOU_BAG(Order.PAYTYPE_GUIZHOU_BAG,"贵州钱袋","com.newspace.aps.payChannel.guizhou.strategy.GuizhouBagStrategy"),   
	PAYTYPE_GUIZHOU_ALIPAY(Order.PAYTYPE_GUIZHOU_ALIPAY,"支付宝","com.newspace.aps.payChannel.guizhou.strategy.GuizhouAlipayStrategy"),
	PAYTYPE_GUIZHOU_WECHAT(Order.PAYTYPE_GUIZHOU_WECHAT,"微信","com.newspace.aps.payChannel.guizhou.strategy.GuizhouWechatStrategy"),
	PAYTYPE_PINGYAO(Order.PAYTYPE_PINGYAO,"平遥","com.newspace.aps.payChannel.pingyao.strategy.PingYaoStrategy"),       
	PAYTYPE_FUJIAN(Order.PAYTYPE_FUJIAN,"福建","com.newspace.aps.payChannel.fujian.strategy.FujianStrategy");
	
	private String Code; //支付方式代码
	private String Name; //支付方式名称
	private String PackageName; //支付方式对应的策略类的包名
	
	private static final Map<String, String> getNameMap = new HashMap<String,String>();
	private static final Map<String, String> getPackageMap = new HashMap<String,String>();
	
	PayTypeEnum(String code,String name,String packageName) 
	{
		this.Code = code;
		this.Name = name;
		this.PackageName = packageName;
	}

	static
	{
		for(PayTypeEnum payType : PayTypeEnum.values())
		{
			getNameMap.put(payType.getCode(), payType.getName());
			getPackageMap.put(payType.getCode(), payType.getPackageName());
		}
	}

	/**
	 * 根据Code获取对应的名称
	 * @param code
	 * @return
	 */
	public static String getName(String code)
	{
		return getNameMap.get(code);
	}
	
	/**
	 * 根据Code获取对应的包名
	 * @param code
	 * @return
	 */
	public static String getPackage(String code)
	{
		return getPackageMap.get(code);
	}
	
	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getPackageName() {
		return PackageName;
	}

	public void setPackageName(String packageName) {
		PackageName = packageName;
	}

	

}
