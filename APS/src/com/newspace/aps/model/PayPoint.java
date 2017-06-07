package com.newspace.aps.model;

import java.sql.Timestamp;

/**
 * @description 支付点实体类
 * @author huqili
 * @date 2016年9月14日
 *
 */
public class PayPoint {

	private int id;
	private Integer Game;
	private String GameName;
	private String Name;  //支付点名称
	private int Price;  //支付点金额，单位为 分
	private Timestamp CreateTime;
	private Timestamp UpdateTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getPrice() {
		return Price;
	}
	public void setPrice(int price) {
		Price = price;
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
