package com.newspace.aps.model.goods;

public class PriceCurrency {

	private String CurrencyType;  //类型：C-人民币；A-A豆；D-钻石；G-金币；P-积分
	private String MerchPrice;  //价格
	
	public String getCurrencyType() {
		return CurrencyType;
	}
	public void setCurrencyType(String currencyType) {
		CurrencyType = currencyType;
	}
	public String getMerchPrice() {
		return MerchPrice;
	}
	public void setMerchPrice(String merchPrice) {
		MerchPrice = merchPrice;
	}
	
	
} 
