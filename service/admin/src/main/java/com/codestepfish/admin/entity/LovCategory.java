package com.codestepfish.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@TableName(value = "lov_category")
public class LovCategory {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 租户id
     */
    @TableField(value = "tenant_id")
    private Long tenantId;

    /**
     * 分组名称
     */
    @TableField(value = "category")
    private String category;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 租户名称
     */
    @TableField(value = "tenantName", exist = false)
    private String tenantName;
}
