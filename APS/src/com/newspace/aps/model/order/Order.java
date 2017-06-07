package com.newspace.aps.model.order;

import java.sql.Timestamp;

import com.newspace.aps.common.ConstantData;
import com.newspace.common.utils.RandomUtils;


/**
 * @description 订单实体类
 * @author huqili
 * @date 2016年9月13日
 *
 */
public class Order {
	
	private Integer SystemDomain;  //客户端包名
	private String KeyId;     //秘钥Id
	private String OrderNo;          //支付平台产生的订单号
	private String ExternalOrderNo;   //CP或商户产生的订单号
	private String PaymentOrderNo;   //支付渠道产生的订单号
	private Integer EndUser;     //用户ID
	private String ExtraId;   //第三方平台用户ID，例如广电的智能卡号
	private Integer Channel;    
	private Integer Device;      //设备ID
	private String TypeCode;   //设备型号
	private String PlatType;  //设备平台  TV-电视； PHONE-手机； PAD-平板
	private String NotifyURL;
	private String PrivateInfo;
	private String MerchType;  //商品类型
	private Integer PayPoint;   
	private String PayPointName;
	private int PayPointMoney;  //支付点单价
	private int Count;
	private int Amount;  //总金额，单位为 分
	private String CurrencyType = CURRENCY_TYPE_RMB;  //交易币种，默认为RMB（人民币）
	private Integer Game;  
	private String GameName;
	private String PayType;  //支付方式
	private String Phone;
	private String PayAgent;  //支付渠道代理商，例如联通VAC下属的2000渠道等
	private String ExtraPayTypeInfo; //支付渠道私有信息，例如联通VAC上行短信内容
	private String VersionCode;  // 支付SDK版本号（服务器根据此字段做版本兼容）默认为 3
	private String Status;   //SUCCESS-支付成功； FAIL-支付失败； QUIT-退订 
	private Timestamp CreateTime;
	private Timestamp UpdateTime;
	
	//----------------支付币种-----------------------
	private static final String CURRENCY_TYPE_RMB = "RMB";  //支付币种：RMB
	
	
	//-----------------支付方式----------------------
	public static final String PAYTYPE_COIN = "COIN" ;  //A豆
	public static final String PAYTYPE_POINT = "POINT";  //积分
	public static final String PAYTYPE_DIAMOND = "DIAMOND";  //钻石
	public static final String PAYTYPE_VAC = "VAC";  //联通VAC
	public static final String PAYTYPE_WO = "WO";   //联通沃邮箱
	public static final String PAYTYPE_IPTV = "IPTV";  //联通IPTV
	public static final String PAYTYPE_ALIPAY = "ALIPAY";  //支付宝手机客户端
	public static final String PAYTYPE_WOSAI_ALIPAY = "WOSAI_ALIPAY";  //喔噻支付宝扫码
	public static final String PAYTYPE_WOSAI_WECHAT = "WOSAI_WECHAT";  //喔噻微信扫码
	public static final String PAYTYPE_HISENSE = "HISENSE";  //海信
	public static final String PAYTYPE_COOCAA = "COOCAA";  //创维酷开
	public static final String PAYTYPE_GUIZHOU_BAG = "GUIZHOU_BAG";  //贵州广电钱袋支付
	public static final String PAYTYPE_GUIZHOU_ALIPAY = "GUIZHOU_ALIPAY";  //贵州广电支付宝扫码
	public static final String PAYTYPE_GUIZHOU_WECHAT = "GUIZHOU_WECHAT";  //贵州广电微信扫码
	public static final String PAYTYPE_PINGYAO = "PINGYAO";  //平遥广电
	public static final String PAYTYPE_FUJIAN = "FUJIAN"; //福建广电
	public static final String PAYTYPE_WOCHENG = "V";  //联通沃橙
	
	
	//----------------------------支付状态------------------------------
	public static final String STATUS_SUCCESS = "SUCCESS";  //支付成功
	public static final String STATUS_FAIL = "FAIL";   //支付失败
	public static final String STATUS_QUIT = "QUIT";   //退订
	public static final String STATUS_WAIT = "WAIT";  //等待客户端处理
	
