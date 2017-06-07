package com.newspace.aps.pay;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.newspace.aps.ReturnCode;
import com.newspace.aps.common.GeneralUtils;
import com.newspace.aps.common.NativeEncryptUtils;
import com.newspace.aps.dao.goods.GoodsDao;
import com.newspace.aps.dao.operLog.OperLogDao;
import com.newspace.aps.dao.order.OrderDao;
import com.newspace.aps.dao.payKey.PayKeyDao;
import com.newspace.aps.dao.payPoint.PayPointDao;
import com.newspace.aps.dao.userAccount.UserAccountDao;
import com.newspace.aps.dao.userFeature.UserFeatureDao;
import com.newspace.aps.dao.userProduct.UserProductDao;
import com.newspace.aps.model.Merchandise;
import com.newspace.aps.model.OperLog;
import com.newspace.aps.model.PayKey;
import com.newspace.aps.model.PayPoint;
import com.newspace.aps.model.UserAccount;
import com.newspace.aps.model.UserFeature;
import com.newspace.aps.model.UserProduct;
import com.newspace.aps.model.goods.Goods;
import com.newspace.aps.model.goods.PriceCurrency;
import com.newspace.aps.model.order.Order;
import com.newspace.aps.model.order.PayReqVo;
import com.newspace.aps.pay.param.PayVo;
import com.newspace.aps.paynotify.HttpAsyncConnExecutor;
import com.newspace.aps.paynotify.param.NotifyContent;
import com.newspace.aps.paynotify.param.NotifyEntity;
import com.newspace.common.coder.Coder;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.StringUtils;
import com.newspace.common.utils.TimeUtils;
import com.newspace.common.utils.TwoTuple;

/**
 * @description APS系统的工具类，存放封装的一些公共方法
 * @author huqili
 * @since JDK1.8
 * @date 2016年6月16日
 *
 */
@Service
public class PayUtils 
{
	
	private static final Logger logger = Logger.getLogger(PayUtils.class);
	
	@Resource
	private PayKeyDao payKeyDao;
	
	@Resource
	private PayPointDao payPointDao;
	
	@Resource
	private OrderDao orderDao;
	
	@Resource
	private GoodsDao goodsDao;
	
	@Resource
	private UserProductDao userProductDao;
	
	@Resource
	private UserAccountDao userAccountDao;
	
	@Resource
	private OperLogDao operLogDao;
	
	@Resource
	private UserFeatureDao userFeatureDao;
	
	/**
	 * @校验签名及支付点
	 * @param reqVo
	 * @return
	 */
	public TwoTuple<Integer, Merchandise> verifyReqInfo(PayReqVo reqVo,String key)
	{
		int returnCode = ReturnCode.SUCCESS.getCode();
		Merchandise merch = null;
		int price;
		
		if(key != null)
		{
			//校验签名，签名生成规则RSA(MD5(Data))
//			returnCode = verifySign(reqVo.getData(), reqVo.getSign(), key);
			
			if(returnCode == ReturnCode.SUCCESS.getCode())
			{
				//判断购买的是商品还是游戏道具
				if(reqVo.getPayPoint() != 0)
				{
					//说明购买的是游戏道具，查询支付点金额
					PayPoint payPoint = queryPayPoint(reqVo.getPayPoint());
					price = payPoint == null ? 0 : payPoint.getPrice();
					
					//封装数据
					merch = fillFromPayPoint(payPoint);
				}
				else
				{
					//说明购买的是商品 (备注：当商品打折时，如果客户端同步数据有延迟，则会出现价格不一致的情况，导致校验不通过)
					Goods goods = goodsDao.queryGoods(reqVo.getMerchId());
					price = goods == null ? 0 : resolvePrice(goods.getPriceCurrency());
					
					//封装数据
					merch = fillFromGoods(goods,price);
				}
				
				//校验支付点金额是否正确 
				if(reqVo.getCount() * price != reqVo.getAmount())
				{
					//支付点不正确，这里只记录日志，继续下面的支付流程
					int retCode = ReturnCode.VERIFY_PAYPOINT_ERROR.getCode();
					String operData = String.format("单价:%s 分,数量:%s,传入的总金额:%s 分", price,reqVo.getCount(),reqVo.getAmount());
					insertLog(operData, retCode, ReturnCode.getDesc(retCode));
				}
			}
			
		}
		else
		{
			logger.error(String.format("The key is null...", reqVo.getKeyId()));
			returnCode = ReturnCode.VERIFY_KEY_ISNULL.getCode();
		}
		
		return new TwoTuple<Integer, Merchandise>(returnCode, merch);
	}
	
