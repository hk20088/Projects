package com.newspace.aps.payChannel.pingyao.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import com.newspace.aps.ReturnCode;
import com.newspace.aps.pay.param.PayVo;
import com.newspace.aps.payChannel.pingyao.param.CustInfo;
import com.newspace.aps.payChannel.pingyao.param.PayRespVo;

/**
 * @description 平遥计费工具类
 * @author Huqili
 * @date 2016年11月14日
 *
 */
public class PingyaoPayUtils {

	private static final Logger logger = Logger.getLogger(PingyaoPayUtils.class);
	
	/**
	 * 请求6.6下发短信验证码接口
	 * @param codeRespVo
	 * @param accNo
	 * @param mobile
	 * @param payFee
	 * @return
	 * @throws DocumentException 
	 */
	public static int getCode(PayVo payVo, String accNo,String payFee) throws DocumentException
	{
	
		HashMap<String, String> result = new HashMap<String, String>();
		int returnCode = ReturnCode.ERROR.getCode();
		
		//获取soap报文
		String soapXml = PingyaoSoapUtils.getCodeXml(accNo, payVo.getMobile(), payFee,payVo.getStrOrderId(),payVo.getStrTxnTime());
		
		//发送soap请求
		String resultXml = PingyaoSoapUtils.soap(soapXml);
		
		//解析响应报文
		result = PingyaoSoapUtils.parseXml(resultXml); 
		logger.info("平遥计费_获取短信验证码接口响应参数是："+result);
		
		if(result != null)
		{
			returnCode = Integer.parseInt(result.get("RETURNCODE"));
		}
		
		return returnCode;
	}
	
	/**
	 * 请求5.3接口，获取custId
	 * @param sm_no
	 * @return
	 * @throws DocumentException 
	 */
	public static String getCustId(String sm_no) throws DocumentException
	{

		String custId = null;
		HashMap<String, String> result = new HashMap<String, String>();
		
		//获取soap报文
		String soapXml = PingyaoSoapUtils.queryServInfoXml(sm_no);
		
		//发送soap请求
		String resultXml = PingyaoSoapUtils.soap(soapXml);
		
		//解析响应报文
		result = PingyaoSoapUtils.parseXml(resultXml); 
		logger.info("平遥广电_用户信息查询接口响应参数是："+result);
		
		if(result != null)
		{
			if(result.get("RETURNCODE").equals("0"))
			{
				custId = result.get("CUSTID");
			}
		}
		
		return custId;
	}
	
	/**
	 * 请求5.1接口，获取客户信息
	 * @param respVo
	 * @param custId
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static CustInfo getCustInfo(CustInfo respVo, String custId) throws UnsupportedEncodingException
	{
		HashMap<String, String> result = new HashMap<String, String>();
		
		//获取soap报文
		String soapXml = PingyaoSoapUtils.queryCustInfoXml(custId);
		
		//发送soap请求
		String resultXml = PingyaoSoapUtils.soap(soapXml);
		
		//解析响应报文
		result = PingyaoSoapUtils.parseXml(resultXml); 
		logger.info("平遥广电_客户信息查询接口响应参数是："+result);
		
		if(result != null)
		{
			if(result.get("RETURNCODE").equals("0"))
			{
				respVo.setCustname(URLEncoder.encode(result.get("CUSTNAME"),"utf-8"));
				respVo.setCustaddr(URLEncoder.encode(result.get("HOMEADDR"),"utf-8"));
				respVo.setMobile(result.get("MPHONE"));
			}
		}
		
		return respVo;
	}
	
	/**
	 * 请求6.8接口获取用户已绑定的银行卡
	 * @param custId
	 * @return
	 */
	public static String getAccNo(String custId)
	{
		
		String accNo = null;
		HashMap<String, String> result = new HashMap<String, String>();
		HashMap<String, String> bankInfo = new HashMap<String, String>();
		
		//获取soap报文
		String soapXml = PingyaoSoapUtils.queryAccNoXml(custId);
		
		//发送soap请求
		String resultXml = PingyaoSoapUtils.soap(soapXml);
		
		//解析响应报文(备注：由于resultXml的外层参数和集合custBankInfo里的参数名相同引起冲突 ，所以这里只能拿到外层参数的值)
		result = PingyaoSoapUtils.parseXml(resultXml);
		logger.info("平遥广电_查询客户绑定的银行卡号接口外层参数是："+result);

		if(result != null)
		{
			if(result.get("RETURNCODE").equals("0"))  //再次向下解析，拿到集合custBankInfo里的银行卡信息
			{
				
				bankInfo = PingyaoSoapUtils.parseSoapXml(resultXml);
				logger.info("平遥广电_查询客户绑定的银行卡号接口集合custBankInfo的参数是："+result);
				if(bankInfo != null)
				{
					accNo = bankInfo.get("STRBANKCARD");
				}
			}
		}
		
		return accNo;
	}
	
	/**
	 * 平遥广电计费接口
	 * @param payFee  单位为 元
	 * @param accNo
	 * @param smsCode
	 * @param strOrderId
	 * @param strTxnTime
	 * @return
	 */
	public static PayRespVo pay(String payFee,String accNo,String smsCode,String strOrderId,String strTxnTime)
	{
		PayRespVo vo = new PayRespVo();
		
		HashMap<String, String> result = new HashMap<String, String>();
		
		//获取soap报文
		String soapXml = PingyaoSoapUtils.payXml(payFee, accNo, smsCode, strOrderId, strTxnTime);
		
		//发送soap请求
		String resultXml = PingyaoSoapUtils.soap(soapXml);
		
		//解析响应报文
		result = PingyaoSoapUtils.parseXml(resultXml); 
		logger.info("平遥广电_计费接口响应参数是："+result);
		
		if(result != null)
		{
			vo.setReturnCode(result.get("RETURNCODE"));
			vo.setReturnmsg(result.get("RETURNMSG"));
			vo.setStrOrigQryId(result.get("STRORIGQRYID"));
		}
		
		return vo;
	}
	                              
	
	public static void main(String[] args) throws DocumentException {
		//获取银行卡号
//		System.out.println(getAccNo("1000009710"));
		
		//查询客户信息
//		GetCodeRespVo respVo = new GetCodeRespVo();
//		System.out.println(getCustInfo(respVo, "1000009710"));
		
		//获取短信验证码
//		System.out.println(getCode(respVo, "6230513581800000040", "13152815892", 100));
		
		//支付
		System.out.println(pay("1", "6230513581800000040", "791231", "1461408085486", "20160423184126"));
	}
	
}
