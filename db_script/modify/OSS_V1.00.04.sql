-- 双屏网格化UI数据表创建脚本

-- ----------------------------
-- Table structure for Element
-- ----------------------------
DROP TABLE IF EXISTS `Element`;
CREATE TABLE `Element` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `ElementTitle` varchar(45) DEFAULT NULL COMMENT '广告元素名称',
  `Size` int(11) NOT NULL,
  `Icon` varchar(45) DEFAULT NULL COMMENT '广告图',
  `BackgroundPic` varchar(45) DEFAULT NULL COMMENT '广告背景图',
  `Video` varchar(256) DEFAULT NULL COMMENT '视频地址',
  `Behavior` varchar(45) DEFAULT NULL COMMENT '跳转类型 默认为activity',
  `BehaviorCode` varchar(128) DEFAULT NULL COMMENT '跳转页面Code',
  `Game` int(9) DEFAULT NULL COMMENT '游戏Id   如果没有游戏Id默认为0',
  `Remark` varchar(500) DEFAULT NULL COMMENT '广告描述',
  `CreateTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for Model
-- ----------------------------
DROP TABLE IF EXISTS `Model`;
CREATE TABLE `Model` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `ModelName` varchar(45) DEFAULT NULL COMMENT '模板名称',
  `ModelId` varchar(45) DEFAULT NULL COMMENT '模板ID',
  `ModelHeight` int(9) DEFAULT NULL COMMENT '模板高度(px)',
  `ModelWidth` int(9) DEFAULT NULL COMMENT '模板宽度(px)',
  `CreateTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `Idx_Modelid` (`ModelId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for PublishModel
-- ----------------------------
DROP TABLE IF EXISTS `PublishModel`;
CREATE TABLE `PublishModel` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `ModelId` varchar(45) NOT NULL,
  `PublishModelName` varchar(45) DEFAULT NULL COMMENT '发布模板名称',
  `Status` varchar(9) DEFAULT NULL COMMENT '状态',
  `StartTime` datetime DEFAULT NULL,
  `EndTime` datetime DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for PublishModelDevice
-- ----------------------------
DROP TABLE IF EXISTS `PublishModelDevice`;
CREATE TABLE `PublishModelDevice` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `DeviceType` int(11) NOT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `PublishModel` int(11) NOT NULL COMMENT '发布模板id',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for PublishModelElement
-- ----------------------------
DROP TABLE IF EXISTS `PublishModelElement`;
CREATE TABLE `PublishModelElement` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `PublishModel` int(11) NOT NULL COMMENT '发布模板Id（主键Id）',
  `Element` int(11) NOT NULL COMMENT '元素Id',
  `ElementIndex` int(9) DEFAULT NULL COMMENT '索引位置',
  `CreateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for Size
-- ----------------------------
DROP TABLE IF EXISTS `Size`;
CREATE TABLE `Size` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Title` varchar(45) DEFAULT NULL COMMENT '广告尺寸名称',
  `SizeType` varchar(45) DEFAULT NULL COMMENT '尺寸类型编码',
  `SizeHeight` int(9) DEFAULT NULL COMMENT '尺寸高度(px)',
  `SizeWidth` int(9) DEFAULT NULL COMMENT '尺寸宽度(px)',
  `CreateTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


-- Promotion表添加字段
-- 跳转类型
alter table Promotion add ActionType varchar(10) comment '跳转类型';
alter table Promotion add ActionContent varchar(45) comment '跳转页面Code';

-- 修改CP Channel表结构
alter table CP add LoginName varchar(45),add Password varchar(45);
alter table Channel add LoginName varchar(45),add Password varchar(45);
alter table CP add State varchar(45);
alter table Channel add State varchar(45);