	/**
	 * 将支付点信息封装到Merchandise
	 * @param payPoint
	 * @return
	 */
	private Merchandise fillFromPayPoint(PayPoint payPoint)
	{
		Merchandise merch = new Merchandise();
		
		if(payPoint != null)
		{
			merch.setType(Goods.TYPE_GAME_PROPS);
			merch.setMerchId(String.valueOf(payPoint.getId()));
			merch.setMerchName(payPoint.getName()); //道具名称
			merch.setGoodsName(payPoint.getGameName()); //游戏名称
			merch.setPrice(payPoint.getPrice());
			merch.setExtraId(payPoint.getGame()); //游戏ID
		}
		
		return merch;
	}

	/**
	 * 将商品信息封装到Merchandise
	 * @param goods
	 * @return
	 */
	private Merchandise fillFromGoods(Goods goods,int price)
	{
		Merchandise merch = new Merchandise();
		
		if(goods != null)
		{
			merch.setMerchName(goods.getGoodsTitle());  //商品名称（游戏也是一种商品）
			merch.setGoodsName(goods.getGoodsTitle());  //游戏名称
			merch.setMerchId(String.valueOf(goods.getId())); 
			merch.setExtraId(goods.getExtraId());  //如果购买的是游戏，此值为游戏ID
			merch.setType(goods.getType());
			merch.setPeriodType(goods.getPeriodType());
			merch.setDuration(goods.getPeriodCount());
			merch.setPrice(price);
		}
		
		return merch;
	}
	
	/**
	 * 解析商品的价格
	 * @param priceCurrency
	 * @return
	 */
	private int resolvePrice(String priceCurrency)
	{
		int price = 0;
		List<PriceCurrency> priceList = new ArrayList<>();
		try 
		{
			priceList = JsonUtils.json2List(priceCurrency, PriceCurrency.class);
			
			for(PriceCurrency currency : priceList)
			{
				if(currency.getCurrencyType().equals(Goods.CURRENCYTYPE_RMB)) //过滤出人民币价格
				{
					price = Integer.parseInt(StringUtils.isNullOrEmpty(currency.getMerchPrice()) ? "0" : currency.getMerchPrice());
					break;
				}
			}
				
		}
		catch (Exception e) 
		{
			logger.error(String.format("解析PriceCurrency时出错,PriceCurrency的值为[%s],异常信息：%s", priceCurrency,e));
		}
		
		return price;
	}
	
	/**
	 * 根据KeyId查询密钥信息
	 * @param keyId
	 * @return
	 */
	private  PayKey queryPayKeyInfo(String keyId)
	{
		return payKeyDao.queryKeyInfo(keyId);
	}
	
	/**
	 * 获取通知地址
	 * @param keyId
	 * @return
	 */
	public String getNotifyURl(String keyId)
	{
		PayKey keyInfo = queryPayKeyInfo(keyId);
		return keyInfo == null ? null : keyInfo.getNotifyURL();
	}
	
	/**
	 * 得到KeyId对应的私钥(接收到请求时解签名、响应客户端时加密)
	 * @param keyId 
	 * @return String 私钥
	 */
	public String getPrivateKey(String keyId)
	{
		PayKey keyInfo = queryPayKeyInfo(keyId);
		return keyInfo == null ? null : keyInfo.getPrivateKey().replaceAll("\\s*|\t|\r|\n", "");
	}
	
