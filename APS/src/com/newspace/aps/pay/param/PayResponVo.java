package com.newspace.aps.pay.param;

public class PayResponVo {

	private Integer returnCode;  //各支付方式的响应状态码
	private String status;  //支付状态
	
/*	public PayResponVo(Integer returnCode,String status)
	{
		this.returnCode = returnCode;
		this.status = status;
	}
	*/
	public Integer getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(Integer returnCode) {
		this.returnCode = returnCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
