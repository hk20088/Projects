
namespace java com.newspace.aps.service

service PayServer
{
	//计费接口  调用方向：client -> APS
	string pay(1:string json);
	
	//查询订单状态接口  调用方向 ：client -> APS
	string queryOrder(1:string OrderNo);
	
	//查询用户信息接口  调用方向：OSS/client -> APS
	string userInfo(1:string ExtraId);
	
}


namespace java com.newspace.HOC.service

service UserServer
{
	/**
	 * 用户注册接口。  调用方向：GameLabby -> HOC
	 * UserId - 用户ID，用户首次进入大厅时会生成用户ID，如果用户不注册，则UserId即为游客ID
	 * ExtraInfo - 附加信息，可上传用户的基本信息
	 * 响应参数：注册结果 
	 */
	string register(1:i32 UserId,2:string Phone,3:string Password,4:string MessCode,5:string ExtraInfo);
	
	/**
	 * 用户登录接口。 调用方向：GameLabby -> HOC
	 * 响应参数：此用户的基本信息及资产信息
	 */
	string logon(1:string Phone,2:string Password,3:string ClientPackageName,4:i32 ChannelId);
	
	/**
	 * 用户结算接口。 调用方向：GameTable -> HOC
	 * Flag - 结算类型  ：  ADD 代表加；  REDUC 代表减
	 * Value - 具体需要更新的值
	 * 响应参数：响应此用户更新后的资产信息
	 */ 
	string updateAssets(1:i32 UserId,2:string Flag, 3:i32 Value, 4:i32 GameId);
	
	/**
	 * 用户消息接口。 调用方向：GameTable -> HOC
	 * Flag - 消息类型： TEXT 代表文字消息；  PIC 代表图片消息
	 * MessInfo - 消息内容，图片可传二进制内容
	 * Target - 消息到达目标，ALL 代表所有玩家
	 * 响应参数：请求状态
	 */
	string message(1:32 UserId,2:string Flag, 3:string MessInfo, 4:String Target)
	
}


namespace java com.newspace.HOC.service

service TableServer
{
	/**
	 * 初始化桌子。 调用方向：GameTable -> HOC
	 * Type - 类型: NUM 代表校验人数；  COIN 代表校验某个用户的金币是否足够
	 * ExtraInfo - 附加信息，例如用户拥有的金币
	 * 响应参数：此游戏的基本信息，例如玩家数、牌面数据、牌面布局
	 */
	string init(1:i32 GameId, 2:string Type, 3:i32 UserId, 4:string ExtraInfo)
	
	/**
	 * 广告广播接口。 调用方向：GameTable -> HOC
	 * 响应参数：
	 * 类型（Type） ：AD 代表广告； RAD 代表广播
	 * 内容（Content）
	 */
	string radioAD(1:i32 GameId)
}

namespace java com.newspace.HOC.service

service GameServer
{
	/**
	 * 游戏接口。 调用方向：GameTable -> GameEngine
	 * ActionFlag - 动作标识，包括洗牌，发牌，杠牌等
	 * CardFace - 牌面数据，动作标识不同， 上传的牌面数据也不同，有可能是所有牌面数据，也有可能是某个用户的牌面数据
	 * 
	 * 
	 * 
	 * 响应参数：根据不同的类型，返回不同的结果（可借鉴支付接口的响应参数）
	 */
	string engine(1:i32 GameId, 2:i32 UserId, 3:string ActionFlag, 4:string CardFace)
	
}






















