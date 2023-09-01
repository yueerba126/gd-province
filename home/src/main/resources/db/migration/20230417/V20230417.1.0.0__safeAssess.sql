CREATE TABLE IF NOT EXISTS `safe_assess_template`
(
    `id`                  varchar(20)  NOT NULL COMMENT '主键ID',
    `number`              varchar(18)  NOT NULL COMMENT '模板编号',
    `name`                varchar(30)  NOT NULL COMMENT '模板名称',
    `year`                varchar(4)   NOT NULL COMMENT '年份',
    `region_ids`          varchar(512) NOT NULL COMMENT '行政区域IDS',
    `assess_scoring_rule` varchar(60)  DEFAULT NULL COMMENT '考核计分规则',
    `check_scoring_rule`  varchar(60)  DEFAULT NULL COMMENT '抽查计分规则',
    `state`               varchar(4)   NOT NULL COMMENT '状态：保存、待分配、待发布、已发布、已完成',
    `allot_date`          date         DEFAULT NULL COMMENT '分配日期',
    `auto_allot_dept_ids` varchar(512) DEFAULT NULL COMMENT '自动分配部门IDS',
    `push_date`           date         DEFAULT NULL COMMENT '发布日期',
    `last_submit_date`    date         DEFAULT NULL COMMENT '最晚提交日期',
    `dept_total_count`    int(11) NOT NULL DEFAULT 0 COMMENT '部门总数量',
    `dept_allot_count`    int(11) NOT NULL DEFAULT 0 COMMENT '部门已完成分配数量',
    `dept_assess_count`   int(11) NOT NULL DEFAULT 0 COMMENT '部门已完成考核数量',
    `region_total_count`  int(11) NOT NULL DEFAULT 0 COMMENT '地市总数量',
    `region_submit_count` int(11) NOT NULL DEFAULT 0 COMMENT '地市已提交数量',
    `region_assess_count` int(11) NOT NULL DEFAULT 0 COMMENT '地市已考核数量',
    `create_by`           varchar(50)  DEFAULT NULL COMMENT '创建者',
    `create_time`         datetime     DEFAULT NULL COMMENT '创建时间',
    `update_by`           varchar(50)  DEFAULT NULL COMMENT '更新者',
    `update_time`         datetime     NOT NULL COMMENT '修改时间',
    `region_id`           varchar(10)  DEFAULT NULL COMMENT '行政区域ID',
    `country_id`          varchar(10)  DEFAULT NULL COMMENT '国ID',
    `province_id`         varchar(10)  DEFAULT NULL COMMENT '省ID',
    `city_id`             varchar(10)  DEFAULT NULL COMMENT '市ID',
    `area_id`             varchar(10)  DEFAULT NULL COMMENT '区ID',
    `organize_id`         varchar(20)  DEFAULT NULL COMMENT '组织ID',
    PRIMARY KEY (`id`, `update_time`),
    UNIQUE KEY `unx_number` (`number`) USING BTREE COMMENT '模板编号唯一索引',
    KEY                   `idx_name` (`name`) USING BTREE COMMENT '模板名称索引',
    KEY                   `idx_state` (`state`) USING BTREE COMMENT '状态索引',
    KEY                   `idx_allot_date` (`allot_date`) USING BTREE COMMENT '分配日期索引',
    KEY                   `idx_push_date` (`push_date`) USING BTREE COMMENT '发布日期索引',
    KEY                   `idx_region_id` (`region_id`) USING BTREE COMMENT '行政区域ID普通索引',
    KEY                   `idx_region_ids` (`country_id`,`province_id`,`city_id`,`area_id`) USING BTREE COMMENT '国/省/市/区组合普通索引',
    KEY                   `idx_organize_id` (`organize_id`) USING BTREE COMMENT '组织ID索引',
    KEY                   `idx_update_time` (`update_time`) USING BTREE COMMENT '最后更新时间普通索引'
) ENGINE=InnoDB COMMENT='粮食安全考核-考核模板表';

CREATE TABLE IF NOT EXISTS `safe_assess_template_index`
(
    `id`                 varchar(20)  NOT NULL COMMENT '主键ID',
    `template_id`        varchar(20)  NOT NULL COMMENT '考核模板ID',
    `parent_id`          varchar(20)  NOT NULL DEFAULT '0' COMMENT '父ID',
    `name`               varchar(256) NOT NULL COMMENT '指标名称',
    `topic`              TINYINT(1) NOT NULL COMMENT '是否是题目 1是 0否',
    `score`              decimal(20, 2)        DEFAULT NULL COMMENT '分数',
    `standard`           varchar(400)          DEFAULT NULL COMMENT '评分标准',
    `illustrate`         varchar(400)          DEFAULT NULL COMMENT '评分说明',
    `lead_dept_id`       bigint                DEFAULT NULL COMMENT '牵头部门ID',
    `cooperate_dept_ids` varchar(128)          DEFAULT NULL COMMENT '配合部门IDS',
    `lack_region_ids`    varchar(512)          DEFAULT NULL COMMENT '缺项行政区域IDS',
    PRIMARY KEY (`id`),
    KEY                  `idx_template_id` (`template_id`) USING BTREE COMMENT '考核模板ID索引'
) ENGINE=InnoDB COMMENT='粮食安全考核-考核模板指标表';

