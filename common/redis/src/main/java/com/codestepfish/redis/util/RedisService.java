package com.codestepfish.redis.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * spring redis 工具类
 **/
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RedisService {

    private final RedissonClient redissonClient;

    public <T> void set(String bucket, T value) {
        RBucket<T> rBucket = redissonClient.getBucket(bucket);
        rBucket.set(value);
    }

    public <T> void set(String bucket, T value, Long time, TimeUnit timeUnit) {
        RBucket<T> rBucket = redissonClient.getBucket(bucket);
        rBucket.set(value, time, timeUnit);
    }

    public <T> T get(String bucket) {
        RBucket<T> rBucket = redissonClient.getBucket(bucket);
        return rBucket.get();
    }

    public <T> T getAndDelete(String bucket) {
        RBucket<T> rBucket = redissonClient.getBucket(bucket);
        return rBucket.getAndDelete();
    }
}
