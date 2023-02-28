package com.codestepfish.user.controller.user;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.DesensitizedUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.codestepfish.core.model.AppUser;
import com.codestepfish.core.result.AppException;
import com.codestepfish.core.result.RCode;
import com.codestepfish.redis.constant.RedisConstants;
import com.codestepfish.redis.util.RedisService;
import com.codestepfish.user.controller.user.dto.CodeParam;
import com.codestepfish.user.entity.User;
import com.codestepfish.user.service.UserService;
import com.codestepfish.wechat.WechatConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;

@RestController
@Slf4j
@RequestMapping("/user")
@SaCheckRole(value = {"user"})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;
    private final RedisService redisService;

    // 用户登录认证
    @SaIgnore
    @GetMapping("/info")
    public AppUser getUserInfo(@RequestParam("code") String code, @RequestParam("appid") String appid) {
        // appid  ----> tenant_id
        Long tenantId = redisService.get(String.format(RedisConstants.WX_APPID_BUCKET, appid));

        WxMaService wxMaService = WechatConfig.findWxServiceByAppid(tenantId, appid, WxMaService.class);

        // 获取小程序用户union id openid
        try {
            WxMaJscode2SessionResult sessionInfo = wxMaService.getUserService().getSessionInfo(code);
            String unionId = sessionInfo.getUnionid();
            String openid = sessionInfo.getOpenid();

            Assert.hasText(unionId, "微信认证异常");
            Assert.hasText(openid, "微信认证异常");

            // 是否已存在此用户
            User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getTenantId, tenantId).eq(User::getWxUnionId, unionId));
            if (ObjectUtils.isEmpty(user)) {
                // 新增
                user = new User();
                user.setTenantId(tenantId)
                        .setWxUnionId(unionId)
                        .setWxMaOpenid(openid)
                        .setCreateTime(LocalDateTime.now());

                userService.save(user);
            } else {
                // 更新
                if (!user.getWxUnionId().equals(unionId) || !user.getWxMaOpenid().equals(openid)) {
                    user.setWxUnionId(unionId)
                            .setWxMaOpenid(openid)
                            .setUpdateTime(LocalDateTime.now());
                }
            }

            return AppUser.builder()
                    .id(user.getId())
                    .roleId(null)
                    .tenantId(tenantId)
                    .account("")
                    .nickName("")
                    .phone(DesensitizedUtil.mobilePhone(user.getPhone()))
                    .roles(Collections.singleton("user"))
                    .permissions(Collections.emptySet())
                    .build();

        } catch (WxErrorException e) {
            log.error("微信登录认证异常: ", e);
            throw new AppException(RCode.DEFAULT.getCode(), e.getError().getErrorMsg());
        }
    }

    // 返回脱敏的手机号
    @PostMapping("/bindPhone")
    public String bindPhone(@RequestBody CodeParam param) {
        Long userId = StpUtil.getLoginIdAsLong();
        Long tenantId = Long.valueOf(String.valueOf(StpUtil.getExtra("tenantId")));

        WxMaService wxMaService = WechatConfig.findWxServiceByAppid(tenantId, param.getAppid(), WxMaService.class);
        try {
            WxMaPhoneNumberInfo phoneNoInfo = wxMaService.getUserService().getNewPhoneNoInfo(param.getCode());
            String phone = phoneNoInfo.getPurePhoneNumber();
            User user = userService.getById(userId);

            // 1. 此手机号是否被其它用户绑定
            User exist = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getTenantId, tenantId).eq(User::getPhone, phone).eq(User::getDelFlag, false));
            Assert.isTrue(ObjectUtils.isEmpty(exist) || exist.getId().equals(userId), "此手机号已绑定其它微信会员");

            // 2. 只有没手机号的用户set  已经有手机号的不去更新

            if (StringUtils.hasText(phone) && !StringUtils.hasText(user.getPhone())) {
                user.setPhone(phone);
                user.setUpdateTime(LocalDateTime.now());
                userService.updateById(user);
            }

            return DesensitizedUtil.mobilePhone(user.getPhone());
        } catch (WxErrorException e) {
            log.error("微信获取用户手机号异常: ", e);
            throw new AppException(RCode.DEFAULT.getCode(), e.getError().getErrorMsg());
        }
    }
}
