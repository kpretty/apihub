create database if not exists apihub;

use apihub;

create table if not exists ds
(
    id          int primary key auto_increment,
    name        varchar(255) not null comment '数据源名称',
    jdbc_url    varchar(255) not null comment '数据源连接信息',
    username    varchar(255) not null comment '用户名',
    password    varchar(255) not null comment '密码',
    description text comment '数据源描述信息',
    create_time datetime default now() comment '创建时间',
    update_time datetime default now() comment '修改时间'
) comment '数据源信息表';