# DROP DATABASE IF EXISTS `xy-cloud1`;
#
# CREATE DATABASE  `xy-cloud1` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

# SET NAMES utf8mb4;
# SET FOREIGN_KEY_CHECKS = 0;
#
# USE `xy-cloud1`;

SET NAMES utf8mb4;

-- ----------------------------
-- 1、部门信息表
-- ----------------------------
drop table if exists sys_dept;
create table sys_dept (
  id                        bigint              not null                                comment '部门id',
  parent_id                 bigint              default 0                               comment '父部门id',
  code                      varchar(64)         default null                            comment '部门编码',
  name                      varchar(30)         default ''                              comment '部门名称',
  level                     int                 not null                                comment '树层级',
  ancestors                 varchar(500)        default ''                              comment '祖级列表',
  leader                    varchar(20)         default ''                              comment '负责人',
  phone                     varchar(11)         default ''                              comment '联系电话',
  email                     varchar(50)         default ''                              comment '邮箱',
  sort                      int unsigned        not null default 0                      comment '显示顺序',
  status                    char(1)             not null default '0'                    comment '状态（0正常 1停用）',
  remark                    varchar(200)        default null                            comment '备注',
  create_by                 bigint              default null                            comment '创建者',
  create_time               datetime            default current_timestamp               comment '创建时间',
  update_by                 bigint              default null                            comment '更新者',
  update_time               datetime            on update current_timestamp             comment '更新时间',
  del_flag		            tinyint             not null default 0                      comment '删除标志(0正常 1删除)',
  tenant_id		            bigint	            not null                                comment '租户Id',
  primary key (id)
) engine = innodb comment = '部门信息表';

-- ----------------------------
-- 初始化-部门表数据
-- ----------------------------
insert into sys_dept (id, code, parent_id, level, ancestors, name, tenant_id)
values (99, '99', 0, 1, '0', '雪忆科技', 1),
       (100, '100', 0, 1, '0', '雪忆科技', 2),
       (101, '101', 100, 2, '0,100', '深圳总公司', 2),
       (102, '102',  100, 2, '0,100', '长沙分公司', 2),
       (103, '103',  101, 3, '0,100,101', '研发部门', 2),
       (104, '104',  101, 3, '0,100,101', '市场部门', 2),
       (105, '105',  101, 3, '0,100,101', '测试部门', 2),
       (106, '106',  101, 3, '0,100,101', '财务部门', 2),
       (107, '107',  101, 3, '0,100,101', '运维部门', 2),
       (108, '108',  102, 3, '0,100,102', '市场部门', 2),
       (109, '109',  102, 3, '0,100,102', '财务部门', 2);

-- ----------------------------
-- 2、岗位信息表
-- ----------------------------
drop table if exists sys_post;
create table sys_post (
  id                        bigint              not null                                comment '岗位Id',
  dept_id		            bigint	            not null                                comment '部门Id',
  code                      varchar(64)         default null                            comment '岗位编码',
  name                      varchar(50)         not null                                comment '岗位名称',
  sort                      int unsigned        not null default 0                      comment '显示顺序',
  status                    char(1)             not null default '0'                    comment '状态（0正常 1停用）',
  remark                    varchar(200)        default null                            comment '备注',
  create_by                 bigint              default null                            comment '创建者',
  create_time               datetime            default current_timestamp               comment '创建时间',
  update_by                 bigint              default null                            comment '更新者',
  update_time               datetime            on update current_timestamp             comment '更新时间',
  del_flag		            tinyint             not null default 0                      comment '删除标志(0正常 1删除)',
  tenant_id		            bigint	            not null                                comment '租户Id',
  primary key (id)
) engine = innodb comment = '岗位信息表';

-- ----------------------------
-- 初始化-岗位信息表数据
-- ----------------------------
insert into sys_post (id, dept_id, code, name, tenant_id)
values (1, 99, 'ceo', '超级管理员', 1),
       (2, 100, 'ceo', '董事长', 2),
       (3, 100, 'se', '项目经理', 2),
       (4, 100, 'hr', '人力资源', 2),
       (5, 100, 'user', '普通员工', 2);

