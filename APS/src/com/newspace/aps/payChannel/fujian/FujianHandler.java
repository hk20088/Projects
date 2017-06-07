package com.newspace.aps.payChannel.fujian;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.newspace.aps.ReturnCode;
import com.newspace.aps.common.ConstantData;
import com.newspace.aps.dao.order.OrderDao;
import com.newspace.aps.model.order.Order;
import com.newspace.aps.payChannel.fujian.param.UserInfo;
import com.newspace.aps.payChannel.fujian.utils.FujianPayConfig;
import com.newspace.common.coder.Coder;
import com.newspace.common.utils.HttpClientUtils;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.StringUtils;
import com.newspace.common.utils.TwoTuple;

/**
 * @description 福建广电支付处理类， 用来生成支付请求URL
 * @author huqili
 * @date 2016年11月29日
 *
 */
@Service
public class FujianHandler {

	private static final Logger logger = Logger.getLogger(FujianHandler.class);
	
	@Resource
	private OrderDao orderDao;
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		
		Order order = new Order();
//		order.setOrderNo("1001"+RandomUtils.getRandom(4, 4));
		order.setOrderNo("test20161229001"); //测试查询单个订单的订单号
		order.setPayPoint(1001);
		order.setPayPointName("亲子VIP30天");
		order.setAmount(100);
		order.setExtraId("8350110805600539");
		order.setCreateTime(new Timestamp(System.currentTimeMillis()));
		
		FujianHandler handler = new FujianHandler();
//		String url = handler.getHttpsUrl(order).second; //缴款接口
		String url = handler.getQueryOrderUrl(order);  //查询单个订单接口
		
		System.out.println("得到的URL为：" + url);
		
