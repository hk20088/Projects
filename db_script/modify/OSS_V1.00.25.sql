-- 游戏分类表，添加绑定VIP字段  liqiao 2017年5月24日
alter table GameTypeLookup add column BindVip varchar(40) comment '游戏分类绑定VIP';

-- 游戏表，添加绑定VIP字段    liqiao 2017年5月24日
alter table Game add column BindVip varchar(40) comment '游戏绑定VIP';

-- 游戏文件表，添加游戏视频价格字段    liqiao 2017年5月24日
alter table GameFileItem add column Price int(11) comment '游戏视频是否免费,0代表免费';

-- DRTData表增加UserType字段
ALTER TABLE `DRTData`
ADD COLUMN `UserType`  varchar(45) NULL COMMENT 'DataType为M（消息）时，记录该字段，有All(系统)，Partial(部分用户)，Channel（渠道）' AFTER `UpdateTime`;

