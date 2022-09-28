package com.codestepfish.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codestepfish.admin.dto.tenant.TenantOut;
import com.codestepfish.admin.dto.tenant.TenantQueryIn;
import com.codestepfish.admin.mapper.TenantMapper;
import com.codestepfish.common.constant.config.ConfigParamEnum;
import com.codestepfish.common.model.AppUser;
import com.codestepfish.common.result.PageOut;
import com.codestepfish.datasource.entity.Tenant;
import com.codestepfish.datasource.service.ConfigParamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TenantService extends ServiceImpl<TenantMapper, Tenant> implements IService<Tenant> {

    private final TenantMapper tenantMapper;
    private final ConfigParamService configParamService;

    public PageOut<List<TenantOut>> listTenant(TenantQueryIn param) {

        if (StringUtils.hasText(param.getCode()) || StringUtils.hasText(param.getTenantName())) {
            param.setPid(null);
        }
        Page<TenantOut> pages = tenantMapper.listTenant(new Page<>(param.getCurrent(), param.getPageSize()), param);

        PageOut<List<TenantOut>> out = new PageOut<>();

        out.setTotal(pages.getTotal());
        out.setRows(pages.getRecords());

        return out;
    }

    public List<TenantOut> subTenant(TenantQueryIn param, AppUser user) {
        return tenantMapper.listTenant(param);
    }

    public List<TenantOut> listV2(AppUser user) {
        Long id = user.getTenantId();

        if (configParamService.getConfigByKey(ConfigParamEnum.SUPER_TENANT.getKey()).getConfigValue().equals(String.valueOf(user.getTenantId()))) {
            id = null;
        }

        return tenantMapper.listTenantV2(id);
    }
}