-- ----------------------------
-- 3、用户信息表
-- ----------------------------
drop table if exists sys_user;
create table sys_user (
  id                        bigint	            not null                                comment '用户Id',
  code                      varchar(64)         default null                            comment '用户编码',
  user_name                 varchar(30)         not null                                comment '用户账号',
  nick_name                 varchar(30)         not null                                comment '用户昵称',
  user_type                 varchar(2)          default '01'                            comment '用户类型（00超管用户 01普通用户）',
  phone                     varchar(11)         default ''                              comment '手机号码',
  email                     varchar(50)         default ''                              comment '用户邮箱',
  sex                       char(1)             default '2'                             comment '用户性别（0男 1女 2保密）',
  avatar                    varchar(100)        default ''                              comment '头像地址',
  profile                   varchar(100)        default '这个人很懒，暂未留下什么'          comment '个人简介',
  password                  varchar(100)        default ''                              comment '密码',
  login_ip                  varchar(128)        default ''                              comment '最后登录IP',
  login_date                datetime                                                    comment '最后登录时间',
  sort                      int unsigned        not null default 0                      comment '显示顺序',
  status                    char(1)             not null default '0'                    comment '状态（0正常 1停用）',
  remark                    varchar(200)        default null                            comment '备注',
  create_by                 bigint              default null                            comment '创建者',
  create_time               datetime            default current_timestamp               comment '创建时间',
  update_by                 bigint              default null                            comment '更新者',
  update_time               datetime            on update current_timestamp             comment '更新时间',
  del_flag		            tinyint             not null default 0                      comment '删除标志(0正常 1删除)',
  tenant_id		            bigint	            not null                                comment '租户Id',
  primary key (id)
) engine = innodb comment = '用户信息表';

-- ----------------------------
-- 初始化-用户信息表数据
-- ----------------------------
insert into sys_user (id, code, user_name, nick_name,user_type, avatar, password, remark, tenant_id)
values (1, '001', 'admin', 'admin', '00', 'https://images.gitee.com/uploads/images/2021/1101/141155_f3dfce1d_7382127.jpeg', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '超级管理员', 1),
       (2, '001', 'admin', 'admin', '00', 'https://images.gitee.com/uploads/images/2021/1101/141155_f3dfce1d_7382127.jpeg', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '系统管理员', 2),
       (3, '002', 'xy', 'xy', '01', 'https://images.gitee.com/uploads/images/2021/1101/141155_f3dfce1d_7382127.jpeg', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '管理员', 2);

-- ----------------------------
-- 4、用户-岗位关联表
-- ----------------------------
drop table if exists sys_user_post_merge;
create table sys_user_post_merge (
  id                        bigint	            not null                                comment 'id',
  user_id                   bigint	            not null                                comment '用户Id',
  post_id		            bigint	            not null                                comment '职位Id',
  tenant_id		            bigint	            not null                                comment '租户Id',
  primary key (id),
  unique (user_id, post_id)
) engine = innodb comment = '用户-岗位关联表';

-- ----------------------------
-- 初始化-用户-岗位关联表数据
-- ----------------------------
insert into sys_user_post_merge (id, user_id, post_id, tenant_id)
values (1, 1, 1, 1),
       (2, 2, 2, 2),
       (3, 3, 3, 2);

-- ----------------------------
-- 5、角色信息表
-- ----------------------------
drop table if exists sys_role;
create table sys_role (
  id                        bigint	            not null                                comment '角色Id',
  code                      varchar(64)         default null                            comment '角色编码',
  name                      varchar(30)         not null                                comment '角色名称',
  role_key                  varchar(100)        default null                            comment '角色权限字符串',
  data_scope                char(1)             default '1'                             comment '数据范围（1全部数据权限 2自定数据权限 3本部门数据权限 4本部门及以下数据权限 5本岗位数据权限  6仅本人数据权限）',
  sort                      int unsigned        not null default 0                      comment '显示顺序',
  status                    char(1)             not null default '0'                    comment '状态（0正常 1停用）',
  remark                    varchar(200)        default null                            comment '备注',
  create_by                 bigint              default null                            comment '创建者',
  create_time               datetime            default current_timestamp               comment '创建时间',
  update_by                 bigint              default null                            comment '更新者',
  update_time               datetime            on update current_timestamp             comment '更新时间',
  del_flag		            tinyint             not null default 0                      comment '删除标志（0正常 1删除）',
  tenant_id		            bigint	            not null                                comment '租户Id',
  primary key (id)
) engine = innodb comment = '角色信息表';

-- ----------------------------
-- 初始化-角色信息表数据
-- ----------------------------
insert into sys_role (id, tenant_id, code, name, role_key, remark)
values (1, 1, '001', '超级管理员', 'admin', '超级管理员'),
       (2, 1, '002', '管理员', 'common', '普通角色');

