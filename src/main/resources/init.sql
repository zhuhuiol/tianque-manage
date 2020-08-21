use tianque;

-- 组织表
DROP TABLE IF EXISTS `tq_team`;
CREATE TABLE `tq_team`  (
  `TEAMID` int(11) NOT NULL AUTO_INCREMENT COMMENT '组织id',
  `TEAMNAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组织名\r\n',
  `TEAMINDEX` int(255) NULL DEFAULT NULL COMMENT '排序',
  `DESCRIBE` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `CREATEDATE` datetime(0) NOT NULL COMMENT '创建时间',
  `CREATOR` int(255) NOT NULL COMMENT '创建人id',
  `CREATORNAME` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人名字',
  `UPDATETIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `VALID` int(11) NOT NULL COMMENT '是否有效',
  `SUPERID` int(11) NULL DEFAULT NULL COMMENT '上级部门',
  PRIMARY KEY (`TEAMID`) USING BTREE,
  INDEX `IDINDEX`(`TEAMID`, `TEAMINDEX`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;


-- 用户join组织表
-- tq_user_team
DROP TABLE IF EXISTS `tq_user_team`;
CREATE TABLE `tq_user_team`  (
  `USERID` int(11) NOT NULL,
  `TEAMID` int(255) NOT NULL,
  INDEX `USER_TEAM`(`USERID`, `TEAMID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;



--  用户表
select * from tq_user;
DROP TABLE IF EXISTS `tq_user`;
CREATE TABLE `tq_user`  (
  `USERID` int(11) NOT NULL AUTO_INCREMENT,
  `PASSWORD` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `BIRTHDAY` datetime(0) NULL DEFAULT NULL COMMENT '生日',
  `AGE` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `SEX` int(11) NULL DEFAULT NULL COMMENT '性别',
  `EMAIL` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `DISABLED` int(11) NULL DEFAULT NULL COMMENT '是否禁用',
  `MOBILE` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `DESCRIPTION` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人简介',
  `AVATAR` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `IDNUMBER` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号码',
  `ADDRESS` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `CREATE_TIME` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATE_TIME` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `VALID` int(11) NOT NULL,
  PRIMARY KEY (`USERID`) USING BTREE,
  UNIQUE INDEX `USERNIKE_UNIQUE`(`USERID`) USING BTREE,
  UNIQUE INDEX `USER_TEAM`(`VALID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;


 -- 初始化一个账号--
insert into tq_user(password, birthday, age,sex, email, disabled,mobile, description, idnumber, address) 
values("$2a$10$4E.3EGBmnGtu9cXLn0RWzuaf3uePbSzqVCNLK6vOJ6/JxxRfeEB9a", now(), 23,1,"sdaklf@163.com", 0, "13787720372",
"躁动起来", "430333199604189984","上海市浦东新区");

 -- delete from tq_user where userid in (2,3);
 -- 角色表
select * from tq_role;
CREATE TABLE `tq_role` (
  `ROLEID` varchar(32) NOT NULL,
  `ROLENAME` varchar(45) NOT NULL COMMENT '角色名称',
  `ROLEDESC` varchar(200) DEFAULT NULL COMMENT '角色描述',
	`ROLECODE` varchar(200) NOT NULL COMMENT 'CODE',
  PRIMARY KEY (`ROLEID`),
  UNIQUE KEY `ROLEID_UNIQUE` (`ROLEID`),
  UNIQUE KEY `ROLENAME_UNIQUE` (`ROLENAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- 初始化部分角色
insert into tq_role values("b81f82517bfc4a19a34cf371ab25215c", "超级管理员", "拥有最高权限", "ADMIN");
insert into tq_role values("5b80a08627e14caaa05509c529ca729b", "一般管理员", "拥有一定权限", "ADMIN_A");
insert into tq_role values("0e4f6efd59c8475e846a8d1002e657da", "普通用户", "只可操作自己的东西", "USER");

-- 账户表 ， 一个用户对应多个账户
select * from tq_accounts;

CREATE TABLE `tq_accounts` (
  `aid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` varchar(45) NOT NULL COMMENT '关联用户表id',
  `username` varchar(45) NOT NULL COMMENT '账户名称',
  `create_time` varchar(45) DEFAULT 'now()' COMMENT '创建时间',
  PRIMARY KEY (`aid`),
  UNIQUE KEY `aid_UNIQUE` (`aid`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='账户表，一个用户多个账户';

-- 初始化用户账户信息

insert into tq_accounts(userid, username) values((select userid from tq_user limit 0,1), "zhuhui");

 -- 角色用户关系表
select * from tq_user_role;
CREATE TABLE `tq_user_role` (
  `urid` int(11) NOT NULL AUTO_INCREMENT COMMENT '关系id',
  `userid` int(11) NOT NULL COMMENT '用户id',
  `roleid` varchar(32) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`urid`),
  UNIQUE KEY `urid_UNIQUE` (`urid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';


insert into tq_user_role(userid, roleid) values((select userid from tq_user limit 0,1), "b81f82517bfc4a19a34cf371ab25215c");

 -- 根据用户id查询所属角色 卫士通张小平：13331899059
 --  
 
 select * from tq_user_role ur left join tq_role ro on ur.roleid = ro.roleid where ur.userid = 4;
 
 -- 登录日志表
CREATE TABLE `tq_loginlog` (
  `id` varchar(32) NOT NULL,
  `ip` varchar(45) DEFAULT NULL,
  `userName` varchar(45) NOT NULL,
  `loginDate` datetime NOT NULL,
  `loginLogType` varchar(45) NOT NULL,
  `result` int(11) NOT NULL,
  `message` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `userName` (`userName`,`result`,`loginLogType`,`loginDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 select * from tq_loginlog;
 -- 操作日志表

select * from tq_operationlog;

-- 菜单 --> 操作
-- 菜单表table
DROP TABLE IF EXISTS `tq_menu`;
CREATE TABLE `tq_menu`  (
  `MENUID` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `MENUNAME` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `PARENTID` int(11) NULL DEFAULT NULL COMMENT '上级菜单id',
  `URL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单地址',
  `ICON` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `MENUINDEX` int(255) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`MENUID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

select * from tq_menu;
-- 初始化菜单数据
insert into tq_menu(menuname,PARENTID,URL,ICON,MENUINDEX) VALUES('统计分析', 0, '', '', 1);
insert into tq_menu(menuname,PARENTID,URL,ICON,MENUINDEX) VALUES('用户', 0, '', '', 2);
insert into tq_menu(menuname,PARENTID,URL,ICON,MENUINDEX) VALUES('菜单', 0, '', '', 3);
insert into tq_menu(menuname,PARENTID,URL,ICON,MENUINDEX) VALUES('test', 9, '', '', 3);
UPDATE 
-- ----角色与菜单和操作的对应关系---
-- 角色join菜单表table

-- 单个用户和菜单和操作的对应关系---
-- 用户join菜单表table

-- 菜单join操作表table

show variables like 'character_set_database';


select version();
