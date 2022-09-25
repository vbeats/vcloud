package com.codestepfish.admin.controller;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codestepfish.admin.dto.open.OpenConfigQueryIn;
import com.codestepfish.admin.dto.open.OpenConfigVo;
import com.codestepfish.admin.service.OpenConfigService;
import com.codestepfish.common.model.AppUser;
import com.codestepfish.common.result.PageOut;
import com.codestepfish.core.annotation.PreAuth;
import com.codestepfish.datasource.entity.OpenConfig;
import com.codestepfish.datasource.type.OpenTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@PreAuth
public class OpenConfigController {

    private final OpenConfigService openConfigService;

    @PostMapping("/list")
    public PageOut<List<OpenConfigVo>> list(@RequestBody OpenConfigQueryIn param, AppUser user) {
        Long tenantId = ObjectUtils.isEmpty(param.getTenantId()) ? user.getTenantId() : param.getTenantId();
        Page<OpenConfigVo> page = openConfigService.listConfigs(new Page<>(param.getCurrent(), param.getPageSize()), tenantId, param.getName());
        PageOut<List<OpenConfigVo>> out = new PageOut<>();
        out.setTotal(page.getTotal());
        out.setRows(page.getRecords());

        return out;
    }

    @PostMapping("/add")
    public void add(@RequestBody OpenConfigVo param) {
        Assert.hasText(param.getName(), "参数错误");
        OpenConfig exist = openConfigService.getOne(Wrappers.<OpenConfig>lambdaQuery().eq(OpenConfig::getTenantId, param.getTenantId()).eq(OpenConfig::getName, param.getName()).isNull(OpenConfig::getDeleteTime));
        Assert.isNull(exist, "名称已存在");
        OpenConfig config = new OpenConfig();

        config.setTenantId(param.getTenantId());
        config.setName(param.getName());
        config.setType(OpenTypeEnum.find(param.getType()));
        config.setConfig(JSON.parseObject(param.getConfig()));
        config.setCreateTime(LocalDateTime.now());
        openConfigService.save(config);
    }

    @PostMapping("/update")
    public void update(@RequestBody OpenConfigVo param) {
        Assert.hasText(param.getName(), "参数错误");
        OpenConfig exist = openConfigService.getOne(Wrappers.<OpenConfig>lambdaQuery().eq(OpenConfig::getTenantId, param.getTenantId()).eq(OpenConfig::getName, param.getName()).isNull(OpenConfig::getDeleteTime));
        Assert.isTrue(ObjectUtils.isEmpty(exist) || exist.getId().equals(param.getId()), "名称已存在");

        OpenConfig config = openConfigService.getById(param.getId());
        config.setName(param.getName());
        config.setType(OpenTypeEnum.find(param.getType()));
        config.setConfig(JSON.parseObject(param.getConfig()));
        config.setUpdateTime(LocalDateTime.now());
        openConfigService.updateById(config);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody OpenConfigVo param) {
        OpenConfig config = openConfigService.getById(param.getId());
        config.setDeleteTime(LocalDateTime.now());

        openConfigService.updateById(config);
    }
}
