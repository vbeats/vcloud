package com.codestepfish.admin.dto.region;

import com.codestepfish.common.serializer.LongToStringSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionVo {

    @JsonSerialize(using = LongToStringSerializer.class)
    private Long id;

    @JsonSerialize(using = LongToStringSerializer.class)
    private Long pid = 0L;

    private String name;

    private String code;

    private String zipCode;

    private Integer sort;

    /**
     * 类型 0:省/自治区/直辖市/特区 1:市/自治州/盟/直辖市下属辖区 2:区县 3:乡镇/街道 4:村/小区
     */
    private Integer type;

    @JsonProperty(value = "hasChildren")
    private Boolean hasChildren;
}
