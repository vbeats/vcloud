package com.codestepfish.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.codestepfish.core.serializer.LocalDateTimeDeserializer;
import com.codestepfish.core.serializer.LocalDateTimeSerializer;
import com.codestepfish.core.serializer.LongToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.util.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 管理员
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@TableName(value = "`admin`")
public class Admin implements Serializable {
    @Serial
    private static final long serialVersionUID = 3642304959523068028L;

    @TableId(value = "id", type = IdType.AUTO)
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long id;

    /**
     * 租户id
     */
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long tenantId;

    /**
     * 账号
     */
    @TableField(value = "account")
    private String account;

    /**
     * 昵称
     */
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 密码
     */
    @TableField(value = "`password`")
    private String password;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 状态 0 禁用  1 正常
     */
    @TableField(value = "`status`")
    private Boolean status = true;

    @TableField(value = "del_flag")
    private Boolean delFlag = false;

    @TableField(value = "create_time")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    @TableField(value = "update_time")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    // -------------------------------------
    @TableField(value = "tenant_code", exist = false)
    private String tenantCode;

    @TableField(value = "tenant_name", exist = false)
    private String tenantName;

    public boolean isSuperAdmin() {
        return !ObjectUtils.isEmpty(id) && id.equals(1L);
    }
}