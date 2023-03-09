package com.codestepfish.core.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson2.JSON;
import com.codestepfish.core.constant.auth.AuthConstant;
import com.codestepfish.core.util.AppContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import java.util.List;

/**
 * 租户数据权限
 */
@Slf4j
public class DataScopeInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        List<Long> dataScopes = JSON.parseArray(String.valueOf(StpUtil.getExtra(AuthConstant.Extra.DATA_SCOPES)), Long.class);
        if (!CollectionUtils.isEmpty(dataScopes)) {
            AppContextHolder.set(AuthConstant.Extra.DATA_SCOPES, dataScopes);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AppContextHolder.clear();
    }
}
