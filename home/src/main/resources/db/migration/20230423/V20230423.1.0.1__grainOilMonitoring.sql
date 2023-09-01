CREATE TABLE IF NOT EXISTS  `monitoring_grain_oil_price` (
    `id` varchar(40) NOT NULL COMMENT '主键ID',
    `point_id` varchar(40) NOT NULL COMMENT '监测点ID',
    `create_by` varchar(50) DEFAULT NULL COMMENT '添加人员',
    `create_time` datetime DEFAULT NULL COMMENT '添加时间',
    `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
    `update_time` datetime NOT NULL COMMENT '修改时间',
    `region_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '行政区域ID',
    `country_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '国ID',
    `province_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '省ID',
    `city_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '市ID',
    `area_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '区ID',
    `enterprise_id` varchar(18) NOT NULL DEFAULT '-1' COMMENT '企业ID',
    `stock_house_id` varchar(21) NOT NULL DEFAULT '-1' COMMENT '库区ID',
    PRIMARY KEY (`id`),
    KEY `idx_region_id` (`region_id`) COMMENT '行政区域ID普通索引',
    KEY `idx_region_ids` (`country_id`,`province_id`,`city_id`,`area_id`) COMMENT '行政地区组合索引',
    KEY `idx_update_time` (`update_time`) COMMENT '最后更新时间普通索引',
    KEY `idx_enterprise` (`enterprise_id`) COMMENT '企业ID普通索引',
    KEY `idx_stock_house` (`stock_house_id`) COMMENT '库区ID普通索引'
) ENGINE=InnoDB  COMMENT='流通检测-粮油价格采集明主表';

CREATE TABLE IF NOT EXISTS  `monitoring_grain_oil_price_dtl` (
    `id` varchar(40) NOT NULL COMMENT '主键ID',
    `main_id` varchar(40) NOT NULL COMMENT '主表ID',
    `bill_code` varchar(40) NOT NULL COMMENT '单据编号',
    `sale_purchase_type` varchar(40) NOT NULL COMMENT '购销类型：销售/采购',
    `monitor_date` date NOT NULL COMMENT '检测日期',
    `bill_date` date DEFAULT NULL COMMENT '单据日期',
    `volatility` decimal(12,2) DEFAULT NULL COMMENT '波动情况',
    `qty` decimal(12,2) DEFAULT NULL COMMENT '数量',
    `bast_qty` decimal(12,2) DEFAULT NULL COMMENT '基本数量',
    `bast_unit` decimal(12,2) DEFAULT NULL COMMENT '基本单位',
    `tax_price` decimal(12,2) DEFAULT NULL COMMENT '含税单价',
    `dwdm` varchar(18) DEFAULT NULL COMMENT '企业单位代码',
    `cfdm` varchar(40) NOT NULL COMMENT '仓库代码',
    `ajdm` varchar(40) NOT NULL COMMENT '廒间代码',
    `hwdm` varchar(40) NOT NULL COMMENT '堆位代码',
    `material_name` varchar(40) NOT NULL COMMENT '物料名称',
    `lspzdm` varchar(40) NOT NULL COMMENT '粮食品种代码',
    `administrative_divisions` varchar(40) NOT NULL COMMENT '行政区划',
    `create_by` varchar(50) DEFAULT NULL COMMENT '添加人员ID',
    `create_time` datetime DEFAULT NULL COMMENT '添加时间',
    `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
    `update_time` datetime NOT NULL COMMENT '修改时间',
    `region_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '行政区域ID',
    `country_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '国ID',
    `province_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '省ID',
    `city_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '市ID',
    `area_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '区ID',
    `enterprise_id` varchar(18) NOT NULL DEFAULT '-1' COMMENT '企业ID',
    `stock_house_id` varchar(21) NOT NULL DEFAULT '-1' COMMENT '库区ID',
    PRIMARY KEY (`id`),
    KEY `idx_region_id` (`region_id`) COMMENT '行政区域ID普通索引',
    KEY `idx_region_ids` (`country_id`,`province_id`,`city_id`,`area_id`) COMMENT '行政地区组合索引',
    KEY `idx_update_time` (`update_time`) COMMENT '最后更新时间普通索引',
    KEY `idx_enterprise` (`enterprise_id`) COMMENT '企业ID普通索引',
    KEY `idx_stock_house` (`stock_house_id`) COMMENT '库区ID普通索引'
) ENGINE=InnoDB  COMMENT='流通检测-粮油价格采集明细表';

