package com.newspace.aps.model.order;

import org.apache.log4j.Logger;

import com.google.gson.annotations.Expose;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.StringUtils;

/**
 * @description 支付请求实体类
 * @author huqili
 * @date 2016年9月14日
 *
 */
public class PayReqVo {

	@Expose
	private String Data;  //原始数据，json串
	
	@Expose
	private String Sign;  //加密串
	
	private PayReqData dataObj = new PayReqData();
	
	private static final Logger logger = Logger.getLogger(PayReqVo.class);
	
	/**
	 * 静态工厂方法，用来初始化PayReq对象
	 * @param json
	 * @return
	 */
	public static PayReqVo getInstanceFromJson(String json)
	{
		try 
		{
			PayReqVo content = JsonUtils.fromJson(json, PayReqVo.class);
			content.dataObj = JsonUtils.fromJson(content.getData(), PayReqData.class);
			return content;
		}
		catch (Exception e) 
		{
			logger.error(String.format("解析json字符串[%s]失败...，异常信息：%s", json,e));
		}
		return null;
	}
	

	/**
	 * 校验请求参数的必填项和非0参数是否合法
	 * @param reqVo
	 * @return
	 */
	public boolean isLegal(PayReqVo reqVo)
	{
		if(StringUtils.isExistNullOrEmpty(reqVo.getKeyId(),reqVo.getClientPackageName()) ||
				StringUtils.isEqualZero(reqVo.getAmount(),reqVo.getUserId(),reqVo.getDeviceId(),reqVo.getCount())
				|| (reqVo.getPayPoint() == 0 && reqVo.getMerchId() == 0))
		{
			return false;
		}
		return true;
	}
	
	public String getData() {
		return StringUtils.isNullOrEmpty(Data) ? getNewestData() : Data;
	}
	/**
	 * 获取最新的data数据。因为可能dataObj已经发生改变。
	 */
	public String getNewestData() {
		Data = JsonUtils.toJson(dataObj);
		return Data;
	}
	
	public String getSign() {
		return Sign;
	}
	public void setSign(String sign) {
		this.Sign = sign;
	}
	public String getClientPackageName() {
		return dataObj.getClientPackageName();
	}
	public void setClientPackageName(String clientPackageName) {
		dataObj.setClientPackageName(clientPackageName);
	}
	public Integer getUserId() {
		return dataObj.getUserId();
	}
	public void setUserId(Integer userId) {
		dataObj.setUserId(userId); 
	}
	public String getKeyId() {
		return dataObj.getKeyId();
	}
	public void setKeyId(String keyId) {
		dataObj.setKeyId(keyId);
	}
	public Integer getDeviceId() {
		return dataObj.getDeviceId();
	}
	public void setDeviceId(Integer deviceId) {
		dataObj.setDeviceId(deviceId);
	}
	public int getPayPoint() {
		return dataObj.getPayPoint();
	}
	public void setPayPoint(int payPoint) {
		dataObj.setPayPoint(payPoint);
	}
	public int getCount() {
		return dataObj.getCount();
	}
	public void setCount(int count) {
		dataObj.setCount(count);
	}
	public int getAmount() {
		return dataObj.getAmount();
	}
	public void setAmount(int amount) {
		dataObj.setAmount(amount);
	}
	public String getExternalOrderNo() {
		return dataObj.getExternalOrderNo();
	}
	public void setExternalOrderNo(String externalOrderNo) {
		dataObj.setExternalOrderNo(externalOrderNo);
	}
	public String getPrivateInfo() {
		return dataObj.getPrivateInfo();
	}
	public void setPrivateInfo(String privateInfo) {
		dataObj.setPrivateInfo(privateInfo);
	}
	public String getExtraPayInfo() {
		return dataObj.getExtraPayInfo();
	}
	public void setExtraPayInfo(String extraPayInfo) {
		dataObj.setExtraPayInfo(extraPayInfo);
	}
	public String getVersionCode() {
		return dataObj.getVersionCode();
	}
	public void setVersionCode(String versionCode) {
		dataObj.setVersionCode(versionCode);
	}
	public int getMerchId() {
		return dataObj.getMerchId();
	}
	public void setMerchId(int merchId) {
		dataObj.setMerchId(merchId);
	}
	public String getExternalId() {
		return dataObj.getExternalId();
	}
	public void setExternalId(String externalId) {
		dataObj.setExternalId(externalId);
	}
	public String getNotifyUrl() {
		return dataObj.getNotifyUrl();
	}
	public void setNotifyUrl(String notifyUrl) {
		dataObj.setNotifyUrl(notifyUrl);
	}
	public String getOrderNo() {
		return dataObj.getOrderNo();
	}
	public void setOrderNo(String orderNo) {
		dataObj.setOrderNo(orderNo);
	}
	
