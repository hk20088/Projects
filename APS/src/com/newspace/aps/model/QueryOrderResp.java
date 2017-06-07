package com.newspace.aps.model;

/**
 * 查询订单号状态响应实体类
 * @author huqili
 * @date 2016年10月24日
 *
 */
public class QueryOrderResp {

	private String Status;
	private Integer Code;
	private String Desc;
	
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public Integer getCode() {
		return Code;
	}
	public void setCode(Integer code) {
		Code = code;
	}
	public String getDesc() {
		return Desc;
	}
	public void setDesc(String desc) {
		Desc = desc;
	}
	
	
}