CREATE TABLE IF NOT EXISTS `safe_assess_review`
(
    `id`                     varchar(20)    NOT NULL COMMENT '主键ID',
    `allot_date`             date           NOT NULL COMMENT '分配日期',
    `auto_allot`             TINYINT(1) NOT NULL COMMENT '自动分配：1是 0否',
    `template_id`            varchar(20)    NOT NULL COMMENT '考核模板ID',
    `template_name`          varchar(30)    NOT NULL COMMENT '考核模板名称',
    `template_year`          varchar(4)     NOT NULL COMMENT '考核模板年份',
    `assess_region_id`       varchar(10)    NOT NULL COMMENT '考核区域ID',
    `org_assess_id`          varchar(20)             DEFAULT NULL COMMENT '单位考核ID',
    `dept_id`                bigint(20) NOT NULL COMMENT '部门ID',
    `state`                  varchar(5)     NOT NULL DEFAULT '待分配' COMMENT '状态：待分配、待发布、待提交、待评分、待考核、已考核',
    `lead_total_count`       int(11) NOT NULL DEFAULT 0 COMMENT '牵头指标总数',
    `lead_allot_count`       int(11) NOT NULL DEFAULT 0 COMMENT '牵头指标分配数',
    `lead_review_count`      int(11) NOT NULL DEFAULT 0 COMMENT '牵头指标考核数',
    `lead_total_score`       decimal(20, 2) NOT NULL DEFAULT 0 COMMENT '牵头指标总分',
    `lead_review_score`      decimal(20, 2) NOT NULL DEFAULT 0 COMMENT '牵头指标考核总分',
    `cooperate_total_count`  int(11) NOT NULL DEFAULT 0 COMMENT '配合指标总数',
    `cooperate_allot_count`  int(11) NOT NULL DEFAULT 0 COMMENT '配合指标分配数',
    `cooperate_review_count` int(11) NOT NULL DEFAULT 0 COMMENT '配合指标考核数',
    `cooperate_total_score`  decimal(20, 2) NOT NULL DEFAULT 0 COMMENT '配合指标总分',
    `cooperate_review_score` decimal(20, 2) NOT NULL DEFAULT 0 COMMENT '配合指标考核总分',
    `dept_total_count`       int(11) NOT NULL DEFAULT 0 COMMENT '部门总数',
    `dept_submit_count`      int(11) NOT NULL DEFAULT 0 COMMENT '部门提交数',
    `create_by`              varchar(50)             DEFAULT NULL COMMENT '创建者',
    `create_time`            datetime                DEFAULT NULL COMMENT '创建时间',
    `update_by`              varchar(50)             DEFAULT NULL COMMENT '更新者',
    `update_time`            datetime       NOT NULL COMMENT '修改时间',
    `region_id`              varchar(10)             DEFAULT NULL COMMENT '行政区域ID',
    `country_id`             varchar(10)             DEFAULT NULL COMMENT '国ID',
    `province_id`            varchar(10)             DEFAULT NULL COMMENT '省ID',
    `city_id`                varchar(10)             DEFAULT NULL COMMENT '市ID',
    `area_id`                varchar(10)             DEFAULT NULL COMMENT '区ID',
    `organize_id`            varchar(20)             DEFAULT NULL COMMENT '组织ID',
    PRIMARY KEY (`id`, update_time),
    UNIQUE KEY `unx_key` (template_id,dept_id,assess_region_id) USING BTREE COMMENT '考核模板ID/部门ID/考核区域ID组合唯一索引',
    KEY                      `idx_allot_date` (allot_date) USING BTREE COMMENT '分配日期索引',
    KEY                      `idx_auto_allot` (auto_allot) USING BTREE COMMENT '自动分配索引',
    KEY                      `idx_template_id` (template_id) USING BTREE COMMENT '考核模板ID索引',
    KEY                      `idx_template_name` (template_name) USING BTREE COMMENT '考核模板名称索引',
    KEY                      `idx_template_year` (template_year) USING BTREE COMMENT '考核模板年份索引',
    KEY                      `idx_assess_region_id` (assess_region_id) USING BTREE COMMENT '考核区域ID索引',
    KEY                      `idx_org_assess_id` (org_assess_id) USING BTREE COMMENT '单位考核ID索引',
    KEY                      `idx_dept_id` (`dept_id`) USING BTREE COMMENT '厅级部门ID索引',
    KEY                      `idx_state` (`state`) USING BTREE COMMENT '状态索引',
    KEY                      `idx_region_id` (`region_id`) USING BTREE COMMENT '行政区域ID普通索引',
    KEY                      `idx_region_ids` (`country_id`,`province_id`,`city_id`,`area_id`) USING BTREE COMMENT '国/省/市/区组合普通索引',
    KEY                      `idx_organize_id` (`organize_id`) USING BTREE COMMENT '组织ID索引',
    KEY                      `idx_update_time` (`update_time`) USING BTREE COMMENT '最后更新时间普通索引'
) ENGINE=InnoDB COMMENT='粮食安全考核-考核评审表';

CREATE TABLE IF NOT EXISTS `safe_assess_review_index`
(
    `id`                       varchar(20)  NOT NULL COMMENT '主键ID',
    `review_id`                varchar(20)  NOT NULL COMMENT '考核评审ID',
    `template_index_id`        varchar(20)  NOT NULL COMMENT '考核模板指标ID',
    `template_index_parent_id` varchar(20)  NOT NULL DEFAULT '0' COMMENT '考核模板指标父ID',
    `name`                     varchar(256) NOT NULL COMMENT '指标名称',
    `topic`                    TINYINT(1) NOT NULL COMMENT '是否是题目 1是 0否',
    `score`                    decimal(20, 2)        DEFAULT NULL COMMENT '分数',
    `standard`                 varchar(400)          DEFAULT NULL COMMENT '评分标准',
    `illustrate`               varchar(400)          DEFAULT NULL COMMENT '评分说明',
    `lead_dept_id`             bigint(20) DEFAULT NULL COMMENT '牵头部门ID',
    `cooperate_dept_ids`       varchar(128)          DEFAULT NULL COMMENT '配合部门IDS',
    `enable`                   TINYINT(1) DEFAULT 0 COMMENT '缺项: 1启用 0禁用',
    `type`                     TINYINT(1) DEFAULT NULL COMMENT '指标类型：1牵头 0配合',
    `allot_lead_dept_id`       bigint(20) DEFAULT NULL COMMENT '分配牵头部门ID',
    `allot_cooperate_dept_ids` varchar(128)          DEFAULT NULL COMMENT '分配配合部门IDS',
    `org_assess_index_id`      varchar(20)           DEFAULT NULL COMMENT '单位考核指标ID',
    `assess_score`             decimal(20, 2)        DEFAULT NULL COMMENT '考核分数',
    `assess_minus_cause`       varchar(256)          DEFAULT NULL COMMENT '考核减分原因',
    PRIMARY KEY (`id`),
    UNIQUE `unx_review_id_template_index_id` (`review_id`,template_index_id) USING BTREE COMMENT '考核评审ID/考核模板指标ID组合唯一索引',
    KEY                        `idx_template_index_id` (`template_index_id`) USING BTREE COMMENT '考核模板指标ID索引'
) ENGINE=InnoDB COMMENT='粮食安全考核-考核评审指标表';


