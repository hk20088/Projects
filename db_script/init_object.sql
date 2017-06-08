-- ----------------------------
-- Records of Channel
-- ----------------------------
INSERT INTO `Channel` VALUES ('1', 'TCL', 'TCL集团股份有限公司', '2016-05-21 09:58:09', '2016-05-30 15:24:07', 'Y');
INSERT INTO `Channel` VALUES ('2', '小米', '北京小米科技有限责任公司', '2016-05-21 09:58:31', '2016-05-30 14:17:46', 'Y');
INSERT INTO `Channel` VALUES ('3', '三星', '三星集团', '2016-05-21 09:58:58', '2016-05-30 14:17:49', 'Y');
INSERT INTO `Channel` VALUES ('4', '华为', '华为技术有限公司', '2016-05-21 09:59:00', '2016-05-30 14:17:51', 'Y');
INSERT INTO `Channel` VALUES ('5', '广电', '国家广播电影电视总局', '2016-08-03 11:16:49', '2016-08-03 11:16:51', 'N');

-- ----------------------------
-- Records of CP
-- ----------------------------
INSERT INTO `CP` VALUES ('1', 'ATET', '深圳市时讯互联科技有限公司', '2016-06-06 16:47:27', '2016-06-06 16:47:29');

-- ----------------------------
-- Records of FeatureLookup
-- ----------------------------
INSERT INTO `FeatureLookup` VALUES ('1', '是否支持手柄', 'HANDLE', '是否支持手柄', 'CHOOSE', '2016-09-01 00:00:00', '2016-09-01 00:00:00');
INSERT INTO `FeatureLookup` VALUES ('2', '是否支持手机', 'PHONE', '是否支持手机', 'CHOOSE', '2016-09-01 00:00:00', '2016-09-01 00:00:00');
INSERT INTO `FeatureLookup` VALUES ('3', '是否支持遥控', 'TELECONTROL', '是否支持遥控', 'CHOOSE', '2016-09-01 00:00:00', '2016-09-01 00:00:00');
INSERT INTO `FeatureLookup` VALUES ('4', '是否支持体感', 'MOTIONSENSOR', '是否支持体感', 'CHOOSE', '2016-09-01 00:00:00', '2016-09-01 00:00:00');
INSERT INTO `FeatureLookup` VALUES ('5', '成长标签', 'GROWLABEL', '多个用逗号隔开', 'TEXT', '2016-11-09 10:22:05', '2016-11-09 10:22:19');

-- ----------------------------
-- Records of FileLookup
-- ----------------------------
INSERT INTO `FileLookup` VALUES ('1', '小图文件', 'MINICON', 'jpg,png,jpeg', '200*200', '2016-04-21 18:20:02', '2016-04-21 18:20:07', 'FILE');
INSERT INTO `FileLookup` VALUES ('2', '中图文件', 'MIDICON', 'jpg,png,jpeg', '400*400', '2016-04-21 18:21:31', '2016-04-21 18:21:34', 'FILE');
INSERT INTO `FileLookup` VALUES ('3', '大图文件', 'MAXICON', 'jpg,png,jpeg', '800*800', '2016-04-21 18:22:07', '2016-04-21 18:22:09', 'FILE');
INSERT INTO `FileLookup` VALUES ('4', '横图', 'CROSSPIC', 'jpg,png,jpeg', null, '2016-04-21 18:23:12', '2016-04-21 18:23:14', 'FILE');
INSERT INTO `FileLookup` VALUES ('5', '竖图', 'VERTICALPIC', 'jpg,png,jpeg', null, '2016-04-21 18:20:02', '2016-04-21 18:20:07', 'FILE');
INSERT INTO `FileLookup` VALUES ('6', '详图', 'PIC', 'jpg,png,jpeg', null, '2016-04-21 18:21:31', '2016-04-21 18:21:34', 'FILE');
INSERT INTO `FileLookup` VALUES ('7', '视频', 'VIDEO', 'avi,wmv,mp4,rm,rmvb,flv', null, '2016-04-21 18:22:07', '2016-04-21 18:22:09', 'FILE');
INSERT INTO `FileLookup` VALUES ('8', '著作权', 'COPYRIGHT', 'xls', null, '2016-04-21 18:23:12', '2016-04-21 18:23:14', 'FILE');
INSERT INTO `FileLookup` VALUES ('9', '资源包', 'ZIP', 'zip,rar', null, '2016-04-21 18:20:02', '2016-04-21 18:20:07', 'FILE');
INSERT INTO `FileLookup` VALUES ('10', '角色图标', 'ROLEICON', 'jpg,png,jpeg', null, '2016-04-21 18:20:02', '2016-04-21 18:20:07', 'FILE');
INSERT INTO `FileLookup` VALUES ('11', '标题音频', 'NAMEVOICE', 'mp3', null, '2016-04-21 18:20:02', '2016-04-21 18:20:07', 'FILE');
INSERT INTO `FileLookup` VALUES ('12', '简介音频', 'INFOVOICE', 'mp3', '', '2016-04-21 18:20:00', '2016-04-21 18:20:00', 'FILE');
INSERT INTO `FileLookup` VALUES ('13', '小图地址', 'MINICON', '请输入小图地址', '', '2016-04-21 18:20:02', '2016-04-21 18:20:07', 'PATH');


