
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for Administrator
-- ----------------------------
DROP TABLE IF EXISTS `Administrator`;
CREATE TABLE `Administrator` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `LoginName` varchar(45) DEFAULT NULL COMMENT '用户名',
  `Password` varchar(45) DEFAULT NULL COMMENT '密码',
  `State` varchar(45) DEFAULT NULL COMMENT '用户状态  NORMAL - 正常   FREEZE - 冻结',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `LastLoginTime` datetime DEFAULT NULL COMMENT '上次登录时间',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Table structure for AdministratorRight
-- ----------------------------
DROP TABLE IF EXISTS `AdministratorRight`;
CREATE TABLE `AdministratorRight` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `Type` varchar(45) DEFAULT NULL COMMENT '目前类型只有系统和渠道.\nS:SystemDomain系统\nC:Channel渠道',
  `ExtraId` int(11) DEFAULT NULL COMMENT '渠道id或者系统id',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `Administrator` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_UserRight_Administrator1` (`Administrator`),
  CONSTRAINT `fk_UserRight_Administrator1` FOREIGN KEY (`Administrator`) REFERENCES `Administrator` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用户权限表';

-- ----------------------------
-- Table structure for AdministratorRole
-- ----------------------------
DROP TABLE IF EXISTS `AdministratorRole`;
CREATE TABLE `AdministratorRole` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `Administrator` int(11) NOT NULL,
  `Role` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_UserRole_User1` (`Administrator`),
  KEY `fk_UserRole_Role1` (`Role`),
  CONSTRAINT `fk_UserRole_Role1` FOREIGN KEY (`Role`) REFERENCES `Role` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_UserRole_User1` FOREIGN KEY (`Administrator`) REFERENCES `Administrator` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用户角色关联表';

-- ----------------------------
-- Table structure for Right
-- ----------------------------
DROP TABLE IF EXISTS `Right`;
CREATE TABLE `Right` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `RightName` varchar(45) DEFAULT NULL COMMENT '权限名称',
  `Uri` varchar(255) DEFAULT NULL COMMENT '权限名的路径',
  `ParentRightCode` varchar(45) DEFAULT NULL COMMENT '父权限标识',
  `CreateTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  `RightCode` varchar(45) DEFAULT NULL COMMENT '权限标识，如GAME_SERVICE',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Table structure for Role
-- ----------------------------
DROP TABLE IF EXISTS `Role`;
CREATE TABLE `Role` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `RoleName` varchar(45) DEFAULT NULL COMMENT '角色名称',
  `CreateTime` datetime DEFAULT NULL,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Table structure for RoleRight
-- ----------------------------
DROP TABLE IF EXISTS `RoleRight`;
CREATE TABLE `RoleRight` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `Right` int(11) NOT NULL,
  `Role` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_RoleRight_Right1` (`Right`),
  KEY `fk_RoleRight_Role1` (`Role`),
  CONSTRAINT `fk_RoleRight_Right1` FOREIGN KEY (`Right`) REFERENCES `Right` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_RoleRight_Role1` FOREIGN KEY (`Role`) REFERENCES `Role` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;




--给Role表和Administrator增加1个字段Type
alter table Role add Type varchar(20);
alter table Administrator add Type varchar(20);

-- 用户名：Administrator  密码：xyzabc963852
insert into Administrator(LoginName,Password,State,CreateTime,UpdateTime,Type) values ('Administrator','FD4F6A16C68A12B13E52F8FF885CF60E','NORMAL',now(),now(),'ADMIN');