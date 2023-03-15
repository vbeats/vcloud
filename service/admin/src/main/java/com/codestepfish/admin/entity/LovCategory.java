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

@Getter
@Setter
@NoArgsConstructor
@TableName(value = "lov_category")
public class LovCategory {

    @TableId(type = IdType.AUTO)
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long id;

    /**
     * 商户id
     */
    @TableField(value = "merchant_id")
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long merchantId;

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
     * 商户名称
     */
    @TableField(value = "merchantName", exist = false)
    private String merchantName;
}
