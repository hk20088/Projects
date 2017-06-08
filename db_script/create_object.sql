/*
Navicat MySQL Data Transfer

Source Server         : 153
Source Server Version : 50510
Source Host           : 10.1.1.153:3306
Source Database       : OSS

Target Server Type    : MYSQL
Target Server Version : 50510
File Encoding         : 65001

Date: 2016-11-17 16:57:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ActivateCode
-- ----------------------------
DROP TABLE IF EXISTS `ActivateCode`;
CREATE TABLE `ActivateCode` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统自增长',
  `Code` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '激活码',
  `Goods` int(11) DEFAULT NULL COMMENT '商品Id',
  `Type` varchar(11) DEFAULT NULL COMMENT '激活码类型\nVIP是购买码\n礼包是礼包码',
  `UseTime` datetime DEFAULT NULL COMMENT '激活码使用时间',
  `User` int(11) DEFAULT NULL COMMENT '使用人',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `Store` int(11) DEFAULT NULL COMMENT '商店Id',
  PRIMARY KEY (`Id`),
  KEY `fk_ActivityCode_Goods_idx` (`Goods`),
  KEY `fk_ActivityCode_Store` (`Store`),
  CONSTRAINT `fk_ActivityCode_Goods` FOREIGN KEY (`Goods`) REFERENCES `Goods` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ActivityCode_Store` FOREIGN KEY (`Store`) REFERENCES `Store` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='激活码信息表';

-- ----------------------------
-- Table structure for AppVersion
-- ----------------------------
DROP TABLE IF EXISTS `AppVersion`;
CREATE TABLE `AppVersion` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `DownloadURL` varchar(256) DEFAULT NULL COMMENT '版本文件',
  `VersionCode` int(16) DEFAULT NULL COMMENT '版本号',
  `VersionName` varchar(128) DEFAULT NULL COMMENT '版本名称',
  `Remark` varchar(500) DEFAULT NULL COMMENT '版本描述',
  `SelfUpdate` int(11) DEFAULT NULL COMMENT '升级方式\n0：普通升级\n1：强制升级\n2：静默升级',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `SystemDomain` int(11) DEFAULT NULL COMMENT '应用Id',
  `DescFile` varchar(50) DEFAULT NULL COMMENT '描述文件',
  `Status` varchar(8) DEFAULT NULL COMMENT '状态',
  `Low` int(16) DEFAULT NULL COMMENT '必须更新的最低版本',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for AppVersionDevice
-- ----------------------------
DROP TABLE IF EXISTS `AppVersionDevice`;
CREATE TABLE `AppVersionDevice` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `DeviceType` int(11) DEFAULT NULL,
  `AppVersion` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_AppVersionDevice_DeviceType_idx` (`DeviceType`),
  KEY `fk_AppVersionDevice_AppVersion1_idx` (`AppVersion`),
  CONSTRAINT `fk_AppVersionDevice_AppVersion1` FOREIGN KEY (`AppVersion`) REFERENCES `AppVersion` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_AppVersionDevice_DeviceType` FOREIGN KEY (`DeviceType`) REFERENCES `DeviceType` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for BatchTask
-- ----------------------------
DROP TABLE IF EXISTS `BatchTask`;
CREATE TABLE `BatchTask` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统自增长',
  `SystemDomain` int(11) DEFAULT NULL COMMENT '批处理任务所属系统',
  `Channel` int(11) DEFAULT NULL COMMENT '批处理任务所属渠道',
  `BatchTemplate` int(11) DEFAULT NULL,
  `TaskTitle` varchar(30) DEFAULT NULL COMMENT '批处理任务的名称',
  `RewardType` varchar(30) DEFAULT NULL COMMENT '奖励类型：\nQ：定额赠送\nP：百分比赠送',
  `Reward` varchar(30) DEFAULT NULL COMMENT '奖励内容\nA豆: A\n积分：P\n钻石：D',
  `RewardAmount` int(11) DEFAULT NULL COMMENT '奖励的数量',
  `TargetType` varchar(30) DEFAULT NULL COMMENT '批处理对象的类型\n游戏：G\n活动：P',
  `BatchTarget` int(9) DEFAULT NULL COMMENT '批处理的对象Id',
  `EmailTemplate` varchar(1000) DEFAULT NULL COMMENT '批处理的邮件内容模板',
  `StartTime` datetime DEFAULT NULL COMMENT '开始时间',
  `EndTime` datetime DEFAULT NULL COMMENT '结束时间',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `ParamType` varchar(30) DEFAULT NULL COMMENT '条件类型',
  `ParamCode` varchar(20) DEFAULT NULL COMMENT '条件值',
  PRIMARY KEY (`Id`),
  KEY `fk_BatchTask_BatchTemplate1_idx` (`BatchTemplate`),
  KEY `fk_Channel_Channel_idx` (`Channel`),
  KEY `fk_SystemDomain_SystemDomain_idx` (`SystemDomain`),
  CONSTRAINT `fk_BatchTask_BatchTemplate` FOREIGN KEY (`BatchTemplate`) REFERENCES `BatchTemplate` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Channel_Channel` FOREIGN KEY (`Channel`) REFERENCES `Channel` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_SystemDomain_SystemDomain` FOREIGN KEY (`SystemDomain`) REFERENCES `SystemDomain` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='批处理任务表';

-- ----------------------------
-- Table structure for BatchTemplate
-- ----------------------------
DROP TABLE IF EXISTS `BatchTemplate`;
CREATE TABLE `BatchTemplate` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增长ID',
  `TemplateTitle` varchar(30) DEFAULT NULL COMMENT '批处理模板名称',
  `TemplateCode` varchar(30) DEFAULT NULL COMMENT '模板Code值',
  `TemplateParam` text COMMENT '模板参数',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `TemplateType` text COMMENT '条件类型',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='批处理模板表';

-- ----------------------------
-- Table structure for BindingCondition
-- ----------------------------
DROP TABLE IF EXISTS `BindingCondition`;
CREATE TABLE `BindingCondition` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Title` varchar(30) NOT NULL,
  `Type` varchar(20) NOT NULL,
  `CreateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for BindingType
-- ----------------------------
DROP TABLE IF EXISTS `BindingType`;
CREATE TABLE `BindingType` (
  `Id` int(11) NOT NULL COMMENT '系统自增长Id',
  `TypeTitle` varchar(30) DEFAULT NULL COMMENT '游戏 活动 视频',
  `TypeCode` varchar(20) DEFAULT NULL COMMENT 'GAME \nPROMOTION \nVIDEO\nVIP',
  `SystemDomain` int(11) DEFAULT NULL COMMENT '系统Id',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='绑定类型初始化数据表';

-- ----------------------------
-- Table structure for Channel
-- ----------------------------
DROP TABLE IF EXISTS `Channel`;
CREATE TABLE `Channel` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `ChannelName` varchar(40) DEFAULT NULL COMMENT '渠道名称',
  `ChannelLongName` varchar(180) DEFAULT NULL COMMENT '渠道全称',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `GateWay` varchar(45) DEFAULT NULL COMMENT '该渠道是否支持网关：Y：是；N：否；',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用于保存渠道商的基本信息';

-- ----------------------------
-- Table structure for ChannelPayType
-- ----------------------------
DROP TABLE IF EXISTS `ChannelPayType`;
CREATE TABLE `ChannelPayType` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `PayType` int(11) NOT NULL,
  `Channel` int(11) DEFAULT NULL,
  `Priority` int(11) DEFAULT NULL COMMENT '支付方式优先级\n例如：0，1 \n数字越小优先级越高',
  `NextStep` varchar(8) DEFAULT NULL COMMENT '如果当前支付方式支付失败或无法支付，是否顺延到下一个支付方式进行支付。\nC - Continue 继续使用下一种方式支付\nN - Not 不再继续支付，返回错误信息给客户端',
  `Status` varchar(8) DEFAULT NULL COMMENT '支付方式状态\nN - Normal 代表正常\nF - Freeze 代表冻结，不可用',
  `CreateTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_ChannelPayType_PayType1_idx` (`PayType`),
  CONSTRAINT `fk_ChannelPayType_PayType1` FOREIGN KEY (`PayType`) REFERENCES `PayType` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ChannelSystem
-- ----------------------------
DROP TABLE IF EXISTS `ChannelSystem`;
CREATE TABLE `ChannelSystem` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `Channel` int(11) DEFAULT NULL COMMENT '渠道Id',
  `SystemDomain` int(11) DEFAULT NULL COMMENT '系统Id',
  `Status` varchar(10) DEFAULT NULL COMMENT '状态   0 :禁用  1: 启用',
  `CreateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_ChannelSystem_Channel_idx` (`Channel`),
  KEY `fk_ChannelSystem_SysteDomain_idx` (`SystemDomain`),
  CONSTRAINT `fk_ChannelSystem_Channel` FOREIGN KEY (`Channel`) REFERENCES `Channel` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ChannelSystem_SysteDomain` FOREIGN KEY (`SystemDomain`) REFERENCES `SystemDomain` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ChannelSystemFeatureItem
-- ----------------------------
DROP TABLE IF EXISTS `ChannelSystemFeatureItem`;
CREATE TABLE `ChannelSystemFeatureItem` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Channel` int(11) DEFAULT NULL COMMENT '所属渠道Id',
  `SystemDomain` int(11) DEFAULT NULL COMMENT '所属系统Id',
  `FeatureCode` varchar(45) DEFAULT NULL COMMENT '属性代号',
  `FeatureValue` varchar(45) DEFAULT NULL COMMENT '属性值',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`Id`),
  KEY `fk_ChannelSysteFeatureItem_Channel_idx` (`Channel`),
  KEY `fk_ChannelSystemFeatureItem_SystemDomain_idx` (`SystemDomain`),
  CONSTRAINT `fk_ChannelSystemFeatureItem_Channel` FOREIGN KEY (`Channel`) REFERENCES `Channel` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ChannelSystemFeatureItem_SystemDomain` FOREIGN KEY (`SystemDomain`) REFERENCES `SystemDomain` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='渠道大厅属性表';

-- ----------------------------
-- Table structure for CP
-- ----------------------------
DROP TABLE IF EXISTS `CP`;
CREATE TABLE `CP` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键- 系统自增长',
  `CPName` varchar(40) DEFAULT NULL COMMENT '开发者名称',
  `CPLongName` varchar(128) DEFAULT NULL COMMENT '开发者全名称',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用于保存游戏开发者基本信息';

-- ----------------------------
-- Table structure for Device
-- ----------------------------
DROP TABLE IF EXISTS `Device`;
CREATE TABLE `Device` (
  `Id` int(10) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `TypeTitle` varchar(45) DEFAULT 'N/A' COMMENT '机型的名称，对应DeviceType中的TypeTitle',
  `TypeCode` varchar(45) DEFAULT 'N/A' COMMENT '设备机型代码，对应DeviceType表中的TypeCode',
  `PlatType` varchar(8) DEFAULT NULL COMMENT '设备平台类型（如TV，PHONE，PAD）',
  `Software` varchar(100) DEFAULT NULL COMMENT '设备的软件信息，比如：安卓6.0 rom 2G',
  `Hardware` varchar(100) DEFAULT NULL COMMENT '设备硬件信息',
  `Resolution` varchar(45) DEFAULT NULL COMMENT '设备屏幕分辨率',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `Channel` int(11) DEFAULT NULL,
  `DeviceType` int(11) DEFAULT NULL,
  `DeviceMark` varchar(64) DEFAULT NULL,
  `DemoFlag` varchar(8) DEFAULT NULL COMMENT 'Y 代表使用Demo数据\nN 代表不使用Demo数据\n',
  `Status` varchar(16) DEFAULT NULL,
  `User` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_Device_Channel1_idx` (`Channel`),
  KEY `fk_Device_DeviceType1_idx` (`DeviceType`),
  CONSTRAINT `fk_Device_Channel1` FOREIGN KEY (`Channel`) REFERENCES `Channel` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Device_DeviceType1` FOREIGN KEY (`DeviceType`) REFERENCES `DeviceType` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用于保存设备基本信息，每台设备一条数据';

-- ----------------------------
-- Table structure for DeviceClass
-- ----------------------------
DROP TABLE IF EXISTS `DeviceClass`;
CREATE TABLE `DeviceClass` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '机型配置Id - 系统自增长',
  `ClassTitle` varchar(45) DEFAULT 'N/A' COMMENT '机型的配置名称',
  `PlatType` varchar(8) DEFAULT NULL COMMENT '设备平台类型（如 TV ,PHONE,PAD）',
  `Resolution` varchar(10) DEFAULT 'N/A' COMMENT '系统分辨率',
  `SystemVersion` varchar(10) DEFAULT 'N/A' COMMENT '系统版本',
  `RAM` varchar(6) DEFAULT 'N/A' COMMENT '运行内存',
  `ROM` varchar(4) DEFAULT NULL COMMENT '只读内存，相当于PC上的硬盘',
  `CPU` varchar(40) DEFAULT NULL COMMENT 'CPU型号',
  `GPU` varchar(45) DEFAULT NULL,
  `ChipVersion` varchar(45) DEFAULT NULL COMMENT '芯片型号',
  `Bluetooth` varchar(45) DEFAULT NULL COMMENT '是否支持蓝牙，“Y”表示支付；“N”表示不支持',
  `Wifi` varchar(45) DEFAULT NULL COMMENT '是否支持Wifi。“Y”代表支持；“N”代表不支持',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用于保存机型级别的基本信息';

-- ----------------------------
-- Table structure for DeviceFeatureItem
-- ----------------------------
DROP TABLE IF EXISTS `DeviceFeatureItem`;
CREATE TABLE `DeviceFeatureItem` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `FeatureType` varchar(45) DEFAULT NULL COMMENT '属性类型',
  `FeatureValue` varchar(45) DEFAULT NULL COMMENT '属性值',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `DeviceType` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_DeviceFeatureItem_DeviceType1_idx` (`DeviceType`),
  CONSTRAINT `fk_DeviceFeatureItem_DeviceType1` FOREIGN KEY (`DeviceType`) REFERENCES `DeviceType` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for DeviceFeatureLookup
-- ----------------------------
DROP TABLE IF EXISTS `DeviceFeatureLookup`;
CREATE TABLE `DeviceFeatureLookup` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Code` varchar(40) NOT NULL COMMENT '属性Code值',
  `Name` varchar(30) DEFAULT NULL COMMENT '属性名称',
  `Time` varchar(10) DEFAULT NULL COMMENT '间隔时间',
  `Unit` varchar(10) DEFAULT NULL COMMENT '时间单位',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for DeviceType
-- ----------------------------
DROP TABLE IF EXISTS `DeviceType`;
CREATE TABLE `DeviceType` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `TypeTitle` varchar(40) DEFAULT NULL COMMENT '设备类型名称 （如 1x2-480x640, 2x8-1024x1289)',
  `TypeCode` varchar(40) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `Channel` int(11) DEFAULT NULL,
  `DeviceClass` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_DeviceType_Channel1_idx` (`Channel`),
  KEY `fk_DeviceType_DeviceClass1_idx` (`DeviceClass`),
  CONSTRAINT `fk_DeviceType_Channel1` FOREIGN KEY (`Channel`) REFERENCES `Channel` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_DeviceType_DeviceClass1` FOREIGN KEY (`DeviceClass`) REFERENCES `DeviceClass` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用于保存机型的基本信息';

-- ----------------------------
-- Table structure for DialogPage
-- ----------------------------
DROP TABLE IF EXISTS `DialogPage`;
CREATE TABLE `DialogPage` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `DialogTitle` varchar(45) DEFAULT NULL COMMENT '弹窗名称',
  `DialogSubTitle` varchar(45) DEFAULT NULL COMMENT '弹窗标题',
  `DialogType` varchar(30) DEFAULT NULL COMMENT '弹窗类型：\nFULL-全屏覆盖\nFLOAT – 漂浮',
  `BckgrndPic` varchar(128) DEFAULT NULL COMMENT '弹窗的背景图片地址',
  `ExpDate` datetime DEFAULT NULL,
  `ContentTitle` varchar(45) DEFAULT NULL COMMENT '弹窗内容标题',
  `ContentText` varchar(1000) DEFAULT NULL COMMENT '弹窗内容文字',
  `ContentPic` varchar(45) DEFAULT NULL COMMENT '弹窗内容图片地址',
  `ObjectType` varchar(30) DEFAULT NULL COMMENT '弹窗嵌入的元素类型\nINFORMATION – 只含有信息，没有其他逻辑元素\n<其他> – 见系统模块标识 (如用户登录、注册、支付) ',
  `ShowCondition` varchar(30) DEFAULT NULL COMMENT '何时显示该弹窗：\nNONE – 无条件显示\nVIP – VIP用户界面时显示\nNOVIP- 非VIP用户时显示\nGUESS – 非注册用户时显示',
  `BindingPage` varchar(128) DEFAULT NULL COMMENT '弹窗隶属的页面',
  `ActionType` varchar(30) DEFAULT NULL COMMENT '动作类型：\n0:没有任何动作，直接关闭。（关闭后，后续窗口不会弹出）\n1:去指定的大厅页面，例如：活动，商城，游戏详情等，去指定游戏详情页面时，Content参数为游戏id。\n2:去指定URL页面，与下方url配对。',
  `ActionContent` varchar(45) DEFAULT NULL COMMENT 'ActionType为1时指定的大厅页面',
  `ActionH5Url` varchar(512) DEFAULT NULL,
  `ActionId` int(9) DEFAULT NULL COMMENT '跳转参数的目标Id或游戏、活动Id',
  `Count` int(9) DEFAULT NULL COMMENT '该弹窗在有效期内的显示次数：\n0：无限制\n其他：该弹窗在有效期内的显示次数',
  `Status` varchar(45) DEFAULT NULL COMMENT '0 :已发布\n1：未发布',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `SystemDomain` int(11) DEFAULT NULL COMMENT '所属系统',
  `JumpType` varchar(45) DEFAULT NULL COMMENT '动作类型: E:活动；G：游戏；V：视频；P：商品；B：游戏包；O：排行榜；A：广告',
  `ActionTitle` varchar(45) DEFAULT NULL,
  `ButtonData` text,
  `VersionCode` int(11) DEFAULT NULL COMMENT '当前弹窗的版本号',
  `ToPublishTime` datetime DEFAULT NULL COMMENT '预发布时间',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for DRTData
-- ----------------------------
DROP TABLE IF EXISTS `DRTData`;
CREATE TABLE `DRTData` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `SystemDomain` int(11) DEFAULT NULL COMMENT '系统Id',
  `User` int(11) DEFAULT NULL COMMENT '用户Id',
  `DeviceType` int(11) DEFAULT NULL,
  `DataType` varchar(45) DEFAULT NULL COMMENT '数据类型',
  `DataValue` text COMMENT '数据值',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`Id`),
  KEY `fk_DRTData_SystemDomian_idx` (`SystemDomain`),
  KEY `fk_DRTData_DeviceType_idx` (`DeviceType`),
  KEY `index_User` (`User`),
  CONSTRAINT `fk_DRTData_DeviceType` FOREIGN KEY (`DeviceType`) REFERENCES `DeviceType` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_DRTData_SystemDomian` FOREIGN KEY (`SystemDomain`) REFERENCES `SystemDomain` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for EndUser
-- ----------------------------
DROP TABLE IF EXISTS `EndUser`;
CREATE TABLE `EndUser` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ExtraId` varchar(45) DEFAULT NULL COMMENT '第三方系统的用户ID，例如：广电的smartCard,微信的unionid',
  `LoginName` varchar(32) DEFAULT NULL COMMENT '用户名-用于登录',
  `Password` varchar(40) DEFAULT NULL COMMENT '密码',
  `NickName` varchar(32) DEFAULT NULL COMMENT '昵称',
  `UserName` varchar(40) DEFAULT NULL COMMENT '姓名',
  `Email` varchar(256) DEFAULT NULL COMMENT '邮箱地址',
  `Phone` varchar(32) DEFAULT NULL COMMENT '手机号',
  `Sex` varchar(10) DEFAULT NULL COMMENT '性别',
  `Age` varchar(10) DEFAULT NULL COMMENT '年龄',
  `Birthday` varchar(20) DEFAULT NULL COMMENT '生日',
  `Address` varchar(256) DEFAULT NULL COMMENT '地址',
  `QQ` varchar(20) DEFAULT NULL COMMENT 'QQ号',
  `Wechat` varchar(20) DEFAULT NULL COMMENT '微信号',
  `Icon` varchar(254) DEFAULT NULL COMMENT '用户头像',
  `LastLogin` datetime DEFAULT NULL COMMENT '最后登录时间',
  `LastDomain` int(9) DEFAULT NULL COMMENT '最后登录应用系统（如双屏，亲子等）',
  `IPAddress` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `Province` varchar(32) DEFAULT NULL COMMENT '用户所在省',
  `City` varchar(32) DEFAULT NULL COMMENT '城市名',
  `Token` varchar(1024) DEFAULT NULL COMMENT 'TOKEN',
  `PKey` varchar(256) DEFAULT NULL COMMENT '用户私钥',
  `Status` varchar(10) DEFAULT NULL COMMENT '状态，(如： 正常 0，冻结 1）',
  `WechatName` varchar(32) DEFAULT NULL COMMENT '微信昵称',
  `SecurityQestion` varchar(60) DEFAULT NULL COMMENT '用户自己设置的问题-用于密码重置',
  `SecurityAnswer` varchar(60) DEFAULT NULL COMMENT '用户自己设置问题的答案-用于密码重置',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `BindingPhoneTime` datetime DEFAULT NULL COMMENT '绑定手机的时间',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for FeatureLookup
-- ----------------------------
DROP TABLE IF EXISTS `FeatureLookup`;
CREATE TABLE `FeatureLookup` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID，自增',
  `FeatureTitle` varchar(20) DEFAULT 'N/A' COMMENT '特征名称',
  `FeatureCode` varchar(20) DEFAULT 'N/A' COMMENT '特征属性英文名',
  `Value` varchar(20) DEFAULT 'N/A' COMMENT '特征属性值',
  `Type` varchar(20) DEFAULT 'N/A' COMMENT '特征类型',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用于保存游戏的动态属性。';

-- ----------------------------
-- Table structure for FileLookup
-- ----------------------------
DROP TABLE IF EXISTS `FileLookup`;
CREATE TABLE `FileLookup` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID，系统自增',
  `FileTitle` varchar(45) DEFAULT 'N/A' COMMENT '属性名称，比如：大图，小图',
  `FileCode` varchar(45) DEFAULT 'N/A' COMMENT '文件类型, 如Icon, Apk，Image',
  `Format` varchar(45) DEFAULT 'N/A' COMMENT '文件格式，多个用‘，’隔开。比如 Jpg,png',
  `Pixel` varchar(45) DEFAULT NULL COMMENT '图片像素',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `Type` varchar(45) DEFAULT NULL COMMENT '类型： file代表文件   path 代表地址',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用于保存游戏的文件属性。';

-- ----------------------------
-- Table structure for Game
-- ----------------------------
DROP TABLE IF EXISTS `Game`;
CREATE TABLE `Game` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统自增长，游戏ID，主键',
  `Title` varchar(120) DEFAULT NULL COMMENT '游戏名称',
  `IconUrl` varchar(256) DEFAULT NULL COMMENT '游戏图标地址',
  `TitlePinYin` varchar(20) DEFAULT NULL COMMENT '游戏名称拼音',
  `PackageTitle` varchar(128) DEFAULT NULL COMMENT '游戏包名',
  `GameFile` varchar(3000) DEFAULT NULL,
  `Size` varchar(10) DEFAULT NULL COMMENT '游戏大小 单位MB',
  `Portal` int(9) DEFAULT NULL COMMENT '游戏场景入口ID，（预留）',
  `Price` int(9) DEFAULT NULL COMMENT '游戏价格',
  `PriceType` int(9) DEFAULT NULL COMMENT '游戏收费方式',
  `VersionTitle` varchar(256) DEFAULT NULL COMMENT '版本名称',
  `VersionCode` int(9) DEFAULT NULL COMMENT '版本号',
  `GameTypeList` varchar(120) DEFAULT NULL COMMENT 'CP建议游戏类型,冒险,设计...',
  `Remark` varchar(500) DEFAULT NULL COMMENT '游戏描述',
  `Status` varchar(10) DEFAULT '0' COMMENT '游戏状态  1：待审核  2：待发布  3：未通过   4：已发布  5：下架  6：未配置',
  `DownloadCount` int(9) DEFAULT '0' COMMENT '下载次数',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `CP` int(11) DEFAULT NULL,
  `SystemDomain` int(11) DEFAULT NULL,
  `GameType` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_Game_CP1_idx` (`CP`),
  KEY `fk_Game_SystemDomain1_idx` (`SystemDomain`),
  CONSTRAINT `fk_Game_CP1` FOREIGN KEY (`CP`) REFERENCES `CP` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Game_SystemDomain1` FOREIGN KEY (`SystemDomain`) REFERENCES `SystemDomain` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用于保存游戏基础信息。';

-- ----------------------------
-- Table structure for GameActionHist
-- ----------------------------
DROP TABLE IF EXISTS `GameActionHist`;
CREATE TABLE `GameActionHist` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID系统自增',
  `Operator` varchar(20) DEFAULT NULL COMMENT '操作人',
  `OperateType` varchar(15) DEFAULT NULL COMMENT '操作类型  add 添加游戏 \n                  update 修改游戏\n                  approve 审核游戏\n                  set 配置游戏\n                  publish 发布游戏',
  `Remark` varchar(3000) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `Game` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_GameActionHist_Game1_idx` (`Game`),
  CONSTRAINT `fk_GameActionHist_Game1` FOREIGN KEY (`Game`) REFERENCES `Game` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用于保存游戏操作和记录信息';

-- ----------------------------
-- Table structure for GameArchives
-- ----------------------------
DROP TABLE IF EXISTS `GameArchives`;
CREATE TABLE `GameArchives` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `User` int(11) DEFAULT NULL,
  `Game` int(11) DEFAULT NULL,
  `Archive` text,
  `CreateTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  `ExteriorUID` varchar(32) DEFAULT NULL COMMENT '外部用户Id',
  PRIMARY KEY (`Id`),
  KEY `GameArchives_User_idx` (`User`),
  KEY `GameArchives_Game_idx` (`Game`),
  CONSTRAINT `fk_GameArchives_Game` FOREIGN KEY (`Game`) REFERENCES `Game` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for GameDevice
-- ----------------------------
DROP TABLE IF EXISTS `GameDevice`;
CREATE TABLE `GameDevice` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Status` varchar(15) DEFAULT 'N/A' COMMENT '游戏在设备上的发布状态\nP：已发布  S：已配置  O：已下架',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `Game` int(11) DEFAULT NULL,
  `DeviceType` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_GameDevice_Game1_idx` (`Game`),
  KEY `fk_GameDevice_DeviceType1_idx` (`DeviceType`),
  CONSTRAINT `fk_GameDevice_DeviceType1` FOREIGN KEY (`DeviceType`) REFERENCES `DeviceType` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_GameDevice_Game1` FOREIGN KEY (`Game`) REFERENCES `Game` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用于保存游戏发布到渠道的关联信息';

-- ----------------------------
-- Table structure for GameFeatureItem
-- ----------------------------
DROP TABLE IF EXISTS `GameFeatureItem`;
CREATE TABLE `GameFeatureItem` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID，自增',
  `FeatureCode` varchar(20) DEFAULT NULL COMMENT '特征代码值',
  `FeatureValue` varchar(20) DEFAULT NULL COMMENT '特征值',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改更新时间',
  `Game` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_GameFeatureItem_Game1_idx` (`Game`),
  CONSTRAINT `fk_GameFeatureItem_Game1` FOREIGN KEY (`Game`) REFERENCES `Game` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用于记录游戏的属性。';

-- ----------------------------
-- Table structure for GameFileItem
-- ----------------------------
DROP TABLE IF EXISTS `GameFileItem`;
CREATE TABLE `GameFileItem` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID系统自增',
  `FileType` varchar(20) DEFAULT NULL COMMENT '文件类型',
  `FilePath` varchar(3000) DEFAULT NULL COMMENT '文件地址',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `Game` int(11) DEFAULT NULL,
  `Type` varchar(10) DEFAULT NULL COMMENT '上传文件的类型  文件：FILE   地址:PATH',
  PRIMARY KEY (`Id`),
  KEY `fk_GameFileItem_Game1_idx` (`Game`),
  CONSTRAINT `fk_GameFileItem_Game1` FOREIGN KEY (`Game`) REFERENCES `Game` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用于记录游戏相关文件录的属性。';

-- ----------------------------
-- Table structure for GameHistory
-- ----------------------------
DROP TABLE IF EXISTS `GameHistory`;
CREATE TABLE `GameHistory` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID系统自增',
  `Title` varchar(40) DEFAULT NULL COMMENT '游戏名称',
  `CP` int(11) DEFAULT NULL COMMENT 'CP商ID',
  `TitlePinYin` varchar(20) DEFAULT NULL COMMENT '游戏拼音',
  `PackageTitle` varchar(128) DEFAULT NULL COMMENT '游戏包名',
  `GameFile` varchar(3000) DEFAULT NULL COMMENT '游戏包存储路径',
  `Size` bigint(12) DEFAULT NULL COMMENT '游戏大小',
  `HostPlatform` varchar(8) DEFAULT NULL COMMENT '游戏运行平台  （TV，   PC，   移动）',
  `VersionTitle` varchar(256) DEFAULT NULL COMMENT '版本名称',
  `VersionCode` int(9) DEFAULT NULL COMMENT '版本号',
  `Remark` varchar(500) DEFAULT NULL COMMENT '游戏描述',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdaTime` datetime DEFAULT NULL COMMENT '修改时间',
  `Game` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_GameHistory_Game1_idx` (`Game`),
  CONSTRAINT `fk_GameHistory_Game1` FOREIGN KEY (`Game`) REFERENCES `Game` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用于保存游戏历史发布信息。';

-- ----------------------------
-- Table structure for GamePackage
-- ----------------------------
DROP TABLE IF EXISTS `GamePackage`;
CREATE TABLE `GamePackage` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `PackageTitle` varchar(200) DEFAULT 'N/A' COMMENT '游戏包标题',
  `PackageCode` varchar(20) DEFAULT NULL,
  `PackageIcon` varchar(512) DEFAULT NULL COMMENT '游戏包Icon',
  `Remark` varchar(200) DEFAULT NULL COMMENT '游戏包描述',
  `Price` int(9) DEFAULT NULL COMMENT '游戏包售价，单位为 分',
  `Status` varchar(15) DEFAULT NULL COMMENT '1：未配置 ；2 未发布 ；0 已发布',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `PackageType` varchar(10) DEFAULT NULL,
  `SystemDomain` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_GamePackage_SystemDomain1_idx` (`SystemDomain`),
  CONSTRAINT `fk_GamePackage_SystemDomain1` FOREIGN KEY (`SystemDomain`) REFERENCES `SystemDomain` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用于记录游戏包信息';

-- ----------------------------
-- Table structure for GamePackageDevice
-- ----------------------------
DROP TABLE IF EXISTS `GamePackageDevice`;
CREATE TABLE `GamePackageDevice` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Status` varchar(10) DEFAULT 'N/A' COMMENT '游戏包在设备上的发布状态\nP：已发布  S：已配置',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `GamePackage` int(11) DEFAULT NULL,
  `DeviceType` int(11) DEFAULT NULL,
  `Channel` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_GamePakageDevice_GamePackage1_idx` (`GamePackage`),
  KEY `fk_GamePakageDevice_DeviceType1_idx` (`DeviceType`),
  KEY `fk_GamePakageDevice_Channel1_idx` (`Channel`),
  CONSTRAINT `fk_GamePakageDevice_Channel1` FOREIGN KEY (`Channel`) REFERENCES `Channel` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_GamePakageDevice_DeviceType1` FOREIGN KEY (`DeviceType`) REFERENCES `DeviceType` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_GamePakageDevice_GamePackage1` FOREIGN KEY (`GamePackage`) REFERENCES `GamePackage` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用于记录游戏包与机型配置的关联关系';

-- ----------------------------
-- Table structure for GamePackageHistory
-- ----------------------------
DROP TABLE IF EXISTS `GamePackageHistory`;
CREATE TABLE `GamePackageHistory` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '操作记录 - 操作记录Id，系统自增长',
  `Operator` varchar(20) DEFAULT NULL COMMENT '操作人',
  `OperateType` varchar(15) DEFAULT NULL COMMENT '操作类型：add 打包游戏 \n          update 修改游戏包\n          set 配置游戏包\n         publish 发布游戏包',
  `CreateTime` datetime DEFAULT NULL COMMENT '审批时间',
  `Remark` varchar(300) DEFAULT NULL COMMENT '操作备注',
  `GamePackage` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_GamePackageHistory_GamePackage1_idx` (`GamePackage`),
  CONSTRAINT `fk_GamePackageHistory_GamePackage1` FOREIGN KEY (`GamePackage`) REFERENCES `GamePackage` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用于保存游戏操作核记录信息';

-- ----------------------------
-- Table structure for GamePackageItem
-- ----------------------------
DROP TABLE IF EXISTS `GamePackageItem`;
CREATE TABLE `GamePackageItem` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `WeightRatio` int(2) DEFAULT NULL COMMENT '游戏比重1-99整数',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `GamePackage` int(11) DEFAULT NULL COMMENT '游戏包Id',
  `Game` int(11) DEFAULT NULL COMMENT '游戏Id',
  `GameTitle` varchar(45) DEFAULT NULL COMMENT '游戏名称',
  `PayLock` varchar(10) DEFAULT NULL COMMENT '锁\n锁定  LOCK\n解锁  UNLOCK ',
  `TimeCondition` varchar(10) DEFAULT NULL COMMENT '解锁绘本的时间条件\n0:无时间限制\n24:   24:00解锁',
  `RankingNo` int(9) DEFAULT NULL COMMENT '排序号',
  PRIMARY KEY (`Id`),
  KEY `fk_GamePackageItem_GamePackage1_idx` (`GamePackage`),
  KEY `fk_GamePackageItem_Game1_idx` (`Game`),
  CONSTRAINT `fk_GamePackageItem_Game1` FOREIGN KEY (`Game`) REFERENCES `Game` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_GamePackageItem_GamePackage1` FOREIGN KEY (`GamePackage`) REFERENCES `GamePackage` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用于记录游戏包与游戏的关联关系';

-- ----------------------------
-- Table structure for GameProgress
-- ----------------------------
DROP TABLE IF EXISTS `GameProgress`;
CREATE TABLE `GameProgress` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `User` int(11) DEFAULT NULL COMMENT '用户Id',
  `Game` int(11) DEFAULT NULL COMMENT '游戏Id',
  `GamePackage` int(11) DEFAULT NULL COMMENT '游戏包Id',
  `EndTime` datetime DEFAULT NULL COMMENT '完成时间',
  `CreateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for GameRelation
-- ----------------------------
DROP TABLE IF EXISTS `GameRelation`;
CREATE TABLE `GameRelation` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `ParentId` int(11) DEFAULT NULL COMMENT '父游戏Id',
  `SubId` int(11) DEFAULT NULL COMMENT '子游戏Id',
  `CreateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_GameRelation_Game_1_idx` (`ParentId`),
  KEY `fk_GameRelation_Game_2_idx` (`SubId`),
  CONSTRAINT `fk_GameRelation_Game_1` FOREIGN KEY (`ParentId`) REFERENCES `Game` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_GameRelation_Game_2` FOREIGN KEY (`SubId`) REFERENCES `Game` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for GameTypeItem
-- ----------------------------
DROP TABLE IF EXISTS `GameTypeItem`;
CREATE TABLE `GameTypeItem` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `CreateTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  `Game` int(11) DEFAULT NULL,
  `GameTypeLookup` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_GameTypeItem_Game1_idx` (`Game`),
  KEY `fk_GameTypeItem_GameTypeLookup1_idx` (`GameTypeLookup`),
  CONSTRAINT `fk_GameTypeItem_Game1` FOREIGN KEY (`Game`) REFERENCES `Game` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_GameTypeItem_GameTypeLookup1` FOREIGN KEY (`GameTypeLookup`) REFERENCES `GameTypeLookup` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for GameTypeLookup
-- ----------------------------
DROP TABLE IF EXISTS `GameTypeLookup`;
CREATE TABLE `GameTypeLookup` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '游戏类型Id，主键',
  `TypeCode` varchar(45) DEFAULT NULL COMMENT '类型代码',
  `TypeValue` varchar(45) DEFAULT NULL COMMENT '类型名称',
  `Icon` varchar(256) DEFAULT NULL COMMENT '类型图标',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `Remark` varchar(512) DEFAULT NULL,
  `SystemDomain` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_GameTypeLookup_SystemDomain1_idx` (`SystemDomain`),
  CONSTRAINT `fk_GameTypeLookup_SystemDomain1` FOREIGN KEY (`SystemDomain`) REFERENCES `SystemDomain` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for Goods
-- ----------------------------
DROP TABLE IF EXISTS `Goods`;
CREATE TABLE `Goods` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统自增长Id',
  `GoodsTitle` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `GoodsNo` varchar(50) DEFAULT NULL COMMENT '商品货号',
  `Type` varchar(11) DEFAULT NULL COMMENT '商品类型\n“VIP”:VIP\n“GIFT”：礼包\n“ENTITY”：实物',
  `Icon` varchar(256) DEFAULT NULL COMMENT '商品图标',
  `PeriodCount` int(11) DEFAULT NULL,
  `RestockPrice` int(9) DEFAULT NULL COMMENT '回收价格（金币）',
  `Quantity` int(11) DEFAULT NULL COMMENT '商品总数',
  `AvalQuantity` int(11) DEFAULT NULL COMMENT '商品剩余数量',
  `BuyLimit` int(11) DEFAULT NULL COMMENT '商品限购数量',
  `Url` varchar(256) DEFAULT NULL COMMENT '商品详情的H5链接',
  `Remark` varchar(500) DEFAULT NULL COMMENT '商品描述',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `Pic` varchar(500) DEFAULT NULL COMMENT '商品详情图   ',
  `ExtraId` int(11) DEFAULT NULL COMMENT '商品的外部ID。例如当MerchType=GAME时，ExtraId即为游戏的ID',
  `PeriodType` varchar(11) DEFAULT NULL COMMENT '时长类型:“MONTHLY”:包月;“THREE”:3个月;“SIX”:6个月;“YEARLY”:包年;',
  `PriceCurrency` varchar(256) DEFAULT NULL COMMENT '商品的收费方式和价格信息（使用json格式存储）收费方式："R":人民币；“C”:"金币"；“D”:“钻石”',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='VIP（商品）信息';

-- ----------------------------
-- Table structure for GoodsMall
-- ----------------------------
DROP TABLE IF EXISTS `GoodsMall`;
CREATE TABLE `GoodsMall` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统id',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `Mall` int(11) DEFAULT NULL,
  `Goods` int(11) DEFAULT NULL COMMENT '上品id',
  PRIMARY KEY (`Id`),
  KEY `fk_GoodsMall_Mall1_idx` (`Mall`),
  KEY `fk_GoodsMall_idx` (`Goods`),
  CONSTRAINT `fk_GoodsMall` FOREIGN KEY (`Goods`) REFERENCES `Goods` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_GoodsMall_Mall` FOREIGN KEY (`Mall`) REFERENCES `Mall` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for GoodsType
-- ----------------------------
DROP TABLE IF EXISTS `GoodsType`;
CREATE TABLE `GoodsType` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统自增长',
  `Title` varchar(50) DEFAULT NULL COMMENT '商品类型名称',
  `Type` varchar(11) DEFAULT NULL COMMENT '商品类型\n“V”：VIP\n“G”：礼包\n“E”：实物',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品类型表';

-- ----------------------------
-- Table structure for GridContent
-- ----------------------------
DROP TABLE IF EXISTS `GridContent`;
CREATE TABLE `GridContent` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统自增长Id',
  `GridElement` int(11) DEFAULT NULL COMMENT '所属布局Id',
  `Type` varchar(20) DEFAULT NULL COMMENT '布局类型\n游戏：G\n活动：P\n视频：V',
  `Icon` varchar(128) DEFAULT NULL COMMENT '布局图标',
  `Url` varchar(512) DEFAULT NULL COMMENT 'H5链接',
  `ContentId` int(11) DEFAULT NULL COMMENT '内容Id',
  `ContentTitle` varchar(30) DEFAULT NULL COMMENT '内容名称',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `Jump` varchar(10) DEFAULT NULL COMMENT '跳转方式',
  `JumpUrl` varchar(512) DEFAULT NULL COMMENT '跳转路径',
  `ActionType` varchar(100) DEFAULT NULL COMMENT '活动类型',
  `PageCode` varchar(128) DEFAULT NULL COMMENT '页面Code值',
  `ShowMode` varchar(11) DEFAULT NULL COMMENT '显示模式',
  PRIMARY KEY (`Id`),
  KEY `fk_LayoutElementContent_LayoutElement_idx` (`GridElement`),
  CONSTRAINT `fk_LayoutElementContent_LayoutElement` FOREIGN KEY (`GridElement`) REFERENCES `GridElement` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='布局元素内容表';

-- ----------------------------
-- Table structure for GridElement
-- ----------------------------
DROP TABLE IF EXISTS `GridElement`;
CREATE TABLE `GridElement` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统自增长Id',
  `GridLayout` int(11) DEFAULT NULL,
  `SortNumber` int(9) DEFAULT NULL COMMENT '布局元素的排序号',
  `Row` int(10) DEFAULT NULL COMMENT '元素所在行',
  `Col` int(10) DEFAULT NULL COMMENT '元素所在列',
  `Width` int(10) DEFAULT NULL COMMENT '元素宽',
  `Height` int(10) DEFAULT NULL COMMENT '元素高',
  `RowSpan` int(10) DEFAULT NULL COMMENT '元素占几行',
  `ColSpan` int(10) DEFAULT NULL COMMENT '元素占几列',
  `PageNum` int(10) DEFAULT NULL COMMENT '第几页',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`Id`),
  KEY `fk_LayoutElement_Layout_idx` (`GridLayout`),
  CONSTRAINT `fk_LayoutElement_Layout` FOREIGN KEY (`GridLayout`) REFERENCES `GridLayout` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=895 DEFAULT CHARSET=utf8 COMMENT='布局元素表';

-- ----------------------------
-- Table structure for GridElementType
-- ----------------------------
DROP TABLE IF EXISTS `GridElementType`;
CREATE TABLE `GridElementType` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统自增长Id',
  `TypeTitle` varchar(30) DEFAULT NULL COMMENT '游戏 活动 视频',
  `TypeCode` varchar(20) DEFAULT NULL COMMENT 'GAME \nPROMOTION \nVIDEO\nVIP',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='绑定类型初始化数据表';

-- ----------------------------
-- Table structure for GridLayout
-- ----------------------------
DROP TABLE IF EXISTS `GridLayout`;
CREATE TABLE `GridLayout` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统自增长Id',
  `Title` varchar(20) DEFAULT NULL COMMENT '条件名称描述',
  `Status` varchar(10) DEFAULT NULL COMMENT '布局状态    未发布和已发布',
  `FlipMode` varchar(11) DEFAULT NULL COMMENT '翻页模式',
  `GridCode` varchar(256) DEFAULT NULL,
  `Rows` int(10) DEFAULT NULL COMMENT '布局行数',
  `Cols` int(10) DEFAULT NULL COMMENT '布局列数',
  `PageSize` int(10) DEFAULT NULL COMMENT '布局页数',
  `SpacingLR` int(10) DEFAULT NULL COMMENT '左右间距',
  `SpacingUD` int(10) DEFAULT NULL COMMENT '上下间距',
  `SystemDomain` int(11) DEFAULT NULL COMMENT '所属系统Id',
  `Page` int(11) DEFAULT NULL COMMENT '所属页面Id',
  `Remark` varchar(500) DEFAULT NULL COMMENT '布局描述',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='布局表';

-- ----------------------------
-- Table structure for HeartData
-- ----------------------------
DROP TABLE IF EXISTS `HeartData`;
CREATE TABLE `HeartData` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Title` varchar(40) DEFAULT NULL COMMENT '服务方法',
  `ObjectType` varchar(10) DEFAULT NULL,
  `ObjectId` int(11) DEFAULT NULL,
  `SystemDomain` int(11) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for Mall
-- ----------------------------
DROP TABLE IF EXISTS `Mall`;
CREATE TABLE `Mall` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `Title` varchar(50) DEFAULT NULL COMMENT '商城名称',
  `Memo` varchar(2000) DEFAULT NULL COMMENT '商城说明',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `SystemDomain` int(11) DEFAULT NULL COMMENT '系统id',
  PRIMARY KEY (`Id`),
  KEY `fk_Mall_idx` (`SystemDomain`),
  CONSTRAINT `fk_Mall` FOREIGN KEY (`SystemDomain`) REFERENCES `SystemDomain` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for Merchandise
-- ----------------------------
DROP TABLE IF EXISTS `Merchandise`;
CREATE TABLE `Merchandise` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `PriceCurrency` varchar(256) DEFAULT NULL COMMENT '商品的收费方式和价格信息（使用json格式存储）\n收费方式：\n‘R’：人民币\n‘C’：金币\n‘D’：钻石',
  `Quantity` int(11) DEFAULT NULL COMMENT '商品总数',
  `Stock` int(11) DEFAULT NULL COMMENT '商品剩余数目',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `Store` int(11) DEFAULT NULL,
  `Goods` int(11) DEFAULT NULL COMMENT '商品id',
  `Status` varchar(10) DEFAULT NULL COMMENT '商品状态',
  PRIMARY KEY (`Id`),
  KEY `fk_Merchandise_Store1_idx` (`Store`),
  KEY `fk_Merchandise_Goods_idx` (`Goods`),
  CONSTRAINT `fk_Merchandise_Goods` FOREIGN KEY (`Goods`) REFERENCES `Goods` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Merchandise_Store1` FOREIGN KEY (`Store`) REFERENCES `Store` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for MerchandiseFeature
-- ----------------------------
DROP TABLE IF EXISTS `MerchandiseFeature`;
CREATE TABLE `MerchandiseFeature` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统自增长Id',
  `Goods` int(11) DEFAULT NULL,
  `FeatureType` varchar(11) DEFAULT NULL COMMENT '商品特征类型：\n‘D：打折\n‘L：标签',
  `URL` varchar(256) DEFAULT NULL COMMENT '商品的角标图片',
  `FeatureValue` varchar(256) DEFAULT NULL COMMENT '商品特征值',
  `StartTime` datetime DEFAULT NULL COMMENT '开始时间',
  `EndTime` datetime DEFAULT NULL COMMENT '结束时间',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `Store` int(11) DEFAULT NULL COMMENT '商店ID',
  PRIMARY KEY (`Id`),
  KEY `fk_GoodsFeature_Goods_idx` (`Goods`),
  CONSTRAINT `fk_GoodsFeature_Goods` FOREIGN KEY (`Goods`) REFERENCES `Goods` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品特征表';

-- ----------------------------
-- Table structure for Message
-- ----------------------------
DROP TABLE IF EXISTS `Message`;
CREATE TABLE `Message` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `Title` varchar(45) DEFAULT NULL COMMENT '消息主题',
  `Type` int(1) DEFAULT NULL COMMENT '消息类型\n    消息类    MESSAGE\n    链接类    H5\n    活动类    ACTIVITY',
  `Icon` varchar(256) DEFAULT NULL COMMENT '消息图标',
  `Content` varchar(1000) DEFAULT NULL COMMENT '消息内容',
  `ExtraId` int(11) DEFAULT NULL COMMENT '活动Id',
  `H5Url` varchar(500) DEFAULT NULL COMMENT 'H5地址',
  `SendTo` varchar(10) DEFAULT NULL COMMENT '发送对象:\n   CHANNEL   渠道\n   ALL            所有用户\n   PARTIAL   部分用户',
  `CreateTime` datetime DEFAULT NULL COMMENT '消息创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '消息修改时间',
  `Status` varchar(10) DEFAULT NULL COMMENT '消息状态',
  `SystemDomain` int(11) DEFAULT NULL COMMENT '消息所属系统',
  `SendMethod` varchar(45) DEFAULT NULL COMMENT '发送方式\n   NORMAL  普通\n   POPUP     弹窗',
  `ToPublishTime` datetime DEFAULT NULL COMMENT '预发布时间',
  `ScrollBar` varchar(500) DEFAULT NULL COMMENT '滚动条',
  PRIMARY KEY (`Id`),
  KEY `fk_Message_SystemDomain_idx` (`SystemDomain`),
  CONSTRAINT `fk_Message_SystemDomain` FOREIGN KEY (`SystemDomain`) REFERENCES `SystemDomain` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用于保存系统下发给用户的消息';

-- ----------------------------
-- Table structure for MessageUser
-- ----------------------------
DROP TABLE IF EXISTS `MessageUser`;
CREATE TABLE `MessageUser` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `Message` int(11) DEFAULT NULL,
  `UserType` varchar(10) DEFAULT NULL COMMENT '用户类型:\n   USER  普通用户\n   CHANNEL  渠道用户',
  `UserId` int(11) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `Status` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_MessageUser_Message1_idx` (`Message`),
  CONSTRAINT `fk_MessageUser_Message1` FOREIGN KEY (`Message`) REFERENCES `Message` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户消息关系表';

-- ----------------------------
-- Table structure for MessCode
-- ----------------------------
DROP TABLE IF EXISTS `MessCode`;
CREATE TABLE `MessCode` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Phone` varchar(20) DEFAULT NULL,
  `Time` int(2) DEFAULT NULL,
  `Code` varchar(6) DEFAULT NULL,
  `SendTime` datetime DEFAULT NULL,
  `Type` varchar(10) DEFAULT NULL COMMENT '验证码类型： R：注册  L：找回密码',
  `Status` int(10) DEFAULT NULL COMMENT '使用状态 ： 0 未使用    1 已使用',
  `UseTime` datetime DEFAULT NULL COMMENT '使用时间',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for OperLog
-- ----------------------------
DROP TABLE IF EXISTS `OperLog`;
CREATE TABLE `OperLog` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `OperData` varchar(512) DEFAULT NULL COMMENT '操作数据',
  `Code` int(11) DEFAULT NULL COMMENT '操作代码',
  `Desc` varchar(256) DEFAULT NULL COMMENT '代码描述信息',
  `LogTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for Order
-- ----------------------------
DROP TABLE IF EXISTS `Order`;
CREATE TABLE `Order` (
  `OrderNo` varchar(40) DEFAULT NULL COMMENT 'ATET订单号',
  `ExternalOrderNo` varchar(120) DEFAULT NULL COMMENT 'CP或商户产生的订单号',
  `PaymentOrderNo` varchar(120) DEFAULT NULL COMMENT '支付渠道产生的订单号',
  `EndUser` int(11) DEFAULT NULL COMMENT '用户ID',
  `ExtraId` varchar(40) DEFAULT NULL COMMENT '第三方平台用户ID 例如广电的智能卡号',
  `Channel` int(11) DEFAULT NULL COMMENT '渠道Id',
  `Device` int(11) DEFAULT NULL COMMENT '设备ID',
  `TypeCode` varchar(40) DEFAULT NULL COMMENT '设备型号',
  `PlatType` varchar(40) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '设备平台类型   TV - 电视  PHONE - 手机   PAD - 平板',
  `NotifyURL` varchar(128) DEFAULT NULL COMMENT '支付完成时通知客户的URL',
  `PrivateInfo` varchar(128) DEFAULT NULL COMMENT '客户私有信息',
  `PayPoint` int(11) DEFAULT NULL COMMENT '支付点',
  `PayPointName` varchar(20) DEFAULT NULL COMMENT '支付点名称',
  `PayPointMoney` int(11) DEFAULT NULL COMMENT '支付点金额，单位为 分',
  `Count` int(9) DEFAULT NULL COMMENT '数量',
  `Amount` int(11) DEFAULT NULL COMMENT '总金额，单位为 分',
  `CurrencyType` varchar(20) DEFAULT NULL COMMENT '交易币种，默认为RMB（人民币）',
  `Game` int(11) DEFAULT NULL COMMENT '游戏ID',
  `GameName` varchar(20) DEFAULT NULL COMMENT '游戏名称',
  `PayType` varchar(20) DEFAULT NULL COMMENT '支付方式',
  `Phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `PayAgent` varchar(20) DEFAULT NULL COMMENT '支付渠道代理商，例如联通VAC下属的2000渠道等',
  `ExtraPayTypeInfo` varchar(512) DEFAULT NULL COMMENT '支付渠道私有信息，例如联通VAC上行短信内容',
  `VersionCode` varchar(8) DEFAULT NULL COMMENT '支付SDK版本号（服务器根据此字段做版本兼容）默认为 3',
  `Status` varchar(20) DEFAULT NULL COMMENT '支付状态：SUCCESS - 支付成功   FAIL - 支付失败',
  `CreateTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  `KeyId` varchar(45) DEFAULT NULL,
  `MerchType` varchar(45) DEFAULT NULL,
  `SystemDomain` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for OrderFeature
-- ----------------------------
DROP TABLE IF EXISTS `OrderFeature`;
CREATE TABLE `OrderFeature` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `OrderNo` varchar(20) DEFAULT NULL,
  `VersionCode` varchar(10) DEFAULT NULL,
  `IsScanPay` int(1) DEFAULT NULL,
  `IsSendGood` int(1) DEFAULT NULL,
  `Telephone` varchar(20) DEFAULT NULL,
  `CommandId` varchar(25) DEFAULT NULL,
  `ChannelShortCode` varchar(8) DEFAULT NULL,
  `CPNotifyUrl` varchar(120) DEFAULT NULL,
  `CPPrivateInfo` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for Page
-- ----------------------------
DROP TABLE IF EXISTS `Page`;
CREATE TABLE `Page` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '页类Id，主键',
  `PageTitle` varchar(45) DEFAULT NULL COMMENT '页面标题（如：首页）',
  `Status` varchar(10) DEFAULT NULL COMMENT '状态： 0：已发布 1：未发布',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `Type` varchar(45) DEFAULT NULL,
  `VersionCode` varchar(20) DEFAULT NULL,
  `SystemDomain` int(11) DEFAULT NULL,
  `BackgroundPic` varchar(256) DEFAULT NULL COMMENT '页面背景图',
  `CodeTitle` varchar(128) DEFAULT NULL COMMENT '页面Code',
  PRIMARY KEY (`Id`),
  KEY `fk_Page_SystemDomain_idx` (`SystemDomain`),
  CONSTRAINT `fk_Page_SystemDomain` FOREIGN KEY (`SystemDomain`) REFERENCES `SystemDomain` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='页面信息表';

-- ----------------------------
-- Table structure for PageCode
-- ----------------------------
DROP TABLE IF EXISTS `PageCode`;
CREATE TABLE `PageCode` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `CodeTitle` varchar(128) DEFAULT NULL COMMENT 'Code值',
  `Type` varchar(10) DEFAULT NULL COMMENT 'Codel类型:\n    PAGE : 页面Code\n   ELEMENT: 元素Code',
  `SystemDomain` int(11) DEFAULT NULL COMMENT '所属系统',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `Title` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_PageCode_SystemDomain_idx` (`SystemDomain`),
  CONSTRAINT `fk_PageCode_SystemDomain` FOREIGN KEY (`SystemDomain`) REFERENCES `SystemDomain` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='页面Code';

-- ----------------------------
-- Table structure for PageColumn
-- ----------------------------
DROP TABLE IF EXISTS `PageColumn`;
CREATE TABLE `PageColumn` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `ColumnTitle` varchar(45) DEFAULT NULL COMMENT '栏目标题',
  `MaxNo` int(2) DEFAULT NULL COMMENT '最大数量',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `SystemDomain` int(11) DEFAULT NULL COMMENT '所属系统Id',
  PRIMARY KEY (`Id`),
  KEY `fk_PageColumn_SystemDomain1_idx` (`SystemDomain`),
  CONSTRAINT `fk_PageColumn_SystemDomain1` FOREIGN KEY (`SystemDomain`) REFERENCES `SystemDomain` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='页面栏目表';

-- ----------------------------
-- Table structure for PageDevice
-- ----------------------------
DROP TABLE IF EXISTS `PageDevice`;
CREATE TABLE `PageDevice` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `Status` varchar(10) DEFAULT NULL COMMENT '状态:   P:已发布     S:已配置',
  `DeviceType` int(11) DEFAULT NULL COMMENT '机型Id',
  `Page` int(11) DEFAULT NULL COMMENT '页面Id',
  `Channel` int(11) DEFAULT NULL COMMENT '渠道Id',
  `Version` varchar(45) DEFAULT NULL COMMENT '页面版本',
  PRIMARY KEY (`Id`),
  KEY `fk_PageDevice_DeviceType1_idx` (`DeviceType`),
  KEY `fk_PageDevice_Page1_idx` (`Page`),
  KEY `fk_PageDevcie_Channel1_idx` (`Channel`),
  CONSTRAINT `fk_PageDevcie_Channel1` FOREIGN KEY (`Channel`) REFERENCES `Channel` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_PageDevice_DeviceType1` FOREIGN KEY (`DeviceType`) REFERENCES `DeviceType` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_PageDevice_Page1` FOREIGN KEY (`Page`) REFERENCES `Page` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='页面发布表';

-- ----------------------------
-- Table structure for PageElement
-- ----------------------------
DROP TABLE IF EXISTS `PageElement`;
CREATE TABLE `PageElement` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '元素Id,主键',
  `ElementTitle` varchar(45) DEFAULT NULL COMMENT '元素标题',
  `CodeTitle` varchar(128) DEFAULT NULL COMMENT '元素代码',
  `Position` int(2) DEFAULT NULL COMMENT '元素在栏目中的位置',
  `Jump` varchar(10) DEFAULT NULL COMMENT '跳转方式\n 内部ENDSIDE  外部 OUTSIDE',
  `JumpUrl` varchar(512) DEFAULT NULL COMMENT '对应动作类型的ID  如:game的Id',
  `ActionType` varchar(100) DEFAULT NULL COMMENT '动作类型',
  `ActionId` int(11) DEFAULT NULL COMMENT '动作Id',
  `CreateTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  `Page` int(11) DEFAULT NULL COMMENT '所属页面Id',
  `PageColumn` int(11) DEFAULT NULL COMMENT '栏目Id',
  `FoucsPic` varchar(256) DEFAULT NULL COMMENT '聚焦样式图片\n',
  `SelectPic` varchar(256) DEFAULT NULL COMMENT '选中样式',
  `NormalPic` varchar(256) DEFAULT NULL COMMENT '普通样式',
  `PageCode` varchar(128) DEFAULT NULL,
  `InitalCursor` int(1) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_PageElement_Page1_idx` (`Page`),
  KEY `fk_PageElement_ColumnName1_idx` (`PageColumn`),
  CONSTRAINT `fk_PageElement_ColumnName1` FOREIGN KEY (`PageColumn`) REFERENCES `PageColumn` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_PageElement_Page1` FOREIGN KEY (`Page`) REFERENCES `Page` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='页面元素信息';

-- ----------------------------
-- Table structure for PayKey
-- ----------------------------
DROP TABLE IF EXISTS `PayKey`;
CREATE TABLE `PayKey` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `KeyId` varchar(45) DEFAULT NULL COMMENT '密钥ID，由后台生成，具体生成规则待定',
  `KeyName` varchar(45) DEFAULT NULL,
  `CustType` varchar(20) DEFAULT NULL COMMENT '客户类型\nCP - 游戏提供商\nCHANNEL - 渠道\nSELLER - 商家\nATET - 时讯互联',
  `CustId` varchar(20) DEFAULT NULL,
  `PrivateKey` varchar(1024) DEFAULT NULL,
  `PublicKey` varchar(256) DEFAULT NULL,
  `NotifyURL` varchar(512) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付密钥表。密钥和客户挂勾，一个客户可以申请一个或多个密钥。密钥仅用于数据传输时加密参数。';

-- ----------------------------
-- Table structure for PayPoint
-- ----------------------------
DROP TABLE IF EXISTS `PayPoint`;
CREATE TABLE `PayPoint` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) DEFAULT NULL,
  `Game` int(11) DEFAULT NULL,
  `Price` int(11) DEFAULT NULL COMMENT '支付点金额，单位为 分',
  `CreateTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_Game` (`Game`),
  CONSTRAINT `FK_Game` FOREIGN KEY (`Game`) REFERENCES `Game` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付点表.\n支付点和游戏挂勾，一个游戏可对应N个支付点';

-- ----------------------------
-- Table structure for PayType
-- ----------------------------
DROP TABLE IF EXISTS `PayType`;
CREATE TABLE `PayType` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `PayCode` varchar(45) DEFAULT NULL,
  `PayName` varchar(45) DEFAULT NULL,
  `Status` varchar(8) DEFAULT NULL COMMENT '支付方式状态\nN - Normal 代表正常\nF - Freeze 代表冻结，不可用',
  `CreateTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付方式表';

-- ----------------------------
-- Table structure for PayTypeFeature
-- ----------------------------
DROP TABLE IF EXISTS `PayTypeFeature`;
CREATE TABLE `PayTypeFeature` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `PayType` int(11) DEFAULT NULL,
  `FeatureCode` varchar(8) DEFAULT NULL COMMENT '属性代码。例如：\nPlatType',
  `FeatureValue` varchar(8) DEFAULT NULL COMMENT '属性值。对应PlatType的值：\nTV - 电视\nPHONE - 手机\nPAD - 平板',
  `CreateTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_PayTypeFeature_PayType1_idx` (`PayType`),
  CONSTRAINT `fk_PayTypeFeature_PayType1` FOREIGN KEY (`PayType`) REFERENCES `PayType` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for PromoConsumter
-- ----------------------------
DROP TABLE IF EXISTS `PromoConsumter`;
CREATE TABLE `PromoConsumter` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Promotion` int(11) DEFAULT NULL COMMENT '活动Id',
  `Channel` int(11) DEFAULT NULL COMMENT '渠道Id',
  `Status` varchar(10) DEFAULT NULL COMMENT '配置状态    S:已配置   P:已发布',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for PromoProducer
-- ----------------------------
DROP TABLE IF EXISTS `PromoProducer`;
CREATE TABLE `PromoProducer` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Game` int(11) DEFAULT NULL COMMENT '参加活动的游戏ID',
  `Promotion` int(11) DEFAULT NULL COMMENT '活动ID',
  `GameTitle` varchar(40) DEFAULT NULL COMMENT '游戏名称',
  `Percent` varchar(10) DEFAULT '0' COMMENT '投入比',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for Promotion
-- ----------------------------
DROP TABLE IF EXISTS `Promotion`;
CREATE TABLE `Promotion` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Title` varchar(20) DEFAULT NULL COMMENT '活动名称',
  `Status` varchar(10) DEFAULT NULL COMMENT '条件类型  1  N: 未配置   2  S：已配置   3  P：已发布',
  `Type` varchar(10) DEFAULT NULL COMMENT '活动类型  游戏Game，平台Platform，用户User,其他Other',
  `AutoReward` varchar(10) DEFAULT NULL COMMENT '奖励方式',
  `Point` int(9) DEFAULT NULL COMMENT '奖励积分',
  `Coin` int(9) DEFAULT NULL COMMENT '奖励A币',
  `Diamond` int(9) DEFAULT NULL COMMENT '奖励钻石数',
  `FrequencyNo` int(9) DEFAULT NULL COMMENT '重复次数，零表示没有限制',
  `RepeatUnit` varchar(10) DEFAULT NULL COMMENT '奖励次数FrequencyNo的单位定义：如 天 DAY, 周 WEEK, 小时 HOUR, 次数  No',
  `SystemDomain` int(11) DEFAULT NULL COMMENT '系统',
  `Url` varchar(512) DEFAULT NULL,
  `Picture` varchar(128) DEFAULT NULL COMMENT '活动图片',
  `StartTime` datetime DEFAULT NULL COMMENT '开始时间',
  `EndTime` datetime DEFAULT NULL COMMENT '结束时间',
  `Remark` varchar(300) DEFAULT NULL COMMENT '活动描述',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `ToPublishTime` datetime DEFAULT NULL COMMENT '预发布时间',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for Question
-- ----------------------------
DROP TABLE IF EXISTS `Question`;
CREATE TABLE `Question` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `OperaNumOne` int(11) DEFAULT NULL COMMENT '运算数字一',
  `OperaSymbol` varchar(45) DEFAULT NULL COMMENT '运算符',
  `OperaNumTwo` int(11) DEFAULT NULL,
  `Equals` varchar(45) DEFAULT NULL,
  `Answer` int(11) DEFAULT NULL COMMENT '结果数字',
  `CreateTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  `QuestionBank` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_Question_QuestionBank1_idx` (`QuestionBank`),
  CONSTRAINT `fk_Question_QuestionBank1` FOREIGN KEY (`QuestionBank`) REFERENCES `QuestionBank` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for QuestionBank
-- ----------------------------
DROP TABLE IF EXISTS `QuestionBank`;
CREATE TABLE `QuestionBank` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Title` varchar(45) DEFAULT NULL COMMENT '''题库的名称''',
  `SystemDomain` int(11) DEFAULT NULL COMMENT '系统Id',
  `Remark` varchar(500) DEFAULT NULL COMMENT '''描述''',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `Status` varchar(45) DEFAULT NULL COMMENT '状态：S:已发布；P：未发布；',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for Rank
-- ----------------------------
DROP TABLE IF EXISTS `Rank`;
CREATE TABLE `Rank` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `RankTitle` varchar(45) DEFAULT NULL COMMENT '排行模板标题',
  `Status` varchar(10) DEFAULT NULL COMMENT '状态：‘S’未发布‘P’已发布',
  `Icon` varchar(50) DEFAULT NULL COMMENT '模板图标',
  `BkgIcon` varchar(50) DEFAULT NULL COMMENT '背景图(预留)',
  `CreateTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for RankBlock
-- ----------------------------
DROP TABLE IF EXISTS `RankBlock`;
CREATE TABLE `RankBlock` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Rank` int(11) DEFAULT NULL COMMENT '所属模板',
  `RankBlockTitle` varchar(45) DEFAULT NULL COMMENT '版块标题',
  `RankBlockIcon` varchar(50) DEFAULT NULL COMMENT '版块图标',
  `BlockBkgIcon` varchar(50) DEFAULT NULL COMMENT '版块背景图',
  `BlockSeq` int(9) DEFAULT NULL COMMENT '板块排序号',
  `BlockType` varchar(30) DEFAULT NULL COMMENT '板块内容的类型  GAME – 游戏  PROD – 产品',
  `CreateTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `Rank` (`Rank`),
  CONSTRAINT `RankBlock_ibfk_1` FOREIGN KEY (`Rank`) REFERENCES `Rank` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for RankBlockElement
-- ----------------------------
DROP TABLE IF EXISTS `RankBlockElement`;
CREATE TABLE `RankBlockElement` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `RankBlock` int(11) DEFAULT NULL COMMENT '排行版块',
  `ElementId` int(11) DEFAULT NULL COMMENT '元素标题',
  `ElementSeq` int(9) DEFAULT NULL COMMENT '元素排序号',
  `Result` varchar(20) DEFAULT NULL COMMENT '排行值',
  `ActType` varchar(30) DEFAULT NULL COMMENT '操作类型',
  `ActParam` varchar(500) DEFAULT NULL COMMENT '操作参数',
  `CreateTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `RankBlock` (`RankBlock`),
  CONSTRAINT `RankBlockElement_ibfk_1` FOREIGN KEY (`RankBlock`) REFERENCES `RankBlock` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for RankSystem
-- ----------------------------
DROP TABLE IF EXISTS `RankSystem`;
CREATE TABLE `RankSystem` (
  `Rank` int(11) NOT NULL COMMENT '模板id',
  `SystemDomain` int(11) NOT NULL COMMENT '系统ID',
  `CreateTime` datetime DEFAULT NULL,
  KEY `Rank` (`Rank`),
  CONSTRAINT `RankSystem_ibfk_1` FOREIGN KEY (`Rank`) REFERENCES `Rank` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for RechargeSet
-- ----------------------------
DROP TABLE IF EXISTS `RechargeSet`;
CREATE TABLE `RechargeSet` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `SystemDomain` int(11) DEFAULT NULL,
  `MoneyMin` int(7) DEFAULT NULL,
  `MoneyMax` int(7) DEFAULT NULL,
  `Type` varchar(10) DEFAULT NULL COMMENT '类型： q:定额赠送 p：百分比赠送',
  `Reward` int(11) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_RechargeSet_SystemDomain1` (`SystemDomain`),
  CONSTRAINT `fk_RechargeSet_SystemDomain1` FOREIGN KEY (`SystemDomain`) REFERENCES `SystemDomain` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ReviewForm
-- ----------------------------
DROP TABLE IF EXISTS `ReviewForm`;
CREATE TABLE `ReviewForm` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `FormTitle` varchar(80) DEFAULT NULL COMMENT '审批单标题',
  `SystemDomain` int(11) DEFAULT NULL COMMENT 'SystemDomain主键',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`Id`),
  KEY `fk_ReviewForm_SystemDomain1_idx` (`SystemDomain`),
  CONSTRAINT `fk_ReviewForm_SystemDomain1` FOREIGN KEY (`SystemDomain`) REFERENCES `SystemDomain` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='保存审批要求，用于游戏的审批。';

-- ----------------------------
-- Table structure for ReviewItem
-- ----------------------------
DROP TABLE IF EXISTS `ReviewItem`;
CREATE TABLE `ReviewItem` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ReviewForm` int(11) DEFAULT NULL COMMENT '相对应的审批表Id',
  `ItemTitle` varchar(200) DEFAULT NULL COMMENT '审批项标题',
  `ItemType` varchar(30) DEFAULT NULL COMMENT '审批项类型（如，DIGITAL, CHOICE, TEXT)',
  `ItemGroup` varchar(45) DEFAULT NULL COMMENT '应用审批项的分组',
  `ItemOrder` int(11) DEFAULT NULL COMMENT '用于显示审批项的排序顺序',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`Id`),
  KEY `fk_ReviewItem_ReviewForm1_idx` (`ReviewForm`),
  CONSTRAINT `fk_ReviewItem_ReviewForm1` FOREIGN KEY (`ReviewForm`) REFERENCES `ReviewForm` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='保存审批条款';

-- ----------------------------
-- Table structure for Reward
-- ----------------------------
DROP TABLE IF EXISTS `Reward`;
CREATE TABLE `Reward` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `User` int(11) DEFAULT NULL COMMENT '用户Id',
  `RewardNo` varchar(10) DEFAULT NULL COMMENT '奖励数量',
  `Type` varchar(10) DEFAULT NULL COMMENT '奖励类型\n1：积分\n2：A币\n3：钻石',
  `Status` int(1) DEFAULT NULL COMMENT '发布状态：\n0：已发布 1：未发布',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `Phone` varchar(45) DEFAULT NULL COMMENT '用户手机号',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for Right
-- ----------------------------
DROP TABLE IF EXISTS `Right`;
CREATE TABLE `Right` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `RightId` varchar(200) DEFAULT NULL COMMENT '用于记录权限的字符串标识',
  `RightName` varchar(200) DEFAULT NULL COMMENT '权限名称',
  `Uri` varchar(256) DEFAULT NULL COMMENT '权限对应的URI链接地址',
  `Desc` varchar(300) DEFAULT NULL COMMENT '权限描述',
  `CreateTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for Role
-- ----------------------------
DROP TABLE IF EXISTS `Role`;
CREATE TABLE `Role` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `RoleName` varchar(30) DEFAULT NULL COMMENT '角色名称',
  `Desc` varchar(200) DEFAULT NULL COMMENT '角色描述',
  `CreateTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for SpringBatch
-- ----------------------------
DROP TABLE IF EXISTS `SpringBatch`;
CREATE TABLE `SpringBatch` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `SpringBatchCode` varchar(40) NOT NULL,
  `SpringBatchTimer` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for Store
-- ----------------------------
DROP TABLE IF EXISTS `Store`;
CREATE TABLE `Store` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id\n',
  `StoreTitle` varchar(45) DEFAULT NULL COMMENT '商店名称',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `Mall` int(11) DEFAULT NULL,
  `Channel` int(11) DEFAULT NULL COMMENT '渠道Id',
  PRIMARY KEY (`Id`),
  KEY `fk_Store_Mall1_idx` (`Mall`),
  KEY `fk_Store_Channel_idx` (`Channel`),
  CONSTRAINT `fk_Store_Channel` FOREIGN KEY (`Channel`) REFERENCES `Channel` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Store_Mall1` FOREIGN KEY (`Mall`) REFERENCES `Mall` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for SystemDomain
-- ----------------------------
DROP TABLE IF EXISTS `SystemDomain`;
CREATE TABLE `SystemDomain` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `DomainName` varchar(45) DEFAULT NULL COMMENT '应用系统名（如：双屏，亲子，组装世界）',
  `EnglishName` varchar(45) DEFAULT NULL COMMENT '系统英文名',
  `PackageName` varchar(128) DEFAULT NULL COMMENT '大厅包名',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='保存应用系统信息（如：双屏，亲子，组装世界）';

-- ----------------------------
-- Table structure for SystemFeatureLookup
-- ----------------------------
DROP TABLE IF EXISTS `SystemFeatureLookup`;
CREATE TABLE `SystemFeatureLookup` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `FeatureTitle` varchar(45) DEFAULT NULL COMMENT '属性名称',
  `FeatureCode` varchar(45) DEFAULT NULL COMMENT '属性Code',
  `Type` varchar(45) DEFAULT NULL COMMENT '属性类型:\n  CHOICE  单选\n  TEXT       文本\n',
  `Value` varchar(45) DEFAULT NULL COMMENT '属性值',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `SystemDomain` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_SystemFeature_SystemDomain_idx` (`SystemDomain`),
  CONSTRAINT `fk_SystemFeature_SystemDomain` FOREIGN KEY (`SystemDomain`) REFERENCES `SystemDomain` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for TeleProvider
-- ----------------------------
DROP TABLE IF EXISTS `TeleProvider`;
CREATE TABLE `TeleProvider` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `CompanyName` varchar(32) DEFAULT NULL COMMENT '公司名称',
  `CompanyCode` varchar(20) DEFAULT NULL COMMENT '公司代码   例如：2000',
  `Rate` int(5) DEFAULT '0' COMMENT '管控比例  例如80，代表管控比例为80%',
  `ParentCompanyId` int(20) DEFAULT NULL COMMENT '母公司代码   例如：3000',
  `CreateTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for TimeCondition
-- ----------------------------
DROP TABLE IF EXISTS `TimeCondition`;
CREATE TABLE `TimeCondition` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Type` varchar(10) DEFAULT NULL COMMENT '类型',
  `Description` varchar(45) DEFAULT NULL COMMENT '描述',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='时间条件表';

-- ----------------------------
-- Table structure for User
-- ----------------------------
DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Role` int(11) DEFAULT NULL COMMENT '用户所属角色',
  `LoginName` varchar(20) DEFAULT NULL COMMENT '登录名',
  `Password` varchar(20) DEFAULT NULL COMMENT '登录密码',
  `State` varchar(10) DEFAULT NULL COMMENT '用户状态  NORMAL - 正常   FREEZE - 冻结',
  `TeleProvider` int(11) DEFAULT NULL COMMENT '用户所属公司',
  `CreateTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  `LastLoginTime` datetime DEFAULT NULL COMMENT '用户最后一次登录时间',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for UserAccount
-- ----------------------------
DROP TABLE IF EXISTS `UserAccount`;
CREATE TABLE `UserAccount` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID，系统自增长',
  `EndUser` int(11) DEFAULT NULL COMMENT '用户ID',
  `Point` int(10) DEFAULT NULL COMMENT '用户积分',
  `Coin` int(10) DEFAULT NULL COMMENT 'A币',
  `Diamond` int(10) DEFAULT NULL COMMENT '钻石',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`Id`),
  KEY `fk_UserAccount_EndUser1_idx` (`EndUser`),
  CONSTRAINT `fk_UserAccount_EndUser1` FOREIGN KEY (`EndUser`) REFERENCES `EndUser` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用于保存用户的账户信息。';

-- ----------------------------
-- Table structure for UserFeatureItem
-- ----------------------------
DROP TABLE IF EXISTS `UserFeatureItem`;
CREATE TABLE `UserFeatureItem` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `FeatureCode` varchar(20) DEFAULT NULL COMMENT '特征代码。比如消费锁为：PAYLOCK',
  `EndUser` int(11) DEFAULT NULL,
  `FeatureValue` varchar(20) DEFAULT NULL COMMENT '特征值。比如消费锁的值为：OPEN',
  `ExtraValue` varchar(45) DEFAULT NULL COMMENT '附加值。如果用户消费锁为开，则这个值记录消费锁的密码',
  `CreateTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  `SystemDomain` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for UserProduct
-- ----------------------------
DROP TABLE IF EXISTS `UserProduct`;
CREATE TABLE `UserProduct` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键 Id',
  `EndUser` int(11) DEFAULT NULL COMMENT '用户Id',
  `SystemDomain` int(11) DEFAULT NULL COMMENT '系统Id',
  `Type` varchar(10) DEFAULT NULL COMMENT '产品类型',
  `ProId` int(11) DEFAULT NULL COMMENT '产品Id\n当Type为 VIP 的时候',
  `TransactionTime` datetime DEFAULT NULL COMMENT '交易时间',
  `ExpireTime` datetime DEFAULT NULL COMMENT '产品失效时间\n当Type为VIP时，用户每次购买VIP，在改时间上做叠加',
  `Status` varchar(10) DEFAULT NULL COMMENT '状态\n0： 正常\n1： 退货',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`Id`),
  KEY `fk_UserProduct_EndUser_idx` (`EndUser`),
  CONSTRAINT `fk_UserProduct_EndUser` FOREIGN KEY (`EndUser`) REFERENCES `EndUser` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for UserSystem
-- ----------------------------
DROP TABLE IF EXISTS `UserSystem`;
CREATE TABLE `UserSystem` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `EndUser` int(11) DEFAULT NULL,
  `Channel` int(11) DEFAULT NULL COMMENT '渠道Id',
  `SystemDomain` int(11) DEFAULT NULL COMMENT '系统Id',
  `CreateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
