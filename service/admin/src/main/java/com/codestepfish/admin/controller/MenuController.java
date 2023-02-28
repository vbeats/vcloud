package com.codestepfish.admin.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.codestepfish.admin.dto.menu.MenuIn;
import com.codestepfish.admin.entity.Menu;
import com.codestepfish.admin.entity.RoleMenu;
import com.codestepfish.admin.service.MenuService;
import com.codestepfish.admin.service.RoleMenuService;
import com.codestepfish.core.constant.auth.AuthConstant;
import com.codestepfish.core.util.PidsUtil;
import com.google.common.base.Splitter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class MenuController {

    private final MenuService menuService;
    private final RoleMenuService roleMenuService;

    // 用户菜单
    @GetMapping("/menus")
    public List<Tree<String>> menus() {
        return menuService.menus();
    }

    // 菜单管理
    @SaCheckRole(value = {AuthConstant.SUPER_ADMIN})
    @GetMapping("/list")
    public List<Menu> list() {
        return menuService.listMenu();
    }

    @SaCheckRole(value = {AuthConstant.SUPER_ADMIN})
    @GetMapping("/sub")
    public List<Menu> list(MenuIn param) {
        return menuService.subMenu(param.getPid());
    }

    @SaCheckRole(value = {AuthConstant.SUPER_ADMIN})
    @PostMapping("/add")
    public String add(@RequestBody Menu menu) {
        if (!menu.getPid().equals(0L)) {
            Menu pm = menuService.getById(menu.getPid());
            menu.setPids(PidsUtil.appendPids(pm.getPids(), pm.getId()));
        }

        menuService.save(menu);
        return String.valueOf(menu.getId());
    }

    @SaCheckRole(value = {AuthConstant.SUPER_ADMIN})
    @PostMapping("/update")
    public void update(@RequestBody Menu menu) {
        menuService.updateById(menu);
    }

    @SaCheckRole(value = {AuthConstant.SUPER_ADMIN})
    @PostMapping("/delete")
    public void delete(@RequestBody Menu menu) {
        Menu m = menuService.getById(menu.getId());

        // 系统管理 不可删除
        Assert.isTrue(!m.getId().equals(1L) && !Splitter.on(",").trimResults().omitEmptyStrings().splitToList(m.getPids()).contains("1"), "系统菜单不可删除");

        // 存在子级的不能删除
        List<Menu> subMenus = menuService.list(Wrappers.<Menu>lambdaQuery().eq(Menu::getPid, m.getId()));
        Assert.isTrue(CollectionUtils.isEmpty(subMenus), "请先删除子级菜单");

        menuService.removeById(m);

        // 已分配的菜单删除掉
        roleMenuService.remove(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getMenuId, menu.getId()));
    }

    @SaCheckRole(value = {AuthConstant.SUPER_ADMIN})
    @GetMapping("/listRoleMenu")
    public List<String> listRoleMenu(MenuIn param) {
        if (param.getRoleId().equals(1L)) {
            // 所有菜单
            return menuService.list().stream().map(e -> String.valueOf(e.getId())).collect(Collectors.toList());
        }

        List<RoleMenu> roleMenus = roleMenuService.list(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId, param.getRoleId()));

        return roleMenus.stream().map(e -> String.valueOf(e.getMenuId())).collect(Collectors.toList());
    }
}
