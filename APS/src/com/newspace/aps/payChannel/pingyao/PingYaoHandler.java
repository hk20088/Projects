package com.newspace.aps.payChannel.pingyao;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.newspace.aps.ReturnCode;
import com.newspace.aps.dao.actiCode.ActiCodeDao;
import com.newspace.aps.model.order.Order;
import com.newspace.aps.pay.param.PayVo;
import com.newspace.aps.payChannel.pingyao.param.CustInfo;
import com.newspace.aps.payChannel.pingyao.param.PayRespVo;
import com.newspace.aps.payChannel.pingyao.utils.PingyaoPayConfig;
import com.newspace.aps.payChannel.pingyao.utils.PingyaoPayUtils;
import com.newspace.common.utils.StringUtils;

@Service
public class PingYaoHandler {

	
	private static final Logger logger = Logger.getLogger(PingYaoHandler.class);
	
	@Resource
	private ActiCodeDao actiCodeDao;
	
	/**
	 * 获取用户绑定的银行卡号，截取后四位，返回给客户端展示
	 * @param sm_no
	 * @param userId
	 * @return
	 */
	public CustInfo getAccNo(String sm_no,int userId)
	{
		CustInfo custInfo = new CustInfo();
		
		try 
		{
			sm_no = querySmartCard(sm_no,userId);
			if(sm_no != null)
			{
				//先根据sm_no调用5.3接口获取custId
				String custId = PingyaoPayUtils.getCustId(sm_no);
				
				if(custId != null)
				{
					//根据custId调用6.8接口获取用户绑定的银行卡
					String accNo = PingyaoPayUtils.getAccNo(custId);
					logger.info(String.format("用户[%s]对应的custId是：[%s]，accNo是：[%s]",sm_no,custId,accNo));
					
					if(accNo != null)
					{
						//只取银行卡的后四位
						custInfo.setAccNo(accNo.substring(accNo.length()-4));
						custInfo.setStrOrderId(PingyaoPayConfig.getStrOrderId());
						custInfo.setStrTxnTime(PingyaoPayConfig.getStrTxnTime());
						custInfo.setCode(ReturnCode.SUCCESS.getCode());
					}
					else
					{
						logger.error(String.format("用户(sm_no)[%s]不存在绑定的银行卡(accNo)", sm_no));
						custInfo.setCode(ReturnCode.ACCNO_ISNULL.getCode());
					}
				}
				else
				{
					logger.error(String.format("用户(sm_no)[%s]不存在对应的客户ID(CustId)", sm_no));
					custInfo.setCode(ReturnCode.CUSTID_ISNULL.getCode());					
				}
			}
			else
			{
				logger.error(String.format("用户(UserId)[%s]尚未绑定智能卡号...", userId));
				custInfo.setCode(ReturnCode.SM_NO_ISEMPTY.getCode());
			}
		
		} 
		catch (Exception e) 
		{
			logger.error(String.format("用户(sm_no)[%s]获取验证码时出现异常：%s", sm_no,e));
			custInfo.setCode(ReturnCode.ERROR.getCode());
		}
		
		return custInfo;
	}
	
	/**
	 * 根据智能卡号获取custId，再根据custId获取用户的银行卡号
	 * @param sm_no
	 * @return
	 */
	public int getCode(PayVo payVo,Order order)
	{
		int returnCode = ReturnCode.ERROR.getCode();
		String sm_no = null;
		
		try 
		{
			sm_no = querySmartCard(order.getExtraId(),order.getEndUser());
			if(sm_no != null)
			{
				//先根据sm_no调用5.3接口获取custId
				String custId = PingyaoPayUtils.getCustId(sm_no);
				
				//根据custId调用6.8接口获取用户绑定的银行卡
				String accNo = PingyaoPayUtils.getAccNo(custId);
				logger.info(String.format("用户[%s]对应的custId是：[%s]，accNo是：[%s]，mobile是：[%s]",sm_no,custId,accNo,payVo.getMobile()));
				
				//最后根据accNo和mobile调用6.6接口下发短信验证码给用户。备注：平遥支付的金额单位由 元 改为了 分
				returnCode = PingyaoPayUtils.getCode(payVo, accNo, String.valueOf(order.getAmount()));
				
			}
			else
			{
				logger.error(String.format("用户(UserId)[%s]尚未绑定智能卡号...", order.getEndUser()));
				returnCode = ReturnCode.SM_NO_ISEMPTY.getCode();
			}
		
		} 
		catch (Exception e) 
		{
			logger.error(String.format("用户(sm_no)[%s]获取验证码时出现异常：%s", sm_no,e));
			returnCode = ReturnCode.ERROR.getCode();
		}
		
		return returnCode;
	}
	
	/**
	 * 调用BOSS支付接口发起支付请求
	 * 备注：只有正常获取到custId，accNo参数才会调用此方法，所以这里不做参数是否为空判断。
	 * @param reqVo
	 * @param order
	 * @return
	 */
	public int pay(PayVo payVo,Order order)
	{
		int returnCode = ReturnCode.ERROR.getCode();
		
		try 
		{
			String sm_no = querySmartCard(order.getExtraId(),order.getEndUser());
			if(sm_no != null)
			{
				//先根据sm_no调用5.3接口获取custId
				String custId = PingyaoPayUtils.getCustId(sm_no);
				
				//然后根据custId调用6.8接口获取用户绑定的银行卡
				String accNo = PingyaoPayUtils.getAccNo(custId);
				
				//请求6.9电商扣费接口 备注：平遥计费的金额单位由 元 改为了 分
				PayRespVo payRespVo = PingyaoPayUtils.pay(String.valueOf(order.getAmount()), accNo, payVo.getMessCode(), payVo.getStrOrderId(), payVo.getStrTxnTime());
				
				if(payRespVo.getReturnCode() !=null && payRespVo.getReturnCode().equals("0"))
				{
					//说明支付成功
					returnCode = ReturnCode.SUCCESS.getCode();
				}
			}
			else
			{
				logger.error(String.format("用户(UserId)[%s]尚未绑定智能卡号...", order.getEndUser()));
			}
			
		}
		catch (Exception e) 
		{
			logger.error(String.format("用户(ExtraId)[%s],订单[%s]支付出现异常: %s",order.getExtraId(),order.getOrderNo(),e));
		}
		
		return returnCode;
	}
	
	/**
	 * 判断智能卡号是否为空
	 * 如果sm_no为空，则可能是云盒（非广电盒子，获取不到智能卡号）在支付，根据UserId查询
	 * @param sm_no
	 * @param userId
	 * @return
	 */
	private String querySmartCard(String sm_no, int userId)
	{
		//如果sm_no为空，则可能是云盒（非广电盒子，获取不到智能卡号）
		if(StringUtils.isNullOrEmpty(sm_no))
		{
			sm_no = actiCodeDao.queryExtraId(userId);
		}
		
		return sm_no;
	}
}