-- ----------------------------
-- 8、角色和模块关联表
-- ----------------------------
drop table if exists sys_role_module_merge;
create table sys_role_module_merge (
  id                        bigint              not null                                comment 'id',
  role_id                   bigint              not null                                comment '角色Id',
  module_id                 bigint              not null                                comment '模块Id',
  tenant_id		            bigint	            not null                                comment '租户Id',
  primary key (id),
  unique (role_id, module_id)
) engine = innodb comment = '角色和模块关联表';

-- ----------------------------
-- 9、角色和菜单关联表
-- ----------------------------
drop table if exists sys_role_menu_merge;
create table sys_role_menu_merge (
  id                        bigint              not null                                comment 'id',
  role_id                   bigint              not null                                comment '角色Id',
  menu_id                   bigint              not null                                comment '菜单Id',
  tenant_id		            bigint	            not null                                comment '租户Id',
  primary key (id),
  unique (role_id, menu_id)
) engine = innodb comment = '角色和菜单关联表';

-- ----------------------------
-- 10、角色和部门关联表（权限范围）
-- ----------------------------
drop table if exists sys_role_dept_merge;
create table sys_role_dept_merge (
  id                        bigint              not null                                comment 'id',
  role_id                   bigint              not null                                comment '角色Id',
  dept_id                   bigint              not null                                comment '部门Id',
  tenant_id		            bigint	            not null                                comment '租户Id',
  primary key(id),
  unique (role_id, dept_id)
) engine = innodb comment = '角色和部门-岗位关联表';

-- ----------------------------
-- 11、角色和岗位关联表（权限范围）
-- ----------------------------
drop table if exists sys_role_post_merge;
create table sys_role_post_merge (
  id                        bigint              not null                                comment 'id',
  role_id                   bigint              not null                                comment '角色Id',
  post_id                   bigint              not null                                comment '岗位Id',
  tenant_id		            bigint	            not null                                comment '租户Id',
  primary key(id),
  unique (role_id, post_id)
) engine = innodb comment = '角色和部门-岗位关联表';

-- ----------------------------
-- 12、组织和角色关联表（角色绑定）
-- ----------------------------
drop table if exists sys_organize_role_merge;
create table sys_organize_role_merge (
  id		                bigint	            not null auto_increment                 comment 'id',
  dept_id                   bigint              default null                            comment '部门id',
  post_id                   bigint              default null                            comment '岗位id',
  user_id                   bigint              default null                            comment '用户id',
  role_id                   bigint              not null                                comment '角色Id',
  tenant_id		            bigint	            not null                                comment '租户Id',
  primary key(id),
  unique (dept_id, post_id, user_id, role_id)
) engine = innodb auto_increment=1 comment = '组织和角色关联表';

-- ----------------------------
-- 15、操作日志记录
-- ----------------------------
drop table if exists sys_operate_log;
create table sys_operate_log (
  id                        bigint              not null auto_increment                 comment '日志主键',
  title                     varchar(50)         default ''                              comment '模块标题',
  business_type             char(2)             default '00'                            comment '业务类型（0其它 1新增 2修改 3删除）',
  method                    varchar(100)        default ''                              comment '方法名称',
  request_method            varchar(10)         default ''                              comment '请求方式',
  operate_type              char(2)             default '00'                            comment '操作类别（00其它 01后台 02手机端）',
  user_id                   bigint              not null default -2                     comment '操作人员',
  user_name                 varchar(50)         default null                            comment '操作人员账号',
  user_nick                 varchar(50)         default null                            comment '操作人员名称',
  url                       varchar(255)        default ''                              comment '请求URL',
  ip                        varchar(128)        default ''                              comment '主机地址',
  param                     varchar(2000)       default ''                              comment '请求参数',
  location                  varchar(255)        default ''                              comment '操作地点',
  json_result               varchar(2000)       default ''                              comment '返回参数',
  status                    char(1)             default 0                               comment '操作状态（0正常 1异常）',
  error_msg                 varchar(2000)       default ''                              comment '错误消息',
  cost_time                 bigint              default 0                               comment '消耗时间',
  operate_time              datetime            default current_timestamp               comment '操作时间',
  del_time                  datetime            on update current_timestamp             comment '删除时间',
  del_flag		            tinyint             not null default 0                      comment '删除标志(0正常 1删除)',
  tenant_id		            bigint	            not null                                comment '租户Id',
  primary key (id),
  key idx_sys_operate_log_bt (business_type),
  key idx_sys_operate_log_s  (status),
  key idx_sys_operate_log_ot (operate_time)
) engine = innodb auto_increment=100 comment = '操作日志记录';

