package com.codestepfish.core.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // feign 请求 添加 AppUser 参数

        Map<String, String[]> parameterMap = request.getParameterMap();

        if (!CollectionUtils.isEmpty(parameterMap)) {
            Map<String, Collection<String>> params = new HashMap<>();
            parameterMap.entrySet().stream().forEach(e -> params.put(e.getKey(), Arrays.asList(e.getValue())));
            if (!CollectionUtils.isEmpty(requestTemplate.queries())) {
                params.putAll(requestTemplate.queries());
            }
            requestTemplate.queries(params);
        }

    }
}
