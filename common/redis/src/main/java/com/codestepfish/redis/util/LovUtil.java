package com.codestepfish.redis.util;

import cn.hutool.extra.spring.SpringUtil;
import com.codestepfish.redis.constant.RedisConstants;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RLocalCachedMap;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * 值集util    namespace:   lov:tenantId:category
 * <p>
 * 默认值集     namespace:   lov:default
 * <p>
 * 获取不存在的值集  应当确保有默认配置   否则会抛出异常
 *
 * <p>
 * 分组:
 * <p>
 * *  - key: value
 * <p>
 * * - key: value
 */
public class LovUtil {
    private static final RedissonClient redissonClient = SpringUtil.getBean(RedissonClient.class);

    private static final LocalCachedMapOptions options = LocalCachedMapOptions.defaults()
            // 用于淘汰清除本地缓存内的元素
            // 共有以下几种选择:
            // LFU - 统计元素的使用频率，淘汰用得最少（最不常用）的。
            // LRU - 按元素使用时间排序比较，淘汰最早（最久远）的。
            // SOFT - 元素用Java的WeakReference来保存，缓存元素通过GC过程清除。
            // WEAK - 元素用Java的SoftReference来保存, 缓存元素通过GC过程清除。
            // NONE - 永不淘汰清除缓存元素。
            .evictionPolicy(LocalCachedMapOptions.EvictionPolicy.LRU)
            // 如果缓存容量值为0表示不限制本地缓存容量大小
            .cacheSize(1000)
            // 以下选项适用于断线原因造成了未收到本地缓存更新消息的情况。
            // 断线重连的策略有以下几种：
            // CLEAR - 如果断线一段时间以后则在重新建立连接以后清空本地缓存
            // LOAD - 在服务端保存一份10分钟的作废日志
            //        如果10分钟内重新建立连接，则按照作废日志内的记录清空本地缓存的元素
            //        如果断线时间超过了这个时间，则将清空本地缓存中所有的内容
            // NONE - 默认值。断线重连时不做处理。
            .reconnectionStrategy(LocalCachedMapOptions.ReconnectionStrategy.NONE)
            // 以下选项适用于不同本地缓存之间相互保持同步的情况
            // 缓存同步策略有以下几种：
            // INVALIDATE - 默认值。当本地缓存映射的某条元素发生变动时，同时驱逐所有相同本地缓存映射内的该元素
            // UPDATE - 当本地缓存映射的某条元素发生变动时，同时更新所有相同本地缓存映射内的该元素
            // NONE - 不做任何同步处理
            .syncStrategy(LocalCachedMapOptions.SyncStrategy.INVALIDATE)
            // 每个Map本地缓存里元素的有效时间
            .timeToLive(30, TimeUnit.MINUTES)
            // 每个Map本地缓存里元素的最长闲置时间
            .maxIdle(10, TimeUnit.MINUTES);

    /**
     * set or update 值集
     *
     * @param tenantId 租户id
     * @param category 分组
     * @param key      key
     * @param value    value
     */
    public static void set(Long tenantId, String category, String key, String value) {
        RMap<String, String> rmap = getRmap(tenantId, category);
        rmap.put(key, value);
    }

    /**
     * set or update 默认值集
     *
     * @param key
     * @param value
     */
    public static void setDefault(String key, String value) {
        RMap<String, String> defaultRmap = getDefaultRmap();
        defaultRmap.put(key, value);
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
        RMap<String, String> rmap = getRmap(tenantId, category);
        return StringUtils.hasText(rmap.get(key)) ? rmap.get(key) : getDefaultValue(key);
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
        RMap<String, String> rmap = getRmap(tenantId, category);
        rmap.remove(key);
    }

    /**
     * 删除某个分组
     *
     * @param tenantId 租户id
     * @param category 分组
     */
    public static void deleteCategory(Long tenantId, String category) {
        RMap<String, String> rmap = getRmap(tenantId, category);
        rmap.delete();
    }

    private static RMap<String, String> getRmap(Long tenantId, String category) {
        RLocalCachedMap<String, String> rmap = redissonClient.getLocalCachedMap(String.format(RedisConstants.LOV_BUCKET, tenantId, category), options);
        return rmap;
    }

    // 默认值集 bucket
    private static RMap<String, String> getDefaultRmap() {
        RLocalCachedMap<String, String> rmap = redissonClient.getLocalCachedMap(RedisConstants.LOV_DEFAULT_BUCKET, options);
        return rmap;
    }

    /**
     * 获取默认值集配置    不存在返回null
     *
     * @param key
     * @return
     */
    private static String getDefaultValue(String key) {
        return getDefaultRmap().get(key);
    }

    /**
     * 删除默认值集配置
     *
     * @param key
     */
    public static void deleteDefault(String key) {
        getDefaultRmap().remove(key);
    }
}
