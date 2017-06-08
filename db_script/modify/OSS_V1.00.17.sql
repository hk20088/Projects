-- 增加UserProduct表Channel 字段
ALTER TABLE `UserProduct`
ADD COLUMN `Channel`  int(11) NULL COMMENT '渠道Id';

-- 根据用户Id给UserProduct表的Channel字段赋值 
UPDATE UserProduct u INNER JOIN EndUser eu on  eu.Id=u.EndUser INNER JOIN Device d on d.DeviceMark=eu.ExtraId  set u.Channel=d.Channel ;