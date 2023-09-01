CREATE TABLE IF NOT EXISTS  `apparitor_system` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '人员制度管理id',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '最后更新者',
  `update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  `tenant_id` bigint DEFAULT NULL COMMENT '租户ID',
  `enterprise_id` bigint DEFAULT NULL COMMENT '租户ID',
  `region_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '行政区域ID',
  `country_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '国ID',
  `province_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '省ID',
  `city_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '市ID',
  `area_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '区ID',
  `stock_house_id` varchar(21) DEFAULT '-1' COMMENT '库区ID',
  `dept_id` varchar(60) DEFAULT NULL COMMENT '部门',
  `file_type_id` bigint DEFAULT NULL COMMENT '文件类别id',
  `file_name` varchar(255) DEFAULT NULL COMMENT '文件标题',
  `number` varchar(255) DEFAULT NULL COMMENT '发文号',
  `remark` varchar(2000) DEFAULT NULL COMMENT '文件描述',
  `release_id` varchar(60) DEFAULT NULL COMMENT '发布人ID',
  `release_time` datetime DEFAULT NULL COMMENT '发布时间',
  `bill_status` varchar(20)  DEFAULT NULL COMMENT '状态',
  `file_attachment` longtext COMMENT '附件记录',
  `dwdm` varchar(18) NOT NULL COMMENT '单位代码',
  `dwmc` varchar(256) NOT NULL COMMENT '单位名称',
  PRIMARY KEY (`id`),
  KEY `idx_dept_id` (`dept_id`) USING BTREE COMMENT '部门ID普通索引',
  KEY `idx_file_type_id` (`file_type_id`) USING BTREE COMMENT '文件类别ID普通索引',
  KEY `idx_bill_status` (`bill_status`) USING BTREE COMMENT '状态普通索引',
  KEY `idx_region_id` (`region_id`) USING BTREE COMMENT '行政区域ID普通索引',
  KEY `idx_region_ids` (`country_id`,`province_id`,`city_id`,`area_id`) USING BTREE COMMENT '国/省/市/区组合普通索引',
  KEY `idx_update_time` (`update_time`) USING BTREE COMMENT '最后更新时间普通索引',
  KEY `idx_enterprise` (`enterprise_id`) USING BTREE COMMENT '企业ID普通索引',
  KEY `idx_stock_house` (`stock_house_id`) USING BTREE COMMENT '库区ID普通索引'
) ENGINE=InnoDB  COMMENT='行政管理-人员制度管理';

CREATE TABLE IF NOT EXISTS `apparitor_system_zoning` (
  `zoning_id` bigint NOT NULL AUTO_INCREMENT COMMENT '人员制度管理管理行政区划id',
  `system_id` bigint NOT NULL COMMENT '人员制度管理id',
  `region_id` varchar(60) DEFAULT NULL COMMENT '行政区划Id',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '最后更新者',
  `update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`zoning_id`),
  KEY `idx_system_id` (`system_id`) USING BTREE COMMENT '人员制度管理id普通索引',
  KEY `idx_region_id` (`region_id`) USING BTREE COMMENT '行政区划普通索引'
) ENGINE=InnoDB  COMMENT='行政管理-人员制度管理管理行政区划';


delete from basis_dict where dict_type = 'apparitor_status';
delete from basis_dict where dict_type = 'case_type';
delete from basis_dict where dict_type = 'case_source';

-- 字典添加
-- 人员管理 人员制度管理状态
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'apparitor_status', 1, '1', '拟稿', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'apparitor_status', 2, '2', '发布', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');

-- 案件管理-案件类型
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'case_type', 1, '1', '粮食质量', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'case_type', 2, '2', '企业运营', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ( 'case_type', 3, '3', '其他', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');

-- 案件管理-案件来源
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('case_source', 1, '1', '监督检查工作', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('case_source', 2, '2', '举报', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('case_source', 3, '3', '上级交办、下级报请、有关部门移送或其他方式披露的监督检查事项', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
