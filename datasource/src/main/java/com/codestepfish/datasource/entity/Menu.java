package com.codestepfish.datasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.codestepfish.common.serializer.LocalDateTimeDeserializer;
import com.codestepfish.common.serializer.LocalDateTimeSerializer;
import com.codestepfish.common.serializer.LongToStringSerializer;
import com.codestepfish.datasource.type.MenuTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜单
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "menu")
public class Menu implements Serializable {
    @Serial
    private static final long serialVersionUID = -6423035146241385171L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long id;

    /**
     * 上级  默认0
     */
    @TableField(value = "pid")
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long pid;

    /**
     * 菜单/按钮名称
     */
    @TableField(value = "title")
    private String title;

    /**
     * 前端路由
     */
    @TableField(value = "`path`")
    private String path;

    /**
     * icon图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 唯一key标识
     */
    @TableField(value = "`key`")
    private String key;

    /**
     * 权限字段
     */
    @TableField(value = "`action`")
    private String action;

    /**
     * 类型 0 菜单 1 按钮
     */
    @TableField(value = "`type`")
    private MenuTypeEnum type;

    /**
     * 顺序
     */
    @TableField(value = "`sort`")
    private Integer sort;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

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

    // ------------------------------------------------------------------

    @JsonProperty(value = "hasChildren")
    @TableField(exist = false, value = "has_children")
    private Boolean hasChildren;

    @TableField(exist = false, value = "children")
    private List<Menu> children;
}