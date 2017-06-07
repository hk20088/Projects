package com.newspace.aps.pay;

/**
 * @description 配置支付相关参数
 * @author huqili
 * @date 2016年9月16日
 *
 */
public class PayConfig {

	
    /**
	 * 当响应请求时如果需要加密但key却为空，则返回一个错误的签名。（此错误签名无任何意义，仅在key为空时使用）
	 */
	public static final String WRONG_SIGN = "HASFDQFAFDLISSAFSAF2WRS2B";
	
	/**
	 * ATET RSA加密中的私钥 
	 */
	public static final String RSA_ATET_PRIVATEKEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJx3hRxjUpFbDLk86gXj4ZOooKRMTp1T1bzP6U6Z+fjNGRLX+aKhewo3VCP2wChQ1kYPxU+AJH0oMT0tXcbxMXCBRfnSdv/xN9nfKmoYybuT+bY7OtNTMc21uHXK0rH+LpN50iR/tCzSK5PAORW4ypzpRNA/IWLqt7IOwZ3wjgfpAgMBAAECgYB6YsqNn+rvo2ZaZhkvLkY9t0KgAMflK7QdkgsN3ka2o8afBKxQ1zpkjU6VKua3IjPYbXGKc9MWyp9pGNknSXW/L09zqp/oOmRo3ap944bw4vW5K0mnzmeIJfV19EUrPX6g/SMKjUd+WaHVvc4Pyn8IuO3viEdEbwFm1q/dmyEd+QJBAMz67ACLyEptWICstydN82Wr6FUyKuIbUh7lHY9kA80QlwhUDVV9zvuJvWnuCM+4QDEzskAxIAfzS1N9K/odUesCQQDDaWRx2OfBvDdjaIRR1SvwyQ3tDRtsFlH429rTSvdUeZe/FqMnXZdF8kr/Y67AbUQZWIS13O5d5h8JsujxkAR7AkBdkkn7sdO7zhCxpKZzRc1PY1tK0PzsfKZPPi3xUCnACcu4XI49sZG0F9ukqKnTEPwUudGsJgDGQFTphuF1ar8DAkBxF8jbHsaaWDzSGoYh+jaRBzs5C1Hoj05nsY4GpSdZS3noTMimGsNW5vBCuEVF9rbn2FQOEMwfqfQin9mzHD+LAkBnS00wd9wG8HAHmqmbhcc0w/hREfpOSmiDY8LRKZZG3hZF1q2e4aEDufkGno521p9JFsgABjEOv/NyanDCcSkc";

	/**
	 * ATET RSA加密中的公钥 
	 */
	public static final String RSA_ATET_PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCcd4UcY1KRWwy5POoF4+GTqKCkTE6dU9W8z+lOmfn4zRkS1/mioXsKN1Qj9sAoUNZGD8VPgCR9KDE9LV3G8TFwgUX50nb/8TfZ3ypqGMm7k/m2OzrTUzHNtbh1ytKx/i6TedIkf7Qs0iuTwDkVuMqc6UTQPyFi6reyDsGd8I4H6QIDAQAB";

	
	//响应给客户端的支付类型
	public static final String PAY_ATET = "ATET";  //采用ATET A豆支付方式
	public static final String PAY_CTC = "CTC"; // Call TPP Client 调用第三方客户端
	public static final String PAY_SPQ = "SPQ"; // Scan Pay QR Code 二维码支付
	public static final String PAY_CTS = "CTS";  // Call Third SDK 调用第三方SDK
	public static final String PAY_MI = "MI";  //Missing Information 信息不足
	
	//渠道代码定义
	public static final String COMMON = "COMMON";  //通用
	public static final String GD_GUIZHOU = "GZGD";  //贵州广电
	
	
	//查询订单的标识
	public static final String FLAG_ORDERNO = "OrderNo";
	public static final String FLAG_PAYMENTORDERNO = "PaymentOrderNo";
	
	//是否根据订单号查询标识
	public static final String QUERY_TRUE = "true";  //根据订单号查
	public static final String QUERY_FALSE = "false"; //不根据订单号查
	
	
}
