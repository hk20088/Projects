package com.newspace.aps.payChannel.fujian.param;

/**
 * 根据智能卡号查询的客户证号和数据区域信息
 * @author huqili
 * @date 2016年12月15日
 *
 */
public class UserInfo {

	private String RETURNCODE;  //接口返回的状态码
	private String USERNO; //客户证号
	private String AREACODE; //区域信息
	
	public String getRETURNCODE() {
		return RETURNCODE;
	}
	public void setRETURNCODE(String rETURNCODE) {
		RETURNCODE = rETURNCODE;
	}
	public String getUSERNO() {
		return USERNO;
	}
	public void setUSERNO(String uSERNO) {
		USERNO = uSERNO;
	}
	public String getAREACODE() {
		return AREACODE;
	}
	public void setAREACODE(String aREACODE) {
		AREACODE = aREACODE;
	}
	
	
	
}
