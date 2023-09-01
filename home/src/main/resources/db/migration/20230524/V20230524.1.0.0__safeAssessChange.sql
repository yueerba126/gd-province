ALTER TABLE safe_assess_template_index MODIFY COLUMN `standard` varchar(1024) DEFAULT NULL COMMENT '评分标准';
ALTER TABLE safe_assess_template_index MODIFY COLUMN `illustrate` varchar(1024) DEFAULT NULL COMMENT '评分说明';


ALTER TABLE safe_assess_review_index MODIFY COLUMN `standard` varchar(1024) DEFAULT NULL COMMENT '评分标准';
ALTER TABLE safe_assess_review_index MODIFY COLUMN `illustrate` varchar(1024) DEFAULT NULL COMMENT '评分说明';
ALTER TABLE safe_assess_review_index MODIFY COLUMN `assess_minus_cause` varchar(1024) DEFAULT NULL COMMENT '减分原因';


ALTER TABLE safe_assess_score_index MODIFY COLUMN `standard` varchar(1024) DEFAULT NULL COMMENT '评分标准';
ALTER TABLE safe_assess_score_index MODIFY COLUMN `illustrate` varchar(1024) DEFAULT NULL COMMENT '评分说明';
ALTER TABLE safe_assess_score_index MODIFY COLUMN `assess_minus_cause` varchar(1024) DEFAULT NULL COMMENT '减分原因';


ALTER TABLE safe_assess_org_assess_index MODIFY COLUMN `standard` varchar(1024) DEFAULT NULL COMMENT '评分标准';
ALTER TABLE safe_assess_org_assess_index MODIFY COLUMN `illustrate` varchar(1024) DEFAULT NULL COMMENT '评分说明';
ALTER TABLE safe_assess_org_assess_index MODIFY COLUMN `org_illustrate` varchar(1024) DEFAULT NULL COMMENT '自评说明';
ALTER TABLE safe_assess_org_assess_index MODIFY COLUMN `minus_cause` varchar(1024) DEFAULT NULL COMMENT '减分原因';
ALTER TABLE safe_assess_org_assess_index MODIFY COLUMN `measure` varchar(1024) DEFAULT NULL COMMENT '整改措施';
ALTER TABLE safe_assess_org_assess_index MODIFY COLUMN `assess_minus_cause` varchar(1024) DEFAULT NULL COMMENT '考核减分原因';
ALTER TABLE safe_assess_org_assess_index MODIFY COLUMN `check_remark` varchar(1024) DEFAULT NULL COMMENT '抽查说明';


ALTER TABLE safe_assess_org_assess_dept_index MODIFY COLUMN `standard` varchar(1024) DEFAULT NULL COMMENT '评分标准';
ALTER TABLE safe_assess_org_assess_dept_index MODIFY COLUMN `illustrate` varchar(1024) DEFAULT NULL COMMENT '评分说明';
ALTER TABLE safe_assess_org_assess_dept_index MODIFY COLUMN `org_illustrate` varchar(1024) DEFAULT NULL COMMENT '自评说明';
ALTER TABLE safe_assess_org_assess_dept_index MODIFY COLUMN `minus_cause` varchar(1024) DEFAULT NULL COMMENT '减分原因';
ALTER TABLE safe_assess_org_assess_dept_index MODIFY COLUMN `measure` varchar(1024) DEFAULT NULL COMMENT '整改措施';


ALTER TABLE safe_assess_org_assess_review_index MODIFY COLUMN `standard` varchar(1024) DEFAULT NULL COMMENT '评分标准';
ALTER TABLE safe_assess_org_assess_review_index MODIFY COLUMN `illustrate` varchar(1024) DEFAULT NULL COMMENT '评分说明';
ALTER TABLE safe_assess_org_assess_review_index MODIFY COLUMN `org_illustrate` varchar(1024) DEFAULT NULL COMMENT '自评说明';
ALTER TABLE safe_assess_org_assess_review_index MODIFY COLUMN `minus_cause` varchar(1024) DEFAULT NULL COMMENT '减分原因';
ALTER TABLE safe_assess_org_assess_review_index MODIFY COLUMN `measure` varchar(1024) DEFAULT NULL COMMENT '整改措施';