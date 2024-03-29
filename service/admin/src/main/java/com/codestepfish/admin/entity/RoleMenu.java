package com.codestepfish.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.codestepfish.core.serializer.LongToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 角色-菜单
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "role_menu")
public class RoleMenu implements Serializable {
    @Serial
    private static final long serialVersionUID = 88564858554537156L;

    @TableId(value = "id", type = IdType.AUTO)
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long id;

    /**
     * 角色id
     */
    @TableField(value = "role_id")
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long roleId;

    /**
     * 菜单id
     */
    @TableField(value = "menu_id")
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long menuId;
}