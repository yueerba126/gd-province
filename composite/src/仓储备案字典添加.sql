-- 仓储备案-企业性质 0:国有 1:外资 2:民营 3:其它
delete from basis_dict where dict_type = 'filing_dwxz';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_dwxz', 0, '0', '国有', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_dwxz', 1, '1', '外资', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_dwxz', 2, '2', '民营', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_dwxz', 3, '3', '其它', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');

-- 仓储备案-备案状态 0:保存 1:待备案 2:已备案 3:审核不通过 4:已注销
delete from basis_dict where dict_type = 'filing_bazt';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (
'filing_bazt', 0, '0', '保存', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (
'filing_bazt', 1, '1', '待备案', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (
'filing_bazt', 2, '2', '已备案', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (
'filing_bazt', 3, '3', '审核不通过', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (
'filing_bazt', 4, '4', '已注销', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');

-- 仓储备案-备案来源(0:库软件,1:粤商局)
delete from basis_dict where dict_type = 'filing_baly';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_baly', 0, '0', '库软件', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_baly', 1, '1', '粤商局', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');

-- 仓储备案-仓储业务类型逗号分隔 0:储备 1:收购 2:加工 3:销售 4:运输 5:中转 6:进出口 7:其他
delete from basis_dict where dict_type = 'filing_ccywlx';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_ccywlx', 0, '0', '储备', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_ccywlx', 1, '1', '收购', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_ccywlx', 2, '2', '加工', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_ccywlx', 3, '3', '销售', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_ccywlx', 4, '4', '运输', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_ccywlx', 5, '5', '中转', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_ccywlx', 6, '6', '进出口', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_ccywlx', 7, '7', '其他', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');

-- 仓储备案-仓储品种逗号分隔 0:小麦 1:玉米 2:稻谷 3:大豆 4:成品粮 5:食用植物油 6:其他
delete from basis_dict where dict_type = 'filing_ccpz';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_ccpz', 0, '0', '小麦', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_ccpz', 1, '1', '玉米', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_ccpz', 2, '2', '稻谷', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_ccpz', 3, '3', '大豆', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_ccpz', 4, '4', '成品粮', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_ccpz', 5, '5', '食用植物油', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_ccpz', 6, '6', '其他', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');

-- 仓储备案-粮食平台注册(0:未注册,1:已注册) 默认已注册
delete from basis_dict where dict_type = 'filing_lsptzc';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_lsptzc', 0, '0', '未注册', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_lsptzc', 1, '1', '已注册', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');

-- 仓储备案-0:有 1:无
delete from basis_dict where dict_type = 'filing_yw';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_yw', 0, '0', '有', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_yw', 1, '1', '无', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');

-- 仓储备案-人员类别(0:法人,1:主要联系人,2:从业人员)
delete from basis_dict where dict_type = 'filing_rylb';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_rylb', 0, '0', '法人', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_rylb', 1, '1', '主要联系人', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_rylb', 2, '2', '从业人员', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');

-- 仓储备案-从业人员职务/岗位(0:粮油保管员 1:粮油质检员）
delete from basis_dict where dict_type = 'filing_cyzw';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_cyzw', 0, '0', '粮油保管员', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_cyzw', 1, '1', '粮油质检员', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');

-- 仓储备案-从业人员级别(0:初级工 1:中级工 2:高级工)
delete from basis_dict where dict_type = 'filing_ryjb';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_ryjb', 0, '0', '初级工', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_ryjb', 1, '1', '中级工', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_ryjb', 2, '2', '高级工', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');

-- 仓储备案-审核结果(0:通过,1:不通过)
delete from basis_dict where dict_type = 'filing_shjg';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_shjg', 0, '0', '通过', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_shjg', 1, '1', '不通过', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');

-- 仓储备案-备案类型(0:初始备案 1:变更备案 2:注销备案)
delete from basis_dict where dict_type = 'filing_balx';
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_balx', 0, '0', '初始备案', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_balx', 1, '1', '变更备案', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
INSERT INTO `basis_dict`( `dict_type`, `dict_sort`, `dict_key`, `dict_value`, `dict_parent_key`, `dict_top_key`, `dict_far_type`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('filing_balx', 2, '2', '注销备案', '', NULL, NULL, '', NULL, '2022-08-24 14:24:25', NULL, '2023-01-30 10:44:55');
