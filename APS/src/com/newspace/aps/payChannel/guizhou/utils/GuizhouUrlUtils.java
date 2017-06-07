package com.newspace.aps.payChannel.guizhou.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

import com.newspace.aps.common.ConstantData;
import com.newspace.aps.payChannel.guizhou.param.OrderPayReqCon;
import com.newspace.aps.payChannel.guizhou.param.OrderReqCon;
import com.newspace.aps.payChannel.guizhou.param.OrderPayReqCon.CustInfo;
import com.newspace.aps.payChannel.guizhou.param.OrderPayReqCon.OrderInfo;
import com.newspace.aps.payChannel.guizhou.param.OrderPayReqCon.OrderInfo.ProductList;
import com.newspace.aps.payChannel.guizhou.param.OrderReqCon.OrderParams;
import com.newspace.common.utils.JsonUtils;


/**
 * @description 贵州广电生成请求URL的工具类，用来封装各个请求的URl
 * @author Huqili
 *
 */
public class GuizhouUrlUtils {

	
	private static final Logger logger = Logger.getLogger(GuizhouUrlUtils.class);
	
	/**
	 * 发送get请求，响应字符串
	 * @param url
	 * @return
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public static String httpGet(String url)
	{
		String respStr = null;
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(url);
		
		try 
		{
			client.executeMethod(method);
			respStr = method.getResponseBodyAsString();
		} 
		catch (Exception e) {
			logger.error(String.format("贵州广电计费_get请求失败！请求的URL是：%s，捕获的异常是：%s", url,e));
		}
		finally
		{
			//释放资源
			method.releaseConnection();
		}
		
		return respStr;
	}
	
	
	/**
	 * 生成获取城市代码的请求URL
	 * @return
	 */
	public static String getCityCodeUrl(String sm_no)
	{
		StringBuffer sb = new StringBuffer(ConstantData.GUIZHOU_BOSS_URL);
		sb.append(GuizhouPayConfig.QUERY_CITYCODE_URL).append("?METHOD=queryCityByKeyno&version=1");
		sb.append("&clientcode=").append(GuizhouPayConfig.CLIENTCODE_QUERY);
		sb.append("&clientpwd=").append(GuizhouPayConfig.CLIENTPWD_QUERY);
		sb.append("&requestid=").append(GuizhouPayConfig.getRequestid(true));
		sb.append("&requestContent=").append("{\"keyno\":\"").append(sm_no).append("\"}");
		
		return sb.toString();
	}
	
	
	/**
	 * 生成获取客户信息的请求RUL
	 * @param sm_no
	 * @param citycode
	 * @return
	 */
	public static String getCustInfoUrl(String sm_no,String citycode)
	{
		StringBuffer sb = new StringBuffer(ConstantData.GUIZHOU_BOSS_URL);
		sb.append(GuizhouPayConfig.QUERY_CUSTINFO_URL).append("?METHOD=uniForward&version=1");
		sb.append("&clientcode=").append(GuizhouPayConfig.CLIENTCODE_QUERY);
		sb.append("&clientpwd=").append(GuizhouPayConfig.CLIENTPWD_QUERY);
		sb.append("&citycode=").append(citycode);
		sb.append("&servicecode=QUE_CUSTINFO");
		sb.append("&requestid=").append(GuizhouPayConfig.getRequestid(true));
		sb.append("&requestContent=").append("{\"city\":\"").append(citycode).append("\",\"identifier\":\"").append(sm_no).append("\",\"identifierType\":\"3\"}");
		
		return sb.toString();
	}
	
	
	/**
	 * 生成查询用户余额的URL地址
	 * @param citycode
	 * @param custid
	 * @return
	 */
	public static String getQueryBalanceUrl(String citycode,String custid)
	{
		StringBuffer sb = new StringBuffer(ConstantData.GUIZHOU_BOSS_URL);
		sb.append(GuizhouPayConfig.QUERY_BALANCE_URL).append("?METHOD=uniForward&version=1");
		sb.append("&citycode=").append(citycode);
		sb.append("&clientcode=").append(GuizhouPayConfig.CLIENTCODE_QUERY);
		sb.append("&clientpwd=").append(GuizhouPayConfig.CLIENTPWD_QUERY);
		sb.append("&servicecode=QUE_FEEBOOKINFO");
		sb.append("&requestid=").append(GuizhouPayConfig.getRequestid(true));
		sb.append("&requestContent=").append("{\"custid\":\"").append(custid).append("\"}");
		
		return sb.toString();
		
	}
	
