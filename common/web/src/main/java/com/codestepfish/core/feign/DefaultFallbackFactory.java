package com.codestepfish.core.feign;

import com.codestepfish.core.result.AppException;
import com.codestepfish.core.result.RCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

@Slf4j
public class DefaultFallbackFactory<T extends Throwable> implements FallbackFactory<T> {
    @Override
    public T create(Throwable cause) {
        log.error("feign调用异常: ", cause);

        if (cause instanceof AppException) {
            throw (AppException) cause;
        } else {
            throw new AppException(RCode.DEFAULT.getCode(), "服务请求异常");
        }
    }
}
