package com.codestepfish.datasource.type;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum OpenTypeEnum {  // 第三方开放平台类型
    WX_MINIAPP(0, "微信小程序"),
    WX_MP(1, "微信公众平台"),
    WX_CP(2, "企业微信"),
    WX_OPEN(3, "微信开放平台"),
    ALI_MINIAPP(4, "支付宝小程序");

    private static final Map<Integer, OpenTypeEnum> lookup = new HashMap<>();

    static {
        EnumSet.allOf(OpenTypeEnum.class).forEach(e -> lookup.put(e.getValue(), e));
    }

    @EnumValue
    private Integer value;
    private String type;

    public static OpenTypeEnum find(Integer value) {
        return lookup.get(value);
    }
}