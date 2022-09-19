package com.codestepfish.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.codestepfish.admin.dto.menu.MenuIn;
import com.codestepfish.admin.dto.menu.MenuOut;
import com.codestepfish.admin.service.MenuService;
import com.codestepfish.admin.service.RoleMenuService;
import com.codestepfish.core.annotation.PreAuth;
import com.codestepfish.datasource.entity.Menu;
import com.codestepfish.datasource.entity.RoleMenu;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
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
@PreAuth
public class MenuController {

    private final MenuService menuService;
    private final RoleMenuService roleMenuService;

    @PostMapping("/list")
    public List<MenuOut> list() {
        return menuService.listMenu();
    }

    @PostMapping("/sub")
    public List<MenuOut> list(@RequestBody MenuIn param) {
        return menuService.subMenu(param.getPid());
    }

    @PostMapping("/add")
    public String add(@RequestBody Menu menu) {
        Menu exist = menuService.getOne(Wrappers.<Menu>lambdaQuery().eq(Menu::getKey, menu.getKey()));
        Assert.isNull(exist, "key已存在");
        menuService.add(menu);
        return String.valueOf(menu.getId());
    }

    @PostMapping("/update")
    public void update(@RequestBody Menu menu) {
        Menu exist = menuService.getOne(Wrappers.<Menu>lambdaQuery().eq(Menu::getKey, menu.getKey()));
        Assert.isTrue(ObjectUtils.isEmpty(exist) || exist.getId().equals(menu.getId()), "key已存在");

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
    }
}