CREATE TABLE IF NOT EXISTS  `monitoring_check_point_grain` (
    `id` varchar(40) NOT NULL COMMENT '主键ID',
    `lspzdm` varchar(7) NOT NULL COMMENT '粮食品种代码',
    `point_id` varchar(40) NOT NULL COMMENT '监测点ID',
    `create_by` varchar(50) DEFAULT NULL COMMENT '添加人员ID',
    `create_time` datetime DEFAULT NULL COMMENT '添加时间',
    `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
    `update_time` datetime NOT NULL COMMENT '修改时间',
    `region_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '行政区域ID',
    `country_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '国ID',
    `province_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '省ID',
    `city_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '市ID',
    `area_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '区ID',
    `enterprise_id` varchar(18) NOT NULL DEFAULT '-1' COMMENT '企业ID',
    `stock_house_id` varchar(21) NOT NULL DEFAULT '-1' COMMENT '库区ID',
    PRIMARY KEY (`id`),
    KEY `idx_point_id` (`point_id`) COMMENT '监测点ID普通索引',
    KEY `idx_region_id` (`region_id`) COMMENT '行政区域ID普通索引',
    KEY `idx_region_ids` (`country_id`,`province_id`,`city_id`,`area_id`) COMMENT '行政地区组合索引',
    KEY `idx_update_time` (`update_time`) COMMENT '最后更新时间普通索引',
    KEY `idx_enterprise` (`enterprise_id`) COMMENT '企业ID普通索引',
    KEY `idx_stock_house` (`stock_house_id`) COMMENT '库区ID普通索引'
) ENGINE=InnoDB  COMMENT='流通检测-监测点粮食品种关联表';

CREATE TABLE IF NOT EXISTS  `monitoring_check_point` (
    `id` varchar(40) NOT NULL COMMENT '主键ID',
    `name` varchar(40) NOT NULL COMMENT '监测点名称',
    `status` tinyint(1) DEFAULT '1' COMMENT '状态：1-正常，0-禁用',
    `jd` varchar(40) DEFAULT NULL COMMENT '经度',
    `wd` varchar(40) DEFAULT NULL COMMENT '纬度',
    `account_user_id` varchar(30) NOT NULL COMMENT '监测点账号ID',
    `create_by` varchar(50) DEFAULT NULL COMMENT '添加人员ID',
    `create_time` datetime DEFAULT NULL COMMENT '添加时间',
    `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
    `update_time` datetime NOT NULL COMMENT '修改时间',
    `region_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '行政区域ID',
    `country_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '国ID',
    `province_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '省ID',
    `city_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '市ID',
    `area_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '区ID',
    `enterprise_id` varchar(18) NOT NULL DEFAULT '-1' COMMENT '企业ID',
    `stock_house_id` varchar(21) NOT NULL DEFAULT '-1' COMMENT '库区ID',
    PRIMARY KEY (`id`),
    KEY `idx_region_id` (`region_id`) COMMENT '行政区域ID普通索引',
    KEY `idx_account_user_id` (`account_user_id`) COMMENT '监测点账号ID普通索引',
    KEY `idx_region_ids` (`country_id`,`province_id`,`city_id`,`area_id`) COMMENT '行政地区组合索引',
    KEY `idx_update_time` (`update_time`) COMMENT '最后更新时间普通索引',
    KEY `idx_enterprise` (`enterprise_id`) COMMENT '企业ID普通索引',
    KEY `idx_stock_house` (`stock_house_id`) COMMENT '库区ID普通索引'
) ENGINE=InnoDB  COMMENT='流通检测-监测点配置表';

