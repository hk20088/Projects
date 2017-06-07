package com.newspace.aps.payChannel.pingyao.param;

/**
 * @description 用户信息响应实体类
 * @author Huqili
 * @date 2016年11月14日
 *
 */
public class CustInfo{

	
	private String custname;   //客户名称
	private String mobile;     //手机号
	private String custaddr;   //客户地址
	private String accNo;      //银行卡号
	private String strOrderId; //广电订单号（无特殊意义，在支付接口中使用）
	private String strTxnTime; //订单发送时间（无特殊意义，在支付接口中使用）
	private Integer code;
	private String desc;
	
	public String getCustname() {
		return custname;
	}
	public void setCustname(String custname) {
		this.custname = custname;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCustaddr() {
		return custaddr;
	}
	public void setCustaddr(String custaddr) {
		this.custaddr = custaddr;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getStrOrderId() {
		return strOrderId;
	}
	public void setStrOrderId(String strOrderId) {
		this.strOrderId = strOrderId;
	}
	public String getStrTxnTime() {
		return strTxnTime;
	}
	public void setStrTxnTime(String strTxnTime) {
		this.strTxnTime = strTxnTime;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
