package com.codestepfish.admin.feign;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.codestepfish.admin.entity.Tenant;
import com.codestepfish.admin.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tenant")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TenantFeignController {

    private final TenantService tenantService;

    @GetMapping("/getTenantByCode")
    public Tenant getTenantByCode(@RequestParam(value = "tenant_code") String tenantCode) {
        return tenantService.getOne(Wrappers.<Tenant>lambdaQuery().eq(Tenant::getCode, tenantCode));
    }

    @GetMapping("/getTenantById")
    public Tenant getTenantById(@RequestParam(value = "tenant_id") Long tenantId) {
        return tenantService.getById(tenantId);
    }
}
