CREATE TABLE IF NOT EXISTS `graded_enterprise_flow`  (
  `id` varchar(66)  NOT NULL COMMENT '主键ID',
  `qyid` varchar(66)  NOT NULL COMMENT '企业ID',
  `flow_code` varchar(255)  NULL DEFAULT NULL COMMENT '流程编码',
  `flow_status` varchar(255)  NULL DEFAULT NULL COMMENT '流程状态(1:通过，2:未通过)',
  `flow_sort` int(11) NULL DEFAULT NULL COMMENT '流程排序',
  `czbz` char(1)  NOT NULL COMMENT '操作标志',
  `xzqhdm` varchar(6)  NOT NULL COMMENT '所在区域代码',
  `create_by` varchar(50)  NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50)  NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`, `update_time`) USING BTREE
) ENGINE = InnoDB  COMMENT = '企业申报流程表' ;


CREATE TABLE IF NOT EXISTS `graded_enterprise_material`  (
  `id` varchar(66)  NOT NULL COMMENT '主键ID',
  `qyid` varchar(66)  NOT NULL COMMENT '企业ID',
  `file_id` varchar(66)  NULL DEFAULT NULL COMMENT '附件id',
  `file_name` varchar(255)  NOT NULL COMMENT '材料名称',
  `cl_sort` int(11) NOT NULL DEFAULT 0 COMMENT '材料顺序',
  `czbz` char(1)  NOT NULL COMMENT '操作标志',
  `create_by` varchar(50)  NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50)  NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`, `update_time`) USING BTREE
) ENGINE = InnoDB  COMMENT = '企业申报证明材料' ;


CREATE TABLE IF NOT EXISTS `graded_enterprise_process`  (
  `id` varchar(66)  NOT NULL COMMENT '主键ID',
  `qyid` varchar(66)  NOT NULL COMMENT '企业ID',
  `qyshr` varchar(255)  NULL DEFAULT NULL COMMENT '审核人',
  `shjg` varchar(1)  NULL DEFAULT NULL COMMENT '审核结果',
  `shsj` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `shyj` varchar(1000)  NULL DEFAULT NULL COMMENT '审核意见',
  `czbz` char(1)  NOT NULL COMMENT '操作标志',
  `create_by` varchar(50)  NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50)  NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`, `update_time`) USING BTREE
) ENGINE = InnoDB  COMMENT = '企业申报审核详情' ;


CREATE TABLE IF NOT EXISTS `graded_enterprise_review`  (
  `id` varchar(66)  NOT NULL COMMENT '主键ID',
  `qymc` varchar(150)  NULL DEFAULT NULL COMMENT '企业名称',
  `kqmc` varchar(150)  NULL DEFAULT NULL COMMENT '库区名称',
  `qydm` varchar(150)  NOT NULL COMMENT '企业代码',
  `kqdm` varchar(150)  NOT NULL COMMENT '库区代码',
  `sbnf` varchar(4)  NOT NULL COMMENT '申报年份',
  `sbrq` date NOT NULL COMMENT '申报或推荐日期',
  `xzqhdm` varchar(6)  NOT NULL COMMENT '所在区域代码',
  `sbdj` varchar(1)  NULL DEFAULT NULL COMMENT '申报等级(1A-5A)',
  `spdj` varchar(1)  NULL DEFAULT NULL COMMENT '授牌等级(1A-5A)',
  `spnf` varchar(4)  NULL DEFAULT NULL COMMENT '授牌年份',
  `spzt` varchar(1)  NULL DEFAULT NULL COMMENT '授牌状态',
  `lkcr` decimal(20, 3) NULL DEFAULT NULL COMMENT '粮库仓(罐)容(吨)',
  `zpf` decimal(20, 1) NULL DEFAULT NULL COMMENT '自评分（总）',
  `sbzt` varchar(1)  NULL DEFAULT NULL COMMENT '申报状态',
  `city_sdpf` decimal(20, 1) NULL DEFAULT NULL COMMENT '市级实地评分（总）',
  `city_sdzt` varchar(1)  NULL DEFAULT NULL COMMENT '市级实地评分状态',
  `province_sdpf` decimal(20, 1) NULL DEFAULT NULL COMMENT '省级实地评分（总）',
  `province_sdzt` varchar(1)  NULL DEFAULT NULL COMMENT '省级实地评分状态',
  `qydj` varchar(1)  NULL DEFAULT NULL COMMENT '企业等级（县、市、省、区）',
  `czbz` char(1)  NOT NULL COMMENT '操作标志',
  `create_by` varchar(50)  NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50)  NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  `region_id` varchar(10)  NOT NULL DEFAULT '-1' COMMENT '行政区域ID',
  `country_id` varchar(10)  NOT NULL DEFAULT '-1' COMMENT '国ID',
  `province_id` varchar(10)  NOT NULL DEFAULT '-1' COMMENT '省ID',
  `city_id` varchar(10)  NOT NULL DEFAULT '-1' COMMENT '市ID',
  `area_id` varchar(10)  NOT NULL DEFAULT '-1' COMMENT '区ID',
  `enterprise_id` varchar(18)  NOT NULL DEFAULT '-1' COMMENT '企业ID',
  `stock_house_id` varchar(21)  NOT NULL DEFAULT '-1' COMMENT '库区ID',
  PRIMARY KEY (`id`, `update_time`) USING BTREE
) ENGINE = InnoDB  COMMENT = '企业申报审核' ;


CREATE TABLE IF NOT EXISTS `graded_enterprise_self_assessment`  (
  `id` varchar(66)  NOT NULL COMMENT '主键ID',
  `qyid` varchar(66)  NOT NULL COMMENT '企业ID',
  `template_id` varchar(66)  NOT NULL COMMENT '等级粮库评定模板ID',
  `scoring_method_id` varchar(66)  NOT NULL COMMENT '模板树叶子节点ID',
  `zpf` decimal(20, 1) NULL DEFAULT NULL COMMENT '自评分',
  `sjzpf` decimal(20, 1) NULL DEFAULT NULL COMMENT '市级自评分',
  `city_sddf` decimal(20, 1) NULL DEFAULT NULL COMMENT '市级实地检查的分',
  `city_kfyy` varchar(1000)  NULL DEFAULT NULL COMMENT '市级扣分原因',
  `province_sddf` decimal(20, 1) NULL DEFAULT NULL COMMENT '省级实地检查的分',
  `province_kfyy` varchar(1000)  NULL DEFAULT NULL COMMENT '省级扣分原因',
  `czbz` char(1)  NOT NULL COMMENT '操作标志',
  `create_by` varchar(50)  NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50)  NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`, `update_time`) USING BTREE
) ENGINE = InnoDB  COMMENT = '企业申报自评表' ;


