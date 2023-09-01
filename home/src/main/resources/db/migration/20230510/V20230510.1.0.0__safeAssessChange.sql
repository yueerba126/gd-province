/***考核模板新增《年份/名称/组织ID》组合唯一索引***/
alter table safe_assess_template add unique unx_name(year,name,organize_id) COMMENT '年份/名称/组织ID组合唯一索引';

/*修改单位考核菜单为市区考核*/
update org_menu set name = '市区考核' where id = '1627498329833345026';