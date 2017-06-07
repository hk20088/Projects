package com.newspace.aps.model.goods;

/**
 * @description 商品信息实体类
 * @author huqili
 * @date 2016年9月22日
 *
 */
public class Goods {

	private int Id;
	private String GoodsTitle;  //商品名称
	private String Type;   //商品类型     VIP-VIP；GIFT-礼包； ENTITY-实物
	private String PeriodType;  //VIP时长类型 :“MONTHLY”:包月;“THREE”:3个月;“SIX”:6个月;“YEARLY”:包年;
	private int PeriodCount;  //VIP时长
	private String PriceCurrency;  //收费方式和价格，例如：[{"CurrencyType":"C","MerchPrice":""},{"CurrencyType":"D","MerchPrice":""}]
	private int ExtraId;
	
	//商品类型
	public static String TYPE_GAME = "GAME";  //游戏
	public static String TYPE_GAME_PROPS = "GAME_PROPS";  //游戏道具
	public static String TYPE_VIP = "VIP";  //VIP
	public static String TYPE_ACOIN = "ACOIN";  //A币
	public static String TYPE_CLASS = "CLASSHOUR";  //课时
	
	//商品收费方式
	public static String CURRENCYTYPE_RMB="C";
	

	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getGoodsTitle() {
		return GoodsTitle;
	}
	public void setGoodsTitle(String goodsTitle) {
		GoodsTitle = goodsTitle;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getPriceCurrency() {
		return PriceCurrency;
	}
	public void setPriceCurrency(String priceCurrency) {
		PriceCurrency = priceCurrency;
	}
	public String getPeriodType() {
		return PeriodType;
	}
	public void setPeriodType(String periodType) {
		PeriodType = periodType;
	}
	public int getPeriodCount() {
		return PeriodCount;
	}
	public void setPeriodCount(int periodCount) {
		PeriodCount = periodCount;
	}
	public int getExtraId() {
		return ExtraId;
	}
	public void setExtraId(int extraId) {
		ExtraId = extraId;
	}
	
	
}