	/**
	 * 查询支付点信息
	 * @param payPoint 支付点
	 * @return
	 */
	private PayPoint queryPayPoint(int payPoint)
	{
		return payPointDao.queryPayPoint(payPoint);
	}
	
	/**
	 * 查询支付点所对应的单价
	 * @param payPoint
	 * @return
	 */
	@SuppressWarnings("unused")
	private int queryPrice(int payPoint)
	{
		PayPoint payPointInfo = queryPayPoint(payPoint);
		return payPointInfo == null ? 0 : payPointInfo.getPrice();
	}
	
	/**
	 * 根据用户ID查询用户账户信息
	 * @param userId
	 * @return
	 */
	public UserAccount queryAccount(int userId,int systemDomain)
	{
		return userAccountDao.queryAccount(userId, systemDomain);
	}
	
	/**
	 * 修改用户账户信息
	 * @param userAccount
	 */
	public int updateAccount(UserAccount userAccount)
	{
		return userAccountDao.updateAccount(userAccount);
	}
	
	/**
	 * 保存订单
	 * @param order
	 */
	public void saveOrder(Order order)
	{
		try 
		{
			orderDao.insert(order);
		} 
		catch (Exception e) 
		{
			logger.error("新增订单记录时出现异常...",e);
		}
	}
	
	/**
	 * 更新订单
	 * @param order
	 */
	public void updateOrder(Order order)
	{
		try 
		{
			orderDao.update(order);
		}
		catch (Exception e) 
		{
			logger.error("更新订单记录时出现异常...",e);
		}
	}
	
	/**
	 * 根据订单号查询订单记录
	 * @param flag  订单号标识 ，值为不同订单号的字段名。
	 * 				例如，如果根据ATET订单号查询订单，则flag = OrderNo
	 * 					如果根据第三方支付平台订单号查询订单，则flag = PaymentOrderNo
	 * @param orderNo
	 * @param boo 如果boo为true，查询订单状态为WAIT的订单。如果boo为flase，查询订单状态为SUCCESS的订单。
	 * @return
	 */
	public Order queryByNo(String flag,String orderNo,boolean boo)
	{
		if(boo)
			return orderDao.queryByNo(flag,orderNo,PayConfig.QUERY_TRUE,Order.STATUS_WAIT);
		else
			return orderDao.queryByNo(flag, orderNo, "", "");
	}
	
	
	
