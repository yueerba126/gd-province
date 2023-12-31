CREATE TABLE IF NOT EXISTS `apparitor_plan`  (
  `id` varchar(64)  NOT NULL COMMENT '主键ID(单位代码)',
  `dwdm` varchar(18)  NOT NULL COMMENT '单位代码',
  `kqdm` varchar(21)  NOT NULL COMMENT '库区代码',
  `lxrq` date NOT NULL COMMENT '立项日期,格式：yyyy-MM-dd',
  `xzqhdm` varchar(6)  NOT NULL COMMENT '行政区划代码',
  `xmdm` varchar(64)  NOT NULL COMMENT '项目代码',
  `xmmc` varchar(128)  NOT NULL COMMENT '项目名称',
  `jsnr` varchar(1024)  NOT NULL COMMENT '主要建设内容',
  `design_unit` varchar(256)  NOT NULL COMMENT '设计单位',
  `jhtz` decimal(20, 6) NOT NULL COMMENT '计划投资(万元)',
  `tendering_unit` varchar(256)  NULL DEFAULT NULL COMMENT '招标单位',
  `winning_bidder_unit` varchar(256)  NULL DEFAULT NULL COMMENT '中标单位',
  `zbje` decimal(20, 6) NULL DEFAULT NULL COMMENT '中标金额(万元)',
  `construction_unit` varchar(256)  NULL DEFAULT NULL COMMENT '监理单位',
  `third_party_evaluation_unit` varchar(256)  NULL DEFAULT NULL COMMENT '第三方评测单位',
  `audit_unit` varchar(256)  NULL DEFAULT NULL COMMENT '审计单位',
  `jsje` decimal(20, 6) NULL DEFAULT NULL COMMENT '决算金额(万元)',
  `xmjz_code` varchar(20)  NOT NULL COMMENT '项目进展(设计01、立项02、招标03、开工04、完工05、验收06)',
  `czbz` char(1)  NOT NULL COMMENT '操作标志（i：新增(默认)，u：更新，d：删除）',
  `create_by` varchar(50)  NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50)  NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  `zhgxsj` datetime(0) NOT NULL COMMENT '最后更新时间',
  `region_id` varchar(10)  NOT NULL DEFAULT '-1' COMMENT '行政区域ID',
  `country_id` varchar(10)  NOT NULL DEFAULT '-1' COMMENT '国ID',
  `province_id` varchar(10)  NOT NULL DEFAULT '-1' COMMENT '省ID',
  `city_id` varchar(10)  NOT NULL DEFAULT '-1' COMMENT '市ID',
  `area_id` varchar(10)  NOT NULL DEFAULT '-1' COMMENT '区ID',
  `enterprise_id` varchar(18)  NOT NULL DEFAULT '-1' COMMENT '企业ID',
  `stock_house_id` varchar(21)  NOT NULL DEFAULT '-1' COMMENT '库区ID',
  PRIMARY KEY (`id`, `update_time`) USING BTREE,
  UNIQUE INDEX `uid_xmdm`(`xmdm`) USING BTREE COMMENT '项目代码唯一索引',
  INDEX `idx_region_id`(`region_id`) USING BTREE COMMENT '行政区域ID普通索引',
  INDEX `idx_region_ids`(`country_id`, `province_id`, `city_id`, `area_id`) USING BTREE COMMENT '国/省/市/区组合普通索引',
  INDEX `idx_update_time`(`update_time`) USING BTREE COMMENT '最后更新时间普通索引',
  INDEX `idx_enterprise`(`enterprise_id`) USING BTREE COMMENT '企业ID普通索引',
  INDEX `idx_stock_house`(`stock_house_id`) USING BTREE COMMENT '库区ID普通索引',
  INDEX `idx_xmmc`(`xmmc`) USING BTREE COMMENT '项目名称普通索引'
) ENGINE = InnoDB COMMENT = '行政管理-仓储设施建设管理';