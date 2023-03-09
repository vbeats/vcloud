package com.codestepfish.auth.provider;

import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.codestepfish.auth.dto.AuthParam;
import com.codestepfish.auth.dto.AuthResponse;
import com.codestepfish.core.constant.auth.AuthConstant;
import com.codestepfish.core.constant.auth.DeviceTypeEnum;
import com.codestepfish.core.model.AppUser;
import com.codestepfish.feign.UserClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.Duration;

/**
 * 微信小程序认证
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WxMaAuthProvider implements AuthProvider {

    private final UserClient userClient;

    @Override
    public AuthResponse handleAuth(AuthParam param) {
        Assert.hasText(param.getWxMaParam().getCode(), "参数错误");
        Assert.hasText(param.getWxMaParam().getAppid(), "参数错误");

        AppUser user = userClient.getUserInfo(param.getWxMaParam().getCode(), param.getWxMaParam().getAppid());

        AuthResponse response = new AuthResponse();

        response.setUser(user);

        // token 4小时过期
        SaLoginModel extra = SaLoginConfig.setDevice(DeviceTypeEnum.WX_APP.getDevice())
                .setTimeout(Duration.ofHours(4L).plus(Duration.ofMinutes(15L)).getSeconds())
                .setExtra(AuthConstant.Extra.TENANT_ID, user.getTenantId());

        StpUtil.login(user.getId(), extra);

        response.setToken(StpUtil.getTokenInfo());

        return response;
    }

    @Override
    public SaTokenInfo refreshToken(AuthParam param) {
        StpUtil.renewTimeout(Duration.ofHours(4L).plus(Duration.ofMinutes(15L)).getSeconds());
        return StpUtil.getTokenInfo();
    }
}
