package com.newspace.aps.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.newspace.aps.ReturnCode;
import com.newspace.aps.dao.chanPayType.ChanPayTypeDao;
import com.newspace.aps.dao.device.DeviceDao;
import com.newspace.aps.dao.payKey.PayKeyDao;
import com.newspace.aps.model.ChanPayType;
import com.newspace.aps.model.Device;
import com.newspace.aps.model.Merchandise;
import com.newspace.aps.model.PayKey;
import com.newspace.aps.model.QueryOrderResp;
import com.newspace.aps.model.order.Order;
import com.newspace.aps.model.order.PayReqVo;
import com.newspace.aps.model.order.PayRespVo;
import com.newspace.aps.model.order.PayRespVo.PayRespData;
import com.newspace.aps.pay.PayConfig;
import com.newspace.aps.pay.PayHandler;
import com.newspace.aps.pay.PayUtils;
import com.newspace.aps.pay.param.PayVo;
import com.newspace.aps.payChannel.guizhou.GuiZhouHandler;
import com.newspace.aps.payChannel.guizhou.param.CustomerInfo;
import com.newspace.aps.service.PayServer;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.StringUtils;
import com.newspace.common.utils.TwoTuple;

/**
 * @description 计费服务，实现和计费相关的方法
 * @author huqili
 * @date 2016年9月12日
 * @since JDK1.7
 *
 */
@Service
public class PayServerImpl implements PayServer.Iface{

	private static final Logger logger = Logger.getLogger(PayServerImpl.class);
	
	@Resource
	private DeviceDao deviceDao;
	@Resource
	private ChanPayTypeDao chanPayTypeDao;
	@Resource
	private PayKeyDao payKeyDao;
	@Resource
	private PayUtils payUtils;
	@Resource
	private PayHandler payHandler;
	
	/**
	 * 计费方法 ，提供给客户端调用，实现计费功能。
	 */
	@Override
	public String pay(String json) throws TException 
	{
		logger.info(String.format("Enter pay method...the request parameter is:[%s]", json));
		
		PayRespData payRespData = null;
		PayRespVo respVo = new PayRespVo();
		
		PayReqVo reqVo = PayReqVo.getInstanceFromJson(json);
		int returnCode = ReturnCode.PARAM_ERROR.getCode();
		
		String key = null;
		
		if(reqVo != null && reqVo.isLegal(reqVo))
		{
			
			try 
			{

				//查询密钥信息
				PayKey payKey = payKeyDao.queryKeyInfo(reqVo.getKeyId());
				key = payKey == null ? null : payKey.getPrivateKey().replaceAll("\\s*|\t|\r|\n", "");
				
				//判断是否开启支付商品信息和获取用户余额功能,解析客户端当前执行步骤
				String flag = StringUtils.isNullOrEmpty(reqVo.getExtraPayInfo()) ? null :  payHandler.resolveInfo(reqVo.getExtraPayInfo()).getFlag();
				//如果flag != null 并且flag != ''并且flag != IOM，则进入获取用户的支付商品信息开关 和 用户的余额信息流程
				if(PayVo.isLegal(flag))
				{
					logger.info("This Request Need Query User's Information...");
					TwoTuple<Integer,PayRespData> balance = payHandler.getUserBalance(reqVo);
					returnCode = balance.first;
					payRespData = balance.second;
				}
				
				//如果payRespData == null，此时获取到支付商品信息开关为关闭状态(没有获取到用户余额信息，和开关信息)，直接进入支付流程
				if(payRespData == null) 
				{
					payRespData = new PayRespData();
					String _notifyURL = payKey == null ? null : payKey.getNotifyURL();
					logger.info("获取到的通知地址："+_notifyURL);
					
					// 校验参数是否被篡改，校验支付点金额是否正确(购买道具时校验支付点，购买商品时不作校验)
					TwoTuple<Integer, Merchandise> result = payUtils.verifyReqInfo(reqVo,key);
					returnCode = result.first;
					logger.info("校验支付点结果是："+returnCode);
					if(returnCode == ReturnCode.SUCCESS.getCode())
					{
						
						//判断ExtraPayInfo中的client是否有值，如果有值，解析判断是哪种支付方式，然后做不同的逻辑处理 
						if(!StringUtils.isNullOrEmpty(payHandler.resolveInfo(reqVo.getExtraPayInfo()).getClient()))
						{
							logger.info("进入ExtraPay...");
							payRespData = payHandler.extraPay(reqVo,result.second ,_notifyURL);
							returnCode = payRespData.getCode();
						}
						else
						{
							 // 1、查询此用户所属渠道，所属平台
							Device deviceInfo = deviceDao.queryDeviceInfo(reqVo.getDeviceId());
							logger.info("查询出的渠道信息为："+deviceInfo.getChannel());
							
							// 2、查询此渠道所支持的可用支付方式及优先级，查询条件：支付方式为正常，设备平台（因为有的支付方式只支持手机或TV）
							List<ChanPayType> list = chanPayTypeDao.queryChanPayInfo(deviceInfo); 
							for(int i = 0;i<list.size(); i++)
							{
								logger.info("获取到的支付方式是："+list.get(i).getPayCode());
							}
							
							logger.info("准备进入pay方法...");
							// 3、传入支付方式，优先级，支付点信息及订单信息，返回支付结果（豆浆机原理）
							TwoTuple<Integer, PayRespData> payResult = payHandler.pay(reqVo, list, result.second,deviceInfo,_notifyURL);
							returnCode = payResult.first;
							payRespData = payResult.second;
						}
					}
					else
					{
						logger.error(String.format("Request parameter is invalid,the parameter:[%s]", json));
					}
				}
			
			} 
			catch (Exception e) 
			{
				returnCode = ReturnCode.ERROR.getCode();
				logger.error(String.format("system error：%s", e));
			}
		}
	
		
		//封装响应参数
		payRespData = payRespData != null ? payRespData : new PayRespData();
		payRespData.setCode(returnCode);
		payRespData.setDesc(ReturnCode.getDesc(returnCode));
		String data = JsonUtils.toJson(payRespData);
		respVo.setData(data);
		
		if(returnCode == ReturnCode.SUCCESS.getCode())
		{
			respVo.setSign(PayUtils.getSignByRSAandMD5(data, key));
		}
		else
		{
			respVo.setSign(PayConfig.WRONG_SIGN);
		}
		
		String respStr = JsonUtils.toJsonWithExpose(respVo);
		logger.info(String.format("Response parameter：[%s]", respStr));
		return respStr;
	}
	
