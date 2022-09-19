package com.codestepfish.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codestepfish.admin.dto.tenant.TenantOut;
import com.codestepfish.admin.dto.tenant.TenantQueryIn;
import com.codestepfish.common.result.PageOut;
import com.codestepfish.datasource.entity.Tenant;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TenantMapper extends BaseMapper<Tenant> {
    Page<TenantOut> listTenant(Page<PageOut> page, @Param("param") TenantQueryIn param);

    List<TenantOut> listTenant(@Param("param") TenantQueryIn param);

    List<TenantOut> listTenantV2(@Param("id") Long id);
}
