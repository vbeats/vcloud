package com.codestepfish.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.codestepfish.admin.dto.tenant.TenantOut;
import com.codestepfish.admin.dto.tenant.TenantQueryIn;
import com.codestepfish.admin.service.TenantService;
import com.codestepfish.common.constant.redis.CacheEnum;
import com.codestepfish.common.model.AppUser;
import com.codestepfish.common.result.PageOut;
import com.codestepfish.core.annotation.PreAuth;
import com.codestepfish.datasource.entity.Tenant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tenant")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@PreAuth
public class TenantController {

    private final TenantService tenantService;

    @PostMapping("/list")
    public PageOut<List<TenantOut>> list(@RequestBody TenantQueryIn param) {
        return tenantService.listTenant(param);
    }

    @PostMapping("/listV2")
    @PreAuth(superOnly = false)
    public List<TenantOut> listV2(AppUser user) {
        return tenantService.listV2(user);
    }

    @PostMapping("/sub")
    public List<TenantOut> subTenant(@RequestBody TenantQueryIn param, AppUser user) {
        return tenantService.subTenant(param, user);
    }

    @PostMapping("/add")
    public String add(@RequestBody Tenant tenant) {
        Tenant exist = tenantService.getOne(Wrappers.<Tenant>lambdaQuery().eq(Tenant::getCode, tenant.getCode()));
        Assert.isNull(exist, "租户编号已存在");

        tenantService.save(tenant);
        return String.valueOf(tenant.getId());
    }

    @PostMapping("/update")
    @CacheEvict(value = CacheEnum.ADMIN_CACHE, allEntries = true)
    public void update(@RequestBody Tenant tenant) {
        Tenant exist = tenantService.getOne(Wrappers.<Tenant>lambdaQuery().eq(Tenant::getCode, tenant.getCode()));
        Assert.isTrue(ObjectUtils.isEmpty(exist) || exist.getId().equals(tenant.getId()), "租户编号已存在");

        tenant.setUpdateTime(LocalDateTime.now());
        tenantService.updateById(tenant);
    }

    @PostMapping("/delete")
    @CacheEvict(value = CacheEnum.ADMIN_CACHE, allEntries = true)
    public void delete(@RequestBody Tenant tenant) {
        Tenant t = tenantService.getById(tenant.getId());

        // 存在下级的不能删除
        List<Tenant> exists = tenantService.list(Wrappers.<Tenant>lambdaQuery().eq(Tenant::getPid, t.getId()).isNull(Tenant::getDeleteTime));
        Assert.isTrue(CollectionUtils.isEmpty(exists), "存在子级,不可删除");

        t.setDeleteTime(LocalDateTime.now());

        tenantService.updateById(t);
    }

    @PostMapping("/block")
    @CacheEvict(value = CacheEnum.ADMIN_CACHE, allEntries = true)
    public void block(@RequestBody List<Tenant> tenants) {
        toggleBlockStatus(tenants, false);
    }

    @PostMapping("/unblock")
    @CacheEvict(value = CacheEnum.ADMIN_CACHE, allEntries = true)
    public void unblock(@RequestBody List<Tenant> tenants) {
        toggleBlockStatus(tenants, true);
    }

    private void toggleBlockStatus(List<Tenant> tenants, boolean status) {
        if (CollectionUtils.isEmpty(tenants)) {
            return;
        }
        List<Tenant> ts = tenantService.list(Wrappers.<Tenant>lambdaQuery().in(Tenant::getId, tenants.stream().map(Tenant::getId).collect(Collectors.toSet())));

        ts.forEach(t -> {
            t.setStatus(status);
            t.setUpdateTime(LocalDateTime.now());
        });

        tenantService.updateBatchById(ts);
    }
}