-- ----------------------------
-- Records of PageCode
-- ----------------------------
INSERT INTO `PageCode` VALUES ('1', 'MainActivity', 'Page', '2', '2016-10-29 00:00:00', '大厅主页（页面）');
INSERT INTO `PageCode` VALUES ('2', 'ManagerActivity', 'Page', '2', '2016-10-29 00:00:00', '家长管理（页面）');
INSERT INTO `PageCode` VALUES ('3', 'DetailActivity', 'Page', '2', '2016-10-29 00:00:00', '游戏详情（页面）');
INSERT INTO `PageCode` VALUES ('4', 'RecommendActivity', 'Page', '2', '2016-10-29 00:00:00', '支付推荐页（页面）');
INSERT INTO `PageCode` VALUES ('5', 'ConfirmPasswordActivity', 'Page', '2', '2016-10-29 00:00:00', '儿童锁（页面）');
INSERT INTO `PageCode` VALUES ('6', 'EmailListActivity', 'Page', '2', '2016-10-29 00:00:00', '邮件列表（页面）');
INSERT INTO `PageCode` VALUES ('7', 'VipFragment', 'Page', '2', '2016-10-29 00:00:00', 'VIP详情（页面）');
INSERT INTO `PageCode` VALUES ('8', 'LoadingActivity', 'Page', '2', '2016-10-29 00:00:00', '大厅启动页(页面）');
INSERT INTO `PageCode` VALUES ('9', 'AboutActivity', 'Page', '2', '2016-10-29 00:00:00', '关于我们（页面）');
INSERT INTO `PageCode` VALUES ('10', 'SetupHandlelinkActivity', 'Page', '2', '2016-10-29 00:00:00', '手柄设置（页面）');
INSERT INTO `PageCode` VALUES ('11', 'SetupHandlelinkDetail1Activity', 'Page', '2', '2016-10-29 00:00:00', '无线手柄设置向导（页面）');
INSERT INTO `PageCode` VALUES ('12', 'SetupHandlelinkDetail2Activity', 'Page', '2', '2016-10-29 00:00:00', '蓝牙手柄设置向导（页面）');
INSERT INTO `PageCode` VALUES ('14', 'LessonFlowChartActivity', 'Page', '2', '2016-10-29 00:00:00', '课程（页面）');
INSERT INTO `PageCode` VALUES ('15', 'AccountFragment', 'Page', '2', '2016-10-29 00:00:00', '账户（页面）');
INSERT INTO `PageCode` VALUES ('16', 'LoginModule', 'Page', '2', '2016-10-29 00:00:00', '登录模块（页面）');
INSERT INTO `PageCode` VALUES ('17', 'RegisterModule', 'Page', '2', '2016-10-29 00:00:00', '注册模块（页面）');
INSERT INTO `PageCode` VALUES ('18', 'GuestModule', 'Page', '2', '2016-10-29 00:00:00', '游客模块（页面）');
INSERT INTO `PageCode` VALUES ('19', 'ForgetPwdModule', 'Page', '2', '2016-10-29 00:00:00', '忘记密码（页面）');
INSERT INTO `PageCode` VALUES ('20', 'HelpActivity', 'Page', '2', '2016-10-29 00:00:00', '帮助（页面）');
INSERT INTO `PageCode` VALUES ('21', 'ManagerActivity', 'Element', '2', '2016-10-29 00:00:00', '家长管理（元素）');
INSERT INTO `PageCode` VALUES ('22', 'DetailActivity', 'Element', '2', '2016-10-29 00:00:00', '游戏详情（元素）');
INSERT INTO `PageCode` VALUES ('23', 'RecommendActivity', 'Element', '2', '2016-10-29 00:00:00', '支付推荐页（元素）');
INSERT INTO `PageCode` VALUES ('24', 'ConfirmPasswordActivity', 'Element', '2', '2016-10-29 00:00:00', '儿童锁（元素）');
INSERT INTO `PageCode` VALUES ('25', 'EmailListActivity', 'Element', '2', '2016-10-29 00:00:00', '邮件列表（元素）');
INSERT INTO `PageCode` VALUES ('26', 'VipFragment', 'Element', '2', '2016-10-29 00:00:00', 'VIP详情（元素）');
INSERT INTO `PageCode` VALUES ('27', 'GameTopFragment', 'Element', '2', '2016-10-29 00:00:00', '排行榜（元素）');
INSERT INTO `PageCode` VALUES ('28', 'KidTimeSetupActivity', 'Element', '2', '2016-10-29 00:00:00', '儿童锁时间设定（元素）');
INSERT INTO `PageCode` VALUES ('29', 'MineFragment', 'Element', '2', '2016-10-29 00:00:00', '我的（元素）');
INSERT INTO `PageCode` VALUES ('30', 'RecommendFragment', 'Element', '2', '2016-10-29 00:00:00', '推荐（元素）');
INSERT INTO `PageCode` VALUES ('31', 'ActivitesFragment', 'Element', '2', '2016-10-29 00:00:00', '活动（元素）');
INSERT INTO `PageCode` VALUES ('32', 'AboutActivity', 'Element', '2', '2016-10-29 00:00:00', '关于我们（元素）');
INSERT INTO `PageCode` VALUES ('33', 'MainActivity', 'Page', '5', '2016-06-24 09:22:00', '大厅主页（页面）');
INSERT INTO `PageCode` VALUES ('34', 'ManagerActivity', 'Page', '5', '2016-06-24 09:22:00', '家长管理（页面）');
INSERT INTO `PageCode` VALUES ('35', 'DetailActivity', 'Page', '5', '2016-06-24 09:22:00', '游戏详情（页面）');
INSERT INTO `PageCode` VALUES ('36', 'RecommendActivity', 'Page', '5', '2016-06-24 09:22:00', '支付推荐页（页面）');
INSERT INTO `PageCode` VALUES ('37', 'ConfirmPasswordActivity', 'Page', '5', '2016-06-24 09:22:00', '儿童锁（页面）');
INSERT INTO `PageCode` VALUES ('38', 'EmailListActivity', 'Page', '5', '2016-06-24 09:22:00', '邮件列表（页面）');
INSERT INTO `PageCode` VALUES ('39', 'VipDetailWebActivity', 'Page', '5', '2016-06-24 09:22:00', 'VIP详情（页面）');
INSERT INTO `PageCode` VALUES ('43', 'LoadingActivity', 'Page', '5', '2016-09-14 13:53:27', '大厅启动页(页面）');
INSERT INTO `PageCode` VALUES ('45', 'AboutActivity', 'Page', '5', '2016-09-19 17:37:00', '关于我们（页面）');
INSERT INTO `PageCode` VALUES ('46', 'MainActivity', 'Element', '5', '2016-06-24 09:22:00', '大厅主页（元素）');
INSERT INTO `PageCode` VALUES ('47', 'ManagerActivity', 'Element', '5', '2016-06-24 09:22:00', '家长管理（元素）');
INSERT INTO `PageCode` VALUES ('48', 'DetailActivity', 'Element', '5', '2016-06-24 09:22:00', '游戏详情（元素）');
INSERT INTO `PageCode` VALUES ('49', 'RecommendActivity', 'Element', '5', '2016-06-24 09:22:00', '支付推荐页（元素）');
INSERT INTO `PageCode` VALUES ('50', 'ConfirmPasswordActivity', 'Element', '5', '2016-06-24 09:22:00', '儿童锁（元素）');
INSERT INTO `PageCode` VALUES ('51', 'EmailListActivity', 'Element', '5', '2016-06-24 09:22:00', '邮件列表（元素）');
INSERT INTO `PageCode` VALUES ('52', 'VipFragment', 'Element', '5', '2016-06-24 09:22:00', 'VIP详情（元素）');
INSERT INTO `PageCode` VALUES ('53', 'BillboardActivitiy', 'Element', '5', '2016-06-24 09:22:00', '排行榜（元素）');
INSERT INTO `PageCode` VALUES ('54', 'KidTimeSetupActivity', 'Element', '5', '2016-06-24 09:22:00', '儿童锁时间设定（元素）');
INSERT INTO `PageCode` VALUES ('55', 'MineFragment', 'Element', '5', '2016-06-24 09:22:00', '我的（元素）');
INSERT INTO `PageCode` VALUES ('56', 'RecommendFragment', 'Element', '5', '2016-06-24 09:22:00', '推荐（元素）');
INSERT INTO `PageCode` VALUES ('57', 'ActivitesFragment', 'Element', '5', '2016-06-24 09:22:00', '活动（元素）');
INSERT INTO `PageCode` VALUES ('58', 'AboutActivity', 'Element', '5', '2016-06-24 09:22:00', '关于我们（元素）');
INSERT INTO `PageCode` VALUES ('65', 'MainActivity', 'Page', '6', '2016-08-26 00:00:00', '大厅主页（页面）');
INSERT INTO `PageCode` VALUES ('66', 'ManagerActivity', 'Page', '6', '2016-08-26 00:00:00', '管理（页面）');
INSERT INTO `PageCode` VALUES ('67', 'AboutActivity', 'Page', '6', '2016-08-26 00:00:00', '关于我们（页面）');
INSERT INTO `PageCode` VALUES ('68', 'ActionActivity', 'Page', '6', '2016-08-26 00:00:00', 'H5页面（页面）');
INSERT INTO `PageCode` VALUES ('69', 'ContactActivity', 'Page', '6', '2016-08-26 00:00:00', '联系我们（页面）');
INSERT INTO `PageCode` VALUES ('70', 'HelpActivity', 'Page', '6', '2016-08-26 00:00:00', '帮助（页面）');
INSERT INTO `PageCode` VALUES ('71', 'DetailActivity', 'Page', '6', '2016-08-26 00:00:00', '游戏详情（页面）');
INSERT INTO `PageCode` VALUES ('72', 'SetupHandlelinkActivity', 'Page', '6', '2016-08-26 00:00:00', '手柄设置（页面）');
INSERT INTO `PageCode` VALUES ('73', 'SetupHandlelinkDetail1Activity', 'Page', '6', '2016-08-26 00:00:00', '无线手柄设置向导（页面）');
INSERT INTO `PageCode` VALUES ('74', 'SetupHandlelinkDetail2Activity', 'Page', '6', '2016-08-26 00:00:00', '蓝牙手柄设置向导（页面）');
INSERT INTO `PageCode` VALUES ('75', 'EmailListActivity', 'Page', '6', '2016-08-26 00:00:00', '邮件列表（页面）');
INSERT INTO `PageCode` VALUES ('76', 'VipFragment', 'Page', '6', '2016-08-26 00:00:00', 'VIP详情页（页面）');
INSERT INTO `PageCode` VALUES ('77', 'RecommendFragment', 'Element', '6', '2016-08-26 00:00:00', '推荐（元素）');
INSERT INTO `PageCode` VALUES ('78', 'MineFragment', 'Element', '6', '2016-08-26 00:00:00', '我的（元素）');
INSERT INTO `PageCode` VALUES ('79', 'ActivitesFragment', 'Element', '6', '2016-08-26 00:00:00', '活动（元素）');
INSERT INTO `PageCode` VALUES ('80', 'VipFragment', 'Element', '6', '2016-08-26 00:00:00', 'VIP（元素）');
INSERT INTO `PageCode` VALUES ('81', 'ManagerActivity', 'Element', '6', '2016-08-26 00:00:00', '设置（元素）');
INSERT INTO `PageCode` VALUES ('82', 'AboutActivity', 'Element', '6', '2016-08-26 00:00:00', '关于我们（元素）');
INSERT INTO `PageCode` VALUES ('83', 'DetailActivity', 'Element', '6', '2016-08-26 00:00:00', '游戏详情（页面）');
INSERT INTO `PageCode` VALUES ('84', 'VipDetailWebActivity', 'Element', '6', '2016-08-26 00:00:00', 'VIP详情页（页面）');
INSERT INTO `PageCode` VALUES ('85', 'ConsumePasswordSetupActivity', 'Page', '6', '2016-06-24 09:22:00', '儿童锁（页面）');
INSERT INTO `PageCode` VALUES ('86', 'LoadingActivity', 'Page', '6', '2016-09-14 13:53:27', '大厅启动页(页面）');
INSERT INTO `PageCode` VALUES ('103', 'MainActivity', 'Page', '3', '2016-06-24 09:22:29', '大厅主页（页面）');
INSERT INTO `PageCode` VALUES ('106', 'HelpActivity', 'Page', '5', '2016-09-21 19:22:28', '帮助（页面）');
INSERT INTO `PageCode` VALUES ('107', 'HelpActivity', 'Element', '5', '2016-09-21 19:23:11', '帮助（元素）');
INSERT INTO `PageCode` VALUES ('108', 'SetupHandlelinkActivity', 'Page', '5', '2016-09-21 19:00:00', '手柄设置（页面）');
INSERT INTO `PageCode` VALUES ('109', 'SetupHandlelinkDetail1Activity', 'Page', '5', '2016-09-21 19:00:00', '无线手柄设置向导（页面）');
INSERT INTO `PageCode` VALUES ('110', 'SetupHandlelinkDetail2Activity', 'Page', '5', '2016-09-21 19:00:00', '蓝牙手柄设置向导（页面）');
INSERT INTO `PageCode` VALUES ('111', 'VipFragment', 'Page', '5', '2016-09-21 19:59:58', 'VIP列表（页面）');
INSERT INTO `PageCode` VALUES ('112', 'SettingFragment', 'Page', '5', '2016-09-21 20:00:46', '设置（页面）');
INSERT INTO `PageCode` VALUES ('113', 'AccountFragment', 'Page', '5', '2016-09-21 20:02:07', '账户（页面）');
INSERT INTO `PageCode` VALUES ('114', 'RecommendFragment', 'Element', '3', '2016-08-26 00:00:00', '推荐（元素）');
INSERT INTO `PageCode` VALUES ('115', 'MineFragment', 'Element', '3', '2016-08-26 00:00:00', '我的（元素）');
INSERT INTO `PageCode` VALUES ('116', 'ActivitesFragment', 'Element', '3', '2016-08-26 00:00:00', '活动（元素）');
INSERT INTO `PageCode` VALUES ('117', 'AccountFragment', 'Page', '6', '2016-09-28 15:20:11', '账户（页面）');
INSERT INTO `PageCode` VALUES ('118', 'BillboardActivitiy', 'Page', '6', '2016-10-13 11:18:29', '排行榜（页面）');
INSERT INTO `PageCode` VALUES ('119', 'BillboardActivitiy', 'Element', '6', '2016-10-13 11:18:29', '排行榜（元素）');
INSERT INTO `PageCode` VALUES ('120', 'LoginModule', 'Page', '6', '2016-10-13 14:18:44', '登录模块');
INSERT INTO `PageCode` VALUES ('121', 'RegisterModule', 'Page', '6', '2016-10-13 14:19:03', '注册模块');
INSERT INTO `PageCode` VALUES ('122', 'GuestModule', 'Page', '6', '2016-10-13 14:19:23', '游客模块');
INSERT INTO `PageCode` VALUES ('123', 'LoginModule', 'Page', '5', '2016-10-13 14:18:44', '登录模块（页面）');
INSERT INTO `PageCode` VALUES ('124', 'RegisterModule', 'Page', '5', '2016-10-13 14:19:03', '注册模块（页面）');
INSERT INTO `PageCode` VALUES ('125', 'GuestModule', 'Page', '5', '2016-10-13 14:19:23', '游客模块（页面）');
INSERT INTO `PageCode` VALUES ('126', 'ForgetPwdModule', 'Page', '5', '2016-10-24 12:04:27', '忘记密码（页面）');
INSERT INTO `PageCode` VALUES ('127', 'MainActivity', 'Page', '1', '2016-06-24 09:22:00', '大厅主页（页面）');
INSERT INTO `PageCode` VALUES ('128', 'ManagerActivity', 'Page', '1', '2016-06-24 09:22:00', '家长管理（页面）');
INSERT INTO `PageCode` VALUES ('129', 'DetailActivity', 'Page', '1', '2016-06-24 09:22:00', '游戏详情（页面）');
INSERT INTO `PageCode` VALUES ('130', 'RecommendActivity', 'Page', '1', '2016-06-24 09:22:00', '支付推荐页（页面）');
INSERT INTO `PageCode` VALUES ('131', 'ConfirmPasswordActivity', 'Page', '1', '2016-06-24 09:22:00', '儿童锁（页面）');
INSERT INTO `PageCode` VALUES ('132', 'EmailListActivity', 'Page', '1', '2016-06-24 09:22:00', '邮件列表（页面）');
INSERT INTO `PageCode` VALUES ('133', 'VipDetailWebActivity', 'Page', '1', '2016-06-24 09:22:00', 'VIP详情（页面）');
INSERT INTO `PageCode` VALUES ('134', 'LoadingActivity', 'Page', '1', '2016-09-14 13:53:27', '大厅启动页(页面）');
INSERT INTO `PageCode` VALUES ('135', 'AboutActivity', 'Page', '1', '2016-09-19 17:37:00', '关于我们（页面）');
INSERT INTO `PageCode` VALUES ('136', 'MainActivity', 'Element', '1', '2016-06-24 09:22:00', '大厅主页（元素）');
INSERT INTO `PageCode` VALUES ('137', 'ManagerActivity', 'Element', '1', '2016-06-24 09:22:00', '家长管理（元素）');
INSERT INTO `PageCode` VALUES ('138', 'DetailActivity', 'Element', '1', '2016-06-24 09:22:00', '游戏详情（元素）');
INSERT INTO `PageCode` VALUES ('139', 'RecommendActivity', 'Element', '1', '2016-06-24 09:22:00', '支付推荐页（元素）');
INSERT INTO `PageCode` VALUES ('140', 'ConfirmPasswordActivity', 'Element', '1', '2016-06-24 09:22:00', '儿童锁（元素）');
INSERT INTO `PageCode` VALUES ('141', 'EmailListActivity', 'Element', '1', '2016-06-24 09:22:00', '邮件列表（元素）');
INSERT INTO `PageCode` VALUES ('142', 'VipFragment', 'Element', '1', '2016-06-24 09:22:00', 'VIP详情（元素）');
INSERT INTO `PageCode` VALUES ('143', 'GameTopFragment', 'Element', '1', '2016-06-24 09:22:00', '排行榜（元素）');
INSERT INTO `PageCode` VALUES ('144', 'KidTimeSetupActivity', 'Element', '1', '2016-06-24 09:22:00', '儿童锁时间设定（元素）');
INSERT INTO `PageCode` VALUES ('145', 'MineFragment', 'Element', '1', '2016-06-24 09:22:00', '我的（元素）');
INSERT INTO `PageCode` VALUES ('146', 'RecommendFragment', 'Element', '1', '2016-06-24 09:22:00', '推荐（元素）');
INSERT INTO `PageCode` VALUES ('147', 'ActivitesFragment', 'Element', '1', '2016-06-24 09:22:00', '活动（元素）');
INSERT INTO `PageCode` VALUES ('148', 'AboutActivity', 'Element', '1', '2016-06-24 09:22:00', '关于我们（元素）');
INSERT INTO `PageCode` VALUES ('149', 'HelpActivity', 'Page', '1', '2016-09-21 19:22:28', '帮助（页面）');
INSERT INTO `PageCode` VALUES ('150', 'GameClassFragment', 'Element', '1', '2016-09-21 19:23:11', '游戏分类（元素）');
INSERT INTO `PageCode` VALUES ('151', 'SetupHandlelinkActivity', 'Page', '1', '2016-09-21 19:00:00', '手柄设置（页面）');
INSERT INTO `PageCode` VALUES ('152', 'SetupHandlelinkDetail1Activity', 'Page', '1', '2016-09-21 19:00:00', '无线手柄设置向导（页面）');
INSERT INTO `PageCode` VALUES ('153', 'SetupHandlelinkDetail2Activity', 'Page', '1', '2016-09-21 19:00:00', '蓝牙手柄设置向导（页面）');
INSERT INTO `PageCode` VALUES ('154', 'CourseFragment', 'Element', '2', '2016-10-29 00:00:00', '课程（元素）');
INSERT INTO `PageCode` VALUES ('155', 'ReadFragment', 'Element', '2', '2016-10-29 00:00:00', '亲子（元素）');
INSERT INTO `PageCode` VALUES ('156', 'TalentFragment', 'Element', '2', '2016-10-29 00:00:00', '才艺（元素）');
INSERT INTO `PageCode` VALUES ('157', 'MainActivity', 'Element', '2', '2016-10-29 00:00:00', '大厅主页（元素）');
INSERT INTO `PageCode` VALUES ('158', 'GameClassActivity', 'Page', '1', '2016-11-03 14:04:32', '游戏分类（页面）');
INSERT INTO `PageCode` VALUES ('159', 'EducationFragment', 'Element', '2', '2016-10-29 00:00:00', '教育（元素）');