	/**
	 * 生成钱袋支付的URL(单次订购)
	 * @param sm_no
	 * @param payPoint
	 * @param name
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getPayUrl(String sm_no,double fee,String payPoint,String name) throws UnsupportedEncodingException
	{
		StringBuffer sb = new StringBuffer(ConstantData.GUIZHOU_BOSS_URL);
		sb.append(GuizhouPayConfig.SINGLE_PAY_URL).append("?METHOD=uniForward&opcode=Z03");
		sb.append("&syscode=").append(GuizhouPayConfig.SYSCODE);
		sb.append("&sm_no=").append(sm_no);
		sb.append("&pcode=").append(GuizhouPayConfig.CAID);
		sb.append("&programid=").append(payPoint);
		sb.append("&programname=").append(URLEncoder.encode(URLEncoder.encode(name,"utf-8"),"utf-8"));
		sb.append("&programprovider=").append(GuizhouPayConfig.PROGRAMPROVIDER);
		sb.append("&sums=").append(fee);  //金额  单位元
		sb.append("&flag=1");
		
		return sb.toString();
	}
	
	/**
	 * 生成钱袋支付的URL(包月 /套餐)
	 * @param flag
	 * 		month: 包月；
	 * 		half_year: 半年套餐
	 * 		one_year:  一年套餐 
	 * 		备注：包月 和套餐的产品代码不同
	 * @param sm_no
	 * @param fee
	 * @return
	 */
	public static String getXPayUrl(String flag,String sm_no,double fee,String packageName)
	{
		StringBuffer sb = new StringBuffer(ConstantData.GUIZHOU_BOSS_URL);
		sb.append(GuizhouPayConfig.MONTH_PAY_URL).append("?METHOD=uniForward&opcode=Z04");
		sb.append("&syscode=").append(GuizhouPayConfig.SYSCODE);
		sb.append("&sm_no=").append(sm_no);
		sb.append("&keyno=").append(sm_no);
		sb.append("&permark=").append("1");
		
		if(flag.equals(GuizhouPayConfig.MONTH))  //包月
		{
			sb.append("&cycle=").append(1);
			sb.append("&cycleUnit=").append("1");
			sb.append("&pcodes=null");
			sb.append("&pcode=").append(GuizhouPayConfig.getMonth(packageName));
		}
		else if(flag.equals(GuizhouPayConfig.HALF_YEAR))  //半年套餐
		{
			sb.append("&yxcycle=1").append("&cycleUnit=1").append("&pcodes=null");
			sb.append("&yxcode=").append(GuizhouPayConfig.getHalfYear(packageName));
			
		}
		else if(flag.equals(GuizhouPayConfig.ONE_YEAR))  //一年套餐
		{
			sb.append("&yxcycle=1").append("&cycleUnit=1").append("&pcodes=null"); //购买套餐时必填
			sb.append("&yxcode=").append(GuizhouPayConfig.getYear(packageName));
		}
		
		sb.append("&sums=").append(fee);  //金额  单位 元
		
		return sb.toString();
	}

	
	/**
	 * 生成订单订购的URL地址(生成订单号)
	 * @param flag
	 * 		 flag = once 单次点播
	 *   	 flag = month  包月
	 *   	 flag = half_year 半年套餐
	 *       flag = one_year  一年套餐
	 * @param citycode
	 * @param custid
	 * @param sm_no
	 * @param fee
	 * @param paypoint
	 * @param name
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getOrderNoUrl(String flag,String citycode,String custid,String sm_no,String fee,String paypoint,String name,String packageName) throws UnsupportedEncodingException
	{
		StringBuffer  sb = new StringBuffer(ConstantData.GUIZHOU_BOSS_URL);
		sb.append(GuizhouPayConfig.ORDER_NO_URL).append("?METHOD=uniForward&version=1");
		sb.append("&citycode=").append(citycode);
		sb.append("&clientcode=").append(GuizhouPayConfig.CLIENTCODE_QUERY);
		sb.append("&clientpwd=").append(GuizhouPayConfig.CLIENTPWD_QUERY);
		sb.append("&servicecode=BIZ_PREPROCESS");
		sb.append("&requestid=").append(GuizhouPayConfig.getRequestid(Boolean.TRUE));
		sb.append("&requestContent=").append(fillOrderReqCon(flag,custid,sm_no,fee,paypoint,name,packageName));
		
		return sb.toString();
	}
	
	
	
	
	/**
	 * 生成订单确认的请求URL
	 * @param citycode
	 * @param orderid
	 * @param paycode
	 * @param payway
	 * @param status
	 * @return
	 */
	public static String getConfirmOrderUrl(String citycode,String orderid,String paycode,String payway,String status)
	{
		StringBuffer  sb = new StringBuffer(ConstantData.GUIZHOU_BOSS_URL);
		sb.append(GuizhouPayConfig.ORDER_CONFIRM_URL).append("?METHOD=uniForward&version=1");
		sb.append("&clientcode=").append(GuizhouPayConfig.CLIENTCODE_QUERY);
		sb.append("&clientpwd=").append(GuizhouPayConfig.CLIENTPWD_QUERY);
		sb.append("&citycode=").append(citycode);
		sb.append("&servicecode=BIZ_ORDERCOMMIT");
		sb.append("&requestid=").append(GuizhouPayConfig.getRequestid(Boolean.TRUE));
		sb.append("&requestContent=").append("{\"orderid\":\"").append(orderid).append("\",\"paycode\":\"").append(paycode).append("\",");
		sb.append("\"payway\":\"").append(payway).append("\",\"status\":\"").append(status).append("\"}");
		
		return sb.toString();
	}
	