CREATE TABLE IF NOT EXISTS `safe_assess_score`
(
    `id`                     varchar(20)    NOT NULL COMMENT '主键ID',
    `template_id`            varchar(20)    NOT NULL COMMENT '考核模板ID',
    `template_name`          varchar(30)    NOT NULL COMMENT '考核模板名称',
    `template_year`          varchar(4)     NOT NULL COMMENT '考核模板年份',
    `review_id`              varchar(20)    NOT NULL COMMENT '考核评审ID',
    `dept_id`                bigint(20) NOT NULL COMMENT '部门ID',
    `assess_region_id`       varchar(10)    NOT NULL COMMENT '考核区域ID',
    `org_assess_id`          varchar(20)             DEFAULT NULL COMMENT '单位考核ID',
    `state`                  varchar(5)     NOT NULL DEFAULT '待发布' COMMENT '状态：待发布、待提交、待评分、已评分',
    `lead_total_count`       int(11) NOT NULL DEFAULT 0 COMMENT '牵头指标总数',
    `lead_assess_count`      int(11) NOT NULL DEFAULT 0 COMMENT '牵头指标考核数',
    `lead_total_score`       decimal(20, 2) NOT NULL DEFAULT 0 COMMENT '牵头指标总分',
    `lead_assess_score`      decimal(20, 2) NOT NULL DEFAULT 0 COMMENT '牵头指标考核总分',
    `cooperate_total_count`  int(11) NOT NULL DEFAULT 0 COMMENT '配合指标总数',
    `cooperate_assess_count` int(11) NOT NULL DEFAULT 0 COMMENT '配合指标考核数',
    `cooperate_total_score`  decimal(20, 2) NOT NULL DEFAULT 0 COMMENT '配合指标总分',
    `cooperate_assess_score` decimal(20, 2) NOT NULL DEFAULT 0 COMMENT '配合指标考核总分',
    `create_by`              varchar(50)             DEFAULT NULL COMMENT '创建者',
    `create_time`            datetime                DEFAULT NULL COMMENT '创建时间',
    `update_by`              varchar(50)             DEFAULT NULL COMMENT '更新者',
    `update_time`            datetime       NOT NULL COMMENT '修改时间',
    `region_id`              varchar(10)             DEFAULT NULL COMMENT '行政区域ID',
    `country_id`             varchar(10)             DEFAULT NULL COMMENT '国ID',
    `province_id`            varchar(10)             DEFAULT NULL COMMENT '省ID',
    `city_id`                varchar(10)             DEFAULT NULL COMMENT '市ID',
    `area_id`                varchar(10)             DEFAULT NULL COMMENT '区ID',
    `organize_id`            varchar(20)             DEFAULT NULL COMMENT '组织ID',
    PRIMARY KEY (`id`, update_time),
    UNIQUE KEY `unx_template` (template_id,dept_id,assess_region_id) USING BTREE COMMENT '考核模板ID/部门ID/考核区域ID组合唯一索引',
    UNIQUE KEY `unx_review` (review_id,dept_id) USING BTREE COMMENT '考核评审ID/部门ID组合唯一索引',
    KEY                      `idx_template_id` (`template_id`) USING BTREE COMMENT '考核模板ID索引',
    KEY                      `idx_template_name` (template_name) USING BTREE COMMENT '考核模板名称索引',
    KEY                      `idx_template_year` (template_year) USING BTREE COMMENT '考核模板年份索引',
    KEY                      `idx_review_id` (review_id) USING BTREE COMMENT '考核评审ID索引',
    KEY                      `idx_dept_id` (dept_id) USING BTREE COMMENT '部门ID索引',
    KEY                      `idx_assess_region_id` (assess_region_id) USING BTREE COMMENT '考核区域ID索引',
    KEY                      `idx_region_id` (`region_id`) USING BTREE COMMENT '行政区域ID普通索引',
    KEY                      `idx_region_ids` (`country_id`,`province_id`,`city_id`,`area_id`) USING BTREE COMMENT '国/省/市/区组合普通索引',
    KEY                      `idx_organize_id` (`organize_id`) USING BTREE COMMENT '组织ID索引',
    KEY                      `idx_update_time` (`update_time`) USING BTREE COMMENT '最后更新时间普通索引'
) ENGINE=InnoDB COMMENT='粮食安全考核-考核评分表';


CREATE TABLE IF NOT EXISTS `safe_assess_score_index`
(
    `id`                       varchar(20)  NOT NULL COMMENT '主键ID',
    `score_id`                 varchar(20)  NOT NULL COMMENT '考核评分ID',
    `template_index_id`        varchar(20)  NOT NULL COMMENT '考核模板指标ID',
    `template_index_parent_id` varchar(20)  NOT NULL DEFAULT '0' COMMENT '考核模板指标父ID',
    `name`                     varchar(256) NOT NULL COMMENT '指标名称',
    `topic`                    TINYINT(1) NOT NULL COMMENT '是否是题目 1是 0否',
    `score`                    decimal(20, 2)        DEFAULT NULL COMMENT '分数',
    `standard`                 varchar(400)          DEFAULT NULL COMMENT '评分标准',
    `illustrate`               varchar(400)          DEFAULT NULL COMMENT '评分说明',
    `lead_dept_id`             bigint(20) DEFAULT NULL COMMENT '牵头部门ID',
    `cooperate_dept_ids`       varchar(128)          DEFAULT NULL COMMENT '配合部门IDS',
    `enable`                   TINYINT(1) DEFAULT 0 COMMENT '缺项: 1启用 0禁用',
    `type`                     TINYINT(1) DEFAULT NULL COMMENT '指标类型：1牵头 0配合',
    `review_index_id`          varchar(20)  NOT NULL COMMENT '考核评审指标ID',
    `allot_lead_dept_id`       bigint(20) DEFAULT NULL COMMENT '分配牵头部门ID',
    `allot_cooperate_dept_ids` varchar(128)          DEFAULT NULL COMMENT '分配配合部门IDS',
    `allot_type`               TINYINT(1) DEFAULT NULL COMMENT '分配指标类型：1牵头 0配合',
    `assess_score`             decimal(20, 2)        DEFAULT NULL COMMENT '考核分数',
    `assess_minus_cause`       varchar(256)          DEFAULT NULL COMMENT '减分原因',
    PRIMARY KEY (`id`),
    UNIQUE `unx_score_id_template_index_id` (`score_id`,template_index_id) USING BTREE COMMENT '考核评分ID/考核模板指标ID组合唯一索引'
) ENGINE=InnoDB COMMENT='粮食安全考核-考核评分指标表';


