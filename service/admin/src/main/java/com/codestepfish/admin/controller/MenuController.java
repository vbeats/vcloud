package com.codestepfish.admin.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.codestepfish.admin.dto.menu.MenuIn;
import com.codestepfish.admin.entity.Menu;
import com.codestepfish.admin.entity.RoleMenu;
import com.codestepfish.admin.service.MenuService;
import com.codestepfish.admin.service.RoleMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@SaCheckRole(value = {"super_admin"})
public class MenuController {

    private final MenuService menuService;
    private final RoleMenuService roleMenuService;

    @PostMapping("/menus")
    public List<Tree<String>> menus() {
        return menuService.menus();
    }

   /* @PostMapping("/list")
    public List<Menu> list() {
        return menuService.listMenu();
    }

    @PostMapping("/add")
    public String add(@RequestBody Menu menu) {
        menuService.save(menu);
        return String.valueOf(menu.getId());
    }

    @PostMapping("/update")
    public void update(@RequestBody Menu menu) {
        menuService.updateById(menu);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody Menu menu) {
        Menu m = menuService.getById(menu.getId());

        // 存在子级的不能删除
        List<Menu> subMenus = menuService.list(Wrappers.<Menu>lambdaQuery().eq(Menu::getPid, m.getId()).isNull(Menu::getDeleteTime));
        Assert.isTrue(CollectionUtils.isEmpty(subMenus), "存在子级,不可删除");

        menuService.removeById(m);

        // 已分配的菜单删除掉
        roleMenuService.remove(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getMenuId, menu.getId()));
    }

    @PostMapping("/listRoleMenu")
    public List<String> listRoleMenu(@RequestBody MenuIn param) {
        List<RoleMenu> roleMenus = roleMenuService.list(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId, param.getRoleId()));

        return roleMenus.stream().map(e -> String.valueOf(e.getMenuId())).collect(Collectors.toList());
    }*/
}
