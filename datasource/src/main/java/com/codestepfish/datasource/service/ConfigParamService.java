package com.codestepfish.datasource.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codestepfish.common.constant.redis.CacheEnum;
import com.codestepfish.common.result.PageOut;
import com.codestepfish.datasource.entity.ConfigParam;
import com.codestepfish.datasource.mapper.ConfigParamMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConfigParamService extends ServiceImpl<ConfigParamMapper, ConfigParam> implements IService<ConfigParam> {

    private final ConfigParamMapper configParamMapper;

    @Cacheable(cacheNames = CacheEnum.CONFIG_CACHE, key = "#key", unless = "#result==null")
    public ConfigParam getConfigByKey(String key) {
        return configParamMapper.selectOne(Wrappers.<ConfigParam>lambdaQuery().eq(ConfigParam::getConfigKey, key).isNull(ConfigParam::getDeleteTime));
    }

    public PageOut<List<ConfigParam>> listConfig(Long current, Long pageSize, String configKey) {
        LambdaQueryWrapper<ConfigParam> query = Wrappers.lambdaQuery();

        query.isNull(ConfigParam::getDeleteTime).orderByAsc(ConfigParam::getCreateTime);

        if (StringUtils.hasText(configKey)) {
            query.eq(ConfigParam::getConfigKey, configKey);
        }
        Page<ConfigParam> pages = this.page(new Page<>(current, pageSize), query);

        PageOut<List<ConfigParam>> out = new PageOut<>();

        out.setTotal(pages.getTotal());
        out.setRows(pages.getRecords());

        return out;
    }
}
