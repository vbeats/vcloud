package com.codestepfish.user.feign;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.hutool.core.util.DesensitizedUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.codestepfish.core.model.AppUser;
import com.codestepfish.core.result.AppException;
import com.codestepfish.core.result.RCode;
import com.codestepfish.redis.constant.RedisConstants;
import com.codestepfish.redis.util.RedisService;
import com.codestepfish.user.entity.User;
import com.codestepfish.user.service.UserService;
import com.codestepfish.wechat.WechatConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Collections;

@RestController
@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserFeignController {

    private final UserService userService;
    private final RedisService redisService;

    // 用户登录认证
    @GetMapping("/info")
    public AppUser getUserInfo(@RequestParam("code") String code, @RequestParam("appid") String appid) {
        // appid  ----> merchant_id
        Long merchantId = redisService.get(String.format(RedisConstants.WX_APPID_BUCKET, appid));

        WxMaService wxMaService = WechatConfig.findWxServiceByAppid(merchantId, appid, WxMaService.class);

        // 获取小程序用户union id openid
        try {
            WxMaJscode2SessionResult sessionInfo = wxMaService.getUserService().getSessionInfo(code);
            String unionId = sessionInfo.getUnionid();
            String openid = sessionInfo.getOpenid();

            Assert.hasText(unionId, "微信认证异常");
            Assert.hasText(openid, "微信认证异常");

            // 是否已存在此用户
            User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getMerchantId, merchantId).eq(User::getWxUnionId, unionId));
            if (ObjectUtils.isEmpty(user)) {
                // 新增
                user = new User();
                user.setMerchantId(merchantId)
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
                    .merchantId(merchantId)
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
}
