package com.codestepfish.datasource.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 管理员
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "`admin`")
public class Admin {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 租户编号
     */
    @TableField(value = "tenant_code")
    private String tenantCode;

    /**
     * 账号
     */
    @TableField(value = "account")
    private String account;

    /**
     * 用户名 (仅作展示用)
     */
    @TableField(value = "username", updateStrategy = FieldStrategy.IGNORED)
    private String username;

    /**
     * 密码
     */
    @TableField(value = "`password`")
    private String password;

    /**
     * 手机号
     */
    @TableField(value = "phone", updateStrategy = FieldStrategy.IGNORED)
    private String phone;

    /**
     * 状态 0 禁用  1 正常
     */
    @TableField(value = "`status`")
    private Boolean status;

    /**
     * 角色id
     */
    @TableField(value = "role_id", updateStrategy = FieldStrategy.IGNORED)
    private Long roleId;

    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    @TableField(value = "delete_time")
    private LocalDateTime deleteTime;
}