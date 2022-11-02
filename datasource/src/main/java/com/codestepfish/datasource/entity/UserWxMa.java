package com.codestepfish.datasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.codestepfish.common.serializer.LocalDateTimeDeserializer;
import com.codestepfish.common.serializer.LocalDateTimeSerializer;
import com.codestepfish.common.serializer.LongToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "`user_wx_ma`")
public class UserWxMa implements Serializable {
    @Serial
    private static final long serialVersionUID = 1004520934311099374L;

    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long id;

    @TableField(value = "user_id")
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long userId;

    /**
     * 开放平台id
     */
    @TableField(value = "open_config_id")
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long openConfigId;

    @TableField(value = "openid")
    private String openid;

    @TableField(value = "unionid")
    private String unionid;

    @TableField(value = "phone")
    private String phone;

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
