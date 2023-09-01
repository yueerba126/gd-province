ALTER TABLE safe_assess_org_assess MODIFY COLUMN `file_ids` varchar(512) DEFAULT NULL COMMENT '文件IDS';
ALTER TABLE safe_assess_org_assess MODIFY COLUMN `check_file_ids` varchar(512) DEFAULT NULL COMMENT '抽查文件IDS';
ALTER TABLE safe_assess_org_assess_index MODIFY COLUMN `file_ids` varchar(512) DEFAULT NULL COMMENT '佐证材料文件IDS';
ALTER TABLE safe_assess_org_assess_index MODIFY COLUMN `check_file_ids` varchar(512) DEFAULT NULL COMMENT '抽查文件IDS';
ALTER TABLE safe_assess_org_assess_dept_index MODIFY COLUMN `file_ids` varchar(512) DEFAULT NULL COMMENT '佐证材料文件IDS';
ALTER TABLE safe_assess_org_assess_review_index MODIFY COLUMN `file_ids` varchar(512) DEFAULT NULL COMMENT '佐证材料文件IDS';