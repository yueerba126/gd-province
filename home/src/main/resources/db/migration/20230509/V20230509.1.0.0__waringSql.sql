/***物料大类***/
delete from basis_dict where dict_type = 'food_big_variety';

INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'food_big_variety', 0, '1', '小麦类', '', NULL, '小麦类', 'new', 'admin', '2022-08-26 15:22:49', '超级管理员', '2023-04-20 18:52:02');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'food_big_variety', 0, '2', '玉米类', '', NULL, '玉米类', 'new', 'admin', '2022-08-26 15:22:49', '超级管理员', '2023-04-20 19:05:39');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'food_big_variety', 0, '3', '稻谷类', '', NULL, '稻谷类', 'new', 'admin', '2022-08-26 15:22:49', '超级管理员', '2023-04-20 19:05:40');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'food_big_variety', 0, '4', '经济作物类', '', NULL, '经济作物类', 'new', 'admin', '2022-08-26 15:22:49', '超级管理员', '2023-04-20 18:34:59');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'food_big_variety', 0, '5', '成品食用植物油类', '', NULL, '成品食用植物油类', 'new', 'admin', '2022-08-26 15:22:49', '超级管理员', '2023-04-20 18:47:11');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'food_big_variety', 0, '6', '原油（毛油）类', '', NULL, '原油（毛油）类', 'new', NULL, '2023-03-27 18:13:22', NULL, '2023-04-20 17:41:18');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'food_big_variety', 0, '7', '小麦粉类', '', NULL, '小麦粉类', 'new', 'admin', '2022-08-26 15:22:49', '超级管理员', '2023-04-20 18:39:07');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'food_big_variety', 0, '8', '玉米加工类', '', NULL, '玉米加工类', 'new', 'admin', '2022-08-26 15:22:49', '超级管理员', '2023-04-20 18:45:30');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'food_big_variety', 0, '9', '大米类', '', NULL, '大米类', 'new', 'admin', '2022-08-26 15:22:49', '超级管理员', '2023-04-20 18:44:29');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'food_big_variety', 0, '10', '粮食加工副产品类', '', NULL, '粮食加工副产品类', 'new', NULL, '2023-03-27 18:13:22', NULL, '2023-04-20 18:50:59');


CREATE TABLE IF NOT EXISTS `admin_age_threshold` (
  `id` bigint NOT NULL COMMENT '库存年限告警阈值设置id',
  `ylpz` varchar(7)  NOT NULL COMMENT '粮食品种代码 字典：food_big_variety',
  `remarks` varchar(256) DEFAULT NULL COMMENT '备注',
  `czbz` char(1)  DEFAULT 'i' COMMENT '操作标志（i：新增(默认)，u：更新，d：删除）',
  `zhgxsj` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新人',
  `region_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '行政区域ID',
  `country_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '国ID',
  `province_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '省ID',
  `city_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '市ID',
  `area_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '区ID',
  `enterprise_id` varchar(18) NOT NULL DEFAULT '-1' COMMENT '企业ID',
  `stock_house_id` varchar(21) NOT NULL DEFAULT '-1' COMMENT '库区ID',
  `age_value` int DEFAULT NULL COMMENT '年限值',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_ylpz` (`ylpz`) USING BTREE COMMENT '粮食品种代码普通索引',
  KEY `idx_region_id` (`region_id`) USING BTREE COMMENT '行政区域ID普通索引',
  KEY `idx_region_ids` (`country_id`,`province_id`,`city_id`,`area_id`) USING BTREE COMMENT '国/省/市/区组合普通索引',
  KEY `idx_update_time` (`update_time`) USING BTREE COMMENT '最后更新时间普通索引',
  KEY `idx_enterprise` (`enterprise_id`) USING BTREE COMMENT '企业ID普通索引',
  KEY `idx_stock_house` (`stock_house_id`) USING BTREE COMMENT '库区ID普通索引'
) ENGINE=InnoDB COMMENT='库存年限告警阈值设置';


