package com.newspace.aps.payChannel.guizhou.param;

/**
 * @description 贵阳广电用户信息实体类,同时作为响应实体类，通过接口将用户信息返回给客户端
 * @author huqili
 *
 */
public class CustomerInfo {
	
	private String ExtraId;  //智能卡号
	private String CityCode; //城市代码
	private double Amount; //钱袋余额,单位为元
	private String Custid;
	private String CustName;  //用户姓名
	private String Phone;  //用户手机
	private String CustAddress; //用户地址
	private Integer Code;
	private String Desc;
	
	
	public String getCityCode() {
		return CityCode;
	}
	public void setCityCode(String cityCode) {
		CityCode = cityCode;
	}
	public String getExtraId() {
		return ExtraId;
	}
	public void setExtraId(String extraId) {
		ExtraId = extraId;
	}
	public double getAmount() {
		return Amount;
	}
	public void setAmount(double amount) {
		Amount = amount;
	}
	
	public String getCustid() {
		return Custid;
	}
	public void setCustid(String custid) {
		Custid = custid;
	}
	public String getCustName() {
		return CustName;
	}
	public void setCustName(String custName) {
		CustName = custName;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getCustAddress() {
		return CustAddress;
	}
	public void setCustAddress(String custAddress) {
		CustAddress = custAddress;
	}
	public Integer getCode() {
		return Code;
	}
	public void setCode(Integer code) {
		Code = code;
	}
	public String getDesc() {
		return Desc;
	}
	public void setDesc(String desc) {
		Desc = desc;
	}
	
	
	
	
}
