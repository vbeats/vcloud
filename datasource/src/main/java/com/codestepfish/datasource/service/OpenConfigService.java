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
    private static final Map<String, Map<Class, Object>> WX_SERVICES = new ConcurrentHashMap<>();
    private final OpenConfigMapper openConfigMapper;
    private final RedissonClient redissonClient;

    public Page<OpenConfigVo> listConfigs(Page<OpenConfigVo> page, Long tenantId, String name) {
        return openConfigMapper.listConfigs(page, tenantId, name);
    }

    // 微信小程序
    @Cacheable(cacheNames = CacheEnum.OPEN_CACHE, key = "#appid.concat('_0')", unless = "#result==null")
    public OpenConfig findWByWxMiniAppid(String appid) {
        return this.getOne(Wrappers.<OpenConfig>lambdaQuery().apply("config->'$.appid'={0}", appid)
                .eq(OpenConfig::getType, OpenTypeEnum.WX_MINIAPP.getValue()).isNull(OpenConfig::getDeleteTime));
    }

    public <T> T findWxMaServiceByAppid(OpenConfig openConfig, Class<T> t) {
        OpenConfigData cf = openConfig.getConfig();
        String appid = cf.getAppid();
        Map<Class, Object> wxService = WX_SERVICES.get(appid);

        if (ObjectUtils.isEmpty(wxService)) {
            String secret = cf.getSecret();
            String token = cf.getToken();
            String aesKey = cf.getAesKey();
            String cloudEnv = cf.getCloudEnv();

            WxMaRedissonConfigImpl config = new WxMaRedissonConfigImpl(redissonClient, "cache:wechat");
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

            wxService = new HashMap<>();
            wxService.put(WxMaService.class, wxMaService);

            WX_SERVICES.put(appid, wxService);
        }
        return (T) WX_SERVICES.get(appid).get(t);
    }
}