-- ----------------------------
-- Records of PageColumn
-- ----------------------------
INSERT INTO `PageColumn` VALUES ('1', 'Ststus', '8', '2016-06-24 09:28:54', '1');
INSERT INTO `PageColumn` VALUES ('2', 'UpMenu ', '8', '2016-06-24 09:28:54', '1');
INSERT INTO `PageColumn` VALUES ('3', 'LeftMenu', '8', '2016-06-24 09:28:54', '1');
INSERT INTO `PageColumn` VALUES ('4', 'DownMenu', '8', '2016-06-24 09:28:54', '1');
INSERT INTO `PageColumn` VALUES ('5', 'Message', '8', '2016-06-24 09:28:54', '1');
INSERT INTO `PageColumn` VALUES ('6', 'Ststus', '8', '2016-06-24 09:28:54', '2');
INSERT INTO `PageColumn` VALUES ('7', 'UpMenu ', '8', '2016-06-24 09:28:54', '2');
INSERT INTO `PageColumn` VALUES ('8', 'LeftMenu', '8', '2016-06-24 09:28:54', '2');
INSERT INTO `PageColumn` VALUES ('9', 'DownMenu', '8', '2016-06-24 09:28:54', '2');
INSERT INTO `PageColumn` VALUES ('10', 'Message', '8', '2016-06-24 09:28:54', '2');
INSERT INTO `PageColumn` VALUES ('11', 'Ststus', '8', '2016-06-24 09:28:54', '3');
INSERT INTO `PageColumn` VALUES ('12', 'UpMenu ', '8', '2016-06-24 09:28:54', '3');
INSERT INTO `PageColumn` VALUES ('13', 'LeftMenu', '8', '2016-06-24 09:28:54', '3');
INSERT INTO `PageColumn` VALUES ('14', 'DownMenu', '8', '2016-06-24 09:28:54', '3');
INSERT INTO `PageColumn` VALUES ('15', 'Message', '8', '2016-06-24 09:28:54', '3');
INSERT INTO `PageColumn` VALUES ('16', 'Ststus', '8', '2016-06-24 09:28:54', '4');
INSERT INTO `PageColumn` VALUES ('17', 'UpMenu ', '8', '2016-06-24 09:28:54', '4');
INSERT INTO `PageColumn` VALUES ('18', 'LeftMenu', '8', '2016-06-24 09:28:54', '4');
INSERT INTO `PageColumn` VALUES ('19', 'DownMenu', '8', '2016-06-24 09:28:54', '4');
INSERT INTO `PageColumn` VALUES ('20', 'Message', '8', '2016-06-24 09:28:54', '4');
INSERT INTO `PageColumn` VALUES ('21', 'Ststus', '8', '2016-06-24 09:28:54', '5');
INSERT INTO `PageColumn` VALUES ('22', 'UpMenu', '8', '2016-06-24 09:28:54', '5');
INSERT INTO `PageColumn` VALUES ('23', 'LeftMenu', '8', '2016-06-24 09:28:54', '5');
INSERT INTO `PageColumn` VALUES ('24', 'DownMenu', '8', '2016-06-24 09:28:54', '5');
INSERT INTO `PageColumn` VALUES ('25', 'Message', '8', '2016-06-24 09:28:54', '5');
INSERT INTO `PageColumn` VALUES ('26', 'Ststus', '8', '2016-06-24 09:28:54', '6');
INSERT INTO `PageColumn` VALUES ('27', 'UpMenu ', '8', '2016-06-24 09:28:54', '6');
INSERT INTO `PageColumn` VALUES ('28', 'LeftMenu', '8', '2016-06-24 09:28:54', '6');
INSERT INTO `PageColumn` VALUES ('29', 'DownMenu', '8', '2016-06-24 09:28:54', '6');
INSERT INTO `PageColumn` VALUES ('30', 'Message', '8', '2016-06-24 09:28:54', '6');



