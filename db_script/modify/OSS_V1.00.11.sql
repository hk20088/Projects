-- -------------------------------------------
-- -----以下脚本在OSSI V1.00.08版本执行
-- -------------------------------------------
-- 增加SystemDomain 字段
ALTER TABLE `UserAccount`
ADD COLUMN `SystemDomain`  int(11) NULL COMMENT '系统Id' AFTER `EndUser`;

-- 修改原来充值的用户的资产所属的系统
SET @SYSTEMID = (SELECT Id FROM SystemDomain WHERE PackageName = 'com.sxhl.tcltvmarket');
UPDATE `UserAccount` SET SystemDomain = @SYSTEMID;
-- --------------------------------
-- -------结束--------------------
-- --------------------------------

ALTER TABLE `BatchTask` DROP FOREIGN KEY `fk_Channel_Channel`;
ALTER TABLE `BatchTask` DROP FOREIGN KEY `fk_SystemDomain_SystemDomain`;
ALTER TABLE `BatchTask` DROP FOREIGN KEY `fk_BatchTask_BatchTemplate`;
DROP TABLE IF EXISTS `OSS`.`BatchTemplate` ;
DROP TABLE IF EXISTS `OSS`.`BatchTask` ;

DROP TABLE IF EXISTS `ActivityTask`;
CREATE TABLE `ActivityTask` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `ActivityTitle` varchar(20) DEFAULT NULL COMMENT '活动的名称',
  `TaskCode` varchar(40) NOT NULL ,
  `Cycle` varchar(10) DEFAULT NULL COMMENT '活动周期：\n日——DAY\n周——WEEK\n月——MONTH\n长期——LONG',
  `ActivityType` varchar(20) DEFAULT NULL COMMENT '活动类型：\n充值——RECHARGE\n消费——CONSUMPTION\n活跃——ACTIVE\n下载——DOWNLOAD',
  `ParamType` varchar(20) DEFAULT NULL COMMENT '条件类型:\n首次——FIRST\n单次——SINGLE\n累计——CUMULATIVE',
  `Frequency` int(11) DEFAULT NULL COMMENT '周期内重复次数：\n0——无限次\n其余具体数字代表具体多少次',
  `Game` int(11) DEFAULT NULL COMMENT '游戏ID',
  `StartTime` datetime DEFAULT NULL COMMENT '活动开始时间',
  `EndTime` datetime DEFAULT NULL COMMENT '活动结束时间',
  `EmailTemplate` varchar(200) DEFAULT NULL COMMENT '活动的邮件内容模板',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `Remark` varchar(500) DEFAULT NULL COMMENT '活动描述',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB 
COMMENT='批处理任务表';


-- -----------------------------------------------------
-- Table `OSS`.`ActivityReward`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `OSS`.`ActivityReward` ;

CREATE TABLE IF NOT EXISTS `OSS`.`ActivityReward` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Requirement` INT NULL COMMENT '条件值',
  `RewardType` VARCHAR(10) NULL COMMENT '奖励类型：\n定额赠送——Q\n百分比赠送——P',
  `Reward` VARCHAR(10) NULL COMMENT '奖励内容\nA豆——A\n积分——P\n钻石——D\nVIP——V',
  `RewardValue` INT NULL COMMENT '奖励的数量',
  `CreateTime` DATETIME NULL COMMENT '创建时间',
  `ActivityTask` INT NOT NULL,
  PRIMARY KEY (`Id`),
  CONSTRAINT `fk_ActivityReward_ActivityTask1`
    FOREIGN KEY (`ActivityTask`)
    REFERENCES `OSS`.`ActivityTask` (`Id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
COMMENT = '奖励信息表';


-- -----------------------------------------------------
-- Table `OSS`.`ActivitySysChan`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `OSS`.`ActivitySysChan` ;

CREATE TABLE IF NOT EXISTS `OSS`.`ActivitySysChan` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Type` VARCHAR(10) NULL COMMENT '类型 \n系统——S\n渠道——C',
  `ExtraId` INT NULL COMMENT '系统或渠道Id',
  `CreateTime` DATETIME NULL COMMENT '创建时间',
  `UpdateTime` DATETIME NULL COMMENT '更新时间',
  `ActivityTask` INT NOT NULL,
  PRIMARY KEY (`Id`),
  CONSTRAINT `fk_ActivitySysChan_ActivityTask1`
    FOREIGN KEY (`ActivityTask`)
    REFERENCES `OSS`.`ActivityTask` (`Id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
COMMENT = '活动系统渠道表';


-- -----------------------------------------------------
-- Table `OSS`.`ActivityRecord`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `OSS`.`ActivityRecord` ;

CREATE TABLE IF NOT EXISTS `OSS`.`ActivityRecord` (
  `Id` INT NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `UserId` INT NULL COMMENT '用户ID',
  `Phone` VARCHAR(20) NULL COMMENT '用户手机号',
  `ActivityTitle` VARCHAR(20) NULL COMMENT '任务名称',
  `CreateTime` DATETIME NULL COMMENT '创建时间',
  `ActivityTask` INT NULL COMMENT '批处理ID',
  `OrderNo` VARCHAR(45) NULL COMMENT '订单号',
  `Requirement` INT NULL COMMENT '条件值',
  `Reward` VARCHAR(20) NULL COMMENT '奖励内容\nA豆——A\n积分——P\n钻石——D\nVIP——V',
  `RewardValue` INT NULL COMMENT '奖励的数量',
  PRIMARY KEY (`Id`))
ENGINE = InnoDB
COMMENT = '活动记录表';