	/**
	 * 支付成功后，判断商品类型，处理用户的资产信息，比如用户购买VIP，则给用户添加相应的天数
	 * @param order
	 */
	public void doAsset(Order order,Merchandise merch)
	{
		try 
		{
			//解析ExtraPayInfo（数据库中对应的是PrivateInfo），如果是K12产品，这个参数会封装购买的商品类型及Id
			PayVo payVo = JsonUtils.fromJson(order.getPrivateInfo(), PayVo.class);
			
			if(!StringUtils.isExistNullOrEmpty(payVo.getType()))  //说明购买的是K12商品的VIP
			{
				buy_vip(order, merch,payVo.getType(),payVo.getId());
			}
			else
			{
				if(order.getMerchType() != null && order.getMerchType().equals(Goods.TYPE_VIP))  //说明购买的是VIP
				{
					buy_vip(order, merch,Goods.TYPE_VIP,order.getPayPoint());
				}
				else if(order.getMerchType() != null && order.getMerchType().equals(Goods.TYPE_ACOIN))  //说明购购买的是A币
				{
					buy_Acoin(order, merch);
				}
				else if(order.getMerchType() != null && order.getMerchType().equals(Goods.TYPE_CLASS))  //说明购买的是课时
				{
					
					buy_class(order, merch);
				}
				else if(order.getMerchType() != null && order.getMerchType().equals(Goods.TYPE_GAME))  //说明购买的是游戏
				{
					logger.info(String.format("准备给订单为[%S]的用户[%S]添加游戏资源[%S]...", order.getOrderNo(),order.getEndUser(),order.getGameName()));
					
					//根据用户ID，系统ID及类型查询用户对应的资产(目前用户购买单个游戏永久可用，如果以后要求购买游戏有时效限制，则需先查询，后做逻辑判断。这行代码暂时不用，这里做预留)
					//List<UserProduct> list = queryAsset(order,Goods.TYPE_GAME);
					
					//添加用户游戏资产信息
					fillUserProduct(order,0,Goods.TYPE_GAME,0);
				}
			}
			
		}
		catch (Exception e) 
		{
			logger.error("给用户添加资产的时候出现异常...",e);
		}
	}
	
	
	/**
	 * 购买VIP
	 * @param order
	 * @param merch
	 * @param goodsType  购买的类型
	 * 					类型1：用户在其它大厅内购买VIP，这时类型就是VIP
	 *                  类型2：用户在K12大厅里购买科目，或者年级，是以VIP的形式购买的，但是类型应该是科目（GAME），或者年级（GAMETYPE）
	 * @param proId   购买的商品ID(对应的就是VIP，科目，年级的ID)
	 */
	private void buy_vip(Order order,Merchandise merch ,String goodsType,int proId)
	{

		//先查询用户购买的VIP的天数
		int days = 0;
		if(merch !=null)
		{
			days = merch.getDuration();
		}
		else
		{
			Goods goods = goodsDao.queryGoods(order.getPayPoint());
			days = goods == null ? 0 : goods.getPeriodCount();
		}
		
		logger.info(String.format("准备给订单为[%s]的用户[%s]添加VIP[%s]天...", order.getOrderNo(),order.getEndUser(),days));
		
		//根据用户ID，系统ID及类型查询用户对应的资产
		List<UserProduct> list = queryAsset(order,goodsType);
		
		if(list == null || list.isEmpty())
		{
			//添加此用户的资产信息，这里添加VIP天数
			fillUserProduct(order, days,goodsType,proId);
		}
		else
		{
			Date date = new Date();
			UserProduct userProduct = list.get(0);
			//判断当前的VIP到期时间是否已失效
			if(userProduct.getExpireTime() != null && userProduct.getExpireTime().getTime() > TimeUtils.getTimestamp().getTime())
			{
				//在原有的时间上加上相应的天数
				date = TimeUtils.getDay(userProduct.getExpireTime(), days);
			}
			else
			{
				//在当前时间上加上相应的天数
				date = TimeUtils.getDay(new Date(), days);
			}
				
			userProduct.setExpireTime(TimeUtils.getTimestamp(date));
			userProduct.setProId(proId);
			//修改此用户的VIP天数
			userProductDao.updateAsset(userProduct);
			
		}
	
	}
	
	/**
	 * 购买A币
	 * @param order
	 * @param merch
	 */
	private void buy_Acoin(Order order,Merchandise merch)
	{
		logger.info(String.format("准备给订单为[%s]的用户[%s]添加A豆[%s]个...", order.getOrderNo(),order.getEndUser(),order.getAmount() / 100.0));
		
		//先判断是否有此用户的账户信息
		UserAccount userAccount = userAccountDao.queryAccount(order.getEndUser(),order.getSystemDomain());
		if(userAccount == null)
		{
			//创建用户账户信息
			fillUserAccount(order);
		}
		else
		{
			//修改此用户的A豆
			userAccount.setCoin(userAccount.getCoin() + order.getAmount());
			userAccountDao.updateAccount(userAccount);
		}
	}
	
	
	/**
	 * 购买课时
	 * @param order
	 * @param merch
	 */
	private void buy_class(Order order,Merchandise merch)
	{
		int classHour = merch == null ? 0 : merch.getDuration();  //得到课时
		logger.info(String.format("准备给订单为[%S]的用户[%S]添加课时[%S]...", order.getOrderNo(),order.getEndUser(),classHour));
		
		//根据用户ID，系统ID及类型查询用户对应的资产
		List<UserProduct> list = queryAsset(order,Goods.TYPE_CLASS);
		
		if(list == null || list.isEmpty())
		{
			//添加此用户的资产信息，这里添加课时
			fillUserProduct(order, classHour,Goods.TYPE_CLASS,0);
		}
		else
		{
			UserProduct userProduct = list.get(0);
			
			userProduct.setClassHour(userProduct.getClassHour() + classHour);  //现有课时加上新增的课时
			userProduct.setProId(order.getPayPoint());
			//修改此用户的课时
			userProductDao.updateAsset(userProduct);
		}
	}
	
