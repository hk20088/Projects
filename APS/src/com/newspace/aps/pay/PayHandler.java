package com.newspace.aps.pay;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonProcessingException;
import org.springframework.stereotype.Service;

import com.newspace.aps.ReturnCode;
import com.newspace.aps.common.GeneralUtils;
import com.newspace.aps.model.ChanPayType;
import com.newspace.aps.model.Device;
import com.newspace.aps.model.Merchandise;
import com.newspace.aps.model.order.Order;
import com.newspace.aps.model.order.PayReqVo;
import com.newspace.aps.model.order.PayRespVo.PayRespData;
import com.newspace.aps.model.order.PayRespVo.PayRespData.ResultBody;
import com.newspace.aps.pay.param.PayResponVo;
import com.newspace.aps.pay.param.PayTypeEnum;
import com.newspace.aps.pay.param.PayVo;
import com.newspace.aps.pay.strategy.Context;
import com.newspace.aps.pay.strategy.IPayStrategy;
import com.newspace.aps.payChannel.coin.ACoinHandler;
import com.newspace.aps.payChannel.fujian.FujianHandler;
import com.newspace.aps.payChannel.guizhou.GuiZhouHandler;
import com.newspace.aps.payChannel.guizhou.param.CustomerInfo;
import com.newspace.aps.payChannel.pingyao.PingYaoHandler;
import com.newspace.aps.payChannel.pingyao.param.CustInfo;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.StringUtils;
import com.newspace.common.utils.TwoTuple;


/**
 * @description 支付操作类，用于实际封装支付相关数据
 * @author huqili
 * @date 2016年9月16日
 *
 */
@Service
public class PayHandler {
	
	private static final Logger logger = Logger.getLogger(PayHandler.class);
	
	@Resource
	private PayUtils payUtils;
	
	@Resource
	private ACoinHandler AcoinHander;
	
	@Resource
	private PingYaoHandler pingYaoHandler;
	
	@Resource
	private FujianHandler fujianHandler;
	
	/**
	 * 客户端第二次请求支付接口，根据ExtraPayInfo参数判断使用哪种支付方式支付，返回支付结果。
	 * 	1、先根据orderNo查询订单信息
	 *  2、解析ExtraPayInfo，根据解析结果做不同的支付逻辑处理
	 * @param reqVo
	 * @return
	 */
	public PayRespData extraPay(PayReqVo reqVo, Merchandise merch,String _notifyURL)
	{
		PayRespData payRespData = new PayRespData();
		Integer returnCode = ReturnCode.SUCCESS.getCode();
		String status = Order.STATUS_FAIL;
		boolean flag = true;  //true代表方法被调用时执行的是支付逻辑； 当根据参数ExtraPayInfo判断没有去支付时，将true改为false(例如平遥广电，有可能去下发验证码)
		
		Order order = payUtils.queryByNo(PayConfig.FLAG_ORDERNO,reqVo.getOrderNo(),Boolean.TRUE);
		if(order != null)
		{
			PayVo payVo = resolveInfo(reqVo.getExtraPayInfo());
			
			if(payVo.getClient().equals(Order.PAYTYPE_COIN))  //A豆
			{
				logger.info("Payment by ACoin...");
				
				returnCode = AcoinHander.pay(order);
				order.setPayType(Order.PAYTYPE_COIN);
			}
			else if(payVo.getClient().equals(Order.PAYTYPE_PINGYAO))  //平遥支付
			{
				logger.info("Payment by PingYao...");
				
				if(payVo.getFlag().equals(PayVo.FLAG_PAY))  //直接支付
				{
					returnCode = pingYaoHandler.pay(payVo, order);
					order.setPayType(Order.PAYTYPE_PINGYAO);
				}
				else if(payVo.getFlag().equals(PayVo.FLAG_REPEAT))  //下发验证码
				{
					flag = false;
					//下发短信验证码，并返回操作状态
					returnCode = pingYaoHandler.getCode(payVo,order);
				}
			}
			
			//如果支付成功 ：1、异步通知商户  2、处理用户资产信息  3、更新订单
			if(returnCode == ReturnCode.SUCCESS.getCode() && flag)
			{
				status = Order.STATUS_SUCCESS;
			
				//异步通知商户
				payUtils.notify(order, _notifyURL);
				
				//处理用户资产信息，比如给用户加VIP
				payUtils.doAsset(order,merch);
				
				//更新订单
				order.setStatus(status);
				payUtils.updateOrder(order);
			}
			
		}
		else
		{
			logger.error(String.format("根据订单号[%s]没有查到对应的订单信息", reqVo.getOrderNo()));
			returnCode = ReturnCode.DATA_NOT_EXIST.getCode();
		}
		
		payRespData.setCode(returnCode);
		payRespData.setStatus(status);
		return payRespData;
	}

