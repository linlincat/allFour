drop table if exists `dest1`;
create table `destlin`
(
    `id`           bigint not null comment 'id',
    `name`         varchar(50) comment '名称',
    `category1_id` bigint comment '分类1',
    `category2_id` bigint comment '分类2',
    `description`  varchar(200) comment '描述',
    `cover`        varchar(200) comment '封面',
    `doc_count`    int comment '文档数',
    `view_count`   int comment '阅读数',
    `vote_count`   int comment '点赞数',
    primary key (`id`)
) engine = innodb
  default charset = utf8mb4 comment ='eeee';

-- 1.用户表t_user
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT, -- 用户编号
  `username` varchar(32) DEFAULT NULL,	-- 用户名字
  `password` varchar(32) DEFAULT NULL,	-- 用户密码
  `email` varchar(32) DEFAULT NULL,		-- 用户邮箱
  `createTime` timestamp not NULL DEFAULT CURRENT_TIMESTAMP, -- 该用户创建时间
  `updateTime` timestamp not NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 该用户修改时间
  PRIMARY KEY (`id`) -- 设置主键
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# insert into t_user values
# (null,'admin','1234','admin@163.com',null,null);


ALTER TABLE `four`.`m_category`
    MODIFY COLUMN `update_time` timestamp not NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;