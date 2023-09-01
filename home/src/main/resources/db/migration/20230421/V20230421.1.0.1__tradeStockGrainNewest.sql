/***库存新增粮权行政区划类型字段,并且修复数据***/
alter table trade_stock_grain add `lqxzqh_type` varchar(2) DEFAULT NULL COMMENT '粮权行政区划类型：国、省、市、区';
alter table trade_stock_grain add index idx_lqxzqhdm(lqxzqhdm) COMMENT '粮权行政区划代码普通索引';
alter table trade_stock_grain add index idx_lqxzqh_type(lqxzqh_type) COMMENT '粮权行政区划类型普通索引';
update trade_stock_grain a,org_region b set a.lqxzqh_type = b.type where a.lqxzqhdm is not null and a.lqxzqhdm = b.id;


CREATE TABLE IF NOT EXISTS `trade_stock_grain_newest`(
    `id`             varchar(68)    NOT NULL COMMENT '主键id',
    `hwdm`           varchar(30)    NOT NULL COMMENT '货位代码',
    `lspzlb`         varchar(10)    NOT NULL COMMENT '粮食品种类别',
    `lspzdm`         varchar(7)     NOT NULL COMMENT '粮食品种代码',
    `lsxzdm`         varchar(3)              DEFAULT NULL COMMENT '粮食性质代码',
    `lsdjdm`         varchar(2)              DEFAULT NULL COMMENT '粮食等级代码',
    `shnd`           varchar(4)              DEFAULT NULL COMMENT '收货年度',
    `gb`             varchar(6)              DEFAULT NULL COMMENT '国别',
    `cd`             varchar(6)              DEFAULT NULL COMMENT '产地',
    `bgy`            varchar(64)             DEFAULT NULL COMMENT '保管员',
    `lqgsdwdm`       varchar(18)             DEFAULT NULL COMMENT '粮权归属单位代码',
    `lqxzqhdm`       varchar(6)              DEFAULT NULL COMMENT '粮权行政区划代码',
    `lqxzqh_type`    varchar(2)              DEFAULT NULL COMMENT '粮权行政区划类型：国、省、市、区',
    `glfs`           varchar(2)              DEFAULT NULL COMMENT '管理方式 01：直储，02：代储，03，租仓',
    `scdd`           varchar(1)              DEFAULT NULL COMMENT '收储地点 1：库内。2：库外',
    `clfs`           varchar(1)              DEFAULT NULL COMMENT '储粮方式 1：散装储粮，2：包装，3：围包散存，9：其他',
    `hwzt`           varchar(1)     NOT NULL COMMENT '货位（油罐）状态1：空仓，2：入库中，3：封仓，4：出库中，9：其他',
    `rcsj`           datetime       NOT NULL COMMENT '入仓时间',
    `fcrq`           date                    DEFAULT NULL COMMENT '封仓日期',
    `ccwcsj`         datetime                DEFAULT NULL COMMENT '出仓完成时间',
    `qcsj`           datetime                DEFAULT NULL COMMENT '清仓时间',
    `chwqsh`         decimal(20, 6)          DEFAULT NULL COMMENT '成货位前损耗',
    `sjsl`           decimal(20, 3) NOT NULL COMMENT '实际数量(公斤)',
    `jjsl`           decimal(20, 3) NOT NULL COMMENT '计价数量（公斤）',
    `bclbs`          int                     DEFAULT NULL COMMENT '包存量包数（包）',
    `sjzlxg`         decimal(20, 2)          DEFAULT NULL COMMENT '实际装粮线高（米）',
    `ldtj`           decimal(20, 3)          DEFAULT NULL COMMENT '粮堆体积（立方米）',
    `czbz`           varchar(1)              DEFAULT NULL COMMENT '操作标志 i：新增，u：更新，d：删除',
    `bz`             varchar(400)            DEFAULT NULL COMMENT '备注',
    `zhgxsj`         datetime       NOT NULL COMMENT '最后更新时间',
    `create_by`      varchar(64)             DEFAULT NULL COMMENT '创建人',
    `create_time`    datetime                DEFAULT NULL COMMENT '创建时间',
    `update_by`      varchar(64)             DEFAULT NULL COMMENT '修改人',
    `update_time`    datetime       NOT NULL COMMENT '修改时间',
    `region_id`      varchar(10)    NOT NULL DEFAULT '-1' COMMENT '行政区域ID',
    `country_id`     varchar(10)    NOT NULL DEFAULT '-1' COMMENT '国ID',
    `province_id`    varchar(10)    NOT NULL DEFAULT '-1' COMMENT '省ID',
    `city_id`        varchar(10)    NOT NULL DEFAULT '-1' COMMENT '市ID',
    `area_id`        varchar(10)    NOT NULL DEFAULT '-1' COMMENT '区ID',
    `enterprise_id`  varchar(18)    NOT NULL DEFAULT '-1' COMMENT '企业ID',
    `stock_house_id` varchar(21)    NOT NULL DEFAULT '-1' COMMENT '库区ID',
    PRIMARY KEY (`id`, `update_time`),
    UNIQUE KEY `unx_hwdm` (`hwdm`,`lspzdm`,`rcsj`) USING BTREE COMMENT '粮食库存唯一索引',
    KEY              `idx_lspzlb` (`lspzlb`) USING BTREE COMMENT '粮食品种类别普通索引',
    KEY              `idx_lqgsdwdm` (`lqgsdwdm`) USING BTREE COMMENT '粮权归属单位代码普通索引',
    KEY              `idx_lspzdm` (`lspzdm`) USING BTREE COMMENT '粮食品种代码普通索引',
    KEY              `idx_lsxzdm` (`lsxzdm`) USING BTREE COMMENT '粮食性质代码普通索引',
    KEY              `idx_rcsj` (`rcsj`) USING BTREE COMMENT '入仓时间普通索引',
    KEY              `idx_zhgxsj` (`zhgxsj`) USING BTREE COMMENT '最后更新时间普通索引',
    KEY              `idx_region_id` (`region_id`) USING BTREE COMMENT '行政区域ID普通索引',
    KEY              `idx_region_ids` (`country_id`,`province_id`,`city_id`,`area_id`) USING BTREE COMMENT '行政地区组合索引',
    KEY              `idx_jjsl` (`jjsl`) USING BTREE COMMENT '计价数量普通索引',
    KEY              `idx_lqxzqhdm` (`lqxzqhdm`) USING BTREE COMMENT '粮权行政区划代码普通索引',
    KEY              `idx_lqxzqh_type` (`lqxzqh_type`) USING BTREE COMMENT '粮权行政区划类型普通索引',
    KEY              `idx_update_time` (`update_time`) USING BTREE COMMENT '最后更新时间普通索引',
    KEY              `idx_enterprise` (`enterprise_id`) USING BTREE COMMENT '企业ID普通索引',
    KEY              `idx_stock_house` (`stock_house_id`) USING BTREE COMMENT '库区ID普通索引'
) ENGINE=InnoDB COMMENT='粮食库存最新数据表';

