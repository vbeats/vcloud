SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`           bigint UNSIGNED                                                NOT NULL,
    `tenant_id`    bigint                                                         NOT NULL COMMENT '租户id',
    `wx_union_id`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL DEFAULT '' COMMENT '微信开放平台union id',
    `wx_ma_openid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL DEFAULT '' COMMENT '微信小程序openid',
    `wx_mp_openid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL DEFAULT '' COMMENT '微信公众平台openid',
    `phone`        varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL DEFAULT '' COMMENT '手机号',
    `status`       tinyint(1)                                                     NOT NULL DEFAULT 1 COMMENT '用户状态  0禁用  1正常',
    `del_flag`     tinyint(1)                                                     NOT NULL DEFAULT 0 COMMENT '是否删除  0否   1是',
    `remark`       varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
    `create_time`  datetime(3)                                                    NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `update_time`  datetime(3)                                                    NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_tenant_id` (`tenant_id` ASC) USING BTREE,
    INDEX `idx_union_id` (`wx_union_id` ASC) USING BTREE,
    INDEX `idx_wx_ma_openid` (`wx_ma_openid` ASC) USING BTREE,
    INDEX `idx_wx_mp_openid` (`wx_mp_openid` ASC) USING BTREE,
    INDEX `idx_phone` (`phone` ASC) USING BTREE,
    INDEX `idx_status` (`status` ASC) USING BTREE,
    INDEX `idx_del_flag` (`del_flag` ASC) USING BTREE,
    INDEX `idx_create_time` (`create_time` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表'
  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;