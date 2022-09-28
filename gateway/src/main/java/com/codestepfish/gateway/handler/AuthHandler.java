package com.codestepfish.gateway.handler;

import com.codestepfish.common.config.app.AppConfig;
import com.codestepfish.common.model.AppUser;
import com.codestepfish.common.result.AppException;
import com.codestepfish.common.result.RCode;
import com.codestepfish.gateway.service.AdminService;
import com.codestepfish.gateway.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;

@Slf4j
public class AuthHandler {

    private static final PathMatcher PATH_MATCHER = new AntPathMatcher();

    /**
     * 是否需要认证后放行
     *
     * @param exchange
     * @param chain
     * @param path
     * @return true 直接放行 false 继续校验用户信息
     */
    public static boolean handleSkipUrls(AppConfig appConfig, ServerWebExchange exchange, GatewayFilterChain chain, String path) {
        if (!CollectionUtils.isEmpty(appConfig.getSkipUrls())) {
            for (String skipUrl : appConfig.getSkipUrls()) {
                if (PATH_MATCHER.match(skipUrl, path)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 校验admin信息
     *
     * @param adminService
     * @param id           admin用户id
     * @param path         请求接口uri
     * @return 自定义AppUser对象
     */
    public static AppUser handleAdmin(AdminService adminService, Long id, String path) {
        AppUser admin = adminService.findById(id);
        if (ObjectUtils.isEmpty(admin) || ObjectUtils.isEmpty(admin.getRoleId())) {
            throw new AppException(RCode.ACCESS_DENY);
        }

        return admin;
    }

    public static AppUser handleUser(UserService userService, Long id) {
        AppUser user = userService.findById(id);
        if (ObjectUtils.isEmpty(user)) {
            throw new AppException(RCode.ACCESS_DENY);
        }

        return user;
    }
}
