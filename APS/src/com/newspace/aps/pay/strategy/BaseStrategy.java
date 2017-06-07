package com.newspace.aps.pay.strategy;

import org.apache.log4j.Logger;

import com.newspace.aps.ReturnCode;
import com.newspace.aps.model.order.Order;
import com.newspace.aps.pay.param.PayResponVo;

/**
 * @description 基础策略类，用来定义一些公共的变量
 * @author huqili
 * @date 2017年2月16日
 *
 */
public class BaseStrategy {

	protected transient Logger logger = Logger.getLogger(getClass());  //定义日志类
	
/*	protected PayResponVo respVo = new PayResponVo(returnCode,status);  //初始化PayResponVo对象
	
	protected static int returnCode = ReturnCode.SUCCESS.getCode();  //定义默认状态码
	
	protected static String status = Order.STATUS_WAIT;  //定义默认支付状态
*/	
}
