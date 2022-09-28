package com.codestepfish.auth.provider;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.hutool.core.bean.BeanUtil;
import com.codestepfish.auth.dto.AuthParam;
import com.codestepfish.auth.dto.AuthResponse;
import com.codestepfish.auth.service.UserService;
import com.codestepfish.common.constant.auth.AuthEnum;
import com.codestepfish.common.model.AppUser;
import com.codestepfish.common.model.Token;
import com.codestepfish.common.result.AppException;
import com.codestepfish.common.result.RCode;
import com.codestepfish.common.util.JwtUtil;
import com.codestepfish.datasource.entity.AuthClient;
import com.codestepfish.datasource.entity.OpenConfig;
import com.codestepfish.datasource.entity.User;
import com.codestepfish.datasource.model.UserOpenInfo;
import com.codestepfish.datasource.model.WxMiniAppUserInfo;
import com.codestepfish.datasource.service.OpenConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WxMiniAppAuthProvider implements AuthProvider {   // 微信小程序用户认证

    private final OpenConfigService openConfigService;
    private final UserService userService;

    @Override
    public AuthResponse handleAuth(AuthParam param, AuthClient client) {
        String code = param.getWxParam().getCode();
        String appid = param.getWxParam().getAppid();

        // 开放平台配置
        OpenConfig openConfig = openConfigService.findWByWxMiniAppid(appid);
        Long tenantId = openConfig.getTenantId();

        WxMaService wxMaService = openConfigService.findWxMaServiceByAppid(openConfig, WxMaService.class);
        try {
            WxMaJscode2SessionResult sessionInfo = wxMaService.getUserService().getSessionInfo(code);
            String openid = sessionInfo.getOpenid();
            String unionid = sessionInfo.getUnionid();

            // 当前 租户 --> open_id ---> openid 是否已存在这个用户
            User user = userService.findByTenantIdAndWxOpenId(tenantId, openConfig.getId(), openid);
            if (!ObjectUtils.isEmpty(user)) {
                log.info("微信小程序: {} 租户: {} 已存在此用户openid: {} 更新用户信息...", appid, tenantId, openid);
                user.getOpenInfo().setWxMiniApp(new WxMiniAppUserInfo(openid, unionid, ""));
                user.setUpdateTime(LocalDateTime.now());
            } else {
                log.info("微信小程序: {} 租户: {} 不存在此用户openid: {}  新增用户...", appid, tenantId, openid);
                user = new User();
                UserOpenInfo openInfo = new UserOpenInfo();
                openInfo.setOpenId(openConfig.getId());
                openInfo.setWxMiniApp(new WxMiniAppUserInfo(openid, unionid, ""));
                user.setOpenInfo(openInfo);
            }

            user.setTenantId(tenantId);

            userService.saveOrUpdate(user);

            AppUser u = new AppUser(user.getId(), 0L, tenantId);

            AuthResponse response = new AuthResponse();

            Token accessToken = new Token(user.getId(), tenantId, AuthEnum.ACCESS_TOKEN.getKey(), "user");
            Token refreshToken = new Token(user.getId(), tenantId, AuthEnum.REFRESH_TOKEN.getKey(), "user");

            response.setId(user.getId());
            response.setTenantId(tenantId);
            response.setAccessToken(JwtUtil.encode(LocalDateTime.now().plusSeconds(client.getAccessTokenExpire()).plusMinutes(5L), BeanUtil.beanToMap(accessToken, true, true)));
            response.setRefreshToken(JwtUtil.encode(LocalDateTime.now().plusDays(client.getRefreshTokenExpire()).plusMinutes(5L), BeanUtil.beanToMap(refreshToken, true, true)));

            return response;

        } catch (WxErrorException e) {
            log.error("微信小程序用户认证失败: ", e);
            throw new AppException(RCode.UNAUTHORIZED_ERROR.getCode(), e.getError().getErrorMsg());
        }
    }
}