	/**
	 * 查询用户信息
	 */
	@Override
	public String userInfo(String ExtraId) throws TException 
	{
		logger.info("Enter userInfo method...");
		
		int returnCode = ReturnCode.SUCCESS.getCode();
		CustomerInfo respVo = new CustomerInfo();
		
		if(ExtraId != null)
		{
			try
			{
				respVo = GuiZhouHandler.getCustInfo(ExtraId);
			} 
			catch (Exception e)
			{
				logger.error("【userInfo】Catch a exception when request the Boss...",e);
				returnCode = ReturnCode.ERROR.getCode();
			}
			
		}
		else
		{
			logger.error("Request parameter is empty...");
			returnCode = ReturnCode.PARAM_ERROR.getCode();
		}
		
		respVo.setCode(returnCode);
		respVo.setDesc(ReturnCode.getDesc(returnCode));
		
		return JsonUtils.toJson(respVo);
	}


	/**
	 * 查询订单号状态接口
	 */
	@Override
	public String queryOrder(String OrderNo) throws TException 
	{
		logger.info(String.format("进入查询订单号状态接口...OrderNo为：%s",OrderNo ));
		ReturnCode returnCode = ReturnCode.SUCCESS;
		QueryOrderResp respVo = new QueryOrderResp();
		Order order = null;
		
		if(!StringUtils.isExistNullOrEmpty(OrderNo))
		{
			//根据订单号查询订单
			order = payUtils.queryByNo(PayConfig.FLAG_ORDERNO,OrderNo,Boolean.FALSE);
			if(order != null)
			{
				respVo.setStatus(order.getStatus());
			}
			else
			{
				logger.error(String.format("查询订单号状态接口，根据订单[%s]查询订单为空...", OrderNo));
				returnCode = ReturnCode.DATA_NOT_EXIST;
			}
				
		}
		else
		{
			returnCode = ReturnCode.PARAM_ERROR;
		}
		
		respVo.setCode(returnCode.getCode());
		respVo.setDesc(returnCode.getDesc());
		
		String str = JsonUtils.toJson(respVo);
		logger.info(String.format("查询订单状态接口，响应信息为：%s", str));
		return str;
	}
	
}