CREATE TABLE IF NOT EXISTS `safe_assess_org_assess`
(
    `id`                         varchar(20)    NOT NULL COMMENT '主键ID',
    `template_id`                varchar(20)    NOT NULL COMMENT '考核模板ID',
    `template_number`            varchar(18)    NOT NULL COMMENT '考核模板编号',
    `template_name`              varchar(30)    NOT NULL COMMENT '考核模板名称',
    `year`                       varchar(4)     NOT NULL COMMENT '年份',
    `push_date`                  date                    DEFAULT NULL COMMENT '发布日期',
    `last_submit_date`           date                    DEFAULT NULL COMMENT '最晚提交日期',
    `state`                      varchar(4)     NOT NULL COMMENT '状态：待分配、待自评、已自评、待考核、已考核',
    `distribution_count`         int(11) NOT NULL DEFAULT 0 COMMENT '分配指标数',
    `org_count`                  int(11) NOT NULL DEFAULT 0 COMMENT '自评指标数',
    `org_await_count`            int(11) NOT NULL DEFAULT 0 COMMENT '待自评指标数',
    `check_await_count`          int(11) NOT NULL DEFAULT 0 COMMENT '待抽查指标数',
    `dept_assess_count`          int(11) NOT NULL DEFAULT 0 COMMENT '考核模板部门数',
    `dept_assess_submit_count`   int(11) NOT NULL DEFAULT 0 COMMENT '考核模板部门提交数',
    `dept_lead_count`            int(11) NOT NULL DEFAULT 0 COMMENT '考核模板牵头部门指标数',
    `dept_lead_await_count`      int(11) NOT NULL DEFAULT 0 COMMENT '考核模板牵头部门待考核指标数',
    `dept_cooperate_count`       int(11) NOT NULL DEFAULT 0 COMMENT '考核模板配合部门指标数',
    `dept_cooperate_await_count` int(11) NOT NULL DEFAULT 0 COMMENT '考核模板配合部门待考核指标数',
    `assess_dept_count`          int(11) NOT NULL DEFAULT 0 COMMENT '部门考核数',
    `assess_dept_submit_count`   int(11) NOT NULL DEFAULT 0 COMMENT '部门考核提交数',
    `lead_count`                 int(11) NOT NULL DEFAULT 0 COMMENT '部门考核牵头部门自评指标数',
    `lead_await_count`           int(11) NOT NULL DEFAULT 0 COMMENT '部门考核牵头部门待自评指标数',
    `cooperate_count`            int(11) NOT NULL DEFAULT 0 COMMENT '部门考核配合部门自评指标数',
    `cooperate_await_count`      int(11) NOT NULL DEFAULT 0 COMMENT '部门考核配合部门待自评指标数',
    `submit_overtime`            TINYINT(1) DEFAULT NULL COMMENT '提交考核是否超时: 1超时 0不超时',
    `submit_time`                datetime                DEFAULT NULL COMMENT '提交考核时间',
    `template_score`             decimal(20, 2) NOT NULL DEFAULT 0 COMMENT '模板总分',
    `org_score`                  decimal(20, 2) NOT NULL DEFAULT 0 COMMENT '自评总分',
    `assess_score`               decimal(20, 2) NOT NULL DEFAULT 0 COMMENT '考核总分',
    `check_score`                decimal(20, 2) NOT NULL DEFAULT 0 COMMENT '抽查总分',
    `score`                      decimal(20, 2) NOT NULL DEFAULT 0 COMMENT '最终得分',
    `file_ids`                   varchar(128)            DEFAULT NULL COMMENT '文件IDS',
    `file_names`                 varchar(1024)           DEFAULT NULL COMMENT '文件名称S',
    `check_file_ids`             varchar(128)            DEFAULT NULL COMMENT '抽查文件IDS',
    `check_file_names`           varchar(1024)           DEFAULT NULL COMMENT '抽查文件名称S',
    `cooperate_dept_end_date`    date                    DEFAULT NULL COMMENT '配合部门填报截止日期',
    `create_by`                  varchar(50)    NOT NULL COMMENT '创建者',
    `create_time`                datetime       NOT NULL COMMENT '创建时间',
    `update_by`                  varchar(50)    NOT NULL COMMENT '更新者',
    `update_time`                datetime       NOT NULL COMMENT '修改时间',
    `region_id`                  varchar(10)    NOT NULL COMMENT '行政区域ID',
    `country_id`                 varchar(10)             DEFAULT NULL COMMENT '国ID',
    `province_id`                varchar(10)             DEFAULT NULL COMMENT '省ID',
    `city_id`                    varchar(10)             DEFAULT NULL COMMENT '市ID',
    `area_id`                    varchar(10)             DEFAULT NULL COMMENT '区ID',
    `organize_id`                varchar(20)             DEFAULT NULL COMMENT '组织ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `unx_template_id` (`template_id`,region_id) USING BTREE COMMENT '考核模板ID/行政区域ID组合唯一索引',
    KEY                          `idx_template_number` (`template_number`) USING BTREE COMMENT '考核模板编号索引',
    KEY                          `idx_template_name` (`template_name`) USING BTREE COMMENT '考核模板名称索引',
    KEY                          `idx_push_date` (`push_date`) USING BTREE COMMENT '发布日期索引',
    KEY                          `idx_last_submit_date` (`last_submit_date`) USING BTREE COMMENT '最晚提交日期索引',
    KEY                          `idx_state` (`state`) USING BTREE COMMENT '状态索引',
    KEY                          `idx_region_id` (`region_id`) USING BTREE COMMENT '行政区域ID普通索引',
    KEY                          `idx_region_ids` (`country_id`,`province_id`,`city_id`,`area_id`) USING BTREE COMMENT '国/省/市/区组合普通索引',
    KEY                          `idx_organize_id` (`organize_id`) USING BTREE COMMENT '组织ID索引',
    KEY                          `idx_update_time` (`update_time`) USING BTREE COMMENT '最后更新时间普通索引'
) ENGINE=InnoDB COMMENT='粮食安全考核-单位考核表';

CREATE TABLE IF NOT EXISTS `safe_assess_org_assess_index`
(
    `id`                          varchar(20)  NOT NULL COMMENT '主键ID',
    `org_assess_id`               varchar(20)  NOT NULL COMMENT '单位考核ID',
    `template_index_id`           varchar(20)  NOT NULL COMMENT '考核模板指标ID',
    `template_index_parent_id`    varchar(20)  NOT NULL DEFAULT '0' COMMENT '考核模板指标父ID',
    `name`                        varchar(256) NOT NULL COMMENT '指标名称',
    `topic`                       TINYINT(1) NOT NULL COMMENT '是否是题目 1是 0否',
    `score`                       decimal(20, 2)        DEFAULT NULL COMMENT '分数',
    `standard`                    varchar(400)          DEFAULT NULL COMMENT '评分标准',
    `illustrate`                  varchar(400)          DEFAULT NULL COMMENT '评分说明',
    `enable`                      TINYINT(1) DEFAULT 0 COMMENT '缺项: 1启用 0禁用',
    `template_lead_dept_id`       bigint(20) DEFAULT NULL COMMENT '考核模板牵头部门ID',
    `template_cooperate_dept_ids` varchar(128)          DEFAULT NULL COMMENT '考核模板配合部门IDS',
    `allot_lead_dept_id`          bigint(20) DEFAULT NULL COMMENT '考核评分分配牵头部门ID',
    `allot_cooperate_dept_ids`    varchar(256)          DEFAULT NULL COMMENT '考核评分分配配合部门IDS',
    `lead_dept_id`                bigint(20) DEFAULT NULL COMMENT '牵头部门ID',
    `cooperate_dept_ids`          varchar(128)          DEFAULT NULL COMMENT '配合部门IDS',
    `org_score`                   decimal(20, 2)        DEFAULT NULL COMMENT '自评分数',
    `org_illustrate`              varchar(256)          DEFAULT NULL COMMENT '自评说明',
    `minus_cause`                 varchar(256)          DEFAULT NULL COMMENT '减分原因',
    `measure`                     varchar(256)          DEFAULT NULL COMMENT '整改措施',
    `file_ids`                    varchar(128)          DEFAULT NULL COMMENT '佐证材料文件IDS',
    `file_names`                  varchar(1024)         DEFAULT NULL COMMENT '佐证材料文件名称S',
    `assess_score`                decimal(20, 2)        DEFAULT NULL COMMENT '考核分数',
    `assess_minus_cause`          varchar(256)          DEFAULT NULL COMMENT '考核减分原因',
    `check_score`                 decimal(20, 2)        DEFAULT NULL COMMENT '抽查分数',
    `check_remark`                varchar(256)          DEFAULT NULL COMMENT '抽查说明',
    `check_file_ids`              varchar(128)          DEFAULT NULL COMMENT '抽查文件IDS',
    `check_file_names`            varchar(1024)         DEFAULT NULL COMMENT '抽查文件名称S',
    PRIMARY KEY (`id`),
    KEY                           `idx_org_assess_id` (`org_assess_id`) USING BTREE COMMENT '单位考核ID普通索引'
) ENGINE=InnoDB COMMENT='粮食安全考核-单位考核指标表';

