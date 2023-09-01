CREATE TABLE IF NOT EXISTS `warn_foodstuff_message` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '库存数量-粮食库存异常告警id',
  `warn_level` varchar(60) DEFAULT NULL COMMENT '预警等级字典：warn_level',
  `warn_type` varchar(60) DEFAULT NULL COMMENT '预警类型字典：warn_type',
  `warn_date` datetime DEFAULT NULL COMMENT '预警时间',
  `dwdm` varchar(18) NOT NULL COMMENT '单位代码',
  `warn_content` varchar(1000) DEFAULT NULL COMMENT '预警内容',
  `handle_status` varchar(60) DEFAULT NULL COMMENT '处理状态字典：handle_status',
  `handle_date` datetime DEFAULT NULL COMMENT '处理时间',
  `handle_people` varchar(60) DEFAULT NULL COMMENT '处理人',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `region_id` varchar(10) DEFAULT NULL COMMENT '行政区域ID',
  `country_id` varchar(10) DEFAULT NULL COMMENT '国ID',
  `province_id` varchar(10) DEFAULT NULL COMMENT '省ID',
  `city_id` varchar(10) DEFAULT NULL COMMENT '市ID',
  `area_id` varchar(10) DEFAULT NULL COMMENT '区ID',
  `enterprise_id` varchar(18) DEFAULT NULL COMMENT '企业ID',
  `stock_house_id` varchar(21) DEFAULT NULL COMMENT '库区ID',
  `rule_id` varchar(60) DEFAULT NULL COMMENT '预留字段：预警规则id',
  `recipient` varchar(255) DEFAULT NULL COMMENT '预留字段：接收人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB COMMENT='库存数量-粮食库存异常告警';

-- 字典
delete from basis_dict where dict_type = 'warn_level';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'warn_level', 1, '1', '紧急', NULL, NULL, NULL, '储备粮', 'admin', '2022-07-28 15:31:29', '超级管理员', '2023-04-28 15:14:15');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'warn_level', 2, '2', '重要', NULL, NULL, NULL, '储备粮', 'admin', '2022-07-28 15:31:29', '超级管理员', '2023-04-28 15:14:15');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'warn_level', 3, '3', '次要', NULL, NULL, NULL, '储备粮', 'admin', '2022-07-28 15:31:29', '超级管理员', '2023-04-28 15:14:15');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'warn_level', 4, '4', '一般', NULL, NULL, NULL, '储备粮', 'admin', '2022-07-28 15:31:29', '超级管理员', '2023-04-28 15:14:15');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'warn_level', 5, '5', '提示', NULL, NULL, NULL, '储备粮', 'admin', '2022-07-28 15:31:29', '超级管理员', '2023-04-28 15:14:15');

delete from basis_dict where dict_type = 'handle_status';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('handle_status', 1, '1', '未处理', NULL, NULL, NULL, '储备粮', 'admin', '2022-07-28 15:31:29', '超级管理员', '2023-04-28 15:14:15');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('handle_status', 2, '2', '处理中', NULL, NULL, NULL, '储备粮', 'admin', '2022-07-28 15:31:29', '超级管理员', '2023-04-28 15:14:15');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('handle_status', 3, '3', '已处理', NULL, NULL, NULL, '储备粮', 'admin', '2022-07-28 15:31:29', '超级管理员', '2023-04-28 15:14:15');

delete from basis_dict where dict_type = 'warn_type';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'warn_type', 1, '1', '库存超限告警', NULL, NULL, NULL, '储备粮', 'admin', '2022-07-28 15:31:29', '超级管理员', '2023-04-28 15:14:15');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'warn_type', 2, '2', '库存数量异常波动告警', NULL, NULL, NULL, '储备粮', 'admin', '2022-07-28 15:31:29', '超级管理员', '2023-04-28 18:43:26');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'warn_type', 3, '3', '储备年限预警', NULL, NULL, NULL, '储备粮', 'admin', '2022-07-28 15:31:29', '超级管理员', '2023-04-28 18:43:26');
