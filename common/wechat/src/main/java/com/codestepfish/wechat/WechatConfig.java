package com.codestepfish.wechat;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaRedissonConfigImpl;
import cn.hutool.extra.spring.SpringUtil;
import com.codestepfish.redis.constant.LovConstants;
import com.codestepfish.redis.util.LovUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.config.impl.WxCpRedissonConfigImpl;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpRedissonConfigImpl;
import org.redisson.api.RedissonClient;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 微信平台
 */
@Slf4j
public class WechatConfig {
    private static final Map<String, Map<Class, Object>> WX_SERVICES = new ConcurrentHashMap<>();  // Map<appid , Map<WxService.class , WxService instance>>

    /**
     * 根据appid 获取对应平台wxService
     *
     * @param merchantId 商户id
     * @param appid      微信平台appid
     * @param t          对应平台WxService.class
     * @param <T>        WxService instance
     * @return
     */
    public static <T> T findWxServiceByAppid(Long merchantId, String appid, Class<T> t) {
        Map<Class, Object> wxService = WX_SERVICES.get(appid);

        if (ObjectUtils.isEmpty(wxService)) {
            WX_SERVICES.put(appid, putWxService(merchantId, appid, t));
        }
        return (T) WX_SERVICES.get(appid).get(t);
    }

    /**
     * @param merchantId
     * @param appid
     * @param t
     * @return
     */
    private static <T> Map<Class, Object> putWxService(Long merchantId, String appid, Class<T> t) {
        Map<Class, Object> wxService = new HashMap<>();

        RedissonClient redissonClient = SpringUtil.getBean(RedissonClient.class);

        if (WxMaService.class.equals(t)) {     // 微信小程序
            String secret = LovUtil.get(merchantId, LovConstants.WECHAT_CATEGORY, LovConstants.WX_MA_SECRET);
            String token = LovUtil.get(merchantId, LovConstants.WECHAT_CATEGORY, LovConstants.WX_MA_TOKEN);
            String aesKey = LovUtil.get(merchantId, LovConstants.WECHAT_CATEGORY, LovConstants.WX_MA_AES_KEY);
            String cloudEnv = LovUtil.get(merchantId, LovConstants.WECHAT_CATEGORY, LovConstants.WX_MA_CLOUD_ENV);

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
            wxMaService.setWxMaConfig(config);

            wxService.put(WxMaService.class, wxMaService);
        } else if (WxMpService.class.equals(t)) {  // 微信公众平台
            String secret = LovUtil.get(merchantId, LovConstants.WECHAT_CATEGORY, LovConstants.WX_MP_SECRET);
            String token = LovUtil.get(merchantId, LovConstants.WECHAT_CATEGORY, LovConstants.WX_MP_TOKEN);
            String aesKey = LovUtil.get(merchantId, LovConstants.WECHAT_CATEGORY, LovConstants.WX_MP_AES_KEY);

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
            wxMpService.setWxMpConfigStorage(config);

            wxService.put(WxMpService.class, wxMpService);
        } else if (WxCpService.class.equals(t)) {  //企业微信
            String secret = LovUtil.get(merchantId, LovConstants.WECHAT_CATEGORY, LovConstants.WX_CP_SECRET);
            String token = LovUtil.get(merchantId, LovConstants.WECHAT_CATEGORY, LovConstants.WX_CP_TOKEN);
            String aesKey = LovUtil.get(merchantId, LovConstants.WECHAT_CATEGORY, LovConstants.WX_CP_AES_KEY);

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

            wxService.put(WxCpService.class, wxCpService);
        }
        return wxService;
    }
}
