CREATE TABLE IF NOT EXISTS `apparitor_project`  (
  `id` varchar(64)  NOT NULL COMMENT '主键ID(项目代码)',
  `xmdm` varchar(64)  NOT NULL COMMENT '项目代码',
  `xmmc` varchar(128)  NOT NULL COMMENT '项目名称',
  `dwdm` varchar(18)  NOT NULL COMMENT '单位代码',
  `xzqhdm` varchar(6)  NOT NULL COMMENT '行政区划代码',
  `kqdm` varchar(21)  NOT NULL COMMENT '库区代码',
  `nf` char(4)  NOT NULL COMMENT '年份',
  `xmlx` varchar(1)  NOT NULL COMMENT '项目类型',
  `jsnr` varchar(1024)  NOT NULL COMMENT '建设内容及规模',
  `nkgsj` datetime(0) NOT NULL COMMENT '拟开工时间',
  `njcsj` datetime(0) NOT NULL COMMENT '拟建成时间',
  `jszt` varchar(1)  NULL DEFAULT NULL COMMENT '建设状态；1:已立项未开工、2:建设中，3:已验收，4:已取消',
  `sbrq` date NOT NULL COMMENT '申报日期',
  `spwh` varchar(128)  NULL DEFAULT NULL COMMENT '审批文号',
  `xmdw` varchar(18)  NULL DEFAULT NULL COMMENT '项目 (法人) 单位（统一社会信用代码）',
  `fddbrzzlx` varchar(2)  NULL DEFAULT NULL COMMENT '法定代表人证照类型',
  `fddbrzzhm` varchar(32)  NULL DEFAULT NULL COMMENT '法定代表人证照号码',
  `lxr` varchar(16)  NULL DEFAULT NULL COMMENT '联系人',
  `lxfs` varchar(16)  NULL DEFAULT NULL COMMENT '联系方式',
  `dzyx` varchar(32)  NULL DEFAULT NULL COMMENT '电子邮箱',
  `jsdd` varchar(128)  NULL DEFAULT NULL COMMENT '建设地点',
  `ztz` decimal(20, 6) NULL DEFAULT NULL COMMENT '总投资（万元）',
  `gdzctz` decimal(20, 6) NULL DEFAULT NULL COMMENT '固定资产投资（万元）',
  `zyczzj` decimal(20, 6) NULL DEFAULT NULL COMMENT '中央财政资金（万元）',
  `sczzj` decimal(20, 6) NULL DEFAULT NULL COMMENT '省财政资金（万元）',
  `sczzj01` decimal(20, 6) NULL DEFAULT NULL COMMENT '市财政资金（万元）',
  `yhdk` decimal(20, 6) NULL DEFAULT NULL COMMENT '银行贷款（万元）',
  `gpzq` decimal(20, 6) NULL DEFAULT NULL COMMENT '股票债券（万元）',
  `qtzj` decimal(20, 6) NULL DEFAULT NULL COMMENT '其他资金（万元）',
  `xmdzjd` decimal(20, 6) NULL DEFAULT NULL COMMENT '项目地址经度',
  `zmdzwd` decimal(20, 6) NULL DEFAULT NULL COMMENT '项目地址纬度',
  `czbz` char(1)  NOT NULL COMMENT '操作标志（i：新增(默认)，u：更新，d：删除）',
  `zhgxsj` datetime(0) NOT NULL COMMENT '最后更新时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64)  NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  `update_by` varchar(64)  NULL DEFAULT NULL COMMENT '更新人',
  `region_id` varchar(10)  NOT NULL DEFAULT '-1' COMMENT '行政区域ID',
  `country_id` varchar(10)  NOT NULL DEFAULT '-1' COMMENT '国ID',
  `province_id` varchar(10)  NOT NULL DEFAULT '-1' COMMENT '省ID',
  `city_id` varchar(10)  NOT NULL DEFAULT '-1' COMMENT '市ID',
  `area_id` varchar(10)  NOT NULL DEFAULT '-1' COMMENT '区ID',
  `enterprise_id` varchar(18)  NOT NULL DEFAULT '-1' COMMENT '企业ID',
  `stock_house_id` varchar(21)  NOT NULL DEFAULT '-1' COMMENT '库区ID',
  PRIMARY KEY (`id`, `update_time`) USING BTREE,
  UNIQUE INDEX `uid_xmdm`(`xmdm`) USING BTREE COMMENT '项目代码唯一索引',
  INDEX `idx_xmmc`(`xmmc`) USING BTREE COMMENT '项目名称普通索引',
  INDEX `idx_nf`(`nf`) USING BTREE COMMENT '年份普通索引',
  INDEX `idx_nkgsj`(`nkgsj`) USING BTREE COMMENT '拟开工时间普通索引',
  INDEX `idx_njcsj`(`njcsj`) USING BTREE COMMENT '拟建成时间普通索引',
  INDEX `idx_frzzhm`(`fddbrzzhm`) USING BTREE COMMENT '法人证照号码普通索引',
  INDEX `idx_jszt`(`jszt`) USING BTREE COMMENT '建设状态普通索引',
  INDEX `idx_region_id`(`region_id`) USING BTREE COMMENT '行政区域ID普通索引',
  INDEX `idx_region_ids`(`country_id`, `province_id`, `city_id`, `area_id`) USING BTREE COMMENT '国/省/市/区组合普通索引',
  INDEX `idx_update_time`(`update_time`) USING BTREE COMMENT '最后更新时间普通索引',
  INDEX `idx_enterprise`(`enterprise_id`) USING BTREE COMMENT '企业ID普通索引',
  INDEX `idx_stock_house`(`stock_house_id`) USING BTREE COMMENT '库区ID普通索引'
) ENGINE = InnoDB COMMENT = '行政管理-项目信息表';