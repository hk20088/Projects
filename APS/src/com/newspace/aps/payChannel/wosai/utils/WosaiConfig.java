package com.newspace.aps.payChannel.wosai.utils;

/**
 * {@link WosaiConfig.java}
 * @description: 喔噻分配的相关相关参数
 * 备注：以下参数是喔噻万能收银台提供给 第三合作⽅的前置代理接口
 *	喔噻会为
 *  1）使本系统的第三方商户分配：wosai_store_id,wosai_appid,wosai_appkey,其中，
 *  wosai_appkey为私钥，必须在商户系统中妥善保存，不得外泄；
 *  2）使用本系统的合作分配：channel_id; channel_secret;其中channel_secret为合
 *  作方私钥，必须妥善保存，不得外泄。由channel_secret外泄造成的损失第三方平台自行承担 ，
 *  channel_id和channel_secret请跟收钱吧申请
 *  
 * @author Huqili
 * @date 2016年9月17日
 */
public class WosaiConfig {

	/**
	 * 喔噻商户ID
	 */
	public static final String WOSAI_STORE_ID = "03322111001200200002615";  //00000011001200200000259    03322111001200200002615
	
	/**
	 * 喔噻分配的ID
	 */
	public static final String WOSAI_APP_ID = "1001200200002615"; //1001200200000259   1001200200002615
	
	/**
	 * wosai密钥
	 */
	public static final String WOSAI_APPKEY = "e7a84e05e1cdf9d6beaea626b167c9fc";  //0820db4af0f86d5689fd19b0ac5298f6   e7a84e05e1cdf9d6beaea626b167c9fc
	
	/**
	 * 代理商ID，需要跟收钱吧申请
	 */
	public static final String CHANNEL_ID = "14340928622177";  //14332152058233
	
	/**
	 * 渠道私钥，需要跟收钱吧申请
	 */
	public static final String CHANNEL_SECRET = "bec5eedd50b859a33c0aef4170d0c37b"; //11cbcf21c4dbbb200b0fe420a9dcd8a8
	
	
}
