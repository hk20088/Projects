package com.newspace.aps.model;

/**
 * 激活码实体类
 * @author huqili
 * @date 2016年11月19日
 *
 */
public class ActiCode {

	private Integer Id;
	private String Code;   //激活码
	private Integer User;  //用户ID
	private String ExtraId;  //第三方渠道用户ID，比如广电的智能卡号
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public Integer getUser() {
		return User;
	}
	public void setUser(Integer user) {
		User = user;
	}
	public String getExtraId() {
		return ExtraId;
	}
	public void setExtraId(String extraId) {
		ExtraId = extraId;
	}
	
	
}
