package com.codestepfish.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.codestepfish.core.serializer.LocalDateTimeDeserializer;
import com.codestepfish.core.serializer.LocalDateTimeSerializer;
import com.codestepfish.core.serializer.LongToStringSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 商户
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@TableName(value = "merchant")
public class Merchant implements Serializable {

    @Serial
    private static final long serialVersionUID = 2779190417236513615L;

    @TableId(value = "id", type = IdType.AUTO)
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long id;

    /**
     * 上级  默认0
     */
    @TableField(value = "pid")
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long pid = 0L;

    /**
     * 所有上级  默认0 ,分隔
     */
    @TableField(value = "pids")
    private String pids = "0";

    /**
     * 商户编号
     */
    @TableField(value = "code")
    private String code;

    /**
     * 商户名称
     */
    @TableField(value = "merchant_name")
    private String merchantName;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    @TableField(value = "create_time")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    @TableField(value = "update_time")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    // ----------------------------
    @JsonProperty(value = "hasChildren")
    @TableField(value = "has_children", exist = false)
    private Boolean hasChildren = false;

    @TableField(value = "children", exist = false)
    private List<Merchant> children;
}