package com.newspace.aps.model;

/**
 * @description 设备信息实体类
 * @author huqili
 * @date 2016年9月16日
 *
 */
public class Device {

	private int id;
	private String TypeCode; //设备机型，对应DeviceType表中的TypeCode
	private String PlatType; //设备平台：TV-电视； PHONE-手机； PAD-平板
	private int Channel;  //渠道主键Id
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getChannel() {
		return Channel;
	}
	public void setChannel(int channel) {
		Channel = channel;
	}
	
	
}