-- ----------------------------
-- Records of SystemDomain
-- ----------------------------
INSERT INTO `SystemDomain` VALUES ('1', '双屏大厅', 'atetplatform', 'com.atet.tvmarket');
INSERT INTO `SystemDomain` VALUES ('2', '亲子时光', 'familytime', 'com.atet.familytime.tv');
INSERT INTO `SystemDomain` VALUES ('3', '组装世界', 'myworld', 'com.atet.myworld');
INSERT INTO `SystemDomain` VALUES ('4', 'TCL双屏大厅', 'tclplatform', 'com.sxhl.tcltvmarket');
INSERT INTO `SystemDomain` VALUES ('5', '家庭游戏', 'familygame', 'com.atet.familygame.tv');
INSERT INTO `SystemDomain` VALUES ('6', '海外游戏', 'overseas', 'com.atet.overseas.tv');


-- ----------------------------
-- Records of SystemFeatureLookup
-- ----------------------------
INSERT INTO `SystemFeatureLookup` VALUES ('1', '开启支付时是否提示弹框', 'CP', 'CHOICE', '', '2016-09-29 11:52:58', '2016-09-29 11:52:58', '1');
INSERT INTO `SystemFeatureLookup` VALUES ('2', '是否开启消费锁', 'P', 'CHOICE', '', '2016-09-29 11:52:58', '2016-09-29 11:52:58', '1');
INSERT INTO `SystemFeatureLookup` VALUES ('3', '是否开启儿童时间锁', 'BT', 'CHOICE', '', '2016-09-29 11:52:58', '2016-09-29 11:52:58', '1');
INSERT INTO `SystemFeatureLookup` VALUES ('4', '开启支付时是否提示弹框', 'CP', 'CHOICE', '', '2016-09-29 11:52:58', '2016-09-29 11:52:58', '2');
INSERT INTO `SystemFeatureLookup` VALUES ('5', '是否开启消费锁', 'P', 'CHOICE', '', '2016-09-29 11:52:58', '2016-09-29 11:52:58', '2');
INSERT INTO `SystemFeatureLookup` VALUES ('6', '是否开启儿童时间锁', 'BT', 'CHOICE', '', '2016-09-29 11:52:58', '2016-09-29 11:52:58', '2');
INSERT INTO `SystemFeatureLookup` VALUES ('7', '开启支付时是否提示弹框', 'CP', 'CHOICE', '', '2016-09-29 11:52:58', '2016-09-29 11:52:58', '3');
INSERT INTO `SystemFeatureLookup` VALUES ('8', '是否开启消费锁', 'P', 'CHOICE', '', '2016-09-29 11:52:58', '2016-09-29 11:52:58', '3');
INSERT INTO `SystemFeatureLookup` VALUES ('9', '是否开启儿童时间锁', 'BT', 'CHOICE', '', '2016-09-29 11:52:58', '2016-09-29 11:52:58', '3');
INSERT INTO `SystemFeatureLookup` VALUES ('10', '开启支付时是否提示弹框', 'CP', 'CHOICE', '', '2016-09-29 11:52:58', '2016-09-29 11:52:58', '4');
INSERT INTO `SystemFeatureLookup` VALUES ('11', '是否开启消费锁', 'P', 'CHOICE', '', '2016-09-29 11:52:58', '2016-09-29 11:52:58', '4');
INSERT INTO `SystemFeatureLookup` VALUES ('12', '是否开启儿童时间锁', 'BT', 'CHOICE', '', '2016-09-29 11:52:58', '2016-09-29 11:52:58', '4');
INSERT INTO `SystemFeatureLookup` VALUES ('13', '开启支付时是否提示弹框', 'CP', 'CHOICE', '', '2016-09-29 11:52:58', '2016-09-29 11:52:58', '5');
INSERT INTO `SystemFeatureLookup` VALUES ('14', '是否开启消费锁', 'P', 'CHOICE', '', '2016-09-29 11:52:58', '2016-09-29 11:52:58', '5');
INSERT INTO `SystemFeatureLookup` VALUES ('15', '是否开启儿童时间锁', 'BT', 'CHOICE', '', '2016-09-29 11:52:58', '2016-09-29 11:52:58', '5');
INSERT INTO `SystemFeatureLookup` VALUES ('16', '开启支付时是否提示弹框', 'CP', 'CHOICE', '', '2016-09-29 11:52:58', '2016-09-29 11:52:58', '6');
INSERT INTO `SystemFeatureLookup` VALUES ('17', '是否开启消费锁', 'P', 'CHOICE', '', '2016-09-29 11:52:58', '2016-09-29 11:52:58', '6');
INSERT INTO `SystemFeatureLookup` VALUES ('18', '是否开启儿童时间锁', 'BT', 'CHOICE', '', '2016-09-29 11:52:58', '2016-09-29 11:52:58', '6');

