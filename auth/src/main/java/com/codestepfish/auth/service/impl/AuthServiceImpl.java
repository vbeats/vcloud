package com.codestepfish.auth.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.codestepfish.auth.dto.AuthParam;
import com.codestepfish.auth.dto.AuthResponse;
import com.codestepfish.auth.dto.Captcha;
import com.codestepfish.auth.handler.PasswordHandler;
import com.codestepfish.auth.service.AuthClientService;
import com.codestepfish.auth.service.AuthService;
import com.codestepfish.core.constant.auth.AuthEnum;
import com.codestepfish.core.constant.auth.GrantTypeEnum;
import com.codestepfish.core.constant.captcha.CaptchaEnum;
import com.codestepfish.core.model.AppUser;
import com.codestepfish.core.model.Token;
import com.codestepfish.core.result.AppException;
import com.codestepfish.core.result.RCode;
import com.codestepfish.core.util.JwtUtil;
import com.codestepfish.datasource.entity.AuthClient;
import com.google.common.base.Splitter;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AuthServiceImpl implements AuthService {

    private static final LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
    private final PasswordHandler passwordHandler;
    private final AuthClientService authClientService;
    private final RedissonClient redissonClient;

    @Override
    public AuthResponse token(AuthParam param) {
        AuthClient authClient = getAuthClient(param.getClientId(), param.getClientSecret(), param.getGrantType());

        switch (GrantTypeEnum.find(param.getGrantType())) {
            case PASSWORD:  // 账号 密码
                return handleResponseToken(passwordHandler.handleLogin(param, authClient), "admin", authClient);
            case REFRESH_TOKEN: // 刷新token
                return handleRefreshToken(param);
            default:
                log.info("不支持的 grantType: {}", param.getGrantType());
                throw new AppException(RCode.PARAM_ERROR);
        }
    }

    @Override
    public Captcha getCaptcha() {
        //  生成验证码
        String key = RandomStringUtils.randomAlphanumeric(12);

        Font font;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, ResourceUtil.getStream("classpath:font.ttf")).deriveFont(Font.PLAIN, 75.0f);
        } catch (Exception e) {
            log.error("字体加载失败.....", e);
            throw new AppException(RCode.DEFAULT);
        }
        lineCaptcha.setFont(font);
        lineCaptcha.createCode();
        String code = lineCaptcha.getCode();
        String image = lineCaptcha.getImageBase64Data();
        RBucket<String> bucket = redissonClient.getBucket(String.format(CaptchaEnum.CAPTCHA_KEY.getKey(), key));
        bucket.set(code, 10, TimeUnit.MINUTES);

        return new Captcha(key, image);
    }

    private AuthResponse handleRefreshToken(AuthParam param) {
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
        Token accessToken = new Token(id, AuthEnum.ACCESS_TOKEN.getKey(), userType);

        AuthResponse response = new AuthResponse();
        response.setAccessToken(JwtUtil.encode(LocalDateTime.now().plusSeconds(7200L).plusSeconds(10L), BeanUtil.beanToMap(accessToken, true, true)));
        response.setRefreshToken(param.getRefreshToken());

        return response;
    }

    private AuthResponse handleResponseToken(AppUser user, String userType, AuthClient authClient) {
        AuthResponse response = new AuthResponse();

        Token accessToken = new Token(user.getId(), AuthEnum.ACCESS_TOKEN.getKey(), userType);
        Token refreshToken = new Token(user.getId(), AuthEnum.REFRESH_TOKEN.getKey(), userType);

        response.setAccessToken(JwtUtil.encode(LocalDateTime.now().plusSeconds(authClient.getAccessTokenExpire()).plusMinutes(5L), BeanUtil.beanToMap(accessToken, true, true)));
        response.setRefreshToken(JwtUtil.encode(LocalDateTime.now().plusDays(authClient.getRefreshTokenExpire()).plusMinutes(5L), BeanUtil.beanToMap(refreshToken, true, true)));

        return response;
    }

    AuthClient getAuthClient(String clientId, String clientSecret, String grantType) {
        List<AuthClient> clients = authClientService.list(Wrappers.<AuthClient>lambdaQuery()
                .eq(AuthClient::getClientId, clientId)
                .eq(AuthClient::getClientSecret, clientSecret)
                .isNull(AuthClient::getDeleteTime)
        );

        if (clients.isEmpty()) {
            throw new AppException(RCode.UNAUTHORIZED_ERROR);
        }

        return clients.stream().filter(e -> Splitter.on(",").omitEmptyStrings().trimResults().splitToList(e.getGrantTypes())
                .contains(grantType)).findFirst().orElseThrow(() -> new AppException(RCode.UNAUTHORIZED_ERROR));
    }
}
