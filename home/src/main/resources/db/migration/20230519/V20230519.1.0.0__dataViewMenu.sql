INSERT ignore INTO `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1659371305482457089', '0', 'BT', '数据视图切换', NULL, NULL, NULL, '2', 'dataView', '超级管理员', '2023-05-19 09:32:04', '超级管理员', '2023-05-19 09:32:04');
INSERT ignore INTO `org_menu` (`id`, `parent_id`, `type`, `name`, `path`, `component`, `icon`, `order_num`, `permission`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1659371468603133954', '0', 'BT', '库区视图切换', NULL, NULL, NULL, '3', 'stockHouseView', '超级管理员', '2023-05-19 09:32:42', '超级管理员', '2023-05-19 09:32:42');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1659371305805836288', '1', '1659371305482457089');
INSERT ignore INTO `org_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1659371468788101120', '1', '1659371468603133954');


