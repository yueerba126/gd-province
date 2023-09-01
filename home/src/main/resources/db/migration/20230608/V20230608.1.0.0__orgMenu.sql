alter table org_menu drop column menu_system_id;
alter table org_organize add column menu_system_id varchar(20) default null comment '菜单系统id';
alter table org_organize add index idx_menu_system_id(menu_system_id) comment '菜单系统id普通索引';
alter table org_organize drop index unx_bus_type;
delete from basis_dict where dict_type = 'organize_bus_type';
insert into `basis_dict` (`dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) values ('organize_bus_type', '1', 'food_assess', '粮安考核单位', '', null, null, null, 'admin', '2022-07-28 15:31:29', '超级管理员', '2023-06-08 10:57:49');
insert into `basis_dict` (`dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) values ('organize_bus_type', '1', 'food_supervise', '粮食监管单位', '', null, null, null, 'admin', '2022-07-28 15:31:29', '超级管理员', '2023-06-08 10:56:47');
