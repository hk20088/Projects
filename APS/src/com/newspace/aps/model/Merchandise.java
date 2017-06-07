package com.newspace.aps.model;

/**
 * @description 商品信息类（包含支付点信息，VIP，礼包，实物等所有可以支付的商品）
 * 				备注：在这里，支付点，VIP，礼包等统称商品
 * @author huqili
 * @date 2016年9月22日
 *
 */
public class Merchandise {

	private String merchId;    //商品Id(储存支付点ID或商品ID)
	private String MerchName;  //商品名称(也指游戏里的道具名称)
	private String GoodsName;  //商品名称(也指游戏名称)， 当购买的是单个游戏或者非游戏端口时，MerchName = GoodsName
	private String Type;   //商品类型     VIP-VIP；GIFT-礼包； ENTITY-实物 ; ACOIN: A豆
	private String PeriodType;  //VIP类型
	private Integer Duration; //时长，VIP特有 
	private int Price;  //商品金额，单位为 分
	private int ExtraId;  //当购买的商品为单个游戏时， 此值为游戏ID
	
	public String getMerchId() {
		return merchId;
	}
	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}
	public String getMerchName() {
		return MerchName;
	}
	public void setMerchName(String merchName) {
		MerchName = merchName;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public Integer getDuration() {
		return Duration;
	}
	public void setDuration(Integer duration) {
		Duration = duration;
	}
	public int getPrice() {
		return Price;
	}
	public void setPrice(int price) {
		Price = price;
	}
	public String getPeriodType() {
		return PeriodType;
	}
	public void setPeriodType(String periodType) {
		PeriodType = periodType;
	}
	public int getExtraId() {
		return ExtraId;
	}
	public void setExtraId(int extraId) {
		ExtraId = extraId;
	}
	public String getGoodsName() {
		return GoodsName;
	}
	public void setGoodsName(String goodsName) {
		GoodsName = goodsName;
	}
	
}
