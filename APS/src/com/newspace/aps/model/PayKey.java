package com.newspace.aps.model;

import java.sql.Timestamp;

/**
 * @description 密钥表
 * @author huqili
 * @date 2016年6月15日
 * @since JDK1.8
 *
 */
public class PayKey {

	private Integer Id;
	private String KeyId;  //密钥ID，申请密钥时生成
	private String CustType;  //客户类型：CP-游戏提供商；  CHANNEL-渠道； SELLER-商家； ATET-时讯互联
	private String CustId;  //客户ID
	private String PrivateKey;  //RSA私钥
	private String PublicKey;  //RSA公钥
	private String NotifyURL;  //通知地址
	private Timestamp CreateTime;
	private Timestamp UpdateTime;
	
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getKeyId() {
		return KeyId;
	}
	public void setKeyId(String keyId) {
		KeyId = keyId;
	}
	public String getCustType() {
		return CustType;
	}
	public void setCustType(String custType) {
		CustType = custType;
	}
	public String getCustId() {
		return CustId;
	}
	public void setCustId(String custId) {
		CustId = custId;
	}
	public String getPrivateKey() {
		return PrivateKey;
	}
	public void setPrivateKey(String privateKey) {
		PrivateKey = privateKey;
	}
	public String getPublicKey() {
		return PublicKey;
	}
	public void setPublicKey(String publicKey) {
		PublicKey = publicKey;
	}
	public String getNotifyURL() {
		return NotifyURL;
	}
	public void setNotifyURL(String notifyURL) {
		NotifyURL = notifyURL;
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
