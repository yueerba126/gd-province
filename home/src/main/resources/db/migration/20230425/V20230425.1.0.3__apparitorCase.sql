CREATE TABLE IF NOT EXISTS  `apparitor_case` (
   `id` bigint NOT NULL AUTO_INCREMENT COMMENT '执法案件ID',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `enterprise_id` varchar(18) NOT NULL DEFAULT '-1' COMMENT '企业ID',
  `case_name` varchar(255) DEFAULT NULL COMMENT '案件名称',
  `case_enterprise_id` varchar(60) DEFAULT NULL COMMENT '案件企业',
  `corporation` varchar(60) DEFAULT NULL COMMENT '法定代表人',
  `phone` varchar(40) DEFAULT NULL COMMENT '联系电话',
  `address` varchar(255) DEFAULT NULL COMMENT '联系人地址',
  `case_type` varchar(60) DEFAULT NULL COMMENT '案件类型',
  `place` varchar(255) DEFAULT NULL COMMENT '案发地点',
  `case_date` date DEFAULT NULL COMMENT '案发时间',
  `case_source` varchar(255) DEFAULT NULL COMMENT '案件来源',
  `case_describe` varchar(2000) DEFAULT NULL COMMENT '立案描述',
  `file_attachment` longtext COMMENT '案件附件',
  `case_procedure` varchar(60) DEFAULT NULL COMMENT '案件处理-处理程序',
  `case_completion` date DEFAULT NULL COMMENT '案件处理-办理完结日期',
  `remark` varchar(2000) DEFAULT NULL COMMENT '案件处理-备注',
  `file_record` longtext COMMENT '案件处理-现场笔录',
  `file_sheet` longtext COMMENT '案件处理-现场检查记录表',
  `file_handle` longtext COMMENT '案件处理-办理附件',
  `tenant_id` bigint DEFAULT NULL COMMENT '租户ID',
  `region_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '行政区域ID',
  `country_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '国ID',
  `province_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '省ID',
  `city_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '市ID',
  `area_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '区ID',
  `stock_house_id` varchar(21) NOT NULL DEFAULT '-1' COMMENT '库区ID',
  PRIMARY KEY (`id`) ,
  KEY `idx_case_name` (`case_name`) USING BTREE COMMENT '案件名称普通索引',
  KEY `idx_case_enterprise_id` (`case_enterprise_id`) USING BTREE COMMENT '案件企业普通索引',
  KEY `idx_region_id` (`region_id`) USING BTREE COMMENT '行政区域ID普通索引',
  KEY `idx_region_ids` (`country_id`,`province_id`,`city_id`,`area_id`) USING BTREE COMMENT '国/省/市/区组合普通索引',
  KEY `idx_update_time` (`update_time`) USING BTREE COMMENT '最后更新时间普通索引',
  KEY `idx_enterprise` (`enterprise_id`) USING BTREE COMMENT '企业ID普通索引',
  KEY `idx_stock_house` (`stock_house_id`) USING BTREE COMMENT '库区ID普通索引'
) ENGINE=InnoDB  COMMENT='行政管理-执法案件';