CREATE TABLE IF NOT EXISTS `graded_enterprise_stock`  (
  `id` varchar(66)  NOT NULL COMMENT '主键ID',
  `qyid` varchar(66)  NOT NULL COMMENT '审核流程企业ID',
  `qymc` varchar(150)  NOT NULL COMMENT '企业名称',
  `kqmc` varchar(150)  NOT NULL COMMENT '库区名称',
  `qydm` varchar(150)  NOT NULL COMMENT '企业代码',
  `kqdm` varchar(150)  NOT NULL COMMENT '库区代码',
  `xzqhdm` varchar(6)  NOT NULL COMMENT '所在区域代码',
  `spdj` varchar(1)  NULL DEFAULT NULL COMMENT '授牌等级(1A-5A)',
  `spnf` varchar(4)  NULL DEFAULT NULL COMMENT '授牌年份',
  `spzt` varchar(1)  NULL DEFAULT NULL COMMENT '授牌状态',
  `lkcr` decimal(20, 3) NULL DEFAULT NULL COMMENT '粮库仓(罐)容(吨)',
  `sbnf` varchar(4)  NULL DEFAULT NULL COMMENT '申报年份',
  `sbrq` date NULL DEFAULT NULL COMMENT '申报或推荐日期',
  `sbdj` varchar(1)  NULL DEFAULT NULL COMMENT '申报等级(1A-5A)',
  `sbzt` varchar(1)  NULL DEFAULT NULL COMMENT '申报状态',
  `qydj` varchar(1)  NULL DEFAULT NULL COMMENT '企业等级（县、市、省、区）',
  `zpf` decimal(20, 1) NULL DEFAULT NULL COMMENT '自评分（总）',
  `city_sdpf` decimal(20, 1) NULL DEFAULT NULL COMMENT '市级实地评分（总）',
  `city_sdzt` varchar(1)  NULL DEFAULT NULL COMMENT '市级实地评分状态',
  `province_sdpf` decimal(20, 1) NULL DEFAULT NULL COMMENT '省级实地评分（总）',
  `province_sdzt` varchar(1)  NULL DEFAULT NULL COMMENT '省级实地评分状态',
  `czbz` char(1)  NOT NULL COMMENT '操作标志',
  `create_by` varchar(50)  NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50)  NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`, `update_time`) USING BTREE
) ENGINE = InnoDB  COMMENT = '企业等级库点' ;


CREATE TABLE IF NOT EXISTS `graded_expert_manage`  (
  `id` varchar(66)  NOT NULL COMMENT '主键ID',
  `expert_name` varchar(66)  NOT NULL COMMENT '姓名',
  `expert_sex` varchar(1)  NULL DEFAULT NULL COMMENT '性别(1男2女)',
  `phone_num` varchar(30)  NULL DEFAULT NULL COMMENT '手机号',
  `expert_sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `expert_title` varchar(70)  NULL DEFAULT NULL COMMENT '职称',
  `sfzh` varchar(70)  NULL DEFAULT NULL COMMENT '身份证号',
  `yhkh` varchar(70)  NULL DEFAULT NULL COMMENT '银行卡号',
  `khzh` varchar(80)  NULL DEFAULT NULL COMMENT '开户行支行名称',
  `gzdw` varchar(80)  NULL DEFAULT NULL COMMENT '工作单位',
  `pdnx` varchar(4)  NULL DEFAULT NULL COMMENT '参与评定年限',
  `czbz` char(1)  NOT NULL COMMENT '操作标志',
  `create_by` varchar(50)  NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50)  NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`, `update_time`) USING BTREE
) ENGINE = InnoDB  COMMENT = '专家管理' ;


CREATE TABLE IF NOT EXISTS `graded_grain_depot_standard`  (
  `id` varchar(66)  NOT NULL COMMENT '主键ID',
  `template_id` varchar(66)  NOT NULL COMMENT '等级粮库评定模板ID',
  `parent_id` varchar(66)  NOT NULL COMMENT '本表上级ID',
  `project_sort` int(11) NULL DEFAULT 0 COMMENT '字段顺序',
  `project_name` varchar(255)  NULL DEFAULT NULL COMMENT '项目名称',
  `project_score` decimal(20, 1) NULL DEFAULT NULL COMMENT '项目分值',
  `assessment_content` varchar(1000)  NULL DEFAULT NULL COMMENT '评定内容',
  `scoring_method` varchar(2000)  NULL DEFAULT NULL COMMENT '计分方法',
  `score` decimal(20, 1) NULL DEFAULT NULL COMMENT '得分',
  `jfzb` varchar(1)  NULL DEFAULT NULL COMMENT '加分指标',
  `bczt` char(1)  NULL DEFAULT NULL COMMENT '保存状态(1:暂存，2:提交)',
  `czbz` char(1)  NOT NULL COMMENT '操作标志',
  `create_by` varchar(50)  NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50)  NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`, `update_time`) USING BTREE
) ENGINE = InnoDB  COMMENT = '等级粮库评定标准' ;


CREATE TABLE IF NOT EXISTS `graded_grain_depot_template`  (
  `id` varchar(66)  NOT NULL COMMENT '主键ID',
  `nf` char(4)  NOT NULL COMMENT '年份',
  `template_name` varchar(100)  NOT NULL COMMENT '模板名称',
  `template_number` varchar(66)  NOT NULL COMMENT '模板编号',
  `template_state` char(2)  NOT NULL COMMENT '状态',
  `qysj` datetime(0) NULL DEFAULT NULL COMMENT '启用时间',
  `file_id` varchar(66)  NULL DEFAULT NULL COMMENT '附件id',
  `file_name` varchar(255)  NULL DEFAULT NULL COMMENT '附件名称',
  `czbz` char(1)  NOT NULL COMMENT '操作标志',
  `create_by` varchar(50)  NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50)  NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`, `update_time`) USING BTREE
) ENGINE = InnoDB  COMMENT = '等级粮库评定模板' ;

