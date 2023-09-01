/*组织新增业务类型*/
alter table org_organize add bus_type varchar(16) default null comment '组织业务类型：粮食监管单位';
alter table org_organize add unique index unx_bus_type(bus_type,region_id) COMMENT '组织业务类型/行政区划组合唯一索引';

/*组织业务类型字典初始化*/
INSERT INTO `basis_dict` (`id`, `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1900', 'organize_bus_type', '1', 'food_supervise', '粮食监管单位', '', NULL, NULL, '一级', 'admin', '2022-07-28 15:31:29', '超级管理员', '2023-04-28 15:16:05');
