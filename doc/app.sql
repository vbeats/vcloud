/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : 127.0.0.1:3306
 Source Schema         : app

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 26/08/2022 16:30:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`
(
    `id`          bigint UNSIGNED                                              NOT NULL,
    `tenant_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户编号',
    `account`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号',
    `username`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户名 (仅作展示用)',
    `password`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '密码',
    `phone`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '手机号',
    `status`      tinyint(1)                                                   NOT NULL DEFAULT 1 COMMENT '状态 0 禁用  1 正常',
    `role_id`     bigint UNSIGNED                                              NULL COMMENT '角色id',
    `create_time` datetime(3)                                                  NOT NULL DEFAULT NOW(3),
    `update_time` datetime(3)                                                  NULL     DEFAULT NULL,
    `delete_time` datetime(3)                                                  NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '管理员'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin`
VALUES (1, '00000000', 'admin', '超级管理员', md5('123456'), '18615262691', 1, 1, now(3), NULL, NULL);

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`
(
    `id`          bigint UNSIGNED                                                NOT NULL,
    `pid`         bigint                                                         NOT NULL DEFAULT 0 COMMENT '上级  默认0',
    `title`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL COMMENT '菜单/按钮名称',
    `path`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL DEFAULT '' COMMENT '前端路由',
    `icon`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL DEFAULT '' COMMENT 'icon图标',
    `key`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL DEFAULT '' COMMENT '唯一key标识',
    `action`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL DEFAULT '' COMMENT '权限字段',
    `type`        tinyint(1)                                                     NOT NULL COMMENT '类型 0 菜单 1 按钮',
    `sort`        int                                                            NOT NULL DEFAULT 0 COMMENT '顺序 ',
    `remark`      varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
    `create_time` datetime(3)                                                    NOT NULL DEFAULT NOW(3),
    `update_time` datetime(3)                                                    NULL     DEFAULT NULL,
    `delete_time` datetime(3)                                                    NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu`
VALUES (1, 0, '系统管理', '/system', 'Setting', 'setting', '', 0, 99999, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (2, 1, '租户管理', '/tenant', 'Operation', 'operation', '', 0, 1, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (3, 1, '菜单管理', '/menu', 'Menu', 'menu', '', 0, 2, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (4, 1, '角色管理', '/role', 'Avatar', 'avatar', '', 0, 3, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (5, 1, '用户管理', '/user', 'User', 'user', '', 0, 5, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (6, 1, '开放平台', '/open', 'Magnet', 'magnet', '', 0, 6, '', now(3), NULL, NULL);

INSERT INTO `menu`
VALUES (10, 2, '新增', '', '', '', 'tenant.add', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (11, 2, '编辑', '', '', '', 'tenant.edit', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (12, 2, '删除', '', '', '', 'tenant.del', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (13, 2, '禁用', '', '', '', 'tenant.block', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (14, 2, '解封', '', '', '', 'tenant.unblock', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (15, 2, '新增子级', '', '', '', 'tenant.addsub', 1, 0, '', now(3), NULL, NULL);

INSERT INTO `menu`
VALUES (16, 3, '新增', '', '', '', 'menu.add', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (17, 3, '编辑', '', '', '', 'menu.edit', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (18, 3, '删除', '', '', '', 'menu.del', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (19, 3, '新增子项', '', '', '', 'menu.addsub', 1, 0, '', now(3), NULL, NULL);

INSERT INTO `menu`
VALUES (20, 4, '新增', '', '', '', 'role.add', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (21, 4, '编辑', '', '', '', 'role.edit', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (22, 4, '删除', '', '', '', 'role.del', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (23, 4, '菜单配置', '', '', '', 'role.menu', 1, 0, '', now(3), NULL, NULL);

INSERT INTO `menu`
VALUES (24, 5, '新增', '', '', '', 'user.add', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (25, 5, '编辑', '', '', '', 'user.edit', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (26, 5, '删除', '', '', '', 'user.del', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (27, 5, '禁用', '', '', '', 'user.block', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (28, 5, '密码重置', '', '', '', 'user.resetpwd', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (29, 5, '账号解封', '', '', '', 'user.unblock', 1, 0, '', now(3), NULL, NULL);

INSERT INTO `menu`
VALUES (30, 6, '新增', '', '', '', 'open.add', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (31, 6, '编辑', '', '', '', 'open.edit', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (32, 6, '删除', '', '', '', 'open.del', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (33, 6, '授权应用-查看', '', '', '', 'open_item.list', 1, 0, '', now(3), NULL, NULL);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`
(
    `id`          bigint UNSIGNED                                                NOT NULL,
    `role_name`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL COMMENT '角色名称',
    `action`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL COMMENT '权限字段',
    `remark`      varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
    `create_time` datetime(3)                                                    NOT NULL DEFAULT NOW(3),
    `update_time` datetime(3)                                                    NULL     DEFAULT NULL,
    `delete_time` datetime(3)                                                    NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role`
VALUES (1, '超级管理员', 'super_admin', '超级管理员', now(3), NULL, NULL);

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`
(
    `id`      bigint UNSIGNED NOT NULL,
    `role_id` bigint UNSIGNED NOT NULL COMMENT '角色id',
    `menu_id` bigint UNSIGNED NOT NULL COMMENT '菜单id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色-菜单'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu`
VALUES (1, 1, 1);
INSERT INTO `role_menu`
VALUES (2, 1, 2);
INSERT INTO `role_menu`
VALUES (3, 1, 3);
INSERT INTO `role_menu`
VALUES (4, 1, 4);
INSERT INTO `role_menu`
VALUES (5, 1, 5);
INSERT INTO `role_menu`
VALUES (6, 1, 6);

INSERT INTO `role_menu`
VALUES (10, 1, 10);
INSERT INTO `role_menu`
VALUES (11, 1, 11);
INSERT INTO `role_menu`
VALUES (12, 1, 12);
INSERT INTO `role_menu`
VALUES (13, 1, 13);
INSERT INTO `role_menu`
VALUES (14, 1, 14);
INSERT INTO `role_menu`
VALUES (15, 1, 15);
INSERT INTO `role_menu`
VALUES (16, 1, 16);
INSERT INTO `role_menu`
VALUES (17, 1, 17);
INSERT INTO `role_menu`
VALUES (18, 1, 18);
INSERT INTO `role_menu`
VALUES (19, 1, 19);
INSERT INTO `role_menu`
VALUES (20, 1, 20);
INSERT INTO `role_menu`
VALUES (21, 1, 21);
INSERT INTO `role_menu`
VALUES (22, 1, 22);
INSERT INTO `role_menu`
VALUES (23, 1, 23);
INSERT INTO `role_menu`
VALUES (24, 1, 24);
INSERT INTO `role_menu`
VALUES (25, 1, 25);
INSERT INTO `role_menu`
VALUES (26, 1, 26);
INSERT INTO `role_menu`
VALUES (27, 1, 27);
INSERT INTO `role_menu`
VALUES (28, 1, 28);
INSERT INTO `role_menu`
VALUES (29, 1, 29);
INSERT INTO `role_menu`
VALUES (30, 1, 30);
INSERT INTO `role_menu`
VALUES (31, 1, 31);
INSERT INTO `role_menu`
VALUES (32, 1, 32);
INSERT INTO `role_menu`
VALUES (33, 1, 33);

-- ----------------------------
-- Table structure for tenant
-- ----------------------------
DROP TABLE IF EXISTS `tenant`;
CREATE TABLE `tenant`
(
    `id`          bigint UNSIGNED                                                NOT NULL,
    `pid`         bigint UNSIGNED                                                NOT NULL DEFAULT 0 COMMENT '上级id 默认0',
    `code`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL COMMENT '租户编号',
    `tenant_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '租户名称',
    `status`      tinyint(1)                                                     NOT NULL COMMENT '状态 0 禁用  1正常',
    `remark`      varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
    `create_time` datetime(3)                                                    NOT NULL DEFAULT NOW(3),
    `update_time` datetime(3)                                                    NULL     DEFAULT NULL,
    `delete_time` datetime(3)                                                    NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '租户'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tenant
-- ----------------------------
INSERT INTO `tenant`
VALUES (1, 0, '00000000', '运营平台', 1, '运营平台', now(3), NULL, NULL);

-- ----------------------------
-- Table structure for open_config
-- ----------------------------
DROP TABLE IF EXISTS `open_config`;
CREATE TABLE `open_config`
(
    `id`          bigint UNSIGNED                                               NOT NULL,
    `pid`         bigint UNSIGNED                                               NOT NULL DEFAULT 0 COMMENT '上级id 默认0',
    `tenant_id`   bigint UNSIGNED                                               NOT NULL DEFAULT 0 COMMENT '所属租户  默认0 未分配',
    `name`        varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '名称',
    `type`        tinyint UNSIGNED                                              NOT NULL COMMENT '开放平台类型',
    `config`      json                                                          NOT NULL COMMENT '参数配置',
    `status`      tinyint UNSIGNED                                              NOT NULL DEFAULT 0 COMMENT '是否授权给pid第三方平台 0未授权 1已授权',
    `create_time` datetime(3)                                                   NOT NULL DEFAULT NOW(3),
    `update_time` datetime(3)                                                   NULL     DEFAULT NULL,
    `delete_time` datetime(3)                                                   NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '第三方开放平台'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`          bigint UNSIGNED NOT NULL,
    `tenant_id`   bigint UNSIGNED NOT NULL COMMENT '所属租户id',
    `create_time` datetime(3)     NOT NULL DEFAULT NOW(3),
    `update_time` datetime(3)     NULL     DEFAULT NULL,
    `delete_time` datetime(3)     NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户会员'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_wx_ma
-- ----------------------------
DROP TABLE IF EXISTS `user_wx_ma`;
CREATE TABLE `user_wx_ma`
(
    `id`             bigint UNSIGNED                                              NOT NULL,
    `user_id`        bigint UNSIGNED                                              NOT NULL,
    `open_config_id` bigint UNSIGNED                                              NOT NULL,
    `openid`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
    `unionid`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
    `phone`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
    `create_time`    datetime(3)                                                  NOT NULL DEFAULT NOW(3),
    `update_time`    datetime(3)                                                  NULL     DEFAULT NULL,
    `delete_time`    datetime(3)                                                  NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = 'user-微信小程序'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_wx_MP
-- ----------------------------
DROP TABLE IF EXISTS `user_wx_mp`;
CREATE TABLE `user_wx_mp`
(
    `id`             bigint UNSIGNED                                              NOT NULL,
    `user_id`        bigint UNSIGNED                                              NOT NULL,
    `open_config_id` bigint UNSIGNED                                              NOT NULL,
    `openid`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
    `unionid`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
    `phone`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
    `create_time`    datetime(3)                                                  NOT NULL DEFAULT NOW(3),
    `update_time`    datetime(3)                                                  NULL     DEFAULT NULL,
    `delete_time`    datetime(3)                                                  NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = 'user-微信公众号'
  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
