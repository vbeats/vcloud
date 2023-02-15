package com.codestepfish.core.handler;

import com.codestepfish.core.result.R;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

public class ResponseObjectHandler implements HandlerMethodReturnValueHandler {
    private final RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor;

    public ResponseObjectHandler(RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor) {
        this.requestResponseBodyMethodProcessor = requestResponseBodyMethodProcessor;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return (AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), ResponseBody.class) || returnType.hasMethodAnnotation(ResponseBody.class));
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        R result = null;
        if (!R.class.isAssignableFrom(returnType.getParameterType())) {
            result = R.success(returnValue);
        } else if (ObjectUtils.isEmpty(returnValue)) {
            result = R.success();
        } else {
            result = (R) returnValue;
        }
        this.requestResponseBodyMethodProcessor.handleReturnValue(result, returnType, mavContainer, webRequest);
    }
}
