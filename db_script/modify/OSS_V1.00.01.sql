
-- 新增弹窗表预发布时间字段语句
-- 修改弹窗表结构 修改预发布时间字段名称为弹窗开始时间
ALTER TABLE `DialogPage`
CHANGE COLUMN `ToPublishTime` `StartTime`  datetime NULL DEFAULT NULL COMMENT '弹窗开始时间' AFTER `VersionCode`;

-- 删除活动表的预发布时间(ToPublishTime)
alter table `Promotion` drop column ToPublishTime


