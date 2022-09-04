package com.codestepfish.admin.dto.tenant;

import com.codestepfish.common.serializer.LocalDateTimeDeserializer;
import com.codestepfish.common.serializer.LocalDateTimeSerializer;
import com.codestepfish.common.serializer.LongToStringSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TenantOut {
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long id;

    @JsonSerialize(using = LongToStringSerializer.class)
    private Long pid;

    /**
     * 租户编号
     */
    private String code;

    /**
     * 租户名称
     */
    private String tenantName;

    /**
     * 状态 0 禁用  1正常
     */
    private Boolean status;

    /**
     * 备注
     */
    private String remark;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime deleteTime;

    @JsonProperty(value = "hasChildren")
    private Boolean hasChildren = false;

    private List<TenantOut> children;
}
