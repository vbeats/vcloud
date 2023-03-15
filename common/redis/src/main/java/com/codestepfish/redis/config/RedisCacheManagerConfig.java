package com.codestepfish.redis.config;

import com.codestepfish.core.config.app.AppConfig;
import com.codestepfish.core.config.app.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RedisCacheManagerConfig {
    // 本地缓存策略     😂 部分功能 pro 版本才支持
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
            .cacheSize(1024)
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
    private final AppConfig appConfig;

    // 生成yaml配置
    public static void main(String[] args) throws IOException {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379")
                .setDatabase(0).setKeepAlive(true)
                .setConnectionMinimumIdleSize(10)
                .setConnectionPoolSize(32)
                .setTimeout(1000);
        config.setCodec(new JsonJacksonCodec());
        log.info("config: {}", config.toYAML());
    }

    @Bean
    @Primary
    CacheManager cacheManager(RedissonClient redissonClient) {
        // ****************cache***************************
        Map<String, CacheConfig> config = new HashMap<>();
        // 过期时间   最长空闲时间
        List<Cache> cache = appConfig.getCaches();
        if (!CollectionUtils.isEmpty(cache)) {
            cache.forEach(it -> {
                CacheConfig cacheConfig = new CacheConfig(it.getTtl(), it.getMaxIdleTime());
                cacheConfig.setMaxSize(it.getMaxSize());

                config.put("cache:" + it.getCacheName(), cacheConfig);
            });
        }

        RedissonSpringCacheManager cacheManager = new RedissonSpringCacheManager(redissonClient, config);
        return cacheManager;
    }
}
