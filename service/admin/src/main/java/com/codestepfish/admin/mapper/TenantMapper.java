package com.codestepfish.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codestepfish.admin.entity.Tenant;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TenantMapper extends BaseMapper<Tenant> {
    Page<Tenant> listTenant(Page<Tenant> page, @Param("param") Tenant param);

    List<Tenant> listTenant(@Param("param") Tenant param);

    List<Tenant> listTenantTree(@Param("tenant_id") Long tenantId);
}
