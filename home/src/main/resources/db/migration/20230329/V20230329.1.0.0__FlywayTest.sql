/*创建表结构*/
CREATE TABLE `flyway_test`
(
    `id`             varchar(18)  NOT NULL COMMENT '主键ID(单位代码)',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='flywaydb测试表';


/*初始化数据*/
insert into flyway_test(id) values ('1');
insert into flyway_test(id) values ('2');
insert into flyway_test(id) values ('3');
insert into flyway_test(id) values ('4');
insert into flyway_test(id) values ('5');


/*删除表*/
drop table flyway_test;