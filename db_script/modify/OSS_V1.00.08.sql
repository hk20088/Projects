--将Game表状态是待配置状态全部修改为待审核状态--
UPDATE Game SET `Status`='1' WHERE `Status`='6'