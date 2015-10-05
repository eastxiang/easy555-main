-- 如果复制到mysql中执行时 加上
-- DELIMITER ;

-- 组织机构、用户、角色
drop table if exists sys_organization;
drop table if exists sys_user;
drop table if exists sys_role;

-- 应用、资源、菜单
drop table if exists sys_application;
drop table if exists sys_resource;
drop table if exists sys_menu;

-- 角色资源权限、用户角色列表、用户授权信息
drop table if exists sys_role_resource_permission;
drop table if exists sys_user_role;
drop table if exists sys_user_permission;

-- 用户登录相关信息
drop table if exists sys_user_status_history;
drop table if exists sys_user_online;
drop table if exists sys_user_last_online;

create table sys_organization(
  id         				bigint not null auto_increment,
  name      				varchar(100),
  organ_type				varchar(20),
  parent_id 				bigint,
  parent_ids  				varchar(200) default '',
  icon       				varchar(200),
  weight    				int,
  status	       			bool,
  constraint pk_sys_organization primary key(id),
  index idx_sys_organization_name (name),
  index idx_sys_organization_parent_id (parent_id),
  index idx_sys_organization_parent_ids_weight (parent_ids, weight)
) charset=utf8 ENGINE=InnoDB;
alter table sys_organization auto_increment=1000;

create table sys_user(
  id         				bigint not null auto_increment,
  username  				varchar(100),
  email  					varchar(100),
  mobile_phone_number  		varchar(20),
  password  				varchar(100),
  salt       				varchar(10),
  create_date 				timestamp default 0,
  status    				varchar(50),
  deleted   				bool,
  admin     				bool,
  constraint  pk_sys_user primary key(id),
  constraint unique_sys_user_username unique(username),
  constraint unique_sys_user_email unique(email),
  constraint unique_sys_user_mobile_phone_number unique(mobile_phone_number),
  index idx_sys_user_status (status)
) charset=utf8 ENGINE=InnoDB;
alter table sys_user auto_increment=1000;

create table sys_role(
  id         				bigint not null auto_increment,
  name      				varchar(100),
  role  					varchar(100),
  description      			varchar(200),
  status      				bool,
  constraint pk_sys_role primary key(id),
  index idx_sys_role_name (name),
  index idx_sys_role_role (role),
  index idx_sys_role_show (visible)
) charset=utf8 ENGINE=InnoDB;
alter table sys_role auto_increment=1000;

create table sys_application(
  id         				bigint not null auto_increment,
  code      				varchar(100),
  name  					varchar(100),
  description      			varchar(200),
  status      				bool,
  constraint pk_sys_application primary key(id),
  index idx_sys_application_code (code),
) charset=utf8 ENGINE=InnoDB;
alter table sys_role auto_increment=1000;

create table sys_resource(
  id						bigint not null auto_increment,
  application_id			bigint not null,
  name      				varchar(100),
  identity  				varchar(100),
  url      					varchar(200),
  icon       				varchar(200),
  permission_val 			int,
  weight    				int,
  status	       			bool,
  constraint pk_sys_resource primary key(id),
  index idx_sys_resource_name (name),
  index idx_sys_resource_application_id (application_id),
) charset=utf8 ENGINE=InnoDB;
alter table sys_resource auto_increment=1000;

create table sys_menu(
  id         				bigint not null auto_increment,
  application_id			bigint not null,
  name      				varchar(100),
  resource_id				bigint,
  menuType					bool,
  parent_id 				bigint,
  parent_ids  				varchar(200) default '',
  icon       				varchar(200),
  weight    				int,
  status	       			bool,
  constraint pk_sys_menu primary key(id),
  index idx_sys_menu_application_id (application_id),
  index idx_sys_menu_parent_id (parent_id),
  index idx_sys_menu_parent_ids_weight (parent_ids, weight)
) charset=utf8 ENGINE=InnoDB;
alter table sys_menu auto_increment=1000;


create table sys_user_role(
  user_id					bigint not null,
  role_id					bigint not null,
  constraint pk_user_role primary key(user_id, role_id)
) charset=utf8 ENGINE=InnoDB;


create table sys_role_resource_permission(
  role_id   				bigint,
  resource_id 				bigint,
  permission_val 			int,
  constraint pk_sys_role_resource_permission primary key(id),
  constraint unique_sys_role_resource_permission unique(role_id, resource_id)
) charset=utf8 ENGINE=InnoDB;
alter table sys_role_resource_permission auto_increment=1000;

create table sys_user_permission(
  id         				bigint not null auto_increment,
  user_id        			bigint,
  resource_id 				bigint,
  sum_role_perm_val			int,
  user_add_perm_val			int,
  user_sub_perm_val			int
  constraint pk_sys_auth primary key(id),
  index idx_sys_auth_user (user_id),
 ) charset=utf8 ENGINE=InnoDB;
alter table sys_auth auto_increment=1000;


create table sys_user_status_history(
  id         				bigint not null auto_increment,
  user_id    				bigint,
  status    				varchar(50),
  reason     				varchar(200),
  op_user_id  			bigint,
  op_date    				timestamp default 0 ,
  constraint pk_sys_user_block_history primary key(id),
  index idx_sys_user_block_history_user_id_block_date (user_id,op_date),
  index idx_sys_user_block_history_op_user_id_op_date (op_user_id, op_date)
) charset=utf8 ENGINE=InnoDB;


create table sys_user_online(
  id         				varchar(100) not null,
  user_id    				bigint default 0,
  username    				varchar(100),
  host  					varchar(100),
  system_host  				varchar(100),
  user_agent 				varchar(200),
  status  					varchar(50),
  start_timestsamp    		timestamp default 0 ,
  last_access_time			timestamp default 0 ,
  timeout    				bigint ,
  session 					mediumtext,
  constraint pk_sys_user_online primary key(id),
  index idx_sys_user_online_sys_user_id (user_id),
  index idx_sys_user_online_username (username),
  index idx_sys_user_online_host (host),
  index idx_sys_user_online_system_host (system_host),
  index idx_sys_user_online_start_timestsamp (start_timestsamp),
  index idx_sys_user_online_last_access_time (last_access_time),
  index idx_sys_user_online_user_agent (user_agent)
) charset=utf8 ENGINE=InnoDB;


create table sys_user_last_online(
  id         				bigint not null auto_increment,
  user_id					bigint,
  username					varchar(100),
  uid						varchar(100),
  host						varchar(100),
  user_agent 				varchar(200),
  system_host  				varchar(100),
  last_login_timestamp  	timestamp default 0 ,
  last_stop_timestamp   	timestamp default 0 ,
  login_count    			bigint ,
  total_online_time 		bigint,
  constraint pk_sys_user_last_online primary key(id),
  constraint unique_sys_user_last_online_sys_user_id unique(user_id),
  index idx_sys_user_last_online_username (username),
  index idx_sys_user_last_online_host (host),
  index idx_sys_user_last_online_system_host (system_host),
  index idx_sys_user_last_online_last_login_timestamp (last_login_timestamp),
  index idx_sys_user_last_online_last_stop_timestamp (last_stop_timestamp),
  index idx_sys_user_last_online_user_agent (user_agent)
) charset=utf8 ENGINE=InnoDB; 
