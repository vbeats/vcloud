package com.codestepfish.auth.provider;

import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.codestepfish.auth.dto.AuthParam;
import com.codestepfish.auth.dto.AuthResponse;
import com.codestepfish.common.constant.captcha.CaptchaEnum;
import com.codestepfish.common.result.AppException;
import com.codestepfish.common.result.RCode;
import com.codestepfish.datasource.entity.Admin;
import com.codestepfish.datasource.entity.Tenant;
import com.codestepfish.datasource.service.AdminService;
import com.codestepfish.datasource.service.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserNamePasswordProvider implements AuthProvider {
    private final RedissonClient redissonClient;
    private final AdminService adminService;
    private final TenantService tenantService;

    @Override
    public AuthResponse handleAuth(AuthParam param) {

        // 1 验证图形验证码
        if (!StringUtils.hasText(param.getKey()) || !StringUtils.hasText(param.getCode()) || !StringUtils.hasText(param.getAccount()) || !StringUtils.hasText(param.getPassword())) {
            throw new AppException(RCode.PARAM_ERROR);
        }

        RBucket<String> bucket = redissonClient.getBucket(String.format(CaptchaEnum.CAPTCHA_KEY.getKey(), param.getKey()));
        String captchaCode = bucket.getAndDelete();
        Assert.isTrue(param.getCode().equalsIgnoreCase(captchaCode), "验证码错误");

        // 2 校验账号 密码
        Assert.hasText(param.getPassword(), "凭证无效");

        Admin admin = adminService.getOne(Wrappers.<Admin>query().lambda()
                .eq(Admin::getAccount, param.getAccount())
                .eq(Admin::getPassword, DigestUtils.md5Hex(param.getPassword()))
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

        AuthResponse response = new AuthResponse();

        response.setId(admin.getId());
        response.setTenantId(tenant.getId());

        SaLoginModel extra = SaLoginConfig.setDevice("web")
                .setTimeout(24 * 3600)
                .setExtra("tenantId", tenant.getId())
                .setExtra("type", "admin");

        StpUtil.login(admin.getId(), extra);

        response.setToken(StpUtil.getTokenInfo());

        return response;
    }
}
