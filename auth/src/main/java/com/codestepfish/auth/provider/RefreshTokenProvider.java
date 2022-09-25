package com.codestepfish.auth.provider;

import cn.hutool.core.bean.BeanUtil;
import com.codestepfish.auth.dto.AuthParam;
import com.codestepfish.auth.dto.AuthResponse;
import com.codestepfish.auth.service.AdminService;
import com.codestepfish.auth.service.TenantService;
import com.codestepfish.auth.service.UserService;
import com.codestepfish.common.constant.auth.AuthEnum;
import com.codestepfish.common.model.Token;
import com.codestepfish.common.result.AppException;
import com.codestepfish.common.result.RCode;
import com.codestepfish.common.util.JwtUtil;
import com.codestepfish.datasource.entity.AuthClient;
import com.codestepfish.datasource.entity.User;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class RefreshTokenProvider implements AuthProvider {

    private final AdminService adminService;
    private final UserService userService;
    private final TenantService tenantService;

    @Override
    public AuthResponse handleAuth(AuthParam param, AuthClient client) {
        // 校验 refresh token
        if (!JwtUtil.isVerify(param.getRefreshToken())) {
            throw new AppException(RCode.UNAUTHORIZED_ERROR);
        }

        Claims claims = JwtUtil.decode(param.getRefreshToken());
        String tokenType = claims.get("token_type", String.class);

        if (!AuthEnum.REFRESH_TOKEN.getKey().equalsIgnoreCase(tokenType)) {
            throw new AppException(RCode.ACCESS_DENY);
        }

        // 新access_token
        Long id = claims.get("id", Long.class);
        String userType = claims.get("user_type", String.class);
        Long tenantId = claims.get("tenant_id", Long.class);
        // 用户所属租户
        if ("user".equalsIgnoreCase(userType)) {
            User u = userService.getById(id);
            tenantId = u.getTenantId();
        }

        Token accessToken = new Token(id, tenantId, AuthEnum.ACCESS_TOKEN.getKey(), userType);

        AuthResponse response = new AuthResponse();

        response.setId(id);
        response.setTenantId(tenantId);
        response.setAccessToken(JwtUtil.encode(LocalDateTime.now().plusSeconds(7200L).plusSeconds(10L), BeanUtil.beanToMap(accessToken, true, true)));
        response.setRefreshToken(param.getRefreshToken());

        return response;
    }
}
