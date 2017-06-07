package com.newspace.aps.model;

import java.sql.Timestamp;

/**
 * @description 操作日志类
 * @author huqili
 * @date 2016年10月31日
 *
 */
public class OperLog {

	private int Id;
	private String OperData;
	private int Code;
	private String Desc;
	private Timestamp LogTime;
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getOperData() {
		return OperData;
	}
	public void setOperData(String operData) {
		OperData = operData;
	}
	public int getCode() {
		return Code;
	}
	public void setCode(int code) {
		Code = code;
	}
	public String getDesc() {
		return Desc;
	}
	public void setDesc(String desc) {
		Desc = desc;
	}
	public Timestamp getLogTime() {
		return LogTime;
	}
	public void setLogTime(Timestamp logTime) {
		LogTime = logTime;
	}
	
	
}
