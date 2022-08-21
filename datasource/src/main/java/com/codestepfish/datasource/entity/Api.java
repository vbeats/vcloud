package com.codestepfish.datasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * api列表
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "api")
public class Api {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 接口名称
     */
    @TableField(value = "api_name")
    private String apiName;

    /**
     * api路径
     */
    @TableField(value = "`path`")
    private String path;

    /**
     * 权限字段
     */
    @TableField(value = "`action`")
    private String action;

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