	/**
	 * 先根据系统，渠道等信息查询用户的资产信息
	 * @param order
	 * @return
	 */
	private List<UserProduct> queryAsset(Order order,String goodsType)
	{
		UserProduct userProduct = new UserProduct();
		
		userProduct.setEndUser(order.getEndUser());
		userProduct.setSystemDomain(order.getSystemDomain());
		userProduct.setType(goodsType);
		userProduct.setChannel(order.getChannel());
		
		return  userProductDao.queryAsset(userProduct);
	}
	
	/**
	 * 封装并记录用户账户信息
	 * @param userId
	 * @param coin
	 */
	private void fillUserAccount(Order order)
	{
		UserAccount userAccount = new UserAccount();
		
		userAccount.setCoin(order.getAmount());
		userAccount.setEndUser(order.getEndUser());
		userAccount.setSystemDomain(order.getSystemDomain());
		
		userAccountDao.insertAccount(userAccount);
	}
	
	/**
	 * 封装并记录用户资产信息
	 * @param order
	 * @param days
	 */
	private void fillUserProduct(Order order,int days, String type,int proId)
	{
		UserProduct userProduct = new UserProduct();
		
		if(proId != 0)  //购买VIP(K12大厅里购买科目，年级，本质上也是购买VIP)
		{
			//在当前时间的基础上添加相应的天数
			Date date = TimeUtils.getDay(new Date(), days);
			userProduct.setExpireTime(TimeUtils.getTimestamp(date));
			userProduct.setDays(days);
			userProduct.setProId(proId);
		}
		else if(type.equals(Goods.TYPE_GAME))  //购买单个游戏
		{
			userProduct.setExpireTime(null); //用户购买的单款游戏永久可用，这里失效时间默认为null
			userProduct.setProId(order.getGame());  //游戏ID
		}
		else if(type.equals(Goods.TYPE_CLASS))  //购买课时
		{
			userProduct.setExpireTime(null);  //以课时判断用户的权限是否到期， 这里失效时间默认为null
			userProduct.setProId(order.getPayPoint());
			userProduct.setClassHour(days);
		}
		
		userProduct.setType(type);
		userProduct.setEndUser(order.getEndUser());
		userProduct.setSystemDomain(order.getSystemDomain());
		userProduct.setStatus("0");
		userProduct.setChannel(order.getChannel());
		
		//记录数据
		userProductDao.insertAsset(userProduct);
	}
	
	
	/**
	 * 封装并记录日志
	 * @param operDate
	 * @param code
	 * @param desc
	 */
	private void insertLog(String operData,int code,String desc)
	{
		OperLog operLog = new OperLog();
		
		operLog.setOperData(operData);
		operLog.setCode(code);
		operLog.setDesc(desc);
		
		try 
		{
			operLogDao.insert(operLog);
		} 
		catch (Exception e) 
		{
			logger.error("记录操作日志出现异常...",e);
		}
	}
	
