package com.codestepfish.auth.provider;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.codestepfish.auth.dto.AuthParam;
import com.codestepfish.auth.dto.AuthResponse;
import com.codestepfish.common.result.AppException;
import com.codestepfish.common.result.RCode;
import com.codestepfish.datasource.entity.OpenConfig;
import com.codestepfish.datasource.entity.User;
import com.codestepfish.datasource.entity.UserWxMa;
import com.codestepfish.datasource.service.OpenConfigService;
import com.codestepfish.datasource.service.UserService;
import com.codestepfish.datasource.service.UserWxMaService;
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
public class WxMaAppAuthProvider implements AuthProvider {   // 微信小程序用户认证

    private final OpenConfigService openConfigService;
    private final UserService userService;
    private final UserWxMaService userWxMaService;

    @Override
    public AuthResponse handleAuth(AuthParam param) {
        String code = param.getWxParam().getCode();
        String appid = param.getWxParam().getAppid();

        // 开放平台配置
        OpenConfig openConfig = openConfigService.findByWxMaAppid(appid);
        Assert.notNull(openConfig, "开放平台配置错误");
        Long tenantId = openConfig.getTenantId();

        WxMaService wxMaService = openConfigService.findWxServiceByAppid(openConfig, WxMaService.class);
        try {
            WxMaJscode2SessionResult sessionInfo = wxMaService.getUserService().getSessionInfo(code);
            String openid = sessionInfo.getOpenid();
            String unionid = sessionInfo.getUnionid();

            // 当前 租户 --> open_config_id ---> openid 是否已存在这个用户
            User user = userWxMaService.findByTenantIdAndOpenId(tenantId, openConfig.getId(), openid);

            if (ObjectUtils.isEmpty(user)) {
                log.info("租户: {} ,open_config_id: {} ,openid: {} ,unionid: {} 不存在此用户 新增...", tenantId, openConfig.getId(), openid, unionid);
                user = new User();

                user.setTenantId(tenantId);
                user.setCreateTime(LocalDateTime.now());
                userService.save(user);

                UserWxMa userWxMa = new UserWxMa();
                userWxMa.setUserId(user.getId());
                userWxMa.setOpenConfigId(openConfig.getId());
                userWxMa.setUnionid(unionid);
                userWxMa.setOpenid(openid);
                userWxMa.setCreateTime(LocalDateTime.now());

                userWxMaService.save(userWxMa);
            } else {
                log.info("租户: {} ,open_config_id: {} ,openid: {} ,unionid: {} 用户已存在 更新...", tenantId, openConfig.getId(), openid, unionid);

                UserWxMa userWxMa = userWxMaService.getOne(Wrappers.<UserWxMa>lambdaQuery().eq(UserWxMa::getUserId, user.getId()).eq(UserWxMa::getOpenConfigId, openConfig.getId()).eq(UserWxMa::getOpenid, openid).isNull(UserWxMa::getDeleteTime));
                userWxMa.setUnionid(unionid);
                userWxMa.setOpenid(openid);
                userWxMa.setUpdateTime(LocalDateTime.now());

                userWxMaService.updateById(userWxMa);
            }

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
