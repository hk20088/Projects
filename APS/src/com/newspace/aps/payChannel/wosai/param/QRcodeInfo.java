package com.newspace.aps.payChannel.wosai.param;

/**
 * @description 获取喔噻支付二维码时，保存喔噻订单号等信息的实体类
 * @author Huqili
 * @date 2016年9月17日
 */
public class QRcodeInfo {

	private String QRcode;  //支付二维码
	private String wosaiOrderNo;  //wosai订单号
	private String payType;  //支付方式：支付宝 或 微信
	
	public String getQRcode() {
		return QRcode;
	}
	public void setQRcode(String qRcode) {
		QRcode = qRcode;
	}
	public String getWosaiOrderNo() {
		return wosaiOrderNo;
	}
	public void setWosaiOrderNo(String wosaiOrderNo) {
		this.wosaiOrderNo = wosaiOrderNo;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}

	
	
}
