CREATE TABLE IF NOT EXISTS `data_quality_data_issue`
(
    `id`             varchar(20)  NOT NULL COMMENT '主键ID',
    `api_code`       varchar(4)   NOT NULL COMMENT '接口编号',
    `data_id`        varchar(100) NOT NULL COMMENT '数据ID',
    `data_json`      json         NOT NULL COMMENT '数据json',
    `issue_count`    int(6) NOT NULL COMMENT '问题数',
    `region_id`      varchar(10) DEFAULT NULL COMMENT '行政区域ID',
    `country_id`     varchar(10) DEFAULT NULL COMMENT '国ID',
    `province_id`    varchar(10) DEFAULT NULL COMMENT '省ID',
    `city_id`        varchar(10) DEFAULT NULL COMMENT '市ID',
    `area_id`        varchar(10) DEFAULT NULL COMMENT '区ID',
    `enterprise_id`  varchar(18) DEFAULT NULL COMMENT '企业ID',
    `stock_house_id` varchar(21) DEFAULT NULL COMMENT '库区ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `unx_data_id` (api_code,data_id) USING BTREE COMMENT '接口编号/数据ID唯一索引',
    KEY              `idx_region_id` (`region_id`) USING BTREE COMMENT '行政区域ID普通索引',
    KEY              `idx_region_ids` (`country_id`,`province_id`,`city_id`,`area_id`) USING BTREE COMMENT '国/省/市/区组合普通索引',
    KEY              `idx_enterprise` (`enterprise_id`) USING BTREE COMMENT '企业ID普通索引',
    KEY              `idx_stock_house` (`stock_house_id`) USING BTREE COMMENT '库区ID普通索引',
    KEY               `idx_issue_count` (`issue_count`) USING BTREE COMMENT '问题数普通索引'
) ENGINE=myisam COMMENT='数据质量-数据问题';

CREATE TABLE IF NOT EXISTS `data_quality_data_issue_dtl`
(
    `id`            varchar(20)  NOT NULL COMMENT '主键ID',
    `issue_data_id` varchar(20)  NOT NULL COMMENT '数据问题ID',
    `field_name`    varchar(20)  NOT NULL COMMENT '字段名',
    `issue_remark`  varchar(256) NOT NULL COMMENT '问题说明',
    PRIMARY KEY (`id`),
    KEY             `idx_issue_data_id` (issue_data_id) USING BTREE COMMENT '数据问题ID索引'
) ENGINE=myisam COMMENT='数据质量-数据问题明细';

