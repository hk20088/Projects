
-- 用户资产表，添加课时字段    huqili 2017年5月6日
alter table UserProduct add column ClassHour int(11) comment '课时';

-- 在GameType表中添加课时类型 tangshuangli 2017-05-09
insert into GoodsType (Title,Type,CreateTime) values ('课时','CLASSHOUR',NOW());

-- 在SystemFeatureLookup中初始化课时数据 tangshuangli 2017-05-09
INSERT INTO SystemFeatureLookup(FeatureTitle,FeatureCode,Type,`Value`,CreateTime,UpdateTime,SystemDomain)
VALUES('课时单位时长(单位：分钟)','CLASSHOUR','TEXT','',NOW(),NOW(),1);

INSERT INTO SystemFeatureLookup(FeatureTitle,FeatureCode,Type,`Value`,CreateTime,UpdateTime,SystemDomain)
VALUES('课时单位时长(单位：分钟)','CLASSHOUR','TEXT','',NOW(),NOW(),2);

INSERT INTO SystemFeatureLookup(FeatureTitle,FeatureCode,Type,`Value`,CreateTime,UpdateTime,SystemDomain)
VALUES('课时单位时长(单位：分钟)','CLASSHOUR','TEXT','',NOW(),NOW(),3);

INSERT INTO SystemFeatureLookup(FeatureTitle,FeatureCode,Type,`Value`,CreateTime,UpdateTime,SystemDomain)
VALUES('课时单位时长(单位：分钟)','CLASSHOUR','TEXT','',NOW(),NOW(),4);

INSERT INTO SystemFeatureLookup(FeatureTitle,FeatureCode,Type,`Value`,CreateTime,UpdateTime,SystemDomain)
VALUES('课时单位时长(单位：分钟)','CLASSHOUR','TEXT','',NOW(),NOW(),5);

INSERT INTO SystemFeatureLookup(FeatureTitle,FeatureCode,Type,`Value`,CreateTime,UpdateTime,SystemDomain)
VALUES('课时单位时长(单位：分钟)','CLASSHOUR','TEXT','',NOW(),NOW(),6);

INSERT INTO SystemFeatureLookup(FeatureTitle,FeatureCode,Type,`Value`,CreateTime,UpdateTime,SystemDomain)
VALUES('课时单位时长(单位：分钟)','CLASSHOUR','TEXT','',NOW(),NOW(),7);