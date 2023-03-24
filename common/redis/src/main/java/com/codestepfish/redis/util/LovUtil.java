package com.codestepfish.redis.util;

import cn.hutool.extra.spring.SpringUtil;
import com.codestepfish.redis.constant.RedisConstants;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

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
    public static void set(Long tenantId, String category, String key, String value) {
        RBucket<String> rBucket = getLovBucket(tenantId, category, key);
        rBucket.set(value);
    }

    /**
     * set or update 默认值集
     *
     * @param key
     * @param value
     */
    public static void setDefault(String key, String value) {
        RBucket<String> rBucket = getDefaultBucket(key);
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
    public static String get(Long tenantId, String category, String key) {
        RBucket<String> rBucket = getLovBucket(tenantId, category, key);
        String value = rBucket.get();
        return StringUtils.hasText(value) ? value : getDefaultValue(key);
    }


    /**
     * 获取int value   , 不存在默认返回默认值集配置
     *
     * @param tenantId 租户id
     * @param category 分组
     * @param key      key
     * @return
     */
    public static Integer getInt(Long tenantId, String category, String key) {
        String value = get(tenantId, category, key);
        return StringUtils.hasText(value) ? Integer.valueOf(value) : Integer.valueOf(getDefaultValue(key));
    }

    /**
     * 获取 Long value   , 不存在默认返回默认值集配置
     *
     * @param tenantId 租户id
     * @param category 分组
     * @param key      key
     * @return
     */
    public static Long getLong(Long tenantId, String category, String key) {
        String value = get(tenantId, category, key);
        return StringUtils.hasText(value) ? Long.valueOf(value) : Long.valueOf(getDefaultValue(key));
    }

    /**
     * 获取 BigDecimal value   , 不存在默认返回默认值集配置
     *
     * @param tenantId 租户id
     * @param category 分组
     * @param key      key
     * @return
     */
    public static BigDecimal getBigDecimal(Long tenantId, String category, String key) {
        String value = get(tenantId, category, key);
        return StringUtils.hasText(value) ? new BigDecimal(value) : new BigDecimal(getDefaultValue(key));
    }

    /**
     * 删除某个分组下 某个key
     *
     * @param tenantId 租户id
     * @param category 分组
     * @param key      key
     */
    public static void delete(Long tenantId, String category, String key) {
        RBucket<String> rBucket = getLovBucket(tenantId, category, key);
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
     * 获取默认值集配置    不存在返回空字符串
     *
     * @param key
     * @return
     */
    private static String getDefaultValue(String key) {
        RBucket<String> rBucket = getDefaultBucket(key);
        String value = rBucket.get();
        return StringUtils.hasText(value) ? value : "";
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
