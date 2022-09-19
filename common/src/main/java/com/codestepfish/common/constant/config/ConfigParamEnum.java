package com.codestepfish.common.constant.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConfigParamEnum {
    SUPER_TENANT("super_tenant", "运营平台租户编号"),
    SUPER_ROLE("super_role", "超级管理员角色编号");

    private String key; // 系统配置参数 key
    private String desc;
}