CREATE TABLE IF NOT EXISTS  `monitoring_check_point` (
    `id` varchar(40) NOT NULL COMMENT '主键ID',
    `name` varchar(40) NOT NULL COMMENT '监测点名称',
    `status` tinyint(1) DEFAULT '1' COMMENT '状态：1-正常，0-禁用',
    `jd` varchar(40) DEFAULT NULL COMMENT '经度',
    `wd` varchar(40) DEFAULT NULL COMMENT '纬度',
    `account_user_id` varchar(30) NOT NULL COMMENT '监测点账号ID',
    `create_by` varchar(50) DEFAULT NULL COMMENT '添加人员ID',
    `create_time` datetime DEFAULT NULL COMMENT '添加时间',
    `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
    `update_time` datetime NOT NULL COMMENT '修改时间',
    `region_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '行政区域ID',
    `country_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '国ID',
    `province_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '省ID',
    `city_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '市ID',
    `area_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '区ID',
    `enterprise_id` varchar(18) NOT NULL DEFAULT '-1' COMMENT '企业ID',
    `stock_house_id` varchar(21) NOT NULL DEFAULT '-1' COMMENT '库区ID',
    PRIMARY KEY (`id`),
    KEY `idx_region_id` (`region_id`) COMMENT '行政区域ID普通索引',
    KEY `idx_account_user_id` (`account_user_id`) COMMENT '监测点账号ID普通索引',
    KEY `idx_region_ids` (`country_id`,`province_id`,`city_id`,`area_id`) COMMENT '行政地区组合索引',
    KEY `idx_update_time` (`update_time`) COMMENT '最后更新时间普通索引',
    KEY `idx_enterprise` (`enterprise_id`) COMMENT '企业ID普通索引',
    KEY `idx_stock_house` (`stock_house_id`) COMMENT '库区ID普通索引'
) ENGINE=InnoDB  COMMENT='流通检测-监测点配置表';

CREATE TABLE IF NOT EXISTS  `monitoring_check_point_price` (
     `id` varchar(40) NOT NULL COMMENT '主键ID',
     `price_type` varchar(7) NOT NULL COMMENT '价格类型',
     `point_id` bigint NOT NULL COMMENT '监测点ID',
     `create_by` varchar(50) DEFAULT NULL COMMENT '添加人员ID',
     `create_time` datetime DEFAULT NULL COMMENT '添加时间',
     `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
     `update_time` datetime NOT NULL COMMENT '修改时间',
     `region_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '行政区域ID',
     `country_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '国ID',
     `province_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '省ID',
     `city_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '市ID',
     `area_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '区ID',
     `enterprise_id` varchar(18) NOT NULL DEFAULT '-1' COMMENT '企业ID',
     `stock_house_id` varchar(21) NOT NULL DEFAULT '-1' COMMENT '库区ID',
     PRIMARY KEY (`id`),
     KEY `idx_point_id` (`point_id`) COMMENT '监测点ID普通索引',
     KEY `idx_region_id` (`region_id`) COMMENT '行政区域ID普通索引',
     KEY `idx_region_ids` (`country_id`,`province_id`,`city_id`,`area_id`) COMMENT '行政地区组合索引',
     KEY `idx_update_time` (`update_time`) COMMENT '最后更新时间普通索引',
     KEY `idx_enterprise` (`enterprise_id`) COMMENT '企业ID普通索引',
     KEY `idx_stock_house` (`stock_house_id`) COMMENT '库区ID普通索引'
) ENGINE=InnoDB  COMMENT='流通检测-监测点价格关联表';

