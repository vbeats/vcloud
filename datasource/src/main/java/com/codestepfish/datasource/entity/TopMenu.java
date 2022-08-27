package com.codestepfish.datasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.codestepfish.common.serializer.LongToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

/**
 * 顶部菜单
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "top_menu")
public class TopMenu {

    @JsonSerialize(using = LongToStringSerializer.class)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 租户编号
     */
    @TableField(value = "tenant_code")
    private String tenantCode;

    /**
     * 菜单名称
     */
    @TableField(value = "title")
    private String title;

    /**
     * 唯一标识key
     */
    @TableField(value = "`key`")
    private String key;

    /**
     * icon图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 顺序
     */
    @TableField(value = "`sort`")
    private Integer sort;
}