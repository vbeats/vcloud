package com.codestepfish.gateway.service;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codestepfish.common.constant.redis.CacheEnum;
import com.codestepfish.common.model.AppUser;
import com.codestepfish.datasource.entity.Admin;
import com.codestepfish.datasource.entity.Tenant;
import com.codestepfish.gateway.mapper.AdminMapper;
import com.codestepfish.gateway.mapper.TenantMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminService extends ServiceImpl<AdminMapper, Admin> implements IService<Admin> {

    private final AdminMapper adminMapper;
    private final TenantMapper tenantMapper;

    @Cacheable(cacheNames = CacheEnum.ADMIN_CACHE, key = "#id", unless = "#result==null")
    public AppUser findById(Long id) {
        Admin admin = adminMapper.selectOne(Wrappers.<Admin>lambdaQuery()
                .eq(Admin::getId, id)
                .eq(Admin::getStatus, true)
                .isNull(Admin::getDeleteTime)
        );

        if (ObjectUtils.isEmpty(admin)) {
            return null;
        }

        Tenant tenant = tenantMapper.selectOne(Wrappers.<Tenant>lambdaQuery()
                .eq(Tenant::getCode, admin.getTenantCode())
                .eq(Tenant::getStatus, true)
                .isNull(Tenant::getDeleteTime)
        );

        if (ObjectUtils.isEmpty(tenant)) {
            return null;
        }

        return new AppUser(id, admin.getRoleId(), tenant.getId(), admin.getTenantCode());
    }
}
