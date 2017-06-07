package com.newspace.aps.model;

import java.sql.Timestamp;

public class UserProduct{
	private Integer Id;
	private Integer EndUser;
	private Integer SystemDomain;
	private String Type;  //产品类型
	private Integer ProId;  //产品ID
	private Timestamp TransactionTime;
	private Timestamp ExpireTime;  //当产品类型为游戏时， 说明用户购买的是单款游戏，则到期时间为null即可。
	private Timestamp CreateTime;
	private Timestamp UpdateTime;
	private String Status;
	private Integer Days;  //天数
	
	private Integer Channel;  //渠道ID，用户资产分渠道处理，此逻辑已与运营（海宝）确认。 - 2017年3月14日   胡起立
	private int ClassHour;  //课时，彩虹巴士专有概念。 - 2017年5月6日  胡起立
	
	
	public Integer getProId() {
		return ProId;
	}
	public void setProId(Integer proId) {
		ProId = proId;
	}
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public Integer getEndUser() {
		return EndUser;
	}
	public void setEndUser(Integer endUser) {
		EndUser = endUser;
	}
	public Integer getSystemDomain() {
		return SystemDomain;
	}
	public void setSystemDomain(Integer systemDomain) {
		SystemDomain = systemDomain;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public Timestamp getTransactionTime() {
		return TransactionTime;
	}
	public void setTransactionTime(Timestamp transactionTime) {
		TransactionTime = transactionTime;
	}
	public Timestamp getExpireTime() {
		return ExpireTime;
	}
	public void setExpireTime(Timestamp expireTime) {
		ExpireTime = expireTime;
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
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public Integer getDays() {
		return Days;
	}
	public void setDays(Integer days) {
		this.Days = days;
	}
	public Integer getChannel() {
		return Channel;
	}
	public void setChannel(Integer channel) {
		Channel = channel;
	}
	public int getClassHour() {
		return ClassHour;
	}
	public void setClassHour(int classHour) {
		ClassHour = classHour;
	}
	
	
	
}
