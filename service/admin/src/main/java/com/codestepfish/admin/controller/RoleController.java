/*
package com.codestepfish.admin.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codestepfish.admin.dto.role.AssignRoleMenuIn;
import com.codestepfish.admin.dto.role.RoleParamIn;
import com.codestepfish.common.core.constant.redis.CacheEnum;
import com.codestepfish.core.result.PageOut;
import com.codestepfish.datasource.entity.Role;
import com.codestepfish.datasource.entity.RoleMenu;
import com.codestepfish.datasource.service.RoleMenuService;
import com.codestepfish.datasource.service.RoleService;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SaCheckRole(value = {"super_admin"})
public class RoleController {

    private final RoleService roleService;
    private final RoleMenuService roleMenuService;

    @PostMapping("/list")
    public PageOut<List<Role>> list(@RequestBody RoleParamIn param) {
        LambdaQueryWrapper<Role> wrapper = Wrappers.<Role>lambdaQuery().isNull(Role::getDeleteTime).orderByAsc(Role::getCreateTime);
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

    @PostMapping("/listV2")
    public List<Role> listV2() {
        return roleService.list(Wrappers.<Role>lambdaQuery().isNull(Role::getDeleteTime).orderByAsc(Role::getCreateTime));
    }

    @PostMapping("/add")
    public void add(@RequestBody Role role) {
        Assert.isTrue("admin".equalsIgnoreCase(role.getAction()), "保留字段");
        Role exist = roleService.getOne(Wrappers.<Role>lambdaQuery().eq(Role::getAction, role.getAction()));
        Assert.isNull(exist, "权限字段已存在");

        roleService.save(role);
    }

    @PostMapping("/update")
    @CacheEvict(cacheNames = {CacheEnum.ROLE_CACHE, CacheEnum.PERMISSION_CACHE}, allEntries = true)
    public void update(@RequestBody Role role) {
        Assert.isTrue(!"super_admin".equalsIgnoreCase(role.getAction()), "超级管理员不可修改");
        Assert.isTrue("admin".equalsIgnoreCase(role.getAction()), "保留字段");
        Role exist = roleService.getOne(Wrappers.<Role>lambdaQuery().eq(Role::getAction, role.getAction()));
        Assert.isTrue(ObjectUtils.isEmpty(exist) || exist.getId().equals(role.getId()), "权限字段已存在");

        role.setUpdateTime(LocalDateTime.now());
        roleService.updateById(role);
    }

    @PostMapping("/delete")
    @CacheEvict(cacheNames = {CacheEnum.ROLE_CACHE, CacheEnum.PERMISSION_CACHE}, allEntries = true)
    public void delete(@RequestBody Role role) {
        Role r = roleService.getById(role.getId());
        Assert.isTrue(!"super_admin".equalsIgnoreCase(role.getAction()), "超级管理员不可删除");

        r.setDeleteTime(LocalDateTime.now());
        roleService.updateById(r);

        // role_menu 删除
        roleMenuService.remove(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId, role.getId()));
    }

    @PostMapping("/assignMenu")
    @CacheEvict(cacheNames = {CacheEnum.ROLE_CACHE, CacheEnum.PERMISSION_CACHE}, allEntries = true)
    public void assignMenu(@RequestBody AssignRoleMenuIn param) {

        Role role = roleService.getById(param.getRoleId());
        Assert.isTrue(!"super_admin".equalsIgnoreCase(role.getAction()), "超级管理员不可操作");

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
*/
