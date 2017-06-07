package com.newspace.common.coder;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;


/**
 * RSACoder.java 
 * @description:  采用RSA加密算法的 加密、解密类。
 * 		   RSA算法思想：生成密钥对，自己持有一个密钥A，对外公布一个密钥B；使用密钥A加密，则对方可以使用密钥B进行解密；对方使用密钥B进行加密，自己可使用密钥A解密。
 * 		   基本流程：甲方构造密钥对，并将公钥告知乙方。甲方将数据使用私钥进行加密，然后加密过的数据再使用私钥构造签名；将加密后的数据及签名发送给乙方
 * 		   乙方接收到之后，使用甲方提供的公钥验证签名是否有效，如果有效，对加密过的数据使用公钥进行解密。(乙方向甲方返回信息也可按这个逻辑；也可直接
 * 		   使用甲方的公钥进行加密，然后发给甲方，甲方使用私钥解密)
 * @author:  huqili
 * @date： 2014-4-14 创建
 */
public class RSACoder extends Coder
{
	/**
	 * 加密算法
	 */
	private static final String ALGORITHM_TYPE_RSA = "RSA";

	/**
	 * 公钥Key
	 */
	private static final String PUBLIC_KEY = "RSAPublicKey";

	/**
	 * 私钥Key
	 */
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	/**
	 * 签名算法
	 */
	private static final String SIGNATURE_ALGORITHM = "SHA1WithRSA";

	/**
	 * RSA最大加密明文字节大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/**
	 * RSA最大解密密文字节大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	/**
	 * 生成公钥和私钥。
	 * 
	 * 密钥的长度越长，安全性就越好，但是加密解密所用的时间就会越多。
	 * 而一次能加密的明文长度也与密钥的长度成正比。
	 * 一次能加密的密文长度为：不能超过密钥的长度-11。所以1024bit长度的密钥一次可以加密的明文为1024/8 - 11=117byte。
	 * 当明文长度大于117byte的时候就会报错 ：Data must not be longer than 117 bytes。当要加密的明文长度大于117byte就要分块进行。
	 * 所以非对称加密一般都用于加密对称加密算法的密钥，而不是直接加密内容。
	 * 对于小文件可以使用RSA加密，但加密过程仍可能会使用分段加密。
	 * 
	 * @return map 包含生成的公钥和私钥的Map。
	 */
	public static Map<String, Object> initKey() throws NoSuchAlgorithmException
	{
		KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance(ALGORITHM_TYPE_RSA);
		keyGenerator.initialize(1024);// 密钥初始化长度为1024，基本不可能被破解（现在只能破解到700多位，1024的很安全）
		KeyPair keyPair = keyGenerator.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put(PUBLIC_KEY, publicKey);
		map.put(PRIVATE_KEY, privateKey);
		return map;
	}

	/**
	 * 得到公钥。
	 * @param map 包含公钥私钥的map对象
	 * @return String 使用BASE64包装过的公钥字符串 
	 */
	public static String getPublicKey(Map<String, Object> map)
	{
		Key key = (Key) map.get(PUBLIC_KEY);
		return encryptBASE64(key.getEncoded());
	}

	/**
	 * 得到私钥 
	 * @param map 包含公钥私钥的map对象
	 * @return String 使用BASE64包装过的私钥字符串 
	 */
	public static String getPrivateKey(Map<String, Object> map)
	{
		Key key = (Key) map.get(PRIVATE_KEY);
		return encryptBASE64(key.getEncoded());
	}

	/**
	 * 私钥加密(解密时采用公钥解密)。
	 * @param data 要加密的数据(通常是对称加密算法密钥或摘要信息)
	 * @param key 私钥
	 * @return byte[] 加密过的数据
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String key) throws Exception
	{
		byte[] keyBytes = decryptBASE64(key);
		// 获取PrivateKey对象
		PKCS8EncodedKeySpec pkcs8 = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory factory = KeyFactory.getInstance(ALGORITHM_TYPE_RSA);
		Key privateKey = factory.generatePrivate(pkcs8);

		// 使用私钥对数据加密
		Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);

		// 当数据长度超过117字节的时候，需要进行分段加密
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		while (inputLen - offSet > 0)
		{
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK)
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			else
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();

		return encryptedData;
	}

	/**
	 * 私钥解密。
	 * @param data 要解密的数据
	 * @param key 私钥
	 * @return byte[] 解密得到的原数据
	 */
	public static byte[] decryptByPrivateKey(byte[] data, String key) throws Exception
	{
		byte[] keyBytes = decryptBASE64(key);

		// 取私钥
		PKCS8EncodedKeySpec pkcs8 = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory factory = KeyFactory.getInstance(ALGORITHM_TYPE_RSA);
		Key privateKey = factory.generatePrivate(pkcs8);

		// 使用私钥对数据解密
		Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0)
		{
			if (inputLen - offSet > MAX_DECRYPT_BLOCK)
				cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
			else
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}
	
