drop table if exists `ebook`;
create table `ebook`
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
  default charset = utf8mb4 comment ='电子书';

insert into `ebook` (id, name, description)
values (1, 'Spring Boot', 'come on Spring ebook');
insert into `ebook` (id, name, description)
values (2, 'Vue Boot', 'come on Vue ebook');
insert into `ebook` (id, name, description)
values (3, 'React Boot', 'come on React ebook');
insert into `ebook` (id, name, description)
values (4, 'Angular Boot', 'come on Angular ebook');



drop table if exists `test1`;

create table test1
(
    id   bigint      not null comment 'id'
        primary key,
    name varchar(50) null comment '名称'
);

### 分类
drop table if exists `category`;
create table `category`
(
    `id`     bigint      not null comment 'id',
    `parent` bigint      not null default 0 comment '父id',
    `name`   varchar(50) not null comment '名称',
    `sort`   int comment '顺序',
    primary key (`id`)
) engine = innodb
  default charset = utf8mb4 comment ='分类';

insert into `category` (id, parent,name,sort) values (100,000,'前段开发',100);
insert into `category` (id, parent,name,sort) values (101,100,'后端开发',101);
insert into `category` (id, parent,name,sort) values (102,100,'狗开发',102);
insert into `category` (id, parent,name,sort) values (200,000,'JAVA开发',200);
insert into `category` (id, parent,name,sort) values (201,200,'HTML5开发',201);
insert into `category` (id, parent,name,sort) values (202,200,'VUE开发',202);