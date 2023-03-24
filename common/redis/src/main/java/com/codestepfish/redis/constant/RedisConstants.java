package com.codestepfish.redis.constant;

// redis cache
public class RedisConstants {
    public static final String WX_APPID_BUCKET = "wx:appid:%s";  // 微信appid bucket

    public static final String LOV_BUCKET = "lov:%s:%s:%s";  // lov值集 bucket   lov:tenantId:category:key
    public static final String LOV_DEFAULT_BUCKET = "lov:default:%s";  // 默认值集 lov:default:key
}