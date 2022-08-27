package com.codestepfish.common.constant.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConfigParamEnum {
    SUPER_TENANT("super_tenant", "运营平台租户编号"),
    TENANT_MENU("tenant_menu", "租户默认分配的一二级菜单key");

    private String key; // 系统配置参数 key
    private String desc;
}
