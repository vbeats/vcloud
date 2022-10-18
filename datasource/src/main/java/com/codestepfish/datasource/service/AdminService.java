package com.codestepfish.datasource.service;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codestepfish.common.constant.redis.CacheEnum;
import com.codestepfish.datasource.entity.Admin;
import com.codestepfish.datasource.entity.Tenant;
import com.codestepfish.datasource.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class AdminService extends ServiceImpl<AdminMapper, Admin> implements IService<Admin> {
    private final TenantService tenantService;
    private final AdminMapper adminMapper;

    @Cacheable(cacheNames = CacheEnum.ADMIN_CACHE, key = "#id", unless = "#result==null")
    public Admin findById(Long id) {
        Admin admin = this.getOne(Wrappers.<Admin>lambdaQuery()
                .eq(Admin::getId, id)
                .eq(Admin::getStatus, true)
                .isNull(Admin::getDeleteTime)
        );

        if (ObjectUtils.isEmpty(admin)) {
            return null;
        }

        Tenant tenant = tenantService.getOne(Wrappers.<Tenant>lambdaQuery()
                .eq(Tenant::getCode, admin.getTenantCode())
                .eq(Tenant::getStatus, true)
                .isNull(Tenant::getDeleteTime)
        );

        if (ObjectUtils.isEmpty(tenant)) {
            return null;
        }

        return admin;
    }

    public Page<Admin> listAdmins(Page<Admin> page, String code, String account, String phone) {
        return adminMapper.listAdmins(page, code, account, phone);
    }
}