-- ----------------------------
-- Records of TimeCondition
-- ----------------------------
INSERT INTO `TimeCondition` VALUES ('1', '00', '无时间限制', '2016-06-24 15:58:13');
INSERT INTO `TimeCondition` VALUES ('2', '24', '凌晨24点解锁', '2016-06-24 15:58:54');




-- 绑定条件
insert into BindingCondition (Id,Title,Type,CreateTime) values ('null','无条件显示','NONE',now());
insert into BindingCondition (Id,Title,Type,CreateTime) values ('null','VIP用户界面时显示','VIP',now());
insert into BindingCondition (Id,Title,Type,CreateTime) values ('null','非VIP用户时显示','NOVIP',now());
insert into BindingCondition (Id,Title,Type,CreateTime) values ('null','非注册用户时显示','GUESS',now());

-- ----------------------------
-- Records of SpringBatch
-- ----------------------------
INSERT INTO `SpringBatch` VALUES ('1', 'message_Job', '2016-09-25 18:16:52');
INSERT INTO `SpringBatch` VALUES ('2', 'phone_Job', '2016-09-28 18:26:47');
INSERT INTO `SpringBatch` VALUES ('3', 'recharg_Job', '2016-09-20 14:05:19');
INSERT INTO `SpringBatch` VALUES ('4', 'dialog_Job', '2016-09-20 14:05:19');
INSERT INTO `SpringBatch` VALUES ('5', 'promotion_Job', '2016-09-20 14:05:19');