	/**
	 * 公钥加密 (解密时采用私钥解密)。
	 * @param data 要进行加密的原数据
	 * @param key 公钥
	 * @return String 加密过的数据
	 * @throws Exception 
	 */
	public static String encryptByPublicKey(String data,String key) throws Exception{
		byte[] dataBytes = data.getBytes(DEFAULT_CHARSET);
		byte[] resource = encryptByPublicKey(dataBytes, key);
		return encryptBASE64(resource);
	}

	/**
	 * 公钥加密 (解密时采用私钥解密)。
	 * @param data 要进行加密的原数据
	 * @param key 公钥
	 * @return byte[] 加密过的数据
	 */
	public static byte[] encryptByPublicKey(byte[] data, String key) throws Exception
	{
		// 对公钥解密
		byte[] keyBytes = decryptBASE64(key);
		// 取公钥
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_TYPE_RSA);
		Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0)
		{
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK)
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			else
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}

	/**
	 * 公钥解密。
	 * @param data 加密过的数据
	 * @param key 密钥
	 * @return byte[] 解密过的原数据
	 */
	public static byte[] decryptByPublicKey(byte[] data, String key) throws Exception
	{
		byte[] keyBytes = decryptBASE64(key);

		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_TYPE_RSA);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);

		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);

		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0)
		{
			if (inputLen - offSet > MAX_DECRYPT_BLOCK)
				cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
			else
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	/**
	 * 使用私钥生成签名 （数字签名技术采用私钥加密，公钥解密）
	 * @param content String 要生成签名的原数据(通常是摘要信息)
	 * @param privateKey 私钥
	 * @return String 生成的数字签名字符串
	 */
	public static String sign(String content, String privateKey)
	{
		try
		{
			return sign(content.getBytes(DEFAULT_CHARSET), privateKey);
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 使用私钥生成签名 （数字签名技术采用私钥加密，公钥解密）
	 * @param data 要生成签名的原数据(通常是摘要信息、密钥信息；而不会直接对原数据进行签名)
	 * @param privateKey 私钥
	 * @return String 生成的数字签名字符串
	 */
	public static String sign(byte[] data, String privateKey)
	{
		try
		{
			byte[] keyBytes = decryptBASE64(privateKey);
			PKCS8EncodedKeySpec pkcsKeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_TYPE_RSA);
			PrivateKey key = keyFactory.generatePrivate(pkcsKeySpec);
			// 用私钥对信息生成数字签名
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initSign(key);
			signature.update(data);
			return encryptBASE64(signature.sign());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 使用公钥校验数字签名
	 * @param content String 加密过的数据(通常是摘要信息)
	 * @param publicKey 公钥
	 * @param sign 数字签名
	 * @return boolean 校验数字签名是正确； true：正确，没有被修改过， false：不正确。
	 */
	public static boolean verify(String content, String publicKey, String sign)
	{
		try
		{
			return verify(content.getBytes(DEFAULT_CHARSET), publicKey, sign);
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 使用公钥校验数字签名
	 * @param data 加密过的数据(通常是摘要信息)
	 * @param publicKey 公钥
	 * @param sign 数字签名
	 * @return boolean 校验数字签名是正确； true：正确，没有被修改过， false：不正确。
	 */
	public static boolean verify(byte[] data, String publicKey, String sign)
	{
		try
		{
			byte[] keyBytes = decryptBASE64(publicKey);

			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory factory = KeyFactory.getInstance(ALGORITHM_TYPE_RSA);
			PublicKey key = factory.generatePublic(x509KeySpec);

			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initVerify(key);
			signature.update(data);
			return signature.verify(decryptBASE64(sign));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	* 支付宝RSA签名
	* @param content 待签名数据
	* @param privateKey 商户私钥
	* @param input_charset 编码格式
	* @return 签名值
	*/
	public static String aliPaySign(String content, String privateKey)
	{
		String input_charset = "utf-8";
        try 
        {
        	PKCS8EncodedKeySpec priPKCS8 	= new PKCS8EncodedKeySpec( Base64.decode(privateKey) ); 
        	KeyFactory keyf 				= KeyFactory.getInstance("RSA");
        	PrivateKey priKey 				= keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature
                .getInstance(SIGNATURE_ALGORITHM);

            signature.initSign(priKey);
            signature.update( content.getBytes(input_charset) );

            byte[] signed = signature.sign();
            
            return Base64.encode(signed);
        }
        catch (Exception e) 
        {
        	e.printStackTrace();
        }
        
        return null;
    }
	
	public static void main(String[] args) throws Exception {
//		Map<String, Object> map = initKey();
//		String privateKey = getPrivateKey(map);
//		String publicKey = getPublicKey(map);
		
		String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJx3hRxjUpFbDLk86gXj4ZOooKRMTp1T1bzP6U6Z+fjNGRLX+aKhewo3VCP2wChQ1kYPxU+AJH0oMT0tXcbxMXCBRfnSdv/xN9nfKmoYybuT+bY7OtNTMc21uHXK0rH+LpN50iR/tCzSK5PAORW4ypzpRNA/IWLqt7IOwZ3wjgfpAgMBAAECgYB6YsqNn+rvo2ZaZhkvLkY9t0KgAMflK7QdkgsN3ka2o8afBKxQ1zpkjU6VKua3IjPYbXGKc9MWyp9pGNknSXW/L09zqp/oOmRo3ap944bw4vW5K0mnzmeIJfV19EUrPX6g/SMKjUd+WaHVvc4Pyn8IuO3viEdEbwFm1q/dmyEd+QJBAMz67ACLyEptWICstydN82Wr6FUyKuIbUh7lHY9kA80QlwhUDVV9zvuJvWnuCM+4QDEzskAxIAfzS1N9K/odUesCQQDDaWRx2OfBvDdjaIRR1SvwyQ3tDRtsFlH429rTSvdUeZe/FqMnXZdF8kr/Y67AbUQZWIS13O5d5h8JsujxkAR7AkBdkkn7sdO7zhCxpKZzRc1PY1tK0PzsfKZPPi3xUCnACcu4XI49sZG0F9ukqKnTEPwUudGsJgDGQFTphuF1ar8DAkBxF8jbHsaaWDzSGoYh+jaRBzs5C1Hoj05nsY4GpSdZS3noTMimGsNW5vBCuEVF9rbn2FQOEMwfqfQin9mzHD+LAkBnS00wd9wG8HAHmqmbhcc0w/hREfpOSmiDY8LRKZZG3hZF1q2e4aEDufkGno521p9JFsgABjEOv/NyanDCcSkc";
//		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCcd4UcY1KRWwy5POoF4+GTqKCkTE6dU9W8z+lOmfn4zRkS1/mioXsKN1Qj9sAoUNZGD8VPgCR9KDE9LV3G8TFwgUX50nb/8TfZ3ypqGMm7k/m2OzrTUzHNtbh1ytKx/i6TedIkf7Qs0iuTwDkVuMqc6UTQPyFi6reyDsGd8I4H6QIDAQAB"; 
//		String str = "DC9977B54DE80F6FF1E4D405536A7FEC";
//		//私钥签名
//		String sign = URLEncoder.encode(sign(str,privateKey),"utf-8");
//		System.out.println("签名："+sign);
//		//公钥校验签名
//		System.out.println("\r\n校验签名："+verify(str, publicKey, URLDecoder.decode(sign,"utf-8")));
		
//		//公钥加密
//		String signByPublicKey = encryptByPublicKey(str, publicKey);
//		System.out.println("\r\n公钥加密："+signByPublicKey);
//		
//		//私钥解密
//		byte[] dataByte = decryptByPrivateKey(decryptBASE64(signByPublicKey), privateKey);
//		String data = new String(dataByte);
//		System.out.println("\r\n私钥解密："+data);
		
//		String data = "partner=\"2088901574104678\"&service=\"mobile.securitypay.pay\"&_input_charset=\"utf-8\"&notify_url=\"http%3A%2F%2F61.145.164.114%3A25001%2Fatetpay%2FatetAlipayNotify.do\"&out_trade_no=\"10011507222240000001\"&subject=\"功能熊猫一个\"&payment_type=\"1\"&seller_id=\"2088901574104678\"&total_fee=\"0.01\"&body=\"ATETID=tcl-000,CPNOTIFYURL=http://10.1.1.199:8080/xxx.do,CPPRIVATEINFO=null,APPID=20140815153928685114\"&sign=\"C9XDlwOUGSzzhfUB5oFx4SWJbWqNJ0xKp%2F5lYJiKC3xefvLoSnpQf668WwuyXAcY%2BvQD2X3jevqzGi8U%2B73vaNpty2nBmiL%2BdmvZ4Bi39gEXpNWXdoZS5YfJUvZX82uxKYUJp%2FRzKHMhhHZBpzKqpfnfiWAirBBKcjHnicIkkLA%3D\"&sign_type=\"RSA\"";
//		String data = "partner=2088901574104678&service=mobile.securitypay.pay&_input_charset=utf-8&notify_url=http%3A%2F%2F61.145.164.114%3A25001%2Fatetpay%2FatetAlipayNotify.do&out_trade_no=10011507222240000001&subject=功能熊猫一个&payment_type=1&seller_id=2088901574104678&total_fee=0.01&body=ATETID=tcl-000,CPNOTIFYURL=http://10.1.1.199:8080/xxx.do,CPPRIVATEINFO=null,APPID=20140815153928685114&sign=C9XDlwOUGSzzhfUB5oFx4SWJbWqNJ0xKp%2F5lYJiKC3xefvLoSnpQf668WwuyXAcY%2BvQD2X3jevqzGi8U%2B73vaNpty2nBmiL%2BdmvZ4Bi39gEXpNWXdoZS5YfJUvZX82uxKYUJp%2FRzKHMhhHZBpzKqpfnfiWAirBBKcjHnicIkkLA%3D&sign_type=RSA";
		String data = "a=\"b\"";
		System.out.println(data);
		String sign = aliPaySign(data, privateKey);
		System.out.println("编码后的值是："+sign);
		sign = URLEncoder.encode(sign,"utf-8");
		System.out.println("URL编码后的值："+sign);
		
	}
}