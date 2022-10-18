package com.codestepfish.datasource.config.redis;

import com.codestepfish.common.config.app.AppConfig;
import com.codestepfish.common.config.app.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableCaching
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RedisCacheManagerConfig {
    private final AppConfig appConfig;

    /*public static void main(String[] args) throws IOException {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379")
                .setDatabase(0).setKeepAlive(true)
                .setConnectionMinimumIdleSize(10)
                .setConnectionPoolSize(32)
                .setTimeout(1000);
        config.setCodec(new JsonJacksonCodec());
        log.info("config: {}", config.toYAML());
    }*/

    @Bean
    @Primary
    CacheManager cacheManager(RedissonClient redissonClient) {
        // ****************cache***************************
        Map<String, CacheConfig> config = new HashMap<>();
        // 过期时间   最长空闲时间
        List<Cache> cache = appConfig.getCaches();
        if (!CollectionUtils.isEmpty(cache)) {
            cache.forEach(it -> config.put("cache:" + it.getCacheName(), new CacheConfig(it.getTtl(), it.getMaxIdleTime())));
        }

        RedissonSpringCacheManager cacheManager = new RedissonSpringCacheManager(redissonClient, config);
        return cacheManager;
    }
}
