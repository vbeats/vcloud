package com.codestepfish.redis.util;

import cn.hutool.extra.spring.SpringUtil;
import com.codestepfish.redis.constant.RedisConstants;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.util.ObjectUtils;

/**
 * 值集util    namespace:   lov:tenantId:category:key ==> value
 * <p>
 * 默认值集     namespace:   lov:default:key  ==> value
 * <p>
 * 获取不存在的值集  应当确保有默认配置
 */
public class LovUtil {

    private static final RedissonClient redissonClient = SpringUtil.getBean(RedissonClient.class);

    /**
     * set or update 值集
     *
     * @param tenantId 租户id
     * @param category 分组
     * @param key      key
     * @param value    value
     */
    public static <T> void set(Long tenantId, String category, String key, T value) {
        RBucket<T> rBucket = getLovBucket(tenantId, category, key);
        rBucket.set(value);
    }

    /**
     * set or update 默认值集
     *
     * @param key
     * @param value
     */
    public static <T> void setDefault(String key, T value) {
        RBucket<T> rBucket = getDefaultBucket(key);
        rBucket.set(value);
    }

    /**
     * 获取String value   , 不存在默认返回默认值集配置
     *
     * @param tenantId 租户id
     * @param category 分组
     * @param key      key
     * @return
     */
    public static <T> T get(Long tenantId, String category, String key) {
        RBucket<T> rBucket = getLovBucket(tenantId, category, key);
        T value = rBucket.get();
        return ObjectUtils.isEmpty(value) ? getDefaultValue(key) : value;
    }

    /**
     * 删除某个分组下 某个key
     *
     * @param tenantId 租户id
     * @param category 分组
     * @param key      key
     */
    public static <T> void delete(Long tenantId, String category, String key) {
        RBucket<T> rBucket = getLovBucket(tenantId, category, key);
        rBucket.delete();
    }

    private static <T> RBucket<T> getLovBucket(Long tenantId, String category, String key) {
        return redissonClient.getBucket(String.format(RedisConstants.LOV_BUCKET, tenantId, category, key));
    }

    // 默认值集 bucket
    private static <T> RBucket<T> getDefaultBucket(String key) {
        return redissonClient.getBucket(String.format(RedisConstants.LOV_DEFAULT_BUCKET, key));
    }

    /**
     * 获取默认值集配置    不存在返回null
     *
     * @param key
     * @return
     */
    private static <T> T getDefaultValue(String key) {
        RBucket<T> rBucket = getDefaultBucket(key);
        T value = rBucket.get();
        return ObjectUtils.isEmpty(value) ? null : value;
    }

    /**
     * 删除默认值集配置
     *
     * @param key
     */
    public static void deleteDefault(String key) {
        getDefaultBucket(key).delete();
    }
}
