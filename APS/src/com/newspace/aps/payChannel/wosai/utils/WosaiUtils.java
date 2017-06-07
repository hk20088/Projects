package com.newspace.aps.payChannel.wosai.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newspace.common.coder.Coder;
import com.newspace.common.utils.StringUtils;

/**
 * {@link WosaiUtils.java}
 * @description: 处理wosai同步通知的工具类
 * @author Huqili
 * @date 2016年9月17日
 *
 */
public class WosaiUtils {

	/**
	 * 得到签名字符串
	 * 规则：非空参数按照ASCII排序后按照 “key=value&key=value”的格式拼接起来，最后再加上“&key=appsecrect”。
	 * 然后再用MD5加密后转大写
	 * @param map
	 * @return
	 */
	public static String getSign(Map<String, Object> map){
		Map<String, String> maps = paraFilter(map);
		String signStr = createLinkString(maps);
		signStr += "&key="+WosaiConfig.WOSAI_APPKEY;
		
		System.out.println("待签名字符串是："+signStr);
		String sign = Coder.getHexStringByEncryptMD5(signStr).toUpperCase(); 
		System.out.println("生成的签名字符串是："+sign);
		return sign;
	}
	
	/** 
	 * 除去Map中的空值和签名参数
	 * @param Array 签名参数Map
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	private static Map<String, String> paraFilter(Map<String, Object> map)
	{
		Map<String, String> result = new HashMap<String, String>();

		if (map == null || map.size() <= 0)
			return result;

		for (String key : map.keySet())
		{
			String value =map.get(key).toString().replaceAll("\"", "");
			if (StringUtils.isNullOrEmpty(value) || value.equals("null") || key.equalsIgnoreCase("sign"))
				continue;
			result.put(key, value);
			System.out.println("key："+key +"  value："+value);
		}
		return result;
	}

	/** 
	 * 把数组所有元素排序，并按照"参数=参数值"的模式用"&"字符拼接成字符串
	 * @param params 需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	private static String createLinkString(Map<String, String> params)
	{
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		StringBuilder prestr = new StringBuilder();

		for (int i = 0; i < keys.size(); i++)
		{
			String key = keys.get(i);
			String value = params.get(key);

			if (i == keys.size() - 1)
				prestr.append(key).append("=").append(value);
			else
				prestr.append(key).append("=").append(value).append("&");
		}

		System.out.println("生成的拼接字符串是："+prestr.toString());
		return prestr.toString();
	}
}
