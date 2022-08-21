package com.codestepfish.core.constant.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CacheEnum {
    public static final String ADMIN_CACHE = "cache:admin"; //admin用户cache
    public static final String USER_CACHE = "cache:user"; //普通用户cache

}
