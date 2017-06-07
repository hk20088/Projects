package com.newspace.aps.payChannel.pingyao.param;

/**
 * @description 封装计费接口响应的参数
 * @author Huqili
 * @date 2016年11月14日
 *
 */
public class PayRespVo {

	private String returnCode;  //应答码
	private String returnmsg;   //应答码对应的描述信息
	private String strOrigQryId;  //交易流水号
	

	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnmsg() {
		return returnmsg;
	}
	public void setReturnmsg(String returnmsg) {
		this.returnmsg = returnmsg;
	}
	public String getStrOrigQryId() {
		return strOrigQryId;
	}
	public void setStrOrigQryId(String strOrigQryId) {
		this.strOrigQryId = strOrigQryId;
	}
	
	
}
