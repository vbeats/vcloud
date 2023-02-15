package com.codestepfish.core.handler;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.DeferredResultMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Configuration
public class ResponseConfiguration implements InitializingBean {
    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @Override
    public void afterPropertiesSet() throws Exception {
        final List<HandlerMethodReturnValueHandler> originalHandlers = new ArrayList<>(Objects.requireNonNull(requestMappingHandlerAdapter.getReturnValueHandlers()));

        RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor = null;
        for (final HandlerMethodReturnValueHandler valueHandler : originalHandlers) {
            if (RequestResponseBodyMethodProcessor.class.isAssignableFrom(valueHandler.getClass())) {
                requestResponseBodyMethodProcessor = (RequestResponseBodyMethodProcessor) valueHandler;
                break;
            }
        }
        ResponseObjectHandler aronResponseValueHandler = new ResponseObjectHandler(requestResponseBodyMethodProcessor);

        final int deferredPos = obtainValueHandlerPosition(originalHandlers);
        originalHandlers.add(deferredPos + 1, aronResponseValueHandler);
        requestMappingHandlerAdapter.setReturnValueHandlers(originalHandlers);
    }

    private int obtainValueHandlerPosition(final List<HandlerMethodReturnValueHandler> originalHandlers) {
        for (int i = 0; i < originalHandlers.size(); i++) {
            final HandlerMethodReturnValueHandler valueHandler = originalHandlers.get(i);
            if (DeferredResultMethodReturnValueHandler.class.isAssignableFrom(valueHandler.getClass())) {
                return i;
            }
        }
        return -1;
    }
}
