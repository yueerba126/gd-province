alter table collect_request_log add column `create_by` varchar(50) DEFAULT NULL COMMENT '创建者';
alter table report_data_handle add column `create_by` varchar(50) DEFAULT NULL COMMENT '创建者';