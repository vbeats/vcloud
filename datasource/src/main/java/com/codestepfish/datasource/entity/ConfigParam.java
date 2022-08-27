package com.codestepfish.datasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统参数
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "config_param")
public class ConfigParam implements Serializable {
    private static final long serialVersionUID = 6218921664483018256L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 配置key
     */
    @TableField(value = "config_key")
    private String configKey;

    /**
     * 配置value
     */
    @TableField(value = "config_value")
    private String configValue;

    /**
     * 配置说明
     */
    @TableField(value = "remark")
    private String remark;

    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    @TableField(value = "delete_time")
    private LocalDateTime deleteTime;
}