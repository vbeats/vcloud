package com.codestepfish.datasource.config.auth;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.codestepfish.common.constant.redis.CacheEnum;
import com.codestepfish.datasource.entity.Admin;
import com.codestepfish.datasource.entity.Menu;
import com.codestepfish.datasource.entity.Role;
import com.codestepfish.datasource.entity.RoleMenu;
import com.codestepfish.datasource.service.AdminService;
import com.codestepfish.datasource.service.MenuService;
import com.codestepfish.datasource.service.RoleMenuService;
import com.codestepfish.datasource.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StpInterfaceImpl implements StpInterface {
    private final AdminService adminService;
    private final RoleService roleService;
    private final RoleMenuService roleMenuService;
    private final MenuService menuService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    @Cacheable(cacheNames = CacheEnum.PERMISSION_CACHE, key = "#loginId", unless = "#result?.size()==0")
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> perms = new ArrayList<>();

        String type = String.valueOf(StpUtil.getExtra("type"));
        try {
            if ("admin".equalsIgnoreCase(type)) {
                Admin admin = adminService.findById(Long.valueOf(loginId.toString()));
                Set<Long> menuIds = roleMenuService.list(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId, admin.getRoleId())).stream().map(RoleMenu::getMenuId).collect(Collectors.toSet());
                perms.addAll(menuService.list(Wrappers.<Menu>lambdaQuery().in(Menu::getId, menuIds).isNull(Menu::getDeleteTime)).stream().map(Menu::getAction).collect(Collectors.toList()));
            }
        } catch (Exception e) {
            log.error("permission查询异常: ", e);
        }
        return perms;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    @Cacheable(cacheNames = CacheEnum.ROLE_CACHE, key = "#loginId", unless = "#result?.size()==0")
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> roles = new ArrayList<>();

        String type = String.valueOf(StpUtil.getExtra("type"));
        try {
            if ("admin".equalsIgnoreCase(type)) {
                roles.add("admin");
                Admin admin = adminService.findById(Long.valueOf(loginId.toString()));
                roles.add(roleService.getOne(Wrappers.<Role>lambdaQuery().eq(Role::getId, admin.getRoleId()).isNull(Role::getDeleteTime)).getAction());
            } else if ("user".equalsIgnoreCase(type)) {
                roles.add("user");
            }
        } catch (Exception e) {
            log.error("role查询异常: ", e);
        }
        return roles;
    }

}