CREATE TABLE IF NOT EXISTS `safe_assess_org_assess_dept`
(
    `id`                      varchar(20)    NOT NULL COMMENT '主键ID',
    `org_assess_id`           varchar(20)    NOT NULL COMMENT '单位考核ID',
    `template_id`             varchar(20)    NOT NULL COMMENT '考核模板ID',
    `template_number`         varchar(18)    NOT NULL COMMENT '考核模板编号',
    `template_name`           varchar(30)    NOT NULL COMMENT '考核模板名称',
    `year`                    varchar(4)     NOT NULL COMMENT '年份',
    `last_submit_date`        date                    DEFAULT NULL COMMENT '最晚提交日期',
    `cooperate_dept_end_date` date           NOT NULL COMMENT '配合部门填报截止日期',
    `submit_overtime`         TINYINT(1) DEFAULT NULL COMMENT '提交自评是否超时: 1超时 0不超时',
    `submit_time`             datetime                DEFAULT NULL COMMENT '提交自评时间',
    `dept_id`                 bigint(20) NOT NULL COMMENT '部门ID',
    `state`                   varchar(4)     NOT NULL COMMENT '状态：待分配、已分配、待自评、已自评、已退回',
    `lead_count`              int(11) NOT NULL DEFAULT 0 COMMENT '牵头指标数',
    `lead_await_count`        int(11) NOT NULL DEFAULT 0 COMMENT '待自评牵头指标数',
    `lead_score`              decimal(20, 2) NOT NULL DEFAULT 0 COMMENT '牵头指标总分',
    `lead_already_score`      decimal(20, 2) NOT NULL DEFAULT 0 COMMENT '已自评牵头指标总分',
    `cooperate_count`         int(11) NOT NULL DEFAULT 0 COMMENT '配合指标数',
    `cooperate_await_count`   int(11) NOT NULL DEFAULT 0 COMMENT '待自评配合指标数',
    `cooperate_score`         decimal(20, 2) NOT NULL DEFAULT 0 COMMENT '配合指标总分',
    `cooperate_already_score` decimal(20, 2) NOT NULL DEFAULT 0 COMMENT '已自评配合指标总分',
    `dept_count`              int(11) NOT NULL DEFAULT 0 COMMENT '自评部门数',
    `dept_submit_count`       int(11) NOT NULL DEFAULT 0 COMMENT '提交自评部门数',
    `create_by`               varchar(50)    NOT NULL COMMENT '创建者',
    `create_time`             datetime       NOT NULL COMMENT '创建时间',
    `update_by`               varchar(50)    NOT NULL COMMENT '更新者',
    `update_time`             datetime       NOT NULL COMMENT '修改时间',
    `region_id`               varchar(10)    NOT NULL COMMENT '行政区域ID',
    `country_id`              varchar(10)             DEFAULT NULL COMMENT '国ID',
    `province_id`             varchar(10)             DEFAULT NULL COMMENT '省ID',
    `city_id`                 varchar(10)             DEFAULT NULL COMMENT '市ID',
    `area_id`                 varchar(10)             DEFAULT NULL COMMENT '区ID',
    `organize_id`             varchar(20)             DEFAULT NULL COMMENT '组织ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `unx_org_assess_id` (org_assess_id,dept_id) USING BTREE COMMENT '单位考核ID/部门ID组合唯一索引',
    KEY                       `idx_dept_id` (`dept_id`) USING BTREE COMMENT '部门ID索引',
    KEY                       `idx_template_number` (`template_number`) USING BTREE COMMENT '考核模板编号索引',
    KEY                       `idx_template_name` (`template_name`) USING BTREE COMMENT '考核模板名称索引',
    KEY                       `idx_cooperate_dept_end_date` (`cooperate_dept_end_date`) USING BTREE COMMENT '配合部门填报截止日期索引',
    KEY                       `idx_state` (`state`) USING BTREE COMMENT '状态索引',
    KEY                       `idx_region_id` (`region_id`) USING BTREE COMMENT '行政区域ID普通索引',
    KEY                       `idx_region_ids` (`country_id`,`province_id`,`city_id`,`area_id`) USING BTREE COMMENT '国/省/市/区组合普通索引',
    KEY                       `idx_organize_id` (`organize_id`) USING BTREE COMMENT '组织ID索引',
    KEY                       `idx_update_time` (`update_time`) USING BTREE COMMENT '最后更新时间普通索引'
) ENGINE=InnoDB COMMENT='粮食安全考核-部门考核表';


CREATE TABLE IF NOT EXISTS `safe_assess_org_assess_dept_index`
(
    `id`                       varchar(20)  NOT NULL COMMENT '主键ID',
    `org_assess_dept_id`       varchar(20)  NOT NULL COMMENT '部门考核ID',
    `org_assess_index_id`      varchar(20)  NOT NULL COMMENT '单位考核指标ID',
    `template_index_id`        varchar(20)  NOT NULL COMMENT '考核模板指标ID',
    `template_index_parent_id` varchar(20)  NOT NULL DEFAULT '0' COMMENT '考核模板指标父ID',
    `name`                     varchar(256) NOT NULL COMMENT '指标名称',
    `topic`                    TINYINT(1) NOT NULL COMMENT '是否是题目 1是 0否',
    `score`                    decimal(20, 2)        DEFAULT NULL COMMENT '分数',
    `standard`                 varchar(400)          DEFAULT NULL COMMENT '评分标准',
    `illustrate`               varchar(400)          DEFAULT NULL COMMENT '评分说明',
    `lead_dept_id`             bigint                DEFAULT NULL COMMENT '牵头部门ID',
    `cooperate_dept_ids`       varchar(128)          DEFAULT NULL COMMENT '配合部门IDS',
    `type`                     TINYINT(1) DEFAULT NULL COMMENT '指标类型1牵头 0配合',
    `dept_id`                  bigint                DEFAULT NULL COMMENT '自评部门ID',
    `org_score`                decimal(20, 2)        DEFAULT NULL COMMENT '自评分数',
    `org_illustrate`           varchar(256)          DEFAULT NULL COMMENT '自评说明',
    `minus_cause`              varchar(256)          DEFAULT NULL COMMENT '减分原因',
    `measure`                  varchar(256)          DEFAULT NULL COMMENT '整改措施',
    `file_ids`                 varchar(128)          DEFAULT NULL COMMENT '佐证材料文件IDS',
    `file_names`               varchar(1024)         DEFAULT NULL COMMENT '佐证材料文件名称S',
    PRIMARY KEY (`id`),
    UNIQUE KEY `unx_org_assess_dept_id` (org_assess_dept_id,org_assess_index_id) USING BTREE COMMENT '部门考核ID/单位考核指标ID组合唯一索引'
) ENGINE=InnoDB COMMENT='粮食安全考核-部门考核指标表';

