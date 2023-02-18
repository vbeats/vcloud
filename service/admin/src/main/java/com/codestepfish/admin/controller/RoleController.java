package com.codestepfish.admin.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codestepfish.admin.dto.role.AssignRoleMenuIn;
import com.codestepfish.admin.dto.role.RoleParamIn;
import com.codestepfish.admin.entity.AdminRole;
import com.codestepfish.admin.entity.Role;
import com.codestepfish.admin.entity.RoleMenu;
import com.codestepfish.admin.service.AdminRoleService;
import com.codestepfish.admin.service.RoleMenuService;
import com.codestepfish.admin.service.RoleService;
import com.codestepfish.core.result.PageOut;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SaCheckRole(value = {"super_admin"})
public class RoleController {

    private final RoleService roleService;
    private final AdminRoleService adminRoleService;
    private final RoleMenuService roleMenuService;

    @GetMapping("/list")
    public PageOut<List<Role>> list(RoleParamIn param) {
        LambdaQueryWrapper<Role> wrapper = Wrappers.lambdaQuery();
        if (StringUtils.hasText(param.getRoleName())) {
            wrapper.eq(Role::getRoleName, param.getRoleName());
        }
        if (StringUtils.hasText(param.getAction())) {
            wrapper.eq(Role::getAction, param.getAction());
        }

        Page<Role> pages = roleService.page(new Page<>(param.getCurrent(), param.getPageSize()), wrapper);

        PageOut<List<Role>> out = new PageOut<>();
        out.setTotal(pages.getTotal());
        out.setRows(pages.getRecords());

        return out;
    }

    @GetMapping("/listAdminRoles")
    public List<Role> listAdminRoles(RoleParamIn param) {
        Set<Long> roleIds = adminRoleService.list(Wrappers.<AdminRole>lambdaQuery().in(AdminRole::getAdminId, param.getAdminId())).stream().map(AdminRole::getRoleId).collect(Collectors.toSet());
        return CollectionUtils.isEmpty(roleIds) ? null : roleService.list(Wrappers.<Role>lambdaQuery().in(Role::getId, roleIds));
    }

    @PostMapping("/add")
    public void add(@RequestBody Role role) {
        Assert.isTrue(!Arrays.asList("super_admin", "admin", "*").contains(role.getAction().toLowerCase()), "保留字段 禁止使用");
        Role exist = roleService.getOne(Wrappers.<Role>lambdaQuery().eq(Role::getAction, role.getAction()).eq(Role::getRoleName, role.getRoleName()));
        Assert.isNull(exist, "此角色已存在");
        role.setAction(role.getAction().toLowerCase());

        roleService.save(role);
    }

    @PostMapping("/update")
    public void update(@RequestBody Role role) {
        Assert.isTrue(!role.getId().equals(1L), "此角色不可修改");
        Assert.isTrue(!"admin".equalsIgnoreCase(role.getAction()), "保留字段");
        Role exist = roleService.getOne(Wrappers.<Role>lambdaQuery().eq(Role::getAction, role.getAction()).eq(Role::getRoleName, role.getRoleName()));
        Assert.isTrue(ObjectUtils.isEmpty(exist) || exist.getId().equals(role.getId()), "此角色已存在");

        roleService.updateById(role);
    }


    @PostMapping("/delete")
    public void delete(@RequestBody Role role) {
        Role r = roleService.getById(role.getId());
        Assert.isTrue(!r.getId().equals(1L), "此角色不可删除");

        roleService.removeById(r);

        // role_menu 删除
        roleMenuService.remove(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId, role.getId()));
    }

    @PostMapping("/assignMenu")
    public void assignMenu(@RequestBody AssignRoleMenuIn param) {

        Role role = roleService.getById(param.getRoleId());
        Assert.isTrue(!role.getId().equals(1L), "此角色禁止修改");

        if (CollectionUtils.isEmpty(param.getMenuIds())) {
            roleMenuService.remove(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId, param.getRoleId()));
            return;
        }

        // 此角色已分配菜单
        List<RoleMenu> roleMenus = roleMenuService.list(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId, param.getRoleId()));
        Set<Long> menuIds = roleMenus.stream().map(e -> e.getMenuId()).collect(Collectors.toSet());

        // 需要删除的菜单
        Sets.SetView<Long> needRemoveIds = Sets.difference(menuIds, param.getMenuIds());
        // 需要新分配的菜单
        Sets.SetView<Long> needAddIds = Sets.difference(param.getMenuIds(), menuIds);

        if (!CollectionUtils.isEmpty(needRemoveIds)) {
            roleMenuService.remove(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId, param.getRoleId()).in(RoleMenu::getMenuId, needRemoveIds));
        }

        if (!CollectionUtils.isEmpty(needAddIds)) {
            List<RoleMenu> rms = needAddIds.stream().map(e -> new RoleMenu(null, param.getRoleId(), e)).collect(Collectors.toList());
            roleMenuService.saveBatch(rms);
        }
    }
}
