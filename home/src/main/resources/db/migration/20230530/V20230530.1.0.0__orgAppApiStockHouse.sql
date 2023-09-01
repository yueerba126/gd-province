CREATE TABLE IF NOT EXISTS `org_app_api_stock_house`(
    `id` varchar(20) NOT NULL COMMENT '主键id',
    `appid` varchar(32) NOT NULL COMMENT '应用id',
    `region_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '行政区域ID',
    `country_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '国ID',
    `province_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '省ID',
    `city_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '市ID',
    `area_id` varchar(10) NOT NULL DEFAULT '-1' COMMENT '区ID',
    `enterprise_id` varchar(18) NOT NULL DEFAULT '-1' COMMENT '企业ID',
    `stock_house_id` varchar(21) NOT NULL DEFAULT '-1' COMMENT '库区ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE `unx_appid` (`appid`,stock_house_id) USING BTREE COMMENT '应用id/库区ID组合唯一索引',
    KEY `idx_enterprise_id` (`enterprise_id`) USING BTREE COMMENT '企业ID普通索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='组织架构-app对接应用关联库区表';


ALTER TABLE org_app_api MODIFY `organize_id` varchar(20) NOT NULL COMMENT '组织ID';