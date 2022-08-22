package com.codestepfish.gateway.filter;

import com.codestepfish.core.config.app.AppConfig;
import com.codestepfish.core.constant.auth.AuthEnum;
import com.codestepfish.core.model.AppUser;
import com.codestepfish.core.result.AppException;
import com.codestepfish.core.result.RCode;
import com.codestepfish.core.util.JwtUtil;
import com.codestepfish.gateway.handler.AuthHandler;
import com.codestepfish.gateway.service.AdminService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthFilter implements GlobalFilter, Ordered {

    private final AppConfig appConfig;
    private final AdminService adminService;

    /**
     * 添加用户信息到queryString
     *
     * @param appUser
     * @param uri
     * @return
     */
    @NotNull
    private static StringBuilder getStringBuilder(AppUser appUser, URI uri) {
        StringBuilder query = new StringBuilder();
        String originalQuery = uri.getRawQuery();   // 原始queryString

        if (StringUtils.hasText(originalQuery)) {
            query.append(originalQuery);
            if (originalQuery.charAt(originalQuery.length() - 1) != '&') {
                query.append('&');
            }
        }

        query.append("id=").append(appUser.getId()).append("&tenantCode=").append(appUser.getTenantCode());
        return query;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        // *************************** uri校验
        if (AuthHandler.handleSkipUrls(appConfig, exchange, chain, path)) return chain.filter(exchange);

        // ************************* 校验token
        String token = "";
        try {
            token = request.getHeaders().getFirst(AuthEnum.REQUEST_HEADER_TOKEN.getKey()).substring("Bearer ".length());
        } catch (Exception e) {
            throw new AppException(RCode.UNAUTHORIZED_ERROR);
        }

        if (!StringUtils.hasText(token) || !JwtUtil.isVerify(token)) {
            throw new AppException(RCode.UNAUTHORIZED_ERROR);
        }

        Claims claims = JwtUtil.decode(token);

        // token不是access_token
        if (!AuthEnum.access_token.getKey().equalsIgnoreCase(claims.get("tokenType", String.class))) {
            throw new AppException(RCode.UNAUTHORIZED_ERROR);
        }

        // ********************* 用户信息校验
        Long userId = claims.get("id", Long.class);
        String userType = claims.get("userType", String.class);

        AppUser appUser = null;

        // 用户是管理员 or 普通user
        switch (userType) {
            case "admin":
                appUser = AuthHandler.handleAdmin(adminService, userId, path);
                break;
            case "user":
                // todo user 相关处理
                break;
            default:
                throw new AppException(RCode.UNAUTHORIZED_ERROR);
        }

        // ******************** request parameters 添加用户信息 中文需要URIEncode
        URI uri = request.getURI();
        StringBuilder query = getStringBuilder(appUser, uri);

        return chain.filter(exchange.mutate().request(
                request.mutate().uri(
                        UriComponentsBuilder.fromUri(uri).replaceQuery(query.toString()).build(true).toUri()
                ).build()).build()
        );
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

}
