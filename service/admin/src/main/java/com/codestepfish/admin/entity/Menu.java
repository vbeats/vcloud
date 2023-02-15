package com.codestepfish.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.codestepfish.admin.type.MenuTypeEnum;
import com.codestepfish.core.serializer.LongToStringSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
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

    @TableId(value = "id", type = IdType.AUTO)
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long id;

    /**
     * 上级  默认0
     */
    @TableField(value = "pid")
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long pid = 0L;

    /**
     * 所有上级  默认0 ,分割
     */
    @TableField(value = "pids")
    private String pids = "0";

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
    @TableField(value = "`permission`")
    private String permission;

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

    // ------------------------------------------------------------------

    @JsonProperty(value = "hasChildren")
    @TableField(exist = false, value = "has_children")
    private Boolean hasChildren;

    @TableField(exist = false, value = "children")
    private List<Menu> children;
}