CREATE TABLE IF NOT EXISTS  `monitoring_good_produce_company` (
    `id` varchar(40) NOT NULL COMMENT '主键ID',
    `company_name` varchar(50) NOT NULL COMMENT '企业名称',
    `status` varchar(15) DEFAULT 'PENDING_AUDIT' COMMENT '状态：PENDING_AUDIT-待审，PASS-审核通过，NOT_PASS-审核不通过',
    `build_detail` varchar(400) DEFAULT NULL COMMENT '建设情况',
    `if_distribution_center` tinyint(1) DEFAULT '0' COMMENT '是否配送中心',
    `if_sale_shop` tinyint(1) DEFAULT '0' COMMENT '是否经营店',
    `sale_detail` varchar(400) DEFAULT NULL COMMENT '经营情况',
    `grain_oil_annual_sales` decimal(12,6) DEFAULT NULL COMMENT '粮油销售量（吨/年）',
    `profit` decimal(12,2) DEFAULT NULL COMMENT '盈利情况（千万）',
    `if_sale_school` tinyint(1) DEFAULT NULL COMMENT '是否进学校',
    `school_qty` decimal(12,6) DEFAULT '0.000000' COMMENT '进学校数量',
    `school_annual_sales` decimal(12,6) DEFAULT NULL COMMENT '学校粮油销售量（吨/年）',
    `create_by` varchar(50) DEFAULT NULL COMMENT '添加人员ID',
    `create_time` datetime DEFAULT NULL COMMENT '添加时间',
    `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
    `update_time` datetime NOT NULL COMMENT '修改时间',
    `region_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '行政区域ID',
    `country_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '国ID',
    `province_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '省ID',
    `city_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '市ID',
    `area_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '区ID',
    `enterprise_id` varchar(18) NOT NULL DEFAULT '-1' COMMENT '企业ID',
    `stock_house_id` varchar(21) NOT NULL DEFAULT '-1' COMMENT '库区ID',
    PRIMARY KEY (`id`),
    KEY `idx_region_id` (`region_id`) COMMENT '行政区域ID普通索引',
    KEY `idx_region_ids` (`country_id`,`province_id`,`city_id`,`area_id`) COMMENT '行政地区组合索引',
    KEY `idx_update_time` (`update_time`) COMMENT '最后更新时间普通索引',
    KEY `idx_enterprise` (`enterprise_id`) COMMENT '企业ID普通索引',
    KEY `idx_stock_house` (`stock_house_id`) COMMENT '库区ID普通索引'
) ENGINE=InnoDB COMMENT='流通检测-放心粮油企业';

CREATE TABLE IF NOT EXISTS  `monitoring_good_produce_company` (
   `id` varchar(40) NOT NULL COMMENT '主键ID',
   `company_name` varchar(50) NOT NULL COMMENT '企业名称',
   `status` varchar(15) DEFAULT 'PENDING_AUDIT' COMMENT '状态：PENDING_AUDIT-待审，PASS-审核通过，NOT_PASS-审核不通过',
   `build_detail` varchar(400) DEFAULT NULL COMMENT '建设情况',
   `if_distribution_center` tinyint(1) DEFAULT '0' COMMENT '是否配送中心',
   `if_sale_shop` tinyint(1) DEFAULT '0' COMMENT '是否经营店',
   `sale_detail` varchar(400) DEFAULT NULL COMMENT '经营情况',
   `grain_oil_annual_sales` decimal(12,6) DEFAULT NULL COMMENT '粮油销售量（吨/年）',
   `profit` decimal(12,2) DEFAULT NULL COMMENT '盈利情况（千万）',
   `if_sale_school` tinyint(1) DEFAULT NULL COMMENT '是否进学校',
   `school_qty` decimal(12,6) DEFAULT '0.000000' COMMENT '进学校数量',
   `school_annual_sales` decimal(12,6) DEFAULT NULL COMMENT '学校粮油销售量（吨/年）',
   `create_by` varchar(50) DEFAULT NULL COMMENT '添加人员ID',
   `create_time` datetime DEFAULT NULL COMMENT '添加时间',
   `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
   `update_time` datetime NOT NULL COMMENT '修改时间',
   `region_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '行政区域ID',
   `country_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '国ID',
   `province_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '省ID',
   `city_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '市ID',
   `area_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '区ID',
   `enterprise_id` varchar(18) NOT NULL DEFAULT '-1' COMMENT '企业ID',
   `stock_house_id` varchar(21) NOT NULL DEFAULT '-1' COMMENT '库区ID',
   PRIMARY KEY (`id`),
   KEY `idx_region_id` (`region_id`) COMMENT '行政区域ID普通索引',
   KEY `idx_region_ids` (`country_id`,`province_id`,`city_id`,`area_id`) COMMENT '行政地区组合索引',
   KEY `idx_update_time` (`update_time`) COMMENT '最后更新时间普通索引',
   KEY `idx_enterprise` (`enterprise_id`) COMMENT '企业ID普通索引',
   KEY `idx_stock_house` (`stock_house_id`) COMMENT '库区ID普通索引'
) ENGINE=InnoDB  COMMENT='流通检测-放心粮油企业';

