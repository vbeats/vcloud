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
-- Table structure for auth_client
-- ----------------------------
DROP TABLE IF EXISTS `auth_client`;
CREATE TABLE `auth_client`
(
    `id`                   bigint UNSIGNED                                                NOT NULL,
    `client_id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL COMMENT 'client id',
    `client_secret`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL COMMENT 'secret',
    `grant_types`          varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '授权类型',
    `access_token_expire`  bigint                                                         NOT NULL DEFAULT 7200 COMMENT 'access_token有效时长 (秒) 默认7200',
    `refresh_token_expire` bigint                                                         NOT NULL DEFAULT 30 COMMENT 'refresh_token有效时长 (天) 默认30',
    `private_key`          varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'rsa私钥',
    `public_key`           varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'rsa公钥',
    `remark`               varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
    `create_time`          datetime(3)                                                    NOT NULL DEFAULT NOW(3),
    `update_time`          datetime(3)                                                    NULL     DEFAULT NULL,
    `delete_time`          datetime(3)                                                    NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = 'client参数'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of auth_client
-- ----------------------------
INSERT INTO `auth_client`
VALUES (1, 'de0b55913ac7', '939da067-8899-40a0-9c21-15f0b0001add', 'wx_app,wx_mp,password,sms,refresh_token', 7200, 30,
        '-----BEGIN PRIVATE KEY-----\r\nMIIJQQIBADANBgkqhkiG9w0BAQEFAASCCSswggknAgEAAoICAQD2KXRkMfA3AQYs\r\nqO8Jikl+MOszsnZTFfCDCh74+CeW+D6MitdhJ7m/Au2uOonkzuOWYfipsbY9hPZa\r\nVGQDkinN45ZimHq8BW2HsKcdf4p1r9NYkPh0QRwKf4eTy6WQJ/lrT5wGfD+/Inf4\r\nYPzPl/llAndgnQKg96mx0QpF8pB3d5IEamlfPZSYS8IWLXSxAZAU4hFx2ove1RiQ\r\nkaclLNmgOWrBbIjziYzokTw02FLG9lhPL1RCP3K0jewWEHuOUaKDHFVl1rKiEpAf\r\n8VPOLtL/DVkNZeK2fCGVXmt0g/T9JZ52u95smR2Sm+OrRbYyRajhgVIkddi0r2kn\r\nLWV/HDx5/dUp/Sbzopqi5/I4sD8WWOereuNGZo68b0BInsQ9go/NKVuoTieEMff0\r\nwGsFzdopuKd2HytaLkbLwm1PQXzX/8psbp4fcSBIi45MiuT+ICZ4tOTq4ZiGP1yx\r\nWGd56wwlEK7cmNtEsfNs/SKSvG9uiTD/nGytA6KoXLlSAH+efww3KvAqlx00p4Hz\r\nhv5tNCL3mSpPW2WH8S1isdbkHxYtd778x9uPh+rwfWsePW5Ztnc/N7DKi6EvD4ns\r\nXte54G/hTRRhqo39uuVdczGvBLzZd9hZvAfdoGwu7J7SoAfCJIJdKtFigToanqw5\r\nLQ9hxZ4YRdtV9ILT2O53QcXHf5FoVQIDAQABAoICACcqI02JsHlf3VafriBrcxPz\r\nvogkHbVMaU6//nuIJ+xaJMGBmZDonCHq2lv9DlFsJUOY5NJC5wbUr8lhYeQ7jhEm\r\n45deQTDHAE01avFDiIj+53ZQ4mbEsSxua+i03uuXoJRVPzK88/t6BXJsI+z3dgN/\r\nJ/UkJfXsUYBsDOFiHWAUkxPGxmsTxh+Q9hlHNCixYfYgnbvqlJRofRcLRXehsiJO\r\n4FBT71ooCVY7PUP/IvRq31QB6Lr9k0dsySIdjzrufBe2G1Qvm44zu+CJKddFFebk\r\nfcWm7zIvf7xfIGOSVxkrshGcBGBs4AqiaM+oMLvA34S5aZNJeKgOvIEyNNLLcEd8\r\nm2yH+McM+7GBsSYRpzKmSvmVL/z2IoXG//0DHXOp7bs20Jv6cLGhdsP9D4nLCtyv\r\nAMGc2aEjliLeOfuGXMuIZeijOLdkULSDvnmB8MVuBQcTumrDOF/PwvTUFr3lixEG\r\n7oPkvnQAjy+2OdgJum/jy29mL+O0LfnQAToS5jQfGGQv6FJuPpHO7Vngq0WGZTuR\r\nSDHkscfbhE5LScErV7bgmTIPY0JlVSRkq2gBqEhnAWtHecY8jI1hcftCDw+3Fdkk\r\nxk5Fmh2Rhj3YoKkEyfJzMnDPRT9Gds7Ov+J4ql9mde5CcQDH4zVWizLpjPvkHtSd\r\n5U6hRTILirtw4MnoBI/hAoIBAQD+Cabxea6tNedpm+lZQyf/fZczq3HpXZHZEa/q\r\nxV32STr4jaewxzxB/UH3icccbKavjodlIpAiPGaadO0bnZZWNGq9PcnA+yV2gvgB\r\nujJy/8+FvATiwcJo0S87tOk17/NYoPugjWEI0bP0EX3t8hjhOCfkGILyElTtMt2G\r\nEkoWXAeb3PHwlXePnHKFVmBX3D/U/knZ7kCZRqGbeC7hB/K/FU0B+379r4JHW2cm\r\n9lPlyTnEgOffOF8Gh9f/BgxBsRt+2NjlULc2j/HTXFyCN0oygOwt2GCLf0h8ng3O\r\nU7yVeHRCQB7U6FwSB7sym8og/U7l3vIUqpUM6hRnpm5C8UzZAoIBAQD4EDqCpYKv\r\n8XNNXXXXdNDgEJdt2S4GnsqmigbKRvAP1pZmbgj8U0qcE5twuf2LFinw4PCNnU1q\r\n5sJxh9hB5vfhSGXii2hlSONu7ZeNNhrWBK3UmeXIjUf4wEehBFKVtaDEHsyV8R1n\r\n5LSOkJ7e+gRouBIWrHRox3HOdyhKl/m1M/8ivaA+mmWHrR0UriipPUQwZsTwB62L\r\naB8bCJgY4yLGmzvsiUek1TqPI8HdR+yxFhBtV6qKXInd3KuP9P0wOXOdrtPgT3CC\r\nUznpFT0p145oc/s5ufiVZvCQS8TH9cT3q3Ei65WayiokChNbT3EFUbydZDTFKU6d\r\nWZStSRd3u/ndAoIBAG0N67LiD9CfwVYe7k+5eqQ9X/l4chBCcOgEpcZdL/cYfM5S\r\n0VgcT4vskrMXXHihU/UtdIZADiwETe+knl0qi206V1AiBEhqCC0WEC19p0ai9o1s\r\np5RIpZKlqmcxRKQ4+/hiM2M7DhrhGyV9lNffBDs7BjTvKNeOcxLYsSxrEY8Dtuh8\r\nMwiawZbPDIKdEALntCdVepKpquWh4qKBN263IMhS9poRQvTYsU68uE2LlXyho8Dt\r\nrZyv8Gs3Scxa5kwVIb6UjJf0zJIJvUCE99mWrq17lugfaWK5I38LwtV+MQjhitcd\r\n+55emZfL5drV8jGzTHl1+epRSGcg3d2ZHuByAhECggEAX39v5wfszes4JlqKmU2h\r\ndAEvKtznOSk8fuy4PHsexBoqgHhwASPXn0p1FuqjTz5TGyadtQcP3M3FoYtYl9Zh\r\nK1uBzbs5j3SGChhxta1Um3vlp+kvawvo3zy21qghWv03TQlGXZsbZPnJAPFwGAtM\r\nUzw5ynzNu+C8UW3SFxV2zmmcGTXDURaDa74bafC6Op0ZeUC3JGjwSLDm+LNQSpR2\r\nuNreMOuQp0Znat+rLJMZ7fq+jDmpr+Z8NOtVKPB54Gzds6CwdLRgbeu4aaEBkPAp\r\nJNExsEGGD443onVo5koZb/eScI0dZR/bJVCzrv1gV1nmMPl4z0Zdu3nXIPb4j+HQ\r\nCQKCAQBwGoarJsRYmtRxqZqeKtYGUg7oNC9NnzMoUNN0ykR4D+7d5xjz1bLngbWG\r\nQSAiGIAHSENdqxFwu9O46bwFodaShuhAfM4A++QJFSBKjZO5TkfbjoBsQe2RU1P5\r\nEeYSEaqWK3FVzsOrp4RjKiiqLy1hS6fpHxO4QR1uB5eil8AqDzt7+mgoLrpanc6I\r\nPYKmG1OgTRL2kzASiYXY0xIriaxfxqbSjbH3wJGUgAX4u+QyH8B6KPUPYjdcgwmZ\r\n/Jdc7H/rEVKrzCaOpkjfeqhBYWOhLPvKZK1A1T1zxwkDuAkB/gZ8DXLbLpxcLpbh\r\nnflau0Dmk4OSv3b6D3qXBYkw6/Lx\r\n-----END PRIVATE KEY-----',
        '-----BEGIN PUBLIC KEY-----\r\nMIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEA9il0ZDHwNwEGLKjvCYpJ\r\nfjDrM7J2UxXwgwoe+Pgnlvg+jIrXYSe5vwLtrjqJ5M7jlmH4qbG2PYT2WlRkA5Ip\r\nzeOWYph6vAVth7CnHX+Kda/TWJD4dEEcCn+Hk8ulkCf5a0+cBnw/vyJ3+GD8z5f5\r\nZQJ3YJ0CoPepsdEKRfKQd3eSBGppXz2UmEvCFi10sQGQFOIRcdqL3tUYkJGnJSzZ\r\noDlqwWyI84mM6JE8NNhSxvZYTy9UQj9ytI3sFhB7jlGigxxVZdayohKQH/FTzi7S\r\n/w1ZDWXitnwhlV5rdIP0/SWedrvebJkdkpvjq0W2MkWo4YFSJHXYtK9pJy1lfxw8\r\nef3VKf0m86KaoufyOLA/Fljnq3rjRmaOvG9ASJ7EPYKPzSlbqE4nhDH39MBrBc3a\r\nKbindh8rWi5Gy8JtT0F81//KbG6eH3EgSIuOTIrk/iAmeLTk6uGYhj9csVhneesM\r\nJRCu3JjbRLHzbP0ikrxvbokw/5xsrQOiqFy5UgB/nn8MNyrwKpcdNKeB84b+bTQi\r\n95kqT1tlh/EtYrHW5B8WLXe+/Mfbj4fq8H1rHj1uWbZ3PzewyouhLw+J7F7XueBv\r\n4U0UYaqN/brlXXMxrwS82XfYWbwH3aBsLuye0qAHwiSCXSrRYoE6Gp6sOS0PYcWe\r\nGEXbVfSC09jud0HFx3+RaFUCAwEAAQ==\r\n-----END PUBLIC KEY-----',
        '默认配置', now(3), NULL, NULL);

-- ----------------------------
-- Table structure for config_param
-- ----------------------------
DROP TABLE IF EXISTS `config_param`;
CREATE TABLE `config_param`
(
    `id`           bigint UNSIGNED                                                NOT NULL,
    `config_key`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '配置key',
    `config_value` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置value',
    `remark`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL DEFAULT '' COMMENT '配置说明',
    `create_time`  datetime(3)                                                    NOT NULL DEFAULT NOW(3),
    `update_time`  datetime(3)                                                    NULL     DEFAULT NULL,
    `delete_time`  datetime(3)                                                    NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统参数'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_param
-- ----------------------------

INSERT INTO `config_param`
VALUES (1, 'super_tenant', '00000000', '运营平台租户编号', now(3), null, null);
INSERT INTO `config_param`
VALUES (2, 'super_role', 'super_admin', '超级管理员角色编号', now(3), null, null);
INSERT INTO `config_param`
VALUES (3, 'default_password', '123456', '管理员默认密码', now(3), null, null);

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
    `type`        tinyint(1)                                                     NOT NULL COMMENT '类型 0 菜单 1 按钮',
    `sort`        int                                                            NOT NULL DEFAULT 0 COMMENT '顺序 ',
    `remark`      varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
    `create_time` datetime(3)                                                    NOT NULL DEFAULT NOW(3),
    `update_time` datetime(3)                                                    NULL     DEFAULT NULL,
    `delete_time` datetime                                                       NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu`
VALUES (1, 0, '系统管理', '/system', 'Setting', 'setting', 0, 99999, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (2, 1, '租户管理', '/tenant', 'Operation', 'operation', 0, 1, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (3, 1, '菜单管理', '/menu', 'Menu', 'menu', 0, 2, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (4, 1, '角色管理', '/role', 'Avatar', 'avatar', 0, 3, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (5, 1, '用户管理', '/user', 'User', 'user', 0, 5, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (6, 1, '参数设置', '/param', 'Cpu', 'cpu', 0, 6, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (7, 1, '应用管理', '/client', 'Coordinate', 'coordinate', 0, 7, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (8, 1, '行政区划', '/region', 'MapLocation', 'region', 0, 8, '', now(3), NULL, NULL);

INSERT INTO `menu`
VALUES (10, 2, '新增', '', '', 'add', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (11, 2, '编辑', '', '', 'edit', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (12, 2, '删除', '', '', 'del', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (13, 2, '禁用', '', '', 'block', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (14, 2, '解封', '', '', 'unblock', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (15, 2, '新增子级', '', '', 'addsub', 1, 0, '', now(3), NULL, NULL);

INSERT INTO `menu`
VALUES (16, 3, '新增', '', '', 'add', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (17, 3, '编辑', '', '', 'edit', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (18, 3, '删除', '', '', 'del', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (19, 3, '新增子项', '', '', 'addsub', 1, 0, '', now(3), NULL, NULL);

INSERT INTO `menu`
VALUES (20, 4, '新增', '', '', 'add', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (21, 4, '编辑', '', '', 'edit', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (22, 4, '删除', '', '', 'del', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (23, 4, '菜单配置', '', '', 'menu', 1, 0, '', now(3), NULL, NULL);

INSERT INTO `menu`
VALUES (25, 5, '新增', '', '', 'add', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (26, 5, '编辑', '', '', 'edit', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (27, 5, '删除', '', '', 'del', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (28, 5, '禁用', '', '', 'block', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (29, 5, '密码重置', '', '', 'resetpwd', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (30, 5, '账号解封', '', '', 'unblock', 1, 0, '', now(3), NULL, NULL);

INSERT INTO `menu`
VALUES (31, 6, '新增', '', '', 'add', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (32, 6, '编辑', '', '', 'edit', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (33, 6, '删除', '', '', 'del', 1, 0, '', now(3), NULL, NULL);

INSERT INTO `menu`
VALUES (34, 7, '新增', '', '', 'add', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (35, 7, '编辑', '', '', 'edit', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (36, 7, '删除', '', '', 'del', 1, 0, '', now(3), NULL, NULL);

INSERT INTO `menu`
VALUES (37, 8, '新增下级', '', '', 'addsub', 1, 0, '', now(3), NULL, NULL);
INSERT INTO `menu`
VALUES (38, 8, '删除', '', '', 'del', 1, 0, '', now(3), NULL, NULL);

-- ----------------------------
-- Table structure for region
-- ----------------------------
DROP TABLE IF EXISTS `region`;
CREATE TABLE `region`
(
    `id`       bigint UNSIGNED                                               NOT NULL,
    `pid`      bigint UNSIGNED                                               NOT NULL DEFAULT 0 COMMENT '上级id',
    `code`     varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL DEFAULT '' COMMENT '2位区划编号/汇总码',
    `name`     varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '名称',
    `zip_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL DEFAULT '' COMMENT '邮编',
    `sort`     int                                                           NOT NULL DEFAULT 0 COMMENT '顺序',
    `type`     tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '类型 0:省/自治区/直辖市/特区 1:市/自治州/盟/直辖市下属辖区 2:区县 3:乡镇/街道 4:村/小区',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '行政区划'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of region
-- ----------------------------

INSERT INTO `region`
VALUES (1, 0, '北京', '110000', '', 1, 0);
INSERT INTO `region`
VALUES (2, 0, '天津', '120000', '', 2, 0);
INSERT INTO `region`
VALUES (3, 0, '河北省', '130000', '', 3, 0);
INSERT INTO `region`
VALUES (4, 0, '山西省', '140000', '', 4, 0);
INSERT INTO `region`
VALUES (5, 0, '内蒙古自治区', '150000', '', 5, 0);
INSERT INTO `region`
VALUES (6, 0, '辽宁省', '210000', '', 6, 0);
INSERT INTO `region`
VALUES (7, 0, '吉林省', '220000', '', 7, 0);
INSERT INTO `region`
VALUES (8, 0, '黑龙江省', '230000', '', 8, 0);
INSERT INTO `region`
VALUES (9, 0, '上海', '310000', '', 9, 0);
INSERT INTO `region`
VALUES (10, 0, '江苏省', '320000', '', 10, 0);
INSERT INTO `region`
VALUES (11, 0, '浙江省', '330000', '', 11, 0);
INSERT INTO `region`
VALUES (12, 0, '安徽省', '340000', '', 12, 0);
INSERT INTO `region`
VALUES (13, 0, '福建省', '350000', '', 13, 0);
INSERT INTO `region`
VALUES (14, 0, '江西省', '360000', '', 14, 0);
INSERT INTO `region`
VALUES (15, 0, '山东省', '370000', '', 15, 0);
INSERT INTO `region`
VALUES (16, 0, '河南省', '410000', '', 16, 0);
INSERT INTO `region`
VALUES (17, 0, '湖北省', '420000', '', 17, 0);
INSERT INTO `region`
VALUES (18, 0, '湖南省', '430000', '', 18, 0);
INSERT INTO `region`
VALUES (19, 0, '广东省', '440000', '', 19, 0);
INSERT INTO `region`
VALUES (20, 0, '广西壮族自治区', '450000', '', 20, 0);
INSERT INTO `region`
VALUES (21, 0, '海南省', '460000', '', 21, 0);
INSERT INTO `region`
VALUES (22, 0, '重庆', '500000', '', 22, 0);
INSERT INTO `region`
VALUES (23, 0, '四川省', '510000', '', 23, 0);
INSERT INTO `region`
VALUES (24, 0, '贵州省', '520000', '', 24, 0);
INSERT INTO `region`
VALUES (25, 0, '云南省', '530000', '', 25, 0);
INSERT INTO `region`
VALUES (26, 0, '西藏自治区', '540000', '', 26, 0);
INSERT INTO `region`
VALUES (27, 0, '陕西省', '610000', '', 27, 0);
INSERT INTO `region`
VALUES (28, 0, '甘肃省', '620000', '', 28, 0);
INSERT INTO `region`
VALUES (29, 0, '青海省', '630000', '', 29, 0);
INSERT INTO `region`
VALUES (30, 0, '宁夏回族自治区', '640000', '', 30, 0);
INSERT INTO `region`
VALUES (31, 0, '新疆维吾尔自治区', '650000', '', 31, 0);
INSERT INTO `region`
VALUES (32, 0, '香港', '810000', '', 32, 0);
INSERT INTO `region`
VALUES (33, 0, '澳门', '820000', '', 33, 0);
INSERT INTO `region`
VALUES (34, 0, '台湾省', '710000', '', 34, 0);
INSERT INTO `region`
VALUES (35, 1, '北京市', '110000', '', 1, 1);
INSERT INTO `region`
VALUES (36, 2, '天津市', '120000', '', 1, 1);
INSERT INTO `region`
VALUES (37, 3, '石家庄市', '130100', '', 0, 1);
INSERT INTO `region`
VALUES (38, 3, '唐山市', '130200', '', 0, 1);
INSERT INTO `region`
VALUES (39, 3, '秦皇岛市', '130300', '', 0, 1);
INSERT INTO `region`
VALUES (40, 3, '邯郸市', '130400', '', 0, 1);
INSERT INTO `region`
VALUES (41, 3, '邢台市', '130500', '', 0, 1);
INSERT INTO `region`
VALUES (42, 3, '保定市', '130600', '', 0, 1);
INSERT INTO `region`
VALUES (43, 3, '张家口市', '130700', '', 0, 1);
INSERT INTO `region`
VALUES (44, 3, '承德市', '130800', '', 0, 1);
INSERT INTO `region`
VALUES (45, 3, '沧州市', '130900', '', 0, 1);
INSERT INTO `region`
VALUES (46, 3, '廊坊市', '131000', '', 0, 1);
INSERT INTO `region`
VALUES (47, 3, '衡水市', '131100', '', 0, 1);
INSERT INTO `region`
VALUES (48, 4, '太原市', '140100', '', 0, 1);
INSERT INTO `region`
VALUES (49, 4, '大同市', '140200', '', 0, 1);
INSERT INTO `region`
VALUES (50, 4, '阳泉市', '140300', '', 0, 1);
INSERT INTO `region`
VALUES (51, 4, '长治市', '140400', '', 0, 1);
INSERT INTO `region`
VALUES (52, 4, '晋城市', '140500', '', 0, 1);
INSERT INTO `region`
VALUES (53, 4, '朔州市', '140600', '', 0, 1);
INSERT INTO `region`
VALUES (54, 4, '晋中市', '140700', '', 0, 1);
INSERT INTO `region`
VALUES (55, 4, '运城市', '140800', '', 0, 1);
INSERT INTO `region`
VALUES (56, 4, '忻州市', '140900', '', 0, 1);
INSERT INTO `region`
VALUES (57, 4, '临汾市', '141000', '', 0, 1);
INSERT INTO `region`
VALUES (58, 4, '吕梁市', '141100', '', 0, 1);
INSERT INTO `region`
VALUES (59, 5, '呼和浩特市', '150100', '', 0, 1);
INSERT INTO `region`
VALUES (60, 5, '包头市', '150200', '', 0, 1);
INSERT INTO `region`
VALUES (61, 5, '乌海市', '150300', '', 0, 1);
INSERT INTO `region`
VALUES (62, 5, '赤峰市', '150400', '', 0, 1);
INSERT INTO `region`
VALUES (63, 5, '通辽市', '150500', '', 0, 1);
INSERT INTO `region`
VALUES (64, 5, '鄂尔多斯市', '150600', '', 0, 1);
INSERT INTO `region`
VALUES (65, 5, '呼伦贝尔市', '150700', '', 0, 1);
INSERT INTO `region`
VALUES (66, 5, '巴彦淖尔市', '150800', '', 0, 1);
INSERT INTO `region`
VALUES (67, 5, '乌兰察布市', '150900', '', 0, 1);
INSERT INTO `region`
VALUES (68, 5, '兴安盟', '152200', '', 0, 1);
INSERT INTO `region`
VALUES (69, 5, '锡林郭勒盟', '152500', '', 0, 1);
INSERT INTO `region`
VALUES (70, 5, '阿拉善盟', '152900', '', 0, 1);
INSERT INTO `region`
VALUES (71, 6, '沈阳市', '210100', '', 0, 1);
INSERT INTO `region`
VALUES (72, 6, '大连市', '210200', '', 0, 1);
INSERT INTO `region`
VALUES (73, 6, '鞍山市', '210300', '', 0, 1);
INSERT INTO `region`
VALUES (74, 6, '抚顺市', '210400', '', 0, 1);
INSERT INTO `region`
VALUES (75, 6, '本溪市', '210500', '', 0, 1);
INSERT INTO `region`
VALUES (76, 6, '丹东市', '210600', '', 0, 1);
INSERT INTO `region`
VALUES (77, 6, '锦州市', '210700', '', 0, 1);
INSERT INTO `region`
VALUES (78, 6, '营口市', '210800', '', 0, 1);
INSERT INTO `region`
VALUES (79, 6, '阜新市', '210900', '', 0, 1);
INSERT INTO `region`
VALUES (80, 6, '辽阳市', '211000', '', 0, 1);
INSERT INTO `region`
VALUES (81, 6, '盘锦市', '211100', '', 0, 1);
INSERT INTO `region`
VALUES (82, 6, '铁岭市', '211200', '', 0, 1);
INSERT INTO `region`
VALUES (83, 6, '朝阳市', '211300', '', 0, 1);
INSERT INTO `region`
VALUES (84, 6, '葫芦岛市', '211400', '', 0, 1);
INSERT INTO `region`
VALUES (85, 7, '长春市', '220100', '', 0, 1);
INSERT INTO `region`
VALUES (86, 7, '吉林市', '220200', '', 0, 1);
INSERT INTO `region`
VALUES (87, 7, '四平市', '220300', '', 0, 1);
INSERT INTO `region`
VALUES (88, 7, '辽源市', '220400', '', 0, 1);
INSERT INTO `region`
VALUES (89, 7, '通化市', '220500', '', 0, 1);
INSERT INTO `region`
VALUES (90, 7, '白山市', '220600', '', 0, 1);
INSERT INTO `region`
VALUES (91, 7, '松原市', '220700', '', 0, 1);
INSERT INTO `region`
VALUES (92, 7, '白城市', '220800', '', 0, 1);
INSERT INTO `region`
VALUES (93, 7, '延边朝鲜族自治州', '222400', '', 0, 1);
INSERT INTO `region`
VALUES (94, 8, '哈尔滨市', '230100', '', 0, 1);
INSERT INTO `region`
VALUES (95, 8, '齐齐哈尔市', '230200', '', 0, 1);
INSERT INTO `region`
VALUES (96, 8, '鸡西市', '230300', '', 0, 1);
INSERT INTO `region`
VALUES (97, 8, '鹤岗市', '230400', '', 0, 1);
INSERT INTO `region`
VALUES (98, 8, '双鸭山市', '230500', '', 0, 1);
INSERT INTO `region`
VALUES (99, 8, '大庆市', '230600', '', 0, 1);
INSERT INTO `region`
VALUES (100, 8, '伊春市', '230700', '', 0, 1);
INSERT INTO `region`
VALUES (101, 8, '佳木斯市', '230800', '', 0, 1);
INSERT INTO `region`
VALUES (102, 8, '七台河市', '230900', '', 0, 1);
INSERT INTO `region`
VALUES (103, 8, '牡丹江市', '231000', '', 0, 1);
INSERT INTO `region`
VALUES (104, 8, '黑河市', '231100', '', 0, 1);
INSERT INTO `region`
VALUES (105, 8, '绥化市', '231200', '', 0, 1);
INSERT INTO `region`
VALUES (106, 8, '大兴安岭地区', '232700', '', 0, 1);
INSERT INTO `region`
VALUES (107, 9, '上海市', '310000', '', 0, 1);
INSERT INTO `region`
VALUES (108, 10, '南京市', '320100', '', 0, 1);
INSERT INTO `region`
VALUES (109, 10, '无锡市', '320200', '', 0, 1);
INSERT INTO `region`
VALUES (110, 10, '徐州市', '320300', '', 0, 1);
INSERT INTO `region`
VALUES (111, 10, '常州市', '320400', '', 0, 1);
INSERT INTO `region`
VALUES (112, 10, '苏州市', '320500', '', 0, 1);
INSERT INTO `region`
VALUES (113, 10, '南通市', '320600', '', 0, 1);
INSERT INTO `region`
VALUES (114, 10, '连云港市', '320700', '', 0, 1);
INSERT INTO `region`
VALUES (115, 10, '淮安市', '320800', '', 0, 1);
INSERT INTO `region`
VALUES (116, 10, '盐城市', '320900', '', 0, 1);
INSERT INTO `region`
VALUES (117, 10, '扬州市', '321000', '', 0, 1);
INSERT INTO `region`
VALUES (118, 10, '镇江市', '321100', '', 0, 1);
INSERT INTO `region`
VALUES (119, 10, '泰州市', '321200', '', 0, 1);
INSERT INTO `region`
VALUES (120, 10, '宿迁市', '321300', '', 0, 1);
INSERT INTO `region`
VALUES (121, 11, '杭州市', '330100', '', 0, 1);
INSERT INTO `region`
VALUES (122, 11, '宁波市', '330200', '', 0, 1);
INSERT INTO `region`
VALUES (123, 11, '温州市', '330300', '', 0, 1);
INSERT INTO `region`
VALUES (124, 11, '嘉兴市', '330400', '', 0, 1);
INSERT INTO `region`
VALUES (125, 11, '湖州市', '330500', '', 0, 1);
INSERT INTO `region`
VALUES (126, 11, '绍兴市', '330600', '', 0, 1);
INSERT INTO `region`
VALUES (127, 11, '金华市', '330700', '', 0, 1);
INSERT INTO `region`
VALUES (128, 11, '衢州市', '330800', '', 0, 1);
INSERT INTO `region`
VALUES (129, 11, '舟山市', '330900', '', 0, 1);
INSERT INTO `region`
VALUES (130, 11, '台州市', '331000', '', 0, 1);
INSERT INTO `region`
VALUES (131, 11, '丽水市', '331100', '', 0, 1);
INSERT INTO `region`
VALUES (132, 12, '合肥市', '340100', '', 0, 1);
INSERT INTO `region`
VALUES (133, 12, '芜湖市', '340200', '', 0, 1);
INSERT INTO `region`
VALUES (134, 12, '蚌埠市', '340300', '', 0, 1);
INSERT INTO `region`
VALUES (135, 12, '淮南市', '340400', '', 0, 1);
INSERT INTO `region`
VALUES (136, 12, '马鞍山市', '340500', '', 0, 1);
INSERT INTO `region`
VALUES (137, 12, '淮北市', '340600', '', 0, 1);
INSERT INTO `region`
VALUES (138, 12, '铜陵市', '340700', '', 0, 1);
INSERT INTO `region`
VALUES (139, 12, '安庆市', '340800', '', 0, 1);
INSERT INTO `region`
VALUES (140, 12, '黄山市', '341000', '', 0, 1);
INSERT INTO `region`
VALUES (141, 12, '滁州市', '341100', '', 0, 1);
INSERT INTO `region`
VALUES (142, 12, '阜阳市', '341200', '', 0, 1);
INSERT INTO `region`
VALUES (143, 12, '宿州市', '341300', '', 0, 1);
INSERT INTO `region`
VALUES (144, 12, '六安市', '341500', '', 0, 1);
INSERT INTO `region`
VALUES (145, 12, '亳州市', '341600', '', 0, 1);
INSERT INTO `region`
VALUES (146, 12, '池州市', '341700', '', 0, 1);
INSERT INTO `region`
VALUES (147, 12, '宣城市', '341800', '', 0, 1);
INSERT INTO `region`
VALUES (148, 13, '福州市', '350100', '', 0, 1);
INSERT INTO `region`
VALUES (149, 13, '厦门市', '350200', '', 0, 1);
INSERT INTO `region`
VALUES (150, 13, '莆田市', '350300', '', 0, 1);
INSERT INTO `region`
VALUES (151, 13, '三明市', '350400', '', 0, 1);
INSERT INTO `region`
VALUES (152, 13, '泉州市', '350500', '', 0, 1);
INSERT INTO `region`
VALUES (153, 13, '漳州市', '350600', '', 0, 1);
INSERT INTO `region`
VALUES (154, 13, '南平市', '350700', '', 0, 1);
INSERT INTO `region`
VALUES (155, 13, '龙岩市', '350800', '', 0, 1);
INSERT INTO `region`
VALUES (156, 13, '宁德市', '350900', '', 0, 1);
INSERT INTO `region`
VALUES (157, 14, '南昌市', '360100', '', 0, 1);
INSERT INTO `region`
VALUES (158, 14, '景德镇市', '360200', '', 0, 1);
INSERT INTO `region`
VALUES (159, 14, '萍乡市', '360300', '', 0, 1);
INSERT INTO `region`
VALUES (160, 14, '九江市', '360400', '', 0, 1);
INSERT INTO `region`
VALUES (161, 14, '新余市', '360500', '', 0, 1);
INSERT INTO `region`
VALUES (162, 14, '鹰潭市', '360600', '', 0, 1);
INSERT INTO `region`
VALUES (163, 14, '赣州市', '360700', '', 0, 1);
INSERT INTO `region`
VALUES (164, 14, '吉安市', '360800', '', 0, 1);
INSERT INTO `region`
VALUES (165, 14, '宜春市', '360900', '', 0, 1);
INSERT INTO `region`
VALUES (166, 14, '抚州市', '361000', '', 0, 1);
INSERT INTO `region`
VALUES (167, 14, '上饶市', '361100', '', 0, 1);
INSERT INTO `region`
VALUES (168, 15, '济南市', '370100', '', 0, 1);
INSERT INTO `region`
VALUES (169, 15, '青岛市', '370200', '', 0, 1);
INSERT INTO `region`
VALUES (170, 15, '淄博市', '370300', '', 0, 1);
INSERT INTO `region`
VALUES (171, 15, '枣庄市', '370400', '', 0, 1);
INSERT INTO `region`
VALUES (172, 15, '东营市', '370500', '', 0, 1);
INSERT INTO `region`
VALUES (173, 15, '烟台市', '370600', '', 0, 1);
INSERT INTO `region`
VALUES (174, 15, '潍坊市', '370700', '', 0, 1);
INSERT INTO `region`
VALUES (175, 15, '济宁市', '370800', '', 0, 1);
INSERT INTO `region`
VALUES (176, 15, '泰安市', '370900', '', 0, 1);
INSERT INTO `region`
VALUES (177, 15, '威海市', '371000', '', 0, 1);
INSERT INTO `region`
VALUES (178, 15, '日照市', '371100', '', 0, 1);
INSERT INTO `region`
VALUES (179, 15, '临沂市', '371300', '', 0, 1);
INSERT INTO `region`
VALUES (180, 15, '德州市', '371400', '', 0, 1);
INSERT INTO `region`
VALUES (181, 15, '聊城市', '371500', '', 0, 1);
INSERT INTO `region`
VALUES (182, 15, '滨州市', '371600', '', 0, 1);
INSERT INTO `region`
VALUES (183, 15, '菏泽市', '371700', '', 0, 1);
INSERT INTO `region`
VALUES (184, 16, '郑州市', '410100', '', 0, 1);
INSERT INTO `region`
VALUES (185, 16, '开封市', '410200', '', 0, 1);
INSERT INTO `region`
VALUES (186, 16, '洛阳市', '410300', '', 0, 1);
INSERT INTO `region`
VALUES (187, 16, '平顶山市', '410400', '', 0, 1);
INSERT INTO `region`
VALUES (188, 16, '安阳市', '410500', '', 0, 1);
INSERT INTO `region`
VALUES (189, 16, '鹤壁市', '410600', '', 0, 1);
INSERT INTO `region`
VALUES (190, 16, '新乡市', '410700', '', 0, 1);
INSERT INTO `region`
VALUES (191, 16, '焦作市', '410800', '', 0, 1);
INSERT INTO `region`
VALUES (192, 16, '濮阳市', '410900', '', 0, 1);
INSERT INTO `region`
VALUES (193, 16, '许昌市', '411000', '', 0, 1);
INSERT INTO `region`
VALUES (194, 16, '漯河市', '411100', '', 0, 1);
INSERT INTO `region`
VALUES (195, 16, '三门峡市', '411200', '', 0, 1);
INSERT INTO `region`
VALUES (196, 16, '南阳市', '411300', '', 0, 1);
INSERT INTO `region`
VALUES (197, 16, '商丘市', '411400', '', 0, 1);
INSERT INTO `region`
VALUES (198, 16, '信阳市', '411500', '', 0, 1);
INSERT INTO `region`
VALUES (199, 16, '周口市', '411600', '', 0, 1);
INSERT INTO `region`
VALUES (200, 16, '驻马店市', '411700', '', 0, 1);
INSERT INTO `region`
VALUES (201, 16, '济源市', '419001', '', 0, 1);
INSERT INTO `region`
VALUES (202, 17, '武汉市', '420100', '', 0, 1);
INSERT INTO `region`
VALUES (203, 17, '黄石市', '420200', '', 0, 1);
INSERT INTO `region`
VALUES (204, 17, '十堰市', '420300', '', 0, 1);
INSERT INTO `region`
VALUES (205, 17, '宜昌市', '420500', '', 0, 1);
INSERT INTO `region`
VALUES (206, 17, '襄阳市', '420600', '', 0, 1);
INSERT INTO `region`
VALUES (207, 17, '鄂州市', '420700', '', 0, 1);
INSERT INTO `region`
VALUES (208, 17, '荆门市', '420800', '', 0, 1);
INSERT INTO `region`
VALUES (209, 17, '孝感市', '420900', '', 0, 1);
INSERT INTO `region`
VALUES (210, 17, '荆州市', '421000', '', 0, 1);
INSERT INTO `region`
VALUES (211, 17, '黄冈市', '421100', '', 0, 1);
INSERT INTO `region`
VALUES (212, 17, '咸宁市', '421200', '', 0, 1);
INSERT INTO `region`
VALUES (213, 17, '随州市', '421300', '', 0, 1);
INSERT INTO `region`
VALUES (214, 17, '恩施土家族苗族自治州', '422800', '', 0, 1);
INSERT INTO `region`
VALUES (215, 17, '仙桃市', '429004', '', 0, 1);
INSERT INTO `region`
VALUES (216, 17, '潜江市', '429005', '', 0, 1);
INSERT INTO `region`
VALUES (217, 17, '天门市', '429006', '', 0, 1);
INSERT INTO `region`
VALUES (218, 17, '神农架林区', '429021', '', 0, 1);
INSERT INTO `region`
VALUES (219, 18, '长沙市', '430100', '', 0, 1);
INSERT INTO `region`
VALUES (220, 18, '株洲市', '430200', '', 0, 1);
INSERT INTO `region`
VALUES (221, 18, '湘潭市', '430300', '', 0, 1);
INSERT INTO `region`
VALUES (222, 18, '衡阳市', '430400', '', 0, 1);
INSERT INTO `region`
VALUES (223, 18, '邵阳市', '430500', '', 0, 1);
INSERT INTO `region`
VALUES (224, 18, '岳阳市', '430600', '', 0, 1);
INSERT INTO `region`
VALUES (225, 18, '常德市', '430700', '', 0, 1);
INSERT INTO `region`
VALUES (226, 18, '张家界市', '430800', '', 0, 1);
INSERT INTO `region`
VALUES (227, 18, '益阳市', '430900', '', 0, 1);
INSERT INTO `region`
VALUES (228, 18, '郴州市', '431000', '', 0, 1);
INSERT INTO `region`
VALUES (229, 18, '永州市', '431100', '', 0, 1);
INSERT INTO `region`
VALUES (230, 18, '怀化市', '431200', '', 0, 1);
INSERT INTO `region`
VALUES (231, 18, '娄底市', '431300', '', 0, 1);
INSERT INTO `region`
VALUES (232, 18, '湘西土家族苗族自治州', '433100', '', 0, 1);
INSERT INTO `region`
VALUES (233, 19, '广州市', '440100', '', 0, 1);
INSERT INTO `region`
VALUES (234, 19, '韶关市', '440200', '', 0, 1);
INSERT INTO `region`
VALUES (235, 19, '深圳市', '440300', '', 0, 1);
INSERT INTO `region`
VALUES (236, 19, '珠海市', '440400', '', 0, 1);
INSERT INTO `region`
VALUES (237, 19, '汕头市', '440500', '', 0, 1);
INSERT INTO `region`
VALUES (238, 19, '佛山市', '440600', '', 0, 1);
INSERT INTO `region`
VALUES (239, 19, '江门市', '440700', '', 0, 1);
INSERT INTO `region`
VALUES (240, 19, '湛江市', '440800', '', 0, 1);
INSERT INTO `region`
VALUES (241, 19, '茂名市', '440900', '', 0, 1);
INSERT INTO `region`
VALUES (242, 19, '肇庆市', '441200', '', 0, 1);
INSERT INTO `region`
VALUES (243, 19, '惠州市', '441300', '', 0, 1);
INSERT INTO `region`
VALUES (244, 19, '梅州市', '441400', '', 0, 1);
INSERT INTO `region`
VALUES (245, 19, '汕尾市', '441500', '', 0, 1);
INSERT INTO `region`
VALUES (246, 19, '河源市', '441600', '', 0, 1);
INSERT INTO `region`
VALUES (247, 19, '阳江市', '441700', '', 0, 1);
INSERT INTO `region`
VALUES (248, 19, '清远市', '441800', '', 0, 1);
INSERT INTO `region`
VALUES (249, 19, '东莞市', '441900', '', 0, 1);
INSERT INTO `region`
VALUES (250, 19, '中山市', '442000', '', 0, 1);
INSERT INTO `region`
VALUES (251, 19, '潮州市', '445100', '', 0, 1);
INSERT INTO `region`
VALUES (252, 19, '揭阳市', '445200', '', 0, 1);
INSERT INTO `region`
VALUES (253, 19, '云浮市', '445300', '', 0, 1);
INSERT INTO `region`
VALUES (254, 20, '南宁市', '450100', '', 0, 1);
INSERT INTO `region`
VALUES (255, 20, '柳州市', '450200', '', 0, 1);
INSERT INTO `region`
VALUES (256, 20, '桂林市', '450300', '', 0, 1);
INSERT INTO `region`
VALUES (257, 20, '梧州市', '450400', '', 0, 1);
INSERT INTO `region`
VALUES (258, 20, '北海市', '450500', '', 0, 1);
INSERT INTO `region`
VALUES (259, 20, '防城港市', '450600', '', 0, 1);
INSERT INTO `region`
VALUES (260, 20, '钦州市', '450700', '', 0, 1);
INSERT INTO `region`
VALUES (261, 20, '贵港市', '450800', '', 0, 1);
INSERT INTO `region`
VALUES (262, 20, '玉林市', '450900', '', 0, 1);
INSERT INTO `region`
VALUES (263, 20, '百色市', '451000', '', 0, 1);
INSERT INTO `region`
VALUES (264, 20, '贺州市', '451100', '', 0, 1);
INSERT INTO `region`
VALUES (265, 20, '河池市', '451200', '', 0, 1);
INSERT INTO `region`
VALUES (266, 20, '来宾市', '451300', '', 0, 1);
INSERT INTO `region`
VALUES (267, 20, '崇左市', '451400', '', 0, 1);
INSERT INTO `region`
VALUES (268, 21, '海口市', '460100', '', 0, 1);
INSERT INTO `region`
VALUES (269, 21, '三亚市', '460200', '', 0, 1);
INSERT INTO `region`
VALUES (270, 21, '三沙市', '460300', '', 0, 1);
INSERT INTO `region`
VALUES (271, 21, '儋州市', '460400', '', 0, 1);
INSERT INTO `region`
VALUES (272, 21, '五指山市', '469001', '', 0, 1);
INSERT INTO `region`
VALUES (273, 21, '琼海市', '469002', '', 0, 1);
INSERT INTO `region`
VALUES (274, 21, '文昌市', '469005', '', 0, 1);
INSERT INTO `region`
VALUES (275, 21, '万宁市', '469006', '', 0, 1);
INSERT INTO `region`
VALUES (276, 21, '东方市', '469007', '', 0, 1);
INSERT INTO `region`
VALUES (277, 21, '定安县', '469021', '', 0, 1);
INSERT INTO `region`
VALUES (278, 21, '屯昌县', '469022', '', 0, 1);
INSERT INTO `region`
VALUES (279, 21, '澄迈县', '469023', '', 0, 1);
INSERT INTO `region`
VALUES (280, 21, '临高县', '469024', '', 0, 1);
INSERT INTO `region`
VALUES (281, 21, '白沙黎族自治县', '469025', '', 0, 1);
INSERT INTO `region`
VALUES (282, 21, '昌江黎族自治县', '469026', '', 0, 1);
INSERT INTO `region`
VALUES (283, 21, '乐东黎族自治县', '469027', '', 0, 1);
INSERT INTO `region`
VALUES (284, 21, '陵水黎族自治县', '469028', '', 0, 1);
INSERT INTO `region`
VALUES (285, 21, '保亭黎族苗族自治县', '469029', '', 0, 1);
INSERT INTO `region`
VALUES (286, 21, '琼中黎族苗族自治县', '469030', '', 0, 1);
INSERT INTO `region`
VALUES (287, 22, '重庆市', '500000', '', 1, 1);
INSERT INTO `region`
VALUES (288, 23, '成都市', '510100', '', 0, 1);
INSERT INTO `region`
VALUES (289, 23, '自贡市', '510300', '', 0, 1);
INSERT INTO `region`
VALUES (290, 23, '攀枝花市', '510400', '', 0, 1);
INSERT INTO `region`
VALUES (291, 23, '泸州市', '510500', '', 0, 1);
INSERT INTO `region`
VALUES (292, 23, '德阳市', '510600', '', 0, 1);
INSERT INTO `region`
VALUES (293, 23, '绵阳市', '510700', '', 0, 1);
INSERT INTO `region`
VALUES (294, 23, '广元市', '510800', '', 0, 1);
INSERT INTO `region`
VALUES (295, 23, '遂宁市', '510900', '', 0, 1);
INSERT INTO `region`
VALUES (296, 23, '内江市', '511000', '', 0, 1);
INSERT INTO `region`
VALUES (297, 23, '乐山市', '511100', '', 0, 1);
INSERT INTO `region`
VALUES (298, 23, '南充市', '511300', '', 0, 1);
INSERT INTO `region`
VALUES (299, 23, '眉山市', '511400', '', 0, 1);
INSERT INTO `region`
VALUES (300, 23, '宜宾市', '511500', '', 0, 1);
INSERT INTO `region`
VALUES (301, 23, '广安市', '511600', '', 0, 1);
INSERT INTO `region`
VALUES (302, 23, '达州市', '511700', '', 0, 1);
INSERT INTO `region`
VALUES (303, 23, '雅安市', '511800', '', 0, 1);
INSERT INTO `region`
VALUES (304, 23, '巴中市', '511900', '', 0, 1);
INSERT INTO `region`
VALUES (305, 23, '资阳市', '512000', '', 0, 1);
INSERT INTO `region`
VALUES (306, 23, '阿坝藏族羌族自治州', '513200', '', 0, 1);
INSERT INTO `region`
VALUES (307, 23, '甘孜藏族自治州', '513300', '', 0, 1);
INSERT INTO `region`
VALUES (308, 23, '凉山彝族自治州', '513400', '', 0, 1);
INSERT INTO `region`
VALUES (309, 24, '贵阳市', '520100', '', 0, 1);
INSERT INTO `region`
VALUES (310, 24, '六盘水市', '520200', '', 0, 1);
INSERT INTO `region`
VALUES (311, 24, '遵义市', '520300', '', 0, 1);
INSERT INTO `region`
VALUES (312, 24, '安顺市', '520400', '', 0, 1);
INSERT INTO `region`
VALUES (313, 24, '毕节市', '520500', '', 0, 1);
INSERT INTO `region`
VALUES (314, 24, '铜仁市', '520600', '', 0, 1);
INSERT INTO `region`
VALUES (315, 24, '黔西南布依族苗族自治州', '522300', '', 0, 1);
INSERT INTO `region`
VALUES (316, 24, '黔东南苗族侗族自治州', '522600', '', 0, 1);
INSERT INTO `region`
VALUES (317, 24, '黔南布依族苗族自治州', '522700', '', 0, 1);
INSERT INTO `region`
VALUES (318, 25, '昆明市', '530100', '', 0, 1);
INSERT INTO `region`
VALUES (319, 25, '曲靖市', '530300', '', 0, 1);
INSERT INTO `region`
VALUES (320, 25, '玉溪市', '530400', '', 0, 1);
INSERT INTO `region`
VALUES (321, 25, '保山市', '530500', '', 0, 1);
INSERT INTO `region`
VALUES (322, 25, '昭通市', '530600', '', 0, 1);
INSERT INTO `region`
VALUES (323, 25, '丽江市', '530700', '', 0, 1);
INSERT INTO `region`
VALUES (324, 25, '普洱市', '530800', '', 0, 1);
INSERT INTO `region`
VALUES (325, 25, '临沧市', '530900', '', 0, 1);
INSERT INTO `region`
VALUES (326, 25, '楚雄彝族自治州', '532300', '', 0, 1);
INSERT INTO `region`
VALUES (327, 25, '红河哈尼族彝族自治州', '532500', '', 0, 1);
INSERT INTO `region`
VALUES (328, 25, '文山壮族苗族自治州', '532600', '', 0, 1);
INSERT INTO `region`
VALUES (329, 25, '西双版纳傣族自治州', '532800', '', 0, 1);
INSERT INTO `region`
VALUES (330, 25, '大理白族自治州', '532900', '', 0, 1);
INSERT INTO `region`
VALUES (331, 25, '德宏傣族景颇族自治州', '533100', '', 0, 1);
INSERT INTO `region`
VALUES (332, 25, '怒江傈僳族自治州', '533300', '', 0, 1);
INSERT INTO `region`
VALUES (333, 25, '迪庆藏族自治州', '533400', '', 0, 1);
INSERT INTO `region`
VALUES (334, 26, '拉萨市', '540100', '', 0, 1);
INSERT INTO `region`
VALUES (335, 26, '日喀则市', '540200', '', 0, 1);
INSERT INTO `region`
VALUES (336, 26, '昌都市', '540300', '', 0, 1);
INSERT INTO `region`
VALUES (337, 26, '林芝市', '540400', '', 0, 1);
INSERT INTO `region`
VALUES (338, 26, '山南市', '540500', '', 0, 1);
INSERT INTO `region`
VALUES (339, 26, '那曲市', '540600', '', 0, 1);
INSERT INTO `region`
VALUES (340, 26, '阿里地区', '542500', '', 0, 1);
INSERT INTO `region`
VALUES (341, 27, '西安市', '610100', '', 0, 1);
INSERT INTO `region`
VALUES (342, 27, '铜川市', '610200', '', 0, 1);
INSERT INTO `region`
VALUES (343, 27, '宝鸡市', '610300', '', 0, 1);
INSERT INTO `region`
VALUES (344, 27, '咸阳市', '610400', '', 0, 1);
INSERT INTO `region`
VALUES (345, 27, '渭南市', '610500', '', 0, 1);
INSERT INTO `region`
VALUES (346, 27, '延安市', '610600', '', 0, 1);
INSERT INTO `region`
VALUES (347, 27, '汉中市', '610700', '', 0, 1);
INSERT INTO `region`
VALUES (348, 27, '榆林市', '610800', '', 0, 1);
INSERT INTO `region`
VALUES (349, 27, '安康市', '610900', '', 0, 1);
INSERT INTO `region`
VALUES (350, 27, '商洛市', '611000', '', 0, 1);
INSERT INTO `region`
VALUES (351, 28, '兰州市', '620100', '', 0, 1);
INSERT INTO `region`
VALUES (352, 28, '嘉峪关市', '620200', '', 0, 1);
INSERT INTO `region`
VALUES (353, 28, '金昌市', '620300', '', 0, 1);
INSERT INTO `region`
VALUES (354, 28, '白银市', '620400', '', 0, 1);
INSERT INTO `region`
VALUES (355, 28, '天水市', '620500', '', 0, 1);
INSERT INTO `region`
VALUES (356, 28, '武威市', '620600', '', 0, 1);
INSERT INTO `region`
VALUES (357, 28, '张掖市', '620700', '', 0, 1);
INSERT INTO `region`
VALUES (358, 28, '平凉市', '620800', '', 0, 1);
INSERT INTO `region`
VALUES (359, 28, '酒泉市', '620900', '', 0, 1);
INSERT INTO `region`
VALUES (360, 28, '庆阳市', '621000', '', 0, 1);
INSERT INTO `region`
VALUES (361, 28, '定西市', '621100', '', 0, 1);
INSERT INTO `region`
VALUES (362, 28, '陇南市', '621200', '', 0, 1);
INSERT INTO `region`
VALUES (363, 28, '临夏回族自治州', '622900', '', 0, 1);
INSERT INTO `region`
VALUES (364, 28, '甘南藏族自治州', '623000', '', 0, 1);
INSERT INTO `region`
VALUES (365, 29, '西宁市', '630100', '', 0, 1);
INSERT INTO `region`
VALUES (366, 29, '海东市', '630200', '', 0, 1);
INSERT INTO `region`
VALUES (367, 29, '海北藏族自治州', '632200', '', 0, 1);
INSERT INTO `region`
VALUES (368, 29, '黄南藏族自治州', '632300', '', 0, 1);
INSERT INTO `region`
VALUES (369, 29, '海南藏族自治州', '632500', '', 0, 1);
INSERT INTO `region`
VALUES (370, 29, '果洛藏族自治州', '632600', '', 0, 1);
INSERT INTO `region`
VALUES (371, 29, '玉树藏族自治州', '632700', '', 0, 1);
INSERT INTO `region`
VALUES (372, 29, '海西蒙古族藏族自治州', '632800', '', 0, 1);
INSERT INTO `region`
VALUES (373, 30, '银川市', '640100', '', 0, 1);
INSERT INTO `region`
VALUES (374, 30, '石嘴山市', '640200', '', 0, 1);
INSERT INTO `region`
VALUES (375, 30, '吴忠市', '640300', '', 0, 1);
INSERT INTO `region`
VALUES (376, 30, '固原市', '640400', '', 0, 1);
INSERT INTO `region`
VALUES (377, 30, '中卫市', '640500', '', 0, 1);
INSERT INTO `region`
VALUES (378, 31, '乌鲁木齐市', '650100', '', 0, 1);
INSERT INTO `region`
VALUES (379, 31, '克拉玛依市', '650200', '', 0, 1);
INSERT INTO `region`
VALUES (380, 31, '吐鲁番市', '650400', '', 0, 1);
INSERT INTO `region`
VALUES (381, 31, '哈密市', '650500', '', 0, 1);
INSERT INTO `region`
VALUES (382, 31, '昌吉回族自治州', '652300', '', 0, 1);
INSERT INTO `region`
VALUES (383, 31, '博尔塔拉蒙古自治州', '652700', '', 0, 1);
INSERT INTO `region`
VALUES (384, 31, '巴音郭楞蒙古自治州', '652800', '', 0, 1);
INSERT INTO `region`
VALUES (385, 31, '阿克苏地区', '652900', '', 0, 1);
INSERT INTO `region`
VALUES (386, 31, '克孜勒苏柯尔克孜自治州', '653000', '', 0, 1);
INSERT INTO `region`
VALUES (387, 31, '喀什地区', '653100', '', 0, 1);
INSERT INTO `region`
VALUES (388, 31, '和田地区', '653200', '', 0, 1);
INSERT INTO `region`
VALUES (389, 31, '伊犁哈萨克自治州', '654000', '', 0, 1);
INSERT INTO `region`
VALUES (390, 31, '塔城地区', '654200', '', 0, 1);
INSERT INTO `region`
VALUES (391, 31, '阿勒泰地区', '654300', '', 0, 1);
INSERT INTO `region`
VALUES (392, 31, '胡杨河市', '659000', '', 0, 1);
INSERT INTO `region`
VALUES (393, 31, '石河子市', '659001', '', 0, 1);
INSERT INTO `region`
VALUES (394, 31, '阿拉尔市', '659002', '', 0, 1);
INSERT INTO `region`
VALUES (395, 31, '图木舒克市', '659003', '', 0, 1);
INSERT INTO `region`
VALUES (396, 31, '五家渠市', '659004', '', 0, 1);
INSERT INTO `region`
VALUES (397, 31, '北屯市', '659005', '', 0, 1);
INSERT INTO `region`
VALUES (398, 31, '铁门关市', '659006', '', 0, 1);
INSERT INTO `region`
VALUES (399, 31, '双河市', '659007', '', 0, 1);
INSERT INTO `region`
VALUES (400, 31, '可克达拉市', '659008', '', 0, 1);
INSERT INTO `region`
VALUES (401, 31, '昆玉市', '659009', '', 0, 1);
INSERT INTO `region`
VALUES (402, 32, '香港特别行政区', '810000', '', 0, 1);
INSERT INTO `region`
VALUES (403, 33, '澳门特别行政区', '820000', '', 0, 1);
INSERT INTO `region`
VALUES (404, 34, '台北市', '710100', '', 0, 1);
INSERT INTO `region`
VALUES (405, 34, '高雄市', '710200', '', 0, 1);
INSERT INTO `region`
VALUES (406, 34, '新北市', '710300', '', 0, 1);
INSERT INTO `region`
VALUES (407, 34, '台中市', '710400', '', 0, 1);
INSERT INTO `region`
VALUES (408, 34, '台南市', '710500', '', 0, 1);
INSERT INTO `region`
VALUES (409, 34, '桃园市', '710600', '', 0, 1);
INSERT INTO `region`
VALUES (410, 34, '基隆市', '719001', '', 0, 1);
INSERT INTO `region`
VALUES (411, 34, '新竹市', '719002', '', 0, 1);
INSERT INTO `region`
VALUES (412, 34, '嘉义市', '719003', '', 0, 1);
INSERT INTO `region`
VALUES (413, 34, '新竹县', '719004', '', 0, 1);
INSERT INTO `region`
VALUES (414, 34, '宜兰县', '719005', '', 0, 1);
INSERT INTO `region`
VALUES (415, 34, '苗栗县', '719006', '', 0, 1);
INSERT INTO `region`
VALUES (416, 34, '彰化县', '719007', '', 0, 1);
INSERT INTO `region`
VALUES (417, 34, '云林县', '719008', '', 0, 1);
INSERT INTO `region`
VALUES (418, 34, '南投县', '719009', '', 0, 1);
INSERT INTO `region`
VALUES (419, 34, '嘉义县', '719010', '', 0, 1);
INSERT INTO `region`
VALUES (420, 34, '屏东县', '719011', '', 0, 1);
INSERT INTO `region`
VALUES (421, 34, '台东县', '719012', '', 0, 1);
INSERT INTO `region`
VALUES (422, 34, '花莲县', '719013', '', 0, 1);
INSERT INTO `region`
VALUES (423, 34, '澎湖县', '719014', '', 0, 1);
INSERT INTO `region`
VALUES (424, 35, '东城区', '110101', '', 0, 2);
INSERT INTO `region`
VALUES (425, 35, '西城区', '110102', '', 0, 2);
INSERT INTO `region`
VALUES (426, 35, '朝阳区', '110105', '', 0, 2);
INSERT INTO `region`
VALUES (427, 35, '丰台区', '110106', '', 0, 2);
INSERT INTO `region`
VALUES (428, 35, '石景山区', '110107', '', 0, 2);
INSERT INTO `region`
VALUES (429, 35, '海淀区', '110108', '', 0, 2);
INSERT INTO `region`
VALUES (430, 35, '门头沟区', '110109', '', 0, 2);
INSERT INTO `region`
VALUES (431, 35, '房山区', '110111', '', 0, 2);
INSERT INTO `region`
VALUES (432, 35, '通州区', '110112', '', 0, 2);
INSERT INTO `region`
VALUES (433, 35, '顺义区', '110113', '', 0, 2);
INSERT INTO `region`
VALUES (434, 35, '昌平区', '110114', '', 0, 2);
INSERT INTO `region`
VALUES (435, 35, '大兴区', '110115', '', 0, 2);
INSERT INTO `region`
VALUES (436, 35, '怀柔区', '110116', '', 0, 2);
INSERT INTO `region`
VALUES (437, 35, '平谷区', '110117', '', 0, 2);
INSERT INTO `region`
VALUES (438, 35, '密云区', '110118', '', 0, 2);
INSERT INTO `region`
VALUES (439, 35, '延庆区', '110119', '', 0, 2);
INSERT INTO `region`
VALUES (440, 36, '和平区', '120101', '', 0, 2);
INSERT INTO `region`
VALUES (441, 36, '河东区', '120102', '', 0, 2);
INSERT INTO `region`
VALUES (442, 36, '河西区', '120103', '', 0, 2);
INSERT INTO `region`
VALUES (443, 36, '南开区', '120104', '', 0, 2);
INSERT INTO `region`
VALUES (444, 36, '河北区', '120105', '', 0, 2);
INSERT INTO `region`
VALUES (445, 36, '红桥区', '120106', '', 0, 2);
INSERT INTO `region`
VALUES (446, 36, '东丽区', '120110', '', 0, 2);
INSERT INTO `region`
VALUES (447, 36, '西青区', '120111', '', 0, 2);
INSERT INTO `region`
VALUES (448, 36, '津南区', '120112', '', 0, 2);
INSERT INTO `region`
VALUES (449, 36, '北辰区', '120113', '', 0, 2);
INSERT INTO `region`
VALUES (450, 36, '武清区', '120114', '', 0, 2);
INSERT INTO `region`
VALUES (451, 36, '宝坻区', '120115', '', 0, 2);
INSERT INTO `region`
VALUES (452, 36, '滨海新区', '120116', '', 0, 2);
INSERT INTO `region`
VALUES (453, 36, '宁河区', '120117', '', 0, 2);
INSERT INTO `region`
VALUES (454, 36, '静海区', '120118', '', 0, 2);
INSERT INTO `region`
VALUES (455, 36, '蓟州区', '120119', '', 0, 2);
INSERT INTO `region`
VALUES (456, 37, '长安区', '130102', '', 0, 2);
INSERT INTO `region`
VALUES (457, 37, '桥西区', '130104', '', 0, 2);
INSERT INTO `region`
VALUES (458, 37, '新华区', '130105', '', 0, 2);
INSERT INTO `region`
VALUES (459, 37, '井陉矿区', '130107', '', 0, 2);
INSERT INTO `region`
VALUES (460, 37, '裕华区', '130108', '', 0, 2);
INSERT INTO `region`
VALUES (461, 37, '藁城区', '130109', '', 0, 2);
INSERT INTO `region`
VALUES (462, 37, '鹿泉区', '130110', '', 0, 2);
INSERT INTO `region`
VALUES (463, 37, '栾城区', '130111', '', 0, 2);
INSERT INTO `region`
VALUES (464, 37, '井陉县', '130121', '', 0, 2);
INSERT INTO `region`
VALUES (465, 37, '正定县', '130123', '', 0, 2);
INSERT INTO `region`
VALUES (466, 37, '行唐县', '130125', '', 0, 2);
INSERT INTO `region`
VALUES (467, 37, '灵寿县', '130126', '', 0, 2);
INSERT INTO `region`
VALUES (468, 37, '高邑县', '130127', '', 0, 2);
INSERT INTO `region`
VALUES (469, 37, '深泽县', '130128', '', 0, 2);
INSERT INTO `region`
VALUES (470, 37, '赞皇县', '130129', '', 0, 2);
INSERT INTO `region`
VALUES (471, 37, '无极县', '130130', '', 0, 2);
INSERT INTO `region`
VALUES (472, 37, '平山县', '130131', '', 0, 2);
INSERT INTO `region`
VALUES (473, 37, '元氏县', '130132', '', 0, 2);
INSERT INTO `region`
VALUES (474, 37, '赵县', '130133', '', 0, 2);
INSERT INTO `region`
VALUES (475, 37, '辛集市', '130181', '', 0, 2);
INSERT INTO `region`
VALUES (476, 37, '晋州市', '130183', '', 0, 2);
INSERT INTO `region`
VALUES (477, 37, '新乐市', '130184', '', 0, 2);
INSERT INTO `region`
VALUES (478, 38, '路南区', '130202', '', 0, 2);
INSERT INTO `region`
VALUES (479, 38, '路北区', '130203', '', 0, 2);
INSERT INTO `region`
VALUES (480, 38, '古冶区', '130204', '', 0, 2);
INSERT INTO `region`
VALUES (481, 38, '开平区', '130205', '', 0, 2);
INSERT INTO `region`
VALUES (482, 38, '丰南区', '130207', '', 0, 2);
INSERT INTO `region`
VALUES (483, 38, '丰润区', '130208', '', 0, 2);
INSERT INTO `region`
VALUES (484, 38, '曹妃甸区', '130209', '', 0, 2);
INSERT INTO `region`
VALUES (485, 38, '滦南县', '130224', '', 0, 2);
INSERT INTO `region`
VALUES (486, 38, '乐亭县', '130225', '', 0, 2);
INSERT INTO `region`
VALUES (487, 38, '迁西县', '130227', '', 0, 2);
INSERT INTO `region`
VALUES (488, 38, '玉田县', '130229', '', 0, 2);
INSERT INTO `region`
VALUES (489, 38, '遵化市', '130281', '', 0, 2);
INSERT INTO `region`
VALUES (490, 38, '迁安市', '130283', '', 0, 2);
INSERT INTO `region`
VALUES (491, 38, '滦州市', '130284', '', 0, 2);
INSERT INTO `region`
VALUES (492, 39, '海港区', '130302', '', 0, 2);
INSERT INTO `region`
VALUES (493, 39, '山海关区', '130303', '', 0, 2);
INSERT INTO `region`
VALUES (494, 39, '北戴河区', '130304', '', 0, 2);
INSERT INTO `region`
VALUES (495, 39, '抚宁区', '130306', '', 0, 2);
INSERT INTO `region`
VALUES (496, 39, '青龙满族自治县', '130321', '', 0, 2);
INSERT INTO `region`
VALUES (497, 39, '昌黎县', '130322', '', 0, 2);
INSERT INTO `region`
VALUES (498, 39, '卢龙县', '130324', '', 0, 2);
INSERT INTO `region`
VALUES (499, 40, '邯山区', '130402', '', 0, 2);
INSERT INTO `region`
VALUES (500, 40, '丛台区', '130403', '', 0, 2);
INSERT INTO `region`
VALUES (501, 40, '复兴区', '130404', '', 0, 2);
INSERT INTO `region`
VALUES (502, 40, '峰峰矿区', '130406', '', 0, 2);
INSERT INTO `region`
VALUES (503, 40, '肥乡区', '130407', '', 0, 2);
INSERT INTO `region`
VALUES (504, 40, '永年区', '130408', '', 0, 2);
INSERT INTO `region`
VALUES (505, 40, '临漳县', '130423', '', 0, 2);
INSERT INTO `region`
VALUES (506, 40, '成安县', '130424', '', 0, 2);
INSERT INTO `region`
VALUES (507, 40, '大名县', '130425', '', 0, 2);
INSERT INTO `region`
VALUES (508, 40, '涉县', '130426', '', 0, 2);
INSERT INTO `region`
VALUES (509, 40, '磁县', '130427', '', 0, 2);
INSERT INTO `region`
VALUES (510, 40, '邱县', '130430', '', 0, 2);
INSERT INTO `region`
VALUES (511, 40, '鸡泽县', '130431', '', 0, 2);
INSERT INTO `region`
VALUES (512, 40, '广平县', '130432', '', 0, 2);
INSERT INTO `region`
VALUES (513, 40, '馆陶县', '130433', '', 0, 2);
INSERT INTO `region`
VALUES (514, 40, '魏县', '130434', '', 0, 2);
INSERT INTO `region`
VALUES (515, 40, '曲周县', '130435', '', 0, 2);
INSERT INTO `region`
VALUES (516, 40, '武安市', '130481', '', 0, 2);
INSERT INTO `region`
VALUES (517, 41, '襄都区', '130502', '', 0, 2);
INSERT INTO `region`
VALUES (518, 41, '信都区', '130503', '', 0, 2);
INSERT INTO `region`
VALUES (519, 41, '任泽区', '130505', '', 0, 2);
INSERT INTO `region`
VALUES (520, 41, '南和区', '130506', '', 0, 2);
INSERT INTO `region`
VALUES (521, 41, '临城县', '130522', '', 0, 2);
INSERT INTO `region`
VALUES (522, 41, '内丘县', '130523', '', 0, 2);
INSERT INTO `region`
VALUES (523, 41, '柏乡县', '130524', '', 0, 2);
INSERT INTO `region`
VALUES (524, 41, '隆尧县', '130525', '', 0, 2);
INSERT INTO `region`
VALUES (525, 41, '宁晋县', '130528', '', 0, 2);
INSERT INTO `region`
VALUES (526, 41, '巨鹿县', '130529', '', 0, 2);
INSERT INTO `region`
VALUES (527, 41, '新河县', '130530', '', 0, 2);
INSERT INTO `region`
VALUES (528, 41, '广宗县', '130531', '', 0, 2);
INSERT INTO `region`
VALUES (529, 41, '平乡县', '130532', '', 0, 2);
INSERT INTO `region`
VALUES (530, 41, '威县', '130533', '', 0, 2);
INSERT INTO `region`
VALUES (531, 41, '清河县', '130534', '', 0, 2);
INSERT INTO `region`
VALUES (532, 41, '临西县', '130535', '', 0, 2);
INSERT INTO `region`
VALUES (533, 41, '南宫市', '130581', '', 0, 2);
INSERT INTO `region`
VALUES (534, 41, '沙河市', '130582', '', 0, 2);
INSERT INTO `region`
VALUES (535, 42, '竞秀区', '130602', '', 0, 2);
INSERT INTO `region`
VALUES (536, 42, '莲池区', '130606', '', 0, 2);
INSERT INTO `region`
VALUES (537, 42, '满城区', '130607', '', 0, 2);
INSERT INTO `region`
VALUES (538, 42, '清苑区', '130608', '', 0, 2);
INSERT INTO `region`
VALUES (539, 42, '徐水区', '130609', '', 0, 2);
INSERT INTO `region`
VALUES (540, 42, '涞水县', '130623', '', 0, 2);
INSERT INTO `region`
VALUES (541, 42, '阜平县', '130624', '', 0, 2);
INSERT INTO `region`
VALUES (542, 42, '定兴县', '130626', '', 0, 2);
INSERT INTO `region`
VALUES (543, 42, '唐县', '130627', '', 0, 2);
INSERT INTO `region`
VALUES (544, 42, '高阳县', '130628', '', 0, 2);
INSERT INTO `region`
VALUES (545, 42, '容城县', '130629', '', 0, 2);
INSERT INTO `region`
VALUES (546, 42, '涞源县', '130630', '', 0, 2);
INSERT INTO `region`
VALUES (547, 42, '望都县', '130631', '', 0, 2);
INSERT INTO `region`
VALUES (548, 42, '安新县', '130632', '', 0, 2);
INSERT INTO `region`
VALUES (549, 42, '易县', '130633', '', 0, 2);
INSERT INTO `region`
VALUES (550, 42, '曲阳县', '130634', '', 0, 2);
INSERT INTO `region`
VALUES (551, 42, '蠡县', '130635', '', 0, 2);
INSERT INTO `region`
VALUES (552, 42, '顺平县', '130636', '', 0, 2);
INSERT INTO `region`
VALUES (553, 42, '博野县', '130637', '', 0, 2);
INSERT INTO `region`
VALUES (554, 42, '雄县', '130638', '', 0, 2);
INSERT INTO `region`
VALUES (555, 42, '涿州市', '130681', '', 0, 2);
INSERT INTO `region`
VALUES (556, 42, '定州市', '130682', '', 0, 2);
INSERT INTO `region`
VALUES (557, 42, '安国市', '130683', '', 0, 2);
INSERT INTO `region`
VALUES (558, 42, '高碑店市', '130684', '', 0, 2);
INSERT INTO `region`
VALUES (559, 43, '桥东区', '130702', '', 0, 2);
INSERT INTO `region`
VALUES (560, 43, '桥西区', '130703', '', 0, 2);
INSERT INTO `region`
VALUES (561, 43, '宣化区', '130705', '', 0, 2);
INSERT INTO `region`
VALUES (562, 43, '下花园区', '130706', '', 0, 2);
INSERT INTO `region`
VALUES (563, 43, '万全区', '130708', '', 0, 2);
INSERT INTO `region`
VALUES (564, 43, '崇礼区', '130709', '', 0, 2);
INSERT INTO `region`
VALUES (565, 43, '张北县', '130722', '', 0, 2);
INSERT INTO `region`
VALUES (566, 43, '康保县', '130723', '', 0, 2);
INSERT INTO `region`
VALUES (567, 43, '沽源县', '130724', '', 0, 2);
INSERT INTO `region`
VALUES (568, 43, '尚义县', '130725', '', 0, 2);
INSERT INTO `region`
VALUES (569, 43, '蔚县', '130726', '', 0, 2);
INSERT INTO `region`
VALUES (570, 43, '阳原县', '130727', '', 0, 2);
INSERT INTO `region`
VALUES (571, 43, '怀安县', '130728', '', 0, 2);
INSERT INTO `region`
VALUES (572, 43, '怀来县', '130730', '', 0, 2);
INSERT INTO `region`
VALUES (573, 43, '涿鹿县', '130731', '', 0, 2);
INSERT INTO `region`
VALUES (574, 43, '赤城县', '130732', '', 0, 2);
INSERT INTO `region`
VALUES (575, 44, '双桥区', '130802', '', 0, 2);
INSERT INTO `region`
VALUES (576, 44, '双滦区', '130803', '', 0, 2);
INSERT INTO `region`
VALUES (577, 44, '鹰手营子矿区', '130804', '', 0, 2);
INSERT INTO `region`
VALUES (578, 44, '承德县', '130821', '', 0, 2);
INSERT INTO `region`
VALUES (579, 44, '兴隆县', '130822', '', 0, 2);
INSERT INTO `region`
VALUES (580, 44, '滦平县', '130824', '', 0, 2);
INSERT INTO `region`
VALUES (581, 44, '隆化县', '130825', '', 0, 2);
INSERT INTO `region`
VALUES (582, 44, '丰宁满族自治县', '130826', '', 0, 2);
INSERT INTO `region`
VALUES (583, 44, '宽城满族自治县', '130827', '', 0, 2);
INSERT INTO `region`
VALUES (584, 44, '围场满族蒙古族自治县', '130828', '', 0, 2);
INSERT INTO `region`
VALUES (585, 44, '平泉市', '130881', '', 0, 2);
INSERT INTO `region`
VALUES (586, 45, '新华区', '130902', '', 0, 2);
INSERT INTO `region`
VALUES (587, 45, '运河区', '130903', '', 0, 2);
INSERT INTO `region`
VALUES (588, 45, '沧县', '130921', '', 0, 2);
INSERT INTO `region`
VALUES (589, 45, '青县', '130922', '', 0, 2);
INSERT INTO `region`
VALUES (590, 45, '东光县', '130923', '', 0, 2);
INSERT INTO `region`
VALUES (591, 45, '海兴县', '130924', '', 0, 2);
INSERT INTO `region`
VALUES (592, 45, '盐山县', '130925', '', 0, 2);
INSERT INTO `region`
VALUES (593, 45, '肃宁县', '130926', '', 0, 2);
INSERT INTO `region`
VALUES (594, 45, '南皮县', '130927', '', 0, 2);
INSERT INTO `region`
VALUES (595, 45, '吴桥县', '130928', '', 0, 2);
INSERT INTO `region`
VALUES (596, 45, '献县', '130929', '', 0, 2);
INSERT INTO `region`
VALUES (597, 45, '孟村回族自治县', '130930', '', 0, 2);
INSERT INTO `region`
VALUES (598, 45, '泊头市', '130981', '', 0, 2);
INSERT INTO `region`
VALUES (599, 45, '任丘市', '130982', '', 0, 2);
INSERT INTO `region`
VALUES (600, 45, '黄骅市', '130983', '', 0, 2);
INSERT INTO `region`
VALUES (601, 45, '河间市', '130984', '', 0, 2);
INSERT INTO `region`
VALUES (602, 46, '安次区', '131002', '', 0, 2);
INSERT INTO `region`
VALUES (603, 46, '广阳区', '131003', '', 0, 2);
INSERT INTO `region`
VALUES (604, 46, '固安县', '131022', '', 0, 2);
INSERT INTO `region`
VALUES (605, 46, '永清县', '131023', '', 0, 2);
INSERT INTO `region`
VALUES (606, 46, '香河县', '131024', '', 0, 2);
INSERT INTO `region`
VALUES (607, 46, '大城县', '131025', '', 0, 2);
INSERT INTO `region`
VALUES (608, 46, '文安县', '131026', '', 0, 2);
INSERT INTO `region`
VALUES (609, 46, '大厂回族自治县', '131028', '', 0, 2);
INSERT INTO `region`
VALUES (610, 46, '霸州市', '131081', '', 0, 2);
INSERT INTO `region`
VALUES (611, 46, '三河市', '131082', '', 0, 2);
INSERT INTO `region`
VALUES (612, 47, '桃城区', '131102', '', 0, 2);
INSERT INTO `region`
VALUES (613, 47, '冀州区', '131103', '', 0, 2);
INSERT INTO `region`
VALUES (614, 47, '枣强县', '131121', '', 0, 2);
INSERT INTO `region`
VALUES (615, 47, '武邑县', '131122', '', 0, 2);
INSERT INTO `region`
VALUES (616, 47, '武强县', '131123', '', 0, 2);
INSERT INTO `region`
VALUES (617, 47, '饶阳县', '131124', '', 0, 2);
INSERT INTO `region`
VALUES (618, 47, '安平县', '131125', '', 0, 2);
INSERT INTO `region`
VALUES (619, 47, '故城县', '131126', '', 0, 2);
INSERT INTO `region`
VALUES (620, 47, '景县', '131127', '', 0, 2);
INSERT INTO `region`
VALUES (621, 47, '阜城县', '131128', '', 0, 2);
INSERT INTO `region`
VALUES (622, 47, '深州市', '131182', '', 0, 2);
INSERT INTO `region`
VALUES (623, 48, '小店区', '140105', '', 0, 2);
INSERT INTO `region`
VALUES (624, 48, '迎泽区', '140106', '', 0, 2);
INSERT INTO `region`
VALUES (625, 48, '杏花岭区', '140107', '', 0, 2);
INSERT INTO `region`
VALUES (626, 48, '尖草坪区', '140108', '', 0, 2);
INSERT INTO `region`
VALUES (627, 48, '万柏林区', '140109', '', 0, 2);
INSERT INTO `region`
VALUES (628, 48, '晋源区', '140110', '', 0, 2);
INSERT INTO `region`
VALUES (629, 48, '清徐县', '140121', '', 0, 2);
INSERT INTO `region`
VALUES (630, 48, '阳曲县', '140122', '', 0, 2);
INSERT INTO `region`
VALUES (631, 48, '娄烦县', '140123', '', 0, 2);
INSERT INTO `region`
VALUES (632, 48, '古交市', '140181', '', 0, 2);
INSERT INTO `region`
VALUES (633, 49, '新荣区', '140212', '', 0, 2);
INSERT INTO `region`
VALUES (634, 49, '平城区', '140213', '', 0, 2);
INSERT INTO `region`
VALUES (635, 49, '云冈区', '140214', '', 0, 2);
INSERT INTO `region`
VALUES (636, 49, '云州区', '140215', '', 0, 2);
INSERT INTO `region`
VALUES (637, 49, '阳高县', '140221', '', 0, 2);
INSERT INTO `region`
VALUES (638, 49, '天镇县', '140222', '', 0, 2);
INSERT INTO `region`
VALUES (639, 49, '广灵县', '140223', '', 0, 2);
INSERT INTO `region`
VALUES (640, 49, '灵丘县', '140224', '', 0, 2);
INSERT INTO `region`
VALUES (641, 49, '浑源县', '140225', '', 0, 2);
INSERT INTO `region`
VALUES (642, 49, '左云县', '140226', '', 0, 2);
INSERT INTO `region`
VALUES (643, 50, '城区', '140302', '', 0, 2);
INSERT INTO `region`
VALUES (644, 50, '矿区', '140303', '', 0, 2);
INSERT INTO `region`
VALUES (645, 50, '郊区', '140311', '', 0, 2);
INSERT INTO `region`
VALUES (646, 50, '平定县', '140321', '', 0, 2);
INSERT INTO `region`
VALUES (647, 50, '盂县', '140322', '', 0, 2);
INSERT INTO `region`
VALUES (648, 51, '潞州区', '140403', '', 0, 2);
INSERT INTO `region`
VALUES (649, 51, '上党区', '140404', '', 0, 2);
INSERT INTO `region`
VALUES (650, 51, '屯留区', '140405', '', 0, 2);
INSERT INTO `region`
VALUES (651, 51, '潞城区', '140406', '', 0, 2);
INSERT INTO `region`
VALUES (652, 51, '襄垣县', '140423', '', 0, 2);
INSERT INTO `region`
VALUES (653, 51, '平顺县', '140425', '', 0, 2);
INSERT INTO `region`
VALUES (654, 51, '黎城县', '140426', '', 0, 2);
INSERT INTO `region`
VALUES (655, 51, '壶关县', '140427', '', 0, 2);
INSERT INTO `region`
VALUES (656, 51, '长子县', '140428', '', 0, 2);
INSERT INTO `region`
VALUES (657, 51, '武乡县', '140429', '', 0, 2);
INSERT INTO `region`
VALUES (658, 51, '沁县', '140430', '', 0, 2);
INSERT INTO `region`
VALUES (659, 51, '沁源县', '140431', '', 0, 2);
INSERT INTO `region`
VALUES (660, 52, '城区', '140502', '', 0, 2);
INSERT INTO `region`
VALUES (661, 52, '沁水县', '140521', '', 0, 2);
INSERT INTO `region`
VALUES (662, 52, '阳城县', '140522', '', 0, 2);
INSERT INTO `region`
VALUES (663, 52, '陵川县', '140524', '', 0, 2);
INSERT INTO `region`
VALUES (664, 52, '泽州县', '140525', '', 0, 2);
INSERT INTO `region`
VALUES (665, 52, '高平市', '140581', '', 0, 2);
INSERT INTO `region`
VALUES (666, 53, '朔城区', '140602', '', 0, 2);
INSERT INTO `region`
VALUES (667, 53, '平鲁区', '140603', '', 0, 2);
INSERT INTO `region`
VALUES (668, 53, '山阴县', '140621', '', 0, 2);
INSERT INTO `region`
VALUES (669, 53, '应县', '140622', '', 0, 2);
INSERT INTO `region`
VALUES (670, 53, '右玉县', '140623', '', 0, 2);
INSERT INTO `region`
VALUES (671, 53, '怀仁市', '140681', '', 0, 2);
INSERT INTO `region`
VALUES (672, 54, '榆次区', '140702', '', 0, 2);
INSERT INTO `region`
VALUES (673, 54, '太谷区', '140703', '', 0, 2);
INSERT INTO `region`
VALUES (674, 54, '榆社县', '140721', '', 0, 2);
INSERT INTO `region`
VALUES (675, 54, '左权县', '140722', '', 0, 2);
INSERT INTO `region`
VALUES (676, 54, '和顺县', '140723', '', 0, 2);
INSERT INTO `region`
VALUES (677, 54, '昔阳县', '140724', '', 0, 2);
INSERT INTO `region`
VALUES (678, 54, '寿阳县', '140725', '', 0, 2);
INSERT INTO `region`
VALUES (679, 54, '祁县', '140727', '', 0, 2);
INSERT INTO `region`
VALUES (680, 54, '平遥县', '140728', '', 0, 2);
INSERT INTO `region`
VALUES (681, 54, '灵石县', '140729', '', 0, 2);
INSERT INTO `region`
VALUES (682, 54, '介休市', '140781', '', 0, 2);
INSERT INTO `region`
VALUES (683, 55, '盐湖区', '140802', '', 0, 2);
INSERT INTO `region`
VALUES (684, 55, '临猗县', '140821', '', 0, 2);
INSERT INTO `region`
VALUES (685, 55, '万荣县', '140822', '', 0, 2);
INSERT INTO `region`
VALUES (686, 55, '闻喜县', '140823', '', 0, 2);
INSERT INTO `region`
VALUES (687, 55, '稷山县', '140824', '', 0, 2);
INSERT INTO `region`
VALUES (688, 55, '新绛县', '140825', '', 0, 2);
INSERT INTO `region`
VALUES (689, 55, '绛县', '140826', '', 0, 2);
INSERT INTO `region`
VALUES (690, 55, '垣曲县', '140827', '', 0, 2);
INSERT INTO `region`
VALUES (691, 55, '夏县', '140828', '', 0, 2);
INSERT INTO `region`
VALUES (692, 55, '平陆县', '140829', '', 0, 2);
INSERT INTO `region`
VALUES (693, 55, '芮城县', '140830', '', 0, 2);
INSERT INTO `region`
VALUES (694, 55, '永济市', '140881', '', 0, 2);
INSERT INTO `region`
VALUES (695, 55, '河津市', '140882', '', 0, 2);
INSERT INTO `region`
VALUES (696, 56, '忻府区', '140902', '', 0, 2);
INSERT INTO `region`
VALUES (697, 56, '定襄县', '140921', '', 0, 2);
INSERT INTO `region`
VALUES (698, 56, '五台县', '140922', '', 0, 2);
INSERT INTO `region`
VALUES (699, 56, '代县', '140923', '', 0, 2);
INSERT INTO `region`
VALUES (700, 56, '繁峙县', '140924', '', 0, 2);
INSERT INTO `region`
VALUES (701, 56, '宁武县', '140925', '', 0, 2);
INSERT INTO `region`
VALUES (702, 56, '静乐县', '140926', '', 0, 2);
INSERT INTO `region`
VALUES (703, 56, '神池县', '140927', '', 0, 2);
INSERT INTO `region`
VALUES (704, 56, '五寨县', '140928', '', 0, 2);
INSERT INTO `region`
VALUES (705, 56, '岢岚县', '140929', '', 0, 2);
INSERT INTO `region`
VALUES (706, 56, '河曲县', '140930', '', 0, 2);
INSERT INTO `region`
VALUES (707, 56, '保德县', '140931', '', 0, 2);
INSERT INTO `region`
VALUES (708, 56, '偏关县', '140932', '', 0, 2);
INSERT INTO `region`
VALUES (709, 56, '原平市', '140981', '', 0, 2);
INSERT INTO `region`
VALUES (710, 57, '尧都区', '141002', '', 0, 2);
INSERT INTO `region`
VALUES (711, 57, '曲沃县', '141021', '', 0, 2);
INSERT INTO `region`
VALUES (712, 57, '翼城县', '141022', '', 0, 2);
INSERT INTO `region`
VALUES (713, 57, '襄汾县', '141023', '', 0, 2);
INSERT INTO `region`
VALUES (714, 57, '洪洞县', '141024', '', 0, 2);
INSERT INTO `region`
VALUES (715, 57, '古县', '141025', '', 0, 2);
INSERT INTO `region`
VALUES (716, 57, '安泽县', '141026', '', 0, 2);
INSERT INTO `region`
VALUES (717, 57, '浮山县', '141027', '', 0, 2);
INSERT INTO `region`
VALUES (718, 57, '吉县', '141028', '', 0, 2);
INSERT INTO `region`
VALUES (719, 57, '乡宁县', '141029', '', 0, 2);
INSERT INTO `region`
VALUES (720, 57, '大宁县', '141030', '', 0, 2);
INSERT INTO `region`
VALUES (721, 57, '隰县', '141031', '', 0, 2);
INSERT INTO `region`
VALUES (722, 57, '永和县', '141032', '', 0, 2);
INSERT INTO `region`
VALUES (723, 57, '蒲县', '141033', '', 0, 2);
INSERT INTO `region`
VALUES (724, 57, '汾西县', '141034', '', 0, 2);
INSERT INTO `region`
VALUES (725, 57, '侯马市', '141081', '', 0, 2);
INSERT INTO `region`
VALUES (726, 57, '霍州市', '141082', '', 0, 2);
INSERT INTO `region`
VALUES (727, 58, '离石区', '141102', '', 0, 2);
INSERT INTO `region`
VALUES (728, 58, '文水县', '141121', '', 0, 2);
INSERT INTO `region`
VALUES (729, 58, '交城县', '141122', '', 0, 2);
INSERT INTO `region`
VALUES (730, 58, '兴县', '141123', '', 0, 2);
INSERT INTO `region`
VALUES (731, 58, '临县', '141124', '', 0, 2);
INSERT INTO `region`
VALUES (732, 58, '柳林县', '141125', '', 0, 2);
INSERT INTO `region`
VALUES (733, 58, '石楼县', '141126', '', 0, 2);
INSERT INTO `region`
VALUES (734, 58, '岚县', '141127', '', 0, 2);
INSERT INTO `region`
VALUES (735, 58, '方山县', '141128', '', 0, 2);
INSERT INTO `region`
VALUES (736, 58, '中阳县', '141129', '', 0, 2);
INSERT INTO `region`
VALUES (737, 58, '交口县', '141130', '', 0, 2);
INSERT INTO `region`
VALUES (738, 58, '孝义市', '141181', '', 0, 2);
INSERT INTO `region`
VALUES (739, 58, '汾阳市', '141182', '', 0, 2);
INSERT INTO `region`
VALUES (740, 59, '新城区', '150102', '', 0, 2);
INSERT INTO `region`
VALUES (741, 59, '回民区', '150103', '', 0, 2);
INSERT INTO `region`
VALUES (742, 59, '玉泉区', '150104', '', 0, 2);
INSERT INTO `region`
VALUES (743, 59, '赛罕区', '150105', '', 0, 2);
INSERT INTO `region`
VALUES (744, 59, '土默特左旗', '150121', '', 0, 2);
INSERT INTO `region`
VALUES (745, 59, '托克托县', '150122', '', 0, 2);
INSERT INTO `region`
VALUES (746, 59, '和林格尔县', '150123', '', 0, 2);
INSERT INTO `region`
VALUES (747, 59, '清水河县', '150124', '', 0, 2);
INSERT INTO `region`
VALUES (748, 59, '武川县', '150125', '', 0, 2);
INSERT INTO `region`
VALUES (749, 60, '东河区', '150202', '', 0, 2);
INSERT INTO `region`
VALUES (750, 60, '昆都仑区', '150203', '', 0, 2);
INSERT INTO `region`
VALUES (751, 60, '青山区', '150204', '', 0, 2);
INSERT INTO `region`
VALUES (752, 60, '石拐区', '150205', '', 0, 2);
INSERT INTO `region`
VALUES (753, 60, '白云鄂博矿区', '150206', '', 0, 2);
INSERT INTO `region`
VALUES (754, 60, '九原区', '150207', '', 0, 2);
INSERT INTO `region`
VALUES (755, 60, '土默特右旗', '150221', '', 0, 2);
INSERT INTO `region`
VALUES (756, 60, '固阳县', '150222', '', 0, 2);
INSERT INTO `region`
VALUES (757, 60, '达尔罕茂明安联合旗', '150223', '', 0, 2);
INSERT INTO `region`
VALUES (758, 61, '海勃湾区', '150302', '', 0, 2);
INSERT INTO `region`
VALUES (759, 61, '海南区', '150303', '', 0, 2);
INSERT INTO `region`
VALUES (760, 61, '乌达区', '150304', '', 0, 2);
INSERT INTO `region`
VALUES (761, 62, '红山区', '150402', '', 0, 2);
INSERT INTO `region`
VALUES (762, 62, '元宝山区', '150403', '', 0, 2);
INSERT INTO `region`
VALUES (763, 62, '松山区', '150404', '', 0, 2);
INSERT INTO `region`
VALUES (764, 62, '阿鲁科尔沁旗', '150421', '', 0, 2);
INSERT INTO `region`
VALUES (765, 62, '巴林左旗', '150422', '', 0, 2);
INSERT INTO `region`
VALUES (766, 62, '巴林右旗', '150423', '', 0, 2);
INSERT INTO `region`
VALUES (767, 62, '林西县', '150424', '', 0, 2);
INSERT INTO `region`
VALUES (768, 62, '克什克腾旗', '150425', '', 0, 2);
INSERT INTO `region`
VALUES (769, 62, '翁牛特旗', '150426', '', 0, 2);
INSERT INTO `region`
VALUES (770, 62, '喀喇沁旗', '150428', '', 0, 2);
INSERT INTO `region`
VALUES (771, 62, '宁城县', '150429', '', 0, 2);
INSERT INTO `region`
VALUES (772, 62, '敖汉旗', '150430', '', 0, 2);
INSERT INTO `region`
VALUES (773, 63, '科尔沁区', '150502', '', 0, 2);
INSERT INTO `region`
VALUES (774, 63, '科尔沁左翼中旗', '150521', '', 0, 2);
INSERT INTO `region`
VALUES (775, 63, '科尔沁左翼后旗', '150522', '', 0, 2);
INSERT INTO `region`
VALUES (776, 63, '开鲁县', '150523', '', 0, 2);
INSERT INTO `region`
VALUES (777, 63, '库伦旗', '150524', '', 0, 2);
INSERT INTO `region`
VALUES (778, 63, '奈曼旗', '150525', '', 0, 2);
INSERT INTO `region`
VALUES (779, 63, '扎鲁特旗', '150526', '', 0, 2);
INSERT INTO `region`
VALUES (780, 63, '霍林郭勒市', '150581', '', 0, 2);
INSERT INTO `region`
VALUES (781, 64, '东胜区', '150602', '', 0, 2);
INSERT INTO `region`
VALUES (782, 64, '康巴什区', '150603', '', 0, 2);
INSERT INTO `region`
VALUES (783, 64, '达拉特旗', '150621', '', 0, 2);
INSERT INTO `region`
VALUES (784, 64, '准格尔旗', '150622', '', 0, 2);
INSERT INTO `region`
VALUES (785, 64, '鄂托克前旗', '150623', '', 0, 2);
INSERT INTO `region`
VALUES (786, 64, '鄂托克旗', '150624', '', 0, 2);
INSERT INTO `region`
VALUES (787, 64, '杭锦旗', '150625', '', 0, 2);
INSERT INTO `region`
VALUES (788, 64, '乌审旗', '150626', '', 0, 2);
INSERT INTO `region`
VALUES (789, 64, '伊金霍洛旗', '150627', '', 0, 2);
INSERT INTO `region`
VALUES (790, 65, '海拉尔区', '150702', '', 0, 2);
INSERT INTO `region`
VALUES (791, 65, '扎赉诺尔区', '150703', '', 0, 2);
INSERT INTO `region`
VALUES (792, 65, '阿荣旗', '150721', '', 0, 2);
INSERT INTO `region`
VALUES (793, 65, '莫力达瓦达斡尔族自治旗', '150722', '', 0, 2);
INSERT INTO `region`
VALUES (794, 65, '鄂伦春自治旗', '150723', '', 0, 2);
INSERT INTO `region`
VALUES (795, 65, '鄂温克族自治旗', '150724', '', 0, 2);
INSERT INTO `region`
VALUES (796, 65, '陈巴尔虎旗', '150725', '', 0, 2);
INSERT INTO `region`
VALUES (797, 65, '新巴尔虎左旗', '150726', '', 0, 2);
INSERT INTO `region`
VALUES (798, 65, '新巴尔虎右旗', '150727', '', 0, 2);
INSERT INTO `region`
VALUES (799, 65, '满洲里市', '150781', '', 0, 2);
INSERT INTO `region`
VALUES (800, 65, '牙克石市', '150782', '', 0, 2);
INSERT INTO `region`
VALUES (801, 65, '扎兰屯市', '150783', '', 0, 2);
INSERT INTO `region`
VALUES (802, 65, '额尔古纳市', '150784', '', 0, 2);
INSERT INTO `region`
VALUES (803, 65, '根河市', '150785', '', 0, 2);
INSERT INTO `region`
VALUES (804, 66, '临河区', '150802', '', 0, 2);
INSERT INTO `region`
VALUES (805, 66, '五原县', '150821', '', 0, 2);
INSERT INTO `region`
VALUES (806, 66, '磴口县', '150822', '', 0, 2);
INSERT INTO `region`
VALUES (807, 66, '乌拉特前旗', '150823', '', 0, 2);
INSERT INTO `region`
VALUES (808, 66, '乌拉特中旗', '150824', '', 0, 2);
INSERT INTO `region`
VALUES (809, 66, '乌拉特后旗', '150825', '', 0, 2);
INSERT INTO `region`
VALUES (810, 66, '杭锦后旗', '150826', '', 0, 2);
INSERT INTO `region`
VALUES (811, 67, '集宁区', '150902', '', 0, 2);
INSERT INTO `region`
VALUES (812, 67, '卓资县', '150921', '', 0, 2);
INSERT INTO `region`
VALUES (813, 67, '化德县', '150922', '', 0, 2);
INSERT INTO `region`
VALUES (814, 67, '商都县', '150923', '', 0, 2);
INSERT INTO `region`
VALUES (815, 67, '兴和县', '150924', '', 0, 2);
INSERT INTO `region`
VALUES (816, 67, '凉城县', '150925', '', 0, 2);
INSERT INTO `region`
VALUES (817, 67, '察哈尔右翼前旗', '150926', '', 0, 2);
INSERT INTO `region`
VALUES (818, 67, '察哈尔右翼中旗', '150927', '', 0, 2);
INSERT INTO `region`
VALUES (819, 67, '察哈尔右翼后旗', '150928', '', 0, 2);
INSERT INTO `region`
VALUES (820, 67, '四子王旗', '150929', '', 0, 2);
INSERT INTO `region`
VALUES (821, 67, '丰镇市', '150981', '', 0, 2);
INSERT INTO `region`
VALUES (822, 68, '乌兰浩特市', '152201', '', 0, 2);
INSERT INTO `region`
VALUES (823, 68, '阿尔山市', '152202', '', 0, 2);
INSERT INTO `region`
VALUES (824, 68, '科尔沁右翼前旗', '152221', '', 0, 2);
INSERT INTO `region`
VALUES (825, 68, '科尔沁右翼中旗', '152222', '', 0, 2);
INSERT INTO `region`
VALUES (826, 68, '扎赉特旗', '152223', '', 0, 2);
INSERT INTO `region`
VALUES (827, 68, '突泉县', '152224', '', 0, 2);
INSERT INTO `region`
VALUES (828, 69, '二连浩特市', '152501', '', 0, 2);
INSERT INTO `region`
VALUES (829, 69, '锡林浩特市', '152502', '', 0, 2);
INSERT INTO `region`
VALUES (830, 69, '阿巴嘎旗', '152522', '', 0, 2);
INSERT INTO `region`
VALUES (831, 69, '苏尼特左旗', '152523', '', 0, 2);
INSERT INTO `region`
VALUES (832, 69, '苏尼特右旗', '152524', '', 0, 2);
INSERT INTO `region`
VALUES (833, 69, '东乌珠穆沁旗', '152525', '', 0, 2);
INSERT INTO `region`
VALUES (834, 69, '西乌珠穆沁旗', '152526', '', 0, 2);
INSERT INTO `region`
VALUES (835, 69, '太仆寺旗', '152527', '', 0, 2);
INSERT INTO `region`
VALUES (836, 69, '镶黄旗', '152528', '', 0, 2);
INSERT INTO `region`
VALUES (837, 69, '正镶白旗', '152529', '', 0, 2);
INSERT INTO `region`
VALUES (838, 69, '正蓝旗', '152530', '', 0, 2);
INSERT INTO `region`
VALUES (839, 69, '多伦县', '152531', '', 0, 2);
INSERT INTO `region`
VALUES (840, 70, '阿拉善左旗', '152921', '', 0, 2);
INSERT INTO `region`
VALUES (841, 70, '阿拉善右旗', '152922', '', 0, 2);
INSERT INTO `region`
VALUES (842, 70, '额济纳旗', '152923', '', 0, 2);
INSERT INTO `region`
VALUES (843, 71, '和平区', '210102', '', 0, 2);
INSERT INTO `region`
VALUES (844, 71, '沈河区', '210103', '', 0, 2);
INSERT INTO `region`
VALUES (845, 71, '大东区', '210104', '', 0, 2);
INSERT INTO `region`
VALUES (846, 71, '皇姑区', '210105', '', 0, 2);
INSERT INTO `region`
VALUES (847, 71, '铁西区', '210106', '', 0, 2);
INSERT INTO `region`
VALUES (848, 71, '苏家屯区', '210111', '', 0, 2);
INSERT INTO `region`
VALUES (849, 71, '浑南区', '210112', '', 0, 2);
INSERT INTO `region`
VALUES (850, 71, '沈北新区', '210113', '', 0, 2);
INSERT INTO `region`
VALUES (851, 71, '于洪区', '210114', '', 0, 2);
INSERT INTO `region`
VALUES (852, 71, '辽中区', '210115', '', 0, 2);
INSERT INTO `region`
VALUES (853, 71, '康平县', '210123', '', 0, 2);
INSERT INTO `region`
VALUES (854, 71, '法库县', '210124', '', 0, 2);
INSERT INTO `region`
VALUES (855, 71, '新民市', '210181', '', 0, 2);
INSERT INTO `region`
VALUES (856, 72, '中山区', '210202', '', 0, 2);
INSERT INTO `region`
VALUES (857, 72, '西岗区', '210203', '', 0, 2);
INSERT INTO `region`
VALUES (858, 72, '沙河口区', '210204', '', 0, 2);
INSERT INTO `region`
VALUES (859, 72, '甘井子区', '210211', '', 0, 2);
INSERT INTO `region`
VALUES (860, 72, '旅顺口区', '210212', '', 0, 2);
INSERT INTO `region`
VALUES (861, 72, '金州区', '210213', '', 0, 2);
INSERT INTO `region`
VALUES (862, 72, '普兰店区', '210214', '', 0, 2);
INSERT INTO `region`
VALUES (863, 72, '长海县', '210224', '', 0, 2);
INSERT INTO `region`
VALUES (864, 72, '瓦房店市', '210281', '', 0, 2);
INSERT INTO `region`
VALUES (865, 72, '庄河市', '210283', '', 0, 2);
INSERT INTO `region`
VALUES (866, 73, '铁东区', '210302', '', 0, 2);
INSERT INTO `region`
VALUES (867, 73, '铁西区', '210303', '', 0, 2);
INSERT INTO `region`
VALUES (868, 73, '立山区', '210304', '', 0, 2);
INSERT INTO `region`
VALUES (869, 73, '千山区', '210311', '', 0, 2);
INSERT INTO `region`
VALUES (870, 73, '台安县', '210321', '', 0, 2);
INSERT INTO `region`
VALUES (871, 73, '岫岩满族自治县', '210323', '', 0, 2);
INSERT INTO `region`
VALUES (872, 73, '海城市', '210381', '', 0, 2);
INSERT INTO `region`
VALUES (873, 74, '新抚区', '210402', '', 0, 2);
INSERT INTO `region`
VALUES (874, 74, '东洲区', '210403', '', 0, 2);
INSERT INTO `region`
VALUES (875, 74, '望花区', '210404', '', 0, 2);
INSERT INTO `region`
VALUES (876, 74, '顺城区', '210411', '', 0, 2);
INSERT INTO `region`
VALUES (877, 74, '抚顺县', '210421', '', 0, 2);
INSERT INTO `region`
VALUES (878, 74, '新宾满族自治县', '210422', '', 0, 2);
INSERT INTO `region`
VALUES (879, 74, '清原满族自治县', '210423', '', 0, 2);
INSERT INTO `region`
VALUES (880, 75, '平山区', '210502', '', 0, 2);
INSERT INTO `region`
VALUES (881, 75, '溪湖区', '210503', '', 0, 2);
INSERT INTO `region`
VALUES (882, 75, '明山区', '210504', '', 0, 2);
INSERT INTO `region`
VALUES (883, 75, '南芬区', '210505', '', 0, 2);
INSERT INTO `region`
VALUES (884, 75, '本溪满族自治县', '210521', '', 0, 2);
INSERT INTO `region`
VALUES (885, 75, '桓仁满族自治县', '210522', '', 0, 2);
INSERT INTO `region`
VALUES (886, 76, '元宝区', '210602', '', 0, 2);
INSERT INTO `region`
VALUES (887, 76, '振兴区', '210603', '', 0, 2);
INSERT INTO `region`
VALUES (888, 76, '振安区', '210604', '', 0, 2);
INSERT INTO `region`
VALUES (889, 76, '宽甸满族自治县', '210624', '', 0, 2);
INSERT INTO `region`
VALUES (890, 76, '东港市', '210681', '', 0, 2);
INSERT INTO `region`
VALUES (891, 76, '凤城市', '210682', '', 0, 2);
INSERT INTO `region`
VALUES (892, 77, '古塔区', '210702', '', 0, 2);
INSERT INTO `region`
VALUES (893, 77, '凌河区', '210703', '', 0, 2);
INSERT INTO `region`
VALUES (894, 77, '太和区', '210711', '', 0, 2);
INSERT INTO `region`
VALUES (895, 77, '黑山县', '210726', '', 0, 2);
INSERT INTO `region`
VALUES (896, 77, '义县', '210727', '', 0, 2);
INSERT INTO `region`
VALUES (897, 77, '凌海市', '210781', '', 0, 2);
INSERT INTO `region`
VALUES (898, 77, '北镇市', '210782', '', 0, 2);
INSERT INTO `region`
VALUES (899, 78, '站前区', '210802', '', 0, 2);
INSERT INTO `region`
VALUES (900, 78, '西市区', '210803', '', 0, 2);
INSERT INTO `region`
VALUES (901, 78, '鲅鱼圈区', '210804', '', 0, 2);
INSERT INTO `region`
VALUES (902, 78, '老边区', '210811', '', 0, 2);
INSERT INTO `region`
VALUES (903, 78, '盖州市', '210881', '', 0, 2);
INSERT INTO `region`
VALUES (904, 78, '大石桥市', '210882', '', 0, 2);
INSERT INTO `region`
VALUES (905, 79, '海州区', '210902', '', 0, 2);
INSERT INTO `region`
VALUES (906, 79, '新邱区', '210903', '', 0, 2);
INSERT INTO `region`
VALUES (907, 79, '太平区', '210904', '', 0, 2);
INSERT INTO `region`
VALUES (908, 79, '清河门区', '210905', '', 0, 2);
INSERT INTO `region`
VALUES (909, 79, '细河区', '210911', '', 0, 2);
INSERT INTO `region`
VALUES (910, 79, '阜新蒙古族自治县', '210921', '', 0, 2);
INSERT INTO `region`
VALUES (911, 79, '彰武县', '210922', '', 0, 2);
INSERT INTO `region`
VALUES (912, 80, '白塔区', '211002', '', 0, 2);
INSERT INTO `region`
VALUES (913, 80, '文圣区', '211003', '', 0, 2);
INSERT INTO `region`
VALUES (914, 80, '宏伟区', '211004', '', 0, 2);
INSERT INTO `region`
VALUES (915, 80, '弓长岭区', '211005', '', 0, 2);
INSERT INTO `region`
VALUES (916, 80, '太子河区', '211011', '', 0, 2);
INSERT INTO `region`
VALUES (917, 80, '辽阳县', '211021', '', 0, 2);
INSERT INTO `region`
VALUES (918, 80, '灯塔市', '211081', '', 0, 2);
INSERT INTO `region`
VALUES (919, 81, '双台子区', '211102', '', 0, 2);
INSERT INTO `region`
VALUES (920, 81, '兴隆台区', '211103', '', 0, 2);
INSERT INTO `region`
VALUES (921, 81, '大洼区', '211104', '', 0, 2);
INSERT INTO `region`
VALUES (922, 81, '盘山县', '211122', '', 0, 2);
INSERT INTO `region`
VALUES (923, 82, '银州区', '211202', '', 0, 2);
INSERT INTO `region`
VALUES (924, 82, '清河区', '211204', '', 0, 2);
INSERT INTO `region`
VALUES (925, 82, '铁岭县', '211221', '', 0, 2);
INSERT INTO `region`
VALUES (926, 82, '西丰县', '211223', '', 0, 2);
INSERT INTO `region`
VALUES (927, 82, '昌图县', '211224', '', 0, 2);
INSERT INTO `region`
VALUES (928, 82, '调兵山市', '211281', '', 0, 2);
INSERT INTO `region`
VALUES (929, 82, '开原市', '211282', '', 0, 2);
INSERT INTO `region`
VALUES (930, 83, '双塔区', '211302', '', 0, 2);
INSERT INTO `region`
VALUES (931, 83, '龙城区', '211303', '', 0, 2);
INSERT INTO `region`
VALUES (932, 83, '朝阳县', '211321', '', 0, 2);
INSERT INTO `region`
VALUES (933, 83, '建平县', '211322', '', 0, 2);
INSERT INTO `region`
VALUES (934, 83, '喀喇沁左翼蒙古族自治县', '211324', '', 0, 2);
INSERT INTO `region`
VALUES (935, 83, '北票市', '211381', '', 0, 2);
INSERT INTO `region`
VALUES (936, 83, '凌源市', '211382', '', 0, 2);
INSERT INTO `region`
VALUES (937, 84, '连山区', '211402', '', 0, 2);
INSERT INTO `region`
VALUES (938, 84, '龙港区', '211403', '', 0, 2);
INSERT INTO `region`
VALUES (939, 84, '南票区', '211404', '', 0, 2);
INSERT INTO `region`
VALUES (940, 84, '绥中县', '211421', '', 0, 2);
INSERT INTO `region`
VALUES (941, 84, '建昌县', '211422', '', 0, 2);
INSERT INTO `region`
VALUES (942, 84, '兴城市', '211481', '', 0, 2);
INSERT INTO `region`
VALUES (943, 85, '南关区', '220102', '', 0, 2);
INSERT INTO `region`
VALUES (944, 85, '宽城区', '220103', '', 0, 2);
INSERT INTO `region`
VALUES (945, 85, '朝阳区', '220104', '', 0, 2);
INSERT INTO `region`
VALUES (946, 85, '二道区', '220105', '', 0, 2);
INSERT INTO `region`
VALUES (947, 85, '绿园区', '220106', '', 0, 2);
INSERT INTO `region`
VALUES (948, 85, '双阳区', '220112', '', 0, 2);
INSERT INTO `region`
VALUES (949, 85, '九台区', '220113', '', 0, 2);
INSERT INTO `region`
VALUES (950, 85, '农安县', '220122', '', 0, 2);
INSERT INTO `region`
VALUES (951, 85, '榆树市', '220182', '', 0, 2);
INSERT INTO `region`
VALUES (952, 85, '德惠市', '220183', '', 0, 2);
INSERT INTO `region`
VALUES (953, 85, '公主岭市', '220184', '', 0, 2);
INSERT INTO `region`
VALUES (954, 86, '昌邑区', '220202', '', 0, 2);
INSERT INTO `region`
VALUES (955, 86, '龙潭区', '220203', '', 0, 2);
INSERT INTO `region`
VALUES (956, 86, '船营区', '220204', '', 0, 2);
INSERT INTO `region`
VALUES (957, 86, '丰满区', '220211', '', 0, 2);
INSERT INTO `region`
VALUES (958, 86, '永吉县', '220221', '', 0, 2);
INSERT INTO `region`
VALUES (959, 86, '蛟河市', '220281', '', 0, 2);
INSERT INTO `region`
VALUES (960, 86, '桦甸市', '220282', '', 0, 2);
INSERT INTO `region`
VALUES (961, 86, '舒兰市', '220283', '', 0, 2);
INSERT INTO `region`
VALUES (962, 86, '磐石市', '220284', '', 0, 2);
INSERT INTO `region`
VALUES (963, 87, '铁西区', '220302', '', 0, 2);
INSERT INTO `region`
VALUES (964, 87, '铁东区', '220303', '', 0, 2);
INSERT INTO `region`
VALUES (965, 87, '梨树县', '220322', '', 0, 2);
INSERT INTO `region`
VALUES (966, 87, '伊通满族自治县', '220323', '', 0, 2);
INSERT INTO `region`
VALUES (967, 87, '双辽市', '220382', '', 0, 2);
INSERT INTO `region`
VALUES (968, 88, '龙山区', '220402', '', 0, 2);
INSERT INTO `region`
VALUES (969, 88, '西安区', '220403', '', 0, 2);
INSERT INTO `region`
VALUES (970, 88, '东丰县', '220421', '', 0, 2);
INSERT INTO `region`
VALUES (971, 88, '东辽县', '220422', '', 0, 2);
INSERT INTO `region`
VALUES (972, 89, '东昌区', '220502', '', 0, 2);
INSERT INTO `region`
VALUES (973, 89, '二道江区', '220503', '', 0, 2);
INSERT INTO `region`
VALUES (974, 89, '通化县', '220521', '', 0, 2);
INSERT INTO `region`
VALUES (975, 89, '辉南县', '220523', '', 0, 2);
INSERT INTO `region`
VALUES (976, 89, '柳河县', '220524', '', 0, 2);
INSERT INTO `region`
VALUES (977, 89, '梅河口市', '220581', '', 0, 2);
INSERT INTO `region`
VALUES (978, 89, '集安市', '220582', '', 0, 2);
INSERT INTO `region`
VALUES (979, 90, '浑江区', '220602', '', 0, 2);
INSERT INTO `region`
VALUES (980, 90, '江源区', '220605', '', 0, 2);
INSERT INTO `region`
VALUES (981, 90, '抚松县', '220621', '', 0, 2);
INSERT INTO `region`
VALUES (982, 90, '靖宇县', '220622', '', 0, 2);
INSERT INTO `region`
VALUES (983, 90, '长白朝鲜族自治县', '220623', '', 0, 2);
INSERT INTO `region`
VALUES (984, 90, '临江市', '220681', '', 0, 2);
INSERT INTO `region`
VALUES (985, 91, '宁江区', '220702', '', 0, 2);
INSERT INTO `region`
VALUES (986, 91, '前郭尔罗斯蒙古族自治县', '220721', '', 0, 2);
INSERT INTO `region`
VALUES (987, 91, '长岭县', '220722', '', 0, 2);
INSERT INTO `region`
VALUES (988, 91, '乾安县', '220723', '', 0, 2);
INSERT INTO `region`
VALUES (989, 91, '扶余市', '220781', '', 0, 2);
INSERT INTO `region`
VALUES (990, 92, '洮北区', '220802', '', 0, 2);
INSERT INTO `region`
VALUES (991, 92, '镇赉县', '220821', '', 0, 2);
INSERT INTO `region`
VALUES (992, 92, '通榆县', '220822', '', 0, 2);
INSERT INTO `region`
VALUES (993, 92, '洮南市', '220881', '', 0, 2);
INSERT INTO `region`
VALUES (994, 92, '大安市', '220882', '', 0, 2);
INSERT INTO `region`
VALUES (995, 93, '延吉市', '222401', '', 0, 2);
INSERT INTO `region`
VALUES (996, 93, '图们市', '222402', '', 0, 2);
INSERT INTO `region`
VALUES (997, 93, '敦化市', '222403', '', 0, 2);
INSERT INTO `region`
VALUES (998, 93, '珲春市', '222404', '', 0, 2);
INSERT INTO `region`
VALUES (999, 93, '龙井市', '222405', '', 0, 2);
INSERT INTO `region`
VALUES (1000, 93, '和龙市', '222406', '', 0, 2);
INSERT INTO `region`
VALUES (1001, 93, '汪清县', '222424', '', 0, 2);
INSERT INTO `region`
VALUES (1002, 93, '安图县', '222426', '', 0, 2);
INSERT INTO `region`
VALUES (1003, 94, '道里区', '230102', '', 0, 2);
INSERT INTO `region`
VALUES (1004, 94, '南岗区', '230103', '', 0, 2);
INSERT INTO `region`
VALUES (1005, 94, '道外区', '230104', '', 0, 2);
INSERT INTO `region`
VALUES (1006, 94, '平房区', '230108', '', 0, 2);
INSERT INTO `region`
VALUES (1007, 94, '松北区', '230109', '', 0, 2);
INSERT INTO `region`
VALUES (1008, 94, '香坊区', '230110', '', 0, 2);
INSERT INTO `region`
VALUES (1009, 94, '呼兰区', '230111', '', 0, 2);
INSERT INTO `region`
VALUES (1010, 94, '阿城区', '230112', '', 0, 2);
INSERT INTO `region`
VALUES (1011, 94, '双城区', '230113', '', 0, 2);
INSERT INTO `region`
VALUES (1012, 94, '依兰县', '230123', '', 0, 2);
INSERT INTO `region`
VALUES (1013, 94, '方正县', '230124', '', 0, 2);
INSERT INTO `region`
VALUES (1014, 94, '宾县', '230125', '', 0, 2);
INSERT INTO `region`
VALUES (1015, 94, '巴彦县', '230126', '', 0, 2);
INSERT INTO `region`
VALUES (1016, 94, '木兰县', '230127', '', 0, 2);
INSERT INTO `region`
VALUES (1017, 94, '通河县', '230128', '', 0, 2);
INSERT INTO `region`
VALUES (1018, 94, '延寿县', '230129', '', 0, 2);
INSERT INTO `region`
VALUES (1019, 94, '尚志市', '230183', '', 0, 2);
INSERT INTO `region`
VALUES (1020, 94, '五常市', '230184', '', 0, 2);
INSERT INTO `region`
VALUES (1021, 95, '龙沙区', '230202', '', 0, 2);
INSERT INTO `region`
VALUES (1022, 95, '建华区', '230203', '', 0, 2);
INSERT INTO `region`
VALUES (1023, 95, '铁锋区', '230204', '', 0, 2);
INSERT INTO `region`
VALUES (1024, 95, '昂昂溪区', '230205', '', 0, 2);
INSERT INTO `region`
VALUES (1025, 95, '富拉尔基区', '230206', '', 0, 2);
INSERT INTO `region`
VALUES (1026, 95, '碾子山区', '230207', '', 0, 2);
INSERT INTO `region`
VALUES (1027, 95, '梅里斯达斡尔族区', '230208', '', 0, 2);
INSERT INTO `region`
VALUES (1028, 95, '龙江县', '230221', '', 0, 2);
INSERT INTO `region`
VALUES (1029, 95, '依安县', '230223', '', 0, 2);
INSERT INTO `region`
VALUES (1030, 95, '泰来县', '230224', '', 0, 2);
INSERT INTO `region`
VALUES (1031, 95, '甘南县', '230225', '', 0, 2);
INSERT INTO `region`
VALUES (1032, 95, '富裕县', '230227', '', 0, 2);
INSERT INTO `region`
VALUES (1033, 95, '克山县', '230229', '', 0, 2);
INSERT INTO `region`
VALUES (1034, 95, '克东县', '230230', '', 0, 2);
INSERT INTO `region`
VALUES (1035, 95, '拜泉县', '230231', '', 0, 2);
INSERT INTO `region`
VALUES (1036, 95, '讷河市', '230281', '', 0, 2);
INSERT INTO `region`
VALUES (1037, 96, '鸡冠区', '230302', '', 0, 2);
INSERT INTO `region`
VALUES (1038, 96, '恒山区', '230303', '', 0, 2);
INSERT INTO `region`
VALUES (1039, 96, '滴道区', '230304', '', 0, 2);
INSERT INTO `region`
VALUES (1040, 96, '梨树区', '230305', '', 0, 2);
INSERT INTO `region`
VALUES (1041, 96, '城子河区', '230306', '', 0, 2);
INSERT INTO `region`
VALUES (1042, 96, '麻山区', '230307', '', 0, 2);
INSERT INTO `region`
VALUES (1043, 96, '鸡东县', '230321', '', 0, 2);
INSERT INTO `region`
VALUES (1044, 96, '虎林市', '230381', '', 0, 2);
INSERT INTO `region`
VALUES (1045, 96, '密山市', '230382', '', 0, 2);
INSERT INTO `region`
VALUES (1046, 97, '向阳区', '230402', '', 0, 2);
INSERT INTO `region`
VALUES (1047, 97, '工农区', '230403', '', 0, 2);
INSERT INTO `region`
VALUES (1048, 97, '南山区', '230404', '', 0, 2);
INSERT INTO `region`
VALUES (1049, 97, '兴安区', '230405', '', 0, 2);
INSERT INTO `region`
VALUES (1050, 97, '东山区', '230406', '', 0, 2);
INSERT INTO `region`
VALUES (1051, 97, '兴山区', '230407', '', 0, 2);
INSERT INTO `region`
VALUES (1052, 97, '萝北县', '230421', '', 0, 2);
INSERT INTO `region`
VALUES (1053, 97, '绥滨县', '230422', '', 0, 2);
INSERT INTO `region`
VALUES (1054, 98, '尖山区', '230502', '', 0, 2);
INSERT INTO `region`
VALUES (1055, 98, '岭东区', '230503', '', 0, 2);
INSERT INTO `region`
VALUES (1056, 98, '四方台区', '230505', '', 0, 2);
INSERT INTO `region`
VALUES (1057, 98, '宝山区', '230506', '', 0, 2);
INSERT INTO `region`
VALUES (1058, 98, '集贤县', '230521', '', 0, 2);
INSERT INTO `region`
VALUES (1059, 98, '友谊县', '230522', '', 0, 2);
INSERT INTO `region`
VALUES (1060, 98, '宝清县', '230523', '', 0, 2);
INSERT INTO `region`
VALUES (1061, 98, '饶河县', '230524', '', 0, 2);
INSERT INTO `region`
VALUES (1062, 99, '萨尔图区', '230602', '', 0, 2);
INSERT INTO `region`
VALUES (1063, 99, '龙凤区', '230603', '', 0, 2);
INSERT INTO `region`
VALUES (1064, 99, '让胡路区', '230604', '', 0, 2);
INSERT INTO `region`
VALUES (1065, 99, '红岗区', '230605', '', 0, 2);
INSERT INTO `region`
VALUES (1066, 99, '大同区', '230606', '', 0, 2);
INSERT INTO `region`
VALUES (1067, 99, '肇州县', '230621', '', 0, 2);
INSERT INTO `region`
VALUES (1068, 99, '肇源县', '230622', '', 0, 2);
INSERT INTO `region`
VALUES (1069, 99, '林甸县', '230623', '', 0, 2);
INSERT INTO `region`
VALUES (1070, 99, '杜尔伯特蒙古族自治县', '230624', '', 0, 2);
INSERT INTO `region`
VALUES (1071, 100, '伊美区', '230717', '', 0, 2);
INSERT INTO `region`
VALUES (1072, 100, '乌翠区', '230718', '', 0, 2);
INSERT INTO `region`
VALUES (1073, 100, '友好区', '230719', '', 0, 2);
INSERT INTO `region`
VALUES (1074, 100, '嘉荫县', '230722', '', 0, 2);
INSERT INTO `region`
VALUES (1075, 100, '汤旺县', '230723', '', 0, 2);
INSERT INTO `region`
VALUES (1076, 100, '丰林县', '230724', '', 0, 2);
INSERT INTO `region`
VALUES (1077, 100, '大箐山县', '230725', '', 0, 2);
INSERT INTO `region`
VALUES (1078, 100, '南岔县', '230726', '', 0, 2);
INSERT INTO `region`
VALUES (1079, 100, '金林区', '230751', '', 0, 2);
INSERT INTO `region`
VALUES (1080, 100, '铁力市', '230781', '', 0, 2);
INSERT INTO `region`
VALUES (1081, 101, '向阳区', '230803', '', 0, 2);
INSERT INTO `region`
VALUES (1082, 101, '前进区', '230804', '', 0, 2);
INSERT INTO `region`
VALUES (1083, 101, '东风区', '230805', '', 0, 2);
INSERT INTO `region`
VALUES (1084, 101, '郊区', '230811', '', 0, 2);
INSERT INTO `region`
VALUES (1085, 101, '桦南县', '230822', '', 0, 2);
INSERT INTO `region`
VALUES (1086, 101, '桦川县', '230826', '', 0, 2);
INSERT INTO `region`
VALUES (1087, 101, '汤原县', '230828', '', 0, 2);
INSERT INTO `region`
VALUES (1088, 101, '同江市', '230881', '', 0, 2);
INSERT INTO `region`
VALUES (1089, 101, '富锦市', '230882', '', 0, 2);
INSERT INTO `region`
VALUES (1090, 101, '抚远市', '230883', '', 0, 2);
INSERT INTO `region`
VALUES (1091, 102, '新兴区', '230902', '', 0, 2);
INSERT INTO `region`
VALUES (1092, 102, '桃山区', '230903', '', 0, 2);
INSERT INTO `region`
VALUES (1093, 102, '茄子河区', '230904', '', 0, 2);
INSERT INTO `region`
VALUES (1094, 102, '勃利县', '230921', '', 0, 2);
INSERT INTO `region`
VALUES (1095, 103, '东安区', '231002', '', 0, 2);
INSERT INTO `region`
VALUES (1096, 103, '阳明区', '231003', '', 0, 2);
INSERT INTO `region`
VALUES (1097, 103, '爱民区', '231004', '', 0, 2);
INSERT INTO `region`
VALUES (1098, 103, '西安区', '231005', '', 0, 2);
INSERT INTO `region`
VALUES (1099, 103, '林口县', '231025', '', 0, 2);
INSERT INTO `region`
VALUES (1100, 103, '绥芬河市', '231081', '', 0, 2);
INSERT INTO `region`
VALUES (1101, 103, '海林市', '231083', '', 0, 2);
INSERT INTO `region`
VALUES (1102, 103, '宁安市', '231084', '', 0, 2);
INSERT INTO `region`
VALUES (1103, 103, '穆棱市', '231085', '', 0, 2);
INSERT INTO `region`
VALUES (1104, 103, '东宁市', '231086', '', 0, 2);
INSERT INTO `region`
VALUES (1105, 104, '爱辉区', '231102', '', 0, 2);
INSERT INTO `region`
VALUES (1106, 104, '逊克县', '231123', '', 0, 2);
INSERT INTO `region`
VALUES (1107, 104, '孙吴县', '231124', '', 0, 2);
INSERT INTO `region`
VALUES (1108, 104, '北安市', '231181', '', 0, 2);
INSERT INTO `region`
VALUES (1109, 104, '五大连池市', '231182', '', 0, 2);
INSERT INTO `region`
VALUES (1110, 104, '嫩江市', '231183', '', 0, 2);
INSERT INTO `region`
VALUES (1111, 105, '北林区', '231202', '', 0, 2);
INSERT INTO `region`
VALUES (1112, 105, '望奎县', '231221', '', 0, 2);
INSERT INTO `region`
VALUES (1113, 105, '兰西县', '231222', '', 0, 2);
INSERT INTO `region`
VALUES (1114, 105, '青冈县', '231223', '', 0, 2);
INSERT INTO `region`
VALUES (1115, 105, '庆安县', '231224', '', 0, 2);
INSERT INTO `region`
VALUES (1116, 105, '明水县', '231225', '', 0, 2);
INSERT INTO `region`
VALUES (1117, 105, '绥棱县', '231226', '', 0, 2);
INSERT INTO `region`
VALUES (1118, 105, '安达市', '231281', '', 0, 2);
INSERT INTO `region`
VALUES (1119, 105, '肇东市', '231282', '', 0, 2);
INSERT INTO `region`
VALUES (1120, 105, '海伦市', '231283', '', 0, 2);
INSERT INTO `region`
VALUES (1121, 106, '漠河市', '232701', '', 0, 2);
INSERT INTO `region`
VALUES (1122, 106, '呼玛县', '232721', '', 0, 2);
INSERT INTO `region`
VALUES (1123, 106, '塔河县', '232722', '', 0, 2);
INSERT INTO `region`
VALUES (1124, 107, '黄浦区', '310101', '', 0, 2);
INSERT INTO `region`
VALUES (1125, 107, '徐汇区', '310104', '', 0, 2);
INSERT INTO `region`
VALUES (1126, 107, '长宁区', '310105', '', 0, 2);
INSERT INTO `region`
VALUES (1127, 107, '静安区', '310106', '', 0, 2);
INSERT INTO `region`
VALUES (1128, 107, '普陀区', '310107', '', 0, 2);
INSERT INTO `region`
VALUES (1129, 107, '虹口区', '310109', '', 0, 2);
INSERT INTO `region`
VALUES (1130, 107, '杨浦区', '310110', '', 0, 2);
INSERT INTO `region`
VALUES (1131, 107, '闵行区', '310112', '', 0, 2);
INSERT INTO `region`
VALUES (1132, 107, '宝山区', '310113', '', 0, 2);
INSERT INTO `region`
VALUES (1133, 107, '嘉定区', '310114', '', 0, 2);
INSERT INTO `region`
VALUES (1134, 107, '浦东新区', '310115', '', 0, 2);
INSERT INTO `region`
VALUES (1135, 107, '金山区', '310116', '', 0, 2);
INSERT INTO `region`
VALUES (1136, 107, '松江区', '310117', '', 0, 2);
INSERT INTO `region`
VALUES (1137, 107, '青浦区', '310118', '', 0, 2);
INSERT INTO `region`
VALUES (1138, 107, '奉贤区', '310120', '', 0, 2);
INSERT INTO `region`
VALUES (1139, 107, '崇明区', '310151', '', 0, 2);
INSERT INTO `region`
VALUES (1140, 108, '玄武区', '320102', '', 0, 2);
INSERT INTO `region`
VALUES (1141, 108, '秦淮区', '320104', '', 0, 2);
INSERT INTO `region`
VALUES (1142, 108, '建邺区', '320105', '', 0, 2);
INSERT INTO `region`
VALUES (1143, 108, '鼓楼区', '320106', '', 0, 2);
INSERT INTO `region`
VALUES (1144, 108, '浦口区', '320111', '', 0, 2);
INSERT INTO `region`
VALUES (1145, 108, '栖霞区', '320113', '', 0, 2);
INSERT INTO `region`
VALUES (1146, 108, '雨花台区', '320114', '', 0, 2);
INSERT INTO `region`
VALUES (1147, 108, '江宁区', '320115', '', 0, 2);
INSERT INTO `region`
VALUES (1148, 108, '六合区', '320116', '', 0, 2);
INSERT INTO `region`
VALUES (1149, 108, '溧水区', '320117', '', 0, 2);
INSERT INTO `region`
VALUES (1150, 108, '高淳区', '320118', '', 0, 2);
INSERT INTO `region`
VALUES (1151, 109, '锡山区', '320205', '', 0, 2);
INSERT INTO `region`
VALUES (1152, 109, '惠山区', '320206', '', 0, 2);
INSERT INTO `region`
VALUES (1153, 109, '滨湖区', '320211', '', 0, 2);
INSERT INTO `region`
VALUES (1154, 109, '梁溪区', '320213', '', 0, 2);
INSERT INTO `region`
VALUES (1155, 109, '新吴区', '320214', '', 0, 2);
INSERT INTO `region`
VALUES (1156, 109, '江阴市', '320281', '', 0, 2);
INSERT INTO `region`
VALUES (1157, 109, '宜兴市', '320282', '', 0, 2);
INSERT INTO `region`
VALUES (1158, 110, '鼓楼区', '320302', '', 0, 2);
INSERT INTO `region`
VALUES (1159, 110, '云龙区', '320303', '', 0, 2);
INSERT INTO `region`
VALUES (1160, 110, '贾汪区', '320305', '', 0, 2);
INSERT INTO `region`
VALUES (1161, 110, '泉山区', '320311', '', 0, 2);
INSERT INTO `region`
VALUES (1162, 110, '铜山区', '320312', '', 0, 2);
INSERT INTO `region`
VALUES (1163, 110, '丰县', '320321', '', 0, 2);
INSERT INTO `region`
VALUES (1164, 110, '沛县', '320322', '', 0, 2);
INSERT INTO `region`
VALUES (1165, 110, '睢宁县', '320324', '', 0, 2);
INSERT INTO `region`
VALUES (1166, 110, '新沂市', '320381', '', 0, 2);
INSERT INTO `region`
VALUES (1167, 110, '邳州市', '320382', '', 0, 2);
INSERT INTO `region`
VALUES (1168, 111, '天宁区', '320402', '', 0, 2);
INSERT INTO `region`
VALUES (1169, 111, '钟楼区', '320404', '', 0, 2);
INSERT INTO `region`
VALUES (1170, 111, '新北区', '320411', '', 0, 2);
INSERT INTO `region`
VALUES (1171, 111, '武进区', '320412', '', 0, 2);
INSERT INTO `region`
VALUES (1172, 111, '金坛区', '320413', '', 0, 2);
INSERT INTO `region`
VALUES (1173, 111, '溧阳市', '320481', '', 0, 2);
INSERT INTO `region`
VALUES (1174, 112, '虎丘区', '320505', '', 0, 2);
INSERT INTO `region`
VALUES (1175, 112, '吴中区', '320506', '', 0, 2);
INSERT INTO `region`
VALUES (1176, 112, '相城区', '320507', '', 0, 2);
INSERT INTO `region`
VALUES (1177, 112, '姑苏区', '320508', '', 0, 2);
INSERT INTO `region`
VALUES (1178, 112, '吴江区', '320509', '', 0, 2);
INSERT INTO `region`
VALUES (1179, 112, '苏州工业园区', '320571', '', 0, 2);
INSERT INTO `region`
VALUES (1180, 112, '常熟市', '320581', '', 0, 2);
INSERT INTO `region`
VALUES (1181, 112, '张家港市', '320582', '', 0, 2);
INSERT INTO `region`
VALUES (1182, 112, '昆山市', '320583', '', 0, 2);
INSERT INTO `region`
VALUES (1183, 112, '太仓市', '320585', '', 0, 2);
INSERT INTO `region`
VALUES (1184, 113, '崇川区', '320602', '', 0, 2);
INSERT INTO `region`
VALUES (1185, 113, '通州区', '320612', '', 0, 2);
INSERT INTO `region`
VALUES (1186, 113, '如东县', '320623', '', 0, 2);
INSERT INTO `region`
VALUES (1187, 113, '启东市', '320681', '', 0, 2);
INSERT INTO `region`
VALUES (1188, 113, '如皋市', '320682', '', 0, 2);
INSERT INTO `region`
VALUES (1189, 113, '海门区', '320684', '', 0, 2);
INSERT INTO `region`
VALUES (1190, 113, '海安市', '320685', '', 0, 2);
INSERT INTO `region`
VALUES (1191, 114, '连云区', '320703', '', 0, 2);
INSERT INTO `region`
VALUES (1192, 114, '海州区', '320706', '', 0, 2);
INSERT INTO `region`
VALUES (1193, 114, '赣榆区', '320707', '', 0, 2);
INSERT INTO `region`
VALUES (1194, 114, '东海县', '320722', '', 0, 2);
INSERT INTO `region`
VALUES (1195, 114, '灌云县', '320723', '', 0, 2);
INSERT INTO `region`
VALUES (1196, 114, '灌南县', '320724', '', 0, 2);
INSERT INTO `region`
VALUES (1197, 115, '淮安区', '320803', '', 0, 2);
INSERT INTO `region`
VALUES (1198, 115, '淮阴区', '320804', '', 0, 2);
INSERT INTO `region`
VALUES (1199, 115, '清江浦区', '320812', '', 0, 2);
INSERT INTO `region`
VALUES (1200, 115, '洪泽区', '320813', '', 0, 2);
INSERT INTO `region`
VALUES (1201, 115, '涟水县', '320826', '', 0, 2);
INSERT INTO `region`
VALUES (1202, 115, '盱眙县', '320830', '', 0, 2);
INSERT INTO `region`
VALUES (1203, 115, '金湖县', '320831', '', 0, 2);
INSERT INTO `region`
VALUES (1204, 116, '亭湖区', '320902', '', 0, 2);
INSERT INTO `region`
VALUES (1205, 116, '盐都区', '320903', '', 0, 2);
INSERT INTO `region`
VALUES (1206, 116, '大丰区', '320904', '', 0, 2);
INSERT INTO `region`
VALUES (1207, 116, '响水县', '320921', '', 0, 2);
INSERT INTO `region`
VALUES (1208, 116, '滨海县', '320922', '', 0, 2);
INSERT INTO `region`
VALUES (1209, 116, '阜宁县', '320923', '', 0, 2);
INSERT INTO `region`
VALUES (1210, 116, '射阳县', '320924', '', 0, 2);
INSERT INTO `region`
VALUES (1211, 116, '建湖县', '320925', '', 0, 2);
INSERT INTO `region`
VALUES (1212, 116, '东台市', '320981', '', 0, 2);
INSERT INTO `region`
VALUES (1213, 117, '广陵区', '321002', '', 0, 2);
INSERT INTO `region`
VALUES (1214, 117, '邗江区', '321003', '', 0, 2);
INSERT INTO `region`
VALUES (1215, 117, '江都区', '321012', '', 0, 2);
INSERT INTO `region`
VALUES (1216, 117, '宝应县', '321023', '', 0, 2);
INSERT INTO `region`
VALUES (1217, 117, '仪征市', '321081', '', 0, 2);
INSERT INTO `region`
VALUES (1218, 117, '高邮市', '321084', '', 0, 2);
INSERT INTO `region`
VALUES (1219, 118, '京口区', '321102', '', 0, 2);
INSERT INTO `region`
VALUES (1220, 118, '润州区', '321111', '', 0, 2);
INSERT INTO `region`
VALUES (1221, 118, '丹徒区', '321112', '', 0, 2);
INSERT INTO `region`
VALUES (1222, 118, '丹阳市', '321181', '', 0, 2);
INSERT INTO `region`
VALUES (1223, 118, '扬中市', '321182', '', 0, 2);
INSERT INTO `region`
VALUES (1224, 118, '句容市', '321183', '', 0, 2);
INSERT INTO `region`
VALUES (1225, 119, '海陵区', '321202', '', 0, 2);
INSERT INTO `region`
VALUES (1226, 119, '高港区', '321203', '', 0, 2);
INSERT INTO `region`
VALUES (1227, 119, '姜堰区', '321204', '', 0, 2);
INSERT INTO `region`
VALUES (1228, 119, '兴化市', '321281', '', 0, 2);
INSERT INTO `region`
VALUES (1229, 119, '靖江市', '321282', '', 0, 2);
INSERT INTO `region`
VALUES (1230, 119, '泰兴市', '321283', '', 0, 2);
INSERT INTO `region`
VALUES (1231, 120, '宿城区', '321302', '', 0, 2);
INSERT INTO `region`
VALUES (1232, 120, '宿豫区', '321311', '', 0, 2);
INSERT INTO `region`
VALUES (1233, 120, '沭阳县', '321322', '', 0, 2);
INSERT INTO `region`
VALUES (1234, 120, '泗阳县', '321323', '', 0, 2);
INSERT INTO `region`
VALUES (1235, 120, '泗洪县', '321324', '', 0, 2);
INSERT INTO `region`
VALUES (1236, 121, '上城区', '330102', '', 0, 2);
INSERT INTO `region`
VALUES (1237, 121, '下城区', '330103', '', 0, 2);
INSERT INTO `region`
VALUES (1238, 121, '江干区', '330104', '', 0, 2);
INSERT INTO `region`
VALUES (1239, 121, '拱墅区', '330105', '', 0, 2);
INSERT INTO `region`
VALUES (1240, 121, '西湖区', '330106', '', 0, 2);
INSERT INTO `region`
VALUES (1241, 121, '滨江区', '330108', '', 0, 2);
INSERT INTO `region`
VALUES (1242, 121, '萧山区', '330109', '', 0, 2);
INSERT INTO `region`
VALUES (1243, 121, '余杭区', '330110', '', 0, 2);
INSERT INTO `region`
VALUES (1244, 121, '富阳区', '330111', '', 0, 2);
INSERT INTO `region`
VALUES (1245, 121, '临安区', '330112', '', 0, 2);
INSERT INTO `region`
VALUES (1246, 121, '临平区', '330113', '', 0, 2);
INSERT INTO `region`
VALUES (1247, 121, '桐庐县', '330122', '', 0, 2);
INSERT INTO `region`
VALUES (1248, 121, '淳安县', '330127', '', 0, 2);
INSERT INTO `region`
VALUES (1249, 121, '建德市', '330182', '', 0, 2);
INSERT INTO `region`
VALUES (1250, 122, '海曙区', '330203', '', 0, 2);
INSERT INTO `region`
VALUES (1251, 122, '江北区', '330205', '', 0, 2);
INSERT INTO `region`
VALUES (1252, 122, '北仑区', '330206', '', 0, 2);
INSERT INTO `region`
VALUES (1253, 122, '镇海区', '330211', '', 0, 2);
INSERT INTO `region`
VALUES (1254, 122, '鄞州区', '330212', '', 0, 2);
INSERT INTO `region`
VALUES (1255, 122, '奉化区', '330213', '', 0, 2);
INSERT INTO `region`
VALUES (1256, 122, '象山县', '330225', '', 0, 2);
INSERT INTO `region`
VALUES (1257, 122, '宁海县', '330226', '', 0, 2);
INSERT INTO `region`
VALUES (1258, 122, '余姚市', '330281', '', 0, 2);
INSERT INTO `region`
VALUES (1259, 122, '慈溪市', '330282', '', 0, 2);
INSERT INTO `region`
VALUES (1260, 123, '鹿城区', '330302', '', 0, 2);
INSERT INTO `region`
VALUES (1261, 123, '龙湾区', '330303', '', 0, 2);
INSERT INTO `region`
VALUES (1262, 123, '瓯海区', '330304', '', 0, 2);
INSERT INTO `region`
VALUES (1263, 123, '洞头区', '330305', '', 0, 2);
INSERT INTO `region`
VALUES (1264, 123, '永嘉县', '330324', '', 0, 2);
INSERT INTO `region`
VALUES (1265, 123, '平阳县', '330326', '', 0, 2);
INSERT INTO `region`
VALUES (1266, 123, '苍南县', '330327', '', 0, 2);
INSERT INTO `region`
VALUES (1267, 123, '文成县', '330328', '', 0, 2);
INSERT INTO `region`
VALUES (1268, 123, '泰顺县', '330329', '', 0, 2);
INSERT INTO `region`
VALUES (1269, 123, '瑞安市', '330381', '', 0, 2);
INSERT INTO `region`
VALUES (1270, 123, '乐清市', '330382', '', 0, 2);
INSERT INTO `region`
VALUES (1271, 123, '龙港市', '330383', '', 0, 2);
INSERT INTO `region`
VALUES (1272, 124, '南湖区', '330402', '', 0, 2);
INSERT INTO `region`
VALUES (1273, 124, '秀洲区', '330411', '', 0, 2);
INSERT INTO `region`
VALUES (1274, 124, '嘉善县', '330421', '', 0, 2);
INSERT INTO `region`
VALUES (1275, 124, '海盐县', '330424', '', 0, 2);
INSERT INTO `region`
VALUES (1276, 124, '海宁市', '330481', '', 0, 2);
INSERT INTO `region`
VALUES (1277, 124, '平湖市', '330482', '', 0, 2);
INSERT INTO `region`
VALUES (1278, 124, '桐乡市', '330483', '', 0, 2);
INSERT INTO `region`
VALUES (1279, 125, '吴兴区', '330502', '', 0, 2);
INSERT INTO `region`
VALUES (1280, 125, '南浔区', '330503', '', 0, 2);
INSERT INTO `region`
VALUES (1281, 125, '德清县', '330521', '', 0, 2);
INSERT INTO `region`
VALUES (1282, 125, '长兴县', '330522', '', 0, 2);
INSERT INTO `region`
VALUES (1283, 125, '安吉县', '330523', '', 0, 2);
INSERT INTO `region`
VALUES (1284, 126, '越城区', '330602', '', 0, 2);
INSERT INTO `region`
VALUES (1285, 126, '柯桥区', '330603', '', 0, 2);
INSERT INTO `region`
VALUES (1286, 126, '上虞区', '330604', '', 0, 2);
INSERT INTO `region`
VALUES (1287, 126, '新昌县', '330624', '', 0, 2);
INSERT INTO `region`
VALUES (1288, 126, '诸暨市', '330681', '', 0, 2);
INSERT INTO `region`
VALUES (1289, 126, '嵊州市', '330683', '', 0, 2);
INSERT INTO `region`
VALUES (1290, 127, '婺城区', '330702', '', 0, 2);
INSERT INTO `region`
VALUES (1291, 127, '金东区', '330703', '', 0, 2);
INSERT INTO `region`
VALUES (1292, 127, '武义县', '330723', '', 0, 2);
INSERT INTO `region`
VALUES (1293, 127, '浦江县', '330726', '', 0, 2);
INSERT INTO `region`
VALUES (1294, 127, '磐安县', '330727', '', 0, 2);
INSERT INTO `region`
VALUES (1295, 127, '兰溪市', '330781', '', 0, 2);
INSERT INTO `region`
VALUES (1296, 127, '义乌市', '330782', '', 0, 2);
INSERT INTO `region`
VALUES (1297, 127, '东阳市', '330783', '', 0, 2);
INSERT INTO `region`
VALUES (1298, 127, '永康市', '330784', '', 0, 2);
INSERT INTO `region`
VALUES (1299, 128, '柯城区', '330802', '', 0, 2);
INSERT INTO `region`
VALUES (1300, 128, '衢江区', '330803', '', 0, 2);
INSERT INTO `region`
VALUES (1301, 128, '常山县', '330822', '', 0, 2);
INSERT INTO `region`
VALUES (1302, 128, '开化县', '330824', '', 0, 2);
INSERT INTO `region`
VALUES (1303, 128, '龙游县', '330825', '', 0, 2);
INSERT INTO `region`
VALUES (1304, 128, '江山市', '330881', '', 0, 2);
INSERT INTO `region`
VALUES (1305, 129, '定海区', '330902', '', 0, 2);
INSERT INTO `region`
VALUES (1306, 129, '普陀区', '330903', '', 0, 2);
INSERT INTO `region`
VALUES (1307, 129, '岱山县', '330921', '', 0, 2);
INSERT INTO `region`
VALUES (1308, 129, '嵊泗县', '330922', '', 0, 2);
INSERT INTO `region`
VALUES (1309, 130, '椒江区', '331002', '', 0, 2);
INSERT INTO `region`
VALUES (1310, 130, '黄岩区', '331003', '', 0, 2);
INSERT INTO `region`
VALUES (1311, 130, '路桥区', '331004', '', 0, 2);
INSERT INTO `region`
VALUES (1312, 130, '三门县', '331022', '', 0, 2);
INSERT INTO `region`
VALUES (1313, 130, '天台县', '331023', '', 0, 2);
INSERT INTO `region`
VALUES (1314, 130, '仙居县', '331024', '', 0, 2);
INSERT INTO `region`
VALUES (1315, 130, '温岭市', '331081', '', 0, 2);
INSERT INTO `region`
VALUES (1316, 130, '临海市', '331082', '', 0, 2);
INSERT INTO `region`
VALUES (1317, 130, '玉环市', '331083', '', 0, 2);
INSERT INTO `region`
VALUES (1318, 131, '莲都区', '331102', '', 0, 2);
INSERT INTO `region`
VALUES (1319, 131, '青田县', '331121', '', 0, 2);
INSERT INTO `region`
VALUES (1320, 131, '缙云县', '331122', '', 0, 2);
INSERT INTO `region`
VALUES (1321, 131, '遂昌县', '331123', '', 0, 2);
INSERT INTO `region`
VALUES (1322, 131, '松阳县', '331124', '', 0, 2);
INSERT INTO `region`
VALUES (1323, 131, '云和县', '331125', '', 0, 2);
INSERT INTO `region`
VALUES (1324, 131, '庆元县', '331126', '', 0, 2);
INSERT INTO `region`
VALUES (1325, 131, '景宁畲族自治县', '331127', '', 0, 2);
INSERT INTO `region`
VALUES (1326, 131, '龙泉市', '331181', '', 0, 2);
INSERT INTO `region`
VALUES (1327, 132, '瑶海区', '340102', '', 0, 2);
INSERT INTO `region`
VALUES (1328, 132, '庐阳区', '340103', '', 0, 2);
INSERT INTO `region`
VALUES (1329, 132, '蜀山区', '340104', '', 0, 2);
INSERT INTO `region`
VALUES (1330, 132, '包河区', '340111', '', 0, 2);
INSERT INTO `region`
VALUES (1331, 132, '长丰县', '340121', '', 0, 2);
INSERT INTO `region`
VALUES (1332, 132, '肥东县', '340122', '', 0, 2);
INSERT INTO `region`
VALUES (1333, 132, '肥西县', '340123', '', 0, 2);
INSERT INTO `region`
VALUES (1334, 132, '庐江县', '340124', '', 0, 2);
INSERT INTO `region`
VALUES (1335, 132, '巢湖市', '340181', '', 0, 2);
INSERT INTO `region`
VALUES (1336, 133, '镜湖区', '340202', '', 0, 2);
INSERT INTO `region`
VALUES (1337, 133, '鸠江区', '340207', '', 0, 2);
INSERT INTO `region`
VALUES (1338, 133, '弋江区', '340209', '', 0, 2);
INSERT INTO `region`
VALUES (1339, 133, '湾沚区', '340210', '', 0, 2);
INSERT INTO `region`
VALUES (1340, 133, '繁昌区', '340211', '', 0, 2);
INSERT INTO `region`
VALUES (1341, 133, '南陵县', '340223', '', 0, 2);
INSERT INTO `region`
VALUES (1342, 133, '无为市', '340281', '', 0, 2);
INSERT INTO `region`
VALUES (1343, 134, '龙子湖区', '340302', '', 0, 2);
INSERT INTO `region`
VALUES (1344, 134, '蚌山区', '340303', '', 0, 2);
INSERT INTO `region`
VALUES (1345, 134, '禹会区', '340304', '', 0, 2);
INSERT INTO `region`
VALUES (1346, 134, '淮上区', '340311', '', 0, 2);
INSERT INTO `region`
VALUES (1347, 134, '怀远县', '340321', '', 0, 2);
INSERT INTO `region`
VALUES (1348, 134, '五河县', '340322', '', 0, 2);
INSERT INTO `region`
VALUES (1349, 134, '固镇县', '340323', '', 0, 2);
INSERT INTO `region`
VALUES (1350, 135, '大通区', '340402', '', 0, 2);
INSERT INTO `region`
VALUES (1351, 135, '田家庵区', '340403', '', 0, 2);
INSERT INTO `region`
VALUES (1352, 135, '谢家集区', '340404', '', 0, 2);
INSERT INTO `region`
VALUES (1353, 135, '八公山区', '340405', '', 0, 2);
INSERT INTO `region`
VALUES (1354, 135, '潘集区', '340406', '', 0, 2);
INSERT INTO `region`
VALUES (1355, 135, '凤台县', '340421', '', 0, 2);
INSERT INTO `region`
VALUES (1356, 135, '寿县', '340422', '', 0, 2);
INSERT INTO `region`
VALUES (1357, 136, '花山区', '340503', '', 0, 2);
INSERT INTO `region`
VALUES (1358, 136, '雨山区', '340504', '', 0, 2);
INSERT INTO `region`
VALUES (1359, 136, '博望区', '340506', '', 0, 2);
INSERT INTO `region`
VALUES (1360, 136, '当涂县', '340521', '', 0, 2);
INSERT INTO `region`
VALUES (1361, 136, '含山县', '340522', '', 0, 2);
INSERT INTO `region`
VALUES (1362, 136, '和县', '340523', '', 0, 2);
INSERT INTO `region`
VALUES (1363, 137, '杜集区', '340602', '', 0, 2);
INSERT INTO `region`
VALUES (1364, 137, '相山区', '340603', '', 0, 2);
INSERT INTO `region`
VALUES (1365, 137, '烈山区', '340604', '', 0, 2);
INSERT INTO `region`
VALUES (1366, 137, '濉溪县', '340621', '', 0, 2);
INSERT INTO `region`
VALUES (1367, 138, '铜官区', '340705', '', 0, 2);
INSERT INTO `region`
VALUES (1368, 138, '义安区', '340706', '', 0, 2);
INSERT INTO `region`
VALUES (1369, 138, '郊区', '340711', '', 0, 2);
INSERT INTO `region`
VALUES (1370, 138, '枞阳县', '340722', '', 0, 2);
INSERT INTO `region`
VALUES (1371, 139, '迎江区', '340802', '', 0, 2);
INSERT INTO `region`
VALUES (1372, 139, '大观区', '340803', '', 0, 2);
INSERT INTO `region`
VALUES (1373, 139, '宜秀区', '340811', '', 0, 2);
INSERT INTO `region`
VALUES (1374, 139, '怀宁县', '340822', '', 0, 2);
INSERT INTO `region`
VALUES (1375, 139, '太湖县', '340825', '', 0, 2);
INSERT INTO `region`
VALUES (1376, 139, '宿松县', '340826', '', 0, 2);
INSERT INTO `region`
VALUES (1377, 139, '望江县', '340827', '', 0, 2);
INSERT INTO `region`
VALUES (1378, 139, '岳西县', '340828', '', 0, 2);
INSERT INTO `region`
VALUES (1379, 139, '桐城市', '340881', '', 0, 2);
INSERT INTO `region`
VALUES (1380, 139, '潜山市', '340882', '', 0, 2);
INSERT INTO `region`
VALUES (1381, 140, '屯溪区', '341002', '', 0, 2);
INSERT INTO `region`
VALUES (1382, 140, '黄山区', '341003', '', 0, 2);
INSERT INTO `region`
VALUES (1383, 140, '徽州区', '341004', '', 0, 2);
INSERT INTO `region`
VALUES (1384, 140, '歙县', '341021', '', 0, 2);
INSERT INTO `region`
VALUES (1385, 140, '休宁县', '341022', '', 0, 2);
INSERT INTO `region`
VALUES (1386, 140, '黟县', '341023', '', 0, 2);
INSERT INTO `region`
VALUES (1387, 140, '祁门县', '341024', '', 0, 2);
INSERT INTO `region`
VALUES (1388, 141, '琅琊区', '341102', '', 0, 2);
INSERT INTO `region`
VALUES (1389, 141, '南谯区', '341103', '', 0, 2);
INSERT INTO `region`
VALUES (1390, 141, '来安县', '341122', '', 0, 2);
INSERT INTO `region`
VALUES (1391, 141, '全椒县', '341124', '', 0, 2);
INSERT INTO `region`
VALUES (1392, 141, '定远县', '341125', '', 0, 2);
INSERT INTO `region`
VALUES (1393, 141, '凤阳县', '341126', '', 0, 2);
INSERT INTO `region`
VALUES (1394, 141, '天长市', '341181', '', 0, 2);
INSERT INTO `region`
VALUES (1395, 141, '明光市', '341182', '', 0, 2);
INSERT INTO `region`
VALUES (1396, 142, '颍州区', '341202', '', 0, 2);
INSERT INTO `region`
VALUES (1397, 142, '颍东区', '341203', '', 0, 2);
INSERT INTO `region`
VALUES (1398, 142, '颍泉区', '341204', '', 0, 2);
INSERT INTO `region`
VALUES (1399, 142, '临泉县', '341221', '', 0, 2);
INSERT INTO `region`
VALUES (1400, 142, '太和县', '341222', '', 0, 2);
INSERT INTO `region`
VALUES (1401, 142, '阜南县', '341225', '', 0, 2);
INSERT INTO `region`
VALUES (1402, 142, '颍上县', '341226', '', 0, 2);
INSERT INTO `region`
VALUES (1403, 142, '界首市', '341282', '', 0, 2);
INSERT INTO `region`
VALUES (1404, 143, '埇桥区', '341302', '', 0, 2);
INSERT INTO `region`
VALUES (1405, 143, '砀山县', '341321', '', 0, 2);
INSERT INTO `region`
VALUES (1406, 143, '萧县', '341322', '', 0, 2);
INSERT INTO `region`
VALUES (1407, 143, '灵璧县', '341323', '', 0, 2);
INSERT INTO `region`
VALUES (1408, 143, '泗县', '341324', '', 0, 2);
INSERT INTO `region`
VALUES (1409, 144, '金安区', '341502', '', 0, 2);
INSERT INTO `region`
VALUES (1410, 144, '裕安区', '341503', '', 0, 2);
INSERT INTO `region`
VALUES (1411, 144, '叶集区', '341504', '', 0, 2);
INSERT INTO `region`
VALUES (1412, 144, '霍邱县', '341522', '', 0, 2);
INSERT INTO `region`
VALUES (1413, 144, '舒城县', '341523', '', 0, 2);
INSERT INTO `region`
VALUES (1414, 144, '金寨县', '341524', '', 0, 2);
INSERT INTO `region`
VALUES (1415, 144, '霍山县', '341525', '', 0, 2);
INSERT INTO `region`
VALUES (1416, 145, '谯城区', '341602', '', 0, 2);
INSERT INTO `region`
VALUES (1417, 145, '涡阳县', '341621', '', 0, 2);
INSERT INTO `region`
VALUES (1418, 145, '蒙城县', '341622', '', 0, 2);
INSERT INTO `region`
VALUES (1419, 145, '利辛县', '341623', '', 0, 2);
INSERT INTO `region`
VALUES (1420, 146, '贵池区', '341702', '', 0, 2);
INSERT INTO `region`
VALUES (1421, 146, '东至县', '341721', '', 0, 2);
INSERT INTO `region`
VALUES (1422, 146, '石台县', '341722', '', 0, 2);
INSERT INTO `region`
VALUES (1423, 146, '青阳县', '341723', '', 0, 2);
INSERT INTO `region`
VALUES (1424, 147, '宣州区', '341802', '', 0, 2);
INSERT INTO `region`
VALUES (1425, 147, '郎溪县', '341821', '', 0, 2);
INSERT INTO `region`
VALUES (1426, 147, '泾县', '341823', '', 0, 2);
INSERT INTO `region`
VALUES (1427, 147, '绩溪县', '341824', '', 0, 2);
INSERT INTO `region`
VALUES (1428, 147, '旌德县', '341825', '', 0, 2);
INSERT INTO `region`
VALUES (1429, 147, '宁国市', '341881', '', 0, 2);
INSERT INTO `region`
VALUES (1430, 147, '广德市', '341882', '', 0, 2);
INSERT INTO `region`
VALUES (1431, 148, '鼓楼区', '350102', '', 0, 2);
INSERT INTO `region`
VALUES (1432, 148, '台江区', '350103', '', 0, 2);
INSERT INTO `region`
VALUES (1433, 148, '仓山区', '350104', '', 0, 2);
INSERT INTO `region`
VALUES (1434, 148, '马尾区', '350105', '', 0, 2);
INSERT INTO `region`
VALUES (1435, 148, '晋安区', '350111', '', 0, 2);
INSERT INTO `region`
VALUES (1436, 148, '长乐区', '350112', '', 0, 2);
INSERT INTO `region`
VALUES (1437, 148, '闽侯县', '350121', '', 0, 2);
INSERT INTO `region`
VALUES (1438, 148, '连江县', '350122', '', 0, 2);
INSERT INTO `region`
VALUES (1439, 148, '罗源县', '350123', '', 0, 2);
INSERT INTO `region`
VALUES (1440, 148, '闽清县', '350124', '', 0, 2);
INSERT INTO `region`
VALUES (1441, 148, '永泰县', '350125', '', 0, 2);
INSERT INTO `region`
VALUES (1442, 148, '平潭县', '350128', '', 0, 2);
INSERT INTO `region`
VALUES (1443, 148, '福清市', '350181', '', 0, 2);
INSERT INTO `region`
VALUES (1444, 149, '思明区', '350203', '', 0, 2);
INSERT INTO `region`
VALUES (1445, 149, '海沧区', '350205', '', 0, 2);
INSERT INTO `region`
VALUES (1446, 149, '湖里区', '350206', '', 0, 2);
INSERT INTO `region`
VALUES (1447, 149, '集美区', '350211', '', 0, 2);
INSERT INTO `region`
VALUES (1448, 149, '同安区', '350212', '', 0, 2);
INSERT INTO `region`
VALUES (1449, 149, '翔安区', '350213', '', 0, 2);
INSERT INTO `region`
VALUES (1450, 150, '城厢区', '350302', '', 0, 2);
INSERT INTO `region`
VALUES (1451, 150, '涵江区', '350303', '', 0, 2);
INSERT INTO `region`
VALUES (1452, 150, '荔城区', '350304', '', 0, 2);
INSERT INTO `region`
VALUES (1453, 150, '秀屿区', '350305', '', 0, 2);
INSERT INTO `region`
VALUES (1454, 150, '仙游县', '350322', '', 0, 2);
INSERT INTO `region`
VALUES (1455, 151, '三元区', '350403', '', 0, 2);
INSERT INTO `region`
VALUES (1456, 151, '明溪县', '350421', '', 0, 2);
INSERT INTO `region`
VALUES (1457, 151, '清流县', '350423', '', 0, 2);
INSERT INTO `region`
VALUES (1458, 151, '宁化县', '350424', '', 0, 2);
INSERT INTO `region`
VALUES (1459, 151, '大田县', '350425', '', 0, 2);
INSERT INTO `region`
VALUES (1460, 151, '尤溪县', '350426', '', 0, 2);
INSERT INTO `region`
VALUES (1461, 151, '沙县区', '350427', '', 0, 2);
INSERT INTO `region`
VALUES (1462, 151, '将乐县', '350428', '', 0, 2);
INSERT INTO `region`
VALUES (1463, 151, '泰宁县', '350429', '', 0, 2);
INSERT INTO `region`
VALUES (1464, 151, '建宁县', '350430', '', 0, 2);
INSERT INTO `region`
VALUES (1465, 151, '永安市', '350481', '', 0, 2);
INSERT INTO `region`
VALUES (1466, 152, '鲤城区', '350502', '', 0, 2);
INSERT INTO `region`
VALUES (1467, 152, '丰泽区', '350503', '', 0, 2);
INSERT INTO `region`
VALUES (1468, 152, '洛江区', '350504', '', 0, 2);
INSERT INTO `region`
VALUES (1469, 152, '泉港区', '350505', '', 0, 2);
INSERT INTO `region`
VALUES (1470, 152, '惠安县', '350521', '', 0, 2);
INSERT INTO `region`
VALUES (1471, 152, '安溪县', '350524', '', 0, 2);
INSERT INTO `region`
VALUES (1472, 152, '永春县', '350525', '', 0, 2);
INSERT INTO `region`
VALUES (1473, 152, '德化县', '350526', '', 0, 2);
INSERT INTO `region`
VALUES (1474, 152, '金门县', '350527', '', 0, 2);
INSERT INTO `region`
VALUES (1475, 152, '石狮市', '350581', '', 0, 2);
INSERT INTO `region`
VALUES (1476, 152, '晋江市', '350582', '', 0, 2);
INSERT INTO `region`
VALUES (1477, 152, '南安市', '350583', '', 0, 2);
INSERT INTO `region`
VALUES (1478, 153, '芗城区', '350602', '', 0, 2);
INSERT INTO `region`
VALUES (1479, 153, '龙文区', '350603', '', 0, 2);
INSERT INTO `region`
VALUES (1480, 153, '云霄县', '350622', '', 0, 2);
INSERT INTO `region`
VALUES (1481, 153, '漳浦县', '350623', '', 0, 2);
INSERT INTO `region`
VALUES (1482, 153, '诏安县', '350624', '', 0, 2);
INSERT INTO `region`
VALUES (1483, 153, '长泰区', '350625', '', 0, 2);
INSERT INTO `region`
VALUES (1484, 153, '东山县', '350626', '', 0, 2);
INSERT INTO `region`
VALUES (1485, 153, '南靖县', '350627', '', 0, 2);
INSERT INTO `region`
VALUES (1486, 153, '平和县', '350628', '', 0, 2);
INSERT INTO `region`
VALUES (1487, 153, '华安县', '350629', '', 0, 2);
INSERT INTO `region`
VALUES (1488, 153, '龙海区', '350681', '', 0, 2);
INSERT INTO `region`
VALUES (1489, 154, '延平区', '350702', '', 0, 2);
INSERT INTO `region`
VALUES (1490, 154, '建阳区', '350703', '', 0, 2);
INSERT INTO `region`
VALUES (1491, 154, '顺昌县', '350721', '', 0, 2);
INSERT INTO `region`
VALUES (1492, 154, '浦城县', '350722', '', 0, 2);
INSERT INTO `region`
VALUES (1493, 154, '光泽县', '350723', '', 0, 2);
INSERT INTO `region`
VALUES (1494, 154, '松溪县', '350724', '', 0, 2);
INSERT INTO `region`
VALUES (1495, 154, '政和县', '350725', '', 0, 2);
INSERT INTO `region`
VALUES (1496, 154, '邵武市', '350781', '', 0, 2);
INSERT INTO `region`
VALUES (1497, 154, '武夷山市', '350782', '', 0, 2);
INSERT INTO `region`
VALUES (1498, 154, '建瓯市', '350783', '', 0, 2);
INSERT INTO `region`
VALUES (1499, 155, '新罗区', '350802', '', 0, 2);
INSERT INTO `region`
VALUES (1500, 155, '永定区', '350803', '', 0, 2);
INSERT INTO `region`
VALUES (1501, 155, '长汀县', '350821', '', 0, 2);
INSERT INTO `region`
VALUES (1502, 155, '上杭县', '350823', '', 0, 2);
INSERT INTO `region`
VALUES (1503, 155, '武平县', '350824', '', 0, 2);
INSERT INTO `region`
VALUES (1504, 155, '连城县', '350825', '', 0, 2);
INSERT INTO `region`
VALUES (1505, 155, '漳平市', '350881', '', 0, 2);
INSERT INTO `region`
VALUES (1506, 156, '蕉城区', '350902', '', 0, 2);
INSERT INTO `region`
VALUES (1507, 156, '霞浦县', '350921', '', 0, 2);
INSERT INTO `region`
VALUES (1508, 156, '古田县', '350922', '', 0, 2);
INSERT INTO `region`
VALUES (1509, 156, '屏南县', '350923', '', 0, 2);
INSERT INTO `region`
VALUES (1510, 156, '寿宁县', '350924', '', 0, 2);
INSERT INTO `region`
VALUES (1511, 156, '周宁县', '350925', '', 0, 2);
INSERT INTO `region`
VALUES (1512, 156, '柘荣县', '350926', '', 0, 2);
INSERT INTO `region`
VALUES (1513, 156, '福安市', '350981', '', 0, 2);
INSERT INTO `region`
VALUES (1514, 156, '福鼎市', '350982', '', 0, 2);
INSERT INTO `region`
VALUES (1515, 157, '东湖区', '360102', '', 0, 2);
INSERT INTO `region`
VALUES (1516, 157, '西湖区', '360103', '', 0, 2);
INSERT INTO `region`
VALUES (1517, 157, '青云谱区', '360104', '', 0, 2);
INSERT INTO `region`
VALUES (1518, 157, '青山湖区', '360111', '', 0, 2);
INSERT INTO `region`
VALUES (1519, 157, '新建区', '360112', '', 0, 2);
INSERT INTO `region`
VALUES (1520, 157, '红谷滩区', '360113', '', 0, 2);
INSERT INTO `region`
VALUES (1521, 157, '南昌县', '360121', '', 0, 2);
INSERT INTO `region`
VALUES (1522, 157, '安义县', '360123', '', 0, 2);
INSERT INTO `region`
VALUES (1523, 157, '进贤县', '360124', '', 0, 2);
INSERT INTO `region`
VALUES (1524, 158, '昌江区', '360202', '', 0, 2);
INSERT INTO `region`
VALUES (1525, 158, '珠山区', '360203', '', 0, 2);
INSERT INTO `region`
VALUES (1526, 158, '浮梁县', '360222', '', 0, 2);
INSERT INTO `region`
VALUES (1527, 158, '乐平市', '360281', '', 0, 2);
INSERT INTO `region`
VALUES (1528, 159, '安源区', '360302', '', 0, 2);
INSERT INTO `region`
VALUES (1529, 159, '湘东区', '360313', '', 0, 2);
INSERT INTO `region`
VALUES (1530, 159, '莲花县', '360321', '', 0, 2);
INSERT INTO `region`
VALUES (1531, 159, '上栗县', '360322', '', 0, 2);
INSERT INTO `region`
VALUES (1532, 159, '芦溪县', '360323', '', 0, 2);
INSERT INTO `region`
VALUES (1533, 160, '濂溪区', '360402', '', 0, 2);
INSERT INTO `region`
VALUES (1534, 160, '浔阳区', '360403', '', 0, 2);
INSERT INTO `region`
VALUES (1535, 160, '柴桑区', '360404', '', 0, 2);
INSERT INTO `region`
VALUES (1536, 160, '武宁县', '360423', '', 0, 2);
INSERT INTO `region`
VALUES (1537, 160, '修水县', '360424', '', 0, 2);
INSERT INTO `region`
VALUES (1538, 160, '永修县', '360425', '', 0, 2);
INSERT INTO `region`
VALUES (1539, 160, '德安县', '360426', '', 0, 2);
INSERT INTO `region`
VALUES (1540, 160, '都昌县', '360428', '', 0, 2);
INSERT INTO `region`
VALUES (1541, 160, '湖口县', '360429', '', 0, 2);
INSERT INTO `region`
VALUES (1542, 160, '彭泽县', '360430', '', 0, 2);
INSERT INTO `region`
VALUES (1543, 160, '瑞昌市', '360481', '', 0, 2);
INSERT INTO `region`
VALUES (1544, 160, '共青城市', '360482', '', 0, 2);
INSERT INTO `region`
VALUES (1545, 160, '庐山市', '360483', '', 0, 2);
INSERT INTO `region`
VALUES (1546, 161, '渝水区', '360502', '', 0, 2);
INSERT INTO `region`
VALUES (1547, 161, '分宜县', '360521', '', 0, 2);
INSERT INTO `region`
VALUES (1548, 162, '月湖区', '360602', '', 0, 2);
INSERT INTO `region`
VALUES (1549, 162, '余江区', '360603', '', 0, 2);
INSERT INTO `region`
VALUES (1550, 162, '贵溪市', '360681', '', 0, 2);
INSERT INTO `region`
VALUES (1551, 163, '章贡区', '360702', '', 0, 2);
INSERT INTO `region`
VALUES (1552, 163, '南康区', '360703', '', 0, 2);
INSERT INTO `region`
VALUES (1553, 163, '赣县区', '360704', '', 0, 2);
INSERT INTO `region`
VALUES (1554, 163, '信丰县', '360722', '', 0, 2);
INSERT INTO `region`
VALUES (1555, 163, '大余县', '360723', '', 0, 2);
INSERT INTO `region`
VALUES (1556, 163, '上犹县', '360724', '', 0, 2);
INSERT INTO `region`
VALUES (1557, 163, '崇义县', '360725', '', 0, 2);
INSERT INTO `region`
VALUES (1558, 163, '安远县', '360726', '', 0, 2);
INSERT INTO `region`
VALUES (1559, 163, '定南县', '360728', '', 0, 2);
INSERT INTO `region`
VALUES (1560, 163, '全南县', '360729', '', 0, 2);
INSERT INTO `region`
VALUES (1561, 163, '宁都县', '360730', '', 0, 2);
INSERT INTO `region`
VALUES (1562, 163, '于都县', '360731', '', 0, 2);
INSERT INTO `region`
VALUES (1563, 163, '兴国县', '360732', '', 0, 2);
INSERT INTO `region`
VALUES (1564, 163, '会昌县', '360733', '', 0, 2);
INSERT INTO `region`
VALUES (1565, 163, '寻乌县', '360734', '', 0, 2);
INSERT INTO `region`
VALUES (1566, 163, '石城县', '360735', '', 0, 2);
INSERT INTO `region`
VALUES (1567, 163, '瑞金市', '360781', '', 0, 2);
INSERT INTO `region`
VALUES (1568, 163, '龙南市', '360783', '', 0, 2);
INSERT INTO `region`
VALUES (1569, 164, '吉州区', '360802', '', 0, 2);
INSERT INTO `region`
VALUES (1570, 164, '青原区', '360803', '', 0, 2);
INSERT INTO `region`
VALUES (1571, 164, '吉安县', '360821', '', 0, 2);
INSERT INTO `region`
VALUES (1572, 164, '吉水县', '360822', '', 0, 2);
INSERT INTO `region`
VALUES (1573, 164, '峡江县', '360823', '', 0, 2);
INSERT INTO `region`
VALUES (1574, 164, '新干县', '360824', '', 0, 2);
INSERT INTO `region`
VALUES (1575, 164, '永丰县', '360825', '', 0, 2);
INSERT INTO `region`
VALUES (1576, 164, '泰和县', '360826', '', 0, 2);
INSERT INTO `region`
VALUES (1577, 164, '遂川县', '360827', '', 0, 2);
INSERT INTO `region`
VALUES (1578, 164, '万安县', '360828', '', 0, 2);
INSERT INTO `region`
VALUES (1579, 164, '安福县', '360829', '', 0, 2);
INSERT INTO `region`
VALUES (1580, 164, '永新县', '360830', '', 0, 2);
INSERT INTO `region`
VALUES (1581, 164, '井冈山市', '360881', '', 0, 2);
INSERT INTO `region`
VALUES (1582, 165, '袁州区', '360902', '', 0, 2);
INSERT INTO `region`
VALUES (1583, 165, '奉新县', '360921', '', 0, 2);
INSERT INTO `region`
VALUES (1584, 165, '万载县', '360922', '', 0, 2);
INSERT INTO `region`
VALUES (1585, 165, '上高县', '360923', '', 0, 2);
INSERT INTO `region`
VALUES (1586, 165, '宜丰县', '360924', '', 0, 2);
INSERT INTO `region`
VALUES (1587, 165, '靖安县', '360925', '', 0, 2);
INSERT INTO `region`
VALUES (1588, 165, '铜鼓县', '360926', '', 0, 2);
INSERT INTO `region`
VALUES (1589, 165, '丰城市', '360981', '', 0, 2);
INSERT INTO `region`
VALUES (1590, 165, '樟树市', '360982', '', 0, 2);
INSERT INTO `region`
VALUES (1591, 165, '高安市', '360983', '', 0, 2);
INSERT INTO `region`
VALUES (1592, 166, '临川区', '361002', '', 0, 2);
INSERT INTO `region`
VALUES (1593, 166, '东乡区', '361003', '', 0, 2);
INSERT INTO `region`
VALUES (1594, 166, '南城县', '361021', '', 0, 2);
INSERT INTO `region`
VALUES (1595, 166, '黎川县', '361022', '', 0, 2);
INSERT INTO `region`
VALUES (1596, 166, '南丰县', '361023', '', 0, 2);
INSERT INTO `region`
VALUES (1597, 166, '崇仁县', '361024', '', 0, 2);
INSERT INTO `region`
VALUES (1598, 166, '乐安县', '361025', '', 0, 2);
INSERT INTO `region`
VALUES (1599, 166, '宜黄县', '361026', '', 0, 2);
INSERT INTO `region`
VALUES (1600, 166, '金溪县', '361027', '', 0, 2);
INSERT INTO `region`
VALUES (1601, 166, '资溪县', '361028', '', 0, 2);
INSERT INTO `region`
VALUES (1602, 166, '广昌县', '361030', '', 0, 2);
INSERT INTO `region`
VALUES (1603, 167, '信州区', '361102', '', 0, 2);
INSERT INTO `region`
VALUES (1604, 167, '广丰区', '361103', '', 0, 2);
INSERT INTO `region`
VALUES (1605, 167, '广信区', '361104', '', 0, 2);
INSERT INTO `region`
VALUES (1606, 167, '玉山县', '361123', '', 0, 2);
INSERT INTO `region`
VALUES (1607, 167, '铅山县', '361124', '', 0, 2);
INSERT INTO `region`
VALUES (1608, 167, '横峰县', '361125', '', 0, 2);
INSERT INTO `region`
VALUES (1609, 167, '弋阳县', '361126', '', 0, 2);
INSERT INTO `region`
VALUES (1610, 167, '余干县', '361127', '', 0, 2);
INSERT INTO `region`
VALUES (1611, 167, '鄱阳县', '361128', '', 0, 2);
INSERT INTO `region`
VALUES (1612, 167, '万年县', '361129', '', 0, 2);
INSERT INTO `region`
VALUES (1613, 167, '婺源县', '361130', '', 0, 2);
INSERT INTO `region`
VALUES (1614, 167, '德兴市', '361181', '', 0, 2);
INSERT INTO `region`
VALUES (1615, 168, '历下区', '370102', '', 0, 2);
INSERT INTO `region`
VALUES (1616, 168, '市中区', '370103', '', 0, 2);
INSERT INTO `region`
VALUES (1617, 168, '槐荫区', '370104', '', 0, 2);
INSERT INTO `region`
VALUES (1618, 168, '天桥区', '370105', '', 0, 2);
INSERT INTO `region`
VALUES (1619, 168, '历城区', '370112', '', 0, 2);
INSERT INTO `region`
VALUES (1620, 168, '长清区', '370113', '', 0, 2);
INSERT INTO `region`
VALUES (1621, 168, '章丘区', '370114', '', 0, 2);
INSERT INTO `region`
VALUES (1622, 168, '济阳区', '370115', '', 0, 2);
INSERT INTO `region`
VALUES (1623, 168, '莱芜区', '370116', '', 0, 2);
INSERT INTO `region`
VALUES (1624, 168, '钢城区', '370117', '', 0, 2);
INSERT INTO `region`
VALUES (1625, 168, '平阴县', '370124', '', 0, 2);
INSERT INTO `region`
VALUES (1626, 168, '商河县', '370126', '', 0, 2);
INSERT INTO `region`
VALUES (1627, 169, '市南区', '370202', '', 0, 2);
INSERT INTO `region`
VALUES (1628, 169, '市北区', '370203', '', 0, 2);
INSERT INTO `region`
VALUES (1629, 169, '黄岛区', '370211', '', 0, 2);
INSERT INTO `region`
VALUES (1630, 169, '崂山区', '370212', '', 0, 2);
INSERT INTO `region`
VALUES (1631, 169, '李沧区', '370213', '', 0, 2);
INSERT INTO `region`
VALUES (1632, 169, '城阳区', '370214', '', 0, 2);
INSERT INTO `region`
VALUES (1633, 169, '即墨区', '370215', '', 0, 2);
INSERT INTO `region`
VALUES (1634, 169, '胶州市', '370281', '', 0, 2);
INSERT INTO `region`
VALUES (1635, 169, '平度市', '370283', '', 0, 2);
INSERT INTO `region`
VALUES (1636, 169, '莱西市', '370285', '', 0, 2);
INSERT INTO `region`
VALUES (1637, 170, '淄川区', '370302', '', 0, 2);
INSERT INTO `region`
VALUES (1638, 170, '张店区', '370303', '', 0, 2);
INSERT INTO `region`
VALUES (1639, 170, '博山区', '370304', '', 0, 2);
INSERT INTO `region`
VALUES (1640, 170, '临淄区', '370305', '', 0, 2);
INSERT INTO `region`
VALUES (1641, 170, '周村区', '370306', '', 0, 2);
INSERT INTO `region`
VALUES (1642, 170, '桓台县', '370321', '', 0, 2);
INSERT INTO `region`
VALUES (1643, 170, '高青县', '370322', '', 0, 2);
INSERT INTO `region`
VALUES (1644, 170, '沂源县', '370323', '', 0, 2);
INSERT INTO `region`
VALUES (1645, 171, '市中区', '370402', '', 0, 2);
INSERT INTO `region`
VALUES (1646, 171, '薛城区', '370403', '', 0, 2);
INSERT INTO `region`
VALUES (1647, 171, '峄城区', '370404', '', 0, 2);
INSERT INTO `region`
VALUES (1648, 171, '台儿庄区', '370405', '', 0, 2);
INSERT INTO `region`
VALUES (1649, 171, '山亭区', '370406', '', 0, 2);
INSERT INTO `region`
VALUES (1650, 171, '滕州市', '370481', '', 0, 2);
INSERT INTO `region`
VALUES (1651, 172, '东营区', '370502', '', 0, 2);
INSERT INTO `region`
VALUES (1652, 172, '河口区', '370503', '', 0, 2);
INSERT INTO `region`
VALUES (1653, 172, '垦利区', '370505', '', 0, 2);
INSERT INTO `region`
VALUES (1654, 172, '利津县', '370522', '', 0, 2);
INSERT INTO `region`
VALUES (1655, 172, '广饶县', '370523', '', 0, 2);
INSERT INTO `region`
VALUES (1656, 173, '芝罘区', '370602', '', 0, 2);
INSERT INTO `region`
VALUES (1657, 173, '福山区', '370611', '', 0, 2);
INSERT INTO `region`
VALUES (1658, 173, '牟平区', '370612', '', 0, 2);
INSERT INTO `region`
VALUES (1659, 173, '莱山区', '370613', '', 0, 2);
INSERT INTO `region`
VALUES (1660, 173, '蓬莱区', '370614', '', 0, 2);
INSERT INTO `region`
VALUES (1661, 173, '龙口市', '370681', '', 0, 2);
INSERT INTO `region`
VALUES (1662, 173, '莱阳市', '370682', '', 0, 2);
INSERT INTO `region`
VALUES (1663, 173, '莱州市', '370683', '', 0, 2);
INSERT INTO `region`
VALUES (1664, 173, '招远市', '370685', '', 0, 2);
INSERT INTO `region`
VALUES (1665, 173, '栖霞市', '370686', '', 0, 2);
INSERT INTO `region`
VALUES (1666, 173, '海阳市', '370687', '', 0, 2);
INSERT INTO `region`
VALUES (1667, 174, '潍城区', '370702', '', 0, 2);
INSERT INTO `region`
VALUES (1668, 174, '寒亭区', '370703', '', 0, 2);
INSERT INTO `region`
VALUES (1669, 174, '坊子区', '370704', '', 0, 2);
INSERT INTO `region`
VALUES (1670, 174, '奎文区', '370705', '', 0, 2);
INSERT INTO `region`
VALUES (1671, 174, '临朐县', '370724', '', 0, 2);
INSERT INTO `region`
VALUES (1672, 174, '昌乐县', '370725', '', 0, 2);
INSERT INTO `region`
VALUES (1673, 174, '青州市', '370781', '', 0, 2);
INSERT INTO `region`
VALUES (1674, 174, '诸城市', '370782', '', 0, 2);
INSERT INTO `region`
VALUES (1675, 174, '寿光市', '370783', '', 0, 2);
INSERT INTO `region`
VALUES (1676, 174, '安丘市', '370784', '', 0, 2);
INSERT INTO `region`
VALUES (1677, 174, '高密市', '370785', '', 0, 2);
INSERT INTO `region`
VALUES (1678, 174, '昌邑市', '370786', '', 0, 2);
INSERT INTO `region`
VALUES (1679, 175, '任城区', '370811', '', 0, 2);
INSERT INTO `region`
VALUES (1680, 175, '兖州区', '370812', '', 0, 2);
INSERT INTO `region`
VALUES (1681, 175, '微山县', '370826', '', 0, 2);
INSERT INTO `region`
VALUES (1682, 175, '鱼台县', '370827', '', 0, 2);
INSERT INTO `region`
VALUES (1683, 175, '金乡县', '370828', '', 0, 2);
INSERT INTO `region`
VALUES (1684, 175, '嘉祥县', '370829', '', 0, 2);
INSERT INTO `region`
VALUES (1685, 175, '汶上县', '370830', '', 0, 2);
INSERT INTO `region`
VALUES (1686, 175, '泗水县', '370831', '', 0, 2);
INSERT INTO `region`
VALUES (1687, 175, '梁山县', '370832', '', 0, 2);
INSERT INTO `region`
VALUES (1688, 175, '曲阜市', '370881', '', 0, 2);
INSERT INTO `region`
VALUES (1689, 175, '邹城市', '370883', '', 0, 2);
INSERT INTO `region`
VALUES (1690, 176, '泰山区', '370902', '', 0, 2);
INSERT INTO `region`
VALUES (1691, 176, '岱岳区', '370911', '', 0, 2);
INSERT INTO `region`
VALUES (1692, 176, '宁阳县', '370921', '', 0, 2);
INSERT INTO `region`
VALUES (1693, 176, '东平县', '370923', '', 0, 2);
INSERT INTO `region`
VALUES (1694, 176, '新泰市', '370982', '', 0, 2);
INSERT INTO `region`
VALUES (1695, 176, '肥城市', '370983', '', 0, 2);
INSERT INTO `region`
VALUES (1696, 177, '环翠区', '371002', '', 0, 2);
INSERT INTO `region`
VALUES (1697, 177, '文登区', '371003', '', 0, 2);
INSERT INTO `region`
VALUES (1698, 177, '荣成市', '371082', '', 0, 2);
INSERT INTO `region`
VALUES (1699, 177, '乳山市', '371083', '', 0, 2);
INSERT INTO `region`
VALUES (1700, 178, '东港区', '371102', '', 0, 2);
INSERT INTO `region`
VALUES (1701, 178, '岚山区', '371103', '', 0, 2);
INSERT INTO `region`
VALUES (1702, 178, '五莲县', '371121', '', 0, 2);
INSERT INTO `region`
VALUES (1703, 178, '莒县', '371122', '', 0, 2);
INSERT INTO `region`
VALUES (1704, 179, '兰山区', '371302', '', 0, 2);
INSERT INTO `region`
VALUES (1705, 179, '罗庄区', '371311', '', 0, 2);
INSERT INTO `region`
VALUES (1706, 179, '河东区', '371312', '', 0, 2);
INSERT INTO `region`
VALUES (1707, 179, '沂南县', '371321', '', 0, 2);
INSERT INTO `region`
VALUES (1708, 179, '郯城县', '371322', '', 0, 2);
INSERT INTO `region`
VALUES (1709, 179, '沂水县', '371323', '', 0, 2);
INSERT INTO `region`
VALUES (1710, 179, '兰陵县', '371324', '', 0, 2);
INSERT INTO `region`
VALUES (1711, 179, '费县', '371325', '', 0, 2);
INSERT INTO `region`
VALUES (1712, 179, '平邑县', '371326', '', 0, 2);
INSERT INTO `region`
VALUES (1713, 179, '莒南县', '371327', '', 0, 2);
INSERT INTO `region`
VALUES (1714, 179, '蒙阴县', '371328', '', 0, 2);
INSERT INTO `region`
VALUES (1715, 179, '临沭县', '371329', '', 0, 2);
INSERT INTO `region`
VALUES (1716, 180, '德城区', '371402', '', 0, 2);
INSERT INTO `region`
VALUES (1717, 180, '陵城区', '371403', '', 0, 2);
INSERT INTO `region`
VALUES (1718, 180, '宁津县', '371422', '', 0, 2);
INSERT INTO `region`
VALUES (1719, 180, '庆云县', '371423', '', 0, 2);
INSERT INTO `region`
VALUES (1720, 180, '临邑县', '371424', '', 0, 2);
INSERT INTO `region`
VALUES (1721, 180, '齐河县', '371425', '', 0, 2);
INSERT INTO `region`
VALUES (1722, 180, '平原县', '371426', '', 0, 2);
INSERT INTO `region`
VALUES (1723, 180, '夏津县', '371427', '', 0, 2);
INSERT INTO `region`
VALUES (1724, 180, '武城县', '371428', '', 0, 2);
INSERT INTO `region`
VALUES (1725, 180, '乐陵市', '371481', '', 0, 2);
INSERT INTO `region`
VALUES (1726, 180, '禹城市', '371482', '', 0, 2);
INSERT INTO `region`
VALUES (1727, 181, '东昌府区', '371502', '', 0, 2);
INSERT INTO `region`
VALUES (1728, 181, '茌平区', '371503', '', 0, 2);
INSERT INTO `region`
VALUES (1729, 181, '阳谷县', '371521', '', 0, 2);
INSERT INTO `region`
VALUES (1730, 181, '莘县', '371522', '', 0, 2);
INSERT INTO `region`
VALUES (1731, 181, '东阿县', '371524', '', 0, 2);
INSERT INTO `region`
VALUES (1732, 181, '冠县', '371525', '', 0, 2);
INSERT INTO `region`
VALUES (1733, 181, '高唐县', '371526', '', 0, 2);
INSERT INTO `region`
VALUES (1734, 181, '临清市', '371581', '', 0, 2);
INSERT INTO `region`
VALUES (1735, 182, '滨城区', '371602', '', 0, 2);
INSERT INTO `region`
VALUES (1736, 182, '沾化区', '371603', '', 0, 2);
INSERT INTO `region`
VALUES (1737, 182, '惠民县', '371621', '', 0, 2);
INSERT INTO `region`
VALUES (1738, 182, '阳信县', '371622', '', 0, 2);
INSERT INTO `region`
VALUES (1739, 182, '无棣县', '371623', '', 0, 2);
INSERT INTO `region`
VALUES (1740, 182, '博兴县', '371625', '', 0, 2);
INSERT INTO `region`
VALUES (1741, 182, '邹平市', '371681', '', 0, 2);
INSERT INTO `region`
VALUES (1742, 183, '牡丹区', '371702', '', 0, 2);
INSERT INTO `region`
VALUES (1743, 183, '定陶区', '371703', '', 0, 2);
INSERT INTO `region`
VALUES (1744, 183, '曹县', '371721', '', 0, 2);
INSERT INTO `region`
VALUES (1745, 183, '单县', '371722', '', 0, 2);
INSERT INTO `region`
VALUES (1746, 183, '成武县', '371723', '', 0, 2);
INSERT INTO `region`
VALUES (1747, 183, '巨野县', '371724', '', 0, 2);
INSERT INTO `region`
VALUES (1748, 183, '郓城县', '371725', '', 0, 2);
INSERT INTO `region`
VALUES (1749, 183, '鄄城县', '371726', '', 0, 2);
INSERT INTO `region`
VALUES (1750, 183, '东明县', '371728', '', 0, 2);
INSERT INTO `region`
VALUES (1751, 184, '中原区', '410102', '', 0, 2);
INSERT INTO `region`
VALUES (1752, 184, '二七区', '410103', '', 0, 2);
INSERT INTO `region`
VALUES (1753, 184, '管城回族区', '410104', '', 0, 2);
INSERT INTO `region`
VALUES (1754, 184, '金水区', '410105', '', 0, 2);
INSERT INTO `region`
VALUES (1755, 184, '上街区', '410106', '', 0, 2);
INSERT INTO `region`
VALUES (1756, 184, '惠济区', '410108', '', 0, 2);
INSERT INTO `region`
VALUES (1757, 184, '中牟县', '410122', '', 0, 2);
INSERT INTO `region`
VALUES (1758, 184, '巩义市', '410181', '', 0, 2);
INSERT INTO `region`
VALUES (1759, 184, '荥阳市', '410182', '', 0, 2);
INSERT INTO `region`
VALUES (1760, 184, '新密市', '410183', '', 0, 2);
INSERT INTO `region`
VALUES (1761, 184, '新郑市', '410184', '', 0, 2);
INSERT INTO `region`
VALUES (1762, 184, '登封市', '410185', '', 0, 2);
INSERT INTO `region`
VALUES (1763, 185, '龙亭区', '410202', '', 0, 2);
INSERT INTO `region`
VALUES (1764, 185, '顺河回族区', '410203', '', 0, 2);
INSERT INTO `region`
VALUES (1765, 185, '鼓楼区', '410204', '', 0, 2);
INSERT INTO `region`
VALUES (1766, 185, '禹王台区', '410205', '', 0, 2);
INSERT INTO `region`
VALUES (1767, 185, '祥符区', '410212', '', 0, 2);
INSERT INTO `region`
VALUES (1768, 185, '杞县', '410221', '', 0, 2);
INSERT INTO `region`
VALUES (1769, 185, '通许县', '410222', '', 0, 2);
INSERT INTO `region`
VALUES (1770, 185, '尉氏县', '410223', '', 0, 2);
INSERT INTO `region`
VALUES (1771, 185, '兰考县', '410225', '', 0, 2);
INSERT INTO `region`
VALUES (1772, 186, '老城区', '410302', '', 0, 2);
INSERT INTO `region`
VALUES (1773, 186, '西工区', '410303', '', 0, 2);
INSERT INTO `region`
VALUES (1774, 186, '瀍河回族区', '410304', '', 0, 2);
INSERT INTO `region`
VALUES (1775, 186, '涧西区', '410305', '', 0, 2);
INSERT INTO `region`
VALUES (1776, 186, '吉利区', '410306', '', 0, 2);
INSERT INTO `region`
VALUES (1777, 186, '洛龙区', '410311', '', 0, 2);
INSERT INTO `region`
VALUES (1778, 186, '孟津县', '410322', '', 0, 2);
INSERT INTO `region`
VALUES (1779, 186, '新安县', '410323', '', 0, 2);
INSERT INTO `region`
VALUES (1780, 186, '栾川县', '410324', '', 0, 2);
INSERT INTO `region`
VALUES (1781, 186, '嵩县', '410325', '', 0, 2);
INSERT INTO `region`
VALUES (1782, 186, '汝阳县', '410326', '', 0, 2);
INSERT INTO `region`
VALUES (1783, 186, '宜阳县', '410327', '', 0, 2);
INSERT INTO `region`
VALUES (1784, 186, '洛宁县', '410328', '', 0, 2);
INSERT INTO `region`
VALUES (1785, 186, '伊川县', '410329', '', 0, 2);
INSERT INTO `region`
VALUES (1786, 186, '偃师市', '410381', '', 0, 2);
INSERT INTO `region`
VALUES (1787, 187, '新华区', '410402', '', 0, 2);
INSERT INTO `region`
VALUES (1788, 187, '卫东区', '410403', '', 0, 2);
INSERT INTO `region`
VALUES (1789, 187, '石龙区', '410404', '', 0, 2);
INSERT INTO `region`
VALUES (1790, 187, '湛河区', '410411', '', 0, 2);
INSERT INTO `region`
VALUES (1791, 187, '宝丰县', '410421', '', 0, 2);
INSERT INTO `region`
VALUES (1792, 187, '叶县', '410422', '', 0, 2);
INSERT INTO `region`
VALUES (1793, 187, '鲁山县', '410423', '', 0, 2);
INSERT INTO `region`
VALUES (1794, 187, '郏县', '410425', '', 0, 2);
INSERT INTO `region`
VALUES (1795, 187, '舞钢市', '410481', '', 0, 2);
INSERT INTO `region`
VALUES (1796, 187, '汝州市', '410482', '', 0, 2);
INSERT INTO `region`
VALUES (1797, 188, '文峰区', '410502', '', 0, 2);
INSERT INTO `region`
VALUES (1798, 188, '北关区', '410503', '', 0, 2);
INSERT INTO `region`
VALUES (1799, 188, '殷都区', '410505', '', 0, 2);
INSERT INTO `region`
VALUES (1800, 188, '龙安区', '410506', '', 0, 2);
INSERT INTO `region`
VALUES (1801, 188, '安阳县', '410522', '', 0, 2);
INSERT INTO `region`
VALUES (1802, 188, '汤阴县', '410523', '', 0, 2);
INSERT INTO `region`
VALUES (1803, 188, '滑县', '410526', '', 0, 2);
INSERT INTO `region`
VALUES (1804, 188, '内黄县', '410527', '', 0, 2);
INSERT INTO `region`
VALUES (1805, 188, '林州市', '410581', '', 0, 2);
INSERT INTO `region`
VALUES (1806, 189, '鹤山区', '410602', '', 0, 2);
INSERT INTO `region`
VALUES (1807, 189, '山城区', '410603', '', 0, 2);
INSERT INTO `region`
VALUES (1808, 189, '淇滨区', '410611', '', 0, 2);
INSERT INTO `region`
VALUES (1809, 189, '浚县', '410621', '', 0, 2);
INSERT INTO `region`
VALUES (1810, 189, '淇县', '410622', '', 0, 2);
INSERT INTO `region`
VALUES (1811, 190, '红旗区', '410702', '', 0, 2);
INSERT INTO `region`
VALUES (1812, 190, '卫滨区', '410703', '', 0, 2);
INSERT INTO `region`
VALUES (1813, 190, '凤泉区', '410704', '', 0, 2);
INSERT INTO `region`
VALUES (1814, 190, '牧野区', '410711', '', 0, 2);
INSERT INTO `region`
VALUES (1815, 190, '新乡县', '410721', '', 0, 2);
INSERT INTO `region`
VALUES (1816, 190, '获嘉县', '410724', '', 0, 2);
INSERT INTO `region`
VALUES (1817, 190, '原阳县', '410725', '', 0, 2);
INSERT INTO `region`
VALUES (1818, 190, '延津县', '410726', '', 0, 2);
INSERT INTO `region`
VALUES (1819, 190, '封丘县', '410727', '', 0, 2);
INSERT INTO `region`
VALUES (1820, 190, '卫辉市', '410781', '', 0, 2);
INSERT INTO `region`
VALUES (1821, 190, '辉县市', '410782', '', 0, 2);
INSERT INTO `region`
VALUES (1822, 190, '长垣市', '410783', '', 0, 2);
INSERT INTO `region`
VALUES (1823, 191, '解放区', '410802', '', 0, 2);
INSERT INTO `region`
VALUES (1824, 191, '中站区', '410803', '', 0, 2);
INSERT INTO `region`
VALUES (1825, 191, '马村区', '410804', '', 0, 2);
INSERT INTO `region`
VALUES (1826, 191, '山阳区', '410811', '', 0, 2);
INSERT INTO `region`
VALUES (1827, 191, '修武县', '410821', '', 0, 2);
INSERT INTO `region`
VALUES (1828, 191, '博爱县', '410822', '', 0, 2);
INSERT INTO `region`
VALUES (1829, 191, '武陟县', '410823', '', 0, 2);
INSERT INTO `region`
VALUES (1830, 191, '温县', '410825', '', 0, 2);
INSERT INTO `region`
VALUES (1831, 191, '沁阳市', '410882', '', 0, 2);
INSERT INTO `region`
VALUES (1832, 191, '孟州市', '410883', '', 0, 2);
INSERT INTO `region`
VALUES (1833, 192, '华龙区', '410902', '', 0, 2);
INSERT INTO `region`
VALUES (1834, 192, '清丰县', '410922', '', 0, 2);
INSERT INTO `region`
VALUES (1835, 192, '南乐县', '410923', '', 0, 2);
INSERT INTO `region`
VALUES (1836, 192, '范县', '410926', '', 0, 2);
INSERT INTO `region`
VALUES (1837, 192, '台前县', '410927', '', 0, 2);
INSERT INTO `region`
VALUES (1838, 192, '濮阳县', '410928', '', 0, 2);
INSERT INTO `region`
VALUES (1839, 193, '魏都区', '411002', '', 0, 2);
INSERT INTO `region`
VALUES (1840, 193, '建安区', '411003', '', 0, 2);
INSERT INTO `region`
VALUES (1841, 193, '鄢陵县', '411024', '', 0, 2);
INSERT INTO `region`
VALUES (1842, 193, '襄城县', '411025', '', 0, 2);
INSERT INTO `region`
VALUES (1843, 193, '禹州市', '411081', '', 0, 2);
INSERT INTO `region`
VALUES (1844, 193, '长葛市', '411082', '', 0, 2);
INSERT INTO `region`
VALUES (1845, 194, '源汇区', '411102', '', 0, 2);
INSERT INTO `region`
VALUES (1846, 194, '郾城区', '411103', '', 0, 2);
INSERT INTO `region`
VALUES (1847, 194, '召陵区', '411104', '', 0, 2);
INSERT INTO `region`
VALUES (1848, 194, '舞阳县', '411121', '', 0, 2);
INSERT INTO `region`
VALUES (1849, 194, '临颍县', '411122', '', 0, 2);
INSERT INTO `region`
VALUES (1850, 195, '湖滨区', '411202', '', 0, 2);
INSERT INTO `region`
VALUES (1851, 195, '陕州区', '411203', '', 0, 2);
INSERT INTO `region`
VALUES (1852, 195, '渑池县', '411221', '', 0, 2);
INSERT INTO `region`
VALUES (1853, 195, '卢氏县', '411224', '', 0, 2);
INSERT INTO `region`
VALUES (1854, 195, '义马市', '411281', '', 0, 2);
INSERT INTO `region`
VALUES (1855, 195, '灵宝市', '411282', '', 0, 2);
INSERT INTO `region`
VALUES (1856, 196, '宛城区', '411302', '', 0, 2);
INSERT INTO `region`
VALUES (1857, 196, '卧龙区', '411303', '', 0, 2);
INSERT INTO `region`
VALUES (1858, 196, '南召县', '411321', '', 0, 2);
INSERT INTO `region`
VALUES (1859, 196, '方城县', '411322', '', 0, 2);
INSERT INTO `region`
VALUES (1860, 196, '西峡县', '411323', '', 0, 2);
INSERT INTO `region`
VALUES (1861, 196, '镇平县', '411324', '', 0, 2);
INSERT INTO `region`
VALUES (1862, 196, '内乡县', '411325', '', 0, 2);
INSERT INTO `region`
VALUES (1863, 196, '淅川县', '411326', '', 0, 2);
INSERT INTO `region`
VALUES (1864, 196, '社旗县', '411327', '', 0, 2);
INSERT INTO `region`
VALUES (1865, 196, '唐河县', '411328', '', 0, 2);
INSERT INTO `region`
VALUES (1866, 196, '新野县', '411329', '', 0, 2);
INSERT INTO `region`
VALUES (1867, 196, '桐柏县', '411330', '', 0, 2);
INSERT INTO `region`
VALUES (1868, 196, '邓州市', '411381', '', 0, 2);
INSERT INTO `region`
VALUES (1869, 197, '梁园区', '411402', '', 0, 2);
INSERT INTO `region`
VALUES (1870, 197, '睢阳区', '411403', '', 0, 2);
INSERT INTO `region`
VALUES (1871, 197, '民权县', '411421', '', 0, 2);
INSERT INTO `region`
VALUES (1872, 197, '睢县', '411422', '', 0, 2);
INSERT INTO `region`
VALUES (1873, 197, '宁陵县', '411423', '', 0, 2);
INSERT INTO `region`
VALUES (1874, 197, '柘城县', '411424', '', 0, 2);
INSERT INTO `region`
VALUES (1875, 197, '虞城县', '411425', '', 0, 2);
INSERT INTO `region`
VALUES (1876, 197, '夏邑县', '411426', '', 0, 2);
INSERT INTO `region`
VALUES (1877, 197, '永城市', '411481', '', 0, 2);
INSERT INTO `region`
VALUES (1878, 198, '浉河区', '411502', '', 0, 2);
INSERT INTO `region`
VALUES (1879, 198, '平桥区', '411503', '', 0, 2);
INSERT INTO `region`
VALUES (1880, 198, '罗山县', '411521', '', 0, 2);
INSERT INTO `region`
VALUES (1881, 198, '光山县', '411522', '', 0, 2);
INSERT INTO `region`
VALUES (1882, 198, '新县', '411523', '', 0, 2);
INSERT INTO `region`
VALUES (1883, 198, '商城县', '411524', '', 0, 2);
INSERT INTO `region`
VALUES (1884, 198, '固始县', '411525', '', 0, 2);
INSERT INTO `region`
VALUES (1885, 198, '潢川县', '411526', '', 0, 2);
INSERT INTO `region`
VALUES (1886, 198, '淮滨县', '411527', '', 0, 2);
INSERT INTO `region`
VALUES (1887, 198, '息县', '411528', '', 0, 2);
INSERT INTO `region`
VALUES (1888, 199, '川汇区', '411602', '', 0, 2);
INSERT INTO `region`
VALUES (1889, 199, '淮阳区', '411603', '', 0, 2);
INSERT INTO `region`
VALUES (1890, 199, '扶沟县', '411621', '', 0, 2);
INSERT INTO `region`
VALUES (1891, 199, '西华县', '411622', '', 0, 2);
INSERT INTO `region`
VALUES (1892, 199, '商水县', '411623', '', 0, 2);
INSERT INTO `region`
VALUES (1893, 199, '沈丘县', '411624', '', 0, 2);
INSERT INTO `region`
VALUES (1894, 199, '郸城县', '411625', '', 0, 2);
INSERT INTO `region`
VALUES (1895, 199, '太康县', '411627', '', 0, 2);
INSERT INTO `region`
VALUES (1896, 199, '鹿邑县', '411628', '', 0, 2);
INSERT INTO `region`
VALUES (1897, 199, '项城市', '411681', '', 0, 2);
INSERT INTO `region`
VALUES (1898, 200, '驿城区', '411702', '', 0, 2);
INSERT INTO `region`
VALUES (1899, 200, '西平县', '411721', '', 0, 2);
INSERT INTO `region`
VALUES (1900, 200, '上蔡县', '411722', '', 0, 2);
INSERT INTO `region`
VALUES (1901, 200, '平舆县', '411723', '', 0, 2);
INSERT INTO `region`
VALUES (1902, 200, '正阳县', '411724', '', 0, 2);
INSERT INTO `region`
VALUES (1903, 200, '确山县', '411725', '', 0, 2);
INSERT INTO `region`
VALUES (1904, 200, '泌阳县', '411726', '', 0, 2);
INSERT INTO `region`
VALUES (1905, 200, '汝南县', '411727', '', 0, 2);
INSERT INTO `region`
VALUES (1906, 200, '遂平县', '411728', '', 0, 2);
INSERT INTO `region`
VALUES (1907, 200, '新蔡县', '411729', '', 0, 2);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`
(
    `id`          bigint UNSIGNED                                                NOT NULL,
    `role_name`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL COMMENT '角色名称',
    `code`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL COMMENT '角色编号',
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
VALUES (7, 1, 7);
INSERT INTO `role_menu`
VALUES (8, 1, 8);

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
INSERT INTO `role_menu`
VALUES (34, 1, 34);
INSERT INTO `role_menu`
VALUES (35, 1, 35);
INSERT INTO `role_menu`
VALUES (36, 1, 36);
INSERT INTO `role_menu`
VALUES (37, 1, 37);
INSERT INTO `role_menu`
VALUES (38, 1, 38);

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

SET FOREIGN_KEY_CHECKS = 1;
