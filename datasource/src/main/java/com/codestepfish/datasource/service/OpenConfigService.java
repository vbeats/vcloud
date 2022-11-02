package com.codestepfish.datasource.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaRedissonConfigImpl;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codestepfish.common.constant.redis.CacheEnum;
import com.codestepfish.datasource.entity.OpenConfig;
import com.codestepfish.datasource.mapper.OpenConfigMapper;
import com.codestepfish.datasource.model.OpenConfigData;
import com.codestepfish.datasource.model.OpenConfigVo;
import com.codestepfish.datasource.type.OpenTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.config.impl.WxCpRedissonConfigImpl;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpRedissonConfigImpl;
import me.chanjar.weixin.open.api.WxOpenComponentService;
import me.chanjar.weixin.open.api.WxOpenService;
import me.chanjar.weixin.open.api.impl.WxOpenComponentServiceImpl;
import me.chanjar.weixin.open.api.impl.WxOpenInRedissonConfigStorage;
import me.chanjar.weixin.open.api.impl.WxOpenServiceImpl;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OpenConfigService extends ServiceImpl<OpenConfigMapper, OpenConfig> implements IService<OpenConfig> {
    private static final Map<String, Map<Class, Object>> WX_SERVICES = new ConcurrentHashMap<>();  // Map<appid , Map<WxService.class , WxService instance>>
    private final OpenConfigMapper openConfigMapper;
    private final RedissonClient redissonClient;

    public Page<OpenConfigVo> listConfigs(Page<OpenConfigVo> page, Long tenantId, String name) {
        return openConfigMapper.listConfigs(page, tenantId, name);
    }

    /**
     * 根据小程序appid 获取对应的 平台配置信息
     *
     * @param appid 对应微信小程序的appid
     * @return
     */
    @Cacheable(cacheNames = CacheEnum.OPEN_CACHE, key = "#appid.concat('_0')", unless = "#result==null")
    public OpenConfig findByWxMaAppid(String appid) {
        return this.getOne(Wrappers.<OpenConfig>lambdaQuery().apply("config->'$.appid'={0}", appid)
                .eq(OpenConfig::getType, OpenTypeEnum.WX_MA.getValue()).isNull(OpenConfig::getDeleteTime));
    }

    /**
     * 根据appid 获取对应平台wxService
     *
     * @param openConfig 平台配置
     * @param t          对应平台WxService.class
     * @param <T>        WxService instance
     * @return
     */
    public <T> T findWxServiceByAppid(OpenConfig openConfig, Class<T> t) {
        OpenConfigData cf = openConfig.getConfig();
        String appid = cf.getAppid();
        Map<Class, Object> wxService = WX_SERVICES.get(appid);

        if (ObjectUtils.isEmpty(wxService)) {
            WX_SERVICES.put(appid, putWxService(cf, appid, t));
        }
        return (T) WX_SERVICES.get(appid).get(t);
    }

    /**
     * @param cf
     * @param appid
     * @param t
     * @return
     */
    private <T> Map<Class, Object> putWxService(OpenConfigData cf, String appid, Class<T> t) {
        Map<Class, Object> wxService = new HashMap<>();

        if (WxMaService.class.equals(t)) {     // 微信小程序
            String secret = cf.getSecret();
            String token = cf.getToken();
            String aesKey = cf.getAesKey();
            String cloudEnv = cf.getCloudEnv();

            WxMaRedissonConfigImpl config = new WxMaRedissonConfigImpl(redissonClient, "open:wx_ma");

            config.setAppid(appid);
            config.setSecret(secret);
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
            String secret = cf.getSecret();
            String token = cf.getToken();
            String aesKey = cf.getAesKey();

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
            String secret = cf.getSecret();
            String token = cf.getToken();
            String aesKey = cf.getAesKey();

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
        } else if (WxOpenService.class.equals(t)) {  // 微信开放平台 基础服务
            String secret = cf.getSecret();
            String token = cf.getToken();
            String aesKey = cf.getAesKey();

            WxOpenInRedissonConfigStorage config = new WxOpenInRedissonConfigStorage(redissonClient, "open:wx_open");

            config.setComponentAppId(appid);
            config.setComponentAppSecret(secret);
            if (StringUtils.hasText(token)) {
                config.setComponentToken(token);
            }
            if (StringUtils.hasText(aesKey)) {
                config.setComponentAesKey(aesKey);
            }

            WxOpenService wxOpenService = new WxOpenServiceImpl();
            wxOpenService.setWxOpenConfigStorage(config);

            wxService.put(WxOpenService.class, wxOpenService);
        } else if (WxOpenComponentService.class.equals(t)) {  // 微信开放平台
            WxOpenService wxOpenService = (WxOpenService) WX_SERVICES.get(appid).get(WxOpenService.class);

            WxOpenComponentService wxOpenComponentService = new WxOpenComponentServiceImpl(wxOpenService);

            wxService.put(WxOpenComponentService.class, wxOpenComponentService);
        }
        return wxService;
    }
}
