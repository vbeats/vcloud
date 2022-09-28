package com.codestepfish.core.aop;

import cn.hutool.core.annotation.AnnotationUtil;
import com.codestepfish.common.constant.config.ConfigParamEnum;
import com.codestepfish.common.result.AppException;
import com.codestepfish.common.result.RCode;
import com.codestepfish.core.annotation.PreAuth;
import com.codestepfish.datasource.service.ConfigParamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthAspect {

    private final ConfigParamService configParamService;

    @Around("@annotation(com.codestepfish.core.annotation.PreAuth) || @within(com.codestepfish.core.annotation.PreAuth)")
    public Object handleAuth(ProceedingJoinPoint point) throws Throwable {
        //获取request 参数----> 来自gateway
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // 与PreAuth注解指定的value对比
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = point.getTarget().getClass().getDeclaredMethod(methodSignature.getName(), methodSignature.getMethod().getParameterTypes());

        // 方法上的  方法上的注解优先级高
        PreAuth preAuthMethod = AnnotationUtil.getAnnotation(method, PreAuth.class);
        PreAuth preAuthClass = AnnotatedElementUtils.findMergedAnnotation(method.getDeclaringClass(), PreAuth.class);

        boolean superOnly = ObjectUtils.isEmpty(preAuthMethod) ? preAuthClass.superOnly() : preAuthMethod.superOnly();

        // 是否只允许  运营平台管理员访问
        if (superOnly) {
            handleSuperOnlyValid(request);
        }

        return point.proceed();
    }

    // 某些接口只能平台管理员访问
    private void handleSuperOnlyValid(HttpServletRequest request) {

        String tenantId = request.getParameter("tenantId");

        if (!configParamService.getConfigByKey(ConfigParamEnum.SUPER_TENANT.getKey()).getConfigValue().equals(tenantId)) {
            log.error("用户id: {} 请求资源: {} 无权访问", request.getParameter("id"), request.getRequestURI());
            throw new AppException(RCode.ACCESS_DENY);
        }
    }
}