package com.codestepfish.auth.provider;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.codestepfish.auth.dto.AuthParam;
import com.codestepfish.auth.dto.AuthResponse;
import com.codestepfish.common.result.AppException;
import com.codestepfish.common.result.RCode;
import com.codestepfish.datasource.entity.OpenConfig;
import com.codestepfish.datasource.entity.User;
import com.codestepfish.datasource.model.UserOpenInfo;
import com.codestepfish.datasource.model.WxMiniAppUserInfo;
import com.codestepfish.datasource.service.OpenConfigService;
import com.codestepfish.datasource.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WxMiniAppAuthProvider implements AuthProvider {   // 微信小程序用户认证

    private final OpenConfigService openConfigService;
    private final UserService userService;

    @Override
    public AuthResponse handleAuth(AuthParam param) {
        String code = param.getWxParam().getCode();
        String appid = param.getWxParam().getAppid();

        // 开放平台配置
        OpenConfig openConfig = openConfigService.findByWxMiniAppid(appid);
        Assert.notNull(openConfig, "开放平台配置错误");
        Long tenantId = openConfig.getTenantId();

        WxMaService wxMaService = openConfigService.findWxServiceByAppid(openConfig, WxMaService.class);
        try {
            WxMaJscode2SessionResult sessionInfo = wxMaService.getUserService().getSessionInfo(code);
            String openid = sessionInfo.getOpenid();
            String unionid = sessionInfo.getUnionid();

            // 当前 租户 --> open_id ---> openid 是否已存在这个用户
            User user = userService.findByTenantIdAndWxOpenId(tenantId, openConfig.getId(), openid);
            if (!ObjectUtils.isEmpty(user)) {
                log.info("微信小程序: {} 租户: {} 已存在此用户openid: {} 更新用户信息...", appid, tenantId, openid);
                user.getOpenInfo().setWxMiniApp(new WxMiniAppUserInfo(openConfig.getId(), openid, ""));
                user.setUpdateTime(LocalDateTime.now());
            } else {
                log.info("微信小程序: {} 租户: {} 不存在此用户openid: {}  新增用户...", appid, tenantId, openid);
                user = new User();
                UserOpenInfo openInfo = new UserOpenInfo();
                openInfo.setUnionid(unionid);
                openInfo.setWxMiniApp(new WxMiniAppUserInfo(openConfig.getId(), openid, ""));
                user.setOpenInfo(openInfo);
            }

            user.setTenantId(tenantId);

            userService.saveOrUpdate(user);

            AuthResponse response = new AuthResponse();

            response.setId(user.getId());
            response.setTenantId(tenantId);

            SaLoginModel extra = SaLoginConfig
                    .setDevice("wx_app")   // 微信小程序
                    .setExtra("tenantId", tenantId)
                    .setExtra("type", "user");

            StpUtil.login(user.getId(), extra);

            response.setToken(StpUtil.getTokenInfo());

            return response;

        } catch (WxErrorException e) {
            log.error("微信小程序用户认证失败: ", e);
            throw new AppException(RCode.UNAUTHORIZED_ERROR.getCode(), e.getError().getErrorMsg());
        }
    }
}
