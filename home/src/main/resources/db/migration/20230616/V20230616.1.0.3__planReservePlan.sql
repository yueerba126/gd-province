alter table plan_reserve_plan add column `main_rotate_type` varchar(20) DEFAULT NULL COMMENT '轮换类型：1静态轮换，2.自主轮换 字典：main_rotate_type';
alter table plan_reserve_plan add index idx_main_rotate_type(main_rotate_type) comment '轮换类型普通索引';

delete from basis_dict where dict_type = 'main_rotate_type';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('main_rotate_type', 1, '1', '静态轮换', '', NULL, NULL, NULL, NULL, '2023-06-16 10:56:25', NULL, '2023-06-16 10:56:25');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('main_rotate_type', 2, '2', '自主轮换', '', NULL, NULL, NULL, NULL, '2023-06-16 10:56:25', NULL, '2023-06-16 10:56:25');
