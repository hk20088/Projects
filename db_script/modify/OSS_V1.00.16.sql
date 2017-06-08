-- 添加活动游戏表和活动表的外键
SET FOREIGN_KEY_CHECKS=0;
ALTER TABLE PromoProducer ADD CONSTRAINT fk_PageElement_Promotion FOREIGN KEY(Promotion) REFERENCES Promotion (Id)  ON DELETE CASCADE ON UPDATE CASCADE;
-- 添加活动和渠道的管理关系表的外键
ALTER TABLE PromoConsumter ADD CONSTRAINT fk_PageElement_Promotion1 FOREIGN KEY(Promotion) REFERENCES Promotion (Id)  ON DELETE CASCADE ON UPDATE CASCADE;
-- 添加发布模板和设备的关联表的外键
ALTER TABLE PublishModelDevice ADD CONSTRAINT fk_PublishModelDevice_model FOREIGN KEY(PublishModel) REFERENCES PublishModel (Id)  ON DELETE CASCADE ON UPDATE CASCADE;
-- 添加发布模板和设备关联表的外键
ALTER TABLE PublishModelDevice ADD CONSTRAINT fk_PublishModelDevice_device FOREIGN KEY(DeviceType) REFERENCES DeviceType (Id)  ON DELETE CASCADE ON UPDATE CASCADE;
-- 添加发布模板元素和模板元素关联表的外键
ALTER TABLE PublishModelElement ADD CONSTRAINT fk_PublishModelElement_model FOREIGN KEY(PublishModel) REFERENCES PublishModel (Id)  ON DELETE CASCADE ON UPDATE CASCADE;
-- 添加发布模板元素和元素的关联表的外键
ALTER TABLE PublishModelElement ADD CONSTRAINT fk_Element_model FOREIGN KEY(Element) REFERENCES Element (Id)  ON DELETE CASCADE ON UPDATE CASCADE;
-- 将Merchandise表中Status字段为"X"的改为"O"
UPDATE Merchandise SET `Status`='O' where Status='X';



-- 增加CP表 字段
ALTER TABLE `CP`
ADD COLUMN `Address`  varchar(128) NULL COMMENT '企业地址' AFTER `State`,
ADD COLUMN `ContactName`  varchar(45) NULL COMMENT '业务联系人姓名' AFTER `Address`,
ADD COLUMN `ContactPhone`  varchar(45) NULL COMMENT '业务联系人号码' AFTER `ContactName`;


-- 增加MerchandiseFeature表Status 字段
ALTER TABLE MerchandiseFeature
ADD COLUMN `Status`  VARCHAR(10) NULL COMMENT '商品特征状态  S:正常   D:删除';
-- 将MerchandiseFeature表Status 初始化为“S”
UPDATE MerchandiseFeature SET `Status`='S'

-- 将游戏包表中的数字状态改为字母大写状态
UPDATE GamePackage set `Status`='N' WHERE `Status`='1';
UPDATE GamePackage set `Status`='S' WHERE `Status`='2';
UPDATE GamePackage set `Status`='P' WHERE `Status`='0';

-- 将AppVersion表中的数字状态改为字母大写状态
UPDATE AppVersion SET `Status`='N' where Status='0';
UPDATE AppVersion SET `Status`='S' where Status='1';
UPDATE AppVersion SET `Status`='P' where Status='2';

-- 将弹窗表内状态修改为 未发布：1--S  已发布：0--P
UPDATE DialogPage SET `Status`='S' where Status='1';
UPDATE DialogPage SET `Status`='P' where Status='0';
