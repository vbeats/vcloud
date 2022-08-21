package com.codestepfish.auth.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import com.bootvue.auth.dto.AuthParam;
import com.bootvue.auth.dto.AuthResponse;
import com.bootvue.auth.dto.Captcha;
import com.bootvue.auth.service.AuthService;
import com.bootvue.auth.service.UserMapperService;
import com.bootvue.common.config.app.AppConfig;
import com.bootvue.common.constant.AppConst;
import com.bootvue.common.model.Token;
import com.bootvue.common.result.AppException;
import com.bootvue.common.result.RCode;
import com.bootvue.common.util.JwtUtil;
import com.bootvue.common.util.RsaUtil;
import com.bootvue.datasource.entity.User;
import com.bootvue.datasource.type.AccountTypeEnum;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AuthServiceImpl implements AuthService {

    private static final LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);

    private final AppConfig appConfig;
    private final UserMapperService userMapperService;
    private final RedissonClient redissonClient;

    @Override
    public AuthResponse token(AuthParam param) {
        switch (param.getGrantType()) {
            case "password":  // 账号 密码
                return handleLogin(param);
            case "refresh_token": // 刷新token
                return handleRefreshToken(param);
            default:
                log.info("不支持的 grantType: {}", param.getGrantType());
                throw new AppException(RCode.PARAM_ERROR);
        }
    }

    private AuthResponse handleLogin(AuthParam param) {
        // 1 验证图形验证码
        if (!StringUtils.hasText(param.getKey()) || !StringUtils.hasText(param.getCode()) || !StringUtils.hasText(param.getMerchantCode()) ||
                !StringUtils.hasText(param.getAccount()) || !StringUtils.hasText(param.getPassword())) {
            throw new AppException(RCode.PARAM_ERROR);
        }

        RBucket<String> bucket = redissonClient.getBucket(String.format(AppConst.CAPTCHA_KEY, param.getKey()));
        String captchaCode = bucket.getAndDelete();
        Assert.isTrue(param.getCode().equalsIgnoreCase(captchaCode), "验证码错误");

        // 2 校验账号 密码
        String password = RsaUtil.decrypt(appConfig.getPrivateKey(), param.getPassword());
        Assert.hasText(password, "凭证无效");
        User user = userMapperService.getByMerchantCodeAndAccountAndPassword(param.getMerchantCode(), param.getAccount(), DigestUtils.md5Hex(password));

        Assert.notNull(user, "凭证无效");

        return handleResponseToken(user, user.getAccountType());
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
        RBucket<String> bucket = redissonClient.getBucket(String.format(AppConst.CAPTCHA_KEY, key));
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

        if (!AppConst.REFRESH_TOKEN.equalsIgnoreCase(tokenType)) {
            throw new AppException(RCode.ACCESS_DENY);
        }

        // 新access_token
        Long id = claims.get("id", Long.class);
        Integer accountType = claims.get("account_type", Integer.class);
        Long merchantId = claims.get("merchant_id", Long.class);
        Token accessToken = new Token(id, merchantId, AppConst.ACCESS_TOKEN, accountType);

        AuthResponse response = new AuthResponse();
        response.setAccessToken(JwtUtil.encode(LocalDateTime.now().plusSeconds(7200L).plusSeconds(10L), BeanUtil.beanToMap(accessToken, true, true)));
        response.setRefreshToken(param.getRefreshToken());

        return response;
    }

    private AuthResponse handleResponseToken(User user, AccountTypeEnum accountTypeEnum) {
        AuthResponse response = new AuthResponse();

        Token accessToken = new Token(user.getId(), user.getMerchantId(), AppConst.ACCESS_TOKEN, accountTypeEnum.getValue());
        Token refreshToken = new Token(user.getId(), user.getMerchantId(), AppConst.REFRESH_TOKEN, accountTypeEnum.getValue());

        response.setAccessToken(JwtUtil.encode(LocalDateTime.now().plusSeconds(7200L).plusMinutes(5L), BeanUtil.beanToMap(accessToken, true, true)));
        response.setRefreshToken(JwtUtil.encode(LocalDateTime.now().plusDays(30L).plusMinutes(5L), BeanUtil.beanToMap(refreshToken, true, true)));

        return response;
    }
}
