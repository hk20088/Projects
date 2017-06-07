package com.newspace.aps.payChannel.wosai.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.newspace.aps.common.ConstantData;
import com.newspace.aps.model.order.Order;
import com.newspace.aps.payChannel.wosai.param.QRcodeInfo;
import com.newspace.common.coder.Coder;
import com.newspace.common.utils.HttpClientUtils;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.StringUtils;

/**
 * {@link WosaiHttpUtils.java}
 * @description: 请求Wosai接口的工具类
 * @author Huqili
 * @date 2016年9月17日
 *
 */
public class WosaiHttpUtils {

	private static final Logger logger = Logger.getLogger(WosaiHttpUtils.class);
	
	
	/**
	 * 获取生成支付二维码的字符串
	 * @param orderVo
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	public static QRcodeInfo getQRCode(Order orderVo,String payType) throws Exception{
		
		QRcodeInfo info = new QRcodeInfo();
		String url = getUrl(orderVo, payType);
		
		logger.info(String.format("订单[%s]生成的wosai请求地址是：%s",orderVo.getOrderNo(),url));
		//请求wosai得到响应json字符串。
		String jsonStr = HttpClientUtils.get(url, "utf-8");
		
		logger.info(String.format("订单[%s]请求wosai得到的响应信息是：%s", orderVo.getOrderNo(),jsonStr));
		//解析json字符串得到QRCode
		info = resolveJson(info,jsonStr, payType);
		
		return info;
	}
	
	/**
	 * 得到请求地址
	 * @param orderVo
	 * @param flag
	 * @return
	 */
	public static String getUrl(Order orderVo , String payType){
		String url = null;
		if( payType.equals(Order.PAYTYPE_WOSAI_ALIPAY)){
			url = ConstantData.WOSAI_ALIPAY_URL + "?" + getParams(orderVo, payType);
		}else if(payType.equals(Order.PAYTYPE_WOSAI_WECHAT)){
			url = ConstantData.WOSAI_WECHAT_URL + "?" + getParams(orderVo, payType);
		}
		return url;
	}
	
	/**
	 * 得到请求参数 
	 * @param orderVo
	 * @param flag
	 * @return
	 */
	private static String getParams(Order orderVo,String payType){
		
		String signData = getSignData(orderVo, payType, Boolean.TRUE);
		String sign = Coder.getHexStringByEncryptMD5(signData).toUpperCase();
		String reqData = getSignData(orderVo, payType, Boolean.FALSE) + "&sign="+sign;
		return reqData;
	}
	
	/**
	 * 得到待签名字符串
	 * @param orderVo
	 * @param flag 支付渠道标识：0 代表支付宝  1 代表微信
	 * @param mark 获取字符串标记： TRUE代表获取的是待签名字符串 ； FALSE 代表获取的是请求参数字符串
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private static String getSignData(Order orderVo,String payType,boolean mark){
		
		StringBuffer sb = new StringBuffer();
		sb.append("channel_id=").append(WosaiConfig.CHANNEL_ID);
		
		if(mark){
			sb.append("&channel_secret=").append(WosaiConfig.CHANNEL_SECRET);
		}
		
		sb.append("&notify_url=").append(ConstantData.WOSAI_NOTIFY_URL);
		
		/*
		 * 当支付方式为微信时，参数operator为必填
		 * 由于喔噻限制一个订单号只能请求一次，但是这里同一个订单号可能分别请求获取生成微信和支付宝二维码的Code，所以在订单号前面加"w"和"A"做标识
		 */
		if(payType.equals(Order.PAYTYPE_WOSAI_WECHAT))
		{
			sb.append("&operator=").append("ATET");
			sb.append("&store_own_order_id=").append("W"+orderVo.getOrderNo());
		}
		else
		{
			sb.append("&store_own_order_id=").append("A"+orderVo.getOrderNo());
		}
		
		sb.append("&subject=").append(StringUtils.isNullOrEmpty(orderVo.getPayPointName()) ? "游戏道具" : orderVo.getPayPointName()); //如果获取不到道具名称，支付时默认显示“游戏道具”
		sb.append("&total_fee=").append(orderVo.getAmount());
		sb.append("&wosai_app_id=").append(WosaiConfig.WOSAI_APP_ID);
		
		if(mark){
			sb.append("&wosai_appkey=").append(WosaiConfig.WOSAI_APPKEY);
		}
		
		sb.append("&wosai_store_id=").append(WosaiConfig.WOSAI_STORE_ID);
		
		return sb.toString();
	}
	
	/**
	 * 解析json字符串得到QRCode
	 * @param json
	 * @return
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	public static QRcodeInfo resolveJson(QRcodeInfo info,String json,String payType) throws JsonProcessingException, IOException{
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode dataNode = mapper.readTree(json);
		
		if(dataNode.get("code").toString().equals("\"10000\"")){
			JsonNode detailNode = mapper.readTree(dataNode.get("data").toString());
			
			//将喔噻订单号和支付方式保存到info中
			info.setWosaiOrderNo(detailNode.get("order_sn").toString().replaceAll("\"", ""));
			
			if(payType.equals(Order.PAYTYPE_WOSAI_ALIPAY)){
				
				JsonNode responseNode = mapper.readTree(detailNode.get("order_pay_detail").toString());
				JsonNode alipayNode = mapper.readTree(responseNode.get("response").toString());
				
				//将取出来的最后一层json字符串转化为map
				Map<String, Object> map = JsonUtils.json2Map(alipayNode.get("alipay").toString());
				
				//将支付二维码和支付方式保存到info中
				info.setQRcode(map.get("qr_code").toString().replaceAll("\"", ""));
				info.setPayType(Order.PAYTYPE_WOSAI_ALIPAY);
				
			}
			else if(payType.equals(Order.PAYTYPE_WOSAI_WECHAT)){
				
				Map<String, Object> map = JsonUtils.json2Map(detailNode.get("order_pay_detail").toString());
				
				//将支付二维码和支付方式保存到info中
				info.setQRcode(map.get("code_url").toString().replaceAll("\"", ""));
				info.setPayType(Order.PAYTYPE_WOSAI_WECHAT);
			}
		}
		
		return info;
	}
}
