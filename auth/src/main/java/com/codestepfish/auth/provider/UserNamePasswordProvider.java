package com.codestepfish.auth.provider;

import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.codestepfish.auth.dto.AuthParam;
import com.codestepfish.auth.dto.AuthResponse;
import com.codestepfish.core.constant.captcha.CaptchaEnum;
import com.codestepfish.core.model.AppUser;
import com.codestepfish.core.result.AppException;
import com.codestepfish.core.result.RCode;
import com.codestepfish.feign.AdminClient;
import com.codestepfish.redis.util.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserNamePasswordProvider implements AuthProvider {
    private final RedisService redisService;
    private final AdminClient adminClient;

    @Override
    public AuthResponse handleAuth(AuthParam param) {

        // 1 验证图形验证码
        if (!StringUtils.hasText(param.getKey()) || !StringUtils.hasText(param.getCode()) || !StringUtils.hasText(param.getAccount()) || !StringUtils.hasText(param.getPassword())) {
            throw new AppException(RCode.PARAM_ERROR);
        }

        String captchaCode = redisService.getAndDelete(String.format(CaptchaEnum.CAPTCHA_KEY.getKey(), param.getKey()));
        Assert.isTrue(param.getCode().equalsIgnoreCase(captchaCode), "验证码错误");

        // 2 校验账号 密码
        Assert.hasText(param.getPassword(), "密码不能为空");

        AppUser admin = adminClient.getAdminInfo(param.getAccount(), DigestUtils.md5Hex(String.format("%s*%s", param.getAccount(), param.getPassword())), param.getTenantCode());

        AuthResponse response = new AuthResponse();

        response.setUser(admin);

        // token 4小时过期
        SaLoginModel extra = SaLoginConfig.setDevice("web")
                .setTimeout(Duration.ofHours(4L).plus(Duration.ofMinutes(15L)).getSeconds())
                .setExtra("identity", "admin")
                .setExtra("tenantId", admin.getTenantId())
                .setExtra("roles", admin.getRoles())
                .setExtra("permissions", admin.getPermissions());

        StpUtil.login(admin.getId(), extra);

        response.setToken(StpUtil.getTokenInfo());

        return response;
    }

    @Override
    public SaTokenInfo refreshToken(AuthParam param) {
        StpUtil.renewTimeout(Duration.ofHours(4L).plus(Duration.ofMinutes(15L)).getSeconds());
        return StpUtil.getTokenInfo();
    }
}
