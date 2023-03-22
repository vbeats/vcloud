package com.codestepfish.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codestepfish.admin.entity.Tenant;
import com.codestepfish.admin.mapper.TenantMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TenantService extends ServiceImpl<TenantMapper, Tenant> implements IService<Tenant> {

    private final TenantMapper tenantMapper;

    public Page<Tenant> listTenant(Page<Tenant> page, Long pid, String code, String tenantName) {
        Tenant param = new Tenant();
        param.setPid(pid);
        param.setCode(code);
        param.setTenantName(tenantName);
        return tenantMapper.listTenant(page, param);
    }

    public List<Tenant> subTenant(Long pid) {
        Tenant param = new Tenant();
        param.setPid(pid);
        return tenantMapper.listTenant(param);
    }

    public List<Tenant> listTenantTree(Long tenantId) {
        return tenantMapper.listTenantTree(tenantId);
    }
}
