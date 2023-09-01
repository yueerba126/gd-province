alter table org_user add column gds_unified_identity_user_id varchar(30) DEFAULT NULL COMMENT '广东省统一身份认证用户ID';
alter table org_user add unique index unx_gds_unified_identity_user_id(gds_unified_identity_user_id) COMMENT '广东省统一身份认证用户ID唯一索引';
