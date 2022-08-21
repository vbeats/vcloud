package com.codestepfish.gateway.config;

import com.alibaba.fastjson2.JSON;
import com.codestepfish.core.result.AppException;
import com.codestepfish.core.result.RCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.TreeMap;

/**
 * gateway网关异常拦截处理
 */
@Slf4j
@Order(-1)
@Configuration
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {
    private static final Map<String, Object> res = new TreeMap<>();

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        res.clear();
        if (ex instanceof AppException) {
            AppException exception = (AppException) ex;
            res.put("code", exception.getCode());
            res.put("msg", exception.getMsg());
        } else if (ex instanceof ResponseStatusException) {
            res.put("code", RCode.NOT_FOUND.getCode());
            res.put("msg", "Service Not Found");
        } else {
            res.put("code", RCode.DEFAULT.getCode());
            res.put("msg", RCode.DEFAULT.getMsg());
            log.error("gateway拦截到异常: {} , path: {}", ex.getMessage(), request.getPath().value());
        }

        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            try {
                HttpStatus status = HttpStatus.OK;
                return bufferFactory.wrap(ByteBuffer.wrap(JSON.toJSONString(res).getBytes(Charset.defaultCharset())));
            } catch (Exception e) {
                log.error("gateway响应异常...", e);
                return bufferFactory.wrap(ByteBuffer.allocate(0));
            }
        }));
    }
}
