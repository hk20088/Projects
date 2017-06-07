package com.newspace.aps.payChannel.guizhou.param;

/**
 * @description 中转参数实体类，获取二维码地址时，将BOSS订单号，城市代码等信息返回到PayHandler，保存到订单
 * 				备注：如果其它支付方式也需要返回信息到PayHandler，则可将此实体类提出去，做成公共的
 * @author huqili
 * @date 2016年9月24日
 *
 */
public class TransitParam {

	private Integer Code;
	private String orderid;
	private String citycode;
	private String payurl;
	
	
	public Integer getCode() {
		return Code;
	}
	public void setCode(Integer code) {
		Code = code;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getCitycode() {
		return citycode;
	}
	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	public String getPayurl() {
		return payurl;
	}
	public void setPayurl(String payurl) {
		this.payurl = payurl;
	}
	
}
