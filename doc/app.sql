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
CREATE TABLE `admin`  (
  `id` bigint UNSIGNED NOT NULL,
  `tenant_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户编号',
  `account` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名 (仅作展示用)',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '手机号',
  `status` tinyint(1) NOT NULL COMMENT '状态 0 禁用  1 正常',
  `role_id` bigint UNSIGNED NOT NULL COMMENT '角色id',
  `create_time` datetime(3) NOT NULL,
  `update_time` datetime(3) NULL DEFAULT NULL,
  `delete_time` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '管理员' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, '00000000', 'admin', '超级管理员', 'e10adc3949ba59abbe56e057f20f883e', '18615262691', 1, 1, '2022-08-23 10:07:04.000', NULL, NULL);

-- ----------------------------
-- Table structure for api_scope
-- ----------------------------
DROP TABLE IF EXISTS `api_scope`;
CREATE TABLE `api_scope`  (
  `id` bigint UNSIGNED NOT NULL,
  `menu_id` bigint UNSIGNED NOT NULL COMMENT '菜单id',
  `api_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '接口名称',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'api path',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备注',
  `create_time` datetime(3) NOT NULL,
  `update_time` datetime(3) NULL DEFAULT NULL,
  `delete_time` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '接口权限' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of api_scope
-- ----------------------------

-- ----------------------------
-- Table structure for auth_client
-- ----------------------------
DROP TABLE IF EXISTS `auth_client`;
CREATE TABLE `auth_client`  (
  `id` bigint UNSIGNED NOT NULL,
  `client_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'client id',
  `client_secret` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'secret',
  `grant_types` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '授权类型',
  `access_token_expire` bigint NOT NULL DEFAULT 7200 COMMENT 'access_token有效时长 (秒) 默认7200',
  `refresh_token_expire` bigint NOT NULL DEFAULT 30 COMMENT 'refresh_token有效时长 (天) 默认30',
  `private_key` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'rsa私钥',
  `public_key` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'rsa公钥',
  `remark` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备注',
  `create_time` datetime(3) NOT NULL,
  `update_time` datetime(3) NULL DEFAULT NULL,
  `delete_time` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'client参数' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of auth_client
-- ----------------------------
INSERT INTO `auth_client` VALUES (1, 'de0b55913ac7', '939da067-8899-40a0-9c21-15f0b0001add', 'wx_app,wx_mp,password,sms,refresh_token', 7200, 30, '-----BEGIN PRIVATE KEY-----\r\n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\r\nnflau0Dmk4OSv3b6D3qXBYkw6/Lx\r\n-----END PRIVATE KEY-----', '-----BEGIN PUBLIC KEY-----\r\nMIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEA9il0ZDHwNwEGLKjvCYpJ\r\nfjDrM7J2UxXwgwoe+Pgnlvg+jIrXYSe5vwLtrjqJ5M7jlmH4qbG2PYT2WlRkA5Ip\r\nzeOWYph6vAVth7CnHX+Kda/TWJD4dEEcCn+Hk8ulkCf5a0+cBnw/vyJ3+GD8z5f5\r\nZQJ3YJ0CoPepsdEKRfKQd3eSBGppXz2UmEvCFi10sQGQFOIRcdqL3tUYkJGnJSzZ\r\noDlqwWyI84mM6JE8NNhSxvZYTy9UQj9ytI3sFhB7jlGigxxVZdayohKQH/FTzi7S\r\n/w1ZDWXitnwhlV5rdIP0/SWedrvebJkdkpvjq0W2MkWo4YFSJHXYtK9pJy1lfxw8\r\nef3VKf0m86KaoufyOLA/Fljnq3rjRmaOvG9ASJ7EPYKPzSlbqE4nhDH39MBrBc3a\r\nKbindh8rWi5Gy8JtT0F81//KbG6eH3EgSIuOTIrk/iAmeLTk6uGYhj9csVhneesM\r\nJRCu3JjbRLHzbP0ikrxvbokw/5xsrQOiqFy5UgB/nn8MNyrwKpcdNKeB84b+bTQi\r\n95kqT1tlh/EtYrHW5B8WLXe+/Mfbj4fq8H1rHj1uWbZ3PzewyouhLw+J7F7XueBv\r\n4U0UYaqN/brlXXMxrwS82XfYWbwH3aBsLuye0qAHwiSCXSrRYoE6Gp6sOS0PYcWe\r\nGEXbVfSC09jud0HFx3+RaFUCAwEAAQ==\r\n-----END PUBLIC KEY-----', '默认配置', '2022-08-23 10:18:24.000', NULL, NULL);

-- ----------------------------
-- Table structure for config_param
-- ----------------------------
DROP TABLE IF EXISTS `config_param`;
CREATE TABLE `config_param`  (
  `id` bigint UNSIGNED NOT NULL,
  `config_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置key',
  `config_value` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置value',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置说明',
  `create_time` datetime(3) NOT NULL,
  `update_time` datetime(3) NULL DEFAULT NULL,
  `delete_time` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统参数' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_param
-- ----------------------------

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` bigint UNSIGNED NOT NULL,
  `p_id` tinyint NOT NULL DEFAULT 0 COMMENT '上级  默认0',
  `title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单/按钮名称',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '前端路由',
  `icon` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'icon图标',
  `key` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '唯一key标识',
  `type` tinyint(1) NOT NULL COMMENT '类型 0 菜单 1 按钮',
  `sort` int NOT NULL DEFAULT 0 COMMENT '顺序 ',
  `remark` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime(3) NOT NULL,
  `update_time` datetime(3) NULL DEFAULT NULL,
  `delete_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, 0, '系统管理', '/system', 'Setting', 'setting', 0, 99999, '', '2022-08-23 19:19:18.000', NULL, NULL);
INSERT INTO `menu` VALUES (2, 1, '用户管理', '/user', 'User', 'user', 0, 0, '', '2022-08-23 19:21:56.000', NULL, NULL);
INSERT INTO `menu` VALUES (3, 1, '租户管理', '/tenant', 'Operation', 'operation', 0, 1, '', '2022-08-23 19:27:02.000', NULL, NULL);
INSERT INTO `menu` VALUES (4, 1, '菜单管理', '/menu', 'Menu', 'menu', 0, 2, '', '2022-08-23 19:28:18.000', NULL, NULL);
INSERT INTO `menu` VALUES (5, 1, '顶部菜单', '/tmenu', 'Grape', 'grape', 0, 3, '', '2022-08-23 19:31:30.000', NULL, NULL);
INSERT INTO `menu` VALUES (6, 1, '角色管理', '/role', 'Avatar', 'avatar', 0, 4, '', '2022-08-23 19:32:47.000', NULL, NULL);
INSERT INTO `menu` VALUES (7, 1, '接口权限', '/apiscope', 'Rank', 'rank', 0, 5, '', '2022-08-23 19:34:56.000', NULL, NULL);
INSERT INTO `menu` VALUES (8, 1, '参数设置', '/param', 'Cpu', 'cpu', 0, 6, '', '2022-08-23 19:36:17.000', NULL, NULL);
INSERT INTO `menu` VALUES (9, 1, '应用管理', '/client', 'Coordinate', 'coordinate', 0, 7, '', '2022-08-23 19:37:49.000', NULL, NULL);
INSERT INTO `menu` VALUES (10, 2, '新增', '', '', 'add', 1, 0, '', '2022-08-23 19:47:30.000', NULL, NULL);
INSERT INTO `menu` VALUES (11, 2, '角色配置', '', '', 'role', 1, 0, '', '2022-08-23 19:48:12.000', NULL, NULL);
INSERT INTO `menu` VALUES (12, 2, '密码重置', '', '', 'resetpwd', 1, 0, '', '2022-08-23 19:48:35.000', NULL, NULL);
INSERT INTO `menu` VALUES (13, 2, '账号解封', '', '', 'deblock', 1, 0, '', '2022-08-23 19:49:47.000', NULL, NULL);
INSERT INTO `menu` VALUES (14, 2, '禁用', '', '', 'block', 1, 0, '', '2022-08-23 19:50:09.000', NULL, NULL);
INSERT INTO `menu` VALUES (15, 2, '查看', '', '', 'detail', 1, 0, '', '2022-08-23 19:50:43.000', NULL, NULL);
INSERT INTO `menu` VALUES (16, 2, '编辑', '', '', 'edit', 1, 0, '', '2022-08-23 19:50:57.000', NULL, NULL);
INSERT INTO `menu` VALUES (17, 2, '删除', '', '', 'del', 1, 0, '', '2022-08-23 19:52:12.000', NULL, NULL);
INSERT INTO `menu` VALUES (18, 3, '新增', '', '', 'add', 1, 0, '', '2022-08-23 19:55:17.000', NULL, NULL);
INSERT INTO `menu` VALUES (19, 3, '查看', '', '', 'detail', 1, 0, '', '2022-08-23 19:55:46.000', NULL, NULL);
INSERT INTO `menu` VALUES (20, 3, '编辑', '', '', 'edit', 1, 0, '', '2022-08-23 19:56:00.000', NULL, NULL);
INSERT INTO `menu` VALUES (21, 3, '删除', '', '', 'del', 1, 0, '', '2022-08-23 19:56:12.000', NULL, NULL);
INSERT INTO `menu` VALUES (22, 4, '新增', '', '', 'add', 1, 0, '', '2022-08-23 19:56:44.000', NULL, NULL);
INSERT INTO `menu` VALUES (23, 4, '查看', '', '', 'detail', 1, 0, '', '2022-08-23 19:57:09.000', NULL, NULL);
INSERT INTO `menu` VALUES (24, 4, '编辑', '', '', 'edit', 1, 0, '', '2022-08-23 19:57:36.000', NULL, NULL);
INSERT INTO `menu` VALUES (25, 4, '删除', '', '', 'del', 1, 0, '', '2022-08-23 19:57:50.000', NULL, NULL);
INSERT INTO `menu` VALUES (26, 4, '新增子项', '', '', 'addsub', 1, 0, '', '2022-08-23 19:58:11.000', NULL, NULL);
INSERT INTO `menu` VALUES (27, 5, '新增', '', '', 'add', 1, 0, '', '2022-08-23 19:59:10.000', NULL, NULL);
INSERT INTO `menu` VALUES (28, 5, '菜单配置', '', '', 'menu', 1, 0, '', '2022-08-23 19:59:31.000', NULL, NULL);
INSERT INTO `menu` VALUES (29, 5, '查看', '', '', 'detail', 1, 0, '', '2022-08-23 19:59:47.000', NULL, NULL);
INSERT INTO `menu` VALUES (30, 5, '编辑', '', '', 'edit', 1, 0, '', '2022-08-23 20:00:02.000', NULL, NULL);
INSERT INTO `menu` VALUES (31, 5, '删除', '', '', 'del', 1, 0, '', '2022-08-23 20:00:17.000', NULL, NULL);
INSERT INTO `menu` VALUES (32, 6, '新增', '', '', 'add', 1, 0, '', '2022-08-23 20:01:09.000', NULL, NULL);
INSERT INTO `menu` VALUES (33, 6, '权限设置', '', '', 'permission', 1, 0, '', '2022-08-23 20:01:35.000', NULL, NULL);
INSERT INTO `menu` VALUES (34, 6, '查看', '', '', 'detail', 1, 0, '', '2022-08-23 20:01:59.000', NULL, NULL);
INSERT INTO `menu` VALUES (35, 6, '编辑', '', '', 'edit', 1, 0, '', '2022-08-23 20:02:16.000', NULL, NULL);
INSERT INTO `menu` VALUES (36, 6, '删除', '', '', 'del', 1, 0, '', '2022-08-23 20:02:29.000', NULL, NULL);
INSERT INTO `menu` VALUES (37, 7, '权限配置', '', '', 'permission', 1, 0, '', '2022-08-23 20:03:15.000', NULL, NULL);
INSERT INTO `menu` VALUES (38, 8, '新增', '', '', 'add', 1, 0, '', '2022-08-23 20:03:53.000', NULL, NULL);
INSERT INTO `menu` VALUES (39, 8, '查看', '', '', 'detail', 1, 0, '', '2022-08-23 20:04:09.000', NULL, NULL);
INSERT INTO `menu` VALUES (40, 8, '编辑', '', '', 'edit', 1, 0, '', '2022-08-23 20:04:22.000', NULL, NULL);
INSERT INTO `menu` VALUES (41, 8, '删除', '', '', 'del', 1, 0, '', '2022-08-23 20:04:33.000', NULL, NULL);
INSERT INTO `menu` VALUES (42, 9, '新增', '', '', 'add', 1, 0, '', '2022-08-23 20:05:35.000', NULL, NULL);
INSERT INTO `menu` VALUES (43, 9, '查看', '', '', 'detail', 1, 0, '', '2022-08-23 20:05:47.000', NULL, NULL);
INSERT INTO `menu` VALUES (44, 9, '编辑', '', '', 'edit', 1, 0, '', '2022-08-23 20:06:01.000', NULL, NULL);
INSERT INTO `menu` VALUES (45, 9, '删除', '', '', 'del', 1, 0, '', '2022-08-23 20:06:15.000', NULL, NULL);

-- ----------------------------
-- Table structure for region
-- ----------------------------
DROP TABLE IF EXISTS `region`;
CREATE TABLE `region`  (
  `id` bigint UNSIGNED NOT NULL,
  `province_code` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '省 自治区 直辖市 特区 行政区划编号',
  `city_code` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '市 自治州 盟 直辖市下属辖区 汇总码',
  `district_code` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '区县 编号',
  `province_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '省级名称',
  `city_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '市级名称',
  `district_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '区县名称',
  `zip_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮编',
  `sort` int NOT NULL DEFAULT 0 COMMENT '顺序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '行政区划' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of region
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint UNSIGNED NOT NULL,
  `tenant_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户编号',
  `role_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `sort` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '排序',
  `remark` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备注',
  `create_time` datetime(3) NOT NULL,
  `update_time` datetime(3) NULL DEFAULT NULL,
  `delete_time` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '00000000', '超级管理员', 0, '超级管理员', '2022-08-23 10:08:00.000', NULL, NULL);

-- ----------------------------
-- Table structure for role_api
-- ----------------------------
DROP TABLE IF EXISTS `role_api`;
CREATE TABLE `role_api`  (
  `id` bigint UNSIGNED NOT NULL,
  `role_id` bigint UNSIGNED NOT NULL COMMENT '角色id',
  `menu_id` bigint UNSIGNED NOT NULL COMMENT '菜单id',
  `api_scope_id` bigint UNSIGNED NOT NULL COMMENT 'api scope id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色-接口权限' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role_api
-- ----------------------------

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `id` bigint UNSIGNED NOT NULL,
  `role_id` bigint UNSIGNED NOT NULL COMMENT '角色id',
  `menu_id` bigint UNSIGNED NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色-菜单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES (1, 1, 1);
INSERT INTO `role_menu` VALUES (2, 1, 2);
INSERT INTO `role_menu` VALUES (3, 1, 3);
INSERT INTO `role_menu` VALUES (4, 1, 4);
INSERT INTO `role_menu` VALUES (5, 1, 5);
INSERT INTO `role_menu` VALUES (6, 1, 6);
INSERT INTO `role_menu` VALUES (7, 1, 7);
INSERT INTO `role_menu` VALUES (8, 1, 8);
INSERT INTO `role_menu` VALUES (9, 1, 9);
INSERT INTO `role_menu` VALUES (10, 1, 10);
INSERT INTO `role_menu` VALUES (11, 1, 11);
INSERT INTO `role_menu` VALUES (12, 1, 12);
INSERT INTO `role_menu` VALUES (13, 1, 13);
INSERT INTO `role_menu` VALUES (14, 1, 14);
INSERT INTO `role_menu` VALUES (15, 1, 15);
INSERT INTO `role_menu` VALUES (16, 1, 16);
INSERT INTO `role_menu` VALUES (17, 1, 17);
INSERT INTO `role_menu` VALUES (18, 1, 18);
INSERT INTO `role_menu` VALUES (19, 1, 19);
INSERT INTO `role_menu` VALUES (20, 1, 20);
INSERT INTO `role_menu` VALUES (21, 1, 21);
INSERT INTO `role_menu` VALUES (22, 1, 22);
INSERT INTO `role_menu` VALUES (23, 1, 23);
INSERT INTO `role_menu` VALUES (24, 1, 24);
INSERT INTO `role_menu` VALUES (25, 1, 25);
INSERT INTO `role_menu` VALUES (26, 1, 26);
INSERT INTO `role_menu` VALUES (27, 1, 27);
INSERT INTO `role_menu` VALUES (28, 1, 28);
INSERT INTO `role_menu` VALUES (29, 1, 29);
INSERT INTO `role_menu` VALUES (30, 1, 30);
INSERT INTO `role_menu` VALUES (31, 1, 31);
INSERT INTO `role_menu` VALUES (32, 1, 32);
INSERT INTO `role_menu` VALUES (33, 1, 33);
INSERT INTO `role_menu` VALUES (34, 1, 34);
INSERT INTO `role_menu` VALUES (35, 1, 35);
INSERT INTO `role_menu` VALUES (36, 1, 36);
INSERT INTO `role_menu` VALUES (37, 1, 37);
INSERT INTO `role_menu` VALUES (38, 1, 38);
INSERT INTO `role_menu` VALUES (39, 1, 39);
INSERT INTO `role_menu` VALUES (40, 1, 40);
INSERT INTO `role_menu` VALUES (41, 1, 41);
INSERT INTO `role_menu` VALUES (42, 1, 42);
INSERT INTO `role_menu` VALUES (43, 1, 43);
INSERT INTO `role_menu` VALUES (44, 1, 44);
INSERT INTO `role_menu` VALUES (45, 1, 45);

-- ----------------------------
-- Table structure for tenant
-- ----------------------------
DROP TABLE IF EXISTS `tenant`;
CREATE TABLE `tenant`  (
  `id` bigint UNSIGNED NOT NULL,
  `p_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '上级id 默认0',
  `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户编号',
  `tenant_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户名称',
  `status` tinyint(1) NOT NULL COMMENT '状态 0 禁用  1正常',
  `remark` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备注',
  `create_time` datetime(3) NOT NULL,
  `update_time` datetime(3) NULL DEFAULT NULL,
  `delete_time` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '租户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tenant
-- ----------------------------
INSERT INTO `tenant` VALUES (1, 0, '00000000', '运营平台', 1, '运营平台', '2022-08-23 10:05:40.000', NULL, NULL);

-- ----------------------------
-- Table structure for top_menu
-- ----------------------------
DROP TABLE IF EXISTS `top_menu`;
CREATE TABLE `top_menu`  (
  `id` bigint NOT NULL,
  `tenant_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '租户编号',
  `title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
  `key` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '唯一标识key',
  `icon` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'icon图标',
  `sort` int NOT NULL DEFAULT 0 COMMENT '顺序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '顶部菜单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of top_menu
-- ----------------------------

-- ----------------------------
-- Table structure for top_menu_tree
-- ----------------------------
DROP TABLE IF EXISTS `top_menu_tree`;
CREATE TABLE `top_menu_tree`  (
  `id` bigint UNSIGNED NOT NULL,
  `top_id` bigint UNSIGNED NOT NULL COMMENT 'top_menu id',
  `menu_id` bigint UNSIGNED NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '顶部菜单-菜单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of top_menu_tree
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
