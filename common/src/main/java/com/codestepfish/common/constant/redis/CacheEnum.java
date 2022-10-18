package com.codestepfish.common.constant.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CacheEnum {
    public static final String ADMIN_CACHE = "cache:admin";     //admin用户cache
    public static final String USER_CACHE = "cache:user";       //普通用户cache
    public static final String ROLE_CACHE = "cache:role";   // 角色
    public static final String PERMISSION_CACHE = "cache:permission";   // 权限
    public static final String OPEN_CACHE = "cache:open";   // 开放平台参数cache
}
