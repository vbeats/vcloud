package com.codestepfish.admin.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.codestepfish.admin.entity.AdminRole;
import com.codestepfish.admin.entity.Menu;
import com.codestepfish.admin.entity.Role;
import com.codestepfish.admin.entity.RoleMenu;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PermissionService {

    private final RoleService roleService;
    private final AdminRoleService adminRoleService;
    private final MenuService menuService;
    private final RoleMenuService roleMenuService;

    public Set<String> getRoles(Long adminId) {
        Set<Long> roleIds = getRoleIds(adminId);
        return roleService.listByIds(roleIds).stream().map(Role::getAction).collect(Collectors.toSet());
    }

    public Set<String> getPermissions(Long adminId) {
        Set<Long> roleIds = getRoleIds(adminId);
        Set<Long> menuIds = roleMenuService.list(Wrappers.<RoleMenu>lambdaQuery().in(RoleMenu::getRoleId, roleIds)).stream().map(RoleMenu::getMenuId).collect(Collectors.toSet());
        return CollectionUtils.isEmpty(menuIds) ? Collections.emptySet() : menuService.listByIds(menuIds).stream().map(Menu::getPermission).collect(Collectors.toSet());
    }

    private Set<Long> getRoleIds(Long adminId) {
        return adminRoleService.list(Wrappers.<AdminRole>lambdaQuery().eq(AdminRole::getAdminId, adminId)).stream().map(AdminRole::getRoleId).collect(Collectors.toSet());
    }
}
