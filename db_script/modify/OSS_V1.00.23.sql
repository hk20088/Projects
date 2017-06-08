-- 给游戏文件表添加文件名称字段
ALTER TABLE `GameFileItem`
ADD COLUMN `FileTitle`  varchar(40) NULL COMMENT '文件名称';

-- 文件索引字段
ALTER TABLE `GameFileItem`
ADD COLUMN `Index`  Int(4) NULL COMMENT '文件索引';

-- 文件描述
ALTER TABLE `GameFileItem`
ADD COLUMN `Description`  varchar(500) NULL COMMENT '文件描述';


-- 存储QQ物联需要的sn和lisence参数的表
DROP TABLE IF EXISTS `IotInfo`;
create table IotInfo
(
	`Id` INT NOT NULL AUTO_INCREMENT,
	`Sn` varchar(25) DEFAULT NULL UNIQUE COMMENT '序列号',
	`Lisence` varchar(150) DEFAULT NULL UNIQUE COMMENT '与Sn一一对应的证书',
	`Device` INT(10) unsigned DEFAULT NULL COMMENT '设备ID',
	`CreateTime` DATETIME NULL,
	`UpdateTime` DATETIME NULL,
	 PRIMARY KEY (`Id`),
	 KEY `fk_IotInfo_Device` (`Device`),
  CONSTRAINT `fk_IotInfo_Device` FOREIGN KEY (`Device`) REFERENCES `Device` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- 给FileLookUp增加课程视频初始化数据
insert into FileLookup(FileTitle,FileCode,Format,Pixel,CreateTime,UpdateTime,Type)
values('课程视频','VIDEO','avi,wmv,mp4,rm,rmvb,flv','',NOW(),NOW(),'PATH')