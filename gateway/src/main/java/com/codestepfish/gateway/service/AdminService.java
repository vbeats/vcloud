package com.codestepfish.gateway.service;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codestepfish.common.constant.redis.CacheEnum;
import com.codestepfish.common.model.AppUser;
import com.codestepfish.datasource.entity.*;
import com.codestepfish.datasource.mapper.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminService extends ServiceImpl<AdminMapper, Admin> implements IService<Admin> {

    private final AdminMapper adminMapper;
    private final TenantMapper tenantMapper;
    private final AdminRoleMapper adminRoleMapper;
    private final ApiScopeMapper apiScopeMapper;
    private final RoleApiMapper roleApiMapper;

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

        return new AppUser(id, admin.getTenantCode());
    }

    public Set<Long> findRolesByAdminId(Long id) {
        return adminRoleMapper.selectList(Wrappers.<AdminRole>lambdaQuery().eq(AdminRole::getAdminId, id)).stream().map(AdminRole::getRoleId).collect(Collectors.toSet());
    }

    public boolean existApiScope(Set<Long> roleIds, String path) {
        List<ApiScope> apiScopes = apiScopeMapper.selectList(Wrappers.<ApiScope>lambdaQuery()
                .eq(ApiScope::getApiPath, path)
                .isNull(ApiScope::getDeleteTime));
        if (CollectionUtils.isEmpty(apiScopes)) {
            return true;  // api_scope 未指定的接口不校验
        }

        return roleApiMapper.exists(Wrappers.<RoleApi>lambdaQuery()
                .in(RoleApi::getRoleId, roleIds)
                .in(RoleApi::getApiScopeId, apiScopes.stream().map(ApiScope::getId).collect(Collectors.toList())));
    }
}
