package com.newspace.aps.payChannel.guizhou;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonProcessingException;

import com.newspace.aps.ReturnCode;
import com.newspace.aps.model.Merchandise;
import com.newspace.aps.model.goods.Goods;
import com.newspace.aps.model.goods.VipTypeEnum;
import com.newspace.aps.model.order.Order;
import com.newspace.aps.payChannel.guizhou.param.CustomerInfo;
import com.newspace.aps.payChannel.guizhou.param.PayResp;
import com.newspace.aps.payChannel.guizhou.param.TransitParam;
import com.newspace.aps.payChannel.guizhou.utils.GuizhouPayConfig;
import com.newspace.aps.payChannel.guizhou.utils.GuizhouPayUtils;
import com.newspace.aps.payChannel.guizhou.utils.GuizhouUrlUtils;

/**
 * @description 贵州广电支付处理类
 * @author huqili
 *
 */
public class GuiZhouHandler {

	private static final Logger logger = Logger.getLogger(GuiZhouHandler.class);
	
	/**
	 * 贵州广电钱袋支付
	 * @param sm_no
	 * @param price 单位 元
	 * @param payPoint
	 * @return
	 */
	public static int pay_bag(String sm_no, double price,Merchandise merch,String packageName) 
	{
		PayResp payResp = new PayResp();
		int returnCode = ReturnCode.SUCCESS.getCode();
		
		try
		{
			if(isEnough(sm_no, price))
			{
				//备注：目前商品表关于VIP的设计还不完善，这里暂时用以下方法判断VIP的类型   - 胡起立 2016年9月22日 
				if(merch.getType() != null && merch.getType().equals(Goods.TYPE_VIP) && VipTypeEnum.VIP_MONTH.getVipType().equals(merch.getPeriodType()))  //包月
				{
					payResp = GuizhouPayUtils.xpay(GuizhouPayConfig.MONTH, sm_no, price,packageName);
				}
				else if(merch.getType() != null && merch.getType().equals(Goods.TYPE_VIP) && VipTypeEnum.VIP_HALF_YEAR.getVipType().equals(merch.getPeriodType()))  //半年
				{
					payResp = GuizhouPayUtils.xpay(GuizhouPayConfig.HALF_YEAR, sm_no, price,packageName);
				}
				else if(merch.getType() != null && merch.getType().equals(Goods.TYPE_VIP) && VipTypeEnum.VIP_ONE_YEAR.getVipType().equals(merch.getPeriodType()))  //一年优惠包
				{
					payResp = GuizhouPayUtils.xpay(GuizhouPayConfig.ONE_YEAR, sm_no, price,packageName);
				}
				else  //单次订购
				{
					payResp = GuizhouPayUtils.pay(sm_no, price, merch.getMerchId(), merch.getMerchName());
				}
				
								
				//如果支付成功
				if("0000".equals(payResp.getStatus()))
				{
					logger.info("钱袋支付成功...");
				}
				else if("101".equals(payResp.getStatus()))
				{
					logger.error(String.format("计费对象已订购过此产品,BOSS返回状态为[%s],状态描述为[%s]", payResp.getStatus(),payResp.getMessage()));
					returnCode = ReturnCode.GUIZHOU_ALREADY_BUY.getCode();
				}
				else 
				{
					logger.error(String.format("钱袋支付失败，BOSS返回状态为[%s],状态描述为[%s]", payResp.getStatus(),payResp.getMessage()));
					returnCode = ReturnCode.ERROR.getCode();
				}
			}
			else
			{
				logger.error("用户钱袋余额不足...");
				returnCode = ReturnCode.GUIZHOU_MONEY_NOT_ENOUGH.getCode();
				
			}
		} 
		catch (Exception e)
		{
			logger.error(String.format("智能卡号[%s]钱袋支付发生异常，异常信息为[%s]",sm_no, e));
			returnCode = ReturnCode.ERROR.getCode();
		}
		
		
		return returnCode;
		
	}
	
	
	/**
	 * 判断用户余额是否足够
	 * @param sm_no
	 * @param amount 订单价格，单位为 分
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	private static boolean isEnough(String sm_no,double price) throws JsonProcessingException, IOException
	{
		//查询用户信息
		CustomerInfo custInfo = getCustInfo(sm_no);
		
		logger.info(String.format("用户钱袋余额为[%s元]，此订单价格为[%s元]", custInfo.getAmount(),price));
		
		//判断余额是否足够
		boolean flag = custInfo.getAmount() >= price ? true : false;
		
		return flag;
	}
	
	/**
	 * 查询用户信息
	 * @param sm_no
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public static CustomerInfo getCustInfo(String sm_no) throws JsonProcessingException, IOException
	{
		//查询城市代码
		String cityCode = GuizhouPayUtils.getCityCode(sm_no);
				
		//查询用户信息
		CustomerInfo custInfo = GuizhouPayUtils.getCusInfo(sm_no, cityCode);
		
		//查询用户余额，这里单位是 元
		double fee = GuizhouPayUtils.queryBalance(cityCode, custInfo.getCustid());
		
		custInfo.setCityCode(cityCode);
		custInfo.setAmount(fee);
		
		return custInfo;
	}
	
	/**
	 * 生成支付二维码
	 * @param sm_no
	 * @param price
	 * @param payPoint
	 * @param flag 支付方式标识
	 * @return 
	 */
	public static TransitParam getQRCodeUrl(String sm_no, double price,Merchandise merch,String flag,String packageName)
	{
		String orderid = null;
		String payUrl = null;
		int returnCode = ReturnCode.ERROR.getCode();
		TransitParam param = new TransitParam();
		
		try
		{
			//查询用户信息
			CustomerInfo custInfo = getCustInfo(sm_no);
			param.setCitycode(custInfo.getCityCode());
			
			//获取订单支付的订单号
			if(merch.getType() != null && merch.getType().equals(Goods.TYPE_VIP) && VipTypeEnum.VIP_MONTH.getVipType().equals(merch.getPeriodType()))  //包月
			{
				orderid = GuizhouPayUtils.getOrderid(GuizhouPayConfig.MONTH, custInfo.getCityCode(), custInfo.getCustid(), String.valueOf(price), sm_no,merch.getMerchId(), merch.getMerchName(),packageName);
			}
			else if(merch.getType() != null && merch.getType().equals(Goods.TYPE_VIP) && VipTypeEnum.VIP_HALF_YEAR.getVipType().equals(merch.getPeriodType()))  //半年
			{
				orderid = GuizhouPayUtils.getOrderid(GuizhouPayConfig.HALF_YEAR, custInfo.getCityCode(), custInfo.getCustid(), String.valueOf(price),sm_no,merch.getMerchId(), merch.getMerchName(),packageName);
			}
			else if(merch.getType() != null && merch.getType().equals(Goods.TYPE_VIP) && VipTypeEnum.VIP_ONE_YEAR.getVipType().equals(merch.getPeriodType()))  //一年优惠包
			{
				orderid = GuizhouPayUtils.getOrderid(GuizhouPayConfig.ONE_YEAR, custInfo.getCityCode(), custInfo.getCustid(), String.valueOf(price),sm_no,merch.getMerchId(), merch.getMerchName(),packageName);
			}
			else  //单次订购
			{
				orderid = GuizhouPayUtils.getOrderid(GuizhouPayConfig.ONCE, custInfo.getCityCode(), custInfo.getCustid(), String.valueOf(price), sm_no,merch.getMerchId(), merch.getMerchName(),packageName);
			}
			
			logger.info("贵州广电计费_生成的订单号是："+orderid);
			
			if(orderid != null)
			{
				if(orderid.equals("0")) //说明用户已订购该产品，提示给用户
				{
					returnCode = ReturnCode.GUIZHOU_ALREADY_BUY.getCode();
				}
				else
				{
					returnCode = ReturnCode.SUCCESS.getCode();
					//封装支付支付二维码URL
					if(flag.equals(Order.PAYTYPE_GUIZHOU_ALIPAY))
					{
						payUrl = GuizhouUrlUtils.getAlipayUrl(orderid, custInfo.getCityCode(), custInfo.getCustid(), custInfo.getCustName(), String.valueOf(price), sm_no, merch.getMerchName());
					}
					else
					{
						payUrl = GuizhouUrlUtils.getWechatUrl(orderid, custInfo.getCityCode(), custInfo.getCustid(), custInfo.getCustName(), String.valueOf(price), sm_no, merch.getMerchName());
					}
					
					logger.info("贵州广电计费_生成的支付二维码URL是："+payUrl);
				}
				
			}
			
		}
		catch (Exception e) 
		{
			logger.error(String.format("贵阳广电_智能卡号[%s]获取支付二维码时出现异常，异常信息为[%s]",sm_no, e));
		}
		
		param.setCode(returnCode);
		param.setOrderid(orderid);
		param.setPayurl(payUrl);
		
		return param;
	}
}