CREATE TABLE IF NOT EXISTS `admin_grain_threshold` (
  `id` bigint NOT NULL COMMENT '粮情预警阈值设置id',
  `remarks` varchar(256) DEFAULT NULL COMMENT '备注',
  `czbz` char(1) DEFAULT 'i' COMMENT '操作标志（i：新增(默认)，u：更新，d：删除）',
  `zhgxsj` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新人',
  `region_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '行政区域ID',
  `country_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '国ID',
  `province_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '省ID',
  `city_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '市ID',
  `area_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '区ID',
  `enterprise_id` varchar(18) NOT NULL DEFAULT '-1' COMMENT '企业ID',
  `stock_house_id` varchar(21) NOT NULL DEFAULT '-1' COMMENT '库区ID',
  `gjts` int DEFAULT NULL COMMENT '告警天数',
  `zglw` decimal(10,2) DEFAULT NULL COMMENT '最高粮温（℃）',
  `zdlw` decimal(10,2) DEFAULT NULL COMMENT '最低粮温（℃）',
  `zgls` decimal(10,2) DEFAULT NULL COMMENT '最高粮湿（%）',
  `zdls` decimal(10,2) DEFAULT NULL COMMENT '最低粮湿（%）',
  `zgcnw` decimal(10,2) DEFAULT NULL COMMENT '最高仓内温（℃）',
  `zdcnw` decimal(10,2) DEFAULT NULL COMMENT '最低仓内温（℃）',
  `zgcnsd` decimal(10,2) DEFAULT NULL COMMENT '最高仓内湿度（%）',
  `zdcnsd` decimal(10,2) DEFAULT NULL COMMENT '最低仓内湿度（%）',
  `zgcww` decimal(10,2) DEFAULT NULL COMMENT '最高仓外温（℃）',
  `zdcww` decimal(10,2) DEFAULT NULL COMMENT '最低仓外温（℃）',
  `zgcwsd` decimal(10,2) DEFAULT NULL COMMENT '最高仓外湿度（%）',
  `zdcwsd` decimal(10,2) DEFAULT NULL COMMENT '最低仓外湿度（%）',
  `zgch` decimal(10,2) DEFAULT NULL COMMENT '最高虫害（头）',
  `zgyq` decimal(10,2) DEFAULT NULL COMMENT '最高氧气',
  `zdyq` decimal(10,2) DEFAULT NULL COMMENT '最低氧气',
  `zgdq` decimal(10,2) DEFAULT NULL COMMENT '最高氮气',
  `zddq` decimal(10,2) DEFAULT NULL COMMENT '最低氮气',
  `zgeyht` decimal(10,2) DEFAULT NULL COMMENT '最高二氧化碳',
  `zdeyht` decimal(10,2) DEFAULT NULL COMMENT '最低二氧化碳',
  `zglhq` decimal(10,2) DEFAULT NULL COMMENT '最高硫化氢',
  `zdlhq` decimal(10,2) DEFAULT NULL COMMENT '最低硫化氢',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_region_id` (`region_id`) USING BTREE COMMENT '行政区域ID普通索引',
  KEY `idx_region_ids` (`country_id`,`province_id`,`city_id`,`area_id`) USING BTREE COMMENT '国/省/市/区组合普通索引',
  KEY `idx_update_time` (`update_time`) USING BTREE COMMENT '最后更新时间普通索引',
  KEY `idx_enterprise` (`enterprise_id`) USING BTREE COMMENT '企业ID普通索引',
  KEY `idx_stock_house` (`stock_house_id`) USING BTREE COMMENT '库区ID普通索引'
) ENGINE=InnoDB  COMMENT='粮情预警阈值';

CREATE TABLE IF NOT EXISTS `admin_plan_threshold` (
  `id` bigint NOT NULL COMMENT '储备计划阈值设置id',
  `remarks` varchar(256) DEFAULT NULL COMMENT '备注',
  `czbz` char(1) DEFAULT 'i' COMMENT '操作标志（i：新增(默认)，u：更新，d：删除）',
  `zhgxsj` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新人',
  `region_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '行政区域ID',
  `country_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '国ID',
  `province_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '省ID',
  `city_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '市ID',
  `area_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '区ID',
  `enterprise_id` varchar(18) NOT NULL DEFAULT '-1' COMMENT '企业ID',
  `stock_house_id` varchar(21) NOT NULL DEFAULT '-1' COMMENT '库区ID',
  `xzqhdm` varchar(6) DEFAULT NULL COMMENT '行政区划代码',
  `gjyz` decimal(10,2) DEFAULT NULL COMMENT '告警阈值（%）',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_region_id` (`region_id`) USING BTREE COMMENT '行政区域ID普通索引',
  KEY `idx_region_ids` (`country_id`,`province_id`,`city_id`,`area_id`) USING BTREE COMMENT '国/省/市/区组合普通索引',
  KEY `idx_update_time` (`update_time`) USING BTREE COMMENT '最后更新时间普通索引',
  KEY `idx_enterprise` (`enterprise_id`) USING BTREE COMMENT '企业ID普通索引',
  KEY `idx_stock_house` (`stock_house_id`) USING BTREE COMMENT '库区ID普通索引',
  KEY `idx_xzqhdm` (`xzqhdm`) USING BTREE COMMENT '行政区划代码普通索引'
) ENGINE=InnoDB COMMENT='储备计划阈值设置';