package com.codestepfish.gateway.service;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codestepfish.common.constant.redis.CacheEnum;
import com.codestepfish.common.model.AppUser;
import com.codestepfish.datasource.entity.Tenant;
import com.codestepfish.datasource.entity.User;
import com.codestepfish.gateway.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService extends ServiceImpl<UserMapper, User> implements IService<User> {

    private final TenantService tenantService;

    @Cacheable(cacheNames = CacheEnum.USER_CACHE, key = "#id", unless = "#result==null")
    public AppUser findById(Long id) {
        User user = this.getOne(Wrappers.<User>lambdaQuery().eq(User::getId, id).isNull(User::getDeleteTime));

        if (ObjectUtils.isEmpty(user)) {
            return null;
        }

        Tenant tenant = tenantService.getOne(Wrappers.<Tenant>lambdaQuery()
                .eq(Tenant::getId, user.getTenantId())
                .eq(Tenant::getStatus, true)
                .isNull(Tenant::getDeleteTime)
        );

        if (ObjectUtils.isEmpty(tenant)) {
            return null;
        }

        return new AppUser(id, 0L, tenant.getId());
    }
}
