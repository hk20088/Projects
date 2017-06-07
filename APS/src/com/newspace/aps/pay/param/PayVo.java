package com.newspace.aps.pay.param;

import com.newspace.aps.pay.PayConfig;
import com.newspace.common.utils.StringUtils;

/**
 * @description 支付请求参数ExtraPayInfo里存放的用来支付的参数
 * @author huqili
 * @date 2016年11月14日
 *
 */
public class PayVo {

	private String client;  //支付方式   例如：client=COIN代表A豆
	private String strOrderId;  //平遥专用字段 ，无实际意义，请求平遥BOSS时会用到
	private String strTxnTime;  //平遥专用字段 ，无实际意义，请求平遥BOSS时会用到
	private String accNo;  //用户绑定的银行卡尾号（目前只有平遥会用到）
	private String messCode; //短信验证码
	private String flag; //标识字段，flag=PAY时代表支付请求； flag=REPEAT时代表重新获取验证码
	private Double balance; //用户余额信息
	private String extralInfo; //附加信息
	private String mobile; //手机号
	
	private String Type;    //K12大厅专用字段，表示购买商品的类型   GAME-科目     GAMETYPE-年级
	private Integer Id;     //K12大厅专用字段，表示购买的科目或年级的ID
	
	public static final String FLAG_PAY = "PAY";  //支付标识，说明拿到了短信验证码，直接支付
	public static final String FLAG_REPEAT = "REPEAT";  //重新下发验证码
	public static final String FLAG_IOM = "IOM"; //用户余额信息。服务器用来判断客户端上一次请求该接口是用来显示商品信息
	public static final String FLAG_EXI = "EXI"; //附加信息
	
	/**
	 * 用来判断是滞需要查询用户余额信息（当flag=pay,repeat时， 直接进入支付流程）
	 * @param flag
	 * @return
	 */
	public static boolean isLegal(String flag)
	{
		boolean fg = true;
		if(StringUtils.isExistNullOrEmpty(flag) || flag.equals(PayConfig.COMMON) || flag.equals(FLAG_IOM) || flag.equals(FLAG_PAY) || flag.equals(FLAG_REPEAT))
		{
			fg = false;
		}
		return fg;
	}
	
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getStrOrderId() {
		return strOrderId;
	}
	public void setStrOrderId(String strOrderId) {
		this.strOrderId = strOrderId;
	}
	public String getStrTxnTime() {
		return strTxnTime;
	}
	public void setStrTxnTime(String strTxnTime) {
		this.strTxnTime = strTxnTime;
	}
	public String getMessCode() {
		return messCode;
	}
	public void setMessCode(String messCode) {
		this.messCode = messCode;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Double getBalance()
	{
		return balance;
	}
	public void setBalance(Double balance)
	{
		this.balance = balance;
	}
	public String getExtralInfo()
	{
		return extralInfo;
	}
	public void setExtralInfo(String extralInfo)
	{
		this.extralInfo = extralInfo;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}
	
}
