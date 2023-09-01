CREATE TABLE IF NOT EXISTS `plan_reserve_plan_send_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `main_id` bigint DEFAULT NULL COMMENT '主表ID',
  `detail_id` bigint DEFAULT NULL COMMENT '详情表id',
  `remarks` varchar(256) DEFAULT NULL COMMENT '备注',
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
  `send_time` datetime DEFAULT NULL COMMENT '下发时间',
  `send_log` longtext COMMENT '下发返回值',
  `send_status` varchar(60) DEFAULT NULL COMMENT '下发状态',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_region_id` (`region_id`) USING BTREE COMMENT '行政区域ID普通索引',
  KEY `idx_region_ids` (`country_id`,`province_id`,`city_id`,`area_id`) USING BTREE COMMENT '国/省/市/区组合普通索引',
  KEY `idx_update_time` (`update_time`) USING BTREE COMMENT '最后更新时间普通索引',
  KEY `idx_enterprise` (`enterprise_id`) USING BTREE COMMENT '企业ID普通索引',
  KEY `idx_stock_house` (`stock_house_id`) USING BTREE COMMENT '库区ID普通索引',
  KEY `idx_main_id` (`main_id`) USING BTREE COMMENT '主表ID普通索引'
) ENGINE=InnoDB COMMENT='储备计划管理-储备计划下发记录';


delete from basis_dict where dict_type = 'food_plan_variety';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'food_plan_variety', 0, '1', '小麦类', '', NULL, '小麦类', 'new', 'admin', '2022-08-26 15:22:49', '超级管理员', '2023-04-20 18:52:02');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'food_plan_variety', 0, '2', '玉米类', '', NULL, '玉米类', 'new', 'admin', '2022-08-26 15:22:49', '超级管理员', '2023-04-20 19:05:39');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'food_plan_variety', 0, '3', '稻谷类', '', NULL, '稻谷类', 'new', 'admin', '2022-08-26 15:22:49', '超级管理员', '2023-04-20 19:05:40');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'food_plan_variety', 0, '4', '经济作物类', '', NULL, '经济作物类', 'new', 'admin', '2022-08-26 15:22:49', '超级管理员', '2023-04-20 18:34:59');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'food_plan_variety', 0, '5', '成品食用植物油类', '', NULL, '成品食用植物油类', 'new', 'admin', '2022-08-26 15:22:49', '超级管理员', '2023-04-20 18:47:11');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'food_plan_variety', 0, '6', '原油（毛油）类', '', NULL, '原油（毛油）类', 'new', NULL, '2023-03-27 18:13:22', NULL, '2023-04-20 17:41:18');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'food_plan_variety', 0, '7', '小麦粉类', '', NULL, '小麦粉类', 'new', 'admin', '2022-08-26 15:22:49', '超级管理员', '2023-04-20 18:39:07');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'food_plan_variety', 0, '8', '玉米加工类', '', NULL, '玉米加工类', 'new', 'admin', '2022-08-26 15:22:49', '超级管理员', '2023-04-20 18:45:30');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'food_plan_variety', 0, '9', '大米类', '', NULL, '大米类', 'new', 'admin', '2022-08-26 15:22:49', '超级管理员', '2023-04-20 18:44:29');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'food_plan_variety', 0, '10', '粮食加工副产品类', '', NULL, '粮食加工副产品类', 'new', NULL, '2023-03-27 18:13:22', NULL, '2023-04-20 18:50:59');

delete from basis_dict where dict_type = 'reserve_plan_status';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'reserve_plan_status', 1, 'save', '拟稿', '', NULL, '', '', '', '2022-08-26 15:22:49', '超级管理员', '2023-04-20 18:52:02');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'reserve_plan_status', 1, 'to_be_issued', '待下发', '', NULL, '', '', '', '2022-08-26 15:22:49', '超级管理员', '2023-04-20 18:52:02');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'reserve_plan_status', 1, 'distribution', '已下发', '', NULL, '', '', '', '2022-08-26 15:22:49', '超级管理员', '2023-04-20 18:52:02');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'reserve_plan_status', 1, 'adjust_be_issued', '调整待下发', '', NULL, '', '', '', '2022-08-26 15:22:49', '超级管理员', '2023-04-20 18:52:02');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'reserve_plan_status', 1, 'distribution_failed', '下发失败', '', NULL, '', '', '', '2022-08-26 15:22:49', '超级管理员', '2023-04-20 18:52:02');

delete from basis_dict where dict_type = 'send_status';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'send_status', 1, '1', '下发成功', '', NULL, '', '', '', '2022-08-26 15:22:49', '超级管理员', '2023-04-20 18:52:02');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'send_status', 1, '2', '下发失败', '', NULL, '', '', '', '2022-08-26 15:22:49', '超级管理员', '2023-04-20 18:52:02');

