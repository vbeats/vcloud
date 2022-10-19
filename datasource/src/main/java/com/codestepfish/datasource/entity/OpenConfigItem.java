package com.codestepfish.datasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.codestepfish.common.serializer.LocalDateTimeDeserializer;
import com.codestepfish.common.serializer.LocalDateTimeSerializer;
import com.codestepfish.common.serializer.LongToStringSerializer;
import com.codestepfish.datasource.config.mybatis.Fastjson2TypeHandler;
import com.codestepfish.datasource.model.OpenConfigData;
import com.codestepfish.datasource.type.OpenTypeEnum;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 开放平台 关联的 小程序/公众号
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "`open_config_item`", autoResultMap = true)
public class OpenConfigItem implements Serializable {
    @Serial
    private static final long serialVersionUID = 3841361090891478421L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long id;

    /**
     * 开放平台id
     */
    @TableField(value = "open_config_id")
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long openConfigId;

    /**
     * 名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 开放平台类型
     */
    @TableField(value = "type")
    private OpenTypeEnum type;

    /**
     * 参数配置
     */
    @TableField(value = "config", typeHandler = Fastjson2TypeHandler.class)
    private OpenConfigData config;

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

    // -----------------------------
}