package com.newspace.aps.payChannel.unicomiptv.param;

import com.newspace.aps.common.JsonVo;

/**
 * @description 联通IPTV响应实体类
 * @author huqili
 * @date 2017年5月23日
 *
 */
public class IptvRespVo implements JsonVo{

	private static final long serialVersionUID = 1L;

	private String ErrorCode;
	
	private String ErrorDesc;

	public String getErrorCode() {
		return ErrorCode;
	}

	public void setErrorCode(String errorCode) {
		ErrorCode = errorCode;
	}

	public String getErrorDesc() {
		return ErrorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		ErrorDesc = errorDesc;
	}
	
	
}
