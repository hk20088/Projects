package com.newspace.aps.payChannel.wosai;

import org.apache.log4j.Logger;

import com.newspace.aps.model.order.Order;
import com.newspace.aps.payChannel.wosai.param.QRcodeInfo;
import com.newspace.aps.payChannel.wosai.utils.WosaiHttpUtils;

/**
 * @description 喔噻渠道支付处理类，用来获取生成支付二维码的Code
 * @author huqili
 * @date 2016年9月17日
 *
 */
public class WosaiHandler {

	private static final Logger logger = Logger.getLogger(WosaiHandler.class);
	
	/**
	 * 获取生成支付二维码的Code
	 * @param order
	 * @param payType
	 * @return
	 * @throws Exception
	 */
	public static String getQRCode(Order order,String payType)
	{
		String QRCode = null;
		try 
		{
			QRcodeInfo info = WosaiHttpUtils.getQRCode(order, payType);
			QRCode = info.getQRcode();
		}
		catch (Exception e) 
		{
			logger.error("喔噻_生成二维码Code时出现异常，异常信息为:" + e);
		}
		
		
		return QRCode;
	}
	
}
