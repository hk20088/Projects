package com.newspace.aps.model;

import java.sql.Timestamp;

/**
 * @description 用户资产表
 * @author huqili
 * @date 2016年10月9日
 *
 */
public class UserAccount {
	
	private int Id;
	private int EndUser;
	private int SystemDomain;
	private int Point;
	private int Coin;
	private int Diamond;
	private Timestamp CreateTime;
	private Timestamp UpdateTime;
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getEndUser() {
		return EndUser;
	}
	public void setEndUser(int endUser) {
		EndUser = endUser;
	}
	public int getPoint() {
		return Point;
	}
	public void setPoint(int point) {
		Point = point;
	}
	public int getCoin() {
		return Coin;
	}
	public void setCoin(int coin) {
		Coin = coin;
	}
	public int getDiamond() {
		return Diamond;
	}
	public void setDiamond(int diamond) {
		Diamond = diamond;
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
	public int getSystemDomain() {
		return SystemDomain;
	}
	public void setSystemDomain(int systemDomain) {
		SystemDomain = systemDomain;
	}


}
