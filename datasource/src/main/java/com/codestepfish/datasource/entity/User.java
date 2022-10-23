package com.codestepfish.datasource.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.codestepfish.common.serializer.LocalDateTimeDeserializer;
import com.codestepfish.common.serializer.LocalDateTimeSerializer;
import com.codestepfish.common.serializer.LongToStringSerializer;
import com.codestepfish.datasource.config.mybatis.Fastjson2TypeHandler;
import com.codestepfish.datasource.model.UserOpenInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import me.ahoo.cosid.annotation.CosId;

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
@TableName(value = "`user`", autoResultMap = true)
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 6212633406797709469L;

    @CosId(value = "user")
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long id;

    /**
     * 所属租户id
     */
    @TableField(value = "tenant_id")
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long tenantId;

    /**
     * 开放平台用户信息
     */
    @TableField(value = "open_info", typeHandler = Fastjson2TypeHandler.class)
    private UserOpenInfo openInfo;

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
}