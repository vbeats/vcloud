package com.codestepfish.datasource.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.codestepfish.common.serializer.LocalDateTimeDeserializer;
import com.codestepfish.common.serializer.LocalDateTimeSerializer;
import com.codestepfish.common.serializer.LongToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
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
public class Admin implements Serializable {
    @Serial
    private static final long serialVersionUID = 3642304959523068028L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = LongToStringSerializer.class)
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
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long roleId;

    @TableField(value = "create_time")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    @TableField(value = "update_time")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    @TableField(value = "delete_time")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime deleteTime;


    // -------------------------------------
    @TableField(value = "tenant_id", exist = false)
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long tenantId;

    @TableField(value = "tenant_name", exist = false)
    private String tenantName;

    @TableField(value = "role_name", exist = false)
    private String roleName;


}