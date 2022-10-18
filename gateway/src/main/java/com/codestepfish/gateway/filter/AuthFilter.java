package com.codestepfish.gateway.filter;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.codestepfish.common.config.app.AppConfig;
import com.codestepfish.common.result.AppException;
import com.codestepfish.common.result.RCode;
import com.codestepfish.datasource.entity.Admin;
import com.codestepfish.datasource.service.AdminService;
import com.codestepfish.datasource.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

@Slf4j
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthFilter {

    private final AppConfig appConfig;
    private final AdminService adminService;
    private final UserService userService;

    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
                .addInclude("/**")
                .setExcludeList(appConfig.getSkipUrls())
                .setAuth(obj -> {
                    SaRouter.match("/**", r -> StpUtil.checkLogin());

                    // *************再次校验 用户状态********************
                    switch (String.valueOf(StpUtil.getExtra("type"))) {
                        case "admin":
                            Admin admin = adminService.findById(StpUtil.getLoginIdAsLong());
                            Assert.notNull(admin, "用户状态过期");
                            break;
                        case "user":
                            // 暂不处理
                            break;
                        default:
                            throw new AppException(RCode.ACCESS_DENY);
                    }

                    SaRouter.match("/admin/**", r -> StpUtil.checkRole("admin"));
                })
                .setBeforeAuth(r -> {
                    SaHolder.getResponse()
                            .setHeader("Access-Control-Allow-Origin", "*")
                            .setHeader("Access-Control-Allow-Methods", "*")
                            .setHeader("Access-Control-Max-Age", "3600")
                            .setHeader("Access-Control-Allow-Credentials", "true")
                            .setHeader("Access-Control-Allow-Headers", "*")
                    ;

                    SaRouter.match(SaHttpMethod.OPTIONS)
                            .back();
                })
                .setError(e -> {
                    log.warn("认证处理异常: ", e);
                    if (e instanceof NotRoleException || e instanceof NotPermissionException) {
                        throw new AppException(RCode.ACCESS_DENY);
                    } else {
                        throw new AppException(RCode.UNAUTHORIZED_ERROR);
                    }
                })
                ;
    }
}