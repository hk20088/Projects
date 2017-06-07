package com.newspace.aps.payChannel.coocaa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newspace.common.coder.Coder;

/**
 * @description: 酷开回调接口工具
 * @author liqiao
 * @date 2017年2月10日 下午2:32:11 
 */
public class CoocaaUtils {

	/**
	 * @description:判断酷开请求是否合法。
	 * 				判断依据：将加密计算出来的值跟传入的sign_code 值进行对比，如果一致，则证明请求是合法的
	 * @param paramJson
	 * @return
	 */
	public static boolean isLegal(Map<String, Object> paramJson)
	{
		boolean flag = Boolean.TRUE;
//		String signCode = getSignCode(paramJson);
		String signCode = getSignStr(paramJson);
		String sign_code = paramJson.get("sign_code").toString();
		if(!signCode.equalsIgnoreCase(sign_code))
		{
			flag = Boolean.FALSE;
		}
		
		return flag;
	}
	
	/**
	 * @description: 根据规则生成签名字符
	 * @param paramJson
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String getSignCode(Map<String, Object> paramJson)
	{
		Map<String, Object> param = new HashMap<String, Object>();
        param.put("appCode", paramJson.get("appCode").toString());
        param.put("mac", paramJson.get("mac").toString());
        param.put("order_id", paramJson.get("order_id").toString());
        param.put("pay_time", paramJson.get("pay_time").toString());
        param.put("resp_code", paramJson.get("resp_code").toString());
        param.put("tel", paramJson.get("tel").toString());
        
        return getSignStr(param);
	}
	

	/**
	 * @description: 按照规则生成签名字符串
	 * 					签名规则：1.	将所有参数名称(不包含sign_code本身)按照字母顺序排序,从a-z
									2.	将这些参数的值连接成一个字符串，在最后连接上key
									3.	将这个字符串作为源字符串进行MD5加密，然后转为小写
									4.	将加密计算出来的值跟传入的sign_code 值进行对比，如果一致，则证明请求是合法的

	 * @param params
	 * @return
	 */
	private static String getSignStr(Map<String, Object> params)
	{
		StringBuffer prestr = new StringBuffer();
		String sign = "";
		String sign_code = "";
		
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		for(int i = 0; i < keys.size(); i++)
		{
			String key = keys.get(i);
			if(!key.equals("sign_code"))
			{
				String value = params.get(key).toString();
				prestr.append(value);
			}
		}
		
		//生成待签名字符串
		sign = prestr + CoocaaConfig.appKey;
		//签名，并转化为小写
		sign_code = Coder.getHexStringByEncryptMD5(sign).toLowerCase();
		
		return sign_code;
	}
	

}