CREATE TABLE IF NOT EXISTS `safe_assess_org_assess_review`
(
    `id`                      varchar(20)    NOT NULL COMMENT '主键ID',
    `org_assess_id`           varchar(20)    NOT NULL COMMENT '单位考核ID',
    `org_assess_dept_id`      varchar(20)    NOT NULL COMMENT '部门考核ID',
    `template_id`             varchar(20)    NOT NULL COMMENT '考核模板ID',
    `template_number`         varchar(18)    NOT NULL COMMENT '考核模板编号',
    `template_name`           varchar(30)    NOT NULL COMMENT '考核模板名称',
    `year`                    varchar(4)     NOT NULL COMMENT '年份',
    `last_submit_date`        date                    DEFAULT NULL COMMENT '最晚提交日期',
    `cooperate_dept_end_date` date           NOT NULL COMMENT '配合部门填报截止日期',
    `submit_overtime`         TINYINT(1) DEFAULT NULL COMMENT '提交自评是否超时: 1超时 0不超时',
    `submit_time`             datetime                DEFAULT NULL COMMENT '提交自评时间',
    `dept_id`                 bigint(20) NOT NULL COMMENT '部门ID',
    `state`                   varchar(4)     NOT NULL COMMENT '状态：待自评、已自评、已退回',
    `lead_count`              int(11) NOT NULL DEFAULT 0 COMMENT '牵头指标数',
    `lead_await_count`        int(11) NOT NULL DEFAULT 0 COMMENT '待自评牵头指标数',
    `lead_finish_count`       int(11) NOT NULL DEFAULT 0 COMMENT '已自评牵头指标数',
    `lead_score`              decimal(20, 2) NOT NULL DEFAULT 0 COMMENT '牵头指标总分',
    `lead_already_score`      decimal(20, 2) NOT NULL DEFAULT 0 COMMENT '已自评牵头指标总分',
    `cooperate_count`         int(11) NOT NULL DEFAULT 0 COMMENT '配合指标数',
    `cooperate_await_count`   int(11) NOT NULL DEFAULT 0 COMMENT '待自评配合指标数',
    `cooperate_finish_count`  int(11) NOT NULL DEFAULT 0 COMMENT '已自评配合指标数',
    `cooperate_score`         decimal(20, 2) NOT NULL DEFAULT 0 COMMENT '配合指标总分',
    `cooperate_already_score` decimal(20, 2) NOT NULL DEFAULT 0 COMMENT '已自评配合指标总分',
    `create_by`               varchar(50)    NOT NULL COMMENT '创建者',
    `create_time`             datetime       NOT NULL COMMENT '创建时间',
    `update_by`               varchar(50)    NOT NULL COMMENT '更新者',
    `update_time`             datetime       NOT NULL COMMENT '修改时间',
    `region_id`               varchar(10)    NOT NULL COMMENT '行政区域ID',
    `country_id`              varchar(10)             DEFAULT NULL COMMENT '国ID',
    `province_id`             varchar(10)             DEFAULT NULL COMMENT '省ID',
    `city_id`                 varchar(10)             DEFAULT NULL COMMENT '市ID',
    `area_id`                 varchar(10)             DEFAULT NULL COMMENT '区ID',
    `organize_id`             varchar(20)             DEFAULT NULL COMMENT '组织ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `unx_org_assess_dept_id` (org_assess_dept_id,dept_id) USING BTREE COMMENT '单位考核ID/部门ID组合唯一索引',
    KEY                       `idx_org_assess_id` (`org_assess_id`) USING BTREE COMMENT '单位考核ID索引',
    KEY                       `idx_dept_id` (`dept_id`) USING BTREE COMMENT '部门ID索引',
    KEY                       `idx_template_number` (`template_number`) USING BTREE COMMENT '考核模板编号索引',
    KEY                       `idx_template_name` (`template_name`) USING BTREE COMMENT '考核模板名称索引',
    KEY                       `idx_cooperate_dept_end_date` (`cooperate_dept_end_date`) USING BTREE COMMENT '配合部门填报截止日期索引',
    KEY                       `idx_state` (`state`) USING BTREE COMMENT '状态索引',
    KEY                       `idx_region_id` (`region_id`) USING BTREE COMMENT '行政区域ID普通索引',
    KEY                       `idx_region_ids` (`country_id`,`province_id`,`city_id`,`area_id`) USING BTREE COMMENT '国/省/市/区组合普通索引',
    KEY                       `idx_organize_id` (`organize_id`) USING BTREE COMMENT '组织ID索引',
    KEY                       `idx_update_time` (`update_time`) USING BTREE COMMENT '最后更新时间普通索引'
) ENGINE=InnoDB COMMENT='粮食安全考核-部门自评表';

CREATE TABLE IF NOT EXISTS `safe_assess_org_assess_review_index`
(
    `id`                        varchar(20)  NOT NULL COMMENT '主键ID',
    `org_assess_dept_review_id` varchar(20)  NOT NULL COMMENT '部门自评ID',
    `org_assess_dept_index_id`  varchar(20)  NOT NULL COMMENT '部门考核指标ID',
    `template_index_id`         varchar(20)  NOT NULL COMMENT '考核模板指标ID',
    `template_index_parent_id`  varchar(20)  NOT NULL DEFAULT '0' COMMENT '考核模板指标父ID',
    `name`                      varchar(256) NOT NULL COMMENT '指标名称',
    `topic`                     TINYINT(1) NOT NULL COMMENT '是否是题目 1是 0否',
    `score`                     decimal(20, 2)        DEFAULT NULL COMMENT '分数',
    `standard`                  varchar(400)          DEFAULT NULL COMMENT '评分标准',
    `illustrate`                varchar(400)          DEFAULT NULL COMMENT '评分说明',
    `lead_dept_id`              bigint                DEFAULT NULL COMMENT '牵头部门ID',
    `cooperate_dept_ids`        varchar(128)          DEFAULT NULL COMMENT '配合部门IDS',
    `type`                      TINYINT(1) DEFAULT NULL COMMENT '指标类型1牵头 0配合',
    `dept_id`                   bigint                DEFAULT NULL COMMENT '自评部门ID',
    `org_score`                 decimal(20, 2)        DEFAULT NULL COMMENT '自评分数',
    `org_illustrate`            varchar(256)          DEFAULT NULL COMMENT '自评说明',
    `minus_cause`               varchar(256)          DEFAULT NULL COMMENT '减分原因',
    `measure`                   varchar(256)          DEFAULT NULL COMMENT '整改措施',
    `file_ids`                  varchar(128)          DEFAULT NULL COMMENT '佐证材料文件IDS',
    `file_names`                varchar(1024)         DEFAULT NULL COMMENT '佐证材料文件名称S',
    PRIMARY KEY (`id`),
    UNIQUE KEY `unx_org_assess_dept_index_id` (org_assess_dept_review_id,org_assess_dept_index_id) USING BTREE COMMENT '部门自评ID/部门考核指标ID唯一索引'
) ENGINE=InnoDB COMMENT='粮食安全考核-部门自评指标表';

