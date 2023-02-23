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

import java.io.Serial;
import java.io.Serializable;

/**
 * 值集默认配置
 */
@Getter
@Setter
@NoArgsConstructor
@TableName(value = "`lov_default`")
public class LovDefault implements Serializable {
    @Serial
    private static final long serialVersionUID = -92482729138642254L;

    @TableId(type = IdType.AUTO)
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long id;

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
