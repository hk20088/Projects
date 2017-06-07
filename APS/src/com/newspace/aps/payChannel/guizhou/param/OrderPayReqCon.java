package com.newspace.aps.payChannel.guizhou.param;

import java.util.List;

/**
 * @description 扫码支付requestContent
 * @author Huqili
 *
 */
public class OrderPayReqCon {

	private String totalFee;  //总金额，单位 元
	private String redirectUrl; //重写向URL，支付成功后会自动重定向到此地址
	private String noticeAction; //异步通知地址
	private String orderNum; //调用[订单订购接口]获取到的订单号
	private OrderInfo orderInfo; //订单信息
	private CustInfo custInfo;  //客户信息
	
	
	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getNoticeAction() {
		return noticeAction;
	}

	public void setNoticeAction(String noticeAction) {
		this.noticeAction = noticeAction;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public CustInfo getCustInfo() {
		return custInfo;
	}

	public void setCustInfo(CustInfo custInfo) {
		this.custInfo = custInfo;
	}



	/**
	 * @description 订单信息实体类
	 * @author huqili
	 * @date 2016年3月29日
	 *
	 */
	public static class OrderInfo
	{
		private String orderNo; //订单号
		private List<ProductList> productList;
		
		public String getOrderNo() {
			return orderNo;
		}
		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}
		public List<ProductList> getProductList() {
			return productList;
		}
		public void setProductList(List<ProductList> productList) {
			this.productList = productList;
		}

		public static class ProductList
		{
			
			private String keyno;  //智能卡号
			private String productName; //产品名称，需进行两次URLEncode
			private String count;  //订购周期
			private String fee;  //总金额，单位 元
			public String getKeyno() {
				return keyno;
			}
			public void setKeyno(String keyno) {
				this.keyno = keyno;
			}
			public String getProductName() {
				return productName;
			}
			public void setProductName(String productName) {
				this.productName = productName;
			}
			public String getCount() {
				return count;
			}
			public void setCount(String count) {
				this.count = count;
			}
			public String getFee() {
				return fee;
			}
			public void setFee(String fee) {
				this.fee = fee;
			}
			
		}
	}
	
	/**
	 * @description 客户信息实体类
	 * @author Huqili
	 * @date 2016年3月29日
	 *
	 */
	public static class CustInfo
	{
		private String custid;  //客户ID
		private String custname;  //客户姓名
		private String city;  //城市编码
		public String getCustid() {
			return custid;
		}
		public void setCustid(String custid) {
			this.custid = custid;
		}
		public String getCustname() {
			return custname;
		}
		public void setCustname(String custname) {
			this.custname = custname;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		
	}
}
