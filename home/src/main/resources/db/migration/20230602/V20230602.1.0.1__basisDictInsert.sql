-- 等级粮库评定管理-等级粮库评定模板-模板状态
delete from basis_dict where dict_type = 'grade_template_state';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_template_state', 1, '1', '拟稿', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_template_state', 2, '2', '已发布', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_template_state', 3, '3', '已完成', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');

-- 等级粮库评定管理-等级粮库评定模板—加分指标
delete from basis_dict where dict_type = 'grade_jfzb';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_jfzb', 1, '1', '是', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_jfzb', 2, '2', '否', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');

-- 等级粮库评定管理-企业申报审核-库点等级
delete from basis_dict where dict_type = 'grade_stock';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_stock', 1, '1', 'A', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_stock', 2, '2', 'AA', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_stock', 3, '3', 'AAA', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_stock', 4, '4', 'AAAA', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_stock', 5, '5', 'AAAAA', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');

-- 等级粮库评定管理-企业申报审核-授牌状态
delete from basis_dict where dict_type = 'grade_spzt';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_spzt', 1, '0', '待授牌', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_spzt', 2, '1', '是', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_spzt', 3, '2', '否', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');

-- 等级粮库评定管理-企业申报审核-实地评分状态
delete from basis_dict where dict_type = 'grade_sdzt';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_sdzt', 1, '1', '待确认', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_sdzt', 2, '2', '通过', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_sdzt', 3, '3', '不通过', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');

-- 等级粮库评定管理-企业申报审核-申报状态
delete from basis_dict where dict_type = 'grade_sbzt';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_sbzt', 1, '1', '保存', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_sbzt', 2, '2', '待审核', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_sbzt', 3, '3', '审核不通过', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_sbzt', 4, '4', '已申报', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_sbzt', 5, '5', '已授牌', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');

-- 等级粮库评定管理-企业申报审核-企业等级
delete from basis_dict where dict_type = 'grade_qydj';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_qydj', 1, '1', '省', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_qydj', 2, '2', '市', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_qydj', 3, '3', '县', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_qydj', 4, '5', '区', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');

-- 等级粮库评定管理-企业申报审核-审核结果
delete from basis_dict where dict_type = 'grade_shjg';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_shjg', 1, '1', '通过', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_shjg', 2, '2', '不通过', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_shjg', 3, '3', '降级', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_shjg', 4, '4', '摘除', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');

-- 等级粮库评定管理-企业申报审核-市级流程
delete from basis_dict where dict_type = 'grade_city_flow';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_city_flow', 1, '1', '库点申报', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_city_flow', 2, '2', '企业内审', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_city_flow', 3, '3', '县粮食局审核', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_city_flow', 4, '4', '市粮食局审核', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_city_flow', 5, '5', '市级实地核查', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_city_flow', 8, '8', '通过授牌', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');

-- 等级粮库评定管理-企业申报审核-省级流程
delete from basis_dict where dict_type = 'grade_province_flow';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_province_flow', 1, '1', '库点申报', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_province_flow', 2, '2', '企业内审', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_province_flow', 3, '3', '县粮食局审核', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_province_flow', 4, '4', '市粮食局审核', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_province_flow', 5, '5', '市级实地核查', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_province_flow', 6, '6', '省粮食局审核', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_province_flow', 7, '7', '省级实地核查', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_province_flow', 8, '8', '通过授牌', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');

-- 等级粮库评定管理-企业申报审核-流程状态
delete from basis_dict where dict_type = 'grade_flow_status';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_flow_status', 1, '1', '保存', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_flow_status', 2, '2', '待审核', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_flow_status', 3, '3', '审核通过', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_flow_status', 4, '4', '审核不通过', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');

-- 等级粮库评定管理-等级粮库评定模板-保存状态
delete from basis_dict where dict_type = 'grade_bczt';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_bczt', 1, '1', '暂存', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_bczt', 2, '2', '提交', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');

-- 等级粮库评定管理-审核操作状态
delete from basis_dict where dict_type = 'grade_czzt';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_czzt', 1, '1', '审核通过', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_czzt', 2, '2', '审核不通过', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_czzt', 3, '3', '实地通过', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_czzt', 4, '4', '实地不通过', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_czzt', 5, '5', '授牌通过', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_czzt', 6, '6', '授牌不通过', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_czzt', 7, '7', '被降级', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_czzt', 8, '8', '被摘除', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');

-- 等级粮库评定管理-状态
delete from basis_dict where dict_type = 'grade_status';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_status', 1, '1', '待评分', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('grade_status', 2, '2', '待授牌', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
