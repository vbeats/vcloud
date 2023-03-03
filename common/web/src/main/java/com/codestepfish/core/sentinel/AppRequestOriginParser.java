package com.codestepfish.core.sentinel;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义 sentinel针对来源控制
 * 开启需要注入到IOC容器中
 */
@Slf4j
public class AppRequestOriginParser implements RequestOriginParser {

    @Override
    public String parseOrigin(HttpServletRequest request) {
        return null;
    }
}