CREATE TABLE IF NOT EXISTS `data_quality_report`
(
    `id`                        varchar(20)    NOT NULL COMMENT '主键ID',
    `statistics_date`           date           NOT NULL COMMENT '统计日期',
    `report_date`               date           NOT NULL COMMENT '报告日期',
    `target_type`               tinyint        NOT NULL COMMENT '报告类型(1库区 2单位 3地区 4地市 5省份)',
    `target_id`                 varchar(30)    NOT NULL COMMENT '报告类型具体的ID',
    `target_name`               varchar(64)    NOT NULL COMMENT '报告类型具体的名称',
    `actual_stock`              decimal(20, 3) NOT NULL COMMENT '实际库存(公斤)',
    `valuation_stock`           decimal(20, 3) NOT NULL COMMENT '计价库存（公斤）',
    `actual_match_rate`         decimal(5, 2)  NOT NULL COMMENT '实际业务相符率(计价库存/实际库存)',
    `correct_match_rate`        decimal(5, 2)  NOT NULL COMMENT '修正业务相符率(实际业务相符率 <= 100 ? 实际业务相符率 : 实际业务相符率 <= 200 ? 200 - 实际业务相符率 : 0)',
    `api_version`               varchar(10)    NOT NULL DEFAULT 'V2022' COMMENT '接口版本',
    `api_total_count`           int            NOT NULL DEFAULT '0' COMMENT '接口总数',
    `api_unicom_count`          int            NOT NULL DEFAULT '0' COMMENT '接口联通数',
    `api_request_total_count`   int            NOT NULL DEFAULT '0' COMMENT '上报次数',
    `api_request_success_count` int            NOT NULL DEFAULT '0' COMMENT '上报成功次数',
    `data_total_count`          int            NOT NULL DEFAULT '0' COMMENT '数据总条数',
    `data_good_count`           int            NOT NULL DEFAULT '0' COMMENT '数据合格条数',
    `data_issue_count`          int            NOT NULL DEFAULT '0' COMMENT '数据问题条数',
    `issue_total_count`         int            NOT NULL DEFAULT '0' COMMENT '问题总数(一条数据可能存在多个问题)',
    `field_total_count`         int            NOT NULL DEFAULT '0' COMMENT '字段总数',
    `field_valid_count`         int            NOT NULL DEFAULT '0' COMMENT '字段上传数(不为空的字段数)',
    `unicom_rate`               decimal(5, 2)  NOT NULL DEFAULT '0.00' COMMENT '开通率(已联通接口个数/所有接口个数)',
    `pass_rate`                 decimal(5, 2)  NOT NULL DEFAULT '0.00' COMMENT '通过率(上报次数/上报成功次数)',
    `full_rate`                 decimal(5, 2)  NOT NULL DEFAULT '0.00' COMMENT '完整率(字段上传数/字段总数)',
    `good_rate`                 decimal(5, 2)  NOT NULL DEFAULT '0.00' COMMENT '合格率(数据合格条数/数据总条数)',
    `score`                     decimal(5, 2)  NOT NULL DEFAULT '0.00' COMMENT '分数(修正业务相符率*(开通率*10%+通过率*10%+完整率*20%+合格率*60%))',
    `region_id`                 varchar(10)             DEFAULT NULL COMMENT '行政区域ID',
    `country_id`                varchar(10)             DEFAULT NULL COMMENT '国ID',
    `province_id`               varchar(10)             DEFAULT NULL COMMENT '省ID',
    `city_id`                   varchar(10)             DEFAULT NULL COMMENT '市ID',
    `area_id`                   varchar(10)             DEFAULT NULL COMMENT '区ID',
    `enterprise_id`             varchar(18)             DEFAULT NULL COMMENT '企业ID',
    `stock_house_id`            varchar(21)             DEFAULT NULL COMMENT '库区ID',
    `stock_house_ids`           varchar(512)            DEFAULT NULL COMMENT '库区IDids,逗号分隔',
    PRIMARY KEY (`id`),
    UNIQUE KEY `unx_key` (`target_type`,`target_id`,`report_date`) USING BTREE COMMENT '报告对象/日期组合唯一索引',
    KEY                         `idx_statistics_date` (`statistics_date`) USING BTREE COMMENT '统计日期普通索引',
    KEY                         `idx_report_date` (`report_date`) USING BTREE COMMENT '报告日期普通索引',
    KEY                         `idx_score` (`score`) USING BTREE COMMENT '分数普通索引',
    KEY                         `idx_region_id` (`region_id`) USING BTREE COMMENT '行政区域ID普通索引',
    KEY                         `idx_region_ids` (`country_id`,`province_id`,`city_id`,`area_id`) USING BTREE COMMENT '国/省/市/区组合普通索引',
    KEY                         `idx_enterprise` (`enterprise_id`) USING BTREE COMMENT '企业ID普通索引',
    KEY                         `idx_stock_house` (`stock_house_id`) USING BTREE COMMENT '库区ID普通索引'
) ENGINE=MyISAM  COMMENT='数据质量-报告';

