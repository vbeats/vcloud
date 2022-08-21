package com.codestepfish.datasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 顶部菜单-菜单
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "top_menu_tree")
public class TopMenuTree {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * top_menu id
     */
    @TableField(value = "top_id")
    private Long topId;

    /**
     * 菜单id
     */
    @TableField(value = "menu_id")
    private Long menuId;
}