CREATE TABLE IF NOT EXISTS `safe_assess_check_group`
(
    `id`             varchar(20)  NOT NULL COMMENT '主键id',
    `group_name`     varchar(128) NOT NULL COMMENT '小组名称',
    `leader_id`      varchar(20)  NOT NULL COMMENT '组长用户id',
    `liaison_man_id` varchar(20)  NOT NULL COMMENT '联络员用户id',
    `group_num`      int           DEFAULT NULL COMMENT '组员数',
    `remark`         varchar(2048) DEFAULT NULL COMMENT '备注',
    `create_by`      varchar(50)   DEFAULT NULL COMMENT '创建者',
    `create_time`    datetime      DEFAULT NULL COMMENT '创建时间',
    `update_by`      varchar(50)   DEFAULT NULL COMMENT '更新者',
    `update_time`    datetime      DEFAULT NULL COMMENT '修改时间',
    `region_id`      varchar(10)   DEFAULT NULL COMMENT '行政区域ID',
    `country_id`     varchar(10)   DEFAULT NULL COMMENT '国ID',
    `province_id`    varchar(10)   DEFAULT NULL COMMENT '省ID',
    `city_id`        varchar(10)   DEFAULT NULL COMMENT '市ID',
    `area_id`        varchar(10)   DEFAULT NULL COMMENT '区ID',
    `organize_id`    varchar(20)  NOT NULL COMMENT '组织ID',
    PRIMARY KEY (`id`),
    KEY              `idx_group_name` (`group_name`) USING BTREE COMMENT '小组名称索引',
    KEY              `idx_leader_id` (`leader_id`) USING BTREE COMMENT '组长的抽查人员id索引',
    KEY              `idx_liaison_man_id` (`liaison_man_id`) USING BTREE COMMENT '联络员的抽查人员id索引',
    KEY              `idx_region_id` (`region_id`) USING BTREE COMMENT '行政区域ID普通索引',
    KEY              `idx_region_ids` (`country_id`,`province_id`,`city_id`,`area_id`) USING BTREE COMMENT '国/省/市/区组合普通索引',
    KEY              `idx_organize_id` (`organize_id`) USING BTREE COMMENT '组织ID索引',
    KEY              `idx_update_time` (`update_time`) USING BTREE COMMENT '最后更新时间普通索引'
) ENGINE=InnoDB COMMENT='粮食安全考核-抽查小组表';

CREATE TABLE IF NOT EXISTS `safe_assess_check_group_personnel`
(
    `id`             varchar(20) NOT NULL COMMENT '主键id',
    `check_group_id` varchar(20) NOT NULL COMMENT '抽查小组id',
    `name`           varchar(64)  DEFAULT NULL COMMENT '抽查人员名称',
    `phone`          varchar(11)  DEFAULT NULL COMMENT '手机号',
    `unit`           varchar(128) DEFAULT NULL COMMENT '单位',
    `job`            varchar(64)  DEFAULT NULL COMMENT '职务',
    `create_by`      varchar(50)  DEFAULT NULL COMMENT '创建者',
    `create_time`    datetime     DEFAULT NULL COMMENT '创建时间',
    `update_by`      varchar(50)  DEFAULT NULL COMMENT '更新者',
    `update_time`    datetime     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY              `idx_check_group_id` (`check_group_id`) USING BTREE COMMENT '抽查小组id索引'
) ENGINE=InnoDB COMMENT='粮食安全考核-抽查小组成员表';

CREATE TABLE IF NOT EXISTS `safe_assess_check_plan`
(
    `id`                   varchar(20)  NOT NULL COMMENT '主键ID',
    `plan_name`            varchar(64)  NOT NULL COMMENT '计划名称',
    `plan_year`            varchar(4)   NOT NULL COMMENT '年度',
    `plan_state`           varchar(6)   NOT NULL DEFAULT '保存' COMMENT '状态：保存、抽查中、已抽查',
    `start_date`           date         NOT NULL COMMENT '计划开始日期',
    `end_date`             date         NOT NULL COMMENT '计划结束日期',
    `template_id`          varchar(20)  NOT NULL COMMENT '模板id',
    `group_id`             varchar(20)  NOT NULL COMMENT '小组id',
    `check_region_ids`     varchar(128) NOT NULL COMMENT '抽查对象行政区域ids,逗号分割',
    `region_count`         int          NOT NULL COMMENT '行政区域总数',
    `region_checked_count` int                   DEFAULT '0' COMMENT '已完成行政区域数量',
    `create_by`            varchar(50)           DEFAULT NULL COMMENT '创建者',
    `create_time`          datetime              DEFAULT NULL COMMENT '创建时间',
    `update_by`            varchar(50)           DEFAULT NULL COMMENT '更新者',
    `update_time`          datetime     NOT NULL COMMENT '修改时间',
    `region_id`            varchar(10)           DEFAULT NULL COMMENT '行政区域ID',
    `country_id`           varchar(10)           DEFAULT NULL COMMENT '国ID',
    `province_id`          varchar(10)           DEFAULT NULL COMMENT '省ID',
    `city_id`              varchar(10)           DEFAULT NULL COMMENT '市ID',
    `area_id`              varchar(10)           DEFAULT NULL COMMENT '区ID',
    `organize_id`          varchar(20)           DEFAULT NULL COMMENT '组织ID',
    PRIMARY KEY (`id`),
    KEY                    `idx_plan_name` (`plan_name`) USING BTREE COMMENT '计划名称普通索引',
    KEY                    `idx_template_id` (`template_id`) USING BTREE COMMENT '模板id普通索引',
    KEY                    `idx_plan_state` (`plan_state`) USING BTREE COMMENT '状态普通索引',
    KEY                    `idx_plan_year` (`plan_year`) USING BTREE COMMENT '年度普通索引',
    KEY                    `idx_region_id` (`region_id`) USING BTREE COMMENT '行政区域ID普通索引',
    KEY                    `idx_region_ids` (`country_id`,`province_id`,`city_id`,`area_id`) USING BTREE COMMENT '国/省/市/区组合普通索引',
    KEY                    `idx_organize_id` (`organize_id`) USING BTREE COMMENT '组织ID索引',
    KEY                    `idx_update_time` (`update_time`) USING BTREE COMMENT '最后更新时间普通索引'
) ENGINE=InnoDB COMMENT='粮食安全考核-抽查计划表';

