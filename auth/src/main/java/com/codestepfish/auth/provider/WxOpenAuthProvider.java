package com.codestepfish.auth.provider;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.codestepfish.auth.dto.AuthParam;
import com.codestepfish.auth.dto.AuthResponse;
import com.codestepfish.common.result.AppException;
import com.codestepfish.common.result.RCode;
import com.codestepfish.datasource.entity.OpenConfig;
import com.codestepfish.datasource.entity.OpenConfigItem;
import com.codestepfish.datasource.entity.User;
import com.codestepfish.datasource.model.UserOpenInfo;
import com.codestepfish.datasource.model.WxMiniAppUserInfo;
import com.codestepfish.datasource.service.OpenConfigService;
import com.codestepfish.datasource.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.WxOpenComponentService;
import me.chanjar.weixin.open.api.WxOpenMaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WxOpenAuthProvider implements AuthProvider {   // 微信开放平台

    private final OpenConfigService openConfigService;
    private final UserService userService;

    @Override
    public AuthResponse handleAuth(AuthParam param) {
        String openAppid = param.getWxParam().getOpenAppId();  // 开放平台appid

        String appid = param.getWxParam().getAppid();
        String code = param.getWxParam().getCode();

        // 开放平台配置
        OpenConfig openConfig = openConfigService.findWByWxOpenAppid(openAppid);
        OpenConfigItem item = openConfigService.findByOpenConfigIdAndAppid(openConfig.getId(), appid);
        Long tenantId = openConfig.getTenantId();

        WxOpenComponentService wxOpenComponentService = openConfigService.findWxServiceByAppid(openConfig, WxOpenComponentService.class);
        try {
            User user = null;

            switch (item.getType()) {
                case WX_MINIAPP:  // 小程序    // todo 第三方平台 ---> 小程序/公众号 这里处理的不对
                    WxOpenMaService wxOpenMaService = wxOpenComponentService.getWxMaServiceByAppid(appid);

                    WxMaJscode2SessionResult sessionInfo = wxOpenMaService.getUserService().getSessionInfo(code);
                    String openid = sessionInfo.getOpenid();
                    String unionid = sessionInfo.getUnionid();

                    // 当前 租户 --> open_id ---> openid 是否已存在这个用户
                    user = userService.findByTenantIdAndWxOpenId(tenantId, openConfig.getId(), openid);
                    if (!ObjectUtils.isEmpty(user)) {
                        log.info("开放平台---微信小程序: {} 租户: {} 已存在此用户openid: {} 更新用户信息...", appid, tenantId, openid);
                        user.getOpenInfo().setWxMiniApp(new WxMiniAppUserInfo(openid, unionid, ""));
                        user.setUpdateTime(LocalDateTime.now());
                    } else {
                        log.info("开放平台---微信小程序: {} 租户: {} 不存在此用户openid: {}  新增用户...", appid, tenantId, openid);
                        user = new User();
                        UserOpenInfo openInfo = new UserOpenInfo();
                        openInfo.setOpenConfigId(openConfig.getId());
                        openInfo.setWxMiniApp(new WxMiniAppUserInfo(openid, unionid, ""));
                        user.setOpenInfo(openInfo);
                    }

                    user.setTenantId(tenantId);

                    userService.saveOrUpdate(user);
                    break;
                case WX_MP:
                    // todo
                    break;
            }

            AuthResponse response = new AuthResponse();

            response.setId(user.getId());
            response.setTenantId(tenantId);

            SaLoginModel extra = SaLoginConfig
                    .setDevice("wx_open")   // 微信开放平台
                    .setExtra("tenantId", tenantId)
                    .setExtra("type", "user");

            StpUtil.login(user.getId(), extra);

            response.setToken(StpUtil.getTokenInfo());

            return response;

        } catch (WxErrorException e) {
            log.error("微信开放平台用户认证失败: ", e);
            throw new AppException(RCode.UNAUTHORIZED_ERROR.getCode(), e.getError().getErrorMsg());
        }
    }
}
