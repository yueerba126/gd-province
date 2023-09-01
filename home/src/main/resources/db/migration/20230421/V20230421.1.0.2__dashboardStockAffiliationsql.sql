CREATE TABLE IF NOT EXISTS `dashboard_stock_affiliation` (
     `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
     `lspzlb` varchar(10) NOT NULL COMMENT '粮食品种类别',
     `lspzdm` varchar(7) DEFAULT NULL COMMENT '粮食品种代码',
     `lsxzdm` varchar(3) DEFAULT NULL COMMENT '粮食性质代码',
     `sjsl` decimal(20,3) NOT NULL DEFAULT '0.000' COMMENT '实际数量(公斤)',
     `jjsl` decimal(20,3) NOT NULL DEFAULT '0.000' COMMENT '计价数量（公斤）',
     `lqxzqhdm` varchar(6) DEFAULT NULL COMMENT '粮权行政区划代码',
     `region_id` varchar(10) DEFAULT NULL COMMENT '行政区域ID',
     `country_id` varchar(10) DEFAULT NULL COMMENT '国ID',
     `province_id` varchar(10) DEFAULT NULL COMMENT '省ID',
     `city_id` varchar(10) DEFAULT NULL COMMENT '市ID',
     `area_id` varchar(10) DEFAULT NULL COMMENT '区ID',
     `enterprise_id` varchar(18) DEFAULT NULL COMMENT '企业ID',
     `stock_house_id` varchar(21) DEFAULT NULL COMMENT '库区ID',
     PRIMARY KEY (`id`),
     UNIQUE KEY unx_key(stock_house_id,lspzdm,lsxzdm,lqxzqhdm)  USING BTREE COMMENT '库区ID/粮食品种/粮食性质/粮权行政区划组合唯一索引',
     KEY `idx_lsxzdm` (`lsxzdm`) USING BTREE COMMENT '粮食性质普通索引',
     KEY `idx_lspzlb` (`lspzlb`) USING BTREE COMMENT '粮食品种类别普通索引',
     KEY `idx_region_id` (`region_id`) USING BTREE COMMENT '行政区域ID普通索引',
     KEY `idx_region_ids` (`country_id`,`province_id`,`city_id`,`area_id`) USING BTREE COMMENT '行政地区组合索引',
     KEY `idx_jjsl` (`jjsl`) USING BTREE COMMENT '计价数量普通索引',
     KEY `idx_enterprise` (`enterprise_id`) USING BTREE COMMENT '企业ID普通索引',
     KEY `idx_stock_house` (`stock_house_id`) USING BTREE COMMENT '库区ID普通索引',
     KEY `idx_lqxzqhdm` (`lqxzqhdm`) USING BTREE COMMENT '粮权行政区划代码普通索引'
) ENGINE=MyISAM COMMENT='报表管理-库存归属报表';


/***初始化数据***/
insert ignore into dashboard_stock_affiliation
(lspzlb,lspzdm,lsxzdm,sjsl,jjsl,lqxzqhdm,region_id,country_id,province_id,city_id,area_id,enterprise_id,stock_house_id)
select
    a.lspzlb,
    a.lspzdm,
    a.lsxzdm,
    sum(a.sjsl) as sjsl,
    sum(a.jjsl) as jjsl,
    a.lqxzqhdm as lqxzqhdm,
    if(a.lqxzqhdm is null,a.region_id,b.id) as region_id,
    if(a.lqxzqhdm is null,a.country_id,b.country_id) as country_id,
    if(a.lqxzqhdm is null,a.province_id,b.province_id) as province_id,
    if(a.lqxzqhdm is null,a.city_id,b.city_id) as city_id,
    if(a.lqxzqhdm is null,a.area_id,b.area_id) as area_id,
    a.enterprise_id,
    a.stock_house_id
from trade_stock_grain_newest a
         LEFT JOIN org_region b on b.id = a.lqxzqhdm
GROUP BY a.stock_house_id,a.lspzdm,a.lsxzdm,a.lqxzqhdm;