CREATE TABLE IF NOT EXISTS `safe_assess_check_plan_dtl`
(
    `id`            varchar(20) NOT NULL COMMENT '主键ID',
    `plan_id`       varchar(20) NOT NULL COMMENT '计划ID',
    `org_assess_id` varchar(20) NOT NULL COMMENT '单位考核ID',
    `region_id`     varchar(10) NOT NULL COMMENT '行政区域ID',
    `country_id`    varchar(10) NOT NULL COMMENT '国ID',
    `province_id`   varchar(10) NOT NULL COMMENT '省ID',
    `city_id`       varchar(10) NOT NULL COMMENT '市ID',
    `area_id`       varchar(10)          DEFAULT NULL COMMENT '区ID',
    `address`       varchar(256)         DEFAULT NULL COMMENT '地址(逗号分隔)',
    `state`         varchar(6)  NOT NULL DEFAULT '保存' COMMENT '状态：保存、抽查中、已抽查',
    PRIMARY KEY (`id`) USING BTREE COMMENT '主键',
    UNIQUE KEY `unx_org_assess_id` (`org_assess_id`) USING BTREE COMMENT '单位考核ID唯一索引',
    KEY             `idx_plan_id` (`plan_id`) USING BTREE COMMENT '计划ID普通索引'
) ENGINE=InnoDB COMMENT='粮食安全考核-抽查计划明细表';

/***初始化角色菜单***/
insert ignore into `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1624969162138980353', '0', 'CL', '耕地保护和粮食安全责任制考核', '/safetyResponsibilityCheck', NULL, 'audit-outlined', '10', NULL, '超级管理员', '2023-02-13 11:10:13', '广东省管理员', '2023-04-07 14:23:13');
insert ignore into `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1624988500308922370', '1624969162138980353', 'CL', '抽查管理', '/spotCheckManage', NULL, 'appstore-outlined', '2', NULL, '超级管理员', '2023-02-13 12:27:04', '超级管理员', '2023-02-14 16:07:27');
insert ignore into `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1625406316547805185', '1624969162138980353', 'CL', '考核管理', '/provinceToCity', NULL, 'appstore-add-outlined', '1', NULL, '超级管理员', '2023-02-14 16:07:19', '超级管理员', '2023-02-15 19:05:28');
insert ignore into `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1625308367641776129', '1624988500308922370', 'MU', '抽查小组设置', '/groupSetting', 'groupSetting', 'setting-outlined', '2', NULL, '超级管理员', '2023-02-14 09:38:07', '超级管理员', '2023-02-14 09:38:07');
insert ignore into `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1626401602220986370', '1624988500308922370', 'MU', '抽查计划管理', '/planManage', 'planManage', 'link-outlined', '3', NULL, '广东省管理员', '2023-02-17 10:02:14', '广东省管理员', '2023-02-17 10:02:14');
insert ignore into `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1625406544055242753', '1625406316547805185', 'MU', '考核模板', '/assessmentTemplate', 'assessmentTemplate', 'bars-outlined', '1', NULL, '超级管理员', '2023-02-14 16:08:13', '超级管理员', '2023-02-15 19:05:43');
insert ignore into `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1627498329833345026', '1625406316547805185', 'MU', '单位考核', '/orgAssess', 'orgAssess', 'link-outlined', '2', NULL, '广东省管理员', '2023-02-20 10:40:14', '广东省管理员', '2023-02-20 10:40:14');
insert ignore into `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1627956728572284929', '1625406316547805185', 'MU', '部门考核', '/deptAssess', 'deptAssess', 'file-search-outlined', '3', NULL, '广东省管理员', '2023-02-21 17:01:45', '广东省管理员', '2023-02-21 17:01:45');
insert ignore into `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1628324410039603202', '1625406316547805185', 'MU', '部门自评', '/deptReview', 'deptReview', 'paper-clip-outlined', '4', NULL, '云浮市管理员', '2023-02-22 17:22:47', '云浮市管理员', '2023-02-22 17:22:47');
insert ignore into `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1643185082950881282', '1625406316547805185', 'MU', '考核评审', '/assessmentCheck', 'assessmentCheck', 'carry-out-outlined', '1', NULL, '超级管理员', '2023-04-04 17:33:47', '超级管理员', '2023-04-04 17:33:47');
insert ignore into `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1643185350455201794', '1625406316547805185', 'MU', '考核评分', '/assessmentScore', 'assessmentScore', 'audit-outlined', '1', NULL, '超级管理员', '2023-04-04 17:34:51', '超级管理员', '2023-04-04 17:34:51');

insert ignore into `org_role` (`id`, `name`, `permission`, `order_num`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1631630199298854913', '耕地保护和粮食安全责任制考核抽查角色', 'safeAssessCheckRole', '1', '超级管理员', '2023-03-03 20:18:48', '广东省管理员', '2023-04-10 09:14:56');
insert ignore into `org_role` (`id`, `name`, `permission`, `order_num`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1645233948151910402', '耕地保护和粮食安全责任制考核考核办角色', 'safeAssess', '1', '广东省管理员', '2023-04-10 09:15:15', '广东省管理员', '2023-04-10 09:15:15');


insert ignore into `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1645280326827098114', '1631630199298854913', '1624969162138980353');
insert ignore into `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1645280326827098112', '1631630199298854913', '1624988500308922370');
insert ignore into `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1645280326827098113', '1631630199298854913', '1625308367641776129');
insert ignore into `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1645280331243700224', '1631630199298854913', '1626401602220986370');
insert ignore into `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1645280364936544263', '1645233948151910402', '1624969162138980353');
insert ignore into `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1645280364936544258', '1645233948151910402', '1624988500308922370');
insert ignore into `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1645280364936544261', '1645233948151910402', '1625308367641776129');
insert ignore into `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1645280364936544259', '1645233948151910402', '1625406316547805185');
insert ignore into `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1645280364936544266', '1645233948151910402', '1625406544055242753');
insert ignore into `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1645280364936544257', '1645233948151910402', '1626401602220986370');
insert ignore into `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1645280364936544264', '1645233948151910402', '1627498329833345026');
insert ignore into `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1645280364936544256', '1645233948151910402', '1627956728572284929');
insert ignore into `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1645280364936544262', '1645233948151910402', '1628324410039603202');
insert ignore into `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1645280364936544265', '1645233948151910402', '1643185082950881282');
insert ignore into `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1645280364936544260', '1645233948151910402', '1643185350455201794');

insert ignore into `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1624969162475012096', '1', '1624969162138980353');
insert ignore into `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1624988500582039552', '1', '1624988500308922370');
insert ignore into `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1625308370045198336', '1', '1625308367641776129');
insert ignore into `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1625406317147328512', '1', '1625406316547805185');
insert ignore into `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1625406544243724288', '1', '1625406544055242753');
insert ignore into `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1626401602578006016', '1', '1626401602220986370');
insert ignore into `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1627498331788406784', '1', '1627498329833345026');
insert ignore into `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1627956731592667136', '1', '1627956728572284929');
insert ignore into `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1628324411751391232', '1', '1628324410039603202');
insert ignore into `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1643185083763904512', '1', '1643185082950881282');
insert ignore into `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1643185350945263616', '1', '1643185350455201794');