	/**
	 * 支付成功后，异步通知商户
	 * @param order
	 * @param _notifyURL
	 */
	public void notify(Order order, String _notifyURL)
	{
		try 
		{
			//优先使用请求参数中的通知地址，如果为空，再去数据库取
			String notifyURL = StringUtils.isNullOrEmpty(order.getNotifyURL()) ? _notifyURL : order.getNotifyURL();
			if(!StringUtils.isNullOrEmpty(notifyURL) && StringUtils.isHttpURL(notifyURL))
			{
				notifyURL = notifyURL.trim();  //去掉通知地址的空格
				logger.info(String.format("准备通知商户，地址为[%s]", notifyURL));
				NotifyContent content = NotifyContent.getInstanceByOrder(order);
				NotifyEntity entity = new NotifyEntity(notifyURL, content.generateContent());
				HttpAsyncConnExecutor.submitNotifyTask(entity);
				logger.info("通知请求发送成功...");
			}
			else
			{
				logger.error(String.format("订单[%s]获取不到NotifyURL，无法通知商户...", order.getOrderNo()));
			}
		} 
		catch (Exception e)
		{
			logger.error(String.format("异步通知商户时出现异常，异常信息：%s", e));
		}
		
	}
	

	/**
	 * 
	 * @param data
	 * @param sign
	 * @param key
	 * @return
	 */
	private static int verifySign(String data,String sign,String key)
	{
		int returnCode = ReturnCode.SUCCESS.getCode();
		
		try
		{
			// 由于之前privatekey长度太长，修改为对key进行MD5之后作为新的key
			key = Coder.getHexStringByEncryptMD5(key);
			String data_md5 = Coder.getHexStringByEncryptMD5(data);
			logger.info(String.format("要开始校验签名了，好激动...key是[%s]，data是[%s]", key,data_md5));
			
			// 解密签名，然后和 源数据生成的MD5值进行比对
			if (!data_md5.equalsIgnoreCase(NativeEncryptUtils.decryptAES(sign, key)))
			{
				logger.error("【校验签名结果不一致！】");
				returnCode = ReturnCode.VERIFY_SIGN_DATACHANGED.getCode();
			}
			else
			{
				logger.info("校验成功！");
			}
		}
		catch (Exception e)
		{
			logger.error("【校验签名操作失败！】", e);
			returnCode = ReturnCode.VERIFY_SIGN_ERROR.getCode();
		}
		
		return returnCode;
	}
	
	
	/**
	 * 对数据进行加密，得到签名。加密规则：AES(MD5(content))
	 * @param content 源数据
	 * @param key 密钥
	 */
	public static String getSignByRSAandMD5(String content, String key)
	{
		try
		{
			if(key == null)
			{
				return PayConfig.WRONG_SIGN;
			}
			else
			{
				// 由于之前privatekey长度太长，修改为对key进行MD5之后作为新的key
				key = Coder.getHexStringByEncryptMD5(key);
//				return NativeEncryptUtils.encryptAES(Coder.getHexStringByEncryptMD5(content), key);
				return "E1D0D1A774F36B6FA44557397B7EF634B445C81FEE787CAAA1A4C74FB452740E776F8A595D704F844372605F49AB9888A352AA79EFE53EE579BD3EB67C3B4C47";
			}
		}
		catch (Exception e)
		{
			logger.error("加密失败！", e);
			return null;
		}
		
	}
	
	/**
	* @Title: getMerchInfoSwitch 
	* @Description: 根据用户Id和系统包名  获取 支付商品信息开关
	* 			true ： 开       false ： 关
	* @param user
	* @param packageName
	* @return Boolean
	* @author tangpan
	* @date 2016年12月2日 下午3:07:57
	* @throws
	 */
	public Boolean getMerchInfoSwitch(Integer user,String packageName)
	{
		//查询用户的支付商品信息开关
		UserFeature userFeature = userFeatureDao.queryMerchInfoSwitch(user, GeneralUtils.getSysDomain.get(packageName));
		Boolean flag = userFeature != null && "OPEN".equals(userFeature.getFeatureValue()) ? Boolean.TRUE : Boolean.FALSE;
		return flag;
	}
	
}
