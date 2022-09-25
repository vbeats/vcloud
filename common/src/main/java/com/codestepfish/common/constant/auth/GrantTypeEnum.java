package com.codestepfish.common.constant.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum GrantTypeEnum {
    WX_MINIAPP("wx_miniapp", "微信小程序"),
    WX_MP("wx_mp", "微信公众平台"),
    WX_CP("wx_cp", "企业微信"),
    WX_OPEN("wx_open", "微信开放平台"),
    ALI_MINIAPP("ali_miniapp", "支付宝小程序"),
    PASSWORD("password", "密码登陆"),
    SMS("sms", "短信登陆"),
    REFRESH_TOKEN("refresh_token", "刷新token"),
    ;

    private static final Map<String, GrantTypeEnum> lookup = new HashMap<>();

    static {
        EnumSet.allOf(GrantTypeEnum.class).forEach(e -> lookup.put(e.getLabel(), e));
    }

    private String label;
    private String desc;

    public static GrantTypeEnum find(String label) {
        return lookup.get(label);
    }
}
