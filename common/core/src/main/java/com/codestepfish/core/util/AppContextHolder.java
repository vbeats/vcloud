package com.codestepfish.core.util;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 线程绑定
 */
public class AppContextHolder<T> {

    private static final TransmittableThreadLocal<Object> TTL = new TransmittableThreadLocal<>();

    public static <T> void set(T value) {
        TTL.set(value);
    }

    public static <T> T get() {
        return (T) TTL.get();
    }

    public static void clear() {
        TTL.remove();
    }
}
