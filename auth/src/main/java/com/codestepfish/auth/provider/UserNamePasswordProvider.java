package com.codestepfish.auth.provider;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.codestepfish.auth.dto.AuthParam;
import com.codestepfish.auth.dto.AuthResponse;
import com.codestepfish.auth.service.AdminService;
import com.codestepfish.auth.service.TenantService;
import com.codestepfish.common.constant.auth.AuthEnum;
import com.codestepfish.common.constant.captcha.CaptchaEnum;
import com.codestepfish.common.model.AppUser;
import com.codestepfish.common.model.Token;
import com.codestepfish.common.result.AppException;
import com.codestepfish.common.result.RCode;
import com.codestepfish.common.util.JwtUtil;
import com.codestepfish.common.util.RsaUtil;
import com.codestepfish.datasource.entity.Admin;
import com.codestepfish.datasource.entity.AuthClient;
import com.codestepfish.datasource.entity.Tenant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserNamePasswordProvider implements AuthProvider {
    private final RedissonClient redissonClient;
    private final AdminService adminService;
    private final TenantService tenantService;

    @Override
    public AuthResponse handleAuth(AuthParam param, AuthClient client) {

        // 1 验证图形验证码
        if (!StringUtils.hasText(param.getKey()) || !StringUtils.hasText(param.getCode()) || !StringUtils.hasText(param.getAccount()) || !StringUtils.hasText(param.getPassword())) {
            throw new AppException(RCode.PARAM_ERROR);
        }

        RBucket<String> bucket = redissonClient.getBucket(String.format(CaptchaEnum.CAPTCHA_KEY.getKey(), param.getKey()));
        String captchaCode = bucket.getAndDelete();
        Assert.isTrue(param.getCode().equalsIgnoreCase(captchaCode), "验证码错误");

        // 2 校验账号 密码
        String password = RsaUtil.decrypt(client.getPrivateKey(), param.getPassword());
        Assert.hasText(password, "凭证无效");

        Admin admin = adminService.getOne(Wrappers.<Admin>query().lambda()
                .eq(Admin::getAccount, param.getAccount())
                .eq(Admin::getPassword, DigestUtils.md5Hex(password))
                .eq(Admin::getTenantCode, param.getTenantCode())
                .eq(Admin::getStatus, true)
                .isNull(Admin::getDeleteTime)
        );

        Assert.notNull(admin, "凭证无效");

        Tenant tenant = tenantService.getOne(Wrappers.<Tenant>query().lambda()
                .eq(Tenant::getCode, param.getTenantCode())
                .eq(Tenant::getStatus, true)
                .isNull(Tenant::getDeleteTime)
        );

        Assert.notNull(tenant, "凭证无效");

        AppUser user = new AppUser(admin.getId(), admin.getRoleId(), tenant.getId(), tenant.getCode());

        AuthResponse response = new AuthResponse();

        Token accessToken = new Token(user.getId(), tenant.getId(), AuthEnum.ACCESS_TOKEN.getKey(), "admin");
        Token refreshToken = new Token(user.getId(), tenant.getId(), AuthEnum.REFRESH_TOKEN.getKey(), "admin");

        response.setId(admin.getId());
        response.setTenantId(tenant.getId());
        response.setAccessToken(JwtUtil.encode(LocalDateTime.now().plusSeconds(client.getAccessTokenExpire()).plusMinutes(5L), BeanUtil.beanToMap(accessToken, true, true)));
        response.setRefreshToken(JwtUtil.encode(LocalDateTime.now().plusDays(client.getRefreshTokenExpire()).plusMinutes(5L), BeanUtil.beanToMap(refreshToken, true, true)));

        return response;
    }
}
