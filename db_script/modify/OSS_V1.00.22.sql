-- 给发布模板的modelId字段添加外键
SET FOREIGN_KEY_CHECKS=0;
ALTER TABLE PublishModel ADD CONSTRAINT fk_PublishModel_ModelId FOREIGN KEY(ModelId) REFERENCES Model (ModelId)  ON DELETE CASCADE ON UPDATE CASCADE;

-- Table `OSS`.`SystemDomainDevice`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `OSS`.`SystemDomainDevice` ;

CREATE TABLE IF NOT EXISTS `OSS`.`SystemDomainDevice` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `SystemDomain` INT NULL COMMENT '系统Id',
  `DeviceType` INT NULL COMMENT '机型Id',
  `CreateTime` DATETIME NULL,
  `UpdateTime` DATETIME NULL,
  PRIMARY KEY (`Id`),
  CONSTRAINT `fk_SystemDomainDevice_DeviceType1`
    FOREIGN KEY (`DeviceType`)
    REFERENCES `OSS`.`DeviceType` (`Id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_SystemDomainDevice_SystemDomain1`
    FOREIGN KEY (`SystemDomain`)
    REFERENCES `OSS`.`SystemDomain` (`Id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;