	//生成订单支付请求URL(请求此URL，直接返回广电封装好的二维码支付页面 )
	public static String getOrderPayUrl(String orderNum,String cityCode,String custid,String custName,String fee,String sm_no,String productName) throws UnsupportedEncodingException
	{
		StringBuffer  sb = new StringBuffer(ConstantData.GUIZHOU_QRCODE_PAY_URL);
		sb.append(GuizhouPayConfig.ORDER_PAY_URL).append("?version=1");
		sb.append("&citycode=").append(cityCode);
		sb.append("&clientcode=").append(GuizhouPayConfig.CLIENTCODE_PAY);
		sb.append("&clientpwd=").append(GuizhouPayConfig.CLIENTPWD_PAY);
		sb.append("&servicecode=Pay");
		sb.append("&requestid=").append(GuizhouPayConfig.getRequestid(Boolean.FALSE));
		sb.append("&requestContent=").append(fillOrderpayReqCon(orderNum, cityCode, custid, custName, fee, sm_no, productName));
		
		return sb.toString();
	}
	
	/**
	 * 生成支付宝二维码
	 * @param orderNum
	 * @param cityCode
	 * @param custid
	 * @param custName
	 * @param fee
	 * @param sm_no
	 * @param productName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getAlipayUrl(String orderNum,String cityCode,String custid,String custName,String fee,String sm_no,String productName) throws UnsupportedEncodingException
	{
		StringBuffer sb = new StringBuffer(ConstantData.GUIZHOU_QRCODE_PAY_URL);
		sb.append(GuizhouPayConfig.ALIPAY_QR_URL).append("?version=1");
		sb.append("&citycode=").append(cityCode);
		sb.append("&clientcode=").append(GuizhouPayConfig.CLIENTCODE_PAY);
		sb.append("&clientpwd=").append(GuizhouPayConfig.CLIENTPWD_PAY);
		sb.append("&servicecode=Pay");
		sb.append("&requestid=").append(GuizhouPayConfig.getRequestid(Boolean.FALSE));
		sb.append("&requestContent=").append(fillOrderpayReqCon(orderNum, cityCode, custid, custName, fee, sm_no, productName));
		
		return sb.toString();
	}
	
	
	/**
	 * 生成微信二维码
	 * @param orderNum
	 * @param cityCode
	 * @param custid
	 * @param custName
	 * @param fee
	 * @param sm_no
	 * @param productName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getWechatUrl(String orderNum,String cityCode,String custid,String custName,String fee,String sm_no,String productName) throws UnsupportedEncodingException
	{
		StringBuffer sb = new StringBuffer(ConstantData.GUIZHOU_QRCODE_PAY_URL);
		sb.append(GuizhouPayConfig.WECHAT_QR_URL).append("?version=1");
		sb.append("&citycode=").append(cityCode);
		sb.append("&clientcode=").append(GuizhouPayConfig.CLIENTCODE_PAY);
		sb.append("&clientpwd=").append(GuizhouPayConfig.CLIENTPWD_PAY);
		sb.append("&servicecode=Pay");
		sb.append("&requestid=").append(GuizhouPayConfig.getRequestid(Boolean.FALSE));
		sb.append("&requestContent=").append(fillOrderpayReqCon(orderNum, cityCode, custid, custName, fee, sm_no, productName));
		
		return sb.toString();
	}
	
	/**
	 * 封装订单订购请求中的参数requestContent
	 * @param flag
	 * 		 flag = once 单次点播
	 *   	 flag = month  包月
	 *   	 flag = half_year 半年套餐
	 *       flag = one_year  一年套餐
	 * @param custid
	 * @param sm_no
	 * @param fee
	 * @param paypoint
	 * @param paypointname
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String fillOrderReqCon(String flag,String custid,String sm_no,String fee,String paypoint,String paypointname,String packageName) throws UnsupportedEncodingException
	{
		List<OrderReqCon.OrderParams> list = new ArrayList<OrderParams>();
		OrderReqCon content = new OrderReqCon();
		OrderReqCon.OrderParams params = new OrderParams();
		
		content.setCustid(custid);
		
		content.setFees(fee);  //点播金额，单位为 元
		content.setIscrtorder("Y");  //是否生成订单 “Y”代表是，“N”代表否
		content.setGdno("");  //广电号，默认为空
		content.setGdnoid("");  //广电号ID，默认为空
		content.setUserid(custid);
		
		if(flag.equals(GuizhouPayConfig.ONCE))  //单次订购
		{
			content.setOrder_type("4");
			
			//订购类型   当ordertype=0时，代表包月购买；当order_type=1时，代表套餐购买； 当order_type=2时，代表充值；当order_type=4时，代表单次订购
			params.setOrdertype("4");  
			//商品编码，当type=0时，传包月产品代码；当type = 1时，传套餐代码；  当tyoe=4时，传栏目ID；
			params.setSalescode(GuizhouPayConfig.CAID);  
			params.setCount("1");  //订购周期
			params.setUnit("");  //订购周期 当ordertype=1时必填
			params.setIspostpone("Y");  //是否可顺延
		}
		else if(flag.equals(GuizhouPayConfig.MONTH))  //包月
		{
			content.setOrder_type("0");
			params.setOrdertype("0");  
			params.setSalescode(GuizhouPayConfig.getMonth(packageName));  
		}
		else if(flag.equals(GuizhouPayConfig.HALF_YEAR))  //半年套餐
		{
			content.setOrder_type("1");
			params.setOrdertype("1");  
			params.setSalescode(GuizhouPayConfig.getHalfYear(packageName));
			params.setPcodestr("");
		}
		else if(flag.equals(GuizhouPayConfig.ONE_YEAR))  //一年套餐
		{
			
			content.setOrder_type("1");
			params.setOrdertype("1");  
			params.setSalescode(GuizhouPayConfig.getYear(packageName));
			params.setPcodestr("");
		}

		//下面这三个参数，在购买套餐时必填
		params.setCount("1");  //订购周期
		params.setUnit("1");  //订购周期单位  当ordertype=1时必填。0：天；1：月；2：年
		params.setIspostpone("Y");  //是否可顺延
		
		params.setKeyno(sm_no); //智能卡号
		params.setPermark("1");  //业务类型，默认为1
		params.setFlag("1");  //是否允许重复订购  0 代表不能，1代表可重复订购
		params.setProgramid(paypoint); //媒资ID，可填支付点
		params.setProgramname(URLEncoder.encode(URLEncoder.encode(paypointname,"utf-8"),"utf-8")); //商品名称，需进行两次URLEncode编码
		params.setProgramprovider(GuizhouPayConfig.PROGRAMPROVIDER);  //媒资提供商，固定值
		list.add(params);
		
		content.setOrderparams(list);
		
		return JsonUtils.toJson(content);
	}
	
	//封装订单支付请求参数RequestContent
		private static String fillOrderpayReqCon(String orderNum,String cityCode,String custid,String custName,String fee,String sm_no,String productName) throws UnsupportedEncodingException
		{
			OrderPayReqCon content = new OrderPayReqCon();
			OrderInfo orderInfo = new OrderInfo();
			CustInfo custInfo = new CustInfo();
			ProductList productList = new ProductList();
			List<ProductList> list = new ArrayList<ProductList>();
			
			productList.setCount("1");
			productList.setFee(fee);
			productList.setKeyno(sm_no);
			productList.setProductName(URLEncoder.encode(URLEncoder.encode(productName,"utf-8"),"utf-8"));
			list.add(productList);
			
			orderInfo.setProductList(list);
			orderInfo.setOrderNo(orderNum);
			
			custInfo.setCity(cityCode);
			custInfo.setCustid(custid);
			//查询用户信息时，用户姓名已经编码过一次，这里只需要再编码一次即可。
			custInfo.setCustname(URLEncoder.encode(custName,"utf-8"));
			
			content.setCustInfo(custInfo);
			content.setOrderInfo(orderInfo);
			content.setNoticeAction(ConstantData.GUIZHOU_NOTIFY_URL);
			content.setOrderNum(orderNum);
			content.setRedirectUrl(ConstantData.GUIZHOU_REDIRECT_URL);
			content.setTotalFee(fee);
			
			return JsonUtils.toJson(content);
		}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		
		//查询城市代码
//		System.out.println(getCityCodeUrl("8851004071598812"));
		
		//查询用户信息
//		System.out.println(getCustInfoUrl("8851004071598812", "5201"));
		
		//查询余额
//		System.out.println(getQueryBalanceUrl("5201", "K10001047601"));
		
		//钱袋扣费
//		System.out.println(getXPayUrl(GuizhouPayConfig.MONTH, "8851004071598812", 29.00));
		
		//扫码支付请求订单号
//		System.out.println(getOrderNoUrl(GuizhouPayConfig.ONE_YEAR, "5201", "K10001047601", "8851004071598812", "299", "20160426155646721104", "VIP365天"));
		
		//请求扫码支付页面
//		System.out.println(getOrderPayUrl("100000412624", "5201", "K10001047601", "卜范煜", "299", "8851004071598812", "VIP365天"));
		
		//请求支付宝二维码页面 
//		System.out.println(getAlipayUrl("100000434867", "5201", "K10001047601", URLEncoder.encode("卜范煜","utf-8"), "299", "8851004071598812", "VIP365天"));
		
		//微信二维码页面 
		System.out.println(getWechatUrl("100000434867", "5201", "K10001047601", URLEncoder.encode("卜范煜","utf-8"), "99", "8851004071598812", "极品飞车"));
	}
	
}
