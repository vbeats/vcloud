package com.codestepfish.datasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 行政区划
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "region")
public class Region {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 上级id
     */
    @TableField(value = "pid")
    private Long pid;

    /**
     * 2位区划编号/汇总码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 邮编
     */
    @TableField(value = "zip_code")
    private String zipCode;

    /**
     * 顺序
     */
    @TableField(value = "`sort`")
    private Integer sort;

    /**
     * 类型 0:省/自治区/直辖市/特区 1:市/自治州/盟/直辖市下属辖区 2:区县 3:乡镇/街道 4:村/小区
     */
    @TableField(value = "`type`")
    private Integer type;
}