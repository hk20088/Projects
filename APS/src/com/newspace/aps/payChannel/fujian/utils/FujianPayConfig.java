package com.newspace.aps.payChannel.fujian.utils;

/**
 * @description 福建广电支付配置类，存储支付请求时需要用到的常量
 * @author huqili
 * @date 2016年11月28日
 *
 */
public class FujianPayConfig {
	
	//版本号
	public static final String APPVER = "V01";

	//接入渠道，固定值为 TV
	public static final String DEVTYPE = "TV";
	
	//商户号
//	public static final String MERCHANTID = "T00001"; //测试商户号
	public static final String MERCHANTID = "400005"; //正式商户号

	//安全校验码
	//测试
//	public static final String ORGKEY = "ZDI2NDg5N2YxY2VmMzQ2ZDkyNTFlNzE2ZmRlZDE3NmEzZDRiOGQ5YWEwMmNhYWMyMWNjMjIxZWU2NDMyZmNiNzY3ZmY5YTcxMzcyY2ZkMjg1MDgxYThkYmQ4YmMzYmIxY2EzOWVhODM3ODc4ZGQxZmI4ZWVlYWM2NjAzMzU4ZDQ=";
	
	//正式
	public static final String ORGKEY = "NDgzOWI0NmQ5ZjFkNTZkM2Y3ODE3MmE2MzM1MDM3MzUyMDllZGQwZDk0ZDEyOTI2ZmNjODgzNGUxMTQ0MjFjYmRiMmYyNjNmOWYwN2YyZGNiMjhlMjNjMDgwN2VkYjQ1Nzk1ZDcwYWE5ZjU4Y2UyNWMwY2RjMGY4ZTQwYjI5NDc=";
	
	//公钥，验签时使用
	//测试
//	public static final String PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDoZy6Gv/rfDT/rIIS7fDk9pMxNubUhoHZm35SYhy47H8wZW8Cleqv7YyjWLcSYJqBzO7iE4SOm1xDS4jrf256nU9cCjsvmPKNny1eb0w5Ofx/LPWBgLlCroDm1iwpOLyrMgMpxHo2nLhmYuIn7i1DXKJb20KwGt5CdNSXRyBD0zQIDAQAB";
	
	//正式
	public static final String PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDoZy6Gv/rfDT/rIIS7fDk9pMxNubUhoHZm35SYhy47H8wZW8Cleqv7YyjWLcSYJqBzO7iE4SOm1xDS4jrf256nU9cCjsvmPKNny1eb0w5Ofx/LPWBgLlCroDm1iwpOLyrMgMpxHo2nLhmYuIn7i1DXKJb20KwGt5CdNSXRyBD0zQIDAQAB";
	
	//用户号查询接口名称
	public static final String QUERYUSERBYSMCARD="/payquerymgr/qryUserByIcCard.json";
	
	//缴款接口名称
	public static final String PAYORDER="/payordermanage/payOrder.do";
	
	//查询单张订单接口名称
	public static final String QUERYORDER="/payquerymgr/findOrder.json";
}
