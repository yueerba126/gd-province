CREATE TABLE IF NOT EXISTS `org_menu_system` (
   `id` varchar(20) NOT NULL COMMENT '主键ID',
   `name` varchar(100) NOT NULL COMMENT '系统名称',
   `short_name` varchar(30) NOT NULL COMMENT '系统简称',
   `logo` varchar(60) NOT NULL COMMENT '系统图标',
   `create_by` varchar(60) DEFAULT NULL COMMENT '创建人',
   `create_time` datetime DEFAULT NULL COMMENT '创建时间',
   `update_by` varchar(60) DEFAULT NULL COMMENT '最后修改人',
   `update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
   `remark` varchar(200) DEFAULT NULL COMMENT '备注',
   PRIMARY KEY (`id`) USING BTREE,
   UNIQUE KEY `unx_name` (`name`) USING BTREE COMMENT '系统名称唯一索引',
   UNIQUE KEY `unx_short_name` (`short_name`) USING BTREE COMMENT '系统简称唯一索引'
) ENGINE=InnoDB COMMENT='组织架构-菜单系统';

alter table org_menu add column menu_system_id varchar(20) DEFAULT NULL COMMENT '菜单系统ID';
alter table org_menu add index idx_menu_system_id(menu_system_id) COMMENT '菜单系统ID普通索引';
