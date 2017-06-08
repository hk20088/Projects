-- 增加goods表Status 字段
ALTER TABLE `Goods`
ADD COLUMN `Status`  VARCHAR(10) NULL COMMENT '商品状态  P:正常   D:删除';
UPDATE Goods SET `Status`='S';

-- 增加RankSystem表Status 字段
ALTER TABLE `RankSystem`
ADD COLUMN `Status`  VARCHAR(10) NULL COMMENT '排行榜与系统关联状态';
UPDATE RankSystem SET `Status`='P';

-- 增加PublishModelDevice表Status 字段
ALTER TABLE `PublishModelDevice`
ADD COLUMN `Status`  VARCHAR(10) NULL COMMENT '广告模板与设备关联状态';
UPDATE PublishModelDevice SET `Status`='S';

-- 增加AppVersionDevice表Status 字段
ALTER TABLE `AppVersionDevice`
ADD COLUMN `Status`  VARCHAR(10) NULL COMMENT '应用升级与设备关联状态  S:未发布  P:已发布 D已删除';
UPDATE AppVersionDevice SET `Status`='S';