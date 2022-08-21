package com.codestepfish.datasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.codestepfish.datasource.type.MenuTypeEnum;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 菜单
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "menu")
public class Menu {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 上级  默认0
     */
    @TableField(value = "`p_id`")
    private Long pId;

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
    private LocalDateTime createTime;

    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    @TableField(value = "delete_time")
    private LocalDateTime deleteTime;
}