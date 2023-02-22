package com.codestepfish.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.codestepfish.core.serializer.LongToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 值集
 */
@Getter
@Setter
@NoArgsConstructor
@TableName(value = "`lov`")
public class Lov implements Serializable {
    private static final long serialVersionUID = 5964496798733547601L;

    @TableId(type = IdType.AUTO)
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long id;

    /**
     * 值集分组id
     */
    @TableField(value = "lov_category_id")
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long lovCategoryId;

    /**
     * 键
     */
    @TableField(value = "`key`")
    private String key;

    /**
     * 值
     */
    @TableField(value = "`value`")
    private String value;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

}