CREATE TABLE IF NOT EXISTS `data_quality_report_dtl`
(
    `id`                        varchar(20)   NOT NULL COMMENT '主键ID',
    `report_id`                 varchar(20)   NOT NULL COMMENT '数据质量报告ID',
    `api_code`                  varchar(4)    NOT NULL COMMENT '接口编号',
    `unicom`                    tinyint(1) NOT NULL COMMENT '联通情况:1是 0否',
    `api_request_total_count`   int           NOT NULL DEFAULT '0' COMMENT '上报次数',
    `api_request_success_count` int           NOT NULL DEFAULT '0' COMMENT '上报成功次数',
    `data_total_count`          int           NOT NULL DEFAULT '0' COMMENT '数据总条数',
    `data_good_count`           int           NOT NULL DEFAULT '0' COMMENT '数据合格条数',
    `data_issue_count`          int           NOT NULL DEFAULT '0' COMMENT '数据问题条数',
    `issue_total_count`         int           NOT NULL DEFAULT '0' COMMENT '问题总数(一条数据可能存在多个问题)',
    `field_total_count`         int           NOT NULL DEFAULT '0' COMMENT '字段总数',
    `field_valid_count`         int           NOT NULL DEFAULT '0' COMMENT '字段上传数(不为空的字段数)',
    `pass_rate`                 decimal(5, 2) NOT NULL DEFAULT '0.00' COMMENT '通过率(上报次数/上报成功次数)',
    `full_rate`                 decimal(5, 2) NOT NULL DEFAULT '0.00' COMMENT '完整率(字段上传数/字段总数)',
    `good_rate`                 decimal(5, 2) NOT NULL DEFAULT '0.00' COMMENT '合格率(数据合格条数/数据总条数)',
    PRIMARY KEY (`id`),
    UNIQUE KEY `unx_report_id` (`report_id`,`api_code`) USING BTREE COMMENT '数据质量报告ID/接口编号组合唯一索引'
) ENGINE=MyISAM  COMMENT='数据质量-报告明细';




-- 初始化菜单
INSERT ignore INTO `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1651146680302702593', '0', 'CL', '数据质量', '/dataQuality', NULL, 'hdd-outlined', '10', NULL, '超级管理员', '2023-04-26 16:50:20', '超级管理员', '2023-04-26 16:50:20');
INSERT ignore INTO `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1651146977007767553', '1651146680302702593', 'CL', '质量报告管理', '/qualityReport', NULL, 'partition-outlined', '1', NULL, '超级管理员', '2023-04-26 16:51:31', '超级管理员', '2023-04-26 16:53:08');
INSERT ignore INTO `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1651147101998026754', '1651146680302702593', 'CL', '问题数据管理', '/problemData', NULL, 'file-unknown-outlined', '2', NULL, '超级管理员', '2023-04-26 16:52:01', '超级管理员', '2023-04-26 16:53:22');
INSERT ignore INTO `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1651147623656198146', '1651146977007767553', 'MU', '库区质量报告', '/warehouseQuality', 'warehouseQuality', 'bars-outlined', '1', NULL, '超级管理员', '2023-04-26 16:54:05', '超级管理员', '2023-04-26 16:54:05');
INSERT ignore INTO `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1651147687267012610', '1651147101998026754', 'MU', '库区数据问题', '/warehouseProblem', 'warehouseProblem', 'bars-outlined', '1', NULL, '超级管理员', '2023-04-26 16:54:20', '超级管理员', '2023-04-26 16:54:20');
INSERT ignore INTO `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1655502823506448386', '1651146977007767553', 'MU', '区域质量报告', '/regionQuality', 'regionQuality', 'bank-outlined', '2', '', '超级管理员', '2023-05-08 17:20:06', '超级管理员', '2023-05-08 19:04:08');

INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1651146685104472064', '1', '1651146680302702593');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1651146977166442496', '1', '1651146977007767553');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1651147102072815616', '1', '1651147101998026754');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1651147623777124352', '1', '1651147623656198146');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1651147687350190080', '1', '1651147687267012610');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1651147687350190080', '1', '1655502823506448386');
