package com.codestepfish.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.codestepfish.admin.dto.config.ConfigQueryIn;
import com.codestepfish.common.constant.redis.CacheEnum;
import com.codestepfish.common.result.PageOut;
import com.codestepfish.core.annotation.PreAuth;
import com.codestepfish.datasource.entity.ConfigParam;
import com.codestepfish.datasource.service.ConfigParamService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/configParam")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@PreAuth
public class ConfigParamController {

    private final ConfigParamService configParamService;

    @PostMapping("/list")
    public PageOut<List<ConfigParam>> list(@RequestBody ConfigQueryIn param) {
        return configParamService.listConfig(param.getCurrent(), param.getPageSize(), param.getConfigKey());
    }

    @PostMapping("/add")
    public void add(@RequestBody ConfigParam configParam) {

        ConfigParam exist = configParamService.getOne(Wrappers.<ConfigParam>lambdaQuery().eq(ConfigParam::getConfigKey, configParam.getConfigKey()));
        Assert.isNull(exist, "key已存在");

        configParamService.save(configParam);
    }

    @PostMapping("/update")
    @CacheEvict(cacheNames = CacheEnum.CONFIG_CACHE, key = "#configParam.configKey")
    public void update(@RequestBody ConfigParam configParam) {
        ConfigParam exist = configParamService.getOne(Wrappers.<ConfigParam>lambdaQuery().eq(ConfigParam::getConfigKey, configParam.getConfigKey()));
        Assert.isTrue(ObjectUtils.isEmpty(exist) || exist.getId().equals(configParam.getId()), "客户端ID已存在");

        configParam.setUpdateTime(LocalDateTime.now());
        configParamService.updateById(configParam);
    }

    @PostMapping("/delete")
    @CacheEvict(cacheNames = CacheEnum.CONFIG_CACHE, key = "#configParam.configKey")
    public void delete(@RequestBody ConfigParam configParam) {
        ConfigParam config = configParamService.getById(configParam.getId());
        config.setDeleteTime(LocalDateTime.now());

        configParamService.updateById(config);
    }
}
