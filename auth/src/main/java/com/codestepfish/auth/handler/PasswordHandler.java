package com.codestepfish.auth.handler;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.codestepfish.auth.dto.AuthParam;
import com.codestepfish.auth.service.AdminService;
import com.codestepfish.common.constant.captcha.CaptchaEnum;
import com.codestepfish.common.model.AppUser;
import com.codestepfish.common.result.AppException;
import com.codestepfish.common.result.RCode;
import com.codestepfish.common.util.RsaUtil;
import com.codestepfish.datasource.entity.Admin;
import com.codestepfish.datasource.entity.AuthClient;
import com.codestepfish.datasource.entity.Tenant;
import com.codestepfish.datasource.mapper.TenantMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PasswordHandler {
    private final RedissonClient redissonClient;
    private final AdminService adminService;
    private final TenantMapper tenantMapper;

    /**
     * 账号 密码认证
     *
     * @param param
     * @param authClient
     * @return
     */
    public AppUser handleLogin(AuthParam param, AuthClient authClient) {
        // 1 验证图形验证码
        if (!StringUtils.hasText(param.getKey()) || !StringUtils.hasText(param.getCode()) || !StringUtils.hasText(param.getAccount()) || !StringUtils.hasText(param.getPassword())) {
            throw new AppException(RCode.PARAM_ERROR);
        }

        RBucket<String> bucket = redissonClient.getBucket(String.format(CaptchaEnum.CAPTCHA_KEY.getKey(), param.getKey()));
        String captchaCode = bucket.getAndDelete();
        Assert.isTrue(param.getCode().equalsIgnoreCase(captchaCode), "验证码错误");

        // 2 校验账号 密码
        String password = RsaUtil.decrypt(authClient.getPrivateKey(), param.getPassword());
        Assert.hasText(password, "凭证无效");

        Admin admin = adminService.getOne(Wrappers.<Admin>query().lambda()
                .eq(Admin::getAccount, param.getAccount())
                .eq(Admin::getPassword, password)
                .eq(Admin::getTenantCode, param.getCode())
                .eq(Admin::getStatus, true)
                .isNull(Admin::getDeleteTime)
        );

        Assert.notNull(admin, "凭证无效");

        Tenant tenant = tenantMapper.selectOne(Wrappers.<Tenant>query().lambda()
                .eq(Tenant::getCode, param.getCode())
                .eq(Tenant::getStatus, true)
                .isNull(Tenant::getDeleteTime)
        );

        Assert.notNull(tenant, "凭证无效");

        return new AppUser(admin.getId(), tenant.getCode());
    }
}
