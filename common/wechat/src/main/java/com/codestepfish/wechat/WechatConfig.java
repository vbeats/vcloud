package com.codestepfish.wechat;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaRedissonConfigImpl;
import cn.hutool.extra.spring.SpringUtil;
import com.codestepfish.redis.constant.LovConstants;
import com.codestepfish.redis.util.LovUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.service.WxService;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.config.impl.WxCpRedissonConfigImpl;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpRedissonConfigImpl;
import org.redisson.api.RedissonClient;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 微信平台
 */
@Slf4j
public class WechatConfig {
    // 小程序&公众号
    private static final Map<Class, WxService> WX_SERVICES = new ConcurrentHashMap<>(10);  // Map<WxService.class , WxService instance>
    // 企业微信
    private static final Map<String, WxCpService> WX_CP_SERVICES = new ConcurrentHashMap<>(10); // appid ==> WxCpService

    /**
     * 根据appid 获取对应平台wxService
     *
     * @param tenantId 租户id
     * @param appid    微信平台appid
     * @param t        对应平台WxService.class
     * @return
     */
    public static <T> T findWxServiceByAppid(Long tenantId, String appid, Class<? extends WxService> t) {
        WxService wxService = null;
        try {
            if (WxMaService.class.equals(t)) {
                wxService = ((WxMaService) WX_SERVICES.get(t)).switchoverTo(appid);
            } else if (WxMpService.class.equals(t)) {
                wxService = ((WxMpService) WX_SERVICES.get(t)).switchoverTo(appid);
            } else if (WxCpService.class.equals(t)) {
                wxService = WX_CP_SERVICES.get(appid);
            }
        } catch (Exception e) {
            wxService = putWxService(tenantId, appid, t);
        }

        Assert.notNull(wxService, "未初始化微信配置");
        return (T) wxService;
    }

    /**
     * @param tenantId
     * @param appid
     * @param t
     * @return
     */
    public static WxService putWxService(Long tenantId, String appid, Class<? extends WxService> t) {

        RedissonClient redissonClient = SpringUtil.getBean(RedissonClient.class);

        if (WxMaService.class.equals(t)) {     // 微信小程序
            String secret = LovUtil.get(tenantId, LovConstants.WECHAT_CATEGORY, LovConstants.WX_MA_SECRET);
            String token = LovUtil.get(tenantId, LovConstants.WECHAT_CATEGORY, LovConstants.WX_MA_TOKEN);
            String aesKey = LovUtil.get(tenantId, LovConstants.WECHAT_CATEGORY, LovConstants.WX_MA_AES_KEY);
            String cloudEnv = LovUtil.get(tenantId, LovConstants.WECHAT_CATEGORY, LovConstants.WX_MA_CLOUD_ENV);

            WxMaRedissonConfigImpl config = new WxMaRedissonConfigImpl(redissonClient, "open:wx_ma");

            config.setAppid(appid);
            config.setSecret(secret);
            config.setMsgDataFormat("JSON");
            if (StringUtils.hasText(token)) {
                config.setToken(token);
            }
            if (StringUtils.hasText(aesKey)) {
                config.setAesKey(aesKey);
            }
            if (StringUtils.hasText(cloudEnv)) {
                config.setCloudEnv(cloudEnv);
            }

            WxMaService wxMaService = new WxMaServiceImpl();
            wxMaService.addConfig(appid, config);

            WX_SERVICES.put(WxMaService.class, wxMaService);

            return wxMaService.switchoverTo(appid);
        } else if (WxMpService.class.equals(t)) {  // 微信公众平台
            String secret = LovUtil.get(tenantId, LovConstants.WECHAT_CATEGORY, LovConstants.WX_MP_SECRET);
            String token = LovUtil.get(tenantId, LovConstants.WECHAT_CATEGORY, LovConstants.WX_MP_TOKEN);
            String aesKey = LovUtil.get(tenantId, LovConstants.WECHAT_CATEGORY, LovConstants.WX_MP_AES_KEY);

            WxMpRedissonConfigImpl config = new WxMpRedissonConfigImpl(redissonClient, "open:wx_mp");

            config.setAppId(appid);
            config.setSecret(secret);
            if (StringUtils.hasText(token)) {
                config.setToken(token);
            }
            if (StringUtils.hasText(aesKey)) {
                config.setAesKey(aesKey);
            }

            WxMpService wxMpService = new WxMpServiceImpl();
            wxMpService.addConfigStorage(appid, config);

            WX_SERVICES.put(WxMpService.class, wxMpService);

            return wxMpService.switchoverTo(appid);
        } else if (WxCpService.class.equals(t)) {  //企业微信
            String secret = LovUtil.get(tenantId, LovConstants.WECHAT_CATEGORY, LovConstants.WX_CP_SECRET);
            String token = LovUtil.get(tenantId, LovConstants.WECHAT_CATEGORY, LovConstants.WX_CP_TOKEN);
            String aesKey = LovUtil.get(tenantId, LovConstants.WECHAT_CATEGORY, LovConstants.WX_CP_AES_KEY);

            WxCpRedissonConfigImpl config = new WxCpRedissonConfigImpl(redissonClient, "open:wx_cp");

            config.setCorpId(appid);
            config.setCorpSecret(secret);
            if (StringUtils.hasText(token)) {
                config.setToken(token);
            }
            if (StringUtils.hasText(aesKey)) {
                config.setAesKey(aesKey);
            }

            WxCpService wxCpService = new WxCpServiceImpl();
            wxCpService.setWxCpConfigStorage(config);

            WX_CP_SERVICES.put(appid, wxCpService);
            return wxCpService;
        }

        return null;
    }
}
