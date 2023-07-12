create database if not exists apihub;

use apihub;


create table if not exists api
(
    id          int primary key auto_increment,
    name        varchar(255) not null unique comment 'api 名称(全局唯一)',
    name_zh     varchar(255) comment 'api 中文名称',
    ds_id       int          not null comment '数据源 id',
    query       text         not null comment '接口查询 sql',
    description text comment '数据源描述信息',
    create_time datetime default now() comment '创建时间',
    update_time datetime default now() comment '修改时间'
) comment '接口信息表';