-- ----------------------------
-- Records of DeviceFeatureLookup
-- ----------------------------
INSERT INTO `DeviceFeatureLookup` VALUES ('1', 'DRT', '心跳间隔时间', null, '分钟', '2016-10-12 17:13:15');
INSERT INTO `DeviceFeatureLookup` VALUES ('2', 'NRT', '更新时间间隔', null, '分钟', '2016-10-12 17:14:25');
INSERT INTO `DeviceFeatureLookup` VALUES ('3', 'BI', '采集频率', null, '秒', '2016-10-12 17:14:44');

-- ----------------------------
-- Records of GoodsType
-- ----------------------------
INSERT INTO `GoodsType` VALUES ('1', 'VIP', 'VIP', '2016-07-19 10:57:23');
INSERT INTO `GoodsType` VALUES ('2', '礼包', 'GIFT', '2016-07-19 10:57:43');
INSERT INTO `GoodsType` VALUES ('3', '实物', 'ENTITY', '2016-07-19 10:57:52');
INSERT INTO `GoodsType` VALUES ('4', '游戏', 'GAME', '2016-10-12 17:34:07');
INSERT INTO `GoodsType` VALUES ('5', '绘本', 'BOOK', '2016-10-12 17:34:32');
INSERT INTO `GoodsType` VALUES ('6', 'A豆', 'ACOIN', '2016-10-12 17:35:29');
INSERT INTO `GoodsType` VALUES ('7', '积分', 'POINT', '2016-10-12 17:35:46');
INSERT INTO `GoodsType` VALUES ('8', '单个游戏包', 'GAMEPACK', '2016-10-12 17:36:20');