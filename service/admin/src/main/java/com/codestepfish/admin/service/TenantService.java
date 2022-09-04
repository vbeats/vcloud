package com.codestepfish.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codestepfish.admin.dto.tenant.TenantOut;
import com.codestepfish.admin.dto.tenant.TenantQueryIn;
import com.codestepfish.admin.mapper.TenantMapper;
import com.codestepfish.common.result.PageOut;
import com.codestepfish.datasource.entity.Tenant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TenantService extends ServiceImpl<TenantMapper, Tenant> implements IService<Tenant> {

    private final TenantMapper tenantMapper;

    public PageOut<List<TenantOut>> listTenant(TenantQueryIn param) {

        Page<TenantOut> pages = tenantMapper.listTenant(new Page<>(param.getCurrent(), param.getPageSize()), param);

        PageOut<List<TenantOut>> out = new PageOut<>();

        out.setTotal(pages.getTotal());
        out.setRows(pages.getRecords());

        return out;
    }

    public List<TenantOut> subTenant(TenantQueryIn param) {
        return tenantMapper.listTenant(param);
    }
}