-- ----------------------------
-- 16、系统访问记录
-- ----------------------------
drop table if exists sys_login_log;
create table sys_login_log (
  id                        bigint              not null auto_increment                 comment '访问Id',
  enterprise_name           varchar(50)         default ''                              comment '企业账号',
  user_id                   bigint              not null default -2                     comment '用户Id',
  user_name                 varchar(50)         default ''                              comment '用户账号',
  user_nick                 varchar(50)         default ''                              comment '用户名称',
  ipaddr                    varchar(128)        default ''                              comment '登录IP地址',
  status                    char(1)             default '0'                             comment '登录状态（0成功 1失败）',
  msg                       varchar(255)        default ''                              comment '提示信息',
  access_time               datetime            default current_timestamp               comment '访问时间',
  del_time                  datetime            on update current_timestamp             comment '删除时间',
  del_flag		            tinyint             not null default 0                      comment '删除标志(0正常 1删除)',
  tenant_id		            bigint	            not null                                comment '租户Id',
  primary key (id),
  key idx_sys_login_log_s  (status),
  key idx_sys_login_log_lt (access_time)
) engine = innodb auto_increment=100 comment = '系统访问记录';

-- ----------------------------
-- 17、通知公告表
-- ----------------------------
drop table if exists sys_notice;
create table sys_notice (
  id                        bigint              not null                                comment '公告Id',
  name                      varchar(50)         not null                                comment '公告标题',
  type                      char(1)             not null default '0'                    comment '公告类型（0通知 1公告）',
  content                   longblob            default null                            comment '公告内容',
  status                    char(1)             default '0'                             comment '公告状态（0待发送 1已发送 2已关闭 3发送失败 4发送异常）',
  remark                    varchar(200)        default null                            comment '备注',
  create_by                 bigint              default null                            comment '创建者',
  create_time               datetime            default current_timestamp               comment '创建时间',
  update_by                 bigint              default null                            comment '更新者',
  update_time               datetime            on update current_timestamp             comment '更新时间',
  del_flag		            tinyint             not null default 0                      comment '删除标志(0正常 1删除)',
  tenant_id		            bigint	            not null                                comment '租户Id',
  primary key (id)
) engine = innodb comment = '通知公告表';

-- ----------------------------
-- 18、通知公告记录表
-- ----------------------------
drop table if exists sys_notice_log;
create table sys_notice_log (
  id                        bigint              not null                                comment 'id',
  notice_id                 bigint              not null                                comment '公告Id',
  user_id                   bigint              not null                                comment '用户Id',
  receive_status            char(1)             not null                                comment '发送状态（0成功 1失败）',
  status                    char(1)             default '0'                             comment '阅读状态（0未读 1已读）',
  remark                    varchar(200)        default null                            comment '备注',
  create_time               datetime            default current_timestamp               comment '创建时间',
  del_flag		            tinyint             not null default 0                      comment '删除标志(0正常 1删除)',
  tenant_id		            bigint	            not null                                comment '租户Id',
  primary key (id)
) engine = innodb comment = '通知公告记录表';

-- ----------------------------
-- 19、定时任务调度日志表
-- ----------------------------
drop table if exists sys_job_log;
create table sys_job_log (
  id                        bigint              not null auto_increment                 comment '任务日志Id',
  job_id                    bigint              not null                                comment '任务Id',
  name                      varchar(64)         not null                                comment '任务名称',
  job_group                 varchar(64)         not null                                comment '任务组名',
  invoke_target             varchar(500)        not null                                comment '调用目标字符串',
  invoke_tenant             varchar(500)        not null                                comment '调用租户字符串',
  job_message               varchar(500)                                                comment '日志信息',
  status                    char(1)             not null default '0'                    comment '执行状态（0正常 1失败）',
  exception_info            varchar(2000)       default ''                              comment '异常信息',
  create_time               datetime            default current_timestamp               comment '创建时间',
  del_time                  datetime            on update current_timestamp             comment '删除时间',
  del_flag		            tinyint             not null default 0                      comment '删除标志(0正常 1删除)',
  tenant_id		            bigint	            not null                                comment '租户Id',
  primary key (id)
) engine = innodb comment = '定时任务调度日志表';
