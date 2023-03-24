package com.codestepfish.core.util;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 线程绑定
 */
public class AppContextHolder<T> {

    private static final TransmittableThreadLocal<Map<String, Object>> TTL = new TransmittableThreadLocal<>();

    public static <T> void set(String key, T value) {
        Map<String, Object> map = TTL.get();
        if (ObjectUtils.isEmpty(map)) {
            map = new ConcurrentHashMap<>(2);
            TTL.set(map);
        }

        map.put(key, value);

    }

    public static <T> T get(String key) {
        Map<String, Object> map = TTL.get();
        if (!ObjectUtils.isEmpty(map)) {
            return (T) map.getOrDefault(key, null);
        }

        return null;
    }

    public static void clear() {
        TTL.remove();
    }
}
