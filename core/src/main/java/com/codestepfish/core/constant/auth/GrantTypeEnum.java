package com.codestepfish.core.constant.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum GrantTypeEnum {
    WX_APP("wx_app", "微信小程序"),
    WX_MP("wx_mp", "微信公众号"),
    PASSWORD("password", "密码登录"),
    SMS("sms", "短信登录"),
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
