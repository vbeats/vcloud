package com.codestepfish.admin.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codestepfish.admin.dto.tenant.TenantQueryIn;
import com.codestepfish.admin.entity.Tenant;
import com.codestepfish.admin.service.TenantService;
import com.codestepfish.core.result.PageOut;
import com.codestepfish.core.util.PidsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/tenant")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TenantController {

    private final TenantService tenantService;

    @SaCheckRole(value = {"super_admin"})
    @GetMapping("/list")  // 所有租户
    public PageOut<List<Tenant>> list(TenantQueryIn param) {
        if (StringUtils.hasText(param.getCode()) || StringUtils.hasText(param.getTenantName())) {
            param.setPid(null);
        }
        Page<Tenant> pages = tenantService.listTenant(new Page<>(param.getCurrent(), param.getPageSize()), param.getPid(), param.getCode(), param.getTenantName());

        PageOut<List<Tenant>> out = new PageOut<>();

        out.setTotal(pages.getTotal());
        out.setRows(pages.getRecords());

        return out;
    }

    @GetMapping("/listTenantTree")
    public List<Tenant> listTenantTree() {
        Long id = StpUtil.getLoginIdAsLong();
        Long tenantId = Long.valueOf(String.valueOf(StpUtil.getExtra("tenantId")));
        if (id.equals(1L)) {
            tenantId = null;
        }

        return tenantService.listTenantTree(tenantId);
    }

    @GetMapping("/sub")
    public List<Tenant> subTenant(TenantQueryIn param) {
        return tenantService.subTenant(param.getPid());
    }

    @SaCheckRole(value = {"super_admin"})
    @PostMapping("/add")
    public Tenant add(@RequestBody Tenant tenant) {
        String code = "M" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd")) + RandomUtil.randomNumbers(5);
        tenant.setCode(code);

        if (!tenant.getPid().equals(0L)) {
            Tenant pt = tenantService.getById(tenant.getPid());
            tenant.setPids(PidsUtil.appendPids(pt.getPids(), pt.getId()));
        }

        tenant.setCreateTime(LocalDateTime.now());
        tenantService.save(tenant);
        return tenant;
    }

    @SaCheckRole(value = {"super_admin"})
    @PostMapping("/update")
    public void update(@RequestBody Tenant tenant) {
        Tenant t = tenantService.getById(tenant.getId());
        t.setTenantName(tenant.getTenantName());
        t.setRemark(tenant.getRemark());

        t.setUpdateTime(LocalDateTime.now());
        tenantService.updateById(t);
    }
}
