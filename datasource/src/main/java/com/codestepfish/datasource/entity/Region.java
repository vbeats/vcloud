package com.codestepfish.datasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 行政区划
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "region")
public class Region {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 省 自治区 直辖市 特区 行政区划编号
     */
    @TableField(value = "province_code")
    private String provinceCode;

    /**
     * 市 自治州 盟 直辖市下属辖区 汇总码
     */
    @TableField(value = "city_code")
    private String cityCode;

    /**
     * 区县 编号
     */
    @TableField(value = "district_code")
    private String districtCode;

    /**
     * 省级名称
     */
    @TableField(value = "province_name")
    private String provinceName;

    /**
     * 市级名称
     */
    @TableField(value = "city_name")
    private String cityName;

    /**
     * 区县名称
     */
    @TableField(value = "district_name")
    private String districtName;

    /**
     * 邮编
     */
    @TableField(value = "zip_code")
    private String zipCode;

    /**
     * 顺序
     */
    @TableField(value = "`sort`")
    private Integer sort;
}