CREATE TABLE IF NOT EXISTS  `monitoring_good_company` (
    `id` varchar(40) NOT NULL COMMENT '主键ID',
    `company_name` varchar(50) NOT NULL COMMENT '企业名称',
    `status` varchar(15) DEFAULT 'PENDING_AUDIT' COMMENT '状态：PENDING_AUDIT-待审，PASS-审核通过，NOT_PASS-审核不通过',
    `record_source` varchar(15) DEFAULT '粮食平台' COMMENT '备案来源',
    `record_date` varchar(15) DEFAULT NULL COMMENT '备案日期',
    `company_type` varchar(20) DEFAULT NULL COMMENT '企业类型，字典：dwlx',
    `company_nature` varchar(20) DEFAULT NULL COMMENT '企业性质',
    `stock_store_type` varchar(20) DEFAULT NULL COMMENT '仓储业务类型',
    `business_license_issuing_authority` varchar(50) DEFAULT NULL COMMENT '营业执照发证机关',
    `registry_date` date DEFAULT NULL COMMENT '注册时间',
    `registry_capital` int DEFAULT NULL COMMENT '注册资本（万元）',
    `company_phone` varchar(13) DEFAULT NULL COMMENT '公司电话',
    `address` varchar(200) DEFAULT NULL COMMENT '注册地址',
    `superior_food_sector` varchar(60) DEFAULT NULL COMMENT '上级粮食部门',
    `affiliation` varchar(60) DEFAULT NULL COMMENT '隶属关系',
    `business_scope` varchar(60) DEFAULT NULL COMMENT '经营范围',
    `business_address_lng_lat` varchar(60) DEFAULT NULL COMMENT '经营地址经纬度',
    `acquisition_eligibility` varchar(60) DEFAULT NULL COMMENT '收购资格',
    `proxy_eligibility` varchar(60) DEFAULT NULL COMMENT '代储资格',
    `business_address` varchar(60) DEFAULT NULL COMMENT '经营地址',
    `corporation` varchar(60) DEFAULT NULL COMMENT '法人',
    `business_license` varchar(200) DEFAULT NULL COMMENT '营业执照',
    `food_licenses` varchar(200) DEFAULT NULL COMMENT '食品许可证',
    `grain_oil_sales` decimal(12,6) DEFAULT NULL COMMENT '粮油销售额（千万）',
    `product_sales` decimal(12,6) DEFAULT NULL COMMENT '产品销售额（千万）',
    `grains_sales` decimal(12,6) DEFAULT NULL COMMENT '杂粮销售额（千万）',
    `sales_products` decimal(12,6) DEFAULT NULL COMMENT '制品销售额（千万）',
    `school_annual_sales` decimal(12,6) DEFAULT NULL COMMENT '学校粮油销售量（吨/年）',
    `create_by` varchar(50) DEFAULT NULL COMMENT '添加人员ID',
    `create_time` datetime DEFAULT NULL COMMENT '添加时间',
    `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
    `update_time` datetime NOT NULL COMMENT '修改时间',
    `region_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '行政区域ID',
    `country_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '国ID',
    `province_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '省ID',
    `city_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '市ID',
    `area_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '区ID',
    `enterprise_id` varchar(18) NOT NULL DEFAULT '-1' COMMENT '企业ID',
    `stock_house_id` varchar(21) NOT NULL DEFAULT '-1' COMMENT '库区ID',
    `credit_code` varchar(18) DEFAULT NULL COMMENT '统一社会信用代码',
    `storage_varieties` varchar(40) DEFAULT NULL COMMENT '仓储品种',
    `platform_register` varchar(40) DEFAULT NULL COMMENT '粮油平台注册',
    PRIMARY KEY (`id`),
    KEY `idx_region_id` (`region_id`) COMMENT '行政区域ID普通索引',
    KEY `idx_region_ids` (`country_id`,`province_id`,`city_id`,`area_id`) COMMENT '行政地区组合索引',
    KEY `idx_update_time` (`update_time`) COMMENT '最后更新时间普通索引',
    KEY `idx_enterprise` (`enterprise_id`) COMMENT '企业ID普通索引',
    KEY `idx_stock_house` (`stock_house_id`) COMMENT '库区ID普通索引'
) ENGINE=InnoDB COMMENT='流通检测-好粮油企业';

