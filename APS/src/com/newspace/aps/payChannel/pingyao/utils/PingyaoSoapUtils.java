package com.newspace.aps.payChannel.pingyao.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

import com.newspace.aps.common.ConstantData;

/**
 * @description 处理soap工具类
 * @author Huqili
 * @date 2016年4月22日
 *
 */
public class PingyaoSoapUtils {

	private static final Logger logger = Logger.getLogger(PingyaoSoapUtils.class);
	
	/**
	 * 生成查询用户信息soap报文
	 * @param sm_no
	 * @return
	 * @throws DocumentException
	 */
	public static String queryServInfoXml(String sm_no)
	{
		try 
		{
			Document document = DocumentHelper .parseText(
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns=\"http://wsserver.upcome.com\"></soap:Envelope>");
	
			Element root = document.getRootElement();
			
			//包头
			 root.addElement("soap:Header");
			
			//包体
			Element body = root.addElement("soap:Body");
			
			Element content = body.addElement("QueryServInfo");
			
			Element wsbase = content.addElement("wsBase");
			wsbase.addElement("version").addText(PingyaoPayConfig.VERSION); 
			wsbase.addElement("stubId").addText(PingyaoPayConfig.getStubId());
			wsbase.addElement("agentCode").addText(PingyaoPayConfig.AGENT_CODE);
			
			Element method = content.addElement("cusServIn");
			method.addElement("smartcard").addText(sm_no);
		
			String xml = document.asXML();
			logger.info("平遥计费_查询用户信息接口生成的soap报文是："+xml);
			
			return xml;
		}
		catch (Exception e) {
			logger.error("平遥计费_生成查询用户信息的soap报文时出现异常！",e);
			return null;
		}
		
		
	}
	
	/**
	 * 生成查询客户信息的soap报文
	 * @param custId
	 * @return
	 */
	public static String queryCustInfoXml(String custId)
	{
		try 
		{
			Document document = DocumentHelper .parseText(
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns=\"http://wsserver.upcome.com\"></soap:Envelope>");

			Element root = document.getRootElement();
			
			//包头
			 root.addElement("soap:Header");
			
			//包体
			Element body = root.addElement("soap:Body");
			
			Element content = body.addElement("QueryCustInfo");
			
			Element wsbase = content.addElement("wsBase");
			wsbase.addElement("version").addText(PingyaoPayConfig.VERSION); 
			wsbase.addElement("stubId").addText(PingyaoPayConfig.getStubId());
			wsbase.addElement("agentCode").addText(PingyaoPayConfig.AGENT_CODE);
			
			Element method = content.addElement("cusCustIn");
			method.addElement("custId").addText(custId);
			
			String xml = document.asXML();
			logger.info("平遥计费_查询客户信息接口生成的soap报文是："+xml);
			
			return xml;
		} 
		catch (Exception e) {
			logger.error("平遥计费_生成查询客户信息的soap报文时出现异常！",e);
			return null;
		}
		
	}
	
	/**
	 * 生成查询客户绑定的银行卡号的soap报文
	 * @param custId
	 * @return
	 */
	public static String queryAccNoXml(String custId)
	{
		try 
		{
			Document document = DocumentHelper .parseText(
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns=\"http://wsserver.upcome.com\"></soap:Envelope>");

			Element root = document.getRootElement();
			
			//包头
			 root.addElement("soap:Header");
			
			//包体
			Element body = root.addElement("soap:Body");
			
			Element content = body.addElement("GetBankCard");
			
			Element wsbase = content.addElement("wsBase");
			wsbase.addElement("version").addText(PingyaoPayConfig.VERSION); 
			wsbase.addElement("stubId").addText(PingyaoPayConfig.getStubId());
			wsbase.addElement("agentCode").addText(PingyaoPayConfig.AGENT_CODE);
			
			Element method = content.addElement("getBankCardIn");
			method.addElement("custId").addText(custId);
			
			String xml = document.asXML();
			logger.info("平遥计费_查询客户绑定的银行卡接口生成的soap报文是："+xml);
			
			return xml;
		} 
		catch (Exception e) {
			logger.error("平遥计费_生成查询客户绑定的银行卡信息的soap报文时出现异常！",e);
			return null;
		}
		
	}
	
	/**
	 * 生成获取短信验证码的soap报文
	 * @param accNo
	 * @param mobile
	 * @param payFee
	 * @return
	 * @throws DocumentException
	 */
	public static String getCodeXml(String accNo,String mobile,String payFee,String strOrderId,String strTxnTime)
	{
		try
		{
			Document document = DocumentHelper .parseText(
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns=\"http://wsserver.upcome.com\"></soap:Envelope>");
	
			Element root = document.getRootElement();
			
			//包头
			 root.addElement("soap:Header");
			
			//包体
			Element body = root.addElement("soap:Body");
			
			Element content = body.addElement("PrdOrderUPay");
			
			Element wsbase = content.addElement("wsBase");
			wsbase.addElement("version").addText(PingyaoPayConfig.VERSION); 
			wsbase.addElement("stubId").addText(PingyaoPayConfig.getStubId());
			wsbase.addElement("agentCode").addText(PingyaoPayConfig.AGENT_CODE);
			
			Element method = content.addElement("prdOrderUPayIn");
			method.addElement("accNo").addText(accNo);
			method.addElement("payFee").addText(payFee); //金额，单位为 分
			method.addElement("strOrderId").addText(strOrderId);
			method.addElement("strTxnTime").addText(strTxnTime);
			method.addElement("strPhoneNo").addText(mobile);
		
			String xml = document.asXML();
			logger.info("平遥计费获取验证码接口生成的soap报文是："+xml);
			return xml;
		} 
		catch (Exception e) {
			logger.error("平遥计费_生成获取验证码的soap报文时出异常！",e);
			return null;
		}
		
	}
	
