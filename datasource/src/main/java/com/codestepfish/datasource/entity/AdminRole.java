package com.codestepfish.datasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 管理员-角色
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "admin_role")
public class AdminRole {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * admin用户id
     */
    @TableField(value = "admin_id")
    private Long adminId;

    /**
     * 角色id
     */
    @TableField(value = "role_id")
    private Long roleId;
}