SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`
(
    `id`          bigint unsigned                                              NOT NULL AUTO_INCREMENT,
    `tenant_id`   bigint unsigned                                              NOT NULL COMMENT '租户id',
    `account`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号',
    `nick_name`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '昵称',
    `password`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '密码',
    `phone`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '手机号',
    `status`      tinyint(1)                                                   NOT NULL DEFAULT 1 COMMENT '状态 0 禁用  1 正常',
    `del_flag`    tinyint(1)                                                   NOT NULL DEFAULT 1 COMMENT '是否删除 0否 1是',
    `create_time` datetime(3)                                                  NOT NULL DEFAULT current_timestamp(3),
    `update_time` datetime(3)                                                  NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '管理员'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin`
VALUES (1, 1, 'admin', '超级管理员', md5('admin*123456'), '18615262691', 1, 0, now(3), NULL);

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`
(
    `id`         bigint UNSIGNED                                                NOT NULL AUTO_INCREMENT,
    `pid`        bigint unsigned                                                not null default 0 comment '上级id 默认0',
    `pids`       varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '所有上级id  默认0 ,分隔',
    `title`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL COMMENT '菜单/按钮名称',
    `path`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL DEFAULT '' COMMENT '前端路由',
    `icon`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL DEFAULT '' COMMENT 'icon图标',
    `key`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL DEFAULT '' COMMENT '唯一key标识',
    `permission` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL DEFAULT '' COMMENT '权限字段',
    `type`       tinyint(1)                                                     NOT NULL COMMENT '类型 0 菜单 1 按钮',
    `sort`       int                                                            NOT NULL DEFAULT 0 COMMENT '顺序 ',
    `remark`     varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_pid` (`pid`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu`
VALUES (1, 0, '0', '系统管理', '/system', 'Setting', 'setting', '', 0, 99999, '');
INSERT INTO `menu`
VALUES (2, 1, '1', '租户管理', '/tenant', 'Operation', 'tenant', '', 0, 1, '');
INSERT INTO `menu`
VALUES (3, 1, '1', '菜单管理', '/menu', 'Menu', 'menus', '', 0, 2, '');
INSERT INTO `menu`
VALUES (4, 1, '1', '角色管理', '/role', 'Avatar', 'role', '', 0, 3, '');
INSERT INTO `menu`
VALUES (5, 1, '1', '用户管理', '/user', 'User', 'user', '', 0, 5, '');
INSERT INTO `menu`
VALUES (6, 1, '1', '值集配置', '/lov', 'Grape', 'lov', '', 0, 5, '');

INSERT INTO `menu`
VALUES (10, 2, '1,2', '新增', '', '', '', 'admin.tenant.add', 1, 0, '');
INSERT INTO `menu`
VALUES (11, 2, '1,2', '编辑', '', '', '', 'admin.tenant.edit', 1, 0, '');
INSERT INTO `menu`
VALUES (12, 2, '1,2', '新增子级', '', '', '', 'admin.tenant.addsub', 1, 0, '');

INSERT INTO `menu`
VALUES (13, 3, '1,3', '新增', '', '', '', 'admin.menu.add', 1, 0, '');
INSERT INTO `menu`
VALUES (14, 3, '1,3', '编辑', '', '', '', 'admin.menu.edit', 1, 0, '');
INSERT INTO `menu`
VALUES (15, 3, '1,3', '删除', '', '', '', 'admin.menu.del', 1, 0, '');
INSERT INTO `menu`
VALUES (16, 3, '1,3', '新增子项', '', '', '', 'admin.menu.addsub', 1, 0, '');

INSERT INTO `menu`
VALUES (17, 4, '1,4', '新增', '', '', '', 'admin.role.add', 1, 0, '');
INSERT INTO `menu`
VALUES (18, 4, '1,4', '编辑', '', '', '', 'admin.role.edit', 1, 0, '');
INSERT INTO `menu`
VALUES (19, 4, '1,4', '删除', '', '', '', 'admin.role.del', 1, 0, '');
INSERT INTO `menu`
VALUES (20, 4, '1,4', '菜单配置', '', '', '', 'admin.role.menu', 1, 0, '');

INSERT INTO `menu`
VALUES (21, 5, '1,5', '新增', '', '', '', 'admin.user.add', 1, 0, '');
INSERT INTO `menu`
VALUES (22, 5, '1,5', '编辑', '', '', '', 'admin.user.edit', 1, 0, '');
INSERT INTO `menu`
VALUES (23, 5, '1,5', '删除', '', '', '', 'admin.user.del', 1, 0, '');
INSERT INTO `menu`
VALUES (24, 5, '1,5', '禁用', '', '', '', 'admin.user.block', 1, 0, '');
INSERT INTO `menu`
VALUES (25, 5, '1,5', '角色配置', '', '', '', 'admin.user.role', 1, 0, '');
INSERT INTO `menu`
VALUES (26, 5, '1,5', '密码重置', '', '', '', 'admin.user.resetpwd', 1, 0, '');
INSERT INTO `menu`
VALUES (27, 5, '1,5', '账号解封', '', '', '', 'admin.user.unblock', 1, 0, '');

INSERT INTO `menu`
VALUES (28, 6, '1,6', '新增', '', '', '', 'admin.lov.add', 1, 0, '');
INSERT INTO `menu`
VALUES (29, 6, '1,6', '编辑', '', '', '', 'admin.lov.edit', 1, 0, '');
INSERT INTO `menu`
VALUES (30, 6, '1,6', '删除', '', '', '', 'admin.lov.del', 1, 0, '');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`
(
    `id`        bigint UNSIGNED                                                NOT NULL auto_increment,
    `role_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL COMMENT '角色名称',
    `action`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL COMMENT '权限字段',
    `remark`    varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role`
VALUES (1, '超级管理员', 'super_admin', '超级管理员');

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role`
(
    `id`       bigint UNSIGNED NOT NULL auto_increment,
    `admin_id` bigint UNSIGNED NOT NULL COMMENT '用户id',
    `role_id`  bigint UNSIGNED NOT NULL COMMENT '角色id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户-角色'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_role
-- ----------------------------
INSERT INTO `admin_role`
VALUES (1, 1, 1);

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`
(
    `id`      bigint UNSIGNED NOT NULL auto_increment,
    `role_id` bigint UNSIGNED NOT NULL COMMENT '角色id',
    `menu_id` bigint UNSIGNED NOT NULL COMMENT '菜单id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色-菜单'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tenant
-- ----------------------------
DROP TABLE IF EXISTS `tenant`;
CREATE TABLE `tenant`
(
    `id`          bigint UNSIGNED                                                NOT NULL auto_increment,
    `pid`         bigint unsigned                                                not null default 0 comment '上级id 默认0',
    `pids`        varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '所有上级id  默认0 ,分隔',
    `code`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL COMMENT '租户编号',
    `tenant_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '租户名称',
    `remark`      varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
    `create_time` datetime(3)                                                    NOT NULL DEFAULT current_timestamp(3),
    `update_time` datetime(3)                                                    NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '租户'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tenant
-- ----------------------------
INSERT INTO `tenant`
VALUES (1, 0, '0', 'V00000001', '运营平台', '运营平台', now(3), NULL);

-- ----------------------------
-- Table structure for lov_category
-- ----------------------------
DROP TABLE IF EXISTS `lov_category`;
CREATE TABLE `lov_category`
(
    `id`        bigint UNSIGNED                                                NOT NULL auto_increment,
    `tenant_id` bigint unsigned                                                NOT NULL COMMENT '租户id',
    `category`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL COMMENT '分组',
    `remark`    varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_tenant_id` (`tenant_id`),
    KEY `idx_category` (`category`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '值集分组'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for lov
-- ----------------------------
DROP TABLE IF EXISTS `lov`;
CREATE TABLE `lov`
(
    `id`              bigint UNSIGNED                                                NOT NULL auto_increment,
    `lov_category_id` bigint unsigned                                                NOT NULL COMMENT '值集分组id',
    `key`             varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL COMMENT '键',
    `value`           text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci          NOT NULL COMMENT '值',
    `remark`          varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_key` (`key`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '值集配置'
  ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
