package com.codestepfish.auth.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.io.resource.ResourceUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.codestepfish.auth.dto.AuthParam;
import com.codestepfish.auth.dto.AuthResponse;
import com.codestepfish.auth.dto.Captcha;
import com.codestepfish.auth.provider.ProviderContextHolder;
import com.codestepfish.auth.service.AuthClientService;
import com.codestepfish.auth.service.AuthService;
import com.codestepfish.common.constant.captcha.CaptchaEnum;
import com.codestepfish.common.result.AppException;
import com.codestepfish.common.result.RCode;
import com.codestepfish.datasource.entity.AuthClient;
import com.google.common.base.Splitter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AuthServiceImpl implements AuthService {

    private static final LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
    private final AuthClientService authClientService;
    private final RedissonClient redissonClient;

    @Override
    public AuthResponse token(AuthParam param) {
        AuthClient authClient = getAuthClient(param.getClientId(), param.getClientSecret(), param.getGrantType());

        return ProviderContextHolder.getAuthProvider(param.getGrantType()).handleAuth(param, authClient);
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

    AuthClient getAuthClient(String clientId, String clientSecret, String grantType) {
        AuthClient client = authClientService.getOne(Wrappers.<AuthClient>lambdaQuery()
                .eq(AuthClient::getClientId, clientId)
                .eq(AuthClient::getClientSecret, clientSecret)
                .isNull(AuthClient::getDeleteTime)
        );

        if (ObjectUtils.isEmpty(client)) {
            throw new AppException(RCode.UNAUTHORIZED_ERROR);
        }

        List<String> grantTypes = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(client.getGrantTypes());

        if (grantTypes.contains(grantType)) {
            return client;
        } else {
            throw new AppException(RCode.UNAUTHORIZED_ERROR);
        }
    }
}