	//生成20位订单号.规则：yyyyMMdd+8位流水（4位随机+4位自增）
	public static String generateOrderNo()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append(ConstantData.ORDER_CODE_PAY);
		sb.append(RandomUtils.getRandom(4, 4));
		
		return sb.toString();
	}

	
	public Integer getSystemDomain() {
		return SystemDomain;
	}
	
	public void setSystemDomain(Integer systemDomain) {
		SystemDomain = systemDomain;
	}
	
	public String getKeyId() {
		return KeyId;
	}

	public void setKeyId(String keyId) {
		KeyId = keyId;
	}

	public String getOrderNo() {
		return OrderNo;
	}

	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
	}

	public String getExternalOrderNo() {
		return ExternalOrderNo;
	}

	public void setExternalOrderNo(String externalOrderNo) {
		ExternalOrderNo = externalOrderNo;
	}

	public String getPaymentOrderNo() {
		return PaymentOrderNo;
	}

	public void setPaymentOrderNo(String paymentOrderNo) {
		PaymentOrderNo = paymentOrderNo;
	}

	public Integer getEndUser() {
		return EndUser;
	}

	public void setEndUser(Integer endUser) {
		EndUser = endUser;
	}

	public String getExtraId() {
		return ExtraId;
	}

	public void setExtraId(String extraId) {
		ExtraId = extraId;
	}

	public Integer getChannel() {
		return Channel;
	}

	public void setChannel(Integer channel) {
		Channel = channel;
	}

	public Integer getDevice() {
		return Device;
	}

	public void setDevice(Integer device) {
		Device = device;
	}

	public String getTypeCode() {
		return TypeCode;
	}

	public void setTypeCode(String typeCode) {
		TypeCode = typeCode;
	}

	public String getPlatType() {
		return PlatType;
	}

	public void setPlatType(String platType) {
		PlatType = platType;
	}

	public String getNotifyURL() {
		return NotifyURL;
	}

	public void setNotifyURL(String notifyURL) {
		NotifyURL = notifyURL;
	}

	public String getPrivateInfo() {
		return PrivateInfo;
	}

	public void setPrivateInfo(String privateInfo) {
		PrivateInfo = privateInfo;
	}

	public String getMerchType() {
		return MerchType;
	}

	public void setMerchType(String merchType) {
		MerchType = merchType;
	}

	public Integer getPayPoint() {
		return PayPoint;
	}

	public void setPayPoint(Integer payPoint) {
		PayPoint = payPoint;
	}

	public String getPayPointName() {
		return PayPointName;
	}

	public void setPayPointName(String payPointName) {
		PayPointName = payPointName;
	}

	public int getPayPointMoney() {
		return PayPointMoney;
	}

	public void setPayPointMoney(int payPointMoney) {
		PayPointMoney = payPointMoney;
	}

	public int getCount() {
		return Count;
	}

	public void setCount(int count) {
		Count = count;
	}

	public int getAmount() {
		return Amount;
	}

	public void setAmount(int amount) {
		Amount = amount;
	}

	public String getCurrencyType() {
		return CurrencyType;
	}

	public void setCurrencyType(String currencyType) {
		CurrencyType = currencyType;
	}

	public Integer getGame() {
		return Game;
	}

	public void setGame(Integer game) {
		Game = game;
	}

	public String getGameName() {
		return GameName;
	}

	public void setGameName(String gameName) {
		GameName = gameName;
	}

	public String getPayType() {
		return PayType;
	}

	public void setPayType(String payType) {
		PayType = payType;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getPayAgent() {
		return PayAgent;
	}

	public void setPayAgent(String payAgent) {
		PayAgent = payAgent;
	}

	public String getExtraPayTypeInfo() {
		return ExtraPayTypeInfo;
	}

	public void setExtraPayTypeInfo(String extraPayTypeInfo) {
		ExtraPayTypeInfo = extraPayTypeInfo;
	}

	public String getVersionCode() {
		return VersionCode;
	}

	public void setVersionCode(String versionCode) {
		VersionCode = versionCode;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public Timestamp getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Timestamp createTime) {
		CreateTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return UpdateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		UpdateTime = updateTime;
	}
	
	

	
	
}
