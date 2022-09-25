package com.codestepfish.datasource.entity;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.codestepfish.datasource.type.OpenTypeEnum;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 第三方开放平台
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "`open_config`", autoResultMap = true)
public class OpenConfig implements Serializable {
    private static final long serialVersionUID = -1985083744027019776L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 所属租户id 默认0 未分配
     */
    @TableField(value = "tenant_id")
    private Long tenantId;

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
    @TableField(value = "config", typeHandler = FastjsonTypeHandler.class)
    private JSONObject config;

    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    @TableField(value = "delete_time")
    private LocalDateTime deleteTime;
}