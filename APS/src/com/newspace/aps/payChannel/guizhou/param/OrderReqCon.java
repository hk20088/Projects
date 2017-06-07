package com.newspace.aps.payChannel.guizhou.param;

import java.util.List;

/**
 * @description 订单订购RequestContent
 * @author Huqili
 *
 */
public class OrderReqCon {

	private String custid;  //客户ID
	private String order_type;  //订单类型
	private String gdno;  //广电号
	private String gdnoid;  //广电号ID
	private String iscrtorder;  //是否可顺延
	private String fees;  //金额 
	private String userid;  //客户ID
	private List<OrderParams> orderparams;
	
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String orderType) {
		order_type = orderType;
	}
	public String getGdno() {
		return gdno;
	}
	public void setGdno(String gdno) {
		this.gdno = gdno;
	}
	public String getGdnoid() {
		return gdnoid;
	}
	public void setGdnoid(String gdnoid) {
		this.gdnoid = gdnoid;
	}
	public String getIscrtorder() {
		return iscrtorder;
	}
	public void setIscrtorder(String iscrtorder) {
		this.iscrtorder = iscrtorder;
	}
	public String getFees() {
		return fees;
	}
	public void setFees(String fees) {
		this.fees = fees;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public List<OrderParams> getOrderparams() {
		return orderparams;
	}
	public void setOrderparams(List<OrderParams> orderparams) {
		this.orderparams = orderparams;
	}
	
	
	/**
	 * 静态内部类，封装orderparams参数
	 * @author Huqili
	 * @date 2016年3月25日
	 *
	 */
	public static class OrderParams{
		private String keyno;  //智能卡号
		private String permark;  //业务类型
		private String ordertype;  //订购类型
		private String salescode;  //商品编码，即pcode
		private String pcodestr;  //营销方案内容
		private String programid;  //媒资ID
		private String programname;  //媒资名（需要再次URLEncode编码）
		private String programprovider;  //媒资提供商
		private String count;
		private String unit;
		private String ispostpone;
		private String flag;
		
		public String getKeyno() {
			return keyno;
		}
		public void setKeyno(String keyno) {
			this.keyno = keyno;
		}
		public String getPermark() {
			return permark;
		}
		public void setPermark(String permark) {
			this.permark = permark;
		}
		public String getOrdertype() {
			return ordertype;
		}
		public void setOrdertype(String ordertype) {
			this.ordertype = ordertype;
		}
		public String getSalescode() {
			return salescode;
		}
		public void setSalescode(String salescode) {
			this.salescode = salescode;
		}
		public String getCount() {
			return count;
		}
		public void setCount(String count) {
			this.count = count;
		}
		public String getUnit() {
			return unit;
		}
		public void setUnit(String unit) {
			this.unit = unit;
		}
		public String getIspostpone() {
			return ispostpone;
		}
		public void setIspostpone(String ispostpone) {
			this.ispostpone = ispostpone;
		}
		public String getFlag() {
			return flag;
		}
		public void setFlag(String flag) {
			this.flag = flag;
		}
		public String getProgramid() {
			return programid;
		}
		public void setProgramid(String programid) {
			this.programid = programid;
		}
		public String getProgramname() {
			return programname;
		}
		public void setProgramname(String programname) {
			this.programname = programname;
		}
		public String getProgramprovider() {
			return programprovider;
		}
		public void setProgramprovider(String programprovider) {
			this.programprovider = programprovider;
		}
		public String getPcodestr() {
			return pcodestr;
		}
		public void setPcodestr(String pcodestr) {
			this.pcodestr = pcodestr;
		}
	}
	
}
