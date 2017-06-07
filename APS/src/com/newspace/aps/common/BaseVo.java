package com.newspace.aps.common;

/**
 * @description Vo抽象类，响应实体类均继承此类
 * @author huqili
 * @since JDK1.8
 * @date 2016年6月16日
 *
 */
public class BaseVo {

	private Integer code;
	private String desc;
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
