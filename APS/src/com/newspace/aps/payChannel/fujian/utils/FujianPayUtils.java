package com.newspace.aps.payChannel.fujian.utils;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @description 福建广电工具类，包含验签方法
 * @author huqili
 * @date 2016年11月29日
 *
 */
public class FujianPayUtils {

	/**
	 * 验签方法 注意：签名原串按字符集GBK转Byte[],公钥做Base64解码，Sign签名做2次Base64解码
	 * 
	 * @param map
	 * @return
	 */
	public static boolean verifySign(Map<String, String> map) {
	
		return com.bs.security.compay.client.BsSignAndMac.verifyPayOrderSign(map.get("merchantID"),
				map.get("ordNum"), map.get("ordDate"), new BigDecimal(map.get("payMent")), map.get("payState"),
				map.get("platformNo"), map.get("tranBank"), map.get("tranBankNo"), map.get("orgPara"),
				map.get("tranTime"), FujianPayConfig.PUBLICKEY, map.get("sign"));
	}

	/**
	 * 根据规则拼装待签名字符串，参数顺序：
	 * merchantID=00001&ordNum=2014000003&ordDate=20140730&payMent=100.00&
	 * payState=Y&platformNo=201400000332
	 * &tranBank=CUP&tranBankNo=201400000353&orgPara=&tranTime=123453
	 * 备注：金额格式化为#.00
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String getData(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		sb.append("merchantID=").append(map.get("merchantID"));
		sb.append("&ordNum=").append(map.get("ordNum"));
		sb.append("&ordDate=").append(map.get("ordDate"));
		sb.append("&payMent=").append(map.get("payMent"));
		sb.append("&payState=").append(map.get("payState"));
		sb.append("&platformNo=").append(map.get("platformNo"));
		sb.append("&tranBank=").append(map.get("tranBank"));
		sb.append("&tranBankNo=").append(map.get("tranBankNo"));
		sb.append("&orgPara=").append(map.get("orgPara"));
		sb.append("&tranTime=").append(map.get("tranTime"));

		return sb.toString();
	}
}
