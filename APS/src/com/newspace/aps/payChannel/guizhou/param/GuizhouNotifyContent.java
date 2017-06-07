package com.newspace.aps.payChannel.guizhou.param;

import com.newspace.aps.common.JsonVo;

/**
 * 贵州广电异步通知请求类
 * @author Huqili
 * @date 2016年9月23日
 *
 */
public class GuizhouNotifyContent implements JsonVo{

	private static final long serialVersionUID = 1L;
	
	private String bankaccno;
	private String orderNo;
	private String paycode;
	private String payreqid;
	private String payway;
	private String status;
	public String getBankaccno() {
		return bankaccno;
	}
	public void setBankaccno(String bankaccno) {
		this.bankaccno = bankaccno;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getPaycode() {
		return paycode;
	}
	public void setPaycode(String paycode) {
		this.paycode = paycode;
	}
	public String getPayreqid() {
		return payreqid;
	}
	public void setPayreqid(String payreqid) {
		this.payreqid = payreqid;
	}
	public String getPayway() {
		return payway;
	}
	public void setPayway(String payway) {
		this.payway = payway;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
}