	/**
	 * 解析ExtraPayInfo参数
	 * @param extraPayInfo
	 * @return
	 */
	public PayVo resolveInfo(String extraPayInfo)
	{
		PayVo payVo = new PayVo();
		
		try 
		{
			if(!StringUtils.isNullOrEmpty(extraPayInfo))
			{
				payVo = JsonUtils.fromJson(extraPayInfo, PayVo.class);
			}
		}
		catch (Exception e) 
		{
			logger.error("解析ExtraPayInfo时出现异常，ExtraPayInfo值为："+extraPayInfo);
		}
		
		return payVo;
	}
	
	/**
	 * 计费功能实现
	 * @param list
	 * 			list集合里储存的是按优先级存放的支付方式
	 * @param payPoint
	 * @return TwoTuple<first,second>
	 *         first - 计费结果
	 *         second - 响应参数 ResultBody
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	@SuppressWarnings("static-access")
	public  TwoTuple<Integer, PayRespData> pay(PayReqVo reqVo, List<ChanPayType> list,Merchandise merch,Device device,String _notifyURL)
	{
		int returnCode = ReturnCode.SUCCESS.getCode();
		String status = null;
		PayRespData payResp = new PayRespData();
		
		List<ResultBody> bodyList = new ArrayList<ResultBody>();
		
		if(list != null && !list.isEmpty())
		{
			//封装订单信息
			Order order = fillOrder(reqVo, device, merch,_notifyURL);
			
			try 
			{
				Context context = null;
				for(ChanPayType payType : list)
				{
					//通过反射拿到此支付渠道对应的类
					Class<?> payChannelClass = Class.forName(PayTypeEnum.getPackage(payType.getPayCode()));
					
					//实例化策略模式中的环境类，并调用对应的具体策略类中的方法
					context = new Context((IPayStrategy) ((Class<?>) payChannelClass).newInstance());
					PayResponVo payResult = context.pay(order, merch, bodyList, reqVo.getClientPackageName());
					
					returnCode = payResult.getReturnCode();
					status = payResult.getStatus();
					
					//如果支付成功，或无法支付（比如贵阳广电，不可连续购买包月业务），则退出循环
					if(order.STATUS_SUCCESS.equals(status) || ReturnCode.GUIZHOU_ALREADY_BUY.getCode() == returnCode)
					{
						break;
					}
					
				}
			} 
			catch (Exception e) {
				returnCode = ReturnCode.ERROR.getCode();
				logger.error("实例化支付渠道时出现异常...",e);
			}
			
			
			//返回订单号给客户端
			payResp.setOrderNo(order.getOrderNo());
			
			//记录订单
			order.setStatus(status);
			payUtils.saveOrder(order);
		}
		else
		{
			returnCode = ReturnCode.PAYTYPE_IS_NULL.getCode();
		}
		
		payResp.setStatus(status);
		payResp.setResultBody(bodyList);
		
		return new TwoTuple<Integer, PayRespData>(returnCode, payResp);
	}
	
	
	/**
	 * 封装响应参数ResultBody
	 * @param bodyList
	 * @param body
	 * @param payType
	 * @param payParam
	 * @return
	 */
	public static List<ResultBody> fillBodyList(List<ResultBody> bodyList,String payType,String payParam)
	{
		ResultBody body = new ResultBody();
		
		body.setPayType(payType);
		body.setPayParam(payParam);
		bodyList.add(body);
		
		return bodyList;
	}
	
	
	/**
	 * 封装二维码支付（SPQ）参数
	 * @param payUrl
	 * @param payCode
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getParam(String payUrl,String payCode)
	{
		StringBuffer sb = new StringBuffer();
		
		try
		{
			sb.append("CLIENT=").append(payCode);
			sb.append("|LINK=").append(payUrl == null ? "NULL" : URLEncoder.encode(payUrl, "utf-8"));
			sb.append("|NAME=").append(URLEncoder.encode(PayTypeEnum.getName(payCode),"utf-8"));
		}
		catch (UnsupportedEncodingException e) 
		{
			logger.error("对LINK或NAME的值进行编码时出现异常...",e);
		}
		
		return sb.toString();
	}
	
	/**
	 * 封装CTS的响应参数
	 * @param payType
	 * @return
	 */
	public static String getParamCTS(String payType)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("SDK=").append(payType);
		return sb.toString();
	}
	
	/**
	 * 封装MI的响应参数
	 * @param custInfo
	 * @return
	 */
	public static String getParamMI(String payType,CustInfo custInfo)
	{
		PayVo payVo = new PayVo();
		
		payVo.setClient(payType);
		if(custInfo != null)
		{
			payVo.setAccNo(custInfo.getAccNo());
			payVo.setStrOrderId(custInfo.getStrOrderId());
			payVo.setStrTxnTime(custInfo.getStrTxnTime());
		}
		
		return JsonUtils.toJson(payVo);
	}
	
	/**
	 * 封装ATET的响应参数
	 * @param payCode 支付方式分类
	 * @param balance 用户A豆余额
	 * @return
	 */
	public static String getParamATET(String payType)
	{
		PayVo payVo = new PayVo();
		payVo.setClient(payType);
		return JsonUtils.toJson(payVo);
	}
	
	/**
	* @Title: getParamIOM 
	* @Description: 封装用户余额信息 
	* @param balance
	* @return String
	* @author tangpan
	* @date 2016年12月2日 上午10:50:24
	* @throws
	 */
	public static String getParamIOM(Double balance)
	{
		PayVo payVo = new PayVo();
		payVo.setBalance(balance);
		payVo.setFlag(PayVo.FLAG_IOM);
		return JsonUtils.toJson(balance);
	}
	
	/**
	* @Title: getParamEXI 
	* @Description: 封装附加信息 
	* @param exi
	* @return String
	* @author tangpan
	* @date 2016年12月2日 上午10:53:54
	* @throws
	 */
	public static String getParamEXI(String exi)
	{
		PayVo payVo = new PayVo();
		payVo.setExtralInfo(exi);
		return JsonUtils.toJson(payVo);
	}

	/**
	 * 封装具体策略类的响应实体类
	 * @return
	 */
	public static PayResponVo getRespVo(int returnCode,String status)
	{
		PayResponVo respVo = new PayResponVo();
		
		respVo.setReturnCode(returnCode);
		respVo.setStatus(status);
		
		return respVo;
	}

	/**
	 * 封装订单对象
	 * @param reqVo
	 * @param device
	 * @param merch
	 * @return
	 */
	private Order fillOrder(PayReqVo reqVo,Device device,Merchandise merch,String _notifyURL)
	{
		Order order = new Order();
		
		order.setSystemDomain(GeneralUtils.getSysDomain.get(reqVo.getClientPackageName()));
		order.setKeyId(reqVo.getKeyId());
		order.setOrderNo(Order.generateOrderNo());
		order.setExternalOrderNo(reqVo.getExternalOrderNo());
		
		order.setEndUser(reqVo.getUserId());
		order.setExtraId(reqVo.getExternalId());
		
		order.setChannel(device.getChannel());
		order.setDevice(reqVo.getDeviceId());
		order.setTypeCode(device.getTypeCode());
		order.setPlatType(device.getPlatType());
		
		order.setNotifyURL(_notifyURL);
		
		//用户在K12大厅购买课程或年级时，请求参数 PrivateInfo为空。 这里将ExtraPayInfo的值赋给PrivateInfo， 方便添加用户资产信息时解析 ExtraPayInfo，做不同的判断。
		order.setPrivateInfo(StringUtils.isNullOrEmpty(reqVo.getPrivateInfo()) ? reqVo.getExtraPayInfo() : reqVo.getPrivateInfo());
		order.setMerchType(merch.getType());  //商品类型
		order.setPayPoint(Integer.valueOf(merch.getMerchId() == null ? "0" : merch.getMerchId()));
		order.setPayPointName(StringUtils.isNullOrEmpty(merch.getMerchName()) ? "游戏道具" : merch.getMerchName());
		order.setPayPointMoney(merch.getPrice());
		order.setGame(merch.getExtraId());
		order.setGameName(merch.getGoodsName());
		
		order.setCount(reqVo.getCount());
		order.setAmount(reqVo.getAmount());
		
		order.setVersionCode(reqVo.getVersionCode());
		
		return order;
	}
	
	
	/**
	* @Title: getUserBalance 
	* @Description: 根据用户Id、ExternalId和ClientPackageName 获取支付商品信息开关和 用户的余额信息
	* @param reqVo
	* @return TwoTuple<Integer,PayRespData>
	* @author tangpan
	* @date 2016年12月2日 下午3:32:02
	 */
	public TwoTuple<Integer,PayRespData> getUserBalance(PayReqVo reqVo) 
	{
		PayRespData payRespData = null;
		int returnCode = ReturnCode.SUCCESS.getCode();
		//1.根据用户Id 和 packageName  获取到商品详情的开关 . 默认为关闭
		Boolean flag = Boolean.FALSE;
		try
		{
			flag = payUtils.getMerchInfoSwitch(reqVo.getUserId(), reqVo.getClientPackageName());
		}
		catch (Exception e)
		{
			logger.error(String.format("获取支付商品信息开关失败:[%s]",e));
			returnCode = ReturnCode.ERROR.getCode();
		}
		
		//2.根据开关判断是否查询用户余额信息
		PayVo iom = null;
		if(flag)
		{
			try
			{
				//解析渠道类型
				PayVo payVo = resolveInfo(reqVo.getExtraPayInfo());
				CustomerInfo customer = new CustomerInfo();
				iom = new PayVo();
				
				 //获取贵州广电用户的账户余额
				if(payVo != null && PayConfig.GD_GUIZHOU.equals(payVo.getFlag()))
				{
					customer = GuiZhouHandler.getCustInfo(reqVo.getExternalId());
				}
				 //封装账户余额
				iom.setBalance(customer != null ? customer.getAmount() : 0);
				iom.setFlag(PayVo.FLAG_IOM);
				
				List<ResultBody> resultBody = new ArrayList<ResultBody>();
				ResultBody iomBody = new ResultBody();
				iomBody.setPayType(PayVo.FLAG_IOM);
				iomBody.setPayParam(JsonUtils.toJson(iom));
				resultBody.add(iomBody);
				//判断余额是否足够
				if(reqVo.getAmount() > iom.getBalance() * 100)
				{
					ResultBody exiBody = new ResultBody();
					exiBody.setPayType(PayVo.FLAG_EXI);
					exiBody.setPayParam("账户余额不足,请选择其他支付方式支付");
					resultBody.add(exiBody);
				}
				payRespData = new PayRespData();
				payRespData.setResultBody(resultBody);
				payRespData.setStatus(Order.STATUS_WAIT);
			}
			catch (Exception e)
			{
				logger.error(String.format("获取用户余额失败:[%s]",e));
				returnCode = ReturnCode.ERROR.getCode();
			}
		}
		
		TwoTuple<Integer,PayRespData> twoTuple = new TwoTuple<Integer,PayRespData>(returnCode, payRespData);
		return twoTuple;
	}
}