CREATE TABLE IF NOT EXISTS  `monitoring_statistics_stock_scale` (
    `id` varchar(40) NOT NULL COMMENT '主键ID',
    `statistic_id` varchar(40) NOT NULL COMMENT '粮油流通统计ID',
    `grain_oil_nature` varchar(40) DEFAULT NULL COMMENT '粮油性质',
    `material_type` varchar(40) DEFAULT NULL COMMENT '品种',
    `store_scale` decimal(12,6) DEFAULT NULL COMMENT '储备规模（吨）',
    `store_plan_no` varchar(40) DEFAULT NULL COMMENT '储备计划文号',
    `year` varchar(40) DEFAULT NULL COMMENT '年度',
    `create_by` varchar(50) DEFAULT NULL COMMENT '添加人员',
    `create_time` datetime DEFAULT NULL COMMENT '添加时间',
    `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
    `update_time` datetime NOT NULL COMMENT '修改时间',
    `region_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '行政区域ID',
    `country_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '国ID',
    `province_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '省ID',
    `city_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '市ID',
    `area_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '区ID',
    `enterprise_id` varchar(18) NOT NULL DEFAULT '-1' COMMENT '企业ID',
    `stock_house_id` varchar(21) NOT NULL DEFAULT '-1' COMMENT '库区ID',
    PRIMARY KEY (`id`),
    KEY `idx_region_id` (`region_id`) COMMENT '行政区域ID普通索引',
    KEY `idx_region_ids` (`country_id`,`province_id`,`city_id`,`area_id`) COMMENT '行政地区组合索引',
    KEY `idx_update_time` (`update_time`) COMMENT '最后更新时间普通索引',
    KEY `idx_enterprise` (`enterprise_id`) COMMENT '企业ID普通索引',
    KEY `idx_stock_house` (`stock_house_id`) COMMENT '库区ID普通索引'
) ENGINE=InnoDB COMMENT='流通检测-库存规模';

/***插入默认字典***/
INSERT ignore INTO basis_dict (dict_type, dict_sort, dict_key, dict_value, dict_parent_key, dict_top_key, dict_far_type, remark, create_by, create_time, update_by, update_time) VALUES('monitoring_price', 99, '05', '批发价', '', NULL, NULL, '流通监测-价格类型', 'admin', '2022-07-28 15:31:29', '超级管理员', '2023-04-25 16:42:22');
INSERT ignore INTO basis_dict (dict_type, dict_sort, dict_key, dict_value, dict_parent_key, dict_top_key, dict_far_type, remark, create_by, create_time, update_by, update_time) VALUES('monitoring_price', 99, '04', '销售价', '', NULL, NULL, '流通监测-价格类型', 'admin', '2022-07-28 15:31:29', '超级管理员', '2023-04-25 16:42:22');
INSERT ignore INTO basis_dict (dict_type, dict_sort, dict_key, dict_value, dict_parent_key, dict_top_key, dict_far_type, remark, create_by, create_time, update_by, update_time) VALUES('monitoring_price', 99, '03', '零售价', '', NULL, NULL, '流通监测-价格类型', 'admin', '2022-07-28 15:31:29', '超级管理员', '2023-04-25 16:42:22');
INSERT ignore INTO basis_dict (dict_type, dict_sort, dict_key, dict_value, dict_parent_key, dict_top_key, dict_far_type, remark, create_by, create_time, update_by, update_time) VALUES('monitoring_price', 99, '02', '出厂价', '', NULL, NULL, '流通监测-价格类型', 'admin', '2022-07-28 15:31:29', '超级管理员', '2023-04-25 16:42:22');
INSERT ignore INTO basis_dict (dict_type, dict_sort, dict_key, dict_value, dict_parent_key, dict_top_key, dict_far_type, remark, create_by, create_time, update_by, update_time) VALUES('monitoring_price', 99, '01', '进厂价', '', NULL, NULL, '流通监测-价格类型', 'admin', '2022-07-28 15:31:29', '超级管理员', '2023-04-25 16:42:22');