	/**
	 * @description 静态内部类，Data字符串所对应的实体类
	 * @author huqili
	 *
	 */
	private static class PayReqData
	{
		private String OrderNo;   //ATET订单号
		private String ClientPackageName;  //客户端包名
		private Integer UserId;  //用户ID
		private String ExternalId;   //第三方平台用户ID，例如广电的智能卡号
		private String KeyId;  //密钥Id
		private Integer DeviceId;  //设备Id
		private int MerchId;  //商品Id
		private int PayPoint;  //支付点Id
		private int Count;
		private int Amount; //总金额 单位为 分
		private String ExternalOrderNo; //外部订单号
		private String NotifyUrl;
		private String PrivateInfo;
		private String ExtraPayInfo; //以json封装的提交支付时所需要的其他信息，包括用户提供的短信确认码。如： {“VERIFY_CODE”:”1234”,”strTxnTime”:”20160826163825822”}
		private String VersionCode;  // 支付SDK版本号（服务器根据此字段做版本兼容）默认为 3
		
		
		public String getOrderNo() {
			return OrderNo;
		}
		public void setOrderNo(String orderNo) {
			OrderNo = orderNo;
		}
		public String getClientPackageName() {
			return ClientPackageName;
		}
		public void setClientPackageName(String clientPackageName) {
			ClientPackageName = clientPackageName;
		}
		public Integer getUserId() {
			return UserId;
		}
		public void setUserId(Integer userId) {
			UserId = userId;
		}
		public String getExternalId() {
			return ExternalId;
		}
		public void setExternalId(String externalId) {
			ExternalId = externalId;
		}
		public String getNotifyUrl() {
			return NotifyUrl;
		}
		public void setNotifyUrl(String notifyUrl) {
			NotifyUrl = notifyUrl;
		}
		public String getKeyId() {
			return KeyId;
		}
		public void setKeyId(String keyId) {
			KeyId = keyId;
		}
		public Integer getDeviceId() {
			return DeviceId;
		}
		public void setDeviceId(Integer deviceId) {
			DeviceId = deviceId;
		}
		
		public int getPayPoint() {
			return PayPoint;
		}
		public void setPayPoint(int payPoint) {
			PayPoint = payPoint;
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
		public String getExternalOrderNo() {
			return ExternalOrderNo;
		}
		public void setExternalOrderNo(String externalOrderNo) {
			ExternalOrderNo = externalOrderNo;
		}
		public String getPrivateInfo() {
			return PrivateInfo;
		}
		public void setPrivateInfo(String privateInfo) {
			PrivateInfo = privateInfo;
		}
		public String getExtraPayInfo() {
			return ExtraPayInfo;
		}
		public void setExtraPayInfo(String extraPayInfo) {
			ExtraPayInfo = extraPayInfo;
		}
		public String getVersionCode() {
			return VersionCode;
		}
		public void setVersionCode(String versionCode) {
			VersionCode = versionCode;
		}
		public int getMerchId() {
			return MerchId;
		}
		public void setMerchId(int merchId) {
			MerchId = merchId;
		}
		
		
	}
}
