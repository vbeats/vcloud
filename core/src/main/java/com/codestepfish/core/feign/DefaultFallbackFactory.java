package com.codestepfish.core.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

@Slf4j
public class DefaultFallbackFactory<T extends Throwable> implements FallbackFactory<T> {
    @Override
    public T create(Throwable cause) {
        log.error("feign调用异常: ", cause);
        return null;
    }
}