/***初始化角色菜单***/
INSERT ignore INTO `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1649354536722239489', '1561549593768919041', 'MU', '粮食最新库存', '/excessStockpilesOfFoodNew', 'excessStockpilesOfFoodNew', 'appstore-outlined', '9', NULL, '超级管理员', '2023-04-21 18:09:00', '超级管理员', '2023-04-21 18:10:58');

INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1649354539918811136', '1', '1649354536722239489');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1649357910247596032', '1592144207126994945', '1649354536722239489');

/***初始化粮食库存最新数据表数据***/
insert ignore into trade_stock_grain_newest
SELECT
    a.`id`,
    a.`hwdm`,
    b.dict_far_type as lspzlb,
    a.`lspzdm`,
    a.`lsxzdm`,
    a.`lsdjdm`,
    a.`shnd`,
    a.`gb`,
    a.`cd`,
    a.`bgy`,
    a.`lqgsdwdm`,
    a.`lqxzqhdm`,
    a.`lqxzqh_type`,
    a.`glfs`,
    a.`scdd`,
    a.`clfs`,
    a.`hwzt`,
    a.`rcsj`,
    a.`fcrq`,
    a.`ccwcsj`,
    a.`qcsj`,
    a.`chwqsh`,
    a.`sjsl`,
    a.`jjsl`,
    a.`bclbs`,
    a.`sjzlxg`,
    a.`ldtj`,
    a.`czbz`,
    a.`bz`,
    a.`zhgxsj`,
    a.`create_by`,
    a.`create_time`,
    a.`update_by`,
    a.`update_time`,
    a.`region_id`,
    a.`country_id`,
    a.`province_id`,
    a.`city_id`,
    a.`area_id`,
    a.`enterprise_id`,
    a.`stock_house_id`
FROM trade_stock_grain a
INNER JOIN (
   SELECT
   CONCAT(hwdm,'-',lspzdm,'-',DATE_FORMAT(rcsj, '%Y%m%d%H%i%s'),'-',DATE_FORMAT(MAX(zhgxsj), '%Y%m%d%H%i%s')) as id
   FROM  trade_stock_grain
   GROUP BY hwdm,lspzdm,rcsj
) c on c.id = a.id
LEFT JOIN basis_dict b on b.dict_key=a.lspzdm
