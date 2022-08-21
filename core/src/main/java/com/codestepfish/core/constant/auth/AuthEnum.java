package com.codestepfish.core.constant.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthEnum {
    access_token("access_token", "access token字段名"),
    refresh_token("refresh_token", "refresh token字段名"),

    REQUEST_HEADER_TOKEN("Authorization", "header请求头 token字段名"),

    ;

    private String key;
    private String desc;
}
