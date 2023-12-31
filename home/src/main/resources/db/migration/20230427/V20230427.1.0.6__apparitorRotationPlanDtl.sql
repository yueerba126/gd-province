CREATE TABLE IF NOT EXISTS `apparitor_rotation_plan_dtl`  (
  `id` varchar(50)  NOT NULL COMMENT '主键id(计划明细单号)',
  `jhmxdh` varchar(50)  NOT NULL COMMENT '计划明细单号',
  `lhjhdh` varchar(25)  NOT NULL COMMENT '轮换计划单号',
  `lspzdm` varchar(7)  NOT NULL COMMENT '粮食品种代码',
  `lsdjdm` varchar(2)  NULL DEFAULT NULL COMMENT '粮食等级代码',
  `lsxzdm` varchar(3)  NOT NULL COMMENT '粮食性质代码',
  `shnd` varchar(4)  NOT NULL COMMENT '收获年度',
  `lhhwdm` varchar(30)  NOT NULL COMMENT '轮换货位代码',
  `lhsl` decimal(20, 3) NOT NULL COMMENT '轮换数量(单位吨)',
  `lhlx` varchar(1)  NOT NULL COMMENT '轮换类型(1.轮出 2.轮入)',
  `czbz` varchar(1)  NOT NULL COMMENT '操作标志',
  `zhgxsj` datetime(0) NOT NULL COMMENT '最后更新时间',
  `create_by` varchar(64)  NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64)  NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  `region_id` varchar(10)  NOT NULL DEFAULT '-1' COMMENT '行政区域ID',
  `country_id` varchar(10)  NOT NULL DEFAULT '-1' COMMENT '国ID',
  `province_id` varchar(10)  NOT NULL DEFAULT '-1' COMMENT '省ID',
  `city_id` varchar(10)  NOT NULL DEFAULT '-1' COMMENT '市ID',
  `area_id` varchar(10)  NOT NULL DEFAULT '-1' COMMENT '区ID',
  `enterprise_id` varchar(18)  NOT NULL DEFAULT '-1' COMMENT '企业ID',
  `stock_house_id` varchar(21)  NOT NULL DEFAULT '-1' COMMENT '库区ID',
  PRIMARY KEY (`id`, `update_time`) USING BTREE,
  UNIQUE INDEX `unx_jhmxdh`(`jhmxdh`) USING BTREE COMMENT '计划明细单号唯一索引',
  INDEX `idx_lhjhdh`(`lhjhdh`) USING BTREE COMMENT '轮换计划单号普通索引',
  INDEX `idx_region_id`(`region_id`) USING BTREE COMMENT '行政区域ID普通索引',
  INDEX `idx_region_ids`(`country_id`, `province_id`, `city_id`, `area_id`) USING BTREE COMMENT '国/省/市/区组合普通索引',
  INDEX `idx_update_time`(`update_time`) USING BTREE COMMENT '最后更新时间普通索引',
  INDEX `idx_enterprise`(`enterprise_id`) USING BTREE COMMENT '企业ID普通索引',
  INDEX `idx_stock_house`(`stock_house_id`) USING BTREE COMMENT '库区ID普通索引'
) ENGINE = InnoDB COMMENT = '行政管理-轮换计划明细表';
