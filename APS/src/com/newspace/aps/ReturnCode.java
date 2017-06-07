package com.newspace.aps;

import java.util.HashMap;
import java.util.Map;

/**
 * @description 接口响应类，为了方便获取描述信息，这里使用枚举的方式实现。
 * 				注：全平台通用状态码：0 代表操作成功；1代表操作失败；2代表请求参数不合法。（可在0-1000内扩展 ）
 * 				本系统（APS）状态码范围3001-4000。
 * 				状态码定义规则：xx(服务代码)x(方法代码)x(某个方法的异常代码)。
 * 				例：UserServer服务的userCU方法，数据更新失败。则状态码为：3034。30代表UserServer服务，13代表userCU方法，4代表数据更新失败 
 * @author huqili
 * @since JDK1.8
 * @date 2016年5月31日
 *
 */
public enum ReturnCode 
{
	/**
	 * 操作成功
	 */
	SUCCESS(0,"操作成功"),
	
	/**
	 * 操作失败
	 */
	ERROR(1,"操作失败"),
	
	/**
	 * 请求参数不合法，可能非空字段传NULL
	 */
	PARAM_ERROR(2,"请求参数不合法"),
	
	/**
	 * 要查询的数据为空
	 */
	DATA_NOT_EXIST(3,"不存在相关数据"),
	
	/**
	 * 校验签名结果不一致
	 */
	VERIFY_SIGN_DATACHANGED(3001, "校验签名结果不一致"),
	
	/**
	 * 校验签名操作失败
	 */
	VERIFY_SIGN_ERROR(3002, "校验签名操作失败"),
	
	/**
	 * 获取到的Key为空
	 */
	VERIFY_KEY_ISNULL(3003,"密钥为空"),
	
	/**
	 * 支付点金额不正确
	 */
	VERIFY_PAYPOINT_ERROR(3004,"支付点金额不正确"),
	
	/**
	 * 查询到的支付方式为空
	 */
	PAYTYPE_IS_NULL(3005,"查询到的支付方式为空"),
	
	/**
	 * 钱袋余额不足:
	 */
	GUIZHOU_MONEY_NOT_ENOUGH(3006,"钱袋余额不足"),
	
	/**
	 * 计费对象已订购过此产品
	 */
	GUIZHOU_ALREADY_BUY(3007,"您已订购过该产品"),
	
	/**
	 * A豆余额不足
	 */
	ACOIN_NOT_ENOUGH(3008,"A豆余额不足"),
	
	/**
	 * 智能卡号为空
	 */
	SM_NO_ISEMPTY(3009,"智能卡号为空"),
	
	/**
	 * 根据智能卡号未查询出客户证号
	 */
	CUSTID_ISNULL(3010,"未查询出客户证号"),
	
	/**
	 * 用户当天消费已超过限额
	 */
	FUJIAN_MORE_LIMIT(3011,"你已超过当天限额，请改天再试"),
	
	/**
	 * 没有查询出用户绑定的银行卡，无法扣费
	 */
	ACCNO_ISNULL(3012,"未查询出绑定的银行卡"),
	
	aaa(10000,"尾端点位");
	
	
	/**
	 * 返回码
	 */
	private int code;
	
	/**
	 * 返回码所描述的信息
	 */
	private String desc;
	
	/**
	 * 包含返回码和对应描述信息的map
	 */
	private final static Map<Integer, String> map = new HashMap<Integer, String>();

	static
	{
		for (ReturnCode returnCode : ReturnCode.values())
			map.put(returnCode.getCode(), returnCode.getDesc());
	}
	
	
	/**
	 * 通过返回码得到描述信息
	 * @param returnCode 返回码
	 * @return String  描述信息
	 */
	public static String getDesc(int returnCode)
	{
		String desc = map.get(returnCode);
		return desc == null ? "不存在该返回码" : desc;
	}
	
	ReturnCode(int code,String desc) 
	{
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	
}
