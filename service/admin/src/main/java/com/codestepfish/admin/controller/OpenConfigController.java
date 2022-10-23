package com.codestepfish.admin.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codestepfish.admin.dto.open.OpenConfigQueryIn;
import com.codestepfish.common.constant.redis.CacheEnum;
import com.codestepfish.common.result.PageOut;
import com.codestepfish.datasource.entity.OpenConfig;
import com.codestepfish.datasource.model.OpenConfigData;
import com.codestepfish.datasource.model.OpenConfigVo;
import com.codestepfish.datasource.service.OpenConfigService;
import com.codestepfish.datasource.type.OpenTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequestMapping("/open")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SaCheckRole(value = {"super_admin"})
public class OpenConfigController {

    private final OpenConfigService openConfigService;

    @PostMapping("/list")
    public PageOut<List<OpenConfigVo>> list(@RequestBody OpenConfigQueryIn param) {
        Long tenantId = ObjectUtils.isEmpty(param.getTenantId()) ? Long.valueOf(String.valueOf(StpUtil.getExtra("tenantId"))) : param.getTenantId();
        Page<OpenConfigVo> page = openConfigService.listConfigs(new Page<>(param.getCurrent(), param.getPageSize()), tenantId, param.getName());
        PageOut<List<OpenConfigVo>> out = new PageOut<>();
        out.setTotal(page.getTotal());
        out.setRows(page.getRecords());

        return out;
    }

    @PostMapping("/add")
    public void add(@RequestBody OpenConfigVo param) {
        Assert.hasText(param.getName(), "参数错误");
        OpenConfigData configData = JSON.parseObject(param.getConfig(), OpenConfigData.class);
        Assert.hasText(configData.getAppid(), "参数错误");
        LambdaQueryWrapper<OpenConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.isNull(OpenConfig::getDeleteTime);

        wrapper.apply("config->'$.appid'={0}", configData.getAppid());

        OpenConfig exist = openConfigService.getOne(wrapper);

        Assert.isNull(exist, "appid已存在");

        OpenConfig config = new OpenConfig();

        config.setTenantId(param.getTenantId());
        config.setName(param.getName());
        config.setType(OpenTypeEnum.find(param.getType()));
        config.setConfig(configData);

        config.setCreateTime(LocalDateTime.now());
        openConfigService.save(config);
    }

    @PostMapping("/update")
    @CacheEvict(cacheNames = CacheEnum.OPEN_CACHE, allEntries = true)
    public void update(@RequestBody OpenConfigVo param) {
        Assert.hasText(param.getName(), "参数错误");
        OpenConfigData configData = JSON.parseObject(param.getConfig(), OpenConfigData.class);
        Assert.hasText(configData.getAppid(), "参数错误");

        LambdaQueryWrapper<OpenConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.isNull(OpenConfig::getDeleteTime);

        wrapper.apply("config->'$.appid'={0}", configData.getAppid());

        OpenConfig exist = openConfigService.getOne(wrapper);

        Assert.isTrue(ObjectUtils.isEmpty(exist) || exist.getId().equals(param.getId()), "appid已存在");

        OpenConfig config = openConfigService.getById(param.getId());
        config.setName(param.getName());
        config.setType(OpenTypeEnum.find(param.getType()));
        config.setConfig(JSON.parseObject(param.getConfig(), OpenConfigData.class));
        config.setUpdateTime(LocalDateTime.now());
        openConfigService.updateById(config);
    }

    @PostMapping("/delete")
    @CacheEvict(cacheNames = CacheEnum.OPEN_CACHE, allEntries = true)
    public void delete(@RequestBody OpenConfigVo param) {
        OpenConfig config = openConfigService.getById(param.getId());
        config.setDeleteTime(LocalDateTime.now());

        openConfigService.updateById(config);
    }

}