		try 
		{
			String json = HttpClientUtils.post(url, null, null, "GBK", null, null);
			System.out.println("json="+json);
			logger.info(String.format("福建广电_缴费接口返回：%s", json));
		}
		catch (Exception e) 
		{
			logger.error("福建广电_根据智能卡号查询用户信息出现异常...",e);
		}
	}
	
	/**
	 * 生成请求缴款的URL
	 * @param order
	 * @return
	 */
	public TwoTuple<Integer, String> getHttpsUrl(Order order)
	{
		int returnCode = ReturnCode.SUCCESS.getCode();
		StringBuffer sb = new StringBuffer();
		
		logger.info(String.format("查询用户[%s]当天消费总额", order.getEndUser()));
		
		//先判断此用户是否已超过当天限额
		int quota = Integer.valueOf(ConstantData.FUJIAN_QUOTA_EVERYDAY); //每日限额
		int amount = orderDao.queryTotalMoney(order.getEndUser()) / 100;  //此用户当天已消费总额
		
		logger.info(String.format("用户[%s]当天消费总额为：[%s] 元", order.getEndUser(),amount));
		if(quota <= 0 || quota > amount)
		{
			//先根据智能卡号获取客户证号
			UserInfo userInfo = getUserInfo(order.getExtraId());
			logger.info(String.format("智能卡[%S]查询出的客户证号是：{%S]", order.getExtraId(),userInfo.getUSERNO()));
			
			//这里会出现userInfo.getRETURNCODE()的值为 000 但是userNo这空的情况 ，所以下面用 userNo来判断是否获取到客户证号
			if(!StringUtils.isNullOrEmpty(userInfo.getUSERNO()) && "000".equals(userInfo.getRETURNCODE()))
			{
				try 
				{
					sb.append(ConstantData.FUJIAN_BOSS_URL).append(FujianPayConfig.PAYORDER);
					sb.append("?").append(getParam(order,userInfo));
				} 
				catch (UnsupportedEncodingException e) 
				{
					logger.error("福建广电_生成请求缴费URL时出现异常...",e);
					returnCode = ReturnCode.ERROR.getCode();
				}
			}
			else
			{
				logger.error(String.format("福建广电_根据智能卡号[%s]未查询出对应的客户证号...", order.getExtraId()));
				returnCode = ReturnCode.CUSTID_ISNULL.getCode();
			}
		}
		else
		{
			logger.error(String.format("智能卡号为[%s]的用户[%s]今天消费已超过限额...", order.getExtraId(),order.getEndUser()));
			returnCode = ReturnCode.FUJIAN_MORE_LIMIT.getCode();
		}
		
		logger.info(String.format("封装好的支付URL是：[%S]", sb.toString()));
		
		return new TwoTuple<Integer, String>(returnCode, sb.toString());
	}
	
	/**
	 * 根据智能卡号查询用户信息
	 * @param sm_no
	 * @return
	 */
	private UserInfo getUserInfo(String sm_no)
	{
		String url = ConstantData.FUJIAN_BOSS_URL + FujianPayConfig.QUERYUSERBYSMCARD + "?cardNo=" + sm_no;
		UserInfo userInfo = new UserInfo();
		try 
		{
			//设置建立连接的超时时间为 5秒， 读取数据的超时时间为10 秒
			String json = HttpClientUtils.post(url, null, null, "GBK", 5000, 10000);
			logger.info(String.format("福建广电_根据智能卡号[%s]获取到的用户信息为：%s",sm_no, json));
			userInfo = JsonUtils.fromJson(json, UserInfo.class);
		}
		catch (Exception e) 
		{
			logger.error(String.format("福建广电_根据智能卡号[%s]查询用户信息出现异常：%s", sm_no,e));
		}
		return userInfo;
	}
	/**
	 * 生成请求参数，备注：对待签名字符串MD5摘要，并做BASE64编码
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private String getParam(Order order,UserInfo userInfo) throws UnsupportedEncodingException
	{
		String ordDate = new SimpleDateFormat("yyyyMMdd").format(new Date()); //订单日期
		BigDecimal payMent = new BigDecimal(new DecimalFormat("#.00").format(order.getAmount() / 100.0));  //金额，保留两位小数
		
		//生成参数字符串
		String data = getData(order,userInfo,ordDate,payMent);
		
		//生成MAC摘要
		String macValue = getMAC(order, userInfo, ordDate, payMent);
		
		//生成最终的参数(生成的macValue可能会带有换行符，URL编码前把它替换掉)
		String param = data + "&macValue=" + URLEncoder.encode(macValue.replaceAll("\\s*|\t|\r|\n", ""),"utf-8"); 
		return param;
	}
	
	/**
	 * 生成待签名字符串
	 * @param order
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private String getData(Order order,UserInfo userInfo, String ordDate,BigDecimal payMent) throws UnsupportedEncodingException
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("appVer=").append(FujianPayConfig.APPVER);
		sb.append("&devType=").append(FujianPayConfig.DEVTYPE);
		sb.append("&merchantID=").append(FujianPayConfig.MERCHANTID);
		sb.append("&ordNum=").append(order.getOrderNo());
		sb.append("&ordDate=").append(ordDate);
		sb.append("&ordTime=").append(new SimpleDateFormat("HHmmss").format(new Date()));
		sb.append("&userNo=").append(userInfo.getUSERNO()); //客户证号，根据智能卡号获得
		sb.append("&orgCode=").append(userInfo.getAREACODE());
		sb.append("&chrgCode=").append(order.getPayPoint());  //收费项目编码，商户自定义，同一商户不同收费项目需要不同编码
		sb.append("&chrgName=").append(Coder.encryptBASE64( StringUtils.isNullOrEmpty(order.getPayPointName()) ? "游戏道具".getBytes("GBK") : order.getPayPointName().getBytes("GBK"))); //收费项目名称，采用BASE64，如 “BOSS充值”、“套餐订购”、“水电费”等
		sb.append("&payMent=").append(payMent);  //金额，单位为 元； 格式 #.00
		sb.append("&orgUrl=").append(URLEncoder.encode(ConstantData.FUJIAN_REDIRECT_URL,"utf-8")); //支付完成跳转参数
		
		return sb.toString();
	}
	
	/**
	 * 生成缴款接口的MAC摘要
	 * @param order
	 * @param userNo
	 * @return
	 */
	private String getMAC(Order order,UserInfo userInfo,String ordDate,BigDecimal payMent)
	{
		return com.bs.security.compay.client.BsSignAndMac.crtPayOrderMac(FujianPayConfig.APPVER,FujianPayConfig.DEVTYPE , FujianPayConfig.MERCHANTID, order.getOrderNo(), ordDate,userInfo.getUSERNO(), userInfo.getAREACODE(), String.valueOf(order.getPayPoint()), payMent, FujianPayConfig.ORGKEY);
	}
	
	
	/**
	 * 生成查询单张订单的URL
	 * @param order
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private String getQueryOrderUrl(Order order) throws UnsupportedEncodingException
	{
		String ordDate = new SimpleDateFormat("yyyyMMdd").format(order.getCreateTime()); //订单日期
		String macValue = getQueryOrderMac(ordDate, order.getOrderNo());  //获取macValue地址
		
		StringBuffer sb = new StringBuffer();
		sb.append(ConstantData.FUJIAN_BOSS_URL).append(FujianPayConfig.QUERYORDER).append("?");
		sb.append("appVer=").append(FujianPayConfig.APPVER);
		sb.append("&merchantID=").append(FujianPayConfig.MERCHANTID);
		sb.append("&ordNum=").append(order.getOrderNo());
		sb.append("&ordDate=").append(ordDate);
		sb.append("&macValue=").append(URLEncoder.encode(macValue.replaceAll("\\s*|\t|\r|\n", ""),"utf-8"));
		
		return sb.toString();
	}
	
	/**
	 * 生成查询单张订单接口的MAC摘要
	 * @param ordDate
	 * @param ordNum
	 * @return
	 */
	private String getQueryOrderMac(String ordDate, String ordNum)
	{
		return com.bs.security.compay.client.BsSignAndMac.crtFindOrderMac(FujianPayConfig.APPVER, FujianPayConfig.MERCHANTID, ordNum, ordDate, FujianPayConfig.ORGKEY);
	}
	
}
