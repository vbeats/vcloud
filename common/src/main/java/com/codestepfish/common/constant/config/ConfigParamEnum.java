package com.codestepfish.common.constant.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConfigParamEnum {
    SUPER_TENANT("super_tenant", "运营平台租户id"),
    SUPER_ROLE("super_role", "超级管理员角色编号"),
    DEFAULT_PASSWORD("default_password", "管理员默认密码"),
    ;

    private String key; // 系统配置参数 key
    private String desc;
}