/***初始化角色菜单***/
INSERT ignore INTO `org_role` (`id`, `name`, `permission`, `order_num`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1650769995518578689', '企业监测点角色', 'monitoring_point', '1', '超级管理员', '2023-04-25 15:53:31', '超级管理员', '2023-04-25 17:19:02');

INSERT ignore INTO `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1649960625876635649', '0', 'CL', '储备布局地理信息', '/StorageLayout', NULL, 'read-outlined', '10', NULL, '超级管理员', '2023-04-23 10:17:23', '超级管理员', '2023-04-23 10:30:04');
INSERT ignore INTO `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1649961174386741250', '1649960625876635649', 'MU', '企业信息', '/organizeInfo', 'organizeInfo', 'home-outlined', '1', NULL, '超级管理员', '2023-04-23 10:19:33', '超级管理员', '2023-04-23 10:19:33');
INSERT ignore INTO `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1649967762103209985', '1649960625876635649', 'CL', '保管作业', '/custodyJob', NULL, 'align-left-outlined', '5', NULL, '超级管理员', '2023-04-23 10:45:44', '超级管理员', '2023-04-23 10:46:26');
INSERT ignore INTO `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1649969769719402497', '1649967762103209985', 'MU', '通风作业管理', '/ventilateJob', 'ventilateJob', 'bars-outlined', '1', NULL, '超级管理员', '2023-04-23 10:53:43', '超级管理员', '2023-04-23 11:01:14');
INSERT ignore INTO `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1649970025966211074', '1649967762103209985', 'MU', '熏蒸药剂管理', '/fumigationAgent', 'fumigationAgent', 'ordered-list-outlined', '2', NULL, '超级管理员', '2023-04-23 10:54:44', '超级管理员', '2023-04-23 11:01:21');
INSERT ignore INTO `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1649970127384481794', '1649967762103209985', 'MU', '熏蒸作业管理', '/fumigationJob', 'fumigationJob', 'project-outlined', '1', NULL, '超级管理员', '2023-04-23 10:55:08', '超级管理员', '2023-04-23 11:01:29');
INSERT ignore INTO `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1650028838639505410', '0', 'CL', '综合业务', '/generalService', NULL, 'layout-outlined', '11', NULL, '超级管理员', '2023-04-23 14:48:26', '超级管理员', '2023-04-23 14:48:42');
INSERT ignore INTO `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1650029202776395777', '1650028838639505410', 'CL', '流通检测', '/flowDetection', NULL, 'file-zip-outlined', '1', NULL, '超级管理员', '2023-04-23 14:49:53', '超级管理员', '2023-04-23 14:49:53');
INSERT ignore INTO `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1650036929959432194', '1650029202776395777', 'MU', '粮食价格采集', '/grainPriceCollection', 'grainPriceCollection', 'layout-outlined', '1', NULL, '超级管理员', '2023-04-23 15:20:35', '超级管理员', '2023-04-23 15:20:46');
INSERT ignore INTO `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1650037047991341058', '1650029202776395777', 'MU', '粮食价格汇总分析', '/grainPriceSum', 'grainPriceSum', 'project-outlined', '2', NULL, '超级管理员', '2023-04-23 15:21:03', '超级管理员', '2023-04-23 15:21:03');
INSERT ignore INTO `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1650039861941506049', '1650029202776395777', 'MU', '监测点配置', '/monitoringSiteCfg', 'monitoringSiteCfg', 'menu-outlined', '3', NULL, '超级管理员', '2023-04-23 15:32:14', '超级管理员', '2023-04-23 15:32:14');
INSERT ignore INTO `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1650039934968532994', '1650029202776395777', 'MU', '放心粮油企业', '/restAssuredGrainAndOil', 'restAssuredGrainAndOil', 'message-outlined', '4', NULL, '超级管理员', '2023-04-23 15:32:31', '超级管理员', '2023-04-23 15:32:31');
INSERT ignore INTO `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1650040000592613378', '1650029202776395777', 'MU', '好粮油企业', '/goodGrainAndOil', 'goodGrainAndOil', 'money-collect-outlined', '5', NULL, '超级管理员', '2023-04-23 15:32:47', '超级管理员', '2023-04-23 15:32:47');
INSERT ignore INTO `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1650040073003077634', '1650029202776395777', 'MU', '粮油疏通统计', '/dredgeStatistics', 'dredgeStatistics', 'idcard-outlined', '6', NULL, '超级管理员', '2023-04-23 15:33:04', '超级管理员', '2023-04-23 15:33:04');
INSERT ignore INTO `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1650061891998978050', '1649960625876635649', 'CL', '仓储单位备案', '/storageUnitRegistration', NULL, 'laptop-outlined', '2', NULL, '超级管理员', '2023-04-23 16:59:46', '超级管理员', '2023-04-23 16:59:46');
INSERT ignore INTO `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1650072475586924546', '1650061891998978050', 'MU', '粮库信息备案', '/grainInformation', 'grainInformation', 'form-outlined', '1', NULL, '超级管理员', '2023-04-23 17:41:50', '超级管理员', '2023-04-23 19:20:35');
INSERT ignore INTO `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1650072725793935361', '1650061891998978050', 'MU', '仓房信息备案', '/warehouseInfoFiling', 'warehouseInfoFiling', 'diff-outlined', '2', NULL, '超级管理员', '2023-04-23 17:42:49', '超级管理员', '2023-04-23 18:43:17');
INSERT ignore INTO `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1650072971026501633', '1650061891998978050', 'MU', '廒间信息备案', '/granaryInfoFiling', 'granaryInfoFiling', 'diff-outlined', '3', NULL, '超级管理员', '2023-04-23 17:43:48', '超级管理员', '2023-04-23 17:43:48');
INSERT ignore INTO `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1650073071681409025', '1650061891998978050', 'MU', '货位信息备案', '/locationInfoFiling', 'locationInfoFiling', 'diff-outlined', '4', NULL, '超级管理员', '2023-04-23 17:44:12', '超级管理员', '2023-04-23 17:44:12');
INSERT ignore INTO `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1650073200702394370', '1650061891998978050', 'MU', '油罐信息备案', '/oilcanInfoFiling', 'oilcanInfoFiling', 'diff-outlined', '5', NULL, '超级管理员', '2023-04-23 17:44:43', '超级管理员', '2023-04-23 17:44:43');
INSERT ignore INTO `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1650073939965251585', '1650061891998978050', 'MU', '粮油专业人员信息库', '/grainOilProfessionals', 'grainOilProfessionals', 'radar-chart-outlined', '6', NULL, '超级管理员', '2023-04-23 17:47:39', '超级管理员', '2023-04-23 18:43:34');

INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1649960626320453632', '1', '1649960625876635649');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1649961174901862400', '1', '1649961174386741250');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1649967762284032000', '1', '1649967762103209985');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1649969769875058688', '1', '1649969769719402497');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1649970026084118528', '1', '1649970025966211074');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1649970127598858240', '1', '1649970127384481794');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1650028839411724288', '1', '1650028838639505410');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1650029204442001408', '1', '1650029202776395777');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1650036934321975296', '1', '1650036929959432194');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1650037049099104256', '1', '1650037047991341058');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1650039862092967936', '1', '1650039861941506049');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1650039935161937920', '1', '1650039934968532994');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1650040000920236032', '1', '1650040000592613378');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1650040073330700288', '1', '1650040073003077634');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1650061892163022848', '1', '1650061891998978050');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1650072475960684544', '1', '1650072475586924546');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1650072726033477632', '1', '1650072725793935361');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1650072971236683776', '1', '1650072971026501633');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1650073072373936128', '1', '1650073071681409025');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1650073201134874624', '1', '1650073200702394370');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1650073940238348288', '1', '1650073939965251585');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1650791567335735302', '1650769995518578689', '1650028838639505410');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1650791567335735298', '1650769995518578689', '1650029202776395777');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1650791567335735303', '1650769995518578689', '1650036929959432194');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1650791567335735299', '1650769995518578689', '1650037047991341058');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1650791567335735301', '1650769995518578689', '1650039861941506049');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1650791567335735296', '1650769995518578689', '1650039934968532994');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1650791567335735300', '1650769995518578689', '1650040000592613378');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1650791567335735297', '1650769995518578689', '1650040073003077634');
