package com.newspace.aps.payChannel.guizhou.utils;

import com.newspace.aps.common.ConstantData;
import com.newspace.common.utils.RandomUtils;

/**
 * {@link GuizhouPayConfig}
 * @author Huqili
 * @description 贵州广电参数配置类
 *
 */
public class GuizhouPayConfig {

	//查询城市代码URL
	public static final String QUERY_CITYCODE_URL = "/UAP/InterfacerouteWXAction.do";
	
	//查询客户信息URL
	public static final String QUERY_CUSTINFO_URL = "/UAP/InterfacerouteWXAction.do";
	
	//查询钱袋余额URL
	public static final String QUERY_BALANCE_URL = "/UAP/InterfacerouteWXAction.do";
	
	//点播扣费URL（单次订购）
	public static final String SINGLE_PAY_URL = "/UAP/UAPJAction.do";
	
	//包月扣费URL（包月模式）
	public static final String MONTH_PAY_URL = "/UAP/UAPJAction.do";
	
	//订单订购URL（即获取扫码支付的订单号）
	public static final String ORDER_NO_URL = "/UAP/InterfacerouteWXAction.do";
	
	//订单支付URL（封装payUrl时使用）
	public static final String ORDER_PAY_URL = "/payweb/paytv/payplat!doPayTV";
	
	//支付宝二维码URL
	public static final String ALIPAY_QR_URL = "/payweb/paytv/pay-box!doPayForAli";
	
	//微信二维码URL
	public static final String WECHAT_QR_URL = "/payweb/paytv/pay-box!doPay";
	
	
	//订单确认URL
	public static final String ORDER_CONFIRM_URL = "/UAP/InterfacerouteWXAction.do";
	
	//系统代码，由BOSS分配
	public static final String SYSCODE = "SZATT001";
	
	//客户端编码，客户端密码（查询时使用）
	public static final String CLIENTCODE_QUERY = "SZATT001";
	public static final String CLIENTPWD_QUERY = "SZATT001";
	
	//客户端编码，客户端密码（支付时使用）
	public static final String CLIENTCODE_PAY = "1019";
	public static final String CLIENTPWD_PAY = "6cb2d0b736f3841658074f9b854e8c54";
	
	//栏目ID
	public static final String CAID = "ATETYXPT";
	
	//----------------家庭游戏产品代码 家庭游戏包名：com.atet.familygame.tv---------------------------
	public static final String FAMILY_MONTH = "510248";  //包月
	public static final String FAMILY_HALF_YEAR = "ATET01";  //套餐(优惠包)产品代码（半年）
	public static final String FAMILY_ONE_YEAR = "ATET02";  //套餐(优惠包)产品代码（一年）

	//---------------亲子时光产品代码 亲子包名：com.atet.familytime.tv------------------------------
	public static final String QZ_MONTH = "510303";  //包月
	public static final String QZ_HALF_YEAR = "QZSG02";  //套餐(优惠包)产品代码（半年）
	public static final String QZ_ONE_YEAR = "QZSG01";   //套餐(优惠包)产品代码（一年）
	
	
	//片源提供商
	public static final String PROGRAMPROVIDER = "10047";
	
	//产品类型
	public static final String ONCE = "once";  //单次点播
	public static final String MONTH = "month";  //包月
	public static final String HALF_YEAR = "half_year";  //半年套餐
	public static final String ONE_YEAR = "one_year";  //一年套餐
	


	//根据大厅包名获取包月代码
	public static String getMonth(String packageName)
	{
		return packageName.equals(ConstantData.FAMILY_PACKAGENAME) ? FAMILY_MONTH : QZ_MONTH;
	}
	
	//根据大厅包名获取半年代码
	public static String getHalfYear(String packageName)
	{
		return packageName.equals(ConstantData.FAMILY_PACKAGENAME) ? FAMILY_HALF_YEAR : QZ_HALF_YEAR;
	}
	
	//根据大厅包名获取一年代码
	public static String getYear(String packageName)
	{
		return packageName.equals(ConstantData.FAMILY_PACKAGENAME) ? FAMILY_ONE_YEAR : QZ_ONE_YEAR;
	}
	
	/**
	 * 生成requestid(生成规则：clientcode+YYYYMMDD+8位流水[4位随机+4位自增])
	 * 注：flag = true时，代表普通接口，clientcode取CLIENTCODE_QUERY的值
	 * flag = flase时，代表支付接口，clientcode取CLIENTCODE_PAY的值
	 * @param flag
	 * @return
	 */
	public static String getRequestid(boolean flag)
	{
		StringBuffer sb = new StringBuffer();
		if(flag)
		{
			sb.append(CLIENTCODE_QUERY);
		}
		else
		{
			sb.append(CLIENTCODE_PAY);
		}
		
		sb.append(RandomUtils.getRandom(4, 4));
		
		return sb.toString();
	}

}