	/**
	 * 生成电商计费的soap报文
	 * @param accNo
	 * @param mobile
	 * @param payFee
	 * @return
	 * @throws DocumentException
	 */
	public static String payXml(String payFee,String accNo,String smsCode,String strOrderId,String strTxnTime)
	{
		try 
		{
			Document document = DocumentHelper .parseText(
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns=\"http://wsserver.upcome.com\"></soap:Envelope>");
	
			Element root = document.getRootElement();
			
			//包头
			 root.addElement("soap:Header");
			
			//包体
			Element body = root.addElement("soap:Body");
			
			Element content = body.addElement("PrdOrderUPayMix");
			
			Element wsbase = content.addElement("wsBase");
			wsbase.addElement("version").addText(PingyaoPayConfig.VERSION); 
			wsbase.addElement("stubId").addText(PingyaoPayConfig.getStubId());
			wsbase.addElement("agentCode").addText(PingyaoPayConfig.AGENT_CODE);
			
			Element method = content.addElement("prdOrderUPayIn");
			method.addElement("accNo").addText(accNo);
			method.addElement("businessId").addText(PingyaoPayConfig.BUSINESSID);
			method.addElement("prdId").addText(PingyaoPayConfig.PRDID);
			method.addElement("prdPrice").addText(payFee);  //金额，单位为 分
			method.addElement("strOrderId").addText(strOrderId);
			method.addElement("strTxnTime").addText(strTxnTime);
			method.addElement("strSmsCode").addText(smsCode);
			method.addElement("txnType").addText(PingyaoPayConfig.TXN_TYPE);
		
			String xml = document.asXML();
			logger.info("平遥计费接口生成的soap报文是："+xml);
			return xml;
		} 
		catch (Exception e) {
			logger.error("平遥计费_生成电商支付的soap报文时出现异常！",e);
			return null;
		}
		
	}
	
	/**
	 * 发送soap请求
	 * @param soapXml
	 * @return
	 */
	public static String soap(String soapXml) {  
	        try {  
	        	
	        	StringBuilder sb = new StringBuilder();  
	            String str = null;  
	        	
	            URL url = new URL(ConstantData.PINGYAO_WSDL);  
	            
	            URLConnection conn = url.openConnection();  
	            conn.setUseCaches(false);  
	            conn.setDoInput(true);  
	            conn.setDoOutput(true);  
	  
	            conn.setRequestProperty("Content-Length", Integer.toString(soapXml.length()));  
	            conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");  
	  
	            OutputStream os = conn.getOutputStream();  
	            OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");  
	            osw.write(soapXml);  
	            osw.flush();  
	            osw.close();  
	            
	            
	            InputStream is = conn.getInputStream();  
	            BufferedReader l_reader = new BufferedReader(new InputStreamReader(is,"utf-8"));  
	            while ((str = l_reader.readLine()) != null) {  
	                sb.append(str);  
	            }  
	            
	            String result = sb.toString();
	            String resultXml = result.substring(result.indexOf("<soapenv:Envelope"), result.indexOf("</soapenv:Envelope>")+19);
	            return resultXml;  
	        } catch (Exception e) { 
	        	logger.error(String.format("平遥计费_发送soap报文出现异常！报文：[%s]，异常信息：[%s]", soapXml,e));
	            return null;  
	        }
	        
	    } 
	
	/**
	 * 解析响应参数
	 * @param xml
	 * @return
	 * @throws DocumentException
	 */
	public static HashMap<String,String> parseXml(String resultXml)
	{
		final HashMap<String, String> result = new HashMap<String, String>();
		
		Document document;
		try {
			document = DocumentHelper.parseText(resultXml);
			
			Element root = document.getRootElement();

			Element body = root.element("Body");
			if(body != null)
			{
				body.accept(new VisitorSupport() 
				{
					@Override
					public void visit(Element node) 
					{
						result.put(node.getName().toUpperCase(), node.getTextTrim());
					}
				});
			}
			
			return result;
		} catch (DocumentException e) {
			logger.error("平遥计费_解析响应的soap报文出现异常！",e);
			return null;
		}
	}
	
	/**
	 * 解析响应参数(当外层参数与集合内的参数相同引起冲突时，需要深入解析到集合节点，才能拿到集合内的参数值)
	 * @param xml
	 * @return
	 * @throws DocumentException
	 */
	public static HashMap<String,String> parseSoapXml(String resultXml)
	{
		final HashMap<String, String> result = new HashMap<String, String>();
		
		Document document;
		try {
			document = DocumentHelper.parseText(resultXml);
			
			Element root = document.getRootElement();

			Element body = root.element("Body");
			Element respone = body.element("GetBankCardResponse");
			Element re = respone.element("return");
			Element bankInfo = re.element("custBankInfo");
			if(bankInfo != null)
			{
				bankInfo.accept(new VisitorSupport() 
				{
					@Override
					public void visit(Element node) 
					{
						result.put(node.getName().toUpperCase(), node.getTextTrim());
					}
				});
			}
			
			return result;
		} catch (DocumentException e) {
			logger.error("平遥计费_解析响应的soap报文出现异常！",e);
			return null;
		}
	}
}
