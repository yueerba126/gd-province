update safe_assess_template set assess_scoring_rule = 0,check_scoring_rule = 0;
alter table safe_assess_template change assess_scoring_rule assess_score_proportion decimal(5,2) NOT NULL COMMENT '考核分占比';
alter table safe_assess_template change check_scoring_rule check_score_proportion decimal(5,2) NOT NULL COMMENT '